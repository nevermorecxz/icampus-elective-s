package com.irengine.campus.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import org.springframework.web.bind.annotation.RestController;

/**
 * 班级类型
 * @author wujing
 * */

@RestController
@Table(name = "ele_nclassType")
public class TypeOfNClass extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -561725671205654149L;

	@Column(nullable = false,unique = true,length = 50)
	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
