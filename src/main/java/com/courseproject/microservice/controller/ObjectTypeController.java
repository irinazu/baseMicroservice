package com.courseproject.microservice.controller;

import com.courseproject.microservice.model.Attribute;
import com.courseproject.microservice.model.Object;
import com.courseproject.microservice.model.ObjectType;
import com.courseproject.microservice.model.Parameter;
import com.courseproject.microservice.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/types")
@CrossOrigin
public class ObjectTypeController {
    ObjectTypeService objectTypeService;
    AttributeService attributeService;
    ParameterService parameterService;

    ObjectTypeController(ObjectTypeService objectTypeService,AttributeService attributeService,
                         ParameterService parameterService){
        super();
        this.objectTypeService=objectTypeService;
        this.attributeService=attributeService;
        this.parameterService=parameterService;

    }

    @PostMapping
    ObjectType saveObject(@RequestBody ObjectType object){
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
        return object;
    }

    @GetMapping
    List<ObjectType> getAllObjectType(){
        return objectTypeService.getAllObjectType();
    }

    @GetMapping("{id}")
    ObjectType getObjectTypeById(@PathVariable("id") Long id){
        return objectTypeService.getById(id).get();
    }

    @PutMapping("{id}")
    ObjectType updateObjectType(@PathVariable("id") Long id,
                                @RequestBody ObjectType objectTypeChange){
        if(objectTypeService.getById(id).isPresent()){
            ObjectType objectType=objectTypeService.getById(id).get();
            objectType.setName(objectTypeChange.getName());
            objectType.setDescription(objectTypeChange.getDescription());
            if(objectTypeService.getById(objectTypeChange.getParentId()).isPresent()){
                ObjectType parent=objectTypeService.getById(objectTypeChange.getParentId()).get();
                objectType.setParent_id(parent);
            }
            return objectTypeService.saveObjectType(objectType);
        }
        return null;
    }

    @PutMapping("/createAttributeFor/{idObT}")
    ObjectType createAttributeForObjectType(@PathVariable("idObT") Long id,
                                            @RequestBody List<Attribute> attributes){
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
            return objectTypeService.saveObjectType(objectType);
        }
        return null;
    }

    @PutMapping("/addAttributeIn/{idObT}")
    ObjectType addAttributeInObjectType(@PathVariable("idObT") Long idObT,
                                        @RequestBody List<Long> attributesId) {

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
            return objectType;
        }
        return null;
    }
}
