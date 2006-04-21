create table Release_ (
	releaseId VARCHAR(100) not null primary key,
	createDate DATE null,
	modifiedDate DATE null,
	buildNumber INTEGER null,
	buildDate DATE null
);

alter table ShoppingCart add altShipping INTEGER null;

alter table ShoppingOrder add altShipping VARCHAR(100) null;