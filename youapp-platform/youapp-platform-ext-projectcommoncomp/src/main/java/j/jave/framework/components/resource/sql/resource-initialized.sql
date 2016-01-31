-------------------RESOURCES---------------------------
insert into RESOURCES(URL,DESCRIPTION,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('/tablemanager.tablemanageraction/toViewRecords','查看表记录','YouAPP','39bb07bf9b2546208f128cca32d4db8f','YouAPP',sysdate,sysdate,'N',1)
insert into RESOURCES(URL,DESCRIPTION,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('/login.loginaction/toViewAllUser','查看所有用户','YouAPP','a22875a2548248f89ac39627d861c149','YouAPP',sysdate,sysdate,'N',1)

-----------------------RESOURCES_ROLES-------------------------------
insert into RESOURCES_ROLES(RESOURCEID,ROLEID,ENABLE,DESCRIPTION,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('39bb07bf9b2546208f128cca32d4db8f','4af5304ffe244b18b5af975417360b17','Y','','YouAPP','287c3e816bbb44ecaa2707d8cde0dae6','YouAPP',sysdate,sysdate,'N',1)
insert into RESOURCES_ROLES(RESOURCEID,ROLEID,ENABLE,DESCRIPTION,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('a22875a2548248f89ac39627d861c149','4af5304ffe244b18b5af975417360b17','Y','','YouAPP','7d6b7e69085545778aed437ee3d4503c','YouAPP',sysdate,sysdate,'N',1)

-----------------------RESOURCES_GROUPS-------------------------------
insert into RESOURCES_GROUPS(RESOURCEID,GROUPID,DESCRIPTION,ENABLE,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('39bb07bf9b2546208f128cca32d4db8f','01cf51a38a604433876ee9fee824c5a8','','Y','YouAPP','6973cb084db7490da29345e3856accfa','YouAPP',sysdate,sysdate,'N',1)
insert into RESOURCES_GROUPS(RESOURCEID,GROUPID,DESCRIPTION,ENABLE,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('a22875a2548248f89ac39627d861c149','01cf51a38a604433876ee9fee824c5a8','','Y','YouAPP','7496ec547e09458fbfd6f5803cb5755e','YouAPP',sysdate,sysdate,'N',1)
-----------------------RESOURCES_EXTEND-------------------------------
insert into RESOURCES_EXTEND(RESOURCEID,URL,ACTION,CACHED,DESCRIPTION,ID,CREATEID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('a22875a2548248f89ac39627d861c149','/login.loginaction/toViewAllUser','','Y','','174675a0c6e746cfacd4a583015ce91d','YouAPP','YouAPP',sysdate,sysdate,'N',1)


