alter table CalEvent add groupId VARCHAR(100) not null;
update CalEvent set groupId = '-1';

create table DLFileRank (
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	repositoryId VARCHAR(100) not null,
	fileName VARCHAR(100) not null,
	createDate DATE null,
	primary key (companyId, userId, repositoryId, fileName)
);

alter table DLRepository add groupId VARCHAR(100) not null;
update DLRepository set groupId = '-1';

alter table IGFolder add groupId VARCHAR(100) not null;
update IGFolder set groupId = '-1';

alter table MBTopic add portletId VARCHAR(100) not null;
alter table MBTopic add groupId VARCHAR(100) not null;
update MBTopic set portletId = '19';
update MBTopic set groupId = '-1';

alter table JournalArticle add portletId VARCHAR(100) not null;
alter table JournalArticle add groupId VARCHAR(100) not null;
update JournalArticle set portletId = '15';
update JournalArticle set groupId = '-1';

alter table ShoppingItem add supplierUserId VARCHAR(100) null;
alter table ShoppingItem add featured_ BOOLEAN;
alter table ShoppingItem add sale_ BOOLEAN;
update ShoppingItem set featured_ = TRUE;
update ShoppingItem set sale_ = TRUE;

alter table ShoppingOrderItem add supplierUserId VARCHAR(100) null;

create table PasswordTracker (
	passwordTrackerId VARCHAR(100) not null primary key,
	userId VARCHAR(100) not null,
	createDate DATE not null,
	password_ VARCHAR(100) not null
);

create table PollsDisplay (
	layoutId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	portletId VARCHAR(100) not null,
	questionId VARCHAR(100) not null,
	primary key (layoutId, userId, portletId)
);

alter table PollsQuestion add portletId VARCHAR(100) not null;
alter table PollsQuestion add groupId VARCHAR(100) not null;
alter table PollsQuestion add expirationDate DATE null;
update PollsQuestion set portletId = '25';
update PollsQuestion set groupId = '-1';

alter table User_ add passwordReset BOOLEAN;

create table WikiDisplay (
	layoutId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	portletId VARCHAR(100) not null,
	nodeId VARCHAR(100) not null,
	showBorders BOOLEAN,
	primary key (layoutId, userId, portletId)
);