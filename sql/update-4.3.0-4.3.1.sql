alter table Layout add description STRING null;
alter table Layout add dlFolderId LONG;

alter table Organization_ add location BOOLEAN;
alter table Organization_ add inheritable BOOLEAN;

update Organization_ set location = FALSE WHERE PARENTORGANIZATIONID = 0;
update Organization_ set location = TRUE WHERE PARENTORGANIZATIONID <> 0;
update Organization_ set inheritable = TRUE;