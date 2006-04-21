create table BlogsCategory (
	categoryId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(100) null,
	lastPostDate DATE null
);

create table BlogsComments (
	commentsId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	modifiedDate DATE null,
	entryId VARCHAR(100) null,
	comments TEXT null
);

create table BlogsEntry (
	entryId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	createDate DATE null,
	modifiedDate DATE null,
	categoryId VARCHAR(100) null,
	sharing BOOLEAN,
	commentable BOOLEAN,
	content TEXT null
);

create table BlogsLink (
	linkId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(100) null,
	url VARCHAR(100) null
);

create table BlogsProps (
	propsId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	modifiedDate DATE null,
	entryId VARCHAR(100) null,
	quantity INTEGER
);

create table BlogsReferer (
	entryId VARCHAR(100) not null,
	url VARCHAR(100) not null,
	type_ VARCHAR(100) not null,
	quantity INTEGER,
	primary key (entryId, url, type_)
);

drop table DLFileProfile;
create table DLFileProfile (
	companyId VARCHAR(100) not null,
	repositoryId VARCHAR(100) not null,
	fileName VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	versionUserId VARCHAR(100) not null,
	versionUserName VARCHAR(100) null,
	createDate DATE null,
	modifiedDate DATE null,
	readRoles VARCHAR(100) null,
	writeRoles VARCHAR(100) null,
	description TEXT null,
	version DOUBLE,
	size_ INTEGER,
	primary key (companyId, repositoryId, fileName)
);

drop table DLFileVersion;
create table DLFileVersion (
	companyId VARCHAR(100) not null,
	repositoryId VARCHAR(100) not null,
	fileName VARCHAR(100) not null,
	version DOUBLE not null, 
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	size_ INTEGER,
	primary key (companyId, repositoryId, fileName, version)
);

create table DLRepository (
	repositoryId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	modifiedDate DATE null,
	readRoles VARCHAR(100) null,
	writeRoles VARCHAR(100) null,
	name VARCHAR(100) null,
	description STRING null,
	lastPostDate DATE null
);

alter table Layer add href VARCHAR(100) null;
alter table Layer add hrefHover VARCHAR(100) null;

alter table MBMessage add attachments BOOLEAN;
alter table MBMessage add anonymous BOOLEAN;

create table Note (
	noteId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	modifiedDate DATE null,
	className VARCHAR(100) null,
	classPK VARCHAR(100) null,
	content TEXT null
);

drop table Portlet;
create table Portlet (
	portletId VARCHAR(100) not null,
	groupId VARCHAR(100) not null,
	companyId VARCHAR(100) not null,
	narrow BOOLEAN,
	defaultPreference TEXT null,
	roles STRING null,
	active_ BOOLEAN,
	primary key (portletId, groupId, companyId)
);

create table ProjFirm (
	firmId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(100) null,
	description STRING null,
	url VARCHAR(100) null
);

create table ProjProject (
	projectId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	modifiedDate DATE null,
	firmId VARCHAR(100) null,
	code VARCHAR(100) null,
	name VARCHAR(100) null,
	description STRING null
);

create table ProjTask (
	taskId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	modifiedDate DATE null,
	projectId VARCHAR(100) null,
	name VARCHAR(100) null,
	description STRING null,
	comments TEXT null,
	estimatedDuration INTEGER,
	estimatedEndDate DATE null,
	actualDuration INTEGER,
	actualEndDate DATE null,
	status INTEGER
);

create table ProjTime (
	timeId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	modifiedDate DATE null,
	projectId VARCHAR(100) null,
	taskId VARCHAR(100) null,
	description STRING null,
	startDate DATE null,
	endDate DATE null
);

alter table User_ add dottedSkins BOOLEAN;

create table Users_ProjProjects (
	userId VARCHAR(100) not null,
	projectId VARCHAR(100) not null
);

create table Users_ProjTasks (
	userId VARCHAR(100) not null,
	taskId VARCHAR(100) not null
);