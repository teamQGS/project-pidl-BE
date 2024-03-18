package com.example.demo.model.Repositories;

import com.example.demo.model.Entities.StaffEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends CrudRepository<StaffEntity, Long> {
}
