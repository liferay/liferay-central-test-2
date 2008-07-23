create table Vocabulary (
	vocabularyId decimal(20,0) not null primary key,
	groupId decimal(20,0),
	companyId decimal(20,0),
	userId decimal(20,0),
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	name varchar(75) null,
	description varchar(75) null,
	folksonomy int
)
go

alter table TagsEntry add groupId decimal(20,0)
go
alter table TagsEntry add vocabularyId decimal(20,0)
go
alter table TagsEntry add parentEntryId decimal(20,0)
go
