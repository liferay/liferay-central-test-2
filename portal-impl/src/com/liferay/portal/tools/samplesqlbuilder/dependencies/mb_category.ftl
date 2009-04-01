<#setting number_format = "0">

insert into MBCategory (uuid_, categoryId, groupId, companyId, userId, createDate, modifiedDate, parentCategoryId, name, description, threadCount, messageCount) values ('${portalUUIDUtil.generate()}', ${mbCategory.categoryId}, ${mbCategory.groupId}, ${mbCategory.companyId}, ${mbCategory.userId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, '${mbCategory.name}', '${mbCategory.description}', ${mbCategory.threadCount}, ${mbCategory.messageCount});

<#if (mbCategory.categoryId != 0)>
	${sampleSQLBuilder.insertSecurity("com.liferay.portlet.messageboards.model.MBCategory", mbCategory.categoryId)}

	insert into MBMailingList (uuid_, groupId, companyId, userId, createDate, modifiedDate, categoryId, emailAddress, inProtocol, inServerName, inServerPort, inUseSSL, inUserName, inPassword, inReadInterval, outEmailAddress, outCustom, outServerName, outServerPort, outUseSSL, outUserName, outPassword, active_, mailingListId) values ('${portalUUIDUtil.generate()}', ${mbCategory.groupId}, ${companyId}, ${mbCategory.userId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${mbCategory.categoryId}, '', 'pop3', '', 110, FALSE, '', '', 5, '', FALSE, '', 25, FALSE, '', '', FALSE, ${counter.get()});
</#if>