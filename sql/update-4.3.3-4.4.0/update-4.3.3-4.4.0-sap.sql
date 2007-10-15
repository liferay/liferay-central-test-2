alter table TagsAsset add priority float;

commit;

update TagsAsset set priority = 0;
