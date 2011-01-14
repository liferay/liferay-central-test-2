alter table AssetCategory add description STRING null;

alter table BlogsEntry add description STRING null;
alter table BlogsEntry add smallImage BOOLEAN null;
alter table BlogsEntry add smallImageId VARCHAR(75) null;
alter table BlogsEntry add smallImageURL STRING null;

alter table BookmarksEntry add description VARCHAR(75) null;

COMMIT_TRANSACTION;

update BookmarksEntry set description = comments;
alter table BookmarksEntry drop column comments;

drop index IX_CE705D48 on DLFileRank;
drop index IX_40B56512 on DLFileRank;
alter table DLFileRank add fileEntryId LONG;

drop index IX_55C736AC on DLFileShortcut;
drop index IX_346A0992 on DLFileShortcut;
alter table DLFileShortcut add toFileEntryId LONG;

drop index IX_B413F1EC on DLFileVersion;
drop index IX_94E784D2 on DLFileVersion;
drop index IX_2F8FED9C on DLFileVersion;
alter table DLFileVersion add fileEntryId LONG;

create table DeletionEntry (
	entryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	classUuid VARCHAR(75) null,
	parentId LONG
);

update Group_ set type_ = 3 where type_ = 0;

alter table Layout drop column dlFolderId;

create table LayoutRevision (
	layoutRevisionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	layoutSetBranchId LONG,
	parentLayoutRevisionId LONG,
	head BOOLEAN,
	plid LONG,
	name STRING null,
	title STRING null,
	description STRING null,
	typeSettings TEXT null,
	iconImage BOOLEAN,
	iconImageId LONG,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	wapThemeId VARCHAR(75) null,
	wapColorSchemeId VARCHAR(75) null,
	css STRING null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table LayoutSetBranch (
	layoutSetBranchId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	privateLayout BOOLEAN,
	name VARCHAR(75) null,
	description STRING null
);

alter table MBCategory add displayStyle VARCHAR(75) null;

COMMIT_TRANSACTION;

update MBCategory set displayStyle = 'default';

alter table MBMessage add format VARCHAR(75) null;

COMMIT_TRANSACTION;

update MBMessage set format = 'bbcode';

alter table MBThread add companyId LONG;
alter table MBThread add rootMessageUserId LONG;

create table Repository (
	repositoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	portletId VARCHAR(75) null,
	type_ INTEGER,
	typeSettings VARCHAR(75) null,
	dlFolderId LONG
);

alter table SocialEquityLog add extraData VARCHAR(255) null;

create table VirtualHost (
	virtualHostId LONG not null primary key,
	companyId LONG,
	layoutSetId LONG,
	hostname VARCHAR(75) null
);