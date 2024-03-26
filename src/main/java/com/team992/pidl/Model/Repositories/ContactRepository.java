package com.team992.pidl.Model.Repositories;

import com.team992.pidl.Model.Entities.ContactEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends MongoRepository<ContactEntity, String> {
    
}
