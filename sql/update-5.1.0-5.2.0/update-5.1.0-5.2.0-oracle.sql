create table Vocabulary (
	vocabularyId number(30,0) not null primary key,
	groupId number(30,0),
	companyId number(30,0),
	userId number(30,0),
	userName varchar2(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name varchar2(75) null,
	description varchar2(75) null,
	folksonomy number(1, 0)
);

alter table TagsEntry add groupId number(30,0);
alter table TagsEntry add vocabularyId number(30,0);
alter table TagsEntry add parentEntryId number(30,0);
