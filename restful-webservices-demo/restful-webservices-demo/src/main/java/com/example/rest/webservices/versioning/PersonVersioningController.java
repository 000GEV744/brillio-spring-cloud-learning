package com.example.rest.webservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

	@GetMapping("/v1/person")
	public PersonV1 personV1() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping("/v2/person")
	public PersonV2 personV2() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	//	..other options using the request params
	@GetMapping(value = "/person/param", params = "version=1")
	public PersonV1 personV1params() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping(value = "/person/param", params = "version=2")
	public PersonV2 personV2params() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
//	..other options using the header params
	@GetMapping(value = "/person/header", headers = "X_API_VERSION=1")
	public PersonV1 personV1header() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping(value = "/person/header", headers = "X_API_VERSION=2")
	public PersonV2 personV2header() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	@GetMapping(value = "/person/produces", produces = "application/vnd.company.add-v1+json")
	public PersonV1 personV1producers() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping(value = "/person/produces", produces = "application/vnd.company.add-v2+json")
	public PersonV2 personV2producers() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
}
