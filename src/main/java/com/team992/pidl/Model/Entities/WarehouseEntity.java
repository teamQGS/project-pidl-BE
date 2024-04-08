package com.team992.pidl.Model.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "warehouse")
@Data
@NoArgsConstructor
public class WarehouseEntity {

    @Id
    private String id;

    private Integer count;

    private Integer totalByes;

    // get id from product
    // private Set<String> products;
}