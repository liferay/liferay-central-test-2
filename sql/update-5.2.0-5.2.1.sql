update Organization_ set type_ = 'regular-organization' where type_ = 'regular';

alter table Company add shardId VARCHAR(75) null;
update Company set shardId = 'default';