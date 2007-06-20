update Account_ set parentAccountId = '0';
update Account_ set legalName = 'Liferay, Inc.' where legalName = 'Liferay, LLC';

alter_column_name Address className classNameId VARCHAR(75) null;

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

create table ClassName_ (
	classNameId LONG not null primary key,
	value VARCHAR(75) null
);

alter table Company add accountId LONG;
alter table Company add webId VARCHAR(75) null;
alter table Company add virtualHost VARCHAR(75) null;
alter table Company add logoId LONG;
update Company set webId = companyId;
alter table Company drop portalURL;
alter table Company drop homeURL;

alter_column_type Contact_ prefixId INTEGER;
alter_column_type Contact_ suffixId INTEGER;
alter_column_type Contact_ parentContactId LONG;
update Contact_ set parentContactId = 0;

alter_column_type Counter currentId LONG;

update Country set countryId = '51' where countryId = '51`';
alter_column_type Country countryId LONG;

alter_column_type CyrusUser userId LONG;

alter_column_type CyrusVirtual userId LONG;

drop table DataTracker;

alter table DLFileEntry add fileEntryId LONG;
alter_column_type DLFileEntry folderId LONG;

alter table DLFileRank add fileRankId LONG;
alter_column_type DLFileRank folderId LONG;

alter_column_type DLFileShortcut folderId LONG;
alter_column_type DLFileShortcut toFolderId LONG;

alter table DLFileVersion add fileVersionId LONG;
alter_column_type DLFileVersion folderId LONG;

alter_column_type DLFolder folderId LONG;
alter_column_type DLFolder groupId LONG;
alter_column_type DLFolder parentFolderId LONG;

alter_column_type EmailAddress emailAddressId LONG;
alter_column_name EmailAddress className classNameId VARCHAR(75) null;
alter_column_type EmailAddress typeId INTEGER;

alter_column_type Group_ groupId LONG;
alter table Group_ add creatorUserId VARCHAR(75) null;
alter_column_name Group_ className classNameId VARCHAR(75) null;
alter_column_type Group_ parentGroupId LONG;
alter table Group_ add liveGroupId LONG;
alter table Group_ add active_ BOOLEAN;
update Group_ set parentGroupId = 0;
update Group_ set liveGroupId = 0;
update Group_ set friendlyURL = '' where classNameId = 'com.liferay.portal.model.User';
update Group_ set active_ = TRUE;

alter_column_type Groups_Orgs groupId LONG;
alter_column_type Groups_Orgs organizationId LONG;

alter_column_type Groups_Permissions groupId LONG;
alter_column_type Groups_Permissions permissionId LONG;

alter_column_type Groups_Roles groupId LONG;
alter_column_type Groups_Roles roleId LONG;

alter_column_type Groups_UserGroups groupId LONG;
alter_column_type Groups_UserGroups userGroupId LONG;

alter_column_type IGFolder folderId LONG;
alter_column_type IGFolder groupId LONG;
alter_column_type IGFolder parentFolderId LONG;

alter_column_type IGImage imageId LONG;
alter_column_type IGImage folderId LONG;
alter table IGImage add smallImageId LONG;
alter table IGImage add largeImageId LONG;
alter table IGImage drop height;
alter table IGImage drop width;
alter table IGImage drop size_;

alter table Image add height INTEGER;
alter table Image add width INTEGER;
alter table Image add size_ INTEGER;

alter table JournalArticle add id_ VARCHAR(75) null;
alter table JournalArticle add resourcePrimKey LONG;
alter_column_type JournalArticle groupId LONG;
update JournalArticle set id_ = articleId;

create table JournalArticleImage (
	articleImageId LONG not null primary key,
	groupId LONG,
	articleId VARCHAR(75) null,
	version DOUBLE,
	elName VARCHAR(75) null,
	languageId VARCHAR(75) null,
	tempImage BOOLEAN
);

create table JournalArticleResource (
	resourcePrimKey LONG not null primary key,
	groupId LONG,
	articleId VARCHAR(75) null
);

drop table JournalContentSearch;
create table JournalContentSearch (
	contentSearchId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	privateLayout BOOLEAN,
	layoutId LONG,
	portletId VARCHAR(75) null,
	articleId VARCHAR(75) null
);

alter table JournalStructure add id_ VARCHAR(75) null;
alter_column_type JournalStructure groupId LONG;
update JournalStructure set id_ = structureId;

alter table JournalTemplate add id_ VARCHAR(75) null;
alter_column_type JournalTemplate groupId LONG;
alter table JournalTemplate add smallImageId LONG;
update JournalTemplate set id_ = templateId;

alter table Layout add plid LONG;
alter table Layout add groupId LONG;
alter table Layout add privateLayout BOOLEAN;
alter_column_type Layout parentLayoutId LONG;
alter table Layout add iconImage BOOLEAN;
alter table Layout add iconImageId LONG;
alter table Layout add wapThemeId VARCHAR(75) null;
alter table Layout add wapColorSchemeId VARCHAR(75) null;
alter table Layout add css VARCHAR(75) null;
update Layout set parentLayoutId = 0 where parentLayoutId = -1;

alter table LayoutSet add layoutSetId LONG;
alter_column_type LayoutSet groupId LONG;
alter table LayoutSet drop userId;
alter table LayoutSet add logo BOOLEAN;
alter table LayoutSet add logoId LONG;
alter table LayoutSet add wapThemeId VARCHAR(75) null;
alter table LayoutSet add wapColorSchemeId VARCHAR(75) null;
alter table LayoutSet add css VARCHAR(75) null;
update LayoutSet set logo = FALSE;

alter_column_type ListType listTypeId INTEGER;

create table MBBan (
	banId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	banUserId LONG
);

alter_column_type MBCategory categoryId LONG;
alter_column_type MBCategory groupId LONG;
alter_column_type MBCategory parentCategoryId LONG;
update MBCategory set companyId = '0', userId = '0' where companyId = 'system';

alter_column_type MBDiscussion discussionId LONG;
alter_column_name MBDiscussion className classNameId VARCHAR(75) null;
alter_column_type MBDiscussion threadId LONG;

alter_column_type MBMessage messageId LONG;
alter_column_type MBMessage categoryId LONG;
alter_column_type MBMessage threadId LONG;
alter_column_type MBMessage parentMessageId LONG;

alter table MBMessageFlag add messageFlagId LONG;
update MBMessageFlag set flag = '1';
alter_column_type MBMessageFlag flag INTEGER;

alter table MBStatsUser add statsUserId LONG;
alter_column_type MBStatsUser groupId LONG;

alter_column_type MBThread threadId LONG;
alter_column_type MBThread categoryId LONG;
alter_column_type MBThread rootMessageId LONG;

alter_column_type Organization_ organizationId LONG;
alter_column_type Organization_ parentOrganizationId LONG;
alter_column_type Organization_ statusId INTEGER;

alter_column_type OrgGroupPermission organizationId LONG;
alter_column_type OrgGroupPermission groupId LONG;
alter_column_type OrgGroupPermission permissionId LONG;

alter_column_type OrgGroupRole organizationId LONG;
alter_column_type OrgGroupRole groupId LONG;
alter_column_type OrgGroupRole roleId LONG;

alter_column_type OrgLabor orgLaborId LONG;
alter_column_type OrgLabor organizationId LONG;
alter_column_type OrgLabor typeId INTEGER;

create table PasswordPolicy (
	passwordPolicyId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	defaultPolicy BOOLEAN,
	name VARCHAR(75) null,
	description STRING null,
	changeable BOOLEAN,
	changeRequired BOOLEAN,
	minAge LONG,
	checkSyntax BOOLEAN,
	allowDictionaryWords BOOLEAN,
	minLength INTEGER,
	history BOOLEAN,
	historyCount INTEGER,
	expireable BOOLEAN,
	maxAge LONG,
	warningTime LONG,
	graceLimit INTEGER,
	lockout BOOLEAN,
	maxFailure INTEGER,
	lockoutDuration LONG,
	requireUnlock BOOLEAN,
	resetFailureCount LONG
);

create table PasswordPolicyRel (
	passwordPolicyRelId LONG not null primary key,
	passwordPolicyId LONG,
	classNameId LONG,
	classPK LONG
);

alter_column_type PasswordTracker passwordTrackerId LONG;

alter_column_type Permission_ permissionId LONG;
alter_column_type Permission_ resourceId LONG;

alter_column_type Phone phoneId LONG;
alter_column_name Phone className classNameId VARCHAR(75) null;
alter_column_type Phone typeId INTEGER;

create table PluginSetting (
	pluginSettingId LONG not null primary key,
	companyId LONG,
	pluginId VARCHAR(75) null,
	pluginType VARCHAR(75) null,
	roles VARCHAR(75) null,
	active_ BOOLEAN
);

alter table PollsChoice drop primary key;
alter_column_type PollsChoice questionId LONG;
alter table PollsChoice add name VARCHAR(75) null;
update PollsChoice set name = choiceId;

alter_column_type PollsQuestion questionId LONG;
alter_column_type PollsQuestion groupId LONG;

alter table PollsVote add voteId LONG;
alter_column_type PollsVote questionId LONG;

alter table Portlet add id_ LONG;

alter table PortletPreferences add portletPreferencesId LONG;
alter table PortletPreferences add ownerType INTEGER;
alter table PortletPreferences add plid LONG;
update PortletPreferences set plid = 0;
alter table PortletPreferences drop primary key;

alter_column_name RatingsEntry className classNameId VARCHAR(75) null;

alter_column_name RatingsStats className classNameId VARCHAR(75) null;

alter_column_type Region regionId LONG;

alter_column_type Release_ releaseId LONG;
alter table Release_ add verified BOOLEAN;

alter_column_type Resource_ resourceId LONG;
alter table Resource_ add codeId LONG;
alter table Resource_ drop typeId;

create table ResourceCode (
	codeId LONG not null primary key,
	companyId LONG,
	name VARCHAR(75) null,
	scope INTEGER
);

alter_column_type Role_ roleId LONG;
alter_column_name Role_ className classNameId VARCHAR(75) null;
alter table Role_ add type_ INTEGER;
update Role_ SET classNameId = '0';
update Role_ SET classPK = '0';
update Role_ SET type_ = 1;

alter_column_type Roles_Permissions roleId LONG;
alter_column_type Roles_Permissions permissionId LONG;

create table SCFrameworkVersi_SCProductVers (
	productVersionId LONG,
	frameworkVersionId LONG,
	primary key (productVersionId, frameworkVersionId)
);

create table SCFrameworkVersion (
	frameworkVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
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
	groupId LONG,
	companyId LONG,
	userId LONG,
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
	companyId LONG,
	userId LONG,
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

alter_column_type ShoppingCart groupId LONG;
alter_column_name ShoppingCart couponIds couponCodes VARCHAR(75) null;

alter_column_type ShoppingCategory categoryId LONG;
alter_column_type ShoppingCategory groupId LONG;
alter_column_type ShoppingCategory parentCategoryId LONG;

alter_column_type ShoppingCoupon groupId LONG;
alter table ShoppingCoupon add code_ VARCHAR(75) null;
update ShoppingCoupon set code_ = couponId;

alter_column_type ShoppingItem itemId LONG;
alter_column_type ShoppingItem categoryId LONG;
alter table ShoppingItem add smallImageId LONG;
alter table ShoppingItem add mediumImageId LONG;
alter table ShoppingItem add largeImageId LONG;

alter_column_type ShoppingItemField itemFieldId LONG;
alter_column_type ShoppingItemField itemId LONG;

alter_column_type ShoppingItemPrice itemPriceId LONG;
alter_column_type ShoppingItemPrice itemId LONG;

alter_column_type ShoppingOrder groupId LONG;
alter table ShoppingOrder add number_ VARCHAR(75) null;
alter_column_name ShoppingOrder couponIds couponCodes VARCHAR(75) null;
update ShoppingOrder set number_ = orderId;

alter table ShoppingOrderItem add orderItemId LONG;

alter_column_type Subscription subscriptionId LONG;
alter_column_name Subscription className classNameId VARCHAR(75) null;

create table TagsAsset (
	assetId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
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
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null
);

create table TagsProperty (
	propertyId LONG not null primary key,
	companyId LONG,
	userId LONG,
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
alter table User_ add passwordModifiedDate DATE null;
alter table User_ add graceLoginCount INTEGER;
alter table User_ add screenName VARCHAR(75) null;
alter table User_ add portraitId LONG;
alter table User_ add lastFailedLoginDate DATE null;
alter table User_ add lockout BOOLEAN;
alter table User_ add lockoutDate DATE null;
update User_ set defaultUser = FALSE;
update User_ set screenName = userId;
alter table User_ drop passwordExpirationDate;

alter_column_type UserGroup userGroupId LONG;

create table UserGroupRole (
	userId LONG not null,
	groupId LONG not null,
	roleId LONG not null,
	primary key (userId, groupId, roleId)
);

alter table UserIdMapper add userIdMapperId LONG;

alter_column_type Users_Groups groupId LONG;

alter_column_type Users_Orgs organizationId LONG;

alter_column_type Users_Permissions permissionId LONG;

alter_column_type Users_Roles roleId LONG;

alter_column_type Users_UserGroups userGroupId LONG;

drop table UserTracker;
create table UserTracker (
	userTrackerId LONG not null primary key,
	companyId LONG,
	userId LONG,
	modifiedDate DATE null,
	sessionId VARCHAR(200) null,
	remoteAddr VARCHAR(75) null,
	remoteHost VARCHAR(75) null,
	userAgent VARCHAR(200) null
);

drop table UserTrackerPath;
create table UserTrackerPath (
	userTrackerPathId LONG not null primary key,
	userTrackerId LONG,
	path STRING null,
	pathDate DATE null
);

alter_column_type Website websiteId LONG;
alter_column_name Website className classNameId VARCHAR(75) null;
alter_column_type Website typeId INTEGER;

alter_column_type WikiNode nodeId LONG;
alter_column_type WikiNode groupId LONG;

alter table WikiPage add pageId LONG;
alter table WikiPage add resourcePrimKey LONG;
alter_column_type WikiPage nodeId LONG;

create table WikiPageResource (
	resourcePrimKey LONG not null primary key,
	nodeId LONG,
	title VARCHAR(75) null
);