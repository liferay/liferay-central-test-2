drop table ExpandoRow;

create table ExpandoRow (
	rowId_ LONG not null primary key,
	tableId LONG,
	classPK LONG
);

drop table ExpandoValue;

create table ExpandoValue (
	valueId LONG not null primary key,
	tableId LONG,
	columnId LONG,
	rowId_ LONG,
	classNameId LONG,
	classPK LONG,
	data_ VARCHAR(75) null
);