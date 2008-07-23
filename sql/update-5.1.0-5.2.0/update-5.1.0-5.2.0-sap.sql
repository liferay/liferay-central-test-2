create table Vocabulary (
	vocabularyId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name varchar(75) null,
	description varchar(75) null,
	folksonomy boolean
);

alter table TagsEntry add groupId bigint;
alter table TagsEntry add vocabularyId bigint;
alter table TagsEntry add parentEntryId bigint;
