alter table Group_ add type_ VARCHAR(75) null;

create table Groups_Orgs (
	groupId VARCHAR(75) not null,
	organizationId VARCHAR(75) not null,
	primary key (groupId, organizationId)
);

alter table Role_ description VARCHAR(75) null;