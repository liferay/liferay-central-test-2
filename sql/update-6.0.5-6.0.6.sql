create table SocialEquityGroupSetting (
	equityGroupSettingId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	classNameId LONG,
	type_ INTEGER,
	enabled BOOLEAN
);