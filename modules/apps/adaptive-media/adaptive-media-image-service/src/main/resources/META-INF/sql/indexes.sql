create index IX_F12E3AB5 on AdaptiveMediaImage (companyId);
create unique index IX_3AFA259 on AdaptiveMediaImage (configurationUuid[$COLUMN_LENGTH:75$], fileVersionId);
create index IX_E83FA4F4 on AdaptiveMediaImage (fileVersionId);
create index IX_EAFFEA77 on AdaptiveMediaImage (groupId);
create index IX_D0C342C7 on AdaptiveMediaImage (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_99FBCF09 on AdaptiveMediaImage (uuid_[$COLUMN_LENGTH:75$], groupId);