package com.example.demo.model.Repositories;

import com.example.demo.model.Entities.WarehouseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends CrudRepository<WarehouseEntity, Long> {
}
