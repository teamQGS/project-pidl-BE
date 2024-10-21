package com.example.demo.model.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
    CREATED,
    IN_PROCESS,
    COMPLETED,
    PENDING, CANCELED
}
