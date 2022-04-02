package com.courseproject.microservice.controller;

import com.courseproject.microservice.model.Attribute;
import com.courseproject.microservice.model.Object;
import com.courseproject.microservice.model.ObjectType;
import com.courseproject.microservice.model.Parameter;
import com.courseproject.microservice.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/objects")
@CrossOrigin
public class ObjectController {
    private static final Logger logger= LoggerFactory.getLogger(ObjectController.class);
    private final ObjectMapper mapper;

    ObjectTypeService objectTypeService;
    ObjectService objectService;
    AttributeService attributeService;
    ParameterService parameterService;

    ObjectController(ObjectService objectService,AttributeService attributeService,
                     ParameterService parameterService, ObjectTypeService objectTypeService,
                     ObjectMapper mapper){
        super();
        this.objectService=objectService;
        this.attributeService = attributeService;
        this.parameterService=parameterService;
        this.objectTypeService=objectTypeService;
        this.mapper=mapper;

    }

    @PostMapping("{id}")
    Object saveObject(@PathVariable Long id,@RequestBody Object object){
        logger.info("Enter in method saveObject()");
        logger.info("Object{}", object);

        ObjectType objectType=objectTypeService.getById(id).get();

        if(objectTypeService.getById(id).isPresent()) {
            List<Object> objects = new ArrayList<>();
            objects.add(object);

            object.setObjectType(objectType);
            objectService.saveObject(object);
            objectType.setObjects(objects);
            objectTypeService.saveObjectType(objectType);

            List<Attribute> attributes = object.getAttributes();

            if (object.getAttributes() != null) {
                for (Attribute attribute : attributes) {
                    attribute.setObjects(objects);
                    attributeService.saveAttribute(attribute);

                    if (attribute.getParameters() != null) {
                        List<Parameter> parameters = attribute.getParameters();
                        attribute.setParameters(parameters);
                        for (Parameter parameter : parameters) {
                            parameter.setAttribute(attribute);
                            parameterService.saveParameter(parameter);
                        }
                        attributeService.saveAttribute(attribute);
                    }
                }
            }
        }
        logger.info("Go out from method saveObject()");

        return object;

    }

    @GetMapping()
    List<Object> getAllObject() throws JsonProcessingException {
        logger.info("Enter in method getAllObject()");
        logger.info("List<Object> {}", mapper.writeValueAsString(objectService.getAllObjects()));
        logger.info("Go out from method getAllObject()");

        return objectService.getAllObjects();
    }

    @GetMapping("{id}")
    Object getObjectById(@PathVariable("id") Long id) throws JsonProcessingException {
        logger.info("Enter in method getObjectById()");
        logger.info("Object {}", mapper.writeValueAsString(objectService.getAllObjects()));

        if(objectService.getObject(id).isPresent()){
            Object object=objectService.getObject(id).get();

            logger.info("Go out from method getObjectById()");

            return object;
        }
        logger.info("Go out from method getObjectById()");

        return null;
    }

    @PutMapping("{ObTid}")
    Object updateObject(@PathVariable("ObTid") Long ObTid,@RequestBody Object object){
        logger.info("Enter in method updateObject()");
        logger.info("Object {}", object);

        if(objectService.getObject(ObTid).isPresent()){
            Object object1=objectService.getObject(ObTid).get();
            object1.setName(object.getName());
            object1.setDescription(object.getDescription());
            if(objectService.getObject(object.getParentId()).isPresent()){
                Object parent=objectService.getObject(object.getParentId()).get();
                object1.setParent_id(parent);
            }
            logger.info("Go out from method updateObject()");

            return objectService.saveObject(object1);
        }
        logger.info("Go out from method updateObject()");

        return null;
    }

    @PutMapping("/createAttributeFor/{id}")
    Object createAttributeForObject(@PathVariable("id") Long id,
                                    @RequestBody List<Attribute> attributes) throws JsonProcessingException {
        logger.info("Enter in method createAttributeForObject()");
        logger.info("Object {}", mapper.writeValueAsString(objectService.getObject(id).get()));
        logger.info("List<Attribute> {}", attributes);


        if(objectService.getObject(id).isPresent()){
            Object object=objectService.getObject(id).get();
            List<Object> objects=new ArrayList<>();
            objects.add(object);

            List<Attribute> attributeList=new ArrayList<>();

            for (Attribute attribute : attributes) {
                attribute.setObjects(objects);
                attributeList.add(attributeService.saveAttribute(attribute));
            }
            object.setAttributes(attributeList);
            logger.info("Go out from method createAttributeForObject()");

            return objectService.saveObject(object);
        }
        logger.info("Go out from method createAttributeForObject()");

        return null;
    }

    @PutMapping("/addAttributeIn/{idOb}")
    Object addAttributeInObject(@PathVariable("idOb") Long idObT,
                                @RequestBody List<Long> attributesId) throws JsonProcessingException {
        logger.info("Enter in method addAttributeInObject()");
        logger.info("Object {}", mapper.writeValueAsString(objectService.getObject(idObT).get()));
        logger.info("List<Long> {}", attributesId);

        if(objectService.getObject(idObT).isPresent()){
            Object object=objectService.getObject(idObT).get();
            for(Long aLong:attributesId){
                if(attributeService.findById(aLong).isPresent()){
                    Attribute attribute=attributeService.findById(aLong).get();
                    List<Object> objects=new ArrayList<>();
                    objects.add(object);
                    List<Attribute> attributes=new ArrayList<>();
                    attributes.add(attribute);

                    attribute.setObjects(objects);
                    attributeService.saveAttribute(attribute);

                    object.setAttributes(attributes);
                    objectService.saveObject(object);
                }
            }
            logger.info("Go out from method addAttributeInObject()");

            return object;
        }
        logger.info("Go out from method addAttributeInObject()");

        return null;
    }

    @DeleteMapping("{id}")
    @CrossOrigin
    void deleteObject(@PathVariable("id") Long id) throws JsonProcessingException {
        logger.info("Enter in method deleteObject()");
        logger.info("Object {}", mapper.writeValueAsString(objectService.getObject(id).get()));

        if(objectService.getObject(id).isPresent()){
            objectService.deleteObject(id);
        }
        logger.info("Go out from method deleteObject()");

    }

    @GetMapping("/getAttributesForObject/{id}")
    List<Attribute> getAttributesForObject(@PathVariable("id") Long id) throws JsonProcessingException {
        logger.info("Enter in method getAttributesForObject()");
        logger.info("Object {}", mapper.writeValueAsString(objectService.getObject(id).get()));

        if(objectService.getObject(id).isPresent()){
            logger.info("Go out from method getAttributesForObject()");

            return objectService.getObject(id).get().getAttributes();
        }
        logger.info("Go out from method getAttributesForObject()");

        return null;
    }



}
