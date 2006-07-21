create table Account (
	accountId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentAccountId VARCHAR(100) null,
	name VARCHAR(100) null,
	legalName VARCHAR(100) null,
	legalId VARCHAR(100) null,
	legalType VARCHAR(100) null,
	sicCode VARCHAR(100) null,
	tickerSymbol VARCHAR(100) null,
	industry VARCHAR(100) null,
	type_ VARCHAR(100) null,
	size_ VARCHAR(100) null,
	website VARCHAR(100) null,
	emailAddress1 VARCHAR(100) null,
	emailAddress2 VARCHAR(100) null
);

alter table Address add pager VARCHAR(100) null;
alter table Address add tollFree VARCHAR(100) null;

drop table BlogsReferer;
create table BlogsReferer (
	refererId VARCHAR(100) not null primary key,
	entryId VARCHAR(100) null,
	url STRING null,
	type_ VARCHAR(100) null,
	quantity INTEGER
);

alter table BookmarksEntry add groupId VARCHAR(100) not null default '';
alter table BookmarksEntry add companyId VARCHAR(100) not null default '';
update BookmarksEntry set groupId = '-1';
update BookmarksEntry set companyId = 'liferay.com';

alter table BookmarksFolder add groupId VARCHAR(100) not null default '';
alter table BookmarksFolder add companyId VARCHAR(100) not null default '';
update BookmarksFolder set groupId = '-1';
update BookmarksFolder set companyId = 'liferay.com';

create table ColorScheme (
	colorSchemeId VARCHAR(100) not null primary key,
	settings_ TEXT null
);

create table Contact (
	contactId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentContactId VARCHAR(100) null,
	firstName VARCHAR(100) null,
	middleName VARCHAR(100) null,
	lastName VARCHAR(100) null,
	nickName VARCHAR(100) null,
	emailAddress1 VARCHAR(100) null,
	emailAddress2 VARCHAR(100) null,
	smsId VARCHAR(100) null,
	aimId VARCHAR(100) null,
	icqId VARCHAR(100) null,
	msnId VARCHAR(100) null,
	skypeId VARCHAR(100) null,
	ymId VARCHAR(100) null,
	website VARCHAR(100) null,
	male BOOLEAN,
	birthday DATE null,
	timeZoneId VARCHAR(100) null,
	employeeNumber VARCHAR(100) null,
	jobTitle VARCHAR(100) null,
	jobClass VARCHAR(100) null,
	hoursOfOperation STRING null
);

alter table Group_ add friendlyURL VARCHAR(100) null;
alter table Group_ add themeId VARCHAR(100) null;
alter table Group_ add colorSchemeId VARCHAR(100) null;
update Group_ set themeId = 'classic';
update Group_ set colorSchemeId = '01';

update JournalArticle set displayDate = CURRENT_TIMESTAMP where displayDate is NULL;

alter table Image add modifiedDate DATE null;
update Image set modifiedDate = CURRENT_TIMESTAMP;

alter table Layout add companyId VARCHAR(100) null;
alter table Layout add parentLayoutId VARCHAR(100) null;
alter table Layout add type_ STRING null;
alter table Layout add typeSettings STRING null;
alter table Layout add friendlyURL VARCHAR(100) null;
alter table Layout add priority INTEGER null;
update Layout set parentLayoutId = '-1' where parentLayoutId is NULL;
update Layout set type_ = 'portlet' where type_ is NULL;

drop table Portlet;
create table Portlet (
	portletId VARCHAR(100) not null,
	companyId VARCHAR(100) not null,
	narrow BOOLEAN,
	roles STRING null,
	active_ BOOLEAN,
	primary key (portletId, companyId)
);

create table Properties (
	companyId VARCHAR(100) not null,
	type_ VARCHAR(100) not null,
	properties TEXT null,
	primary key (companyId, type_)
);

alter table ShoppingCart add insure BOOLEAN null;

alter table ShoppingOrder add insure BOOLEAN null;

alter table User_ add themeId VARCHAR(100) null;
alter table User_ add colorSchemeId VARCHAR(100) null;
update User_ set themeId = 'classic';
update User_ set colorSchemeId = '01';