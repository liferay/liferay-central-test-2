create table Vocabulary (
	vocabularyId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75),
	createDate timestamp,
	modifiedDate timestamp,
	name varchar(75),
	description varchar(75),
	folksonomy smallint
);

alter table TagsEntry add groupId bigint;
alter table TagsEntry add vocabularyId bigint;
alter table TagsEntry add parentEntryId bigint;
