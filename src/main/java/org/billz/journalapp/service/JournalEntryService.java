package org.billz.journalapp.service;

import org.billz.journalapp.entity.JournalEntry;
import org.billz.journalapp.entity.User;
import org.billz.journalapp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            User user=userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
           JournalEntry saved= journalEntryRepository.save(journalEntry);
           user.getJournalEntryList().add(saved);
           userService.saveEntry(user);
        }
        catch (Exception e){
            log.error("Exception ",e);
        }

    }

    public void saveEntry(JournalEntry journalEntry){
        try{
            journalEntryRepository.save(journalEntry);
        }
        catch (Exception e){
            log.error("Exception ",e);
        }

    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
       return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id,String userName){
        boolean removed=false;
        try{
            User user=userService.findByUserName(userName);
             removed=user.getJournalEntryList().removeIf(x->x.getId().equals(id));
            if(removed){
                userService.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
        }
        catch(Exception e){
            log.error("The exception occurred ",e);
            throw new RuntimeException("An error occurred when deleting entry "+e);

        }
            return removed;
    }

}
