package com.irengine.campus.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 排课参数
 * 
 * @author wujing
 */

@Entity
@Table(name = "ele_array_course")
public class ArrangeCourse extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -3439247275650222022L;

	// 届数
	private Integer th;

	// 选课年份
	private Integer year;

	private Integer term;
	/*
	 * 1.加length的作用。2.nullable=false什么时候用
	 */
	@Column(length = 100)
	private String name;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "YYYY-MM-DD HH:MM:SS", timezone = "GMT+08:00")
	private Date createTime;

	public ArrangeCourse() {
		super();
	}

	public ArrangeCourse(Integer th, Integer year, Integer term, String name, Date createTime) {
		super();
		this.th = th;
		this.year = year;
		this.term = term;
		this.name = name;
		this.createTime = createTime;
	}

	public Integer getTh() {
		return th;
	}

	public void setTh(Integer th) {
		this.th = th;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
