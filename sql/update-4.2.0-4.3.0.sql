alter_column_type Address addressId LONG;
alter_column_type Address typeId INTEGER;

alter_column_type Contact_ prefixId INTEGER;
alter_column_type Contact_ suffixId INTEGER;

alter_column_type Counter currentId LONG;

alter_column_type EmailAddress emailAddressId LONG;
alter_column_type EmailAddress typeId INTEGER;

alter_column_type Groups_Permissions permissionId LONG;

alter_column_type ListType listTypeId INTEGER;

alter_column_type Organization_ statusId INTEGER;

alter_column_type OrgGroupPermission permissionId LONG;

alter_column_type OrgLabor typeId INTEGER;

alter_column_type Permission_ permissionId LONG;
alter_column_type Permission_ resourceId LONG;

alter_column_type Phone phoneId LONG;
alter_column_type Phone typeId INTEGER;

alter table Release_ add verified BOOLEAN;

alter_column_type Resource_ resourceId LONG;

alter_column_type Roles_Permissions permissionId LONG;

create table SRFrameworkVersion (
	frameworkVersionId LONG primary key,
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	url VARCHAR(1024) null,
	active_ BOOLEAN,
	priority INTEGER
);

create table SRFrameworkVersions_SRProductVersions (
	productVersionId LONG,
	frameworkVersionId LONG,
	primary key (productVersionId, frameworkVersionId)
);

create table SRLicense (
	licenseId LONG primary key,
	name VARCHAR(75) null,
	url VARCHAR(1024) null,
	openSource BOOLEAN,
	active_ BOOLEAN,
	recommended BOOLEAN
);

create table SRLicenses_SRProductEntries (
	productEntryId LONG,
	licenseId LONG,
	primary key (productEntryId, licenseId)
);

create table SRProductEntry (
	productEntryId LONG primary key,
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null,
	shortDescription STRING null,
	longDescription STRING null,
	pageURL VARCHAR(1024) null,
	repoGroupId VARCHAR(75) null,
	repoArtifactId VARCHAR(75) null
);

create table SRProductVersion (
	productVersionId LONG primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	productEntryId LONG,
	version VARCHAR(75) null,
	changeLog STRING null,
	downloadPageURL VARCHAR(1024) null,
	directDownloadURL VARCHAR(1024) null,
	repoStoreArtifact BOOLEAN
);

alter_column_type Users_Permissions permissionId LONG;

alter_column_type Website websiteId LONG;
alter_column_type Website typeId INTEGER;