package com.courseproject.microservice.repository;

//import com.courseproject.microservice.model.Attribute;
import com.courseproject.microservice.model.Object;
//import com.courseproject.microservice.model.Parameter;
import com.courseproject.microservice.model.ObjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface ObjectRepository extends JpaRepository<Object,Long> {
    @Override
    List<Object> findAll();

    @Transactional
    List<Object> findAllByName(String name);

    @Override
    Optional<Object> findById(Long aLong);

    //Object findById(Object object);

    @Transactional
    List<Object> findAllByDescription(String desc);

    @Override
    void deleteById(Long aLong);
}
