package com.courseproject.microservice.controller;

import com.courseproject.microservice.model.Attribute;
import com.courseproject.microservice.model.TypeOfAttribute;
import com.courseproject.microservice.service.AttributeService;
import com.courseproject.microservice.service.TypeOfAttributeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/typeOfAttribute")
@CrossOrigin
public class TypeOfAttributeController {
    private final ObjectMapper mapper;

    private static final Logger logger= LoggerFactory.getLogger(TypeOfAttributeController.class);

    TypeOfAttributeService typeOfAttributeService;
    AttributeService attributeService;

    public TypeOfAttributeController(ObjectMapper mapper, TypeOfAttributeService typeOfAttributeService, AttributeService attributeService) {
        this.mapper = mapper;
        this.typeOfAttributeService = typeOfAttributeService;
        this.attributeService = attributeService;
    }

    @GetMapping("{id}")
    TypeOfAttribute getParameter(@PathVariable("id") Long id) throws JsonProcessingException {
        logger.info("Enter in method getParameter()");
        logger.info("TypeOfAttribute {}", mapper.writeValueAsString(typeOfAttributeService.findById(id).get()));

        if(typeOfAttributeService.findById(id).isPresent()){
            logger.info("Go out from method getParameter()");

            return typeOfAttributeService.findById(id).get();
        }
        logger.info("Go out from method getParameter()");

        return null;
    }

    @GetMapping
    List<TypeOfAttribute> parameterList() throws JsonProcessingException {
        logger.info("Enter in method parameterList()");
        logger.info("List<TypeOfAttribute> {}", mapper.writeValueAsString(typeOfAttributeService.findAll()));
        logger.info("Go out from method parameterList()");

        return typeOfAttributeService.findAll();
    }

    @PutMapping("{id}")
    TypeOfAttribute updateTypeOfAttribute(@PathVariable("id") Long id,
                                          @RequestBody TypeOfAttribute typeOfAttribute)
            throws JsonProcessingException {
        logger.info("Enter in method updateTypeOfAttribute()");
        logger.info("TypeOfAttribute {}", mapper.writeValueAsString(typeOfAttributeService.findById(id).get()));
        logger.info("TypeOfAttribute {}", typeOfAttribute);

        if (typeOfAttributeService.findById(id).isPresent()) {
            TypeOfAttribute typeOfAttribute1 = typeOfAttributeService.findById(id).get();
            typeOfAttribute1.setName(typeOfAttribute.getName());

            logger.info("Go out from method updateTypeOfAttribute()");

            return typeOfAttributeService.saveTypeOfAttribute(typeOfAttribute1);
        }
        logger.info("Go out from method updateTypeOfAttribute()");

        return null;
    }

    @PutMapping("/addAttributeIn/{id}")
    TypeOfAttribute updateTypeOfAttribute(@PathVariable("id") Long id,
                                          @RequestBody List<Long> attributesId) throws JsonProcessingException {
        logger.info("Enter in method updateTypeOfAttribute()");
        logger.info("TypeOfAttribute {}", mapper.writeValueAsString(typeOfAttributeService.findById(id).get()));
        logger.info("List<Long> {}", attributesId);


        if (typeOfAttributeService.findById(id).isPresent()) {
            TypeOfAttribute typeOfAttribute1 = typeOfAttributeService.findById(id).get();
            for (Long aLong : attributesId) {
                if (attributeService.findById(aLong).isPresent()) {
                    Attribute attribute = attributeService.findById(aLong).get();
                    attribute.setTypeOfAttribute(typeOfAttribute1);
                    attributeService.saveAttribute(attribute);

                    List<Attribute> attributeList = new ArrayList<>();
                    attributeList.add(attribute);

                    typeOfAttribute1.setAttributeList(attributeList);
                    typeOfAttributeService.saveTypeOfAttribute(typeOfAttribute1);

                }
            }
            logger.info("Go out from method updateTypeOfAttribute()");

            return typeOfAttribute1;
        }
        logger.info("Go out from method updateTypeOfAttribute()");

        return null;
    }
}