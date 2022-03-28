package com.courseproject.microservice.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
/*
@Entity
@Data
@Table(name = "objectType_attribute")
@JsonIdentityInfo(
        scope = ObjectTypeAttribute.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ObjectTypeAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "objectType_id")
    private ObjectType objectType;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

}
*/