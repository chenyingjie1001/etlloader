-----------------------------------------------------
-- Export file for user IDA                        --
-- Created by yingjie.chen on 2018/11/30, 10:42:14 --
-----------------------------------------------------

spool ida_v1.0.log

prompt
prompt Creating table IDA_CHARGE_BILL
prompt ==============================
prompt
create table IDA.IDA_CHARGE_BILL
(
  ID              VARCHAR2(40),
  MONTH           VARCHAR2(100),
  NAME            VARCHAR2(100),
  IMPORT_USERNAME VARCHAR2(100),
  IMPORT_TIME     DATE,
  REMARK          VARCHAR2(1000)
)
tablespace HDC_IDA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on column IDA.IDA_CHARGE_BILL.MONTH
  is '�·�';
comment on column IDA.IDA_CHARGE_BILL.NAME
  is '�ۿ����';
comment on column IDA.IDA_CHARGE_BILL.IMPORT_USERNAME
  is '������';
comment on column IDA.IDA_CHARGE_BILL.IMPORT_TIME
  is '����ʱ��';
comment on column IDA.IDA_CHARGE_BILL.REMARK
  is '��ע';

prompt
prompt Creating table IDA_IMPORT_RECORD
prompt ================================
prompt
create table IDA.IDA_IMPORT_RECORD
(
  ID              VARCHAR2(40),
  MONTH           VARCHAR2(100),
  FILE_ADDRESS    VARCHAR2(100),
  IMPORT_USERNAME VARCHAR2(100),
  IMPORT_TIME     DATE
)
tablespace HDC_IDA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on column IDA.IDA_IMPORT_RECORD.MONTH
  is '�·�';
comment on column IDA.IDA_IMPORT_RECORD.FILE_ADDRESS
  is '�ļ���ַ';
comment on column IDA.IDA_IMPORT_RECORD.IMPORT_USERNAME
  is '������';
comment on column IDA.IDA_IMPORT_RECORD.IMPORT_TIME
  is '����ʱ��';

prompt
prompt Creating table IDA_VIEW
prompt =======================
prompt
create table IDA.IDA_VIEW
(
  ID         VARCHAR2(64),
  NAME       VARCHAR2(64),
  FIELDNAME  VARCHAR2(64),
  TYPE       VARCHAR2(64),
  SORT       CHAR(10),
  CREATEDATE DATE,
  UPDATEDATE DATE,
  BAK        VARCHAR2(64),
  BAK1       VARCHAR2(64),
  BAK2       VARCHAR2(64)
)
tablespace HDC_IDA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table IDA.IDA_VIEW
  is 'ҳ��Ҫ��ʾ���ֶ�';
comment on column IDA.IDA_VIEW.ID
  is '����';
comment on column IDA.IDA_VIEW.NAME
  is '�ֶ�';
comment on column IDA.IDA_VIEW.TYPE
  is '����varchar2 number';
comment on column IDA.IDA_VIEW.SORT
  is '����';
comment on column IDA.IDA_VIEW.CREATEDATE
  is '��������';
comment on column IDA.IDA_VIEW.UPDATEDATE
  is '��������';
comment on column IDA.IDA_VIEW.BAK
  is '����';
comment on column IDA.IDA_VIEW.BAK1
  is '����1';
comment on column IDA.IDA_VIEW.BAK2
  is '����2';

prompt
prompt Creating table IDA_XLS_VIEW
prompt ===========================
prompt
create table IDA.IDA_XLS_VIEW
(
  ID         VARCHAR2(64),
  NAME       VARCHAR2(64),
  TYPE       VARCHAR2(64),
  FIELDNAME  VARCHAR2(64),
  CREATEDATE DATE,
  CREATENAME VARCHAR2(64),
  ISKEY      CHAR(1)
)
tablespace HDC_IDA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table IDA.IDA_XLS_VIEW
  is '���ƻ��Ŀۿexcelģ��';

prompt
prompt Creating function GETKESHI
prompt ==========================
prompt
create or replace function ida.getKeshi(para in varchar2)
return varchar2
as
begin
       return 'Ƥ����';
end getKeshi;
/

prompt
prompt Creating function GETPROJECTNAME
prompt ================================
prompt
create or replace function ida.getProjectName(para in varchar2)
return varchar2
as
begin
       return 'ҩƷ';
end getProjectName;
/


spool off