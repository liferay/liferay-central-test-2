create index IX_DC80A26C on AdaptiveMediaImageEntry (companyId, configurationUuid[$COLUMN_LENGTH:75$]);
create unique index IX_3AFA259 on AdaptiveMediaImageEntry (configurationUuid[$COLUMN_LENGTH:75$], fileVersionId);
create index IX_E83FA4F4 on AdaptiveMediaImageEntry (fileVersionId);
create index IX_EAFFEA77 on AdaptiveMediaImageEntry (groupId);
create index IX_D0C342C7 on AdaptiveMediaImageEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_99FBCF09 on AdaptiveMediaImageEntry (uuid_[$COLUMN_LENGTH:75$], groupId);