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
@Table(name = "attribute")
@JsonIdentityInfo(
        scope = Attribute.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "attribute")
    //@JsonIgnore
    private List<Parameter> parameters;

    @ManyToMany
    @JoinTable(
            name = "object_attribute",
            joinColumns = @JoinColumn(name = "attribute_id"),
            inverseJoinColumns = @JoinColumn(name = "object_id"))
    List<Object> objects;

/*    @OneToMany(mappedBy = "attribute")
    List<ObjectAttribute> objectAttributes;*/

    @ManyToMany
    @JoinTable(name ="objectTypes_attribute",
               joinColumns = @JoinColumn(name = "attribute_id"),
               inverseJoinColumns = @JoinColumn(name = "objectType_id"))
    List<ObjectType> objectTypes;

    /*@OneToMany(mappedBy = "attribute")
    List<ObjectTypeAttribute> objectTypeAttributes;*/

    @ManyToOne
    @JoinColumn(name = "typeOfAttribute_id")
    TypeOfAttribute typeOfAttribute;
}