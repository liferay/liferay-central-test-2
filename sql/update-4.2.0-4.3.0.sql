alter_column_type Address addressId LONG;
alter_column_type Address typeId INTEGER;

alter_column_type BlogsEntry groupId LONG;

alter_column_type BookmarksFolder groupId LONG;

alter_column_type CalEvent groupId LONG;

alter_column_type Contact_ prefixId INTEGER;
alter_column_type Contact_ suffixId INTEGER;

alter_column_type Counter currentId LONG;

alter_column_type DLFolder groupId LONG;

alter_column_type EmailAddress emailAddressId LONG;
alter_column_type EmailAddress typeId INTEGER;

alter_column_type Group_ groupId LONG;
alter_column_type Group_ parentGroupId LONG;
alter table Group_ add creatorUserId VARCHAR(75) null;

alter_column_type Groups_Orgs groupId LONG;

alter_column_type Groups_Permissions groupId LONG;
alter_column_type Groups_Permissions permissionId LONG;

alter_column_type Groups_Roles groupId LONG;

alter_column_type Groups_UserGroups groupId LONG;

alter_column_type IGFolder groupId LONG;

alter_column_type JournalArticle groupId LONG;

alter_column_type JournalContentSearch groupId LONG;

alter_column_type JournalStructure groupId LONG;

alter_column_type JournalTemplate groupId LONG;

alter table Layout add iconImage BOOLEAN;

alter_column_type LayoutSet groupId LONG;
alter table LayoutSet add logo BOOLEAN;

alter_column_type ListType listTypeId INTEGER;

alter_column_type MBStatsUser groupId LONG;

alter_column_type MBCategory groupId LONG;

alter_column_type Organization_ statusId INTEGER;

alter_column_type OrgGroupPermission permissionId LONG;
alter_column_type OrgGroupPermission groupId LONG;

alter_column_type OrgGroupRole groupId LONG;

alter_column_type OrgLabor typeId INTEGER;

alter_column_type Permission_ permissionId LONG;
alter_column_type Permission_ resourceId LONG;

alter_column_type Phone phoneId LONG;
alter_column_type Phone typeId INTEGER;

alter_column_type PollsQuestion groupId LONG;

alter table Release_ add verified BOOLEAN;

alter_column_type Resource_ resourceId LONG;

alter table Role_ add type_ INTEGER;
update Role_ SET type_ = 1;

alter_column_type Roles_Permissions permissionId LONG;

alter_column_type ShoppingCart groupId LONG;

alter_column_type ShoppingCategory groupId LONG;

alter_column_type ShoppingCoupon groupId LONG;

alter_column_type ShoppingOrder groupId LONG;

create table SCFrameworkVersion (
	frameworkVersionId LONG primary key,
	groupId LONG,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	url VARCHAR(75) null,
	active_ BOOLEAN,
	priority INTEGER
);

create table SCFrameworkVersions_SCProductVersions (
	productVersionId LONG,
	frameworkVersionId LONG,
	primary key (productVersionId, frameworkVersionId)
);

create table SCLicense (
	licenseId LONG primary key,
	name VARCHAR(75) null,
	url VARCHAR(75) null,
	openSource BOOLEAN,
	active_ BOOLEAN,
	recommended BOOLEAN
);

create table SCLicenses_SCProductEntries (
	productEntryId LONG,
	licenseId LONG,
	primary key (productEntryId, licenseId)
);

create table SCProductEntry (
	productEntryId LONG primary key,
	groupId LONG,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null,
	shortDescription VARCHAR(75) null,
	longDescription VARCHAR(75) null,
	pageURL VARCHAR(75) null,
	repoGroupId VARCHAR(75) null,
	repoArtifactId VARCHAR(75) null
);

create table SCProductVersion (
	productVersionId LONG primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	productEntryId LONG,
	version VARCHAR(75) null,
	changeLog VARCHAR(75) null,
	downloadPageURL VARCHAR(75) null,
	directDownloadURL VARCHAR(75) null,
	repoStoreArtifact BOOLEAN
);

create table SCLicenses_SCProductEntries (
	productEntryId LONG,
	licenseId LONG,
	primary key (productEntryId, licenseId)
);

create table SCProductEntry (
	productEntryId LONG primary key,
	groupId LONG,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null,
	shortDescription STRING null,
	longDescription STRING null,
	pageURL VARCHAR(75) null,
	repoGroupId VARCHAR(75) null,
	repoArtifactId VARCHAR(75) null
);

create table SCProductVersion (
	productVersionId LONG primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	productEntryId LONG,
	version VARCHAR(75) null,
	changeLog STRING null,
	downloadPageURL VARCHAR(75) null,
	directDownloadURL VARCHAR(75) null,
	repoStoreArtifact BOOLEAN
);

create table TagsAsset (
	assetId LONG primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	className VARCHAR(75) null,
	classPK VARCHAR(75) null,
	startDate DATE null,
	endDate DATE null,
	publishDate DATE null,
	expirationDate DATE null,
	mimeType VARCHAR(75) null,
	title VARCHAR(75) null,
	url VARCHAR(75) null,
	height INTEGER,
	width INTEGER
);

create table TagsAssets_TagsEntries (
	assetId LONG,
	entryId LONG,
	primary key (assetId, entryId)
);

create table TagsEntry (
	entryId LONG primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null
);

create table TagsProperty (
	propertyId LONG primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	entryId LONG,
	key_ VARCHAR(75) null,
	value VARCHAR(75) null
);

create table TagsSource (
	sourceId LONG primary key,
	parentSourceId LONG,
	name VARCHAR(75) null,
	acronym VARCHAR(75) null
);

create table UserGroupRole (
	userId VARCHAR(75) not null,
	groupId LONG,
	roleId VARCHAR(75) not null,
	primary key (userId, groupId, roleId)
);

alter_column_type Users_Groups groupId LONG;

alter_column_type Users_Permissions permissionId LONG;

alter_column_type Website websiteId LONG;
alter_column_type Website typeId INTEGER;

alter_column_type WikiNode groupId LONG;