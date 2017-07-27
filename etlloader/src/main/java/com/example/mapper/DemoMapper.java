package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.Area;
@Mapper
public interface DemoMapper {

	List<Area> getAreas();
}
