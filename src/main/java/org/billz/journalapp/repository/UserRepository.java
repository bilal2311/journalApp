package org.billz.journalapp.repository;

import org.billz.journalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository <User, ObjectId> {

     User findByUserName(String userName);

    void deleteByUserName(String name);
}
