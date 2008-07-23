create table Vocabulary (
	vocabularyId int64 not null primary key,
	groupId int64,
	companyId int64,
	userId int64,
	userName varchar(75),
	createDate timestamp,
	modifiedDate timestamp,
	name varchar(75),
	description varchar(75),
	folksonomy smallint
);

alter table TagsEntry add groupId int64;
alter table TagsEntry add vocabularyId int64;
alter table TagsEntry add parentEntryId int64;
