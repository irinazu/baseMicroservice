package com.courseproject.microservice.service;

import com.courseproject.microservice.model.Object;
import com.courseproject.microservice.model.ObjectType;

import java.util.List;
import java.util.Optional;

public interface ObjectTypeService {
    ObjectType saveObjectType(ObjectType object);

    List<ObjectType> getAllObjectType();

    Optional<ObjectType> getById(Long id);
}
