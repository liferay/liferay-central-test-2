alter table IGImage add name VARCHAR(75) null;
alter table IGImage add custom1ImageId LONG null;
alter table IGImage add custom2ImageId LONG null;

update Group_ set type_ = 3 where type_ = 0;

update Image set type_ = 'jpg' where type_ = 'jpeg';

create table TasksProposal (
	proposalId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null,
	publishDate DATE null,
	dueDate DATE null
);

create table TasksReview (
	reviewId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	proposalId LONG,
	assignedByUserId LONG,
	assignedByUserName VARCHAR(75) null,
	stage INTEGER,
	completed BOOLEAN,
	rejected BOOLEAN
);

alter table WikiPage add parentTitle VARCHAR(75) null;
alter table WikiPage add redirectTitle VARCHAR(75) null;