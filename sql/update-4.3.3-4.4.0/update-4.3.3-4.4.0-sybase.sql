alter table TagsAsset add priority float;

go

update TagsAsset set priority = 0;
