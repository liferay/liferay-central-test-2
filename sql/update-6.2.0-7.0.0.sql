alter table AssetEntry add listable BOOLEAN;

COMMIT_TRANSACTION;

update AssetEntry set listable = TRUE;

drop table AssetTagProperty;

alter table BlogsEntry add subtitle STRING null;
alter table BlogsEntry add coverImageFileEntryId LONG;
alter table BlogsEntry add coverImageURL STRING null;
alter table BlogsEntry add smallImageFileEntryId LONG;

alter table DDMStructure add version VARCHAR(75) null;

update DDMStructure set version = '1.0';

create table DDMStructureVersion (
	structureVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	structureId LONG,
	version VARCHAR(75) null,
	name STRING null,
	description STRING null,
	definition TEXT null,
	storageType VARCHAR(75) null,
	type_ INTEGER
);

alter table DDMTemplate add version VARCHAR(75) null;

update DDMTemplate set version = '1.0';

create table DDMTemplateVersion (
	templateVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	templateId LONG,
	version VARCHAR(75) null,
	name STRING null,
	description STRING null,
	language VARCHAR(75) null,
	script TEXT null
);

alter table DLFolder add restrictionType INTEGER;

update DLFolder set restrictionType = 1 where overrideFileEntryTypes = 1;

alter table DLFolder drop column overrideFileEntryTypes;

create table ExportImportConfiguration (
	mvccVersion LONG default 0,
	exportImportConfigurationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	type_ INTEGER,
	settings_ TEXT null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

alter table Group_ add groupKey STRING;

update Group_ set groupKey = name;

alter table Group_ add inheritContent BOOLEAN;

alter table JournalFolder add restrictionType INTEGER;

create table JournalFolders_DDMStructures (
	structureId LONG not null,
	folderId LONG not null,
	primary key (structureId, folderId)
);

alter table Layout drop column iconImage;

alter table LayoutRevision drop column iconImage;

alter table LayoutSet drop column logo;

alter table LayoutSetBranch drop column logo;

alter table Organization_ add logoId LONG;

alter table RatingsEntry add uuid_ VARCHAR(75) null;

insert into Region (regionId, countryId, regionCode, name, active_) values (33001, 33, 'AT-1', 'Burgenland', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33002, 33, 'AT-2', 'Kärnten', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33003, 33, 'AT-3', 'Niederösterreich', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33004, 33, 'AT-4', 'Oberösterreich', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33005, 33, 'AT-5', 'Salzburg', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33006, 33, 'AT-6', 'Steiermark', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33007, 33, 'AT-7', 'Tirol', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33008, 33, 'AT-8', 'Vorarlberg', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33009, 33, 'AT-9', 'Vienna', TRUE);

update Region set regionCode = 'BB' where regionId = 4004 and regionCode = 'BR';
update Region set name = 'Monza e Brianza', regionCode = 'MB' where regionId = 8060 and regionCode = 'MZ';

alter table UserNotificationEvent add actionRequired BOOLEAN;