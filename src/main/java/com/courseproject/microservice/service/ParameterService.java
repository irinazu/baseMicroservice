package com.courseproject.microservice.service;

import com.courseproject.microservice.model.Attribute;
import com.courseproject.microservice.model.Object;
import com.courseproject.microservice.model.Parameter;

import java.util.List;
import java.util.Optional;


public interface ParameterService {
    Parameter saveParameter(Parameter object);

    List<Parameter> getAllParameters();

    Optional<Parameter> findById(Long id);

    void deleteParameter(Long id);

    void saveAll(List<Parameter> parameters);
}
