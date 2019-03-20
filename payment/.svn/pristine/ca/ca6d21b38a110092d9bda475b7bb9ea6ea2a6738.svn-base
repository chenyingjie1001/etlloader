prompt PL/SQL Developer import file
prompt Created on 2019年2月1日 by yingjie.chen
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
values ('1', '1', '绍兴', to_date('02-01-2019', 'dd-mm-yyyy'), '1');
insert into IDA_AREA (ID, AREAID, AREANAME, CREATEDATE, ISUSE)
values ('2', '2', '宁波', to_date('02-01-2019', 'dd-mm-yyyy'), '0');
commit;
prompt 2 records loaded
prompt Loading IDA_CHARGEBILLTYPE_CONFIG...
insert into IDA_CHARGEBILLTYPE_CONFIG (ID, NAME, CREATETIME, UPDATETIME)
values ('1', '市医保', to_date('21-01-2019', 'dd-mm-yyyy'), to_date('21-01-2019', 'dd-mm-yyyy'));
insert into IDA_CHARGEBILLTYPE_CONFIG (ID, NAME, CREATETIME, UPDATETIME)
values ('2', '柯桥职工', to_date('21-01-2019', 'dd-mm-yyyy'), to_date('21-01-2019', 'dd-mm-yyyy'));
insert into IDA_CHARGEBILLTYPE_CONFIG (ID, NAME, CREATETIME, UPDATETIME)
values ('3', '柯桥居民', to_date('21-01-2019', 'dd-mm-yyyy'), to_date('21-01-2019', 'dd-mm-yyyy'));
commit;
prompt 3 records loaded
prompt Loading IDA_VIEW...
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('1', '新扣款科室', 'DEDUCTEDEPT', ' varchar2', '1         ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('2', '扣款核算项目', 'DEDUCTEPROJECT', ' varchar2', '3         ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('3', '交易流水号', 'TRANSACTION_NO', ' varchar2', '4         ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('4', '审核意见书编码', 'AUDITCOMMENT_CODE', ' varchar2', '5         ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('5', '项目编码', 'ITEM_CODE', ' varchar2', '6         ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('6', '项目名称', 'ITEM_NAME', ' varchar2', '7         ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('7', '医保内金额', 'MEDICALINSURANCE_MONEY', ' varchar2', '8         ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('8', '扣除金额', 'CHARGE_MONEY', ' varchar2', '9         ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('9', '规则名称', 'RULE_NAME', ' varchar2', '10        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('10', '扣款原因', 'CHARGE_REASON', ' varchar2', '11        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('11', '还款原因', 'PAY_REASON', ' varchar2', '12        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('12', '操作人', 'OPERATOR', ' varchar2', '13        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('13', '医生姓名', 'DOCTOR_NAME', ' varchar2', '14        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('14', '社保卡号', 'SOCIALSECURECARD_NO', ' varchar2', '15        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('15', '参保类型', 'INSURE_TYPE', ' varchar2', '16        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('16', '科室编码', 'DEPARTMENT_CODE', ' varchar2', '17        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('17', '原扣款科室', 'DEPARTMENT_NAME', ' varchar2', '2         ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('18', '入院日期', 'INHOSPITAL_TIME', ' varchar2', '18        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('19', '出院日期', 'OUTHOSPITAL_TIME', ' varchar2', '19        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('20', '费用日期', 'FEE_TIME', ' varchar2', '20        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('21', '数量', 'NUM', ' varchar2', '21        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('22', '住院号', 'HOSPITALIZATION_NO', ' varchar2', '22        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('23', '就医方式', 'MEDICALATTENTION_WAY', ' varchar2', '23        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('24', '结算日期', 'BALANCE_TIME', ' varchar2', '24        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('25', '个人编号', 'PERSON_NO', ' varchar2', '25        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('26', '参保人姓名', 'INSUREPERSON_NAME', ' varchar2', '26        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('27', '反馈申诉', 'APPEALSTR', ' varchar2', '28        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('28', '单据号', 'DOCUMENT_NO', ' varchar2', '27        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('29', '填写状态', 'ISWRITE', ' varchar2', '29        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('30', '申诉状态', 'ISAPPEAL', ' varchar2', '30        ', null, null, null, null, null, '1');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('31', '新扣款科室', 'DEDUCTEDEPT', ' varchar2', '1         ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('32', '扣款核算项目', 'DEDUCTEPROJECT', ' varchar2', '3         ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('33', '交易流水号', 'TRANSACTION_NO', ' varchar2', '4         ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('34', '单据号', 'DOCUMENT_NO', ' varchar2', '5         ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('35', '审核意见书编码', 'AUDITCOMMENT_CODE', ' varchar2', '6         ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('36', '项目编码', 'ITEM_CODE', ' varchar2', '7         ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('37', '项目名称', 'ITEM_NAME', ' varchar2', '8         ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('38', '医保内金额', 'MEDICALINSURANCE_MONEY', ' varchar2', '9         ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('39', '扣除金额', 'CHARGE_MONEY', ' varchar2', '10        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('40', '规则名称', 'RULE_NAME', ' varchar2', '11        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('41', '扣除原因', 'CHARGE_REASON', ' varchar2', '12        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('42', '还款原因', 'PAY_REASON', ' varchar2', '13        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('43', '操作人', 'OPERATOR', ' varchar2', '14        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('44', '医生姓名', 'DOCTOR_NAME', ' varchar2', '15        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('45', '社保卡号', 'SOCIALSECURECARD_NO', ' varchar2', '16        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('46', '参保类型', 'INSURE_TYPE', ' varchar2', '17        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('47', '科室编码', 'DEPARTMENT_CODE', ' varchar2', '18        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('48', '原扣款科室', 'DEPARTMENT_NAME', ' varchar2', '2         ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('49', '入院日期', 'INHOSPITAL_TIME', ' varchar2', '19        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('50', '出院日期', 'OUTHOSPITAL_TIME', ' varchar2', '20        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('51', '费用日期', 'FEE_TIME', ' varchar2', '21        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('52', '数量', 'NUM', ' varchar2', '22        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('53', '住院号', 'HOSPITALIZATION_NO', ' varchar2', '23        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('54', '就医方式', 'MEDICALATTENTION_WAY', ' varchar2', '24        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('55', '结算日期', 'BALANCE_TIME', ' varchar2', '25        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('56', '个人编号', 'PERSON_NO', ' varchar2', '26        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('57', '参保人姓名', 'INSUREPERSON_NAME', ' varchar2', '28        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('58', '反馈申诉', 'APPEALSTR', ' varchar2', '27        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('59', '填写状态', 'ISWRITE', ' varchar2', '29        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('60', '申诉状态', 'ISAPPEAL', ' varchar2', '30        ', null, null, null, null, null, '2');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('61', '新扣款科室', 'DEDUCTEDEPT', ' varchar2', '1         ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('62', '明细号', 'DETAIL_NUMBER', ' varchar2', '2         ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('63', '医疗机构', 'MEDICAL_INSTITUTION', ' varchar2', '3         ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('64', '就诊编号', 'VISITING_NUMBER', ' varchar2', '4         ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('65', '住院门诊号', 'HOSPITALIZATION_NO', ' varchar2', '5         ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('66', '姓名', 'INSUREPERSON_NAME', ' varchar2', '6         ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('67', '身份证号', 'ID_NUMBER', ' varchar2', '7         ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('68', '医保卡号', 'CARD_NUMBER', ' varchar2', '8         ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('69', '结算日期', 'SETTLEMENT_DATE', ' varchar2', '9         ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('70', '费用名称', 'ITEM_NAME', ' varchar2', '10        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('71', '审核方式', 'AUDIT_MODE', ' varchar2', '11        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('72', '结算编号', 'SETTLEMENT_NUMBER', ' varchar2', '12        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('73', 'ttt', 'TTT', ' varchar2', '13        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('74', '审核事件ID', 'EVENT_ID', ' varchar2', '14        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('75', 'xxx', 'XXX', ' varchar2', '15        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('76', '反馈信息', 'APPEALSTR', ' varchar2', '27        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('77', '使用数量', 'USAGE_QUANTITY', ' varchar2', '16        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('78', '使用金额', 'USAGE_AMOUNT', ' varchar2', '17        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('79', '剔除数量', 'ELIMINATION_QUANTITY', ' varchar2', '18        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('80', '剔除金额', 'REJECTION_AMOUNT', ' varchar2', '19        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('81', '剔除原因', 'CHARGE_REASON', ' varchar2', '20        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('82', '放大比例', 'ENLARGE_SCALE', ' varchar2', '21        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('83', '放大后金额', 'ENLARGED_AMOUNT', ' varchar2', '22        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('84', '扣款比例', 'DEDUCTION_RATIO', ' varchar2', '23        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('85', '扣款金额', 'CHARGE_MONEY', ' varchar2', '24        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('86', '医师', 'DOCTOR_NAME', ' varchar2', '25        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('87', '确认状态', 'ACKNOWLEDGEMENT_STATE', ' varchar2', '26        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('88', '填写状态', 'ISWRITE', ' varchar2', '28        ', null, null, null, null, null, '3');
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2, BILLTYPE)
values ('89', '申诉状态', 'ISAPPEAL', ' varchar2', '29        ', null, null, null, null, null, '3');
commit;
prompt 89 records loaded
prompt Loading IDA_XLS_VIEW...
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('1', '序号', 'varchar2', 'order_id', to_date('23-11-2018', 'dd-mm-yyyy'), null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('81', '科室名称', 'varchar2', 'department_name', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('2', '交易流水号', 'varchar2', 'transaction_no', to_date('23-11-2018', 'dd-mm-yyyy'), null, '1', '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('3', '审核意见书编码', 'varchar2', 'auditcomment_code', to_date('23-11-2018', 'dd-mm-yyyy'), null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('4', '项目编码', 'varchar2', 'item_code', null, null, '1', '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('5', '项目名称', 'varchar2', 'item_name', null, null, '1', '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('6', '医保内金额', 'varchar2', 'medicalinsurance_money', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('7', '扣除金额', 'varchar2', 'charge_money', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('8', '规则名称', 'varchar2', 'rule_name', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('9', '扣款原因', 'varchar2', 'charge_reason', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('10', '还款原因', 'varchar2', 'pay_reason', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('11', '操作人', 'varchar2', 'operator', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('12', '医生姓名', 'varchar2', 'doctor_name', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('13', '社保卡号', 'varchar2', 'socialsecurecard_no', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('14', '参保类型', 'varchar2', 'insure_type', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('15', '科室编码', 'varchar2', 'department_code', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('16', '科室名称', 'varchar2', 'department_name', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('17', '入院日期', 'varchar2', 'inhospital_time', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('18', '出院日期', 'varchar2', 'outhospital_time', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('19', '费用日期', 'varchar2', 'fee_time', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('20', '数量', 'varchar2', 'num', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('21', '住院号', 'varchar2', 'hospitalization_no', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('22', '就医方式', 'varchar2', 'medicalattention_way', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('23', '结算日期', 'varchar2', 'balance_time', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('24', '个人编号', 'varchar2', 'person_no', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('25', '参保人姓名', 'varchar2', 'insureperson_name', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('26', '反馈申诉', 'varchar2', 'feedback_complaint', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('27', '单据号', 'varchar2', 'document_no', null, null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('28', '序号', 'varchar2', 'order_id', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('29', '交易流水号', 'varchar2', 'transaction_no', null, null, '1', '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('30', '单据号', 'varchar2', 'document_no', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('31', '审核意见书编码', 'varchar2', 'auditcomment_code', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('32', '项目编码', 'varchar2', 'item_code', null, null, '1', '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('33', '项目名称', 'varchar2', 'item_name', null, null, '1', '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('34', '医保内金额', 'varchar2', 'medicalinsurance_money', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('35', '扣除金额', 'varchar2', 'charge_money', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('36', '规则名称', 'varchar2', 'rule_name', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('37', '扣除原因', 'varchar2', 'charge_reason', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('38', '还款原因', 'varchar2', 'pay_reason', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('39', '操作人', 'varchar2', 'operator', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('40', '医生姓名', 'varchar2', 'doctor_name', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('41', '社保卡号', 'varchar2', 'socialsecurecard_no', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('42', '参保类型', 'varchar2', 'insure_type', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('43', '科室编码', 'varchar2', 'department_code', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('44', '科室名称', 'varchar2', 'department_name', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('45', '入院日期', 'varchar2', 'inhospital_time', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('46', '出院日期', 'varchar2', 'outhospital_time', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('47', '费用日期', 'varchar2', 'fee_time', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('48', '数量', 'varchar2', 'num', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('49', '住院号', 'varchar2', 'hospitalization_no', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('50', '就医方式', 'varchar2', 'medicalattention_way', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('51', '结算日期', 'varchar2', 'balance_time', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('52', '个人编号', 'varchar2', 'person_no', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('53', '参保人姓名', 'varchar2', 'insureperson_name', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('54', '反馈申诉', 'varchar2', 'feedback_complaint', null, null, null, '2');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('55', '明细号', 'varchar2', 'detail_number', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('56', '医疗机构', 'varchar2', 'medical_institution', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('57', '就诊编号', 'varchar2', 'visiting_number', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('58', '住院门诊号', 'varchar2', 'hospitalization_no', null, null, '1', '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('59', '姓名', 'varchar2', 'insureperson_name', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('60', '身份证号', 'varchar2', 'id_number', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('61', '医保卡号', 'varchar2', 'card_number', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('62', '结算日期', 'varchar2', 'settlement_date', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('63', '费用名称', 'varchar2', 'item_name', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('64', '审核方式', 'varchar2', 'audit_mode', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('65', '结算编号', 'varchar2', 'settlement_number', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('66', 'ttt', 'varchar2', 'ttt', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('67', '审核事件ID', 'varchar2', 'event_id', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('69', '反馈信息', 'varchar2', 'feedback_information', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('70', '使用数量', 'varchar2', 'usage_quantity', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('71', '使用金额', 'varchar2', 'usage_amount', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('72', '剔除数量', 'varchar2', 'elimination_quantity', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('73', '剔除金额', 'varchar2', 'rejection_amount', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('74', '剔除原因', 'varchar2', 'charge_reason', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('75', '放大比例', 'varchar2', 'enlarge_scale', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('76', '放大后金额', 'varchar2', 'enlarged_amount', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('77', '扣款比例', 'varchar2', 'deduction_ratio', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('78', '扣款金额', 'varchar2', 'charge_money', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('79', '医师', 'varchar2', 'doctor_name', null, null, null, '3');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY, BILLTYPE)
values ('80', '确认状态', 'varchar2', 'acknowledgement_state', null, null, null, '3');
commit;
prompt 81 records loaded
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
