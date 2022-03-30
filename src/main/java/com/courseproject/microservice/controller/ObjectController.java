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
    List<Object> getAllObject(){
        return objectService.getAllObjects();
    }

    @GetMapping("{id}")
    Object getObjectById(@PathVariable("id") Long id){
        Optional<Object> opt=objectService.getObject(id);
        if(opt.isPresent()){
            Object object=opt.get();
            return object;
        }
        return null;
    }

    @PutMapping("{ObTid}")
    Object updateObject(@PathVariable("ObTid") Long ObTid,@RequestBody Object object){
        if(objectService.getObject(ObTid).isPresent()){
            Object object1=objectService.getObject(ObTid).get();
            object1.setName(object.getName());
            object1.setDescription(object.getDescription());
            if(objectService.getObject(object.getParentId()).isPresent()){
                Object parent=objectService.getObject(object.getParentId()).get();
                object1.setParent_id(parent);
            }
            return objectService.saveObject(object1);
        }
        return null;
    }

    @PutMapping("/createAttributeFor/{id}")
    Object createAttributeForObject(@PathVariable("id") Long id,
                                    @RequestBody List<Attribute> attributes){
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
            return objectService.saveObject(object);
        }
        return null;
    }

    @PutMapping("/addAttributeIn/{idOb}")
    Object addAttributeInObject(@PathVariable("idOb") Long idObT,
                                @RequestBody List<Long> attributesId) {

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
            return object;
        }
        return null;
    }

    @DeleteMapping("{id}")
    @CrossOrigin
    void deleteObject(@PathVariable("id") Long id){
        objectService.deleteObject(id);
    }
}
