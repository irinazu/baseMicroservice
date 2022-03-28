/*package com.courseproject.microservice.service;

import com.courseproject.microservice.model.ObjectTypeAttribute;
import com.courseproject.microservice.repository.ObjectTypeAttributeRepository;
import com.courseproject.microservice.repository.ObjectTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectTypeAttributeServiceImpl implements ObjectTypeAttributeService{

    ObjectTypeAttributeRepository objectTypeRepository;

    public ObjectTypeAttributeServiceImpl(ObjectTypeAttributeRepository objectTypeRepository) {
        super();
        this.objectTypeRepository = objectTypeRepository;
    }

    @Override
    public ObjectTypeAttribute saveObjectTypeAttribute(ObjectTypeAttribute object) {
        return objectTypeRepository.save(object);
    }

    @Override
    public List<ObjectTypeAttribute> getAllObjectTypeAttribute() {
        return objectTypeRepository.findAll();
    }
}
*/