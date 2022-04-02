package com.courseproject.microservice.service;

import com.courseproject.microservice.model.TypeOfAttribute;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface TypeOfAttributeService {
    Optional<TypeOfAttribute> findById(Long aLong);

    List<TypeOfAttribute> findAll();

    TypeOfAttribute saveTypeOfAttribute(TypeOfAttribute typeOfAttribute);
}
