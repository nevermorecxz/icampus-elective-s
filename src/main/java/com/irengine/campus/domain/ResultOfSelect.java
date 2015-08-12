package com.irengine.campus.domain;

import java.io.Serializable;

import javax.persistence.Table;

import org.springframework.web.bind.annotation.RestController;

/**
 * 选课结果
 * @author wujing
 * */

@RestController
@Table(name = "ele_select_result")
public class ResultOfSelect extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -7571880898998965337L;

	
}
