alter table TagsAsset add priority double;

commit;

update TagsAsset set priority = 0;
