package com.courseproject.microservice.service;

import com.courseproject.microservice.model.TypeOfAttribute;
import com.courseproject.microservice.repository.TypeOfAttributeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeOfAttributeServiceImpl implements TypeOfAttributeService{
    TypeOfAttributeRepository typeOfAttributeRepository;

    public TypeOfAttributeServiceImpl(TypeOfAttributeRepository typeOfAttributeRepository) {
        this.typeOfAttributeRepository = typeOfAttributeRepository;
    }

    @Override
    public Optional<TypeOfAttribute> findById(Long aLong) {
        return typeOfAttributeRepository.findById(aLong);
    }

    @Override
    public List<TypeOfAttribute> findAll() {
        return typeOfAttributeRepository.findAll();
    }

    @Override
    public TypeOfAttribute saveTypeOfAttribute(TypeOfAttribute typeOfAttribute){
        return typeOfAttributeRepository.save(typeOfAttribute);
    }
}
