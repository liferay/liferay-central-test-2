<#setting number_format = "0">

insert into Company (companyId, accountId, webId, virtualHost, mx) values (${companyId}, ${dataFactory.company.accountId}, 'liferay.com', 'localhost', 'liferay.com');
insert into Account_ (accountId, companyId, userId, userName, createDate, modifiedDate, parentAccountId, name, legalName, legalId, legalType, sicCode, tickerSymbol, industry, type_, size_) values (${dataFactory.company.accountId}, ${companyId}, ${defaultUserId}, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 'Liferay', 'Liferay, Inc.', '', '', '', '', '', '', '');

<#assign contact = dataFactory.addContact("", "")>
<#assign user = dataFactory.addUser(true, "")>

${sampleSQLBuilder.insertUser(contact, null, null, null, null, null, null, user)}

<#assign mbSystemCategory = dataFactory.addMBCategory(0, 0, 0, 0, "", "", 0, 0)>

${sampleSQLBuilder.insertMBCategory(mbSystemCategory)}