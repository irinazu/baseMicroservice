package com.courseproject.microservice.model;
/*
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "object_attribute")
@JsonIdentityInfo(
        scope = ObjectAttribute.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ObjectAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "object_id")
    private Object object;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;


}
*/