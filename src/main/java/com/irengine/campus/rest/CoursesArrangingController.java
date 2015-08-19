package com.irengine.campus.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.irengine.campus.domain.ArrangeCourse;
import com.irengine.campus.domain.BaseSyllabus;
import com.irengine.campus.domain.BasicSettings;
import com.irengine.campus.domain.ClassHourModule;
import com.irengine.campus.domain.Course;
import com.irengine.campus.domain.Group;
import com.irengine.campus.domain.NaturalClass;
import com.irengine.campus.domain.SelectCourse;
import com.irengine.campus.domain.StatisticalDataOfSelectCourse;
import com.irengine.campus.domain.StatisticalDataOfSelectCourseAssist;
import com.irengine.campus.domain.Student;
import com.irengine.campus.domain.Teacher;
import com.irengine.campus.service.ArrangeCourseService;
import com.irengine.campus.service.BasicSettingsService;
import com.irengine.campus.service.CourseService;
import com.irengine.campus.service.GroupService;
import com.irengine.campus.service.NaturalClassService;
import com.irengine.campus.service.PreferenceService;
import com.irengine.campus.service.StatisticalDataOfSelectCourseAssistService;
import com.irengine.campus.service.StatisticalDataOfSelectCourseService;
import com.irengine.campus.service.StudentService;
import com.irengine.campus.service.TeacherService;
import com.irengine.campus.web.rest.dto.GroupDTO;
import com.irengine.campus.web.rest.dto.GroupDTO3;
import com.irengine.campus.web.rest.dto.Point;
import com.irengine.campus.web.rest.dto.Scheme;
import com.irengine.campus.web.rest.dto.TeacherAndGroups;
import com.irengine.campus.web.rest.dto.util.GroupDTOUtil;

@RestController
@RequestMapping("/arranging")
public class CoursesArrangingController {

	private static Logger logger = LoggerFactory
			.getLogger(CoursesArrangingController.class);

	@Autowired
	private StudentService studentService;

	@Autowired
	private StatisticalDataOfSelectCourseAssistService statisticalDataOfSelectCourseAssistService;

	@Autowired
	private PreferenceService preferenceService;

	@Autowired
	private StatisticalDataOfSelectCourseService statisticalDataOfSelectCourseService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private BasicSettingsService basicSettingsService;

	@Autowired
	private ArrangeCourseService arrangeCourseService;

	@Autowired
	private NaturalClassService naturalClassService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private TeacherService teacherService;
	
	@RequestMapping(value="/test3",method = RequestMethod.GET)
	public ResponseEntity<?> arranging3(
			//排2016年的课
			@RequestParam("year") Integer year,
			// 0:上学期,1:下学期
			@RequestParam("term") Integer term, 
			//第几届
			@RequestParam("th") Integer th,
			@RequestParam(value = "name", required = false) String name,
			//根据type返回不同结果
			@RequestParam("type") Integer type,
			@RequestParam(value = "scores", required = false) Integer scores1) {
		ArrangeCourse arrangeCourse = new ArrangeCourse(th, year, term, name,
				new Date());
		arrangeCourse = arrangeCourseService.save(arrangeCourse);
		// 读取基础设置参数
		BasicSettings basicSettings = basicSettingsService.findOneByTh(arrangeCourse.getTh());
		/* 统计出每门课等级考课程多少人,会考课程多少人 */
		List<GroupDTO> groupDTOs=new ArrayList<GroupDTO>();
		/* 新建好基础组 */
		List<Course> courses = courseService.findAll();
		for (Course course : courses) {
			GroupDTO groupDTO = new GroupDTO(course, true, 0);
			GroupDTO groupDTO2 = new GroupDTO(course, false, 0);
			groupDTOs.add(groupDTO);
			groupDTOs.add(groupDTO2);
		}
		List<Student> students = studentService.findAll();
		for (Student student : students) {
			/* 查找所有选修课 */
			List<SelectCourse> selectCourses = student.getSelectCourses();
			for (SelectCourse selectCourse : selectCourses) {
				// 得到已学几学期
				int num = (arrangeCourse.getYear() - selectCourse.getYear())
						* 2 + arrangeCourse.getTerm() - selectCourse.getTerm();
				if (selectCourse.isSelected()) {
					/* 判断其等级考是否已经学完 */
					if (num < selectCourse.getCourse().getLevelTerm()) {
						/*该课程等级考统计人数*/
						for(GroupDTO groupDTO:groupDTOs){
							if(groupDTO.getCourse().getId()==selectCourse.getCourse().getId()&&groupDTO.isSelected()==true){
								groupDTO.setNum(groupDTO.getNum()+1);
							}
						}
					}
				} else {
					/* 判断其会考是否已经学完 */
					if (num < selectCourse.getCourse().getUnifiedTerm()) {
						/*该课程会考统计人数*/
						for(GroupDTO groupDTO:groupDTOs){
							if(groupDTO.getCourse().getId()==selectCourse.getCourse().getId()&&groupDTO.isSelected()==false){
								groupDTO.setNum(groupDTO.getNum()+1);
							}
						}
					}
				}
			}
		}
		//删除分组科目人数为0的groupDTO,确定ClassHourModule的size
		List<Long> courseIds=new ArrayList<Long>();
		for(int i=0;i<groupDTOs.size();i++){
			if(groupDTOs.get(i).getNum()==0){
				groupDTOs.remove(i);
				i--;
			}else{
				if(courseIds.indexOf(groupDTOs.get(i).getCourse().getId())==-1){
					courseIds.add(groupDTOs.get(i).getCourse().getId());
				}
			}
		}
		
		List<TeacherAndGroups> teacherAndGroups=new ArrayList<TeacherAndGroups>();
		for(Course course:courses){
			List<Teacher> teachers=teacherService.findAllByThAndCourseId(th, course.getId());
			List<GroupDTO> groupDTOs2=GroupDTOUtil.findAllByCourseId(course.getId(),groupDTOs);
			//添加到老师实体类的groupDTO
			List<GroupDTO> groupDTOs3=new ArrayList<GroupDTO>();
			for(GroupDTO groupDTO:groupDTOs2){
				int totalNum=groupDTO.getNum();
				int numOfClass=basicSettings.getNumOfClass();
				int size=totalNum / numOfClass+ (totalNum % numOfClass <= (numOfClass / 2) ? 0 : 1);
				for(int i=0;i<size;i++){
					GroupDTO groupDTO2=new GroupDTO(course, groupDTO.isSelected(), 0);
					groupDTOs3.add(groupDTO2);
				}
			}
			List<TeacherAndGroups> teacherAndGroups3=new ArrayList<TeacherAndGroups>();
			for(Teacher teacher:teachers){
				TeacherAndGroups teacherAndGroups2=new TeacherAndGroups(teacher, new ArrayList<GroupDTO>());
				teacherAndGroups3.add(teacherAndGroups2);
			}
			//辅助数据
			int a=0;
			for(GroupDTO groupDTO:groupDTOs3){
					teacherAndGroups3.get(a%teacherAndGroups3.size()).getGroupDTOs().add(groupDTO);
					a++;
			}
			teacherAndGroups.addAll(teacherAndGroups3);
		}
		/*自动生成课程表(空)*/
		BaseSyllabus baseSyllabus=new BaseSyllabus();
		for(int i=0;i<courseIds.size();i++){
			ClassHourModule classHourModule=new ClassHourModule();
			baseSyllabus.getClassHourModules().add(classHourModule);
		}
		/*分配课程(组)*/
		/*1:哪里没有物理课
		 * 2:哪里没有同类型的物理课(安排物理等级课,则尽量不到有物理等级课的地方)
		 * 3:找该课时中课程数量最少的课时
		 * */
		for(TeacherAndGroups teacherAndGroups2:teacherAndGroups){
			if(teacherAndGroups2.getGroupDTOs().size()>0){
				for(GroupDTO groupDTO:teacherAndGroups2.getGroupDTOs()){
					Group group=new Group(new ArrayList<Student>(), teacherAndGroups2.getTeacher(), groupDTO.getCourse(), groupDTO.isSelected(), arrangeCourse);
					if(!arrangingStep1(group,baseSyllabus)){
						if(!arrangingStep2(group,baseSyllabus)){
							arrangingStep3(group,baseSyllabus);
						}
					}
				}
			}
		}
		for(Student student:students){
		//Student student=studentService.findOneById(1L); 
			//列
			List<Scheme> schemes=new ArrayList<Scheme>();
			for(int i=0;i<courseIds.size();i++){
				Scheme scheme=new Scheme();
				schemes.add(scheme);
			}
			for(int j=0;j<student.getSelectCourses().size();j++){
				SelectCourse selectCourse=student.getSelectCourses().get(j);
				for(int i=0;i<baseSyllabus.getClassHourModules().size();i++){
					for(Group group:baseSyllabus.getClassHourModules().get(i).getGroups()){
						if(group.getCourse().getId()==selectCourse.getCourse().getId()&&group.isSelected()==selectCourse.isSelected()){
							/*该ClassHourModule中有对应的课程*/
							//评分逻辑
							int scores=0;
							if(student.getnClass().getTeachers().indexOf(group.getTeacher())!=-1){
								scores+=scores1;
							}
							scores-=group.getStudents().size();
//							logger.debug("group.getStudentSize():"+group.getStudentSize());
//							logger.debug("scores"+scores);
							schemes.get(j).getGroupDTO3s().add(new GroupDTO3(i, scores, group));
						}
					}
				}
			}
			List<List<Point>> lists=new ArrayList<List<Point>>();
			for(int i=0;i<courseIds.size();i++){
				for(int j=0;j<schemes.get(i).getGroupDTO3s().size();j++){
					if(lists.size()>0){
						for(int k=0;k<lists.size();k++){
							if(lists.get(k).size()==i){
								boolean j2=true;
								for(Point point:lists.get(k)){
									if(point.getX()==schemes.get(i).getGroupDTO3s().get(j).getClassHourModuleIndex()){
										j2=false;
										break;
									}
								}
								if(j2){
									List<Point> integers=new ArrayList<Point>();
									integers.addAll(lists.get(k));
									integers.add(new Point(schemes.get(i).getGroupDTO3s().get(j).getClassHourModuleIndex(),j));
									lists.add(integers);
								}
							}
						}
					}else{
						for(int k=0;k<schemes.get(i).getGroupDTO3s().size();k++){
							List<Point> integers=new ArrayList<Point>();
							GroupDTO3 groupDTO3=schemes.get(i).getGroupDTO3s().get(k);
							integers.add(new Point(groupDTO3.getClassHourModuleIndex(),k));
							lists.add(integers);
						}
					}
					for(int k=0;k<lists.size();k++){
						 if(lists.get(k).size()<i){
								lists.remove(k);
								k--;
							}
					}
				}
			}
			/*选评分最高的排课方式*/
			int scores=-2000;
			int index=0;
			//List<List<String>> lists2=new ArrayList<List<String>>();
			for(int i=0;i<lists.size();i++){
				List<Point> integers=lists.get(i);
				if(integers.size()==courseIds.size()){
					//该选课的评分
					int scores2=0;
					List<Integer> integers2=new ArrayList<Integer>();
					List<String> strings=new ArrayList<String>();
					for(int j=0;j<integers.size();j++){
						scores2+=schemes.get(j).getGroupDTO3s().get(integers.get(j).getY()).getScores();
						integers2.add(schemes.get(j).getGroupDTO3s().get(integers.get(j).getY()).getScores());
						strings.add("课时"+schemes.get(j).getGroupDTO3s().get(integers.get(j).getY()).getClassHourModuleIndex()+","+schemes.get(j).getGroupDTO3s().get(integers.get(j).getY()).getGroup().getInfo());
					}
//					logger.debug("课程:"+strings);
//					logger.debug("分数:"+integers2.toString());
					if(scores2>scores){
						scores=scores2;
						index=i;
					}
				}
			}
//			logger.debug("选取方案:scores:"+scores);
			if(lists.size()==0){
				logger.debug("有同学排课失败,该同学id:"+student.getId());
			}else{
				for(int i=0;i<lists.get(index).size();i++){
					Point point=lists.get(index).get(i);
					schemes.get(i).getGroupDTO3s().get(point.getY()).getGroup().getStudents().add(student);
				}
			}
		}
		if(type==1){
			//return new ResponseEntity<>(schemes,HttpStatus.OK);
		}
		if(type==2){
			return new ResponseEntity<>(baseSyllabus,HttpStatus.OK);
		}
		//return new ResponseEntity<>(lists,HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/*满足老师不同即可*/
	private void arrangingStep3(Group group,
			BaseSyllabus baseSyllabus) {
		//满足放置条件的课时
		List<ClassHourModule> classHourModules=new ArrayList<ClassHourModule>();
		for(ClassHourModule classHourModule:baseSyllabus.getClassHourModules()){
			boolean j2=true;
			for(Group group2:classHourModule.getGroups()){
				//一个老师的课程不能重复出现在一个课时中
				if(group.getTeacher().getId()==group2.getTeacher().getId()){
					j2=false;
					break;
				}
			}
			//可添加
			if(j2){
				classHourModules.add(classHourModule);
			}
		}
		boolean j =false;
		int size=1000;
		int index=0;
		if(classHourModules.size()>0){
			j=true;
			/*选择课程更少的添加*/
			for(int i=0;i<classHourModules.size();i++){
				if(size>classHourModules.get(i).getGroups().size()){
					size=classHourModules.get(i).getGroups().size();
					index=i;
				}
			}
			//classHourModules.get(index).getGroups().add(group);
			/*得到课程数最少的课时区*/
			List<ClassHourModule> classHourModules2=new ArrayList<ClassHourModule>();
			for(ClassHourModule classHourModule:classHourModules){
				if(classHourModule.getGroups().size()==size){
					classHourModules2.add(classHourModule);
				}
			}
			int index2=0;
			int size2=1000;
			if(classHourModules2.size()>1){
				for(int i=0;i<classHourModules2.size();i++){
					ClassHourModule classHourModule=classHourModules2.get(i);
//					for(Group group2:classHourModule.getGroups()){
//						
//					}
					//等级课/会考课总数
					int size3=0;
					for(int j1=0;j1<classHourModule.getGroups().size();j1++){
						if(classHourModule.getGroups().get(j1).isSelected()==group.isSelected()){
							size3++;
						}
					}
					if(size2>size3){
						size2=size3;
						index2=i;
					}
				}
			}
			classHourModules2.get(index2).getGroups().add(group);
		}
		if(!j){
			logger.debug("排课失败,直到group:"+group.getInfo());
		}
	}

	/*所有的地方都有(物理)课了.找到性质不一样的物理课(老师肯定不能一样,最好性质不一样(等级考/会考)),找到多个地方则选人数少的*/
	private boolean arrangingStep2(Group group,
			BaseSyllabus baseSyllabus) {
		//满足放置条件的课时
		List<ClassHourModule> classHourModules=new ArrayList<ClassHourModule>();
		for(ClassHourModule classHourModule:baseSyllabus.getClassHourModules()){
			boolean j2=true;
			for(Group group2:classHourModule.getGroups()){
				//一个老师的课程不能重复出现在一个课时中
				if(group.getTeacher().getId()==group2.getTeacher().getId()){
					j2=false;
					break;
				}
				//存在同类型的课程,(物理等级课)
				if(group.getCourse().getId()==group2.getCourse().getId()&&group.isSelected()==group2.isSelected()){
					j2=false;
					break;
				}
			}
			//可添加
			if(j2){
				classHourModules.add(classHourModule);
			}
		}
		boolean j =false;
		int size=1000;
		int index=0;
		if(classHourModules.size()>0){
			j=true;
			/*选择课程更少的添加*/
			for(int i=0;i<classHourModules.size();i++){
				if(size>classHourModules.get(i).getGroups().size()){
					size=classHourModules.get(i).getGroups().size();
					index=i;
				}
			}
			//classHourModules.get(index).getGroups().add(group);
			/*得到课程数最少的课时区*/
			List<ClassHourModule> classHourModules2=new ArrayList<ClassHourModule>();
			for(ClassHourModule classHourModule:classHourModules){
				if(classHourModule.getGroups().size()==size){
					classHourModules2.add(classHourModule);
				}
			}
			int index2=0;
			int size2=1000;
			if(classHourModules2.size()>1){
				for(int i=0;i<classHourModules2.size();i++){
					ClassHourModule classHourModule=classHourModules2.get(i);
//					for(Group group2:classHourModule.getGroups()){
//						
//					}
					//等级课/会考课总数
					int size3=0;
					for(int j1=0;j1<classHourModule.getGroups().size();j1++){
						if(classHourModule.getGroups().get(j1).isSelected()==group.isSelected()){
							size3++;
						}
					}
					if(size2>size3){
						size2=size3;
						index2=i;
					}
				}
			}
			classHourModules2.get(index2).getGroups().add(group);
		}
		return j;
	}

	/**
	 * 找没有(物理)课的地方
	 * 如果有多个,则放在课程更少的课时
	 * @param group
	 * @param baseSyllabus
	 * @return
	 */
	private boolean arrangingStep1(Group group,
			BaseSyllabus baseSyllabus) {
		//存在多少个没有(物理)课的地方
		List<ClassHourModule> classHourModules=new ArrayList<ClassHourModule>();
		for(ClassHourModule classHourModule:baseSyllabus.getClassHourModules()){
			boolean j2=true;
			for(Group group2:classHourModule.getGroups()){
				//存在相同课程(存在物理课)
				if(group.getCourse().getId()==group2.getCourse().getId()){
					j2=false;
					break;
				}
			}
			//可添加
			if(j2){
				classHourModules.add(classHourModule);
			}
		}
		boolean j =false;
		int size=1000;
		int index=0;
		if(classHourModules.size()>0){
			j=true;
			/*选择课程更少的添加*/
			for(int i=0;i<classHourModules.size();i++){
				if(size>classHourModules.get(i).getGroups().size()){
					size=classHourModules.get(i).getGroups().size();
					index=i;
				}
			}
			//classHourModules.get(index).getGroups().add(group);
			/*得到课程数最少的课时区*/
			List<ClassHourModule> classHourModules2=new ArrayList<ClassHourModule>();
			for(ClassHourModule classHourModule:classHourModules){
				if(classHourModule.getGroups().size()==size){
					classHourModules2.add(classHourModule);
				}
			}
			int index2=0;
			int size2=1000;
			if(classHourModules2.size()>1){
				for(int i=0;i<classHourModules2.size();i++){
					ClassHourModule classHourModule=classHourModules2.get(i);
//					for(Group group2:classHourModule.getGroups()){
//						
//					}
					//等级课/会考课总数
					int size3=0;
					for(int j1=0;j1<classHourModule.getGroups().size();j1++){
						if(classHourModule.getGroups().get(j1).isSelected()==group.isSelected()){
							size3++;
						}
					}
					if(size2>size3){
						size2=size3;
						index2=i;
					}
				}
			}
			classHourModules2.get(index2).getGroups().add(group);
		}
		return j;
	}

//	@RequestMapping(value="/test2",method = RequestMethod.GET)
//	public ResponseEntity<?> arranging2(
//			@RequestParam("year") Integer year,
//			// 0:上学期,1:下学期
//			@RequestParam("term") Integer term, @RequestParam("th") Integer th,
//			@RequestParam(value = "name", required = false) String name,
//			@RequestParam("type") Integer type) {
//		/* 统计出每门课等级考课程多少人,会考课程多少人 */
//		// 等级考组
//		List<Group> levelGroups = new ArrayList<Group>();
//		// 会考组
//		List<Group> unifiedGroups = new ArrayList<Group>();
//		/* 新建好基础组 */
//		List<NaturalClass> naturalClasses = naturalClassService.findAllByTh(th);
//		List<Course> courses = courseService.findAll();
//		for (NaturalClass naturalClass : naturalClasses) {
//			for (Course course : courses) {
//				Group group = new Group(new ArrayList<Student>(),
//						null, course, true);
//				Group group2 = new Group(new ArrayList<Student>(),
//						new ArrayList<Teacher>(), course, naturalClass, false);
//				/* 设置group的老师 */
//				List<Teacher> teachers = naturalClass.getTeachers();
//				for (Teacher teacher : teachers) {
//					if (teacher.getCourse().getId() == course.getId()) {
//						group.getTeachers().add(teacher);
//						group2.getTeachers().add(teacher);
//						break;
//					}
//				}
//				levelGroups.add(group);
//				unifiedGroups.add(group2);
//			}
//		}
//		List<Student> students = studentService.findAll();
//		ArrangeCourse arrangeCourse = new ArrangeCourse(th, year, term, name,
//				new Date());
//		arrangeCourse = arrangeCourseService.save(arrangeCourse);
//		for (Student student : students) {
//			/* 查找所有选修课 */
//			List<SelectCourse> selectCourses = student.getSelectCourses();
//			for (SelectCourse selectCourse : selectCourses) {
//				// 得到已学几学期
//				int num = (arrangeCourse.getYear() - selectCourse.getYear())
//						* 2 + arrangeCourse.getTerm() - selectCourse.getTerm();
//				if (selectCourse.isSelected()) {
//					/* 判断其等级考是否已经学完 */
//					if (num < selectCourse.getCourse().getLevelTerm()) {
//						// 等级考分组
//						levelGroups = arrangingStepOne(student, selectCourse,
//								levelGroups);
//					}
//				} else {
//					/* 判断其会考是否已经学完 */
//					if (num < selectCourse.getCourse().getUnifiedTerm()) {
//						// 会考分组
//						unifiedGroups = arrangingStepOne(student, selectCourse,
//								unifiedGroups);
//					}
//				}
//			}
//		}
//		// 读取基础设置参数
//		BasicSettings basicSettings = basicSettingsService
//				.findOneByTh(arrangeCourse.getTh());
//		/* 分组 */
//		for (Course course : courses) {
//			List<Group> groups = groupService.findAllByCourseFromList(
//					course.getId(), levelGroups);
//			// 当该门科目没有人选时,跳过分组步骤
//			if (groups.size() == 0 || groups.get(0).getStudentSize() == 0) {
//				continue;
//			}
//			List<Group> needSaveGroups=new ArrayList<Group>();
//			needSaveGroups=arrangingStepTwo(basicSettings,groups,course);
//			groupService.save(needSaveGroups);
//		}
//		for (Course course : courses) {
//			List<Group> groups = groupService.findAllByCourseFromList(
//					course.getId(), unifiedGroups);
//			// 当该门科目没有人选时,跳过分组步骤
//			if (groups.size() == 0 || groups.get(0).getStudentSize() == 0) {
//				continue;
//			}
//			List<Group> needSaveGroups=new ArrayList<Group>();
//			needSaveGroups=arrangingStepTwo(basicSettings,groups,course);
//			groupService.save(needSaveGroups);
//		}
//		if (type == 1) {
//			return new ResponseEntity<>(levelGroups, HttpStatus.OK);
//		} else if (type == 2) {
//			return new ResponseEntity<>(unifiedGroups, HttpStatus.OK);
//		} else if (type == 3) {
//
//		}
//		return null;
//	}

//	private List<Group> arrangingStepTwo(BasicSettings basicSettings, List<Group> groups, Course course) {
//		// 得到各个班选(物理)等级考的总人数
//		int totalNum = groupService.getTotalNum(groups);
//		int numOfClass = basicSettings.getNumOfClass();
//		// 决定多少个组和每个组的人数
//		// (物理)等级考有多少个组
//		int size = totalNum / numOfClass
//				+ (totalNum % numOfClass <= (numOfClass / 2) ? 0 : 1);
//		List<Integer> groupSizes = new ArrayList<Integer>();
//		// 余数
//		int remainder = totalNum % size;
//		for (int i = 0; i < size; i++) {
//			int num = totalNum / size;
//			if (remainder > 0) {
//				num++;
//				remainder--;
//			}
//			groupSizes.add(num);
//		}
//		// debug(物理)分组人数情况
//		logger.debug(course.getCourseName() + ":" + groupSizes.toString());
//		// *分组详细步骤:*/
//		/* 检测每个组的人数是否太多(超过groupSizes) */
//		List<Group> groups2 = new ArrayList<Group>();
//		groups2.addAll(groups);
//		for (Group group : groups) {
//			if (group.getStudentSize() > (totalNum / size) + 1) {
//				int num = group.getStudentSize()
//						/ ((totalNum / size) + 1)
//						+ (group.getStudentSize() % ((totalNum / size) + 1) > 0 ? 1
//								: 0);
//				/* 人数太多,需拆分组 */
//				for (int i = num - 1; i > 0; i--) {
//					List<Student> students2 = group.getStudents();
//					// 得到新分组的学生
//					List<Student> students3 = students2.subList(i
//							* ((totalNum / size) + 1), students2.size());
//					List<Student> students4 = new ArrayList<Student>();
//					students4.addAll(students3);
//					students2.removeAll(students3);
//					group.setStudents(students2);
//					// 新建分组
//					Group group2 = new Group(students4,
//							group.getTeacher(), course,
//							group.isSelected());
//					groups2.add(group2);
//				}
//			}
//		}
//		/* 排序 */
//		Collections.sort(groups2);
//		//待分配学生(组多出来的学生)
//		List<Student> assignedStudents=new ArrayList<Student>();
//		for(int i=0;i<groupSizes.size();i++){
//			int num1=groups2.get(i).getStudents().size();
//			int num2=groupSizes.get(i);
//			if(num1==num2){
//				continue;
//			}else if(num1>num2){
//				/*大于预定人数,分配人(减人)*/
//				assignedStudents.addAll(groups2.get(i).getStudents().subList(num2, num1));
//				groups2.get(i).getStudents().removeAll(groups2.get(i).getStudents().subList(num2, num1));
//			}else{
//				/*小于预定人数,分配人(加人)*/
//				if(assignedStudents.size()>(num2-num1)){
//					/*前面累计的待分配同学大于改组需要的人数时,分配需要的人数(num2-num1)*/
//					groups2.get(i).getStudents().addAll(assignedStudents.subList(0, num2-num1));
//					assignedStudents.removeAll(assignedStudents.subList(0, num2-num1));
//				}else{
//					/*前面累计的待分配同学小于或等于改组需要的人数时*/
//					//把所有待分配同学放到该组,并且加上最后一个组的成员(直到成员满了为止)
//					while(assignedStudents.size()<(num2-num1)){
//						assignedStudents.addAll(groups2.get(groups2.size()-1).getStudents());
//						groups2.remove(groups2.size()-1);
//					}
//					groups2.get(i).getStudents().addAll(assignedStudents.subList(0, num2-num1));
//					assignedStudents.removeAll(assignedStudents.subList(0, num2-num1));
//				}
//			}
//		}
//		for(Group group:groups2){
//			logger.debug(group.toString());
//		}
//		return groups2;
//	}

//	private List<Group> arrangingStepOne(Student student,
//			SelectCourse selectCourse, List<Group> groups) {
//		Group group = groupService.findOneByNaturalClassAndCourseFromList(
//				student.getnClass(), selectCourse.getCourse(), groups).get(0);
//		group.getStudents().add(student);
//		return groups;
//	}

	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	public ResponseEntity<?> arranging(@RequestParam("year") Integer year,
			// 0:上学期,1:下学期
			@RequestParam("term") Integer term, @RequestParam("th") Integer th,
			@RequestParam(value = "name", required = false) String name) {
		/* 统计出每门课等级考课程多少人,会考课程多少人 */
		List<Student> students = studentService.findAll();
		List<Course> courses = courseService.findAll();
		List<StatisticalDataOfSelectCourseAssist> statisticalDataOfSelectCourseAssists = new ArrayList<StatisticalDataOfSelectCourseAssist>();
		ArrangeCourse arrangeCourse = new ArrangeCourse(th, year, term, name,
				new Date());
		arrangeCourse = arrangeCourseService.save(arrangeCourse);
		for (Course course : courses) {
			StatisticalDataOfSelectCourseAssist statisticalDataOfSelectCourseAssist = new StatisticalDataOfSelectCourseAssist(
					course, 0, 0);
			statisticalDataOfSelectCourseAssists
					.add(statisticalDataOfSelectCourseAssist);
		}
		for (Student student : students) {
			/* 查找所有选修课 */
			List<SelectCourse> selectCourses = student.getSelectCourses();
			for (SelectCourse selectCourse : selectCourses) {
				for (StatisticalDataOfSelectCourseAssist statisticalDataOfSelectCourseAssist : statisticalDataOfSelectCourseAssists) {
					if (selectCourse.getCourse().getId() == statisticalDataOfSelectCourseAssist
							.getCourse().getId()) {
						// 得到已学几学期
						int num = (arrangeCourse.getYear() - selectCourse
								.getYear())
								* 2
								+ arrangeCourse.getTerm()
								- selectCourse.getTerm();
						if (selectCourse.isSelected()) {
							/* 判断其等级考是否已经学完 */

							if (num < selectCourse.getCourse().getLevelTerm()) {
								// 等级考人数加一
								statisticalDataOfSelectCourseAssist
										.setLevelNum(statisticalDataOfSelectCourseAssist
												.getLevelNum() + 1);
							}
						} else {
							/* 判断其会考是否已经学完 */
							if (num < selectCourse.getCourse().getUnifiedTerm()) {
								// 会考人数加一
								statisticalDataOfSelectCourseAssist
										.setUnifiedNum(statisticalDataOfSelectCourseAssist
												.getUnifiedNum() + 1);
							}

						}
					}
				}
			}
		}
		statisticalDataOfSelectCourseAssistService
				.save(statisticalDataOfSelectCourseAssists);
		StatisticalDataOfSelectCourse statisticalDataOfSelectCourse = new StatisticalDataOfSelectCourse(
				arrangeCourse, statisticalDataOfSelectCourseAssists);
		StatisticalDataOfSelectCourse statisticalDataOfSelectCourse2 = statisticalDataOfSelectCourseService
				.save(statisticalDataOfSelectCourse);
		return new ResponseEntity<>(statisticalDataOfSelectCourse2,
				HttpStatus.OK);
	}
}
