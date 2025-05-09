package org.billz.journalapp.repository;


import org.billz.journalapp.entity.ConfigJournalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalRepository extends MongoRepository <ConfigJournalAppEntity, ObjectId> {

}
