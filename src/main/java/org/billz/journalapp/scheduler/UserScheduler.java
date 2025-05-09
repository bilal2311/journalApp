package org.billz.journalapp.scheduler;

import org.billz.journalapp.cache.AppCache;
import org.billz.journalapp.entity.JournalEntry;
import org.billz.journalapp.entity.User;
import org.billz.journalapp.enums.Sentiment;
import org.billz.journalapp.repository.UserRepositoryImpl;
import org.billz.journalapp.service.EmailService;
import org.billz.journalapp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
   private EmailService emailService;

    @Autowired
   private  UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    @Autowired
    private AppCache appCache;

   // @Scheduled(cron = "0 0 9 ? * SUN *")
    public void fetchUsersAndSendSaMail(){

        List<User> users=userRepository.getUserForSA();
        for(User user:users){
            List<JournalEntry>journalEntries=user.getJournalEntryList();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment())
                    .collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                emailService.sendEmail(user.getEmail(),
                        "Sentiment for lats 7 days",mostFrequentSentiment.toString());
            }
        }
    }

   //@Scheduled(cron = "0 0 12 1/1 * ? *")
   public void clearAppCache(){
        appCache.init();

    }
}
