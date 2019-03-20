### ida 1.1版本升级步骤


1. 创建表, 并加入当地名称 1, 1, 宁波, null, 1 五个字段添加进去
```
-- Create table
create table IDA_AREA
(
  ID         VARCHAR2(64),
  AREAID     VARCHAR2(64),
  AREANAME   VARCHAR2(64),
  CREATEDATE DATE,
  ISUSE      CHAR(1)
)
tablespace HDC_IDA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table IDA_AREA
  is '地区设置';
-- Add comments to the columns 
comment on column IDA_AREA.AREAID
  is '地区ID';
comment on column IDA_AREA.AREANAME
  is '地区名字';
comment on column IDA_AREA.ISUSE
  is '是否当前地区1 是， 0 否';
  
  
  create table IDA.IDA_DEPARTMENT_CONFIG
  (
    ID              VARCHAR2(40),
    CODE            VARCHAR2(100),
    NAME            VARCHAR2(100),
    MAIN_DEPARTMENT VARCHAR2(100)
  )
  ;
  comment on column IDA_DEPARTMENT_CONFIG.CODE
    is '科室代码';
  comment on column IDA_DEPARTMENT_CONFIG.NAME
    is '科室名称';
  comment on column IDA_DEPARTMENT_CONFIG.MAIN_DEPARTMENT
    is '主科室';
  
  
  create table IDA.IDA_APPEALTEMPLATE_CONFIG
  (
    ID        VARCHAR2(40),
    RULE_NAME VARCHAR2(100),
    ITEM_CODE VARCHAR2(100),
    ITEM_NAME VARCHAR2(100),
    CHARGEREASON VARCHAR2(1000),
    APPEALEXPLAINNUM VARCHAR2(10),
    APPEALEXPLAIN CLOB,
    CREATETIME DATE,
    UPDATETIME DATE
  )
  ;
  comment on column IDA_APPEALTEMPLATE_CONFIG.RULE_NAME
    is '规则名称';
  comment on column IDA_APPEALTEMPLATE_CONFIG.ITEM_CODE
    is '项目编码';
  comment on column IDA_APPEALTEMPLATE_CONFIG.ITEM_NAME
    is '项目名称';
  comment on column IDA_APPEALTEMPLATE_CONFIG.CHARGEREASON
    is '扣款原因';
  comment on column IDA_APPEALTEMPLATE_CONFIG.APPEALEXPLAINNUM
    is '申诉说明数目';
  comment on column IDA_APPEALTEMPLATE_CONFIG.APPEALEXPLAIN
    is '申诉说明';

```

```
insert into IDA_AREA (ID, AREAID, AREANAME, CREATEDATE, ISUSE)
   values ('1', '1', '绍兴', to_date('02-01-2019', 'dd-mm-yyyy'), '1');
   commit;
   ```

2. 数据库改动执行  宁波执行@ida_xls_ningbo.sql，绍兴执行@ida_xls.sql（执行过不需要再执行）

3. 执行
```
update portal.base_application_version t set t.version = '1.1.0' where t.app_name = '智能扣款助手'
```