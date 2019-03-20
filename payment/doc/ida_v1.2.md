1. 执行以下脚本
```
create table IDA_ASSESSMANAGE_MONTH
(
  ID             VARCHAR2(40),
  ASSESS_TIME    VARCHAR2(100),
  DEPARTMENT     VARCHAR2(20),
  CHATGE_MONEY   NUMBER,
  ASSESS_SCORE   NUMBER,
  POORCOST_SCORE NUMBER,
  CREATETIME     DATE
)
;
comment on column IDA_ASSESSMANAGE_MONTH.ASSESS_TIME
  is '时间';
comment on column IDA_ASSESSMANAGE_MONTH.DEPARTMENT
  is '科室';
comment on column IDA_ASSESSMANAGE_MONTH.CHATGE_MONEY
  is '扣款金额';
comment on column IDA_ASSESSMANAGE_MONTH.ASSESS_SCORE
  is '考核得分';
comment on column IDA_ASSESSMANAGE_MONTH.POORCOST_SCORE
  is '劣质成本得分';


create table IDA_ASSESSMANAGE_YEAR
(
  ID             VARCHAR2(40),
  ASSESS_TIME    VARCHAR2(100),
  DEPARTMENT     VARCHAR2(20),
  CHATGE_MONEY   NUMBER,
  ASSESS_SCORE   NUMBER,
  POORCOST_SCORE NUMBER,
  CREATETIME     DATE
)
;
comment on column IDA_ASSESSMANAGE_YEAR.ASSESS_TIME
  is '时间';
comment on column IDA_ASSESSMANAGE_YEAR.DEPARTMENT
  is '科室';
comment on column IDA_ASSESSMANAGE_YEAR.CHATGE_MONEY
  is '扣款金额';
comment on column IDA_ASSESSMANAGE_YEAR.ASSESS_SCORE
  is '考核得分';
comment on column IDA_ASSESSMANAGE_YEAR.POORCOST_SCORE
  is '劣质成本得分';


create table IDA_CHARGEBILLTYPE_CONFIG
(
  ID         VARCHAR2(40),
  NAME       VARCHAR2(100),
  CREATETIME DATE,
  UPDATETIME DATE
)
;
comment on column IDA_CHARGEBILLTYPE_CONFIG.NAME
  is '扣款单类别名称';
  
  
-- Alter table 
alter table IDA_XLS_VIEW
  storage
  (
    next 8
  )
;
-- Add/modify columns 
alter table IDA_XLS_VIEW add billtype char(1);
-- Add comments to the columns 
comment on column IDA_XLS_VIEW.billtype
  is '扣款单类型';
  
-- Alter table 
alter table IDA_VIEW
  storage
  (
    next 8
  )
;
-- Add/modify columns 
alter table IDA_VIEW add billtype char(1);
-- Add comments to the columns 
comment on column IDA_VIEW.billtype
  is '扣款单类型';
  
-- Alter table 
alter table IDA_CHARGE_BILL
  storage
  (
    next 8
  )
;
-- Add/modify columns 
alter table IDA_CHARGE_BILL add billtype char(1);

-- Alter table 
alter table IDA_IMPORT_RECORD
  storage
  (
    next 8
  )
;
-- Add/modify columns 
alter table IDA_IMPORT_RECORD add billtype char(1);

create or replace procedure P_cory_test(tablename varchar2)
is
num number;
begin
    select count(1) into num from user_tables where table_name = upper(tablename || '_test') ;
    if num > 0 then
        execute immediate 'drop table '||tablename||'_test' ;
    end if;
    execute immediate 'create table '||tablename||'_test as select * from '||tablename;
end P_cory_test;
```
2. 执行ida_xls_绍兴1.2.sql脚本直接@执行
3. 清空 ida_import_record、ida_charge_bill；删除 ida_chargebill_detail
4. ida_department_config 关于主科室的信息配置好；ida_chargebilltype_config 关于扣款单类别信息配置好（默认表里一定会有数据

5. 执行
```
update portal.base_application_version t set t.version = '1.2.0' where t.app_name = '智能扣款助手'
```