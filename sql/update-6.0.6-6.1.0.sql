alter table AssetCategory add description STRING null;

alter table BlogsEntry add description STRING null;
alter table BlogsEntry add smallImage BOOLEAN null;
alter table BlogsEntry add smallImageId VARCHAR(75) null;
alter table BlogsEntry add smallImageURL STRING null;

alter table BookmarksEntry add description VARCHAR(75) null;

COMMIT_TRANSACTION;

update BookmarksEntry set description = comments;
alter table BookmarksEntry drop column comments;

alter table CalEvent add location STRING null;

create table DDMStructureEntry (
	uuid_ VARCHAR(75) null,
	structureEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	structureId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null,
	xsd TEXT null
);

create table DDMStructureEntryLink (
	structureEntryLinkId LONG not null primary key,
	structureId VARCHAR(75) null,
	className VARCHAR(75) null,
	classPK LONG
);

alter table DLFileEntry add repositoryId LONG;

COMMIT_TRANSACTION;

update DLFileEntry set repositoryId = groupId;

drop index IX_CE705D48 on DLFileRank;
drop index IX_40B56512 on DLFileRank;
alter table DLFileRank add fileEntryId LONG;

drop index IX_55C736AC on DLFileShortcut;
drop index IX_346A0992 on DLFileShortcut;
alter table DLFileShortcut add repositoryId LONG;
alter table DLFileShortcut add toFileEntryId LONG;

COMMIT_TRANSACTION;

update DLFileShortcut set repositoryId = groupId;

drop index IX_B413F1EC on DLFileVersion;
drop index IX_94E784D2 on DLFileVersion;
drop index IX_2F8FED9C on DLFileVersion;
alter table DLFileVersion add repositoryId LONG;
alter table DLFileVersion add fileEntryId LONG;

COMMIT_TRANSACTION;

update DLFileVersion set repositoryId = groupId;

alter table DLFolder add repositoryId LONG;
alter table DLFolder add mountPoint BOOLEAN null;

COMMIT_TRANSACTION;

update DLFolder set repositoryId = groupId;

update Group_ set type_ = 3 where type_ = 0;

alter table Layout add keywords STRING null;
alter table Layout add robots STRING null;
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
	major BOOLEAN,
	plid LONG,
	privateLayout BOOLEAN,
	name STRING null,
	title STRING null,
	description STRING null,
	keywords STRING null,
	robots STRING null,
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
	classNameId LONG,
	name VARCHAR(75) null,
	description STRING null,
	portletId VARCHAR(75) null,
	typeSettings TEXT null,
	dlFolderId LONG
);

create table RepositoryEntry (
	uuid_ VARCHAR(75) null,
	repositoryEntryId LONG not null primary key,
	groupId LONG,
	repositoryId LONG,
	mappedId VARCHAR(75) null
);

alter table SocialEquityLog add extraData VARCHAR(255) null;

alter table User_ add status int;

COMMIT_TRANSACTION;

update User_ set status = 0;
update User_ set status = 5 where active_ = FALSE;
alter table User_ drop column active_;

create table UserGroups_Teams (
	userGroupId LONG not null,
	teamId LONG not null,
	primary key (userGroupId, teamId)
);

create table UserNotificationEvent (
	uuid_ VARCHAR(75) null,
	userNotificationEventId LONG not null primary key,
	companyId LONG,
	userId LONG,
	type_ VARCHAR(75) null,
	timestamp LONG,
	deliverBy LONG,
	payload TEXT null
);

create table VirtualHost (
	virtualHostId LONG not null primary key,
	companyId LONG,
	layoutSetId LONG,
	hostname VARCHAR(75) null
);