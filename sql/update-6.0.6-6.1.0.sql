alter table MBMessage add messageFormat VARCHAR(75) null;

COMMIT_TRANSACTION;

update MBMessage set messageFormat = 'bbcode';
