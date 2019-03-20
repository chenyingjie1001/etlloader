```

----1 注意id的问题 语句的id和你当前库的冲突。就以你库的为准

insert into BASE_RESOURCE (ID, APPLICATION_ID, RESOURCE_CODE, RESOURCE_NAME, RESOURCE_URL, RESOURCE_TYPE, PID, LEVEL_NUM, DORDER, CREATE_TIME, CREATE_USER, ICON, DASHBOARD_ID, BUILD_TYPE, PUBLISH_TYPE)
values (333, 22, 'ida_appeal', '扣款分析', '/analysis/appeal', '2', 329, null, 2, null, null, null, null, null, null);
insert into BASE_RESOURCE (ID, APPLICATION_ID, RESOURCE_CODE, RESOURCE_NAME, RESOURCE_URL, RESOURCE_TYPE, PID, LEVEL_NUM, DORDER, CREATE_TIME, CREATE_USER, ICON, DASHBOARD_ID, BUILD_TYPE, PUBLISH_TYPE)
values (334, 22, 'ida_appealconfig', '申诉说明设置', '/system/appealconfig', '2', 331, null, 1, null, null, null, null, null, null);
insert into BASE_RESOURCE (ID, APPLICATION_ID, RESOURCE_CODE, RESOURCE_NAME, RESOURCE_URL, RESOURCE_TYPE, PID, LEVEL_NUM, DORDER, CREATE_TIME, CREATE_USER, ICON, DASHBOARD_ID, BUILD_TYPE, PUBLISH_TYPE)
values (328, 22, 'ida_s_kkcl', '扣款处理', null, '2', 0, null, 1, null, null, null, null, null, null);
insert into BASE_RESOURCE (ID, APPLICATION_ID, RESOURCE_CODE, RESOURCE_NAME, RESOURCE_URL, RESOURCE_TYPE, PID, LEVEL_NUM, DORDER, CREATE_TIME, CREATE_USER, ICON, DASHBOARD_ID, BUILD_TYPE, PUBLISH_TYPE)
values (329, 22, 'ida_analysis', '扣款分析', '/analysis', '2', 0, null, 2, null, null, null, null, null, null);
insert into BASE_RESOURCE (ID, APPLICATION_ID, RESOURCE_CODE, RESOURCE_NAME, RESOURCE_URL, RESOURCE_TYPE, PID, LEVEL_NUM, DORDER, CREATE_TIME, CREATE_USER, ICON, DASHBOARD_ID, BUILD_TYPE, PUBLISH_TYPE)
values (330, 22, 'ida_manage', '考核管理', '/manage', '2', 0, null, 3, null, null, null, null, null, null);
insert into BASE_RESOURCE (ID, APPLICATION_ID, RESOURCE_CODE, RESOURCE_NAME, RESOURCE_URL, RESOURCE_TYPE, PID, LEVEL_NUM, DORDER, CREATE_TIME, CREATE_USER, ICON, DASHBOARD_ID, BUILD_TYPE, PUBLISH_TYPE)
values (331, 22, 'ida_system', '系统设置', '/system', '2', 0, null, 4, null, null, null, null, null, null);
insert into BASE_RESOURCE (ID, APPLICATION_ID, RESOURCE_CODE, RESOURCE_NAME, RESOURCE_URL, RESOURCE_TYPE, PID, LEVEL_NUM, DORDER, CREATE_TIME, CREATE_USER, ICON, DASHBOARD_ID, BUILD_TYPE, PUBLISH_TYPE)
values (332, 22, 'ida_collect', '扣款汇总', '/analysis/collect', '2', 329, null, 1, null, null, null, null, null, null);
commit;


----2 
update portal.base_application_version t set t.version = '1.4.0'  where app_code  = 'ida'
``` 
清空老数据 删除表
```
truncate table ida_import_record;


truncate table ida_charge_bill;

drop table ida_chargebill_detail_1;

drop table ida_chargebill_detail_1_test;

drop table ida_chargebill_detail;

```

执行脚本@ ida_xls_ningbo.sql
