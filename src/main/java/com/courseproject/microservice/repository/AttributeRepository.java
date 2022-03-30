package com.courseproject.microservice.repository;

import com.courseproject.microservice.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface AttributeRepository extends JpaRepository<Attribute,Long> {
    @Override
    List<Attribute> findAll();

    @Override
    Optional<Attribute> findById(Long aLong);

}
