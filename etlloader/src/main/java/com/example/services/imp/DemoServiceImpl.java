package com.example.services.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.Area;
import com.example.mapper.DemoMapper;
import com.example.services.DemoService;

@Service
public class DemoServiceImpl implements DemoService{
	
	@Autowired
	private DemoMapper mapper;
	
	@Override
	public List<Area> getAreas() {
		return mapper.getAreas();
	}

}
