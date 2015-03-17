create table App (
	uuid_ VARCHAR(75) null,
	appId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	remoteAppId LONG,
	title VARCHAR(75) null,
	description VARCHAR(75) null,
	category VARCHAR(75) null,
	iconURL VARCHAR(75) null,
	version VARCHAR(75) null
);

create table Module (
	uuid_ VARCHAR(75) null,
	moduleId LONG not null primary key,
	appId LONG,
	bundleSymbolicName VARCHAR(75) null,
	bundleVersion VARCHAR(75) null,
	contextName VARCHAR(75) null
);