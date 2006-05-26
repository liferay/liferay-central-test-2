create table ABContact (
	contactId VARCHAR(75) not null primary key,
	userId VARCHAR(75) not null,
	firstName VARCHAR(75) null,
	middleName VARCHAR(75) null,
	lastName VARCHAR(75) null,
	nickName VARCHAR(75) null,
	emailAddress VARCHAR(75) null,
	homeStreet VARCHAR(75) null,
	homeCity VARCHAR(75) null,
	homeState VARCHAR(75) null,
	homeZip VARCHAR(75) null,
	homeCountry VARCHAR(75) null,
	homePhone VARCHAR(75) null,
	homeFax VARCHAR(75) null,
	homeCell VARCHAR(75) null,
	homePager VARCHAR(75) null,
	homeTollFree VARCHAR(75) null,
	homeEmailAddress VARCHAR(75) null,
	businessCompany VARCHAR(75) null,
	businessStreet VARCHAR(75) null,
	businessCity VARCHAR(75) null,
	businessState VARCHAR(75) null,
	businessZip VARCHAR(75) null,
	businessCountry VARCHAR(75) null,
	businessPhone VARCHAR(75) null,
	businessFax VARCHAR(75) null,
	businessCell VARCHAR(75) null,
	businessPager VARCHAR(75) null,
	businessTollFree VARCHAR(75) null,
	businessEmailAddress VARCHAR(75) null,
	employeeNumber VARCHAR(75) null,
	jobTitle VARCHAR(75) null,
	jobClass VARCHAR(75) null,
	hoursOfOperation VARCHAR(75) null,
	birthday DATE null,
	timeZoneId VARCHAR(75) null,
	instantMessenger VARCHAR(75) null,
	website VARCHAR(75) null,
	comments VARCHAR(75) null
);

create table ABContacts_ABLists (
	contactId VARCHAR(75) not null,
	listId VARCHAR(75) not null,
	primary key (contactId, listId)
);

create table ABList (
	listId VARCHAR(75) not null primary key,
	userId VARCHAR(75) not null,
	name VARCHAR(75) null
);

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

create table Address (
	addressId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	className VARCHAR(75) null,
	classPK VARCHAR(75) null,
	street1 VARCHAR(75) null,
	street2 VARCHAR(75) null,
	street3 VARCHAR(75) null,
	city VARCHAR(75) null,
	zip VARCHAR(75) null,
	regionId VARCHAR(75) null,
	countryId VARCHAR(75) null,
	typeId VARCHAR(75) null,
	mailing BOOLEAN,
	primary_ BOOLEAN
);

create table BlogsCategory (
	categoryId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCategoryId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null
);

create table BlogsEntry (
	entryId VARCHAR(75) not null primary key,
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	categoryId VARCHAR(75) null,
	title VARCHAR(75) null,
	content TEXT null,
	displayDate DATE null
);

create table BookmarksEntry (
	entryId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	createDate DATE null,
	modifiedDate DATE null,
	folderId VARCHAR(75) null,
	name VARCHAR(75) null,
	url STRING null,
	comments STRING null,
	visits INTEGER
);

create table BookmarksFolder (
	folderId VARCHAR(75) not null primary key,
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	createDate DATE null,
	modifiedDate DATE null,
	parentFolderId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null
);

create table CalEvent (
	eventId VARCHAR(75) not null primary key,
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
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

create table Company (
	companyId VARCHAR(75) not null primary key,
	key_ TEXT null,
	portalURL VARCHAR(75) null,
	homeURL VARCHAR(75) null,
	mx VARCHAR(75) null
);

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

create table Counter (
	name VARCHAR(75) not null primary key,
	currentId INTEGER
);

create table Country (
	countryId VARCHAR(75) not null primary key,
	countryCode VARCHAR(75) null,
	name VARCHAR(75) null,
	active_ BOOLEAN
);

create table CyrusUser (
	userId VARCHAR(75) not null primary key,
	password_ VARCHAR(75) not null
);

create table CyrusVirtual (
	emailAddress VARCHAR(75) not null primary key,
	userId VARCHAR(75) not null
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

create table DLFileRank (
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	folderId VARCHAR(75) not null,
	name VARCHAR(100) not null,
	createDate DATE null,
	primary key (companyId, userId, folderId, name)
);

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

create table Group_ (
	groupId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	className VARCHAR(75) null,
	classPK VARCHAR(75) null,
	parentGroupId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null,
	friendlyURL VARCHAR(75) null
);

create table Groups_Permissions (
	groupId  VARCHAR(75) not null,
	permissionId VARCHAR(75) not null,
	primary key (groupId, permissionId)
);

create table Groups_Roles (
	groupId VARCHAR(75) not null,
	roleId VARCHAR(75) not null,
	primary key (groupId, roleId)
);

create table IGFolder (
	folderId VARCHAR(75) not null primary key,
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	createDate DATE null,
	modifiedDate DATE null,
	parentFolderId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null
);

create table IGImage (
	companyId VARCHAR(75) not null,
	imageId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	createDate DATE null,
	modifiedDate DATE null,
	folderId VARCHAR(75) null,
	description STRING null,
	height INTEGER,
	width INTEGER,
	size_ INTEGER,
	primary key (companyId, imageId)
);

create table Image (
	imageId VARCHAR(200) not null primary key,
	modifiedDate DATE null,
	text_ TEXT null
);

create table JournalArticle (
	companyId VARCHAR(75) not null,
	articleId VARCHAR(75) not null,
	version DOUBLE not null,
	groupId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(75) null,
	content TEXT null,
	type_ VARCHAR(75) null,
	structureId VARCHAR(75) null,
	templateId VARCHAR(75) null,
	displayDate DATE null,
	approved BOOLEAN,
	approvedByUserId VARCHAR(75) null,
	approvedByUserName VARCHAR(75) null,
	approvedDate DATE null,
	expired BOOLEAN,
	expirationDate DATE null,
	reviewDate DATE null,
	primary key (companyId, articleId, version)
);

create table JournalContentSearch (
	portletId VARCHAR(75) not null,
	layoutId VARCHAR(75) not null,
	ownerId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	articleId VARCHAR(75) null,
	primary key (portletId, layoutId, ownerId)
);

create table JournalStructure (
	companyId VARCHAR(75) not null,
	structureId VARCHAR(75) not null,
	groupId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	xsd TEXT null,
	primary key (companyId, structureId)
);

create table JournalTemplate (
	companyId VARCHAR(75) not null,
	templateId VARCHAR(75) not null,
	groupId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
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
	primary key (companyId, templateId)
);

create table Layout (
	layoutId VARCHAR(75) not null,
	ownerId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	parentLayoutId VARCHAR(75) null,
	name STRING null,
	type_ VARCHAR(75) null,
	typeSettings TEXT null,
	hidden_ BOOLEAN,
	friendlyURL VARCHAR(75) null,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	priority INTEGER,
	primary key (layoutId, ownerId)
);

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

create table MailReceipt (
	receiptId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	createDate DATE null,
	modifiedDate DATE null,
	recipientName VARCHAR(75) null,
	recipientAddress VARCHAR(75) null,
	subject VARCHAR(75) null,
	sentDate DATE null,
	readCount INTEGER,
	firstReadDate DATE null,
	lastReadDate DATE null
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

create table MBMessage (
	topicId VARCHAR(75) not null,
	messageId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	threadId VARCHAR(75) null,
	parentMessageId VARCHAR(75) null,
	subject VARCHAR(75) null,
	body TEXT null,
	attachments BOOLEAN,
	anonymous BOOLEAN,
	primary key (topicId, messageId)
);

create table MBMessageFlag (
	topicId VARCHAR(75) not null,
	messageId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	flag VARCHAR(75) null,
	primary key (topicId, messageId, userId)
);

create table MBThread (
	threadId VARCHAR(75) not null primary key,
	rootMessageId VARCHAR(75) null,
	topicId VARCHAR(75) null,
	messageCount INTEGER,
	lastPostDate DATE null
);

create table MBTopic (
	topicId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	categoryId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null,
	lastPostDate DATE null
);

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

create table PasswordTracker (
	passwordTrackerId VARCHAR(75) not null primary key,
	userId VARCHAR(75) not null,
	createDate DATE null,
	password_ VARCHAR(75) null
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

create table PollsChoice (
	questionId VARCHAR(75) not null,
	choiceId VARCHAR(75) not null,
	description VARCHAR(75) null,
	primary key (questionId, choiceId)
);

create table PollsQuestion (
	questionId VARCHAR(75) not null primary key,
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(75) null,
	description STRING null,
	expirationDate DATE null,
	lastVoteDate DATE null
);

create table PollsVote (
	questionId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	choiceId VARCHAR(75) null,
	voteDate DATE null,
	primary key (questionId, userId)
);

create table Portlet (
	portletId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	narrow BOOLEAN,
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

create table Properties (
	companyId VARCHAR(75) not null,
	type_ VARCHAR(75) not null,
	properties TEXT null,
	primary key (companyId, type_)
);

create table Region (
	regionId VARCHAR(75) not null primary key,
	countryId VARCHAR(75) null,
	regionCode VARCHAR(75) null,
	name VARCHAR(75) null,
	active_ BOOLEAN
);

create table Release_ (
	releaseId VARCHAR(75) not null primary key,
	createDate DATE null,
	modifiedDate DATE null,
	buildNumber INTEGER,
	buildDate DATE null
);

create table Resource_ (
	resourceId VARCHAR(25) not null primary key,
	companyId VARCHAR(25) not null,
	name VARCHAR(75) null,
	typeId VARCHAR(15) null,
	scope VARCHAR(15) null,
	primKey VARCHAR(200) null
);

create table Role_ (
	roleId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	className VARCHAR(75) null,
	classPK VARCHAR(75) null,
	name VARCHAR(75) null
);

create table Roles_Permissions (
	roleId VARCHAR(75) not null,
	permissionId VARCHAR(75) not null,
	primary key (roleId, permissionId)
);

create table ShoppingCart (
	cartId VARCHAR(75) not null primary key,
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
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

create table ShoppingCoupon (
	couponId VARCHAR(75) not null primary key,
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
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
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
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
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
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

create table User_ (
	userId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	createDate DATE null,
	modifiedDate DATE null,
	contactId VARCHAR(75) null,
	password_ VARCHAR(75) null,
	passwordEncrypted BOOLEAN,
	passwordExpirationDate DATE null,
	passwordReset BOOLEAN,
	emailAddress VARCHAR(75) null,
	languageId VARCHAR(75) null,
	timeZoneId VARCHAR(75) null,
	greeting VARCHAR(75) null,
	resolution VARCHAR(75) null,
	comments STRING null,
	loginDate DATE null,
	loginIP VARCHAR(75) null,
	lastLoginDate DATE null,
	lastLoginIP VARCHAR(75) null,
	failedLoginAttempts INTEGER,
	agreedToTermsOfUse BOOLEAN,
	active_ BOOLEAN
);

create table UserIdMapper (
	userId VARCHAR(75) not null,
	type_ VARCHAR(75) not null,
	description VARCHAR(75) null,
	externalUserId VARCHAR(75) null,
	primary key (userId, type_)
);

create table Users_Groups (
	userId VARCHAR(75) not null,
	groupId VARCHAR(75) not null,
	primary key (userId, groupId)
);

create table Users_Orgs (
	userId VARCHAR(75) not null,
	organizationId VARCHAR(75) not null,
	primary key (userId, organizationId)
);

create table Users_Permissions (
	userId VARCHAR(75) not null,
	permissionId VARCHAR(75) not null,
	primary key (userId, permissionId)
);

create table Users_Roles (
	userId VARCHAR(75) not null,
	roleId VARCHAR(75) not null,
	primary key (userId, roleId)
);

create table UserTracker (
	userTrackerId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	modifiedDate DATE null,
	remoteAddr VARCHAR(75) null,
	remoteHost VARCHAR(75) null,
	userAgent VARCHAR(75) null
);

create table UserTrackerPath (
	userTrackerPathId VARCHAR(75) not null primary key,
	userTrackerId VARCHAR(75) null,
	path STRING null,
	pathDate DATE null
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

create table WikiNode (
	nodeId VARCHAR(75) not null primary key,
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	lastPostDate DATE null
);

create table WikiPage (
	nodeId VARCHAR(75) not null,
	title VARCHAR(75) not null,
	version DOUBLE not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	content TEXT null,
	format VARCHAR(75) null,
	head BOOLEAN,
	primary key (nodeId, title, version)
);