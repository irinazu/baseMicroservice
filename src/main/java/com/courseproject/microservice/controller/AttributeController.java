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
            return attributeService.saveAttribute(attribute);
        }
        return null;
    }

    @PutMapping("/addParameter/{idAt}")
    Attribute createParametersFroAttribute(@PathVariable("idAt") Long id,
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
    }

    @PutMapping("/addAttribute/{idAt}/{idPar}")
    Attribute addParameterInAttribute(@PathVariable("idAt") Long idAt,
                                         @PathVariable("idPar") Long idPar) {

        if(attributeService.findById(idAt).isPresent()&&parameterService.findById(idPar).isPresent()){
            Attribute attribute=attributeService.findById(idAt).get();
            Parameter parameter=parameterService.findById(idPar).get();
            List<Parameter> parameters=new ArrayList<>();
            parameters.add(parameter);
            List<Attribute> attributes=new ArrayList<>();
            attributes.add(attribute);

            attribute.setParameters(parameters);
            attributeService.saveAttribute(attribute);

            parameter.setAttribute(attribute);
            parameterService.saveParameter(parameter);
            return attribute;
        }
        return null;
    }

}
