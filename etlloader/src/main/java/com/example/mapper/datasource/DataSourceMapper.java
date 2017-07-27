package com.example.mapper.datasource;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.datasource.DataSource;
import com.example.mapper.base.BaseMapper;


@Mapper
public interface DataSourceMapper extends BaseMapper<DataSource> {
	DataSource findById(int id)  throws Exception;
}
