package com.irengine.campus.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.irengine.campus.domain.Course;
import com.irengine.campus.domain.NaturalClass;
import com.irengine.campus.domain.Preferences;
import com.irengine.campus.domain.Student;
import com.irengine.campus.domain.util.DateProvider;
import com.irengine.campus.service.CourseService;
import com.irengine.campus.service.NaturalClassService;
import com.irengine.campus.service.PreferenceService;
import com.irengine.campus.service.StudentService;

@RestController
@RequestMapping("/preferences")
public class PreferencesController {

	private static Logger logger = LoggerFactory
			.getLogger(PreferencesController.class);
	@Autowired
	private PreferenceService preferenceService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private NaturalClassService naturalClassService;

	/**
	 * 新建选课
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> create(@RequestParam("name") String name,
			@RequestParam("startDate") String startDateString,
			@RequestParam("endDate") String endDateString,
			@RequestParam("courseIds") List<Long> courseIds,
			@RequestParam("num") String num, @RequestParam("info") String info,
			@RequestParam("th") Integer th) {
		logger.debug("调用新的选课接口");
		/* 处理日期 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date startDate;
		Date endDate;
		try {
			startDate = sdf.parse(startDateString);
			endDate = sdf.parse(endDateString);
		} catch (ParseException e) {
			e.getStackTrace();
			return new ResponseEntity<>("日期转换错误", HttpStatus.OK);
		}
		/* 记录可选课程集合 */
		List<Course> courses = new ArrayList<Course>();
		for (Long courseId : courseIds) {
			Course course = courseService.findOneById(courseId);
			if (course != null) {
				courses.add(course);
			}
		}
		/* 保存此处选课 */
		Preferences preferences = new Preferences(name,
				DateProvider.DEFAULT.getDate(), startDate, endDate, courses,
				num, info, th);
		preferences = preferenceService.save(preferences);
		return new ResponseEntity<>(preferences, HttpStatus.OK);
	}

	/**
	 * 为该次选课设置能选课的学生
	 * 
	 */
	@RequestMapping(value = "{id}/students", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> setStudent(@PathVariable("id") Long id,
			@RequestParam("studentIds") List<Long> studentIds,
			@RequestParam("nClassIds") List<Long> nClassIds) {
		Preferences preferences = preferenceService.findOneById(id);
		/* 额外的参与学生 */
		if (studentIds != null && studentIds.size() > 0) {
			List<Student> students = new ArrayList<Student>();
			for (Long studentId : studentIds) {
				Student student = studentService.findOneById(studentId);
				students.add(student);
			}
			preferences.setStudents(students);
		}
		/* 参与班级 */
		if (nClassIds != null && nClassIds.size() > 0) {
			List<NaturalClass> nClasses = new ArrayList<NaturalClass>();
			for (Long nClassId : nClassIds) {
				NaturalClass nClass = naturalClassService.findOneById(nClassId);
				// why?
				if (nClass != null) {
					nClasses.add(nClass);
				}
			}
			preferences.setClasses(nClasses);
		}
		preferences = preferenceService.save(preferences);
		return new ResponseEntity<>(preferences, HttpStatus.OK);
	}
}
