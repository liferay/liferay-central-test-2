create table WSRP_WSRPConsumer (
	uuid_ VARCHAR(75) null,
	wsrpConsumerId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	url VARCHAR(75) null,
	wsdl VARCHAR(75) null,
	registrationContextString VARCHAR(75) null,
	registrationPropertiesString VARCHAR(75) null,
	forwardCookies VARCHAR(75) null,
	forwardHeaders VARCHAR(75) null,
	markupCharacterSets VARCHAR(75) null,
	lastPublishDate DATE null
);

create table WSRP_WSRPConsumerPortlet (
	uuid_ VARCHAR(75) null,
	wsrpConsumerPortletId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	wsrpConsumerId LONG,
	name VARCHAR(75) null,
	portletHandle VARCHAR(75) null,
	lastPublishDate DATE null
);

create table WSRP_WSRPProducer (
	uuid_ VARCHAR(75) null,
	wsrpProducerId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	version VARCHAR(75) null,
	portletIds VARCHAR(75) null,
	lastPublishDate DATE null
);