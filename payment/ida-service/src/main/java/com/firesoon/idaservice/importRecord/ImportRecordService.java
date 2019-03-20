package com.firesoon.idaservice.importRecord;

import java.util.*;

public interface ImportRecordService {

	List<Map<String, Object>> findImportRecord(Map<String, Object> paramMap);

	List<Map<String, Object>> findAllAddress(Map<String, Object> paramMap);

	void delete(Map<String, Object> paramMap);
}
