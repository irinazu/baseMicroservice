package com.courseproject.microservice.service;

import com.courseproject.microservice.model.Attribute;
import com.courseproject.microservice.model.ObjectType;
import com.courseproject.microservice.repository.AttributeRepository;
import com.courseproject.microservice.repository.ObjectTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttributeServiceImpl implements AttributeService{

    AttributeRepository attributeRepository;

    AttributeServiceImpl(AttributeRepository attributeRepository){
        super();
        this.attributeRepository=attributeRepository;
    }

    @Override
    public Attribute saveAttribute(Attribute object) {
        return attributeRepository.save(object);
    }

    @Override
    public List<Attribute> getAllAttributes() {
        return attributeRepository.findAll();
    }

    @Override
    public Optional<Attribute> findById(Long id) {
        return attributeRepository.findById(id);
    }
}
