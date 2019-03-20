package com.firesoon.idaservice.importRecord.impl;

import com.firesoon.idaservice.importRecord.ImportRecordService;
import com.firesoon.paymentmapper.importRecord.ImportRecordMapper;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ImportRecordServiceImpl implements ImportRecordService {
	@Autowired
	private ImportRecordMapper importRecordMapper;

	@Override
	public List<Map<String, Object>> findImportRecord(Map<String, Object> paramMap) {
		return importRecordMapper.findImportRecord(paramMap);
	}

	@Override
	public List<Map<String, Object>> findAllAddress(Map<String, Object> paramMap) {
		return importRecordMapper.findAllAddress(paramMap);
	}

	@Override
	public void delete(Map<String, Object> paramMap) {
		importRecordMapper.deleteImportRecord(paramMap);
	}

}