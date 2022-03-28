package com.courseproject.microservice.controller;

import com.courseproject.microservice.model.Attribute;
import com.courseproject.microservice.model.Object;
import com.courseproject.microservice.model.ObjectType;
import com.courseproject.microservice.model.Parameter;
import com.courseproject.microservice.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/objects")
@CrossOrigin
public class ObjectController {
    ObjectTypeService objectTypeService;
    ObjectService objectService;
    AttributeService attributeService;
    ParameterService parameterService;

    ObjectController(ObjectService objectService,AttributeService attributeService,
                     ParameterService parameterService, ObjectTypeService objectTypeService){
        super();
        this.objectService=objectService;
        this.attributeService = attributeService;
        this.parameterService=parameterService;
        this.objectTypeService=objectTypeService;

    }

    @PostMapping("{id}")
    @CrossOrigin
    Object saveObject(@PathVariable Long id,@RequestBody Object object){
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
            return object;

    }

    @GetMapping()
    @CrossOrigin
    List<Object> getAllObject(){
        return objectService.getAllObjects();
    }

    @GetMapping("{id}")
    @CrossOrigin
    Object getObjectById(@PathVariable("id") Long id){
        Optional<Object> opt=objectService.getObject(id);
        if(opt.isPresent()){
            Object object=opt.get();
            return object;
        }
        return null;
    }

    @PutMapping("{id}")
    @CrossOrigin
    Object updateObject(@PathVariable("id") Long id,@RequestBody Object object){
        Object object1=objectService.getObject(id).get();
        object1.setName(object.getName());
        object1.setDescription(object.getDescription());
        return objectService.saveObject(object1);
    }

    @PutMapping("/addAttribute/{id}")
    @CrossOrigin
    Object createAttributeFroObject(@PathVariable("id") Long id,@RequestBody List<Attribute> attributes){
        Object object=objectService.getObject(id).get();
        List<Object> objects=new ArrayList<>();
        objects.add(object);

        List<Attribute> attributeList=new ArrayList<>();

        for(int i=0;i<attributes.size();i++){
            attributes.get(i).setObjects(objects);
            attributeList.add(attributeService.saveAttribute(attributes.get(i)));
        }
        object.setAttributes(attributeList);
        return objectService.saveObject(object);
    }

    @PutMapping("/addAttribute/{idOb}/{idAt}")
    Object addAttributeInObject(@PathVariable("idOb") Long idObT,
                                             @PathVariable("idAt") Long idAt) {

        if(attributeService.findById(idAt).isPresent()&&objectService.getObject(idObT).isPresent()){
            Attribute attribute=attributeService.findById(idAt).get();
            Object object=objectService.getObject(idObT).get();
            List<Object> objects=new ArrayList<>();
            objects.add(object);
            List<Attribute> attributes=new ArrayList<>();
            attributes.add(attribute);

            attribute.setObjects(objects);
            attributeService.saveAttribute(attribute);

            object.setAttributes(attributes);
            objectService.saveObject(object);
            return object;
        }
        return null;
    }

    /*@PutMapping("/addParameter/{idAt}")
    @CrossOrigin
    Attribute updateObjectAddParameters(@PathVariable("idAt") Long id,@RequestBody List<Parameter> parameters){
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


    @DeleteMapping("{id}")
    @CrossOrigin
    void deleteObject(@PathVariable("id") Long id){
        objectService.deleteObject(id);
    }
}
