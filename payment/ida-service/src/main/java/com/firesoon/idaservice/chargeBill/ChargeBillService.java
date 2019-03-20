package com.firesoon.idaservice.chargeBill;

import java.util.*;

public interface ChargeBillService {
	
	List<Map<String, Object>> query(Map<String, Object> paramMap);

	void delete(Map<String, Object> paramMap);

}
