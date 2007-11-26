create table ActivityTracker (
	activityTrackerId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	activity VARCHAR(75) null,
	extraData TEXT null,
	receiverUserId LONG,
	receiverUserName VARCHAR(75) null
);

alter table BlogsEntry add uuid_ VARCHAR(75) null;

alter table BookmarksEntry add uuid_ VARCHAR(75) null;

alter table BookmarksFolder add uuid_ VARCHAR(75) null;

alter table CalEvent add uuid_ VARCHAR(75) null;

alter table DLFileEntry add uuid_ VARCHAR(75) null;

alter table DLFileShortcut add uuid_ VARCHAR(75) null;

alter table DLFolder add uuid_ VARCHAR(75) null;

alter table IGFolder add uuid_ VARCHAR(75) null;

alter table IGImage add uuid_ VARCHAR(75) null;

alter table JournalArticle add uuid_ VARCHAR(75) null;

alter table JournalArticleImage add uuid_ VARCHAR(75) null;

alter table JournalStructure add uuid_ VARCHAR(75) null;

alter table JournalTemplate add uuid_ VARCHAR(75) null;

alter table MBCategory add uuid_ VARCHAR(75) null;

alter table MBMessage add uuid_ VARCHAR(75) null;

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

alter table PollsChoice add uuid_ VARCHAR(75) null;

alter table PollsQuestion add uuid_ VARCHAR(75) null;

alter table TagsAsset add groupId LONG;
alter table TagsAsset add priority DOUBLE;
alter table TagsAsset add viewCount INTEGER;

alter table User_ add uuid_ VARCHAR(75) null;

alter table WikiNode add uuid_ VARCHAR(75) null;

alter table WikiPage add uuid_ VARCHAR(75) null;

COMMIT_TRANSACTION;

update TagsAsset set priority = 0, viewCount = 0;