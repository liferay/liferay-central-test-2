create table Vocabulary (
	vocabularyId int8 not null primary key,
	groupId int8,
	companyId int8,
	userId int8,
	userName varchar(75),
	createDate datetime YEAR TO FRACTION,
	modifiedDate datetime YEAR TO FRACTION,
	name varchar(75),
	description varchar(75),
	folksonomy boolean
)
extent size 16 next size 16
lock mode row;

alter table TagsEntry add groupId int8;
alter table TagsEntry add vocabularyId int8;
alter table TagsEntry add parentEntryId int8;
