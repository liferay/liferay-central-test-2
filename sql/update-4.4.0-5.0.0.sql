drop table ActivityTracker;

create table AnnouncementsDelivery (
	deliveryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	type_ VARCHAR(75) null,
	email BOOLEAN,
	sms BOOLEAN,
	website BOOLEAN
);

create table AnnouncementsEntry (
	uuid_ VARCHAR(75) null,
	entryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	title VARCHAR(75) null,
	content STRING null,
	url STRING null,
	type_ VARCHAR(75) null,
	displayDate DATE null,
	expirationDate DATE null,
	priority INTEGER,
	alert BOOLEAN
);

create table AnnouncementsFlag (
	flagId LONG not null primary key,
	userId LONG,
	createDate DATE null,
	entryId LONG,
	value INTEGER
);

create table ExpandoColumn (
	columnId LONG not null primary key,
	tableId LONG,
	name VARCHAR(75) null,
	type_ INTEGER
);

create table ExpandoRow (
	rowId_ LONG not null primary key,
	tableId LONG,
	classPK LONG
);

create table ExpandoTable (
	tableId LONG not null primary key,
	classNameId LONG,
	name VARCHAR(75) null
);

create table ExpandoValue (
	valueId LONG not null primary key,
	tableId LONG,
	columnId LONG,
	rowId_ LONG,
	classNameId LONG,
	classPK LONG,
	data_ VARCHAR(75) null
);

alter table IGImage add name VARCHAR(75) null;
alter table IGImage add custom1ImageId LONG null;
alter table IGImage add custom2ImageId LONG null;

update Group_ set type_ = 3 where type_ = 0;

update Image set type_ = 'jpg' where type_ = 'jpeg';

create table PortletItem (
	portletItemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	portletId VARCHAR(75) null,
	classNameId LONG
);

create table SocialActivity (
	activityId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	type_ VARCHAR(75) null,
	extraData TEXT null,
	receiverUserId LONG
);

create table SocialRelation (
	uuid_ VARCHAR(75) null,
	relationId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	userId1 LONG,
	userId2 LONG,
	type_ INTEGER
);

create table TasksProposal (
	proposalId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null,
	publishDate DATE null,
	dueDate DATE null
);

create table TasksReview (
	reviewId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	proposalId LONG,
	assignedByUserId LONG,
	assignedByUserName VARCHAR(75) null,
	stage INTEGER,
	completed BOOLEAN,
	rejected BOOLEAN
);

alter table WikiPage add parentTitle VARCHAR(75) null;
alter table WikiPage add redirectTitle VARCHAR(75) null;