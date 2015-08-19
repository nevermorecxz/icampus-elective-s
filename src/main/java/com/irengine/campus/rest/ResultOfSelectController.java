package com.irengine.campus.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.irengine.campus.domain.ResultOfSelect;
import com.irengine.campus.service.NaturalClassService;
import com.irengine.campus.service.PreferenceService;
import com.irengine.campus.service.ResultOfSelectService;
import com.irengine.campus.service.StudentService;

@RestController
@RequestMapping("/sresults")
public class ResultOfSelectController {

	private static Logger logger = LoggerFactory.getLogger(ResultOfSelectController.class);
	
	@Autowired
	private ResultOfSelectService resultOfSelectService;
	
	@Autowired
	private PreferenceService preferenceService;
	
	@Autowired
	private NaturalClassService naturalClassService;
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getResult(){
		List<ResultOfSelect> results =  new ArrayList<ResultOfSelect>();
		results = resultOfSelectService.findAll();
		return new ResponseEntity<>(results, HttpStatus.OK);
	}
}