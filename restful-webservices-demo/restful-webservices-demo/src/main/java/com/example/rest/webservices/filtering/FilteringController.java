package com.example.rest.webservices.filtering;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/filtering")
public class FilteringController {

	// field1, field2
	@GetMapping("/some-bean")
	public MappingJacksonValue retriveSomeBean() {
		SomeBean someBean = new SomeBean("value1", "value1", "value1");
		return applyDynamicFilters(someBean,"field1","field2");
	}

	// field2, field3
	@GetMapping("/some-bean/list")
	public MappingJacksonValue retriveListOfSomeBean() {
		List<SomeBean> list = new ArrayList<>();
		list.add(new SomeBean("value1", "value1", "value1"));
		list.add(new SomeBean("value2", "value2", "value2"));
		return applyDynamicFilters(list,"field1","field3");
	}

	private MappingJacksonValue applyDynamicFilters(Object value, String ... str ) {
		
		// step 3: We will filter out everything except field1 and field2.(that's the
		// attributes that we only want to send; and add this filter to filter provider.
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(str);

		// step 2: Create a filter and we have to put an instance of filter in the
		// addFilter()
		FilterProvider filters = new SimpleFilterProvider().addFilter("someBeanFilter", filter);

		// step 1: create a MappingJacksonValue object and configure a filter.
		MappingJacksonValue mapping = new MappingJacksonValue(value);
		mapping.setFilters(filters);
		
		return mapping;
	}
}
