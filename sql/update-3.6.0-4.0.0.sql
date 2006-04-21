drop table Account;
create table Account_ (
	accountId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentAccountId VARCHAR(75) null,
	name VARCHAR(75) null,
	legalName VARCHAR(75) null,
	legalId VARCHAR(75) null,
	legalType VARCHAR(75) null,
	sicCode VARCHAR(75) null,
	tickerSymbol VARCHAR(75) null,
	industry VARCHAR(75) null,
	type_ VARCHAR(75) null,
	size_ VARCHAR(75) null
);

alter table Address add street3 VARCHAR(75) null;
alter table Address add regionId VARCHAR(75) null;
alter table Address add countryId VARCHAR(75) null;
alter table Address add typeId VARCHAR(75) null;
alter table Address add mailing BOOLEAN;
alter table Address add primary_ BOOLEAN;

alter table BlogsCategory add userName VARCHAR(75) null;
alter table BlogsCategory add parentCategoryId VARCHAR(75) null;
alter table BlogsCategory add description STRING null;

drop table BlogsComments;

alter table BlogsEntry add groupId VARCHAR(75) not null;
alter table BlogsEntry add userName VARCHAR(75) null;

drop table BlogsLink;

drop table BlogsProps;

drop table BlogsReferer;

drop table BlogsUser;

alter table BookmarksFolder add description STRING null;

drop table Contact;
create table Contact_ (
	contactId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	accountId VARCHAR(75) null,
	parentContactId VARCHAR(75) null,
	firstName VARCHAR(75) null,
	middleName VARCHAR(75) null,
	lastName VARCHAR(75) null,
	nickName VARCHAR(75) null,
	prefixId VARCHAR(75) null,
	suffixId VARCHAR(75) null,
	male BOOLEAN,
	birthday DATE null,
	smsSn VARCHAR(75) null,
	aimSn VARCHAR(75) null,
	icqSn VARCHAR(75) null,
	msnSn VARCHAR(75) null,
	skypeSn VARCHAR(75) null,
	ymSn VARCHAR(75) null,
	employeeStatusId VARCHAR(75) null,
	employeeNumber VARCHAR(75) null,
	jobTitle VARCHAR(75) null,
	jobClass VARCHAR(75) null,
	hoursOfOperation VARCHAR(75) null
);

create table Country (
	countryId VARCHAR(75) not null primary key,
	countryCode VARCHAR(75) null,
	name VARCHAR(75) null,
	active_ BOOLEAN
);

create table DataTracker (
	dataTrackerId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	createdOn DATE null,
	createdByUserId VARCHAR(75) null,
	createdByUserName VARCHAR(75) null,
	updatedOn DATE null,
	updatedBy VARCHAR(75) null,
	className VARCHAR(75) null,
	classPK VARCHAR(75) null,
	active_ BOOLEAN
);

create table DLFileEntry (
	folderId VARCHAR(75) not null,
	name VARCHAR(100) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	versionUserId VARCHAR(75) null,
	versionUserName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(100) null,
	description STRING null,
	version DOUBLE,
	size_ INTEGER,
	readCount INTEGER,
	primary key (folderId, name)
);

drop table DLFileRank;
create table DLFileRank (
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	folderId VARCHAR(75) not null,
	name VARCHAR(100) not null,
	createDate DATE null,
	primary key (companyId, userId, folderId, name)
);

drop table DLFileVersion;
create table DLFileVersion (
	folderId VARCHAR(75) not null,
	name VARCHAR(100) not null,
	version DOUBLE not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	size_ INTEGER,
	primary key (folderId, name, version)
);

create table DLFolder (
	folderId VARCHAR(75) not null primary key,
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentFolderId VARCHAR(75) null,
	name VARCHAR(100) null,
	description STRING null,
	lastPostDate DATE null
);

create table EmailAddress (
	emailAddressId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	className VARCHAR(75) null,
	classPK VARCHAR(75) null,
	address VARCHAR(75) null,
	typeId VARCHAR(75) null,
	primary_ BOOLEAN
);

alter table Group_ add className VARCHAR(75) null;
alter table Group_ add classPK VARCHAR(75) null;
alter table Group_ add description STRING null;
alter table Group_ add availability VARCHAR(75) null;

create table Groups_Permissions (
	groupId  VARCHAR(75) not null,
	permissionId VARCHAR(75) not null
);

alter table IGFolder add description STRING null;

alter table JournalArticle add approvedDate DATE null;
alter table JournalArticle add expired BOOLEAN;
alter table JournalArticle add reviewDate DATE null;
update JournalArticle set approvedDate = modifiedDate where approved = 'TRUE';
update JournalArticle set expired = 'FALSE' where approved = 'TRUE';
update JournalArticle set expired = 'TRUE' where approved = 'FALSE';

alter_column_name JournalContentSearch userId ownerId VARCHAR(75) not null;
alter table JournalContentSearch add companyId VARCHAR(100) not null;
update JournalContentSearch set companyId = 'liferay.com';

alter table JournalTemplate add langType VARCHAR(75) null;
update JournalTemplate set langType = 'xsl';

alter_column_name Layout userId ownerId VARCHAR(75) not null;
alter_column_type Layout name STRING null;
alter table Layout add hidden_ BOOLEAN;
alter table Layout add themeId VARCHAR(75) null;
alter table Layout add colorSchemeId VARCHAR(75) null;

create table LayoutSet (
	ownerId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	groupId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	privateLayout BOOLEAN,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	pageCount INTEGER
);

create table ListType (
	listTypeId VARCHAR(75) not null primary key,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null
);

create table MBCategory (
	categoryId VARCHAR(75) not null primary key,
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCategoryId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null
);

create table MBDiscussion (
	discussionId VARCHAR(75) not null primary key,
	className VARCHAR(75) null,
	classPK VARCHAR(75) null,
	threadId VARCHAR(75) null
);

alter table MBTopic add categoryId VARCHAR(75) null;

drop table NetworkAddress;

create table Organization_ (
	organizationId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	parentOrganizationId VARCHAR(75) null,
	name VARCHAR(75) null,
	recursable BOOLEAN,
	regionId VARCHAR(75) null,
	countryId VARCHAR(75) null,
	statusId VARCHAR(75) null,
	comments STRING null
);

create table OrgGroupPermission (
	organizationId VARCHAR(75) not null,
	groupId VARCHAR(75) not null,
	permissionId VARCHAR(75) not null,
	primary key (organizationId, groupId, permissionId)
);

create table OrgGroupRole (
	organizationId VARCHAR(75) not null,
	groupId VARCHAR(75) not null,
	roleId VARCHAR(75) not null,
	primary key (organizationId, groupId, roleId)
);

create table OrgLabor (
	orgLaborId VARCHAR(75) not null primary key,
	organizationId VARCHAR(75) null,
	typeId VARCHAR(75) null,
	sunOpen INTEGER,
	sunClose INTEGER,
	monOpen INTEGER,
	monClose INTEGER,
	tueOpen INTEGER,
	tueClose INTEGER,
	wedOpen INTEGER,
	wedClose INTEGER,
	thuOpen INTEGER,
	thuClose INTEGER,
	friOpen INTEGER,
	friClose INTEGER,
	satOpen INTEGER,
	satClose INTEGER
);

create table Permission_ (
	permissionId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	actionId VARCHAR(75) null,
	resourceId VARCHAR(75) null
);

create table Phone (
	phoneId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	className VARCHAR(75) null,
	classPK VARCHAR(75) null,
	number_ VARCHAR(75) null,
	extension VARCHAR(75) null,
	typeId VARCHAR(75) null,
	primary_ BOOLEAN
);

alter_column_name PortletPreferences userId ownerId VARCHAR(75) not null;

create table Region (
	regionId VARCHAR(75) not null primary key,
	countryId VARCHAR(75) null,
	regionCode VARCHAR(75) null,
	name VARCHAR(75) null,
	active_ BOOLEAN
);

create table Resource_ (
	resourceId VARCHAR(25) not null primary key,
	companyId VARCHAR(25) not null,
	name VARCHAR(75) null,
	typeId VARCHAR(15) null,
	scope VARCHAR(15) null,
	primKey VARCHAR(200) null
);

create table Roles_Permissions (
	roleId VARCHAR(75) not null,
	permissionId VARCHAR(75) not null
);

create table Subscription (
	subscriptionId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	className VARCHAR(75) null,
	classPK VARCHAR(75) null,
	frequency VARCHAR(75) null
);

alter table User_ add contactId VARCHAR(75) null;
alter table User_ add modifiedDate DATE null;

create table Users_Orgs (
	userId VARCHAR(75) not null,
	organizationId VARCHAR(75) not null
);

create table Users_Permissions (
	userId VARCHAR(75) not null,
	permissionId VARCHAR(75) not null
);

create table Website (
	websiteId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	className VARCHAR(75) null,
	classPK VARCHAR(75) null,
	url VARCHAR(75) null,
	typeId VARCHAR(75) null,
	primary_ BOOLEAN
);

alter table WikiNode add groupId VARCHAR(75) not null;

@include portal-data-common.sql