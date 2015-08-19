package com.irengine.campus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.campus.domain.Group;
import com.irengine.campus.repository.GroupDao;

@Service
@Transactional
public class GroupService {

	@Autowired
	private GroupDao groupDao;

	public List<Group> deleteWhereSizeIsZero(List<Group> groups) {
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).getStudents().size() == 0) {
				groups.remove(i);
				i--;
			}
		}
		return groups;
	}

	// /* 设定人数小于某个值时取消该分组 */
	// public List<Group> deleteWhereSizeLessThan(List<Group> groups, Integer
	// num) {
	// for (int i = 0; i < groups.size(); i++) {
	// if (groups.get(i).getStudents().size() < num) {
	// groups.remove(i);
	// i--;
	// }
	// }
	// return groups;
	// }
	public List<Group> findAllByCourseFromList(Long id, List<Group> levelGroups) {
		List<Group> groups = new ArrayList<Group>();
		for (Group group : levelGroups) {
			if (group.getCourse().getId() == id) {
				groups.add(group);
			}
		}
		return groups;
	}

	public int getTotalNum(List<Group> groups) {
		int num = 0;
		for (Group group : groups) {
			num += group.getStudents().size();
		}
		return num;
	}
	
	public List<Group> save(List<Group> needSaveGroups){
		return groupDao.save(needSaveGroups);
	}
}
