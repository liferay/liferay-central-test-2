alter table TagsAsset add priority number(30,20);

commit;

update TagsAsset set priority = 0;
