create table ComicEntry (
	entryId VARCHAR(100) not null primary key,
	companyId VARCHAR(100) not null,
	name VARCHAR(100) not null,
	url VARCHAR(100) not null,
	imageSearchKey VARCHAR(100) not null,
	active_ BOOLEAN not null
);

insert into Counter values ('com.liferay.portlet.comic.model.ComicEntry', 5);

insert into ComicEntry (entryId, companyId, name, url, imageSearchKey, active_) values ('1', 'liferay.com', 'FoxTrot', 'http://www.ucomics.com/foxtrot/', '<img src="http://images.ucomics.com/comics/ft/', TRUE);
insert into ComicEntry (entryId, companyId, name, url, imageSearchKey, active_) values ('2', 'liferay.com', 'Calvin and Hobbes', 'http://www.ucomics.com/calvinandhobbes/', '<img src="http://images.ucomics.com/comics/ch/', TRUE);
insert into ComicEntry (entryId, companyId, name, url, imageSearchKey, active_) values ('3', 'liferay.com', 'Dilbert', 'http://news.yahoo.com/news?tmpl=story&u=/umedia/cx_dilbert_umedia/latest', '<img src="http://us.news1.yimg.com/us.yimg.com/p/umedia/', TRUE);
insert into ComicEntry (entryId, companyId, name, url, imageSearchKey, active_) values ('4', 'liferay.com', 'Garfield', 'http://www.ucomics.com/garfield/', '<img src="http://images.ucomics.com/comics/ga/', TRUE);
insert into ComicEntry (entryId, companyId, name, url, imageSearchKey, active_) values ('5', 'liferay.com', 'Get Fuzzy', 'http://news.yahoo.com/news?tmpl=story&u=/umedia/cx_getfuzzy_umedia/latest', '<img src="http://us.news1.yimg.com/us.yimg.com/p/umedia/', TRUE);

create table JournalContentSearch (
	layoutId VARCHAR(100) not null,
	userId VARCHAR(100) not null,
	portletId VARCHAR(100) not null,
	articleId VARCHAR(100),
	primary key (layoutId, userId, portletId)
);

create table UserIdMapper (
	userId VARCHAR(100) not null,
	type_ VARCHAR(100) not null,
	description STRING,
	externalUserId VARCHAR(100),
	primary key (userId, type_)
);

create table Users_ComicEntries (
	userId VARCHAR(100) not null,
	entryId VARCHAR(100) not null
);