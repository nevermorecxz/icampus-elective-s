package com.irengine.campus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.campus.domain.ResultOfSelect;
import com.irengine.campus.repository.ResultOfSelectDao;

@Service
@Transactional
public class ResultOfSelectService {

	@Autowired
	private ResultOfSelectDao resultOfSelectDao;

	public ResultOfSelect save(ResultOfSelect result) {
		return resultOfSelectDao.save(result);
	}

	public List<ResultOfSelect> findAll() {
		return resultOfSelectDao.findAll(new Sort(Direction.DESC, "id"));
	}

	public ResultOfSelect saveOrUpdate(ResultOfSelect result) {
		ResultOfSelect result1 = resultOfSelectDao
				.findOneByPreferencesIdAndStudentId(result.getPreferences()
						.getId(), result.getStudent().getId());
		if (result1 != null) {
			result.setId(result1.getId());
		}
		result = resultOfSelectDao.save(result);
		return result;
	}

	public List<ResultOfSelect> findAllByPreferencesIdAndnClassId(
			Long preferencesId, Long nClassId) {
		return resultOfSelectDao.findAllByPreferencesIdAndnClassId(
				preferencesId, nClassId);
	}

	public List<ResultOfSelect> findAllByPreferencesId(Long preferencesId) {
		return resultOfSelectDao.findAllByPreferencesId(preferencesId);
	}

	public ResultOfSelect findOneByPreferencesIdAndStudentId(Long id, Long id2) {
		return resultOfSelectDao.findOneByPreferencesIdAndStudentId(id, id2);
	}

}
