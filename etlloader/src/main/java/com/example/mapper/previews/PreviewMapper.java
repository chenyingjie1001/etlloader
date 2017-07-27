package com.example.mapper.previews;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.previews.Relationship;
import com.example.mapper.base.BaseMapper;
@Mapper
public interface PreviewMapper extends BaseMapper<Relationship>{

	List<Map<String, Object>> preview(Relationship tempt) throws Exception;
	
	
	int update_delete(Relationship tempt) throws Exception;
}
