alter table TagsAsset add priority double precision;

commit;

update TagsAsset set priority = 0;
