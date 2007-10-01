alter table JournalArticle add indexable BOOLEAN;

COMMIT_TRANSACTION;

update JournalArticle set indexable = TRUE;

delete from UserTracker;

drop table UserTrackerPath;
create table UserTrackerPath (
	userTrackerPathId LONG not null primary key,
	userTrackerId LONG,
	path_ STRING null,
	pathDate DATE null
);