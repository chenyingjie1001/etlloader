package com.example.services.previews;

import java.util.List;
import java.util.Map;

import com.example.dto.previews.Relationship;
import com.example.services.base.IBaseService;

public interface IPreviewService extends IBaseService<Relationship>{
	
	List<Map<String, Object>> preview(Relationship tempt);
	
	int update_delete(Relationship tempt);
}
