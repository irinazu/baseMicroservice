package com.courseproject.microservice.service;

import com.courseproject.microservice.model.Attribute;
import com.courseproject.microservice.model.Object;

import java.util.List;
import java.util.Optional;


public interface AttributeService {
    Attribute saveAttribute(Attribute object);

    List<Attribute> getAllAttributes();

    Optional<Attribute> findById(Long id);
}
