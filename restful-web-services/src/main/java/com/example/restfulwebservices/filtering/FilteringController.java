package com.example.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public MappingJacksonValue retrieve() {
        SomeBean someBean = new SomeBean("val1","val2","val3");
        String[] args = {"field1","field2"};
        return getMappingJacksonValue(someBean, args);
    }

    private MappingJacksonValue getMappingJacksonValue(Object someBean, String[] args) {
        SimpleBeanPropertyFilter
            filter = SimpleBeanPropertyFilter.filterOutAllExcept(args);
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue retrieveFiltered() {
        List<SomeBean> list = Arrays.asList(new SomeBean("val1","val2","val3"),new SomeBean("v1","v2","v3"));
        String[] args = {"field1","field3"};
        return getMappingJacksonValue(list, args);
    }
}
