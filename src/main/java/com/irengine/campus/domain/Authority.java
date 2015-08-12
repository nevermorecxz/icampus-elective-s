package com.irengine.campus.domain;

/**
 * 权限
 * @author wujing
 * */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import org.springframework.web.bind.annotation.RestController;

@RestController
@Table(name = "ele_authority")
public class Authority extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6929857710638196792L;

	@Column(nullable = false, unique = true, length = 50)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
