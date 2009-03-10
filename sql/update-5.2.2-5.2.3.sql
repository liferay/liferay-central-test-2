alter table Company add shardId VARCHAR(75) null;
update Company set shardId = 'default';

update Group_ set name = classPK where classPK > 0;

update Region set regionCode = 'AB' where countryId = 1 and name = 'Alberta';