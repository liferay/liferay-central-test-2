alter table JournalArticle add uuid_ VARCHAR(75) null;

alter table JournalArticleImage add uuid_ VARCHAR(75) null;

alter table JournalStructure add uuid_ VARCHAR(75) null;

alter table JournalTemplate add uuid_ VARCHAR(75) null;

create table MembershipRequest (
	membershipRequestId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	groupId LONG,
	comments STRING null,
	replyComments STRING null,
	replyDate DATE null,
	replierUserId LONG,
	statusId INTEGER
);

alter table TagsAsset add priority DOUBLE;
alter table TagsAsset add viewCount INTEGER;

COMMIT_TRANSACTION;

update TagsAsset set priority = 0, viewCount = 0;