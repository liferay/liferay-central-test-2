update Group_ set name = classPK where classPK > 0 and name = '';

alter table JournalArticle add urlTitle VARCHAR(150) null;

COMMIT_TRANSACTION;

update JournalArticle set urlTitle = articleId;

alter table MBCategory add threadCount INTEGER;
alter table MBCategory add messageCount INTEGER;

alter table MBMessage add groupId LONG;

alter table MBThread add groupId LONG;

update Region set regionCode = 'AB' where countryId = 1 and name = 'Alberta';

create table ResourceAction (
	resourceActionId LONG not null primary key,
	name VARCHAR(75) null,
	actionId VARCHAR(75) null,
	bitwiseValue LONG
);

create table ResourcePermission (
	resourcePermissionId LONG not null primary key,
	resourceId LONG,
	roleId LONG,
	actionIds LONG
);

create table Shard (
	shardId LONG not null primary key,
	classNameId LONG,
	classPK LONG,
	name VARCHAR(75) null
);