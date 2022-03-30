package com.courseproject.microservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "object")
@JsonIdentityInfo(
        scope = Object.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Object {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToOne
    @JoinColumn(name = "parent_object_id")
    //@JsonIgnore
    private Object parent_id=null;

    @ManyToOne
    @JoinColumn(name = "object_type_id")
    //@JsonBackReference

    private ObjectType objectType=null;

    @ManyToMany(mappedBy = "objects")
    List<Attribute> attributes;
    /*@OneToMany(mappedBy = "object")
    List<ObjectAttribute> objectAttributes;*/

    @Transient
    Long parentId;
}
