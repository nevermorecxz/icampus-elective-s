package com.irengine.campus.web.rest.dto.util;

import java.util.ArrayList;
import java.util.List;

import com.irengine.campus.web.rest.dto.GroupDTO;

public class GroupDTOUtil {

	public static List<GroupDTO> findAllByCourseId(Long id, List<GroupDTO> groupDTOs){
		List<GroupDTO> groupDTOs2 = new ArrayList<GroupDTO>();
		for(GroupDTO groupDTO:groupDTOs){
			if(groupDTO.getCourse().getId()==id){
				groupDTOs2.add(groupDTO);
			}
		}
		return groupDTOs2;
	}
}
