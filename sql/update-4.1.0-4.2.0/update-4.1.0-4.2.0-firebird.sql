alter table Country alter column "countryCode" to "a3";
alter table Country add a2 varchar(75);
alter table Country add number_ varchar(75);
alter table Country add idd_ varchar(75);



alter table DLFileEntry add extraSettings blob;

create table DLFileShortcut (
	fileShortcutId integer not null primary key,
	companyId varchar(75) not null,
	userId varchar(75) not null,
	userName varchar(75),
	createDate timestamp,
	modifiedDate timestamp,
	folderId varchar(75),
	toFolderId varchar(75),
	toName varchar(75)
);

alter table Image add type_ varchar(75);

alter table JournalArticle add description varchar(4000);

alter table JournalArticle drop primary key;
alter table JournalArticle add primary key (companyId, groupId, articleId, version);

alter table JournalStructure drop primary key;
alter table JournalStructure add primary key (companyId, groupId, structureId);

alter table JournalTemplate drop primary key;
alter table JournalTemplate add primary key (companyId, groupId, templateId);

drop table JournalContentSearch;
create table JournalContentSearch (
	portletId varchar(75) not null,
	layoutId varchar(75) not null,
	ownerId varchar(75) not null,
	articleId varchar(75) not null,
	companyId varchar(75) not null,
	groupId varchar(75) not null,
	primary key (portletId, layoutId, ownerId, articleId)
);

alter table Layout add title varchar(4000);

alter table LayoutSet add virtualHost varchar(75);

alter table MBThread add priority double precision;

create table RatingsEntry (
	entryId integer not null primary key,
	companyId varchar(75) not null,
	userId varchar(75) not null,
	userName varchar(75),
	createDate timestamp,
	modifiedDate timestamp,
	className varchar(75),
	classPK varchar(75),
	score double precision
);

create table RatingsStats (
	statsId integer not null primary key,
	className varchar(75),
	classPK varchar(75),
	totalEntries integer,
	totalScore double precision,
	averageScore double precision
);
