create table Account_ (
	accountId LONG primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentAccountId LONG,
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

create table Address (
	addressId LONG primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	street1 VARCHAR(75) null,
	street2 VARCHAR(75) null,
	street3 VARCHAR(75) null,
	city VARCHAR(75) null,
	zip VARCHAR(75) null,
	regionId LONG,
	countryId LONG,
	typeId INTEGER,
	mailing BOOLEAN,
	primary_ BOOLEAN
);

create table BlogsCategory (
	categoryId LONG primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCategoryId LONG,
	name VARCHAR(75) null,
	description STRING null
);

create table BlogsEntry (
	entryId LONG primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	categoryId LONG,
	title VARCHAR(75) null,
	content TEXT null,
	displayDate DATE null
);

create table BookmarksEntry (
	entryId LONG primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	folderId LONG,
	name VARCHAR(75) null,
	url STRING null,
	comments STRING null,
	visits INTEGER
);

create table BookmarksFolder (
	folderId LONG primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	parentFolderId LONG,
	name VARCHAR(75) null,
	description STRING null
);

create table CalEvent (
	eventId LONG primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(75) null,
	description STRING null,
	startDate DATE null,
	endDate DATE null,
	durationHour INTEGER,
	durationMinute INTEGER,
	allDay BOOLEAN,
	timeZoneSensitive BOOLEAN,
	type_ VARCHAR(75) null,
	repeating BOOLEAN,
	recurrence TEXT null,
	remindBy VARCHAR(75) null,
	firstReminder INTEGER,
	secondReminder INTEGER
);

create table ClassName_ (
	classNameId LONG primary key,
	value VARCHAR(75) null
);

create table Company (
	companyId LONG primary key,
	accountId LONG,
	webId VARCHAR(75) null,
	key_ TEXT null,
	portalURL VARCHAR(75) null,
	homeURL VARCHAR(75) null,
	mx VARCHAR(75) null
);

create table Contact_ (
	contactId LONG primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	accountId LONG,
	parentContactId LONG,
	firstName VARCHAR(75) null,
	middleName VARCHAR(75) null,
	lastName VARCHAR(75) null,
	prefixId INTEGER,
	suffixId INTEGER,
	male BOOLEAN,
	birthday DATE null,
	smsSn VARCHAR(75) null,
	aimSn VARCHAR(75) null,
	icqSn VARCHAR(75) null,
	jabberSn VARCHAR(75) null,
	msnSn VARCHAR(75) null,
	skypeSn VARCHAR(75) null,
	ymSn VARCHAR(75) null,
	employeeStatusId VARCHAR(75) null,
	employeeNumber VARCHAR(75) null,
	jobTitle VARCHAR(75) null,
	jobClass VARCHAR(75) null,
	hoursOfOperation VARCHAR(75) null
);

create table Counter (
	name VARCHAR(75) not null primary key,
	currentId LONG
);

create table Country (
	countryId LONG primary key,
	name VARCHAR(75) null,
	a2 VARCHAR(75) null,
	a3 VARCHAR(75) null,
	number_ VARCHAR(75) null,
	idd_ VARCHAR(75) null,
	active_ BOOLEAN
);

create table CyrusUser (
	userId LONG primary key,
	password_ VARCHAR(75) not null
);

create table CyrusVirtual (
	emailAddress VARCHAR(75) not null primary key,
	userId LONG
);

create table DLFileEntry (
	folderId VARCHAR(75) not null,
	name VARCHAR(100) not null,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	versionUserId LONG,
	versionUserName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(100) null,
	description STRING null,
	version DOUBLE,
	size_ INTEGER,
	readCount INTEGER,
	extraSettings TEXT null,
	primary key (folderId, name)
);

create table DLFileRank (
	companyId LONG,
	userId LONG,
	folderId VARCHAR(75) not null,
	name VARCHAR(100) not null,
	createDate DATE null,
	primary key (companyId, userId, folderId, name)
);

create table DLFileShortcut (
	fileShortcutId LONG primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	folderId VARCHAR(75) null,
	toFolderId VARCHAR(75) null,
	toName VARCHAR(75) null
);

create table DLFileVersion (
	folderId VARCHAR(75) not null,
	name VARCHAR(100) not null,
	version DOUBLE,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	size_ INTEGER,
	primary key (folderId, name, version)
);

create table DLFolder (
	folderId VARCHAR(75) not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentFolderId VARCHAR(75) null,
	name VARCHAR(100) null,
	description STRING null,
	lastPostDate DATE null
);

create table EmailAddress (
	emailAddressId LONG primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	address VARCHAR(75) null,
	typeId INTEGER,
	primary_ BOOLEAN
);

create table Group_ (
	groupId LONG primary key,
	companyId LONG,
	creatorUserId LONG,
	classNameId LONG,
	classPK LONG,
	parentGroupId LONG,
	liveGroupId LONG,
	name VARCHAR(75) null,
	description STRING null,
	type_ VARCHAR(75) null,
	friendlyURL VARCHAR(75) null,
	active_ BOOLEAN
);

create table Groups_Orgs (
	groupId LONG,
	organizationId LONG,
	primary key (groupId, organizationId)
);

create table Groups_Permissions (
	groupId LONG,
	permissionId LONG,
	primary key (groupId, permissionId)
);

create table Groups_Roles (
	groupId LONG,
	roleId LONG,
	primary key (groupId, roleId)
);

create table Groups_UserGroups (
	groupId LONG,
	userGroupId LONG,
	primary key (groupId, userGroupId)
);

create table IGFolder (
	folderId LONG primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	parentFolderId LONG,
	name VARCHAR(75) null,
	description STRING null
);

create table IGImage (
	imageId LONG primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	folderId LONG,
	description STRING null,
	height INTEGER,
	width INTEGER,
	size_ INTEGER
);

create table Image (
	imageId VARCHAR(200) not null primary key,
	modifiedDate DATE null,
	text_ TEXT null,
	type_ VARCHAR(75) null
);

create table JournalArticle (
	companyId LONG,
	groupId LONG,
	articleId VARCHAR(75) not null,
	version DOUBLE,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(75) null,
	description STRING null,
	content TEXT null,
	type_ VARCHAR(75) null,
	structureId VARCHAR(75) null,
	templateId VARCHAR(75) null,
	displayDate DATE null,
	approved BOOLEAN,
	approvedByUserId LONG,
	approvedByUserName VARCHAR(75) null,
	approvedDate DATE null,
	expired BOOLEAN,
	expirationDate DATE null,
	reviewDate DATE null,
	primary key (companyId, groupId, articleId, version)
);

create table JournalContentSearch (
	portletId VARCHAR(75) not null,
	layoutId VARCHAR(75) not null,
	ownerId VARCHAR(75) not null,
	articleId VARCHAR(75) not null,
	companyId LONG,
	groupId LONG,
	primary key (portletId, layoutId, ownerId, articleId)
);

create table JournalStructure (
	companyId LONG,
	groupId LONG,
	structureId VARCHAR(75) not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	xsd TEXT null,
	primary key (companyId, groupId, structureId)
);

create table JournalTemplate (
	companyId LONG,
	groupId LONG,
	templateId VARCHAR(75) not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	structureId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null,
	xsl TEXT null,
	langType VARCHAR(75) null,
	smallImage BOOLEAN,
	smallImageURL VARCHAR(75) null,
	primary key (companyId, groupId, templateId)
);

create table Layout (
	layoutId VARCHAR(75) not null,
	ownerId VARCHAR(75) not null,
	companyId LONG,
	parentLayoutId VARCHAR(75) null,
	name STRING null,
	title STRING null,
	type_ VARCHAR(75) null,
	typeSettings TEXT null,
	hidden_ BOOLEAN,
	friendlyURL VARCHAR(75) null,
	iconImage BOOLEAN,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	wapThemeId VARCHAR(75) null,
	wapColorSchemeId VARCHAR(75) null,
	css VARCHAR(75) null,
	priority INTEGER,
	primary key (layoutId, ownerId)
);

create table LayoutSet (
	ownerId VARCHAR(75) not null primary key,
	companyId LONG,
	groupId LONG,
	userId LONG,
	privateLayout BOOLEAN,
	logo BOOLEAN,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	wapThemeId VARCHAR(75) null,
	wapColorSchemeId VARCHAR(75) null,
	css VARCHAR(75) null,
	pageCount INTEGER,
	virtualHost VARCHAR(75) null
);

create table ListType (
	listTypeId INTEGER primary key,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null
);

create table MBBan (
	banId LONG primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	banUserId LONG
);

create table MBCategory (
	categoryId LONG primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCategoryId LONG,
	name VARCHAR(75) null,
	description STRING null,
	lastPostDate DATE null
);

create table MBDiscussion (
	discussionId LONG primary key,
	className VARCHAR(75) null,
	classPK VARCHAR(75) null,
	threadId LONG
);

create table MBMessage (
	messageId LONG primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	categoryId LONG,
	threadId LONG,
	parentMessageId LONG,
	subject VARCHAR(75) null,
	body TEXT null,
	attachments BOOLEAN,
	anonymous BOOLEAN
);

create table MBMessageFlag (
	messageFlagId LONG primary key,
	userId LONG,
	messageId LONG,
	flag INTEGER
);

create table MBStatsUser (
	statsUserId LONG primary key,
	groupId LONG,
	userId LONG,
	messageCount INTEGER,
	lastPostDate DATE null
);

create table MBThread (
	threadId LONG primary key,
	categoryId LONG,
	rootMessageId LONG,
	messageCount INTEGER,
	viewCount INTEGER,
	lastPostByUserId LONG,
	lastPostDate DATE null,
	priority DOUBLE
);

create table Organization_ (
	organizationId LONG primary key,
	companyId LONG,
	parentOrganizationId LONG,
	name VARCHAR(75) null,
	recursable BOOLEAN,
	regionId LONG,
	countryId LONG,
	statusId INTEGER,
	comments STRING null
);

create table OrgGroupPermission (
	organizationId LONG,
	groupId LONG,
	permissionId LONG,
	primary key (organizationId, groupId, permissionId)
);

create table OrgGroupRole (
	organizationId LONG,
	groupId LONG,
	roleId LONG,
	primary key (organizationId, groupId, roleId)
);

create table OrgLabor (
	orgLaborId LONG primary key,
	organizationId LONG,
	typeId INTEGER,
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

create table PasswordPolicy (
	passwordPolicyId LONG primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	defaultPolicy BOOLEAN,
	name VARCHAR(75) null,
	description STRING null,
	storageScheme VARCHAR(75) null,
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
	passwordPolicyRelId LONG primary key,
	passwordPolicyId LONG,
	classNameId LONG,
	classPK LONG
);

create table PasswordTracker (
	passwordTrackerId LONG primary key,
	userId LONG,
	createDate DATE null,
	password_ VARCHAR(75) null
);

create table Permission_ (
	permissionId LONG primary key,
	companyId LONG,
	actionId VARCHAR(75) null,
	resourceId LONG
);

create table Phone (
	phoneId LONG primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	number_ VARCHAR(75) null,
	extension VARCHAR(75) null,
	typeId INTEGER,
	primary_ BOOLEAN
);

create table PluginSetting (
	pluginSettingId LONG primary key,
	companyId LONG,
	pluginId VARCHAR(75) null,
	pluginType VARCHAR(75) null,
	roles VARCHAR(75) null,
	active_ BOOLEAN
);

create table PollsChoice (
	questionId LONG,
	choiceId VARCHAR(75) not null,
	description VARCHAR(75) null,
	primary key (questionId, choiceId)
);

create table PollsQuestion (
	questionId LONG primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(75) null,
	description STRING null,
	expirationDate DATE null,
	lastVoteDate DATE null
);

create table PollsVote (
	questionId LONG,
	userId LONG,
	choiceId VARCHAR(75) null,
	voteDate DATE null,
	primary key (questionId, userId)
);

create table Portlet (
	portletId VARCHAR(75) not null,
	companyId LONG,
	roles VARCHAR(75) null,
	active_ BOOLEAN,
	primary key (portletId, companyId)
);

create table PortletPreferences (
	portletId VARCHAR(75) not null,
	layoutId VARCHAR(75) not null,
	ownerId VARCHAR(75) not null,
	preferences TEXT null,
	primary key (portletId, layoutId, ownerId)
);

create table RatingsEntry (
	entryId LONG primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	className VARCHAR(75) null,
	classPK VARCHAR(75) null,
	score DOUBLE
);

create table RatingsStats (
	statsId LONG primary key,
	className VARCHAR(75) null,
	classPK VARCHAR(75) null,
	totalEntries INTEGER,
	totalScore DOUBLE,
	averageScore DOUBLE
);

create table Region (
	regionId LONG primary key,
	countryId LONG,
	regionCode VARCHAR(75) null,
	name VARCHAR(75) null,
	active_ BOOLEAN
);

create table Release_ (
	releaseId LONG primary key,
	createDate DATE null,
	modifiedDate DATE null,
	buildNumber INTEGER,
	buildDate DATE null,
	verified BOOLEAN
);

create table Resource_ (
	resourceId LONG primary key,
	codeId LONG,
	primKey VARCHAR(200) null
);

create table ResourceCode (
	codeId LONG primary key,
	companyId LONG,
	name VARCHAR(75) null,
	scope INTEGER
);

create table Role_ (
	roleId LONG primary key,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	name VARCHAR(75) null,
	description STRING null,
	type_ INTEGER
);

create table Roles_Permissions (
	roleId LONG,
	permissionId LONG,
	primary key (roleId, permissionId)
);

create table SCFrameworkVersi_SCProductVers (
	productVersionId LONG,
	frameworkVersionId LONG,
	primary key (productVersionId, frameworkVersionId)
);

create table SCFrameworkVersion (
	frameworkVersionId LONG primary key,
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
	licenseId LONG primary key,
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
	productEntryId LONG primary key,
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
	productVersionId LONG primary key,
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

create table ShoppingCart (
	cartId VARCHAR(75) not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	itemIds STRING null,
	couponIds STRING null,
	altShipping INTEGER,
	insure BOOLEAN
);

create table ShoppingCategory (
	categoryId VARCHAR(75) not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCategoryId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null
);

create table ShoppingCoupon (
	couponId VARCHAR(75) not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	startDate DATE null,
	endDate DATE null,
	active_ BOOLEAN,
	limitCategories STRING null,
	limitSkus STRING null,
	minOrder DOUBLE,
	discount DOUBLE,
	discountType VARCHAR(75) null
);

create table ShoppingItem (
	itemId VARCHAR(75) not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	categoryId VARCHAR(75) null,
	sku VARCHAR(75) null,
	name VARCHAR(200) null,
	description STRING null,
	properties STRING null,
	fields_ BOOLEAN,
	fieldsQuantities STRING null,
	minQuantity INTEGER,
	maxQuantity INTEGER,
	price DOUBLE,
	discount DOUBLE,
	taxable BOOLEAN,
	shipping DOUBLE,
	useShippingFormula BOOLEAN,
	requiresShipping BOOLEAN,
	stockQuantity INTEGER,
	featured_ BOOLEAN,
	sale_ BOOLEAN,
	smallImage BOOLEAN,
	smallImageURL VARCHAR(75) null,
	mediumImage BOOLEAN,
	mediumImageURL VARCHAR(75) null,
	largeImage BOOLEAN,
	largeImageURL VARCHAR(75) null
);

create table ShoppingItemField (
	itemFieldId VARCHAR(75) not null primary key,
	itemId VARCHAR(75) null,
	name VARCHAR(75) null,
	values_ STRING null,
	description STRING null
);

create table ShoppingItemPrice (
	itemPriceId VARCHAR(75) not null primary key,
	itemId VARCHAR(75) null,
	minQuantity INTEGER,
	maxQuantity INTEGER,
	price DOUBLE,
	discount DOUBLE,
	taxable BOOLEAN,
	shipping DOUBLE,
	useShippingFormula BOOLEAN,
	status INTEGER
);

create table ShoppingOrder (
	orderId VARCHAR(75) not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	tax DOUBLE,
	shipping DOUBLE,
	altShipping VARCHAR(75) null,
	requiresShipping BOOLEAN,
	insure BOOLEAN,
	insurance DOUBLE,
	couponIds VARCHAR(75) null,
	couponDiscount DOUBLE,
	billingFirstName VARCHAR(75) null,
	billingLastName VARCHAR(75) null,
	billingEmailAddress VARCHAR(75) null,
	billingCompany VARCHAR(75) null,
	billingStreet VARCHAR(75) null,
	billingCity VARCHAR(75) null,
	billingState VARCHAR(75) null,
	billingZip VARCHAR(75) null,
	billingCountry VARCHAR(75) null,
	billingPhone VARCHAR(75) null,
	shipToBilling BOOLEAN,
	shippingFirstName VARCHAR(75) null,
	shippingLastName VARCHAR(75) null,
	shippingEmailAddress VARCHAR(75) null,
	shippingCompany VARCHAR(75) null,
	shippingStreet VARCHAR(75) null,
	shippingCity VARCHAR(75) null,
	shippingState VARCHAR(75) null,
	shippingZip VARCHAR(75) null,
	shippingCountry VARCHAR(75) null,
	shippingPhone VARCHAR(75) null,
	ccName VARCHAR(75) null,
	ccType VARCHAR(75) null,
	ccNumber VARCHAR(75) null,
	ccExpMonth INTEGER,
	ccExpYear INTEGER,
	ccVerNumber VARCHAR(75) null,
	comments STRING null,
	ppTxnId VARCHAR(75) null,
	ppPaymentStatus VARCHAR(75) null,
	ppPaymentGross DOUBLE,
	ppReceiverEmail VARCHAR(75) null,
	ppPayerEmail VARCHAR(75) null,
	sendOrderEmail BOOLEAN,
	sendShippingEmail BOOLEAN
);

create table ShoppingOrderItem (
	orderId VARCHAR(75) not null,
	itemId VARCHAR(75) not null,
	sku VARCHAR(75) null,
	name VARCHAR(200) null,
	description STRING null,
	properties STRING null,
	price DOUBLE,
	quantity INTEGER,
	shippedDate DATE null,
	primary key (orderId, itemId)
);

create table Subscription (
	subscriptionId LONG primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	frequency VARCHAR(75) null
);

create table TagsAsset (
	assetId LONG primary key,
	companyId LONG,
	userId LONG,
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
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null
);

create table TagsProperty (
	propertyId LONG primary key,
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
	sourceId LONG primary key,
	parentSourceId LONG,
	name VARCHAR(75) null,
	acronym VARCHAR(75) null
);

create table User_ (
	userId LONG primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	defaultUser BOOLEAN,
	contactId LONG,
	password_ VARCHAR(75) null,
	passwordEncrypted BOOLEAN,
	passwordExpirationDate DATE null,
	passwordReset BOOLEAN,
	passwordModifiedDate DATE null,
	graceLoginCount INTEGER,
	screenName VARCHAR(75) null,
	emailAddress VARCHAR(75) null,
	languageId VARCHAR(75) null,
	timeZoneId VARCHAR(75) null,
	greeting VARCHAR(75) null,
	comments STRING null,
	loginDate DATE null,
	loginIP VARCHAR(75) null,
	lastLoginDate DATE null,
	lastLoginIP VARCHAR(75) null,
	lastFailedLoginDate DATE null,
	failedLoginAttempts INTEGER,
	lockout BOOLEAN,
	lockoutDate DATE null,
	agreedToTermsOfUse BOOLEAN,
	active_ BOOLEAN
);

create table UserGroup (
	userGroupId LONG primary key,
	companyId LONG,
	parentUserGroupId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null
);

create table UserGroupRole (
	userId LONG,
	groupId LONG,
	roleId LONG,
	primary key (userId, groupId, roleId)
);

create table UserIdMapper (
	userIdMapperId LONG primary key,
	userId LONG,
	type_ VARCHAR(75) null,
	description VARCHAR(75) null,
	externalUserId VARCHAR(75) null
);

create table Users_Groups (
	userId LONG,
	groupId LONG,
	primary key (userId, groupId)
);

create table Users_Orgs (
	userId LONG,
	organizationId LONG,
	primary key (userId, organizationId)
);

create table Users_Permissions (
	userId LONG,
	permissionId LONG,
	primary key (userId, permissionId)
);

create table Users_Roles (
	userId LONG,
	roleId LONG,
	primary key (userId, roleId)
);

create table Users_UserGroups (
	userId LONG,
	userGroupId LONG,
	primary key (userId, userGroupId)
);

create table UserTracker (
	userTrackerId LONG primary key,
	companyId LONG,
	userId LONG,
	modifiedDate DATE null,
	sessionId VARCHAR(200) null,
	remoteAddr VARCHAR(75) null,
	remoteHost VARCHAR(75) null,
	userAgent VARCHAR(200) null
);

create table UserTrackerPath (
	userTrackerPathId LONG primary key,
	userTrackerId LONG,
	path STRING null,
	pathDate DATE null
);

create table Website (
	websiteId LONG primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	url VARCHAR(75) null,
	typeId INTEGER,
	primary_ BOOLEAN
);

create table WikiNode (
	nodeId LONG primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	lastPostDate DATE null
);

create table WikiPage (
	pageId LONG primary key,
	resourcePrimKey LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	nodeId LONG,
	title VARCHAR(75) null,
	version DOUBLE,
	content TEXT null,
	format VARCHAR(75) null,
	head BOOLEAN
);

create table WikiPageResource (
	resourcePrimKey LONG primary key,
	nodeId LONG,
	title VARCHAR(75) null
);