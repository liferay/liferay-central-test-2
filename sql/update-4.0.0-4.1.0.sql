alter table Contact_ add jabberSn VARCHAR(75) null;

alter table Group_ add type_ VARCHAR(75) null;

create table Groups_Orgs (
	groupId VARCHAR(75) not null,
	organizationId VARCHAR(75) not null,
	primary key (groupId, organizationId)
);

create table Groups_UserGroups (
	groupId VARCHAR(75) not null,
	userGroupId VARCHAR(75) not null,
	primary key (groupId, userGroupId)
);

create table MBStatsUser (
	groupId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	messageCount INTEGER,
	lastPostDate DATE null,
	primary key (groupId, userId)
);

alter table MBThread add viewCount INTEGER;

drop table Properties;

alter table Role_ add description VARCHAR(75) null;

create table UserGroup (
	userGroupId VARCHAR(75) not null primary key,
	companyId VARCHAR(75) not null,
	parentUserGroupId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null
);

create table Users_UserGroups (
	userId VARCHAR(75) not null,
	userGroupId VARCHAR(75) not null,
	primary key (userId, userGroupId)
);