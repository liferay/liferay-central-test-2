alter table Address add companyId VARCHAR(100) not null;
alter table Address add userName VARCHAR(100) null;
alter table Address add createDate DATE null;
alter table Address add modifiedDate DATE null;
alter table Address add className VARCHAR(100) null;
alter table Address add classPK VARCHAR(100) null;
update Address set companyId = 'liferay.com';
update Address set userName = '';
update Address set createDate = CURRENT_TIMESTAMP;
update Address set modifiedDate = CURRENT_TIMESTAMP;
update Address set className = 'com.liferay.portal.model.User';
update Address set classPK = userId;

create table CyrusUser (
	userId varchar(100) not null primary key,
	password_ varchar(100) not null
);

create table CyrusVirtual (
	emailAddress varchar(100) not null primary key,
	userId varchar(100) not null
);

alter table Group_ add parentGroupId VARCHAR(100) null;
update Group_ set parentGroupId = '-1';

alter table Guestbook add modifiedDate DATE null;

alter table Layout add stateMax VARCHAR(100) null;
alter table Layout add stateMin VARCHAR(100) null;
alter table Layout add modeEdit VARCHAR(100) null;
alter table Layout add modeHelp VARCHAR(100) null;

create table MBMessageFlag (
	topicId VARCHAR(100) not null,
	messageId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	flag VARCHAR(100) null,
	primary key (topicId, messageId, userId)
);

drop table Portlet;
create table Portlet (
	portletId VARCHAR(100) not null,
	groupId VARCHAR(100) not null,
	companyId VARCHAR(100) not null,
	defaultPreferences TEXT null,
	narrow BOOLEAN,
	roles STRING null,
	active_ BOOLEAN,
	primary key (portletId, groupId, companyId)
);

drop table PortletPreference;
create table PortletPreferences (
	portletId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	layoutId VARCHAR(100) not null,
	preferences TEXT null,
	primary key (portletId, userId, layoutId)
);

alter table ShoppingItem add fields_ BOOLEAN;
alter table ShoppingItem add fieldsQuantities STRING null;

create table ShoppingItemField (
	itemFieldId VARCHAR(100) not null primary key,
	itemId VARCHAR(100) null,
	name VARCHAR(100) null,
	values_ STRING null,
	description STRING null
);

alter table ShoppingOrder add modifiedDate DATE null;
alter table ShoppingOrder add ccName VARCHAR(100) null;
alter table ShoppingOrder add ccType VARCHAR(100) null;
alter table ShoppingOrder add ccNumber VARCHAR(100) null;
alter table ShoppingOrder add ccExpMonth INTEGER;
alter table ShoppingOrder add ccExpYear INTEGER;
alter table ShoppingOrder add ccVerNumber VARCHAR(100) null;
alter table ShoppingOrder add sendOrderEmail BOOLEAN;
alter table ShoppingOrder add sendShippingEmail BOOLEAN;
update ShoppingOrder set modifiedDate = createDate;
update ShoppingOrder set sendOrderEmail = FALSE;
update ShoppingOrder set sendShippingEmail = FALSE;

alter table User_ add passwordEncrypted BOOLEAN;
alter table User_ add passwordExpirationDate DATE null;