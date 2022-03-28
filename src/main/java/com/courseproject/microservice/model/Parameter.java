package com.courseproject.microservice.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import javax.persistence.*;


@Entity
@Data
@Table(name = "parameter")
@JsonIdentityInfo(
        scope = Parameter.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String value;
    private String date_value;


    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;
}