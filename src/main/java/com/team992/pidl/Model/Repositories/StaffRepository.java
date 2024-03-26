package com.team992.pidl.Model.Repositories;

import com.team992.pidl.Model.Entities.StaffEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends MongoRepository<StaffEntity, String> {
    
}
