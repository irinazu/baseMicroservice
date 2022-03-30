package com.courseproject.microservice.repository;

import com.courseproject.microservice.model.Attribute;
import com.courseproject.microservice.model.Object;
import com.courseproject.microservice.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ParameterRepository extends JpaRepository<Parameter,Long> {
    @Override
    List<Parameter> findAll();

    @Override
    Optional<Parameter> findById(Long aLong);

    @Override
    void deleteById(Long aLong);

    @Override
    <S extends Parameter> List<S> saveAll(Iterable<S> entities);
}
