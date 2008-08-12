alter table TagsEntry add groupId LONG;
alter table TagsEntry add parentEntryId LONG;
alter table TagsEntry add vocabularyId LONG;

COMMIT_TRANSACTION;

update TagsEntry set groupId = 0;
update TagsEntry set parentEntryId = 0;
update TagsEntry set vocabularyId = 0;

create table TagsVocabulary (
	vocabularyId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null,
	folksonomy BOOLEAN
);

alter table WikiPage add minorEdit BOOLEAN;

COMMIT_TRANSACTION;

update WikiPage set minorEdit = FALSE;