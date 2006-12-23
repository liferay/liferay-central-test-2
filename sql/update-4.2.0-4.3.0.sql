alter_column_type Address addressId BIGINT not null;

alter_column_type Counter currentId BIGINT not null;

alter_column_type EmailAddress emailAddressId BIGINT not null;

alter_column_type Phone phoneId BIGINT not null;

alter table Release_ add verified BOOLEAN null;
update table Release_ set verified = false;

create table SRFrameworkVersion (
	frameworkVersionId INTEGER not null primary key,
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	url VARCHAR(1024) null,
	active_ BOOLEAN,
	priority INTEGER
);

create table SRLicense (
	licenseId INTEGER not null primary key,
	name VARCHAR(75) null,
	openSource BOOLEAN,
	url VARCHAR(1024) null,
	active_ BOOLEAN,
	recommended BOOLEAN
);

create table SRProductEntry (
	productEntryId INTEGER not null primary key,
	groupId VARCHAR(75) not null,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null,
	shortDescription STRING null,
	longDescription STRING null,
	pageURL VARCHAR(1024) null,
	repoGroupId VARCHAR(75) null,
	repoArtifactId VARCHAR(75) null
);

create table SRLicenses_SRProductEntries (
	productEntryId INTEGER not null,
	licenseId INTEGER not null,
	primary key (productEntryId, licenseId)
);

create table SRFrameworkVersions_SRProductVersions (
	productVersionId INTEGER not null,
	frameworkVersionId INTEGER not null,
	primary key (productVersionId, frameworkVersionId)
);

create table SRProductVersion (
	productVersionId INTEGER not null primary key,
	companyId VARCHAR(75) not null,
	userId VARCHAR(75) not null,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	productEntryId INTEGER,
	version VARCHAR(75) null,
	changeLog VARCHAR(75) null,
	downloadPageURL VARCHAR(75) null,
	directDownloadURL VARCHAR(75) null,
	repoStoreArtifact BOOLEAN
);

insert into SRFrameworkVersion (companyId, groupId, frameworkVersionId, name, url, active_, priority) values ('liferay.com', 15, 1, 'Liferay 4.0', 'http://www.liferay.com', TRUE, 1);
insert into SRFrameworkVersion (companyId, groupId, frameworkVersionId, name, url, active_, priority) values ('liferay.com', 15, 2, 'Liferay 4.1', 'http://www.liferay.com', TRUE, 2);
insert into SRFrameworkVersion (companyId, groupId, frameworkVersionId, name, url, active_, priority) values ('liferay.com', 15, 3, 'Liferay 4.2', 'http://www.liferay.com', FALSE, 3);
insert into Counter (name, currentId) values ('com.liferay.portlet.softwarerepository.model.SRFrameworkVersion', 3);

insert into SRLicense (licenseId, name, openSource, url, active_, recommended) values (1, 'GPL', TRUE, 'http://www.opensource.org/licenses/gpl-license.php', TRUE, TRUE);
insert into SRLicense (licenseId, name, openSource, url, active_, recommended) values (2, 'Proprietary', FALSE, NULL, FALSE, TRUE);
insert into SRLicense (licenseId, name, openSource, url, active_, recommended) values (3, 'MIT', TRUE, 'http://www.opensource.org/licenses/mit-license.php', TRUE, TRUE);
insert into SRLicense (licenseId, name, openSource, url, active_, recommended) values (4, 'Artistic', TRUE, 'http://www.opensource.org/licenses/artistic-license.php', TRUE, FALSE);
insert into Counter (name, currentId) values ('com.liferay.portlet.softwarerepository.model.SRLicense', 4);

alter_column_type Website websiteId BIGINT not null;