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

    ObjectTypeController(ObjectTypeService objectTypeService,AttributeService attributeService,ParameterService parameterService){
        super();
        this.objectTypeService=objectTypeService;
        this.attributeService=attributeService;
        this.parameterService=parameterService;

    }

    @PostMapping
    ObjectType saveObject(@RequestBody ObjectType object){
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
    ObjectType updateObjectType(@PathVariable("id") Long id,@RequestBody ObjectType objectTypeChance){
        ObjectType objectType=objectTypeService.getById(id).get();
        if(objectType!=null){
            objectType.setName(objectTypeChance.getName());
            objectType.setDescription(objectTypeChance.getDescription());
            return objectTypeService.saveObjectType(objectType);
        }
        return null;
    }

    @PutMapping("/addAttribute/{idObT}")
    ObjectType createAttributeForObjectType(@PathVariable("idObT") Long id,@RequestBody List<Attribute> attributes){
        ObjectType objectType=objectTypeService.getById(id).get();
        List<ObjectType> objectTypes=new ArrayList<>();
        objectTypes.add(objectType);

        List<Attribute> attributeList=new ArrayList<>();

        for(int i=0;i<attributes.size();i++){
            attributes.get(i).setObjectTypes(objectTypes);
            attributeList.add(attributeService.saveAttribute(attributes.get(i)));
        }
        objectType.setAttributes(attributeList);
        return objectTypeService.saveObjectType(objectType);
    }

    @PutMapping("/addAttribute/{idObT}/{idAt}")
    ObjectType addAttributeInObjectType(@PathVariable("idObT") Long idObT,
                                             @PathVariable("idAt") Long idAt) {

        if(attributeService.findById(idAt).isPresent()&&objectTypeService.getById(idObT).isPresent()){
            Attribute attribute=attributeService.findById(idAt).get();
            ObjectType objectType=objectTypeService.getById(idObT).get();
            List<ObjectType> objectTypes=new ArrayList<>();
            objectTypes.add(objectType);
            List<Attribute> attributes=new ArrayList<>();
            attributes.add(attribute);

            attribute.setObjectTypes(objectTypes);
            attributeService.saveAttribute(attribute);

            objectType.setAttributes(attributes);
            objectTypeService.saveObjectType(objectType);
            return objectType;
        }
        return null;
    }
    /*@PutMapping("/addParameter/{idAt}")
    Attribute updateObjectTypeAddParameters(@PathVariable("idAt") Long id,
                                             @RequestBody List<Parameter> parameters){

        Attribute attribute=attributeService.findById(id).get();
        List<Attribute> attributes=new ArrayList<>();
        attributes.add(attribute);

        List<Parameter> parameterList=new ArrayList<>();

        for(int i=0;i<parameters.size();i++){
            parameters.get(i).setAttribute(attribute);
            parameterList.add(parameterService.saveParameter(parameters.get(i)));
        }
        attribute.setParameters(parameterList);
        return attributeService.saveAttribute(attribute);
    }*/
}
