alter table MBMessage add format VARCHAR(75) null;

COMMIT_TRANSACTION;

update MBMessage set format = 'bbcode';