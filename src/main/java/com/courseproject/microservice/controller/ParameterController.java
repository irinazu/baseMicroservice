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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parameters")
@CrossOrigin
public class ParameterController {
    private static final Logger logger= LoggerFactory.getLogger(ParameterController.class);
    private final ObjectMapper mapper;

    ParameterService parameterService;

    public ParameterController(ObjectMapper mapper, ParameterService parameterService) {
        this.mapper = mapper;
        this.parameterService = parameterService;
    }

    @GetMapping("{id}")
    Parameter getParameter(@PathVariable("id") Long id) throws JsonProcessingException {
        logger.info("Enter in method getParameter()");
        logger.info("Parameter {}", mapper.writeValueAsString(parameterService.findById(id).get()));
        if(parameterService.findById(id).isPresent()){
            logger.info("Go out from method getParameter()");

            return parameterService.findById(id).get();
        }
        logger.info("Go out from method getParameter()");

        return null;
    }

    @GetMapping
    List<Parameter> parameterList() throws JsonProcessingException {
        logger.info("Enter in method parameterList()");
        logger.info("List<Parameter> {}",mapper.writeValueAsString(parameterService.getAllParameters()));
        logger.info("Go out from method parameterList()");

        return parameterService.getAllParameters();
    }

    @PutMapping("{id}")
    Parameter updateParameter(@PathVariable("id") Long id,@RequestBody Parameter parameterChance)
            throws JsonProcessingException {

        logger.info("Enter in method updateParameter()");
        logger.info("Parameter {}", mapper.writeValueAsString(parameterService.findById(id).get()));
        logger.info("Update data  {}", parameterChance);

        if(parameterService.findById(id).isPresent()){
            Parameter parameter=parameterService.findById(id).get();
            parameter.setName(parameterChance.getName());
            parameter.setValue(parameterChance.getValue());
            parameter.setDate_value(parameterChance.getDate_value());
            logger.info("Go out from method updateParameter()");

            return parameterService.saveParameter(parameter);
        }
        logger.info("Go out from method updateParameter()");

        return null;
    }
}
