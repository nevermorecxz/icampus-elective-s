package com.irengine.campus.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 人物基础信息
 * @author huang
 *
 */
@Entity
@Table(name="ele_users")
public class UserBaseInfo extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 5470913330297528743L;
	
	@Column(nullable=false,length=50)
	private String name;
	
	@Column(length=10)
	private String gender;

	@JsonIgnore
	@Column(nullable=false,unique=true,length=100)
	private String username;
	
	@JsonIgnore
	@Column(nullable=false,length=100)
	private String password;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="authority_id")
	private Authority authority;
	
	public String getReturnAuthority(){
		return authority.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	
}
