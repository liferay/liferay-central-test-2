create table AdaptiveMediaImage (
	uuid_ VARCHAR(75) null,
	adaptiveMediaImageId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	configurationUuid VARCHAR(75) null,
	fileVersionId LONG,
	height INTEGER,
	width INTEGER,
	size_ LONG
);