package com.firesoon.idaservice.assessment.impl;

import com.firesoon.idaservice.assessment.AssessmentService;
import com.firesoon.paymentmapper.assessment.AssessmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author create by yingjie.chen on 2019/1/25.
 * @version 2019/1/25 10:04
 */

@Primary
@Service
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentMapper assessmentMapper;

    @Override
    public List<Map<String, Object>> listForMonth(Map<String, Object> params) {
        return assessmentMapper.listAssmessmentForMonth(params);
    }


    @Override
    public List<Map<String, Object>> listForYear(Map<String, Object> params) {
        return assessmentMapper.listAssmessmentForYear(params);
    }
}
