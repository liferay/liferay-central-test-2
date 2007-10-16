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

COMMIT_TRANSACTION;

update TagsAsset set priority = 0;