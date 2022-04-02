package com.courseproject.microservice.repository;

import com.courseproject.microservice.model.TypeOfAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TypeOfAttributeRepository extends JpaRepository<TypeOfAttribute,Long> {
    @Override
    Optional<TypeOfAttribute> findById(Long aLong);

    @Override
    List<TypeOfAttribute> findAll();
}
