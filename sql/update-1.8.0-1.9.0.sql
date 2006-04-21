alter table DLFileProfile add versionUserId VARCHAR(100) not null;
alter table DLFileProfile add versionUserName VARCHAR(100) null;

alter table DLFileVersion add userId VARCHAR(100) not null;
alter table DLFileVersion add userName VARCHAR(100) null;

alter table JournalArticle add internal_ BOOLEAN;

alter table ShoppingItem add sku VARCHAR(100) null;
alter table ShoppingItem add minQuantity INTEGER;
alter table ShoppingItem add maxQuantity INTEGER;
alter table ShoppingItem add stockQuantity INTEGER;
alter table ShoppingItem add smallImageURL VARCHAR(100) null;
alter table ShoppingItem add mediumImageURL VARCHAR(100) null;
alter table ShoppingItem add largeImageURL VARCHAR(100) null;

create table ShoppingItemPrice (
	itemPriceId VARCHAR(100) not null primary key,
	itemId VARCHAR(100) null,
	minQuantity INTEGER,
	maxQuantity INTEGER,
	price DOUBLE,
	discount DOUBLE,
	taxable BOOLEAN,
	shipping DOUBLE,
	useShippingFormula BOOLEAN,
	status INTEGER
);

alter table ShoppingOrder add tax DOUBLE;
alter table ShoppingOrder add shipping DOUBLE;

alter table ShoppingOrderItem add sku VARCHAR(100) null;
alter table ShoppingOrderItem add name VARCHAR(100) null;
alter table ShoppingOrderItem add description STRING null;
alter table ShoppingOrderItem add properties STRING null;

drop table SSEntry;