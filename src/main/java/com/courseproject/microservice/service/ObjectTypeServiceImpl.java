package com.courseproject.microservice.service;

import com.courseproject.microservice.model.ObjectType;
import com.courseproject.microservice.repository.ObjectRepository;
import com.courseproject.microservice.repository.ObjectTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjectTypeServiceImpl implements ObjectTypeService{

    ObjectTypeRepository objectTypeRepository;

    ObjectTypeServiceImpl(ObjectTypeRepository objectTypeRepository){
        super();
        this.objectTypeRepository=objectTypeRepository;
    }
    @Override
    public ObjectType saveObjectType(ObjectType object) {
        return objectTypeRepository.save(object);
    }

    @Override
    public List<ObjectType> getAllObjectType() {
        return objectTypeRepository.findAll();
    }

    @Override
    public Optional<ObjectType> getById(Long id) {
        return objectTypeRepository.findById(id);
    }
}
