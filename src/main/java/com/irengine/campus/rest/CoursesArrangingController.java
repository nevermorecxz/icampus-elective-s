package com.irengine.campus.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.irengine.campus.domain.Student;
import com.irengine.campus.service.StudentService;

@RestController
@RequestMapping("/arranging")
public class CoursesArrangingController {

	@Autowired
	private StudentService studentService;
	
	@RequestMapping(method=RequestMethod.GET)
	public void arranging(){
		/*统计出每门课等级考课程多少人,会考课程多少人*/
		List<Student> students=studentService.findAll();
		for(Student student:students){
			/*查找所有选修课*/
		}
		
	}
}
