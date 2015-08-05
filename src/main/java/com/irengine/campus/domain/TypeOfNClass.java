package com.irengine.campus.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ele_nclass_type")
public class TypeOfNClass extends BaseEntity  implements Serializable{

	private static final long serialVersionUID = -6377607670528126620L;
	
	@Column(nullable=false,unique=true,length=50)
	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
