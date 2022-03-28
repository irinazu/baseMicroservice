package com.courseproject.microservice.controller;

import com.courseproject.microservice.model.Attribute;
import com.courseproject.microservice.model.Parameter;
import com.courseproject.microservice.service.AttributeService;
import com.courseproject.microservice.service.ParameterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parameters")
@CrossOrigin
public class ParameterController {
    ParameterService parameterService;

    public ParameterController(ParameterService parameterService) {
        super();
        this.parameterService = parameterService;
    }

    @GetMapping("{id}")
    Parameter getParameter(@PathVariable("id") Long id){
        Optional<Parameter> opt=parameterService.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        return null;
    }

    @GetMapping
    List<Parameter> parameterList(){
        return parameterService.getAllParameters();
    }

    @PutMapping("{id}")
    Parameter updateParameter(@PathVariable("id") Long id,@RequestBody Parameter parameterChance){
        Optional<Parameter> opt=parameterService.findById(id);
        if(opt.isPresent()){
            Parameter parameter=opt.get();
            parameter.setName(parameterChance.getName());
            parameter.setValue(parameterChance.getValue());
            parameter.setDate_value(parameterChance.getDate_value());
            return parameterService.saveParameter(parameter);
        }
        return null;
    }
}
