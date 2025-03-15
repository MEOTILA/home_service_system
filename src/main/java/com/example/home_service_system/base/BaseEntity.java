package com.example.home_service_system.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@MappedSuperclass
public class BaseEntity<ID extends Serializable>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;
}