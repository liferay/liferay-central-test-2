alter table Address add country VARCHAR(100) null;

alter table BJEntry add name VARCHAR(100) null;
alter table BJEntry add versesInput STRING null;

alter table BJTopic add createDate DATE null;
alter table BJTopic add modifiedDate DATE null;
alter table BJTopic add description TEXT null;

alter table BJVerse add name VARCHAR(100) null;

alter table Cache rename to Cache_;
delete from Cache_;

alter table CalEvent add remindBy VARCHAR(100) null;
alter table CalEvent add firstReminder INTEGER;
alter table CalEvent add secondReminder INTEGER;

alter table FileProfile rename to DLFileProfile;
alter table DLFileProfile add modifiedDate DATE null;

alter table VersionedFile rename to DLFileVersion;

alter table ExtranetLink add userName VARCHAR(100) null;

alter table Group_ add wikiNodeIds VARCHAR(100) null;

alter table MBTopic add lastPostDate DATE null;

create table NetworkAddress (
	addressId VARCHAR(100) not null primary key,
	userId VARCHAR(100) not null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(100) null,
	url VARCHAR(100) null,
	comments STRING null,
	content TEXT null,
	status INTEGER,
	lastUpdated DATE null,
	notifyBy VARCHAR(100) null,
	interval_ INTEGER,
	active_ BOOLEAN
);

alter table PollsQuestion add lastVoteDate DATE null;

alter table Portlet add active_ BOOLEAN;

delete from PortletPreference where portletId = '4';

alter table SSEntry add createDate DATE null;
alter table SSEntry add modifiedDate DATE null;

alter table User_ add loginDate DATE null;
alter table User_ add loginIP VARCHAR(100) null;
alter table User_ add active_ BOOLEAN;

create table UserTracker (
	userTrackerId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	modifiedDate DATE null,
	remoteAddr VARCHAR(100) null,
	remoteHost VARCHAR(100) null,
	userAgent VARCHAR(100) null
);

create table UserTrackerPath (
	userTrackerPathId VARCHAR(100) not null primary key,
	userTrackerId VARCHAR(100) not null,
	path VARCHAR(100) not null,
	pathDate DATE not null
);

create table WikiNode (
	nodeId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	modifiedDate DATE null,
	readRoles VARCHAR(100) null,
	writeRoles VARCHAR(100) null,
	name VARCHAR(100) null,
	description STRING null,
	sharing BOOLEAN,
	lastPostDate DATE null
);

create table WikiPage (
	nodeId VARCHAR(100) not null,
	title VARCHAR(100) not null,
	version DOUBLE not null,
	companyId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	userName VARCHAR(100) null,
	createDate DATE null,
	content TEXT null,
	format VARCHAR(100) null,
	head BOOLEAN,
	primary key (nodeId, title, version)
);