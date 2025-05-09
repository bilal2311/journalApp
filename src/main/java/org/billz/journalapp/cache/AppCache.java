package org.billz.journalapp.cache;

import jakarta.annotation.PostConstruct;
import org.billz.journalapp.entity.ConfigJournalAppEntity;
import org.billz.journalapp.repository.ConfigJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
public enum keys{
    WEATHER_API;
}

    @Autowired
    ConfigJournalRepository configJournalRepository;
    public Map<String,String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all=configJournalRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity:all){
            appCache.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }

    }

}
