alter table ABContact add middleName VARCHAR(100) null;
alter table ABContact add nickName VARCHAR(100) null;
alter table ABContact add homePager VARCHAR(100) null;
alter table ABContact add homeTollFree VARCHAR(100) null;
alter table ABContact add homeEmailAddress VARCHAR(100) null;
alter table ABContact add businessPager VARCHAR(100) null;
alter table ABContact add businessTollFree VARCHAR(100) null;
alter table ABContact add businessEmailAddress VARCHAR(100) null;
alter table ABContact add employeeNumber VARCHAR(100) null;
alter table ABContact add jobTitle VARCHAR(100) null;
alter table ABContact add jobClass VARCHAR(100) null;
alter table ABContact add hoursOfOperation STRING null;
alter table ABContact add timeZoneId VARCHAR(100) null;
alter table ABContact add comments STRING null;

alter table BlogsComments add content TEXT null;

alter table BlogsEntry add title VARCHAR(100) null;
alter table BlogsEntry add displayDate DATE null;
alter table BlogsEntry add propsCount INTEGER;
alter table BlogsEntry add commentsCount INTEGER;

create table BlogsUser (
	userId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	entryId VARCHAR(100) not null,
	lastPostDate DATE null
);

alter table JournalArticle add version DOUBLE not null;
alter table JournalArticle add type_ VARCHAR(100) null;
alter table JournalArticle add structureId VARCHAR(100) null;
alter table JournalArticle add templateId VARCHAR(100) null;
alter table JournalArticle drop primary key;
alter table JournalArticle add primary key (articleId, version);

create table JournalStructure (
	structureId VARCHAR(100) not null primary key,
	portletId VARCHAR(100) not null,
	groupId VARCHAR(100) not null,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(100) null,
	description STRING null,
	xsd TEXT null
);

create table JournalTemplate (
	templateId VARCHAR(100) not null primary key,
	portletId VARCHAR(100) not null,
	groupId VARCHAR(100) not null,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	modifiedDate DATE null,
	structureId VARCHAR(100) null,
	name VARCHAR(100) null,
	description STRING null,
	xsl TEXT null,
	smallImage BOOLEAN,
	smallImageURL VARCHAR(100) null
);

create table MailReceipt (
	receiptId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	createDate DATE null,
	modifiedDate DATE null,
	recipientName VARCHAR(100) null,
	recipientAddress VARCHAR(100) null,
	subject VARCHAR(100) null,
	sentDate DATE null,
	readCount INTEGER,
	firstReadDate DATE null,
	lastReadDate DATE null
);

alter table MBThread add messageCount INTEGER;
alter table MBThread add lastPostDate DATE null;

alter table ShoppingCart add couponIds STRING null;

create table ShoppingCoupon (
	couponId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(100) null,
	description STRING null,
	startDate DATE null,
	endDate DATE null,
	active_ BOOLEAN,
	limitCategories STRING null,
	limitSkus STRING null,
	minOrder DOUBLE,
	discount DOUBLE,
	discountType VARCHAR(100) null
);

alter table ShoppingOrder add couponIds STRING null;
alter table ShoppingOrder add couponDiscount DOUBLE;
alter table ShoppingOrder add comments TEXT null;

alter table User_ add nickName VARCHAR(100) null;