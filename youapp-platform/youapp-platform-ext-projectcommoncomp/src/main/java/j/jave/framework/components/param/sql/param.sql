CREATE TABLE PARAM (
  ID VARCHAR(32),
  FUNCTIONID VARCHAR(32) DEFAULT NULL,
  CODE VARCHAR(32) DEFAULT NULL,
  NAME VARCHAR(128) DEFAULT NULL,
  DESCRIPTION VARCHAR(512) DEFAULT NULL,
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
);