create table GroupFriendlyURL (
	uuid_ VARCHAR(75) null,
	groupFriendlyURLId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	groupId LONG,
	friendlyURL VARCHAR(75) null,
	languageId VARCHAR(75) null,
	lastPublishDate DATE null
);

create table Site_GroupFriendlyURL (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	groupFriendlyURLId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	groupId LONG,
	friendlyURL VARCHAR(75) null,
	languageId VARCHAR(75) null,
	lastPublishDate DATE null
);