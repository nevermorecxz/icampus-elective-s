package com.irengine.campus.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.irengine.campus.domain.Course;
import com.irengine.campus.domain.Preferences;
import com.irengine.campus.domain.SelectCourse;
import com.irengine.campus.domain.Student;
import com.irengine.campus.domain.UserBaseInfo;
import com.irengine.campus.security.SecurityUtils;
import com.irengine.campus.service.CourseService;
import com.irengine.campus.service.PreferenceService;
import com.irengine.campus.service.ResultOfSelectService;
import com.irengine.campus.service.SelectCourseService;
import com.irengine.campus.service.StudentService;
import com.irengine.campus.service.UserBaseInfoService;

@RestController
@RequestMapping("/students")
public class StudentController {

	private static Logger logger = LoggerFactory
			.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;

	@Autowired
	private UserBaseInfoService userBaseInfoService;

	@Autowired
	private PreferenceService preferenceService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private ResultOfSelectService resultOfSelectService;

	@Autowired
	private SelectCourseService selectCourseService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getInfo() {
		List<Student> students = new ArrayList<Student>();
		students = studentService.findAll();
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	@RequestMapping(value = "/elective", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> elective(
			@RequestParam("courseIds") List<Long> courseIds) {
		logger.debug("调用选课接口");
		/* 获得该学生信息 */
		UserBaseInfo user = userBaseInfoService.findOneByUsername(SecurityUtils
				.getCurrentUsername());
		if (user == null) {
			logger.debug("未找到该学生信息");
			return new ResponseEntity<>("未找到该学生信息", HttpStatus.NOT_FOUND);
		}
		Student student = studentService.findOneByUserBaseInfoId(user.getId());
		/* 查找现在对应的是哪次选课(那个preferences) */
		logger.debug("student:" + student.getBaseInfo().getName() + ",th:"
				+ student.getnClass().getAttendance());
		Preferences preferences = preferenceService.findOneByCurrentTimeAndTh(
				new Date(), student.getnClass().getAttendance());
		if (preferences == null) {
			return new ResponseEntity<>("不是选课时间", HttpStatus.NOT_FOUND);
		}
		/* 判断该学生是否可以选课(先判断班级,然后个人id) */
		if (preferences.getClasses().indexOf(student.getnClass()) == -1
				&& preferences.getStudents().indexOf(student) == -1) {
			return new ResponseEntity<>("该学生不能参与这次选课", HttpStatus.NOT_FOUND);
		}
		// allowCourses:可选科目
		List<Course> allowCourses = new ArrayList<Course>();
		allowCourses.addAll(preferences.getCourses());
		/* 得到该同学选择的科目 */
		List<Course> selectCourses = new ArrayList<>();
		for (Long courseId : courseIds) {
			Course course = courseService.findOneById(courseId);
			if (course != null) {
				if (allowCourses.indexOf(course) == -1) {
					return new ResponseEntity<>("选课错误", HttpStatus.NOT_FOUND);
				} else {
					selectCourses.add(course);
					allowCourses.remove(allowCourses.indexOf(course));
				}
			} else {
				return new ResponseEntity<>("未知课程", HttpStatus.NOT_FOUND);
			}
		}

		if (numVerification(preferences.getNum(), selectCourses.size())) {
			/* 记录选课结果 */
			// 选课排序后记录
			Collections.sort(selectCourses, new Comparator<Course>() {
				@Override
				public int compare(Course o1, Course o2) {
					return (int) (o1.getId() - o2.getId());
				}
			});
			/* 保存学生选择的课程记录(会考课,等级考课程) */
			/* 保存等级考 */
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);// 得到年
			int month = cal.get(Calendar.MONTH) + 1;
			int term = 0;
			if (month >= 5 && month <= 11) {
				// 下学期
				term = 1;
			} else {
				// 上学期
				term = 0;
			}
			List<SelectCourse> selectCourses2 = new ArrayList<SelectCourse>();
			List<SelectCourse> selectCourses3 = new ArrayList<SelectCourse>();
			selectCourses3.addAll(student.getSelectCourses());
			/* 保存等级考在student属性里 */
			for (Course course : selectCourses) {
				SelectCourse selectCourse = selectCourseService
						.save(new SelectCourse(course, true, year, term,
								preferences));
				selectCourses2.add(selectCourse);
			}
			/* 保存会考 */
			for (Course course : allowCourses) {
				SelectCourse selectCourse = selectCourseService
						.save(new SelectCourse(course, false, year, term,
								preferences));
				selectCourses2.add(selectCourse);
			}
			student.setSelectCourses(selectCourses2);
			student = studentService.save(student);
			if(selectCourses3.size()>0){
				selectCourseService.deleteAll(selectCourses3);
			}
			// ResultOfSelect result = new ResultOfSelect(preferences,
			// student,selectCourses);
			// result = resultOfSelectService.saveOrUpdate(result);
			return new ResponseEntity<>(student, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("选课数量错误", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 判断选课数目是否符合preferences中的设置
	 * 
	 * @param num
	 * @param size
	 * @return
	 */
	private boolean numVerification(String num, int size) {
		String[] strs = num.split(",");
		int num1 = Integer.parseInt(strs[1]);
		if ("<".equals(strs[0])) {
			return size > num1 ? false : true;
		} else if (">".equals(strs[1])) {
			return size < num1 ? false : true;
		} else {
			return size == num1 ? true : false;
		}
	}

}
