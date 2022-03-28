package com.courseproject.microservice.service;

import com.courseproject.microservice.model.Object;
import com.courseproject.microservice.repository.ObjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjectServiceImpl implements ObjectService{

    ObjectRepository objectRepository;

    ObjectServiceImpl(ObjectRepository objectRepository){
        super();
        this.objectRepository=objectRepository;
    }
    @Override
    public Object saveObject(Object object) {
        return objectRepository.save(object);
    }

    @Override
    public void deleteObject(Long id) {
        objectRepository.deleteById(id);
    }

    @Override
    public List<Object> getAllObjects() {
        return objectRepository.findAll();
    }

    @Override
    public Optional<Object> getObject(Long id) {
        return objectRepository.findById(id);
    }
}
