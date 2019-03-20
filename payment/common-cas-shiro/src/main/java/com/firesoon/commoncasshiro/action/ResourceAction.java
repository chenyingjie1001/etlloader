package com.firesoon.commoncasshiro.action;

import com.firesoon.commoncasshiro.services.resource.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author create by yingjie.chen on 2019/3/18.
 * @version 2019/3/18 18:30
 */
@Api
@Slf4j
@RestController
@RequestMapping(value = "/api/common/", method = RequestMethod.POST)
public class ResourceAction extends BaseAction {


    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "menu", notes = "menu")
    @RequestMapping(value = "queryMenus")
    public Object queryMenus(@ApiParam("{\"search\":\"ida\"}")@RequestBody Map<String, Object> params){
        return succ(resourceService.queryApp(params));
    }
}
