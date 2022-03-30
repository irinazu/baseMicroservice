package com.courseproject.microservice.controller;

import com.courseproject.microservice.model.Attribute;
import com.courseproject.microservice.model.Object;
import com.courseproject.microservice.model.Parameter;
import com.courseproject.microservice.service.AttributeService;
import com.courseproject.microservice.service.ParameterService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attributes")
@CrossOrigin
public class AttributeController{
    AttributeService attributeService;
    ParameterService parameterService;

    public AttributeController(AttributeService attributeService,    ParameterService parameterService) {
        super();
        this.attributeService = attributeService;
        this.parameterService=parameterService;
    }

    @GetMapping("{id}")
    Attribute getAttribute(@PathVariable("id") Long id){
        Optional<Attribute> opt=attributeService.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        return null;
    }

    @GetMapping
    List<Attribute> attributeList(){
        return attributeService.getAllAttributes();
    }

    @PutMapping("{id}")
    Attribute updateAttribute(@PathVariable("id") Long id,@RequestBody Attribute attributeChance){
        Optional<Attribute> opt=attributeService.findById(id);
        if(opt.isPresent()){
            Attribute attribute=opt.get();
            attribute.setName(attributeChance.getName());
            attribute.setTypeOfAttribute(attribute.getTypeOfAttribute());
            return attributeService.saveAttribute(attribute);
        }
        return null;
    }

    @PutMapping("/addParameter/{idAt}")
    Attribute createNewParametersForAttribute(@PathVariable("idAt") Long id,
                                            @RequestBody List<Parameter> parameters){

        if(attributeService.findById(id).isPresent()){
            Attribute attribute=attributeService.findById(id).get();
            List<Attribute> attributes=new ArrayList<>();
            attributes.add(attribute);

            List<Parameter> parameterList=new ArrayList<>();

            for (Parameter parameter : parameters) {
                parameter.setAttribute(attribute);
                parameterList.add(parameterService.saveParameter(parameter));
            }
            attribute.setParameters(parameterList);
            return attributeService.saveAttribute(attribute);
        }
        return null;
    }

    @PutMapping("/addParameterIn/{idAt}")
    Attribute addParameterInAttribute(@PathVariable("idAt") Long idAt,
                                         @RequestBody List<Long> parametersId) {

        if(attributeService.findById(idAt).isPresent()){
            Attribute attribute=attributeService.findById(idAt).get();
            for (Long aLong : parametersId) {
                if (parameterService.findById(aLong).isPresent()) {
                    Parameter parameter = parameterService.findById(aLong).get();
                    List<Parameter> parameters = new ArrayList<>();
                    parameters.add(parameter);
                    List<Attribute> attributes = new ArrayList<>();
                    attributes.add(attribute);

                    attribute.setParameters(parameters);
                    attributeService.saveAttribute(attribute);

                    parameter.setAttribute(attribute);
                    parameterService.saveParameter(parameter);
                }
            }
            return attribute;
        }
        return null;
    }

}
