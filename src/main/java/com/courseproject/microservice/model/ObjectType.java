package com.courseproject.microservice.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "object_type")
@JsonIdentityInfo(
        scope = ObjectType.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ObjectType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToOne
    @JoinColumn(name = "parent_object_type_id")
    private ObjectType parent_id;

    @OneToMany(mappedBy = "objectType")
    //@JsonManagedReference
    //@JsonIgnore
    private List<Object> objects=null;

    @ManyToMany(mappedBy = "objectTypes")
    List<Attribute> attributes;

    /*@OneToMany(mappedBy = "objectType")
    List<ObjectTypeAttribute> objectTypeAttributes;*/
}

