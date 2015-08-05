package com.irengine.campus.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.irengine.campus.domain.Course;
import com.irengine.campus.domain.NaturalClass;
import com.irengine.campus.domain.Preferences;
import com.irengine.campus.domain.ResultOfSelect;
import com.irengine.campus.domain.SelectCourse;
import com.irengine.campus.domain.Student;
import com.irengine.campus.service.CourseService;
import com.irengine.campus.service.NaturalClassService;
import com.irengine.campus.service.PreferenceService;
import com.irengine.campus.service.ResultOfSelectService;
import com.irengine.campus.service.StudentService;
import com.irengine.campus.web.rest.dto.CourseDTO;
import com.irengine.campus.web.rest.dto.DataOfResultsDTOByCourses;
import com.irengine.campus.web.rest.dto.ResultsDTOByClass;
import com.irengine.campus.web.rest.dto.ResultsDTOByCourses;
import com.irengine.campus.web.rest.dto.StudentDTO;
import com.irengine.campus.web.rest.util.CreateExcel;

@RestController
@RequestMapping("/sresults")
public class ResultOfSelectController {

	private static Logger logger = LoggerFactory
			.getLogger(ResultOfSelectController.class);

	@Autowired
	private ResultOfSelectService resultOfSelectService;

	@Autowired
	private PreferenceService preferenceService;

	@Autowired
	private NaturalClassService naturalClassService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private CourseService courseService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getResult() {
		List<ResultOfSelect> results = new ArrayList<ResultOfSelect>();
		results = resultOfSelectService.findAll();
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	/**
	 * 查询查询格式json
	 * 
	 * @param preferencesId
	 * @param sType
	 * @param eType
	 * @param nClassId
	 * @return
	 */
	@RequestMapping("/excelInfo")
	public ResponseEntity<?> testExcel(
			@RequestParam("preferencesId") Long preferencesId,
			@RequestParam("sType") String sType,
			@RequestParam("eType") String eType,
			@RequestParam("nClassId") Long nClassId) {
		if (StringUtils.equals("class", eType)) {
			ResultsDTOByClass result = getResultsDTOByClass(preferencesId,
					nClassId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else if (StringUtils.equals("courses", eType)) {
			ResultsDTOByCourses result = getResultsDTOByCourses(preferencesId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else if (StringUtils.equals("course", eType)) {
			ResultsDTOByCourses result = getResultsDTOByCourse(preferencesId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return null;
	}

	/**
	 * 打印预选结果
	 */
	@RequestMapping("/excel")
	public ResponseEntity<?> excel(
			@RequestParam("preferencesId") Long preferencesId,
			// sType:学生类型(按学生类型查询),
			@RequestParam("sType") String sType,
			// 得到什么类型的表格(查询数据)
			@RequestParam("eType") String eType,
			// 班级
			@RequestParam("nClassId") Long nClassId,
			// 科目(查询哪门科目的统计情况)
			@RequestParam(value = "courseId", required = false) Long courseId,
			HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.equals("class", eType)) {
			logger.debug("导出按班级查询学生选课表");
			/* 按班级查询 */
			try {
				createExcelByClass(preferencesId, nClassId, sType, response);
			} catch (IOException e) {
				e.printStackTrace();
				return new ResponseEntity<>("创建表格失败",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else if (StringUtils.equals("courses", eType)) {
			logger.debug("导出组合情况统计表");
			/* 组合情况统计表 */
			try {
				createExcelByCourses(preferencesId, response);
			} catch (IOException e) {
				e.printStackTrace();
				return new ResponseEntity<>("创建表格失败",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else if (StringUtils.equals("course", eType)) {
			logger.debug("导出单项情况统计表");
			/* 按学科统计人数 */
			try {
				createExcelByCourse(preferencesId, response);
			} catch (IOException e) {
				e.printStackTrace();
				return new ResponseEntity<>("创建表格失败",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else if (StringUtils.equals("onecourse", eType)) {
			logger.debug("导出但学科统计表");
			/* 按学科统计人数 */
			try {
				if (courseId == null) {
					return new ResponseEntity<>("请输入您想查询的科目",
							HttpStatus.BAD_REQUEST);
				}
				createExcelByOneCourse(preferencesId, courseId, response);
			} catch (IOException e) {
				e.printStackTrace();
				return new ResponseEntity<>("创建表格失败",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>("不明白您想打印什么类型的表格,请检查参数eType是否输入正确",
				HttpStatus.OK);
	}

	private void createExcelByOneCourse(Long preferencesId, Long courseId,
			HttpServletResponse response) throws IOException {
		ResultsDTOByClass result = getResultsDTOByOneCourse(preferencesId,
				courseId);
		CreateExcel.createExcelByClass(result, response);
	}

	private ResultsDTOByClass getResultsDTOByOneCourse(Long preferencesId,
			Long courseId) {
		Course course=courseService.findOneById(courseId);
		Preferences preferences = preferenceService.findOneById(preferencesId);
		List<Student> students=studentService.findAllByAttendance(preferences.getTh());
		ResultsDTOByClass result = new ResultsDTOByClass();
		result.setTitle(preferences.getName()+"-单科目统计表-"+course.getCourseName());
		result.setTeacherInfo("课程:"+course.getCourseName());
		List<StudentDTO> studentDTOs = new ArrayList<StudentDTO>();
		studentDTOs.add(new StudentDTO("班级", "学号", "姓名", "选课情况"));
		for (Student student : students) {
			/*如果没选该门课,则不算入统计*/
			if(student.getSelectCourses().size()==0){
				continue;
			}
			boolean j=false;
			for(SelectCourse selectCourse:student.getSelectCourses()){
				if(!selectCourse.isSelected()){
					continue;
				}
				if(selectCourse.getCourse().getId()==course.getId()){
					j=true;
					break;
				}
			}
			logger.debug("j:"+j);
			if(!j){
				continue;
			}
			StudentDTO studentDTO = new StudentDTO(student.getnClass()
					.getClassInfo(), student.getStudentNum(), student
					.getBaseInfo().getName(), StringUtils.equals(
					student.getCoursesInfo(), "") ? "未选课"
					: student.getCoursesInfo());
			studentDTOs.add(studentDTO);
		}
		Collections.sort(studentDTOs);
		result.setStudents(studentDTOs);
		return result;
	}

	/**
	 * 单项情况统计表
	 * 
	 * @param preferencesId
	 * @param response
	 * @throws IOException
	 */
	private void createExcelByCourse(Long preferencesId,
			HttpServletResponse response) throws IOException {
		ResultsDTOByCourses result = getResultsDTOByCourse(preferencesId);
		CreateExcel.createExcelByCourse(result, response);
	}

	/**
	 * 得到单项情况统计表的格式json
	 * 
	 * @param preferencesId
	 * @return
	 */
	private ResultsDTOByCourses getResultsDTOByCourse(Long preferencesId) {
		ResultsDTOByCourses result = new ResultsDTOByCourses();
		Preferences preferences = preferenceService.findOneById(preferencesId);
		result.setTitle(preferences.getName() + "----单项情况统计表");
		List<Student> students = studentService.findAllByAttendance(preferences
				.getTh());
		List<Course> courses = courseService.findAll();
		List<String> courseStrs = new ArrayList<String>();
		courseStrs.add("课程");
		for (Course course : courses) {
			courseStrs.add(course.getCourseName());
		}
		// 参与选课的班级
		List<NaturalClass> nClasses = new ArrayList<NaturalClass>();
		for (Student student : students) {
			NaturalClass nClass = student.getnClass();
			if (nClasses.indexOf(nClass) == -1) {
				nClasses.add(nClass);
			}
		}
		/* 排序 */
		Collections.sort(nClasses);
		/* 第一行 */
		DataOfResultsDTOByCourses row1 = new DataOfResultsDTOByCourses();
		row1.setData(courseStrs);
		result.getData().add(row1);
		/* 生成内容 */
		for (NaturalClass nClass : nClasses) {
			DataOfResultsDTOByCourses rownum = new DataOfResultsDTOByCourses();
			rownum.getData().add(nClass.getClassInfo());
			while (rownum.getData().size() < row1.getData().size()) {
				rownum.getData().add("0");
			}
			result.getData().add(rownum);
		}
		/* 最后一行,总计 */
		DataOfResultsDTOByCourses lastRow = new DataOfResultsDTOByCourses();
		lastRow.getData().add("总计");
		while (lastRow.getData().size() < row1.getData().size()) {
			lastRow.getData().add("0");
		}
		result.getData().add(lastRow);
		for (Student student : students) {
			int y = nClasses.indexOf(student.getnClass()) + 1;
			if (student.getSelectCourses().size() == 0) {
				continue;
			}
			for (SelectCourse selectCourse : student.getSelectCourses()) {
				if (selectCourse.isSelected()) {
					continue;
				}
				int x = row1.getData().indexOf(
						selectCourse.getCourse().getCourseName());
				if (x != -1 && y != -1) {
					result.getData()
							.get(y)
							.getData()
							.set(x,
									Long.parseLong(result.getData().get(y)
											.getData().get(x))
											+ 1 + "");
					// 最后一行对应的格子加1
					result.getData()
							.get(result.getData().size() - 1)
							.getData()
							.set(x,
									Long.parseLong(result.getData()
											.get(result.getData().size() - 1)
											.getData().get(x))
											+ 1 + "");
				}
			}
		}
		return result;
	}

	/**
	 * 组合情况统计表(处理数据,得到最适合制表的格式)
	 * 
	 * @param preferencesId
	 * @param response
	 * @throws IOException
	 */
	private void createExcelByCourses(Long preferencesId,
			HttpServletResponse response) throws IOException {
		ResultsDTOByCourses result = getResultsDTOByCourses(preferencesId);
		CreateExcel.createExcelByCourses(result, response);
	}

	/**
	 * 得到ResultsDTOByCourses类型的数据
	 * 
	 * @param preferencesId
	 * @return
	 */
	private ResultsDTOByCourses getResultsDTOByCourses(Long preferencesId) {
		ResultsDTOByCourses result = new ResultsDTOByCourses();
		Preferences preferences = preferenceService.findOneById(preferencesId);
		result.setTitle(preferences.getName() + "----组合情况统计表");
		// 选课组合
		List<CourseDTO> courses = new ArrayList<CourseDTO>();
		// 参与选课班级
		List<NaturalClass> nClasses = new ArrayList<NaturalClass>();
		List<Student> students = studentService.findAllByAttendance(preferences
				.getTh());
		for (Student student : students) {
			String courseIds = student.getCourseIds();
			if (StringUtils.equals(student.getCoursesInfo(), "")) {
				continue;
			}
			String courseNames = student.getCoursesInfo();
			CourseDTO courseDTO = new CourseDTO(courseIds, courseNames);
			if (courses.indexOf(courseDTO) == -1) {
				courses.add(courseDTO);
			}
			NaturalClass nClass = student.getnClass();
			if (nClasses.indexOf(nClass) == -1) {
				nClasses.add(nClass);
			}
		}
		/* 排序 */
		Collections.sort(courses);
		Collections.sort(nClasses);
		// 第一行:标题
		DataOfResultsDTOByCourses row1 = new DataOfResultsDTOByCourses();
		row1.getData().add("选课");
		for (CourseDTO course : courses) {
			row1.getData().add(course.getCourseNames());
		}
		row1.getData().add("总计");
		result.getData().add(row1);
		/* 生成内容 */
		for (NaturalClass nClass : nClasses) {
			DataOfResultsDTOByCourses rownum = new DataOfResultsDTOByCourses();
			rownum.getData().add(nClass.getClassInfo());
			while (rownum.getData().size() < row1.getData().size()) {
				rownum.getData().add("0");
			}
			result.getData().add(rownum);
		}
		/* 最后一行,总计 */
		DataOfResultsDTOByCourses lastRow = new DataOfResultsDTOByCourses();
		lastRow.getData().add("总计");
		while (lastRow.getData().size() < row1.getData().size()) {
			lastRow.getData().add("0");
		}
		result.getData().add(lastRow);
		/* 统计数字 */
		// 记录几个人未选课
		int num = 0;
		for (Student student : students) {
			if (StringUtils.equals(student.getCoursesInfo(), "")) {
				num += 1;
				continue;
			}
			int y = nClasses.indexOf(student.getnClass()) + 1;
			int x = row1.getData().indexOf(student.getCoursesInfo());
			if (x != -1 && y != -1) {
				result.getData()
						.get(y)
						.getData()
						.set(x,
								Long.parseLong(result.getData().get(y)
										.getData().get(x))
										+ 1 + "");
				// 最后一行对应的格子加1
				result.getData()
						.get(result.getData().size() - 1)
						.getData()
						.set(x,
								Long.parseLong(result.getData()
										.get(result.getData().size() - 1)
										.getData().get(x))
										+ 1 + "");
				// 最右边对应的格子加1
				result.getData()
						.get(y)
						.getData()
						.set(row1.getData().size() - 1,
								Long.parseLong(result.getData().get(y)
										.getData()
										.get(row1.getData().size() - 1))
										+ 1 + "");
			}
		}
		result.getData().get(result.getData().size() - 1).getData()
				.set(row1.getData().size() - 1, students.size() - num + "");
		return result;
	}

	/**
	 * 新建按班级查询选课结果表格
	 * 
	 * @param nClassId
	 * @throws IOException
	 */
	private void createExcelByClass(Long preferencesId, Long nClassId,
			String sType, HttpServletResponse response) throws IOException {
		ResultsDTOByClass result = getResultsDTOByClass(preferencesId, nClassId);
		CreateExcel.createExcelByClass(result, response);
	}

	/**
	 * 得到表格传输格式(重写,老版本通过resultOfSelect中的数据统计,新版本方法通过student中的selectCouses统计)
	 */
	private ResultsDTOByClass getResultsDTOByClass(Long preferencesId,
			Long nClassId) {
		NaturalClass nClass = naturalClassService.findOneById(nClassId);
		Preferences preferences = preferenceService.findOneById(preferencesId);
		ResultsDTOByClass result = new ResultsDTOByClass();
		result.setTitle(preferences.getName() + "-" + nClass.getClassInfo());
		result.setTeacherInfo(nClass.getClassInfo() + "班主任:"
				+ nClass.getTeacher().getBaseInfo().getName());
		List<StudentDTO> studentDTOs = new ArrayList<StudentDTO>();
		studentDTOs.add(new StudentDTO("班级", "学号", "姓名", "选课情况"));
		List<Student> students = studentService.findAllByNClassId(nClassId);
		for (Student student : students) {
			StudentDTO studentDTO = new StudentDTO(student.getnClass()
					.getClassInfo(), student.getStudentNum(), student
					.getBaseInfo().getName(), StringUtils.equals(
					student.getCoursesInfo(), "") ? "未选课"
					: student.getCoursesInfo());
			studentDTOs.add(studentDTO);
		}
		Collections.sort(studentDTOs);
		result.setStudents(studentDTOs);
		return result;
	}

}
