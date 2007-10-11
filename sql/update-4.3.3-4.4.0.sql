alter table TagsAsset add priority DOUBLE;


COMMIT_TRANSACTION;

update TagsAsset set priority = 0;
