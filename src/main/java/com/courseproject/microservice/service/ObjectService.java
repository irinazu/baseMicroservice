package com.courseproject.microservice.service;

import com.courseproject.microservice.model.Object;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ObjectService {
    Object saveObject(Object object);

    List<Object> getAllObjects();

    Optional<Object> getObject(Long id);

    void deleteObject(Long id);
}
