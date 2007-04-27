alter_column_type Address addressId LONG;
alter_column_type Address typeId INTEGER;

alter_column_type BlogsCategory categoryId LONG;
alter_column_type BlogsCategory parentCategoryId LONG;

alter_column_type BlogsEntry entryId LONG;
alter_column_type BlogsEntry groupId LONG;
alter_column_type BlogsEntry categoryId LONG;

alter_column_type BookmarksEntry entryId LONG;

alter_column_type BookmarksFolder folderId LONG;
alter_column_type BookmarksFolder groupId LONG;
alter_column_type BookmarksFolder parentFolderId LONG;

alter_column_type CalEvent eventId LONG;
alter_column_type CalEvent groupId LONG;

alter table Company add accountId LONG;
alter table Company add webId VARCHAR(75) null;
update Company set webId = companyId;

alter_column_type Contact_ prefixId INTEGER;
alter_column_type Contact_ suffixId INTEGER;
alter_column_type Contact_ parentContactId LONG;

alter_column_type Counter currentId LONG;

alter_column_type DLFolder groupId LONG;

alter_column_type EmailAddress emailAddressId LONG;
alter_column_type EmailAddress typeId INTEGER;

alter_column_type Group_ groupId LONG;
alter table Group_ add creatorUserId VARCHAR(75) null;
alter_column_type Group_ parentGroupId LONG;
alter table Group_ add liveGroupId LONG;
alter table Group_ add active_ BOOLEAN;
update Group_ set liveGroupId = -1;
update Group_ set friendlyURL = '' where className = 'com.liferay.portal.model.User';
update Group_ set active_ = TRUE;

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
alter table Layout add css VARCHAR(75) null;

alter_column_type LayoutSet groupId LONG;
alter table LayoutSet add logo BOOLEAN;
alter table LayoutSet add css VARCHAR(75) null;
update LayoutSet set logo = FALSE;

alter_column_type ListType listTypeId INTEGER;

create table MBBan (
	banId LONG not null primary key,
	groupId LONG not null,
	companyId LONG not null,
	userId LONG not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	banUserId LONG
);

alter_column_type MBCategory groupId LONG;

alter_column_type MBStatsUser groupId LONG;

alter_column_type Organization_ statusId INTEGER;

alter_column_type OrgGroupPermission permissionId LONG;
alter_column_type OrgGroupPermission groupId LONG;

alter_column_type OrgGroupRole groupId LONG;

alter_column_type OrgLabor typeId INTEGER;

create table PasswordPolicy (
	passwordPolicyId LONG not null primary key,
	companyId LONG not null,
	userId LONG not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	changeable BOOLEAN,
	changeRequired BOOLEAN,
	minAge INTEGER,
	storageScheme VARCHAR(75) null,
	checkSyntax BOOLEAN,
	allowDictionaryWords BOOLEAN,
	minLength INTEGER,
	history BOOLEAN,
	historyCount INTEGER,
	expireable BOOLEAN,
	maxAge INTEGER,
	warningTime INTEGER,
	graceLimit INTEGER,
	lockout BOOLEAN,
	maxFailure INTEGER,
	requireUnlock BOOLEAN,
	lockoutDuration INTEGER,
	resetFailureCount INTEGER
);

create table PasswordPolicyRel (
	passwordPolicyRelId LONG not null primary key,
	passwordPolicyId LONG,
	className VARCHAR(75) null,
	classPK VARCHAR(75) null
);


alter_column_type PasswordTracker passwordTrackerId LONG;

alter_column_type Permission_ permissionId LONG;
alter_column_type Permission_ resourceId LONG;

alter_column_type Phone phoneId LONG;
alter_column_type Phone typeId INTEGER;

create table PluginSetting (
	pluginSettingId LONG not null primary key,
	companyId LONG not null,
	pluginId VARCHAR(75) null,
	pluginType VARCHAR(75) null,
	roles VARCHAR(75) null,
	active_ BOOLEAN
);

alter_column_type PollsQuestion groupId LONG;

alter_column_type Release_ releaseId LONG;
alter table Release_ add verified BOOLEAN;

alter_column_type Resource_ resourceId LONG;
alter table Resource_ add codeId LONG;
alter table Resource_ drop typeId;

create table ResourceCode (
	codeId LONG not null primary key,
	companyId LONG not null,
	name VARCHAR(75) null,
	scope VARCHAR(75) null
);

alter table Role_ add type_ INTEGER;
update Role_ SET type_ = 1;

alter_column_type Roles_Permissions permissionId LONG;

create table SCFrameworkVersi_SCProductVers (
	productVersionId LONG,
	frameworkVersionId LONG,
	primary key (productVersionId, frameworkVersionId)
);

create table SCFrameworkVersion (
	frameworkVersionId LONG not null primary key,
	groupId LONG not null,
	companyId LONG not null,
	userId LONG not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	url VARCHAR(1024) null,
	active_ BOOLEAN,
	priority INTEGER
);

create table SCLicense (
	licenseId LONG not null primary key,
	name VARCHAR(75) null,
	url VARCHAR(1024) null,
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
	productEntryId LONG not null primary key,
	groupId LONG not null,
	companyId LONG not null,
	userId LONG not null,
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

create table SCProductVersion (
	productVersionId LONG not null primary key,
	companyId LONG not null,
	userId LONG not null,
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

alter_column_type ShoppingCart groupId LONG;

alter_column_type ShoppingCategory groupId LONG;

alter_column_type ShoppingCoupon groupId LONG;

alter_column_type ShoppingOrder groupId LONG;

alter_column_type Subscription subscriptionId LONG;

create table TagsAsset (
	assetId LONG not null primary key,
	companyId LONG not null,
	userId LONG not null,
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
	entryId LONG not null primary key,
	companyId LONG not null,
	userId LONG not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null
);

create table TagsProperty (
	propertyId LONG not null primary key,
	companyId LONG not null,
	userId LONG not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	entryId LONG,
	key_ VARCHAR(75) null,
	value VARCHAR(75) null
);

create table TagsSource (
	sourceId LONG not null primary key,
	parentSourceId LONG,
	name VARCHAR(75) null,
	acronym VARCHAR(75) null
);

alter table User_ add defaultUser BOOLEAN;
alter_column_type User_ contactId LONG;
alter table User_ add screenName VARCHAR(75) null;
update User_ set defaultUser = FALSE;
update User_ set screenName = userId;

create table UserGroupRole (
	userId LONG not null,
	groupId LONG not null,
	roleId VARCHAR(75) not null,
	primary key (userId, groupId, roleId)
);

alter_column_type Users_Groups groupId LONG;

alter_column_type Users_Permissions permissionId LONG;

alter_column_type Website websiteId LONG;
alter_column_type Website typeId INTEGER;

alter_column_type WikiNode groupId LONG;