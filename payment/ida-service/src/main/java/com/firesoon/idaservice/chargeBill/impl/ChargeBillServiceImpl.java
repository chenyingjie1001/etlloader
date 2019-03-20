package com.firesoon.idaservice.chargeBill.impl;

import com.firesoon.idaservice.chargeBill.ChargeBillService;
import com.firesoon.paymentmapper.chargeBill.ChargeBillMapper;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ChargeBillServiceImpl implements ChargeBillService {

	@Autowired
	private ChargeBillMapper chargeBillMapper;

	@Override
	public List<Map<String, Object>> query(Map<String, Object> map) {
		return chargeBillMapper.findChargeBill(map);
	}

	@Override
	public void delete(Map<String, Object> paramMap) {
		chargeBillMapper.deleteChargeBill(paramMap);
	}

}
