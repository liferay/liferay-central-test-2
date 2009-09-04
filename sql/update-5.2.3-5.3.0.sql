create table AssetCategory (
	uuid_ VARCHAR(75) null,
	categoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCategoryId LONG,
	name VARCHAR(75) null,
	vocabularyId LONG
);

create table AssetCategoryProperty (
	categoryPropertyId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	categoryId LONG,
	key_ VARCHAR(75) null,
	value VARCHAR(75) null
);

create table AssetEntries_AssetCategories (
	entryId LONG not null,
	categoryId LONG not null,
	primary key (entryId, categoryId)
);

create table AssetEntries_AssetTags (
	entryId LONG not null,
	tagId LONG not null,
	primary key (entryId, tagId)
);

create table AssetEntry (
	entryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	visible BOOLEAN,
	startDate DATE null,
	endDate DATE null,
	publishDate DATE null,
	expirationDate DATE null,
	mimeType VARCHAR(75) null,
	title VARCHAR(255) null,
	description STRING null,
	summary STRING null,
	url STRING null,
	height INTEGER,
	width INTEGER,
	priority DOUBLE,
	viewCount INTEGER
);

create table AssetTag (
	tagId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	assetCount INTEGER
);

create table AssetTagProperty (
	tagPropertyId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	tagId LONG,
	key_ VARCHAR(75) null,
	value VARCHAR(255) null
);

create table AssetTagStats (
	tagStatsId LONG not null primary key,
	tagId LONG,
	classNameId LONG,
	assetCount INTEGER
);

create table AssetVocabulary (
	uuid_ VARCHAR(75) null,
	vocabularyId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null
);

alter table BlogsEntry add status INTEGER;

update BlogsEntry set status = 0 where draft = FALSE;
update BlogsEntry set status = 2 where draft = TRUE;

alter table Layout add layoutPrototypeId LONG;

create table LayoutPrototype (
	layoutPrototypeId LONG not null primary key,
	companyId LONG,
	name STRING null,
	description STRING null,
	settings_ STRING null,
	active_ BOOLEAN
);

alter table LayoutSet add layoutSetPrototypeId LONG;

create table LayoutSetPrototype (
	layoutSetPrototypeId LONG not null primary key,
	companyId LONG,
	name STRING null,
	description STRING null,
	settings_ STRING null,
	active_ BOOLEAN
);

create table Lock_ (
	uuid_ VARCHAR(75) null,
	lockId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	className VARCHAR(75) null,
	key_ VARCHAR(200) null,
	owner VARCHAR(75) null,
	inheritable BOOLEAN,
	expirationDate DATE null
);

create table UserGroupGroupRole (
	userGroupId LONG not null,
	groupId LONG not null,
	roleId LONG not null,
	primary key (userGroupId, groupId, roleId)
);