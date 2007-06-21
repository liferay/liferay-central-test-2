update Account_ set parentAccountId = '0';
update Account_ set legalName = 'Liferay, Inc.' where legalName = 'Liferay, LLC';

alter_column_name Address className classNameId VARCHAR(75) null;

create table ClassName_ (
	classNameId LONG not null primary key,
	value VARCHAR(75) null
);

alter table Company add accountId LONG;
alter table Company add webId VARCHAR(75) null;
alter table Company add virtualHost VARCHAR(75) null;
alter table Company add logoId LONG;
update Company set webId = companyId;

update Contact_ set parentContactId = '0';

update Country set countryId = '51' where countryId = '51`';

drop table DataTracker;

alter table DLFileEntry add fileEntryId LONG;

alter table DLFileRank add fileRankId LONG;

alter table DLFileVersion add fileVersionId LONG;

alter_column_name EmailAddress className classNameId VARCHAR(75) null;

alter table Group_ add creatorUserId VARCHAR(75) null;
alter_column_name Group_ className classNameId VARCHAR(75) null;
alter table Group_ add liveGroupId LONG;
alter table Group_ add active_ BOOLEAN;
update Group_ set parentGroupId = '0';
update Group_ set liveGroupId = 0;
update Group_ set friendlyURL = '' where classNameId = 'com.liferay.portal.model.User';
update Group_ set active_ = TRUE;

alter table IGImage add smallImageId LONG;
alter table IGImage add largeImageId LONG;

alter table Image add height INTEGER;
alter table Image add width INTEGER;
alter table Image add size_ INTEGER;

alter table JournalArticle add id_ VARCHAR(75) null;
alter table JournalArticle add resourcePrimKey LONG;
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
update JournalStructure set id_ = structureId;

alter table JournalTemplate add id_ VARCHAR(75) null;
alter table JournalTemplate add smallImageId LONG;
update JournalTemplate set id_ = templateId;

alter table Layout add plid LONG;
alter table Layout add groupId LONG;
alter table Layout add privateLayout BOOLEAN;
alter table Layout add iconImage BOOLEAN;
alter table Layout add iconImageId LONG;
alter table Layout add wapThemeId VARCHAR(75) null;
alter table Layout add wapColorSchemeId VARCHAR(75) null;
alter table Layout add css VARCHAR(75) null;
update Layout set parentLayoutId = '0' where parentLayoutId = '-1';

alter table LayoutSet add layoutSetId LONG;
alter table LayoutSet add logo BOOLEAN;
alter table LayoutSet add logoId LONG;
alter table LayoutSet add wapThemeId VARCHAR(75) null;
alter table LayoutSet add wapColorSchemeId VARCHAR(75) null;
alter table LayoutSet add css VARCHAR(75) null;
update LayoutSet set logo = FALSE;

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

update MBCategory set categoryId = '0' where categoryId = 'system';
update MBCategory set companyId = '0', userId = '0' where companyId = 'system';

update MBMessage set categoryId = '0' where categoryId = 'system';

update MBThread set categoryId = '0' where categoryId = 'system';

alter_column_name MBDiscussion className classNameId VARCHAR(75) null;

alter table MBMessageFlag add messageFlagId LONG;
update MBMessageFlag set flag = '1';

alter table MBStatsUser add statsUserId LONG;

drop table OrgGroupRole;
create table OrgGroupRole (
	organizationId LONG not null,
	groupId LONG not null,
	roleId LONG not null,
	primary key (organizationId, groupId, roleId)
);

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

alter_column_name Phone className classNameId VARCHAR(75) null;

create table PluginSetting (
	pluginSettingId LONG not null primary key,
	companyId LONG,
	pluginId VARCHAR(75) null,
	pluginType VARCHAR(75) null,
	roles VARCHAR(75) null,
	active_ BOOLEAN
);

alter table PollsChoice add name VARCHAR(75) null;
update PollsChoice set name = choiceId;

alter table PollsVote add voteId LONG;

alter table Portlet add id_ LONG;

alter table PortletPreferences add portletPreferencesId LONG;
alter table PortletPreferences add ownerType INTEGER;
alter table PortletPreferences add plid LONG;
update PortletPreferences set plid = 0;

alter_column_name RatingsEntry className classNameId VARCHAR(75) null;

alter_column_name RatingsStats className classNameId VARCHAR(75) null;

alter table Release_ add verified BOOLEAN;

alter table Resource_ add codeId LONG;
alter table Resource_ drop typeId;

create table ResourceCode (
	codeId LONG not null primary key,
	companyId LONG,
	name VARCHAR(75) null,
	scope INTEGER
);

alter_column_name Role_ className classNameId VARCHAR(75) null;
alter table Role_ add type_ INTEGER;
update Role_ SET classNameId = '0';
update Role_ SET classPK = '0';
update Role_ SET type_ = 1;

create table SCFrameworkVersi_SCProductVers (
	productVersionId LONG not null,
	frameworkVersionId LONG not null,
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
	productEntryId LONG not null,
	licenseId LONG not null,
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

alter_column_name ShoppingCart couponIds couponCodes VARCHAR(75) null;

alter table ShoppingCoupon add code_ VARCHAR(75) null;
update ShoppingCoupon set code_ = couponId;

alter table ShoppingItem add smallImageId LONG;
alter table ShoppingItem add mediumImageId LONG;
alter table ShoppingItem add largeImageId LONG;

alter table ShoppingOrder add number_ VARCHAR(75) null;
alter_column_name ShoppingOrder couponIds couponCodes VARCHAR(75) null;
update ShoppingOrder set number_ = orderId;

alter table ShoppingOrderItem add orderItemId LONG;

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
	assetId LONG not null,
	entryId LONG not null,
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
alter table User_ add passwordModifiedDate DATE null;
alter table User_ add graceLoginCount INTEGER;
alter table User_ add screenName VARCHAR(75) null;
alter table User_ add portraitId LONG;
alter table User_ add lastFailedLoginDate DATE null;
alter table User_ add lockout BOOLEAN;
alter table User_ add lockoutDate DATE null;
update User_ set defaultUser = FALSE;
update User_ set screenName = userId;

update UserGroup SET parentUserGroupId = '0';

create table UserGroupRole (
	userId LONG not null,
	groupId LONG not null,
	roleId LONG not null,
	primary key (userId, groupId, roleId)
);

alter table UserIdMapper add userIdMapperId LONG;

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

alter_column_name Website className classNameId VARCHAR(75) null;

alter table WikiPage add pageId LONG;
alter table WikiPage add resourcePrimKey LONG;

create table WikiPageResource (
	resourcePrimKey LONG not null primary key,
	nodeId LONG,
	title VARCHAR(75) null
);