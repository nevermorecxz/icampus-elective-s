package com.irengine.campus.web.rest;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {

	@RequestMapping(value="/test1",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public void test1(@RequestBody Map<String,String> map){
		System.out.println(map);
	}
}
