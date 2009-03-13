update Group_ set name = classPK where classPK > 0 and name = '';

update Region set regionCode = 'AB' where countryId = 1 and name = 'Alberta';

create table Shard (
	shardId LONG not null primary key,
	classNameId LONG,
	classPK LONG,
	name VARCHAR(75) null
);