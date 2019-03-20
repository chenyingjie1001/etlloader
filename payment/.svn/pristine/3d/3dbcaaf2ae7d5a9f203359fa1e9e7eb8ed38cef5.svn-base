prompt PL/SQL Developer import file
prompt Created on 2019年3月6日 by yingjie.chen
set feedback off
set define off
prompt Disabling triggers for IDA_AREA...
alter table IDA_AREA disable all triggers;
prompt Disabling triggers for IDA_CHARGEBILLTYPE_CONFIG...
alter table IDA_CHARGEBILLTYPE_CONFIG disable all triggers;
prompt Disabling triggers for IDA_VIEW...
alter table IDA_VIEW disable all triggers;
prompt Disabling triggers for IDA_XLS_VIEW...
alter table IDA_XLS_VIEW disable all triggers;
prompt Deleting IDA_XLS_VIEW...
delete from IDA_XLS_VIEW;
commit;
prompt Deleting IDA_VIEW...
delete from IDA_VIEW;
commit;
prompt Deleting IDA_CHARGEBILLTYPE_CONFIG...
delete from IDA_CHARGEBILLTYPE_CONFIG;
commit;
prompt Deleting IDA_AREA...
delete from IDA_AREA;
commit;
prompt Loading IDA_AREA...
insert into IDA_AREA (ID, AREAID, AREANAME, CREATEDATE, ISUSE)
values ('1', '1', '绍兴', to_date('02-01-2019', 'dd-mm-yyyy'), '0');
insert into IDA_AREA (ID, AREAID, AREANAME, CREATEDATE, ISUSE)
values ('2', '2', '宁波', to_date('02-01-2019', 'dd-mm-yyyy'), '1');
commit;
prompt 2 records loaded
prompt Loading IDA_CHARGEBILLTYPE_CONFIG...
insert into IDA_CHARGEBILLTYPE_CONFIG (ID, NAME, CREATETIME, UPDATETIME)
values ('1', '市本级', to_date('21-01-2019', 'dd-mm-yyyy'), to_date('21-01-2019', 'dd-mm-yyyy'));
commit;
prompt 1 records loaded
prompt Loading IDA_VIEW...
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('2', '扣款核算项目', 'DEDUCTEPROJECT', ' varchar2', '3         ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('8', '扣除金额', 'CHARGE_MONEY', ' varchar2', '9         ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('10', '填写状态', 'ISWRITE', ' varchar2', '11        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('11', '申诉状态', 'ISAPPEAL', ' varchar2', '12        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('9', '反馈申诉', 'APPEALSTR', ' varchar2', '10        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('1', '姓名', 'INSUREPERSON_NAME', ' varchar2', '1         ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('12', '新扣款科室', 'DEDUCTEDEPT', ' varchar2', '2         ', null, null, null, null, null, '1');
commit;
prompt 7 records loaded
prompt Loading IDA_XLS_VIEW...
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('22', '就医方式', 'varchar2', 'medicalattention_way', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('20', '医生', 'varchar2', 'doctor_name', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('21', '参保类型', 'varchar2', 'insure_type', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('1', '姓名', 'varchar2', 'insureperson_name', to_date('23-11-2018', 'dd-mm-yyyy'), null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('2', '单位区县', 'varchar2', 'area', to_date('23-11-2018', 'dd-mm-yyyy'), null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('3', '处罚类型', 'varchar2', 'cftype', to_date('23-11-2018', 'dd-mm-yyyy'), null, '1', '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('4', '医保号', 'varchar2', 'hospitalization_no', null, null, '1', '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('5', '就诊编号', 'varchar2', 'jzno', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('6', '就诊类型', 'varchar2', 'curetype', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('7', '人员类别', 'varchar2', 'persontype', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('8', '就诊时间', 'varchar2', 'curedate', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('9', '医院名称', 'varchar2', 'hospitalname', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('10', '剔除费用额', 'varchar2', 'charge_money', null, null, '1', '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('11', '增减单元数', 'varchar2', 'cellnumber', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('12', '操作人', 'varchar2', 'operator', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('13', '操作人区县', 'varchar2', 'operatorarea', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('14', '操作时间', 'varchar2', 'operatordate', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('15', '备注', 'varchar2', 'bak', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('16', '项目名称', 'varchar2', 'item_name', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('17', '项目编号', 'varchar2', 'item_code', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('18', '扣款原因', 'varchar2', 'charge_reason', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('19', '规则名称', 'varchar2', 'rule_name', null, null, null, '1');
commit;
prompt 22 records loaded
prompt Enabling triggers for IDA_AREA...
alter table IDA_AREA enable all triggers;
prompt Enabling triggers for IDA_CHARGEBILLTYPE_CONFIG...
alter table IDA_CHARGEBILLTYPE_CONFIG enable all triggers;
prompt Enabling triggers for IDA_VIEW...
alter table IDA_VIEW enable all triggers;
prompt Enabling triggers for IDA_XLS_VIEW...
alter table IDA_XLS_VIEW enable all triggers;
set feedback on
set define on
prompt Done.
