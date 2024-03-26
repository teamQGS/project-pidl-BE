package com.team992.pidl.Model.Repositories;

import com.team992.pidl.Model.Entities.WarehouseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends MongoRepository<WarehouseEntity, String> {
    
}
