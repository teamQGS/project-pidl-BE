package com.example.demo.services;

import com.example.demo.DTOS.WarehouseDTO;
import com.example.demo.model.Entities.ProductEntity;
import com.example.demo.model.Entities.WarehouseEntity;
import com.example.demo.model.Repositories.WarehouseRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<WarehouseDTO> getAllWarehouses(){
        List<WarehouseEntity> warehouseEntities = warehouseRepository.findAll();
        return warehouseEntities.stream()
                .map(WarehouseEntity->modelMapper.map(warehouseEntities, WarehouseDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<WarehouseDTO> getWarehouseById (ObjectId id){
        Optional<WarehouseEntity> warehouseEntity = warehouseRepository.findById(id);
        return warehouseEntity.map(WarehouseEntity -> modelMapper.map(warehouseEntity, WarehouseDTO.class));

    }
}
