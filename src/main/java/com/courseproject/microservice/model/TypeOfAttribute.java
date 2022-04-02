package com.courseproject.microservice.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "type_of_attribute")
@JsonIdentityInfo(
        scope = TypeOfAttribute.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class TypeOfAttribute {
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "typeOfAttribute")
    List<Attribute> attributeList;

}
