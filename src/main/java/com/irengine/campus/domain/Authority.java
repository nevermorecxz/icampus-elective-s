package com.irengine.campus.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ele_authority")
public class Authority extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -6672370611961694217L;
	
	@Column(nullable=false,unique=true,length=50)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
