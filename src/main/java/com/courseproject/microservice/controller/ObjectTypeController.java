package com.courseproject.microservice.controller;

import com.courseproject.microservice.model.Attribute;
import com.courseproject.microservice.model.Object;
import com.courseproject.microservice.model.ObjectType;
import com.courseproject.microservice.model.Parameter;
import com.courseproject.microservice.service.*;
//import org.apache.logging.log4j.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/types")
@CrossOrigin
public class ObjectTypeController {
    private static final Logger logger= LoggerFactory.getLogger(ObjectTypeController.class);
    private final ObjectMapper mapper;

    ObjectTypeService objectTypeService;
    AttributeService attributeService;
    ParameterService parameterService;

    ObjectTypeController(ObjectTypeService objectTypeService,AttributeService attributeService,
                         ParameterService parameterService,ObjectMapper mapper){
        super();
        this.objectTypeService=objectTypeService;
        this.attributeService=attributeService;
        this.parameterService=parameterService;
        this.mapper=mapper;

    }

    @PostMapping
    ObjectType saveObjectType(@RequestBody ObjectType object){
        logger.info("Enter in method saveObjectType()");
        logger.info("ObjectType {}", object);

        if(object.getParentId()!=null&&objectTypeService.getById(object.getParentId()).isPresent()){
            ObjectType parent=objectTypeService.getById(object.getParentId()).get();
            object.setParent_id(parent);
        }
        objectTypeService.saveObjectType(object);

        if (object.getAttributes()!=null){

            List<Attribute> attributes=object.getAttributes();
            List<ObjectType> objectTypes=new ArrayList<>();
            objectTypes.add(object);

            for (Attribute attribute : attributes){
                attribute.setObjectTypes(objectTypes);
                attributeService.saveAttribute(attribute);

                if(attribute.getParameters()!=null){
                    List<Parameter> parameters=attribute.getParameters();
                    attribute.setParameters(parameters);
                    for (Parameter parameter : parameters){
                        parameter.setAttribute(attribute);
                        parameterService.saveParameter(parameter);
                    }
                    attributeService.saveAttribute(attribute);
                }
            }
        }
        logger.info("Go out from method saveObjectType()");
        return object;
    }

    @GetMapping
    List<ObjectType> getAllObjectType() throws JsonProcessingException {
        logger.info("Enter in method getAllObjectType()");
        logger.info("List<ObjectType> {}", mapper.writeValueAsString(objectTypeService.getAllObjectType()));
        logger.info("Go out from method getAllObjectType()");

        return objectTypeService.getAllObjectType();
    }

    @GetMapping("{id}")
    ObjectType getObjectTypeById(@PathVariable("id") Long id) throws JsonProcessingException {
        logger.info("Enter in method getObjectTypeById()");
        logger.info("objectType {}",mapper.writeValueAsString(objectTypeService.getById(id).get()));

        if(objectTypeService.getById(id).isPresent()){
            logger.info("Go out from method getObjectTypeById()");

            return objectTypeService.getById(id).get();
        }
        logger.info("Go out from method getObjectTypeById()");

        return null;
    }

    @PutMapping("{id}")
    ObjectType updateObjectType(@PathVariable("id") Long id,
                                @RequestBody ObjectType objectTypeChange){
        logger.info("Enter in method updateObjectType()");
        logger.info("ObjectType{}", objectTypeChange);

        if(objectTypeService.getById(id).isPresent()){
            ObjectType objectType=objectTypeService.getById(id).get();
            objectType.setName(objectTypeChange.getName());
            objectType.setDescription(objectTypeChange.getDescription());
            if(objectTypeService.getById(objectTypeChange.getParentId()).isPresent()){
                ObjectType parent=objectTypeService.getById(objectTypeChange.getParentId()).get();
                objectType.setParent_id(parent);
            }
            logger.info("Go out from method getObjectTypeById()");

            return objectTypeService.saveObjectType(objectType);
        }
        logger.info("Go out from method updateObjectType()");

        return null;
    }

    @PutMapping("/createAttributeFor/{idObT}")
    ObjectType createAttributeForObjectType(@PathVariable("idObT") Long id,
                                            @RequestBody List<Attribute> attributes) throws JsonProcessingException {
        logger.info("Enter in method createAttributeForObjectType()");
        logger.info("ObjectType {}",mapper.writeValueAsString(objectTypeService.getById(id).get()));
        logger.info("List<Attribute> {}", attributes);

        if(objectTypeService.getById(id).isPresent()){
            ObjectType objectType=objectTypeService.getById(id).get();
            List<ObjectType> objectTypes=new ArrayList<>();
            objectTypes.add(objectType);

            List<Attribute> attributeList=new ArrayList<>();

            for (Attribute attribute : attributes) {
                attribute.setObjectTypes(objectTypes);
                attributeList.add(attributeService.saveAttribute(attribute));
            }
            objectType.setAttributes(attributeList);

            logger.info("Go out from method createAttributeForObjectType()");

            return objectTypeService.saveObjectType(objectType);
        }
        logger.info("Go out from method createAttributeForObjectType()");

        return null;
    }

    @PutMapping("/addAttributeIn/{idObT}")
    public ObjectType addAttributeInObjectType(@PathVariable("idObT") Long idObT,
                                               @RequestBody List<Long> attributesId)
                                                 throws JsonProcessingException {
        logger.info("Enter in method addAttributeInObjectType()");
        logger.info("ObjectType {}",mapper.writeValueAsString(objectTypeService.getById(idObT).get()));
        logger.info("List<Long> {}", attributesId);

        if(objectTypeService.getById(idObT).isPresent()){
            ObjectType objectType=objectTypeService.getById(idObT).get();
            for (Long aLong:attributesId){
                if(attributeService.findById(aLong).isPresent()){
                    Attribute attribute=attributeService.findById(aLong).get();
                    List<ObjectType> objectTypes=new ArrayList<>();
                    objectTypes.add(objectType);
                    List<Attribute> attributes=new ArrayList<>();
                    attributes.add(attribute);

                    attribute.setObjectTypes(objectTypes);
                    attributeService.saveAttribute(attribute);

                    objectType.setAttributes(attributes);
                    objectTypeService.saveObjectType(objectType);
                }
            }
            logger.info("Go out from method addAttributeInObjectType()");

            return objectType;
        }
        logger.info("Go out from method addAttributeInObjectType()");

        return null;
    }

    @GetMapping("/getObjectsByOT/{id}")
    public List<Object> getObjectsByOT(@PathVariable("id") Long id) throws JsonProcessingException {
        logger.info("Enter in method getObjectsByOT()");
        logger.info("ObjectType {}",mapper.writeValueAsString(objectTypeService.getById(id).get()));
        logger.info("List<Object> {}",mapper.writeValueAsString(objectTypeService.getById(id).get().getObjects()));

        if(objectTypeService.getById(id).isPresent()){
            logger.info("Go out from method getObjectsByOT()");

            return objectTypeService.getById(id).get().getObjects();
        }
        logger.info("Go out from method getObjectsByOT()");

        return null;
    }
}
