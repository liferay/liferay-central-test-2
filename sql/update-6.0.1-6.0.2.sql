alter table AssetEntry add socialInformationK DOUBLE;
alter table AssetEntry add socialInformationB DOUBLE;
alter table AssetEntry add socialInformationEquity DOUBLE;

alter table LayoutSet add settings_ TEXT null;

create table SocialEquityHistory (
	equityHistoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	personalEquity INTEGER
);

create table SocialEquityLog (
	equityLogId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	assetEntryId LONG,
	actionId VARCHAR(75) null,
	actionDate INTEGER,
	type_ INTEGER,
	value INTEGER,
	validity INTEGER
);

create table SocialEquitySetting (
	equitySettingId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	classNameId LONG,
	actionId VARCHAR(75) null,
	type_ INTEGER,
	value INTEGER,
	validity INTEGER,
	active_ BOOLEAN
);

create table Ticket (
	ticketId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	key_ VARCHAR(75) null,
	expirationDate DATE null
);

alter table User_ add socialContributionEquity DOUBLE;
alter table User_ add socialParticipationK DOUBLE;
alter table User_ add socialParticipationB DOUBLE;
alter table User_ add socialParticipationEquity DOUBLE;
alter table User_ add socialPersonalEquity DOUBLE;