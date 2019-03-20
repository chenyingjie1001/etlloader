package com.firesoon.idaservice.appealTemplate;

import java.util.*;

public interface AppealTemplateService {
	Map<String, Object> query();

	Set<String> queryByTerm(Map<String, Object> paramMap);

	Map<String, Object> add(Map<String, Object> paramMap);

	Map<String, Object> update(Map<String, Object> paramMap);

	void delete(Map<String, Object> paramMap);

	Map<String, Object> explainDownload();

	Integer[] importExplainXlsResult(Map<String, Object> params);

	Integer importExplainTimes(Map<String, Object> params);

}
