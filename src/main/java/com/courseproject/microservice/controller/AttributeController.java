package com.courseproject.microservice.controller;

import com.courseproject.microservice.model.Attribute;
import com.courseproject.microservice.model.Parameter;
import com.courseproject.microservice.service.AttributeService;
import com.courseproject.microservice.service.ParameterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/attributes")
@CrossOrigin
public class AttributeController{
    private final ObjectMapper mapper;

    private static final Logger logger= LoggerFactory.getLogger(AttributeController.class);

    AttributeService attributeService;
    ParameterService parameterService;

    public AttributeController(ObjectMapper mapper, AttributeService attributeService, ParameterService parameterService) {
        this.mapper = mapper;
        this.attributeService = attributeService;
        this.parameterService = parameterService;
    }

    @GetMapping("{id}")
    Attribute getAttribute(@PathVariable("id") Long id) throws JsonProcessingException {
        logger.info("Enter in method getAttribute()");
        logger.info("Attribute {}", mapper.writeValueAsString(attributeService.findById(id).get()));

        if(attributeService.findById(id).isPresent()){
            logger.info("Go out from method getAttribute()");

            return attributeService.findById(id).get();
        }
        logger.info("Go out from method getAttribute()");

        return null;
    }

    @GetMapping
    List<Attribute> attributeList() throws JsonProcessingException {
        logger.info("Enter in method attributeList()");
        logger.info("List<Attribute> {}", mapper.writeValueAsString(attributeService.getAllAttributes()));
        logger.info("Go out from method attributeList()");

        return attributeService.getAllAttributes();
    }

    @PutMapping("{id}")
    Attribute updateAttribute(@PathVariable("id") Long id,
                              @RequestBody Attribute attributeChance) throws JsonProcessingException {
        logger.info("Enter in method updateAttribute()");
        logger.info("Attribute {}", mapper.writeValueAsString(attributeService.findById(id).get()));
        logger.info("Update data {}", attributeChance);

        if(attributeService.findById(id).isPresent()){
            Attribute attribute=attributeService.findById(id).get();
            attribute.setName(attributeChance.getName());
            attribute.setTypeOfAttribute(attribute.getTypeOfAttribute());

            logger.info("Go out from method updateAttribute()");

            return attributeService.saveAttribute(attribute);
        }
        logger.info("Go out from method updateAttribute()");

        return null;
    }

    @PutMapping("/addParameter/{idAt}")
    Attribute createNewParametersForAttribute(@PathVariable("idAt") Long id,
                                              @RequestBody List<Parameter> parameters) throws JsonProcessingException {

        logger.info("Enter in method createNewParametersForAttribute()");
        logger.info("Attribute {}",mapper.writeValueAsString(attributeService.findById(id).get()));
        logger.info("Parameters {}",parameters);

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

            logger.info("Go out from method createNewParametersForAttribute()");

            return attributeService.saveAttribute(attribute);
        }

        logger.info("Go out from method createNewParametersForAttribute()");

        return null;
    }

    @PutMapping("/addParameterIn/{idAt}")
    Attribute addParameterInAttribute(@PathVariable("idAt") Long idAt,
                                      @RequestBody List<Long> parametersId) throws JsonProcessingException {

        logger.info("Enter in method addParameterInAttribute()");
        logger.info("Attribute {}",mapper.writeValueAsString(attributeService.findById(idAt).get()));
        logger.info("List<Long> {}", parametersId);

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
            logger.info("Go out from method addParameterInAttribute()");

            return attribute;
        }
        logger.info("Go out from method addParameterInAttribute()");

        return null;
    }
    @GetMapping("/getParametersForAttribute/{id}")
    List<Parameter> getParametersForAttribute(@PathVariable("id") Long id) throws JsonProcessingException {
        logger.info("Enter in method getParametersForAttribute()");
        logger.info("Attribute {}",mapper.writeValueAsString(attributeService.findById(id).get()));
        logger.info("List<Parameter> {}", mapper.writeValueAsString(attributeService.findById(id).get().getParameters()));

        if(attributeService.findById(id).isPresent()){
            logger.info("Go out from method getParametersForAttribute()");

            return attributeService.findById(id).get().getParameters();
        }
        logger.info("Go out from method getParametersForAttribute()");

        return null;
    }
}
