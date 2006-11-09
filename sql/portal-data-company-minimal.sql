##
## Company
##

insert into Company (companyId, portalURL, homeURL, mx) values ('liferay.com', 'localhost:8080', 'localhost:8080', 'liferay.com');
insert into Account_ (accountId, companyId, userId, userName, createDate, modifiedDate, parentAccountId, name, legalName, legalId, legalType, sicCode, tickerSymbol, industry, type_, size_) values ('liferay.com', 'default', 'liferay.com.default', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '-1', 'Liferay', 'Liferay, LLC', '', '', '', '', '', '', '');
insert into EmailAddress (emailAddressId, companyId, userId, className, classPK, address, typeId, primary_) values ('1', 'default', 'liferay.com.default', 'com.liferay.portal.model.Account', 'liferay.com', 'test@liferay.com', '10004', TRUE);

##
## Groups 
##

insert into Group_ (groupId, companyId, parentGroupId, name, friendlyURL) values ('1', 'liferay.com', '-1', 'Guest', '/guest');
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, themeId, colorSchemeId, pageCount) values ('PRI.1', 'liferay.com', '1', '1', TRUE, 'brochure', '01', 0.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, themeId, colorSchemeId, pageCount) values ('PUB.1', 'liferay.com', '1', '1', FALSE, 'brochure', '01', 89.0);

##
## Roles
##

insert into Role_ (roleId, companyId, name) values ('1', 'liferay.com', 'Administrator');
insert into Role_ (roleId, companyId, name) values ('2', 'liferay.com', 'Guest');
insert into Role_ (roleId, companyId, name) values ('3', 'liferay.com', 'Power User');
insert into Role_ (roleId, companyId, name) values ('4', 'liferay.com', 'User');

##
## User (default)
##

insert into User_ (userId, companyId, createDate, modifiedDate, password_, passwordEncrypted, passwordReset, emailAddress, greeting, loginDate, failedLoginAttempts, agreedToTermsOfUse, active_) values ('liferay.com.default', 'default', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'password', FALSE, FALSE, 'default@liferay.com', 'Welcome!', CURRENT_TIMESTAMP, 0, FALSE, TRUE);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, accountId, parentContactId, firstName, middleName, lastName, nickName, male, birthday) values ('liferay.com.default', 'default', 'liferay.com.default', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'default', '-1', '', '', '', '', TRUE, '01/01/1970');
insert into EmailAddress (emailAddressId, companyId, userId, className, classPK, address, typeId, primary_) values ('2', 'default', 'liferay.com.default', 'com.liferay.portal.model.Contact', 'liferay.com.default', 'default@liferay.com', '11003', TRUE);

##
## User (test@liferay.com)
##

insert into User_ (userId, companyId, createDate, modifiedDate, password_, passwordEncrypted, passwordReset, emailAddress, greeting, loginDate, failedLoginAttempts, agreedToTermsOfUse, active_) values ('liferay.com.1', 'liferay.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'qUqP5cyxm6YcTAhz05Hph5gvu9M=', TRUE, FALSE, 'test@liferay.com', 'Welcome Joe Bloggs!', CURRENT_TIMESTAMP, 0, TRUE, TRUE);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, accountId, parentContactId, firstName, middleName, lastName, nickName, male, birthday) values ('liferay.com.1', 'default', 'liferay.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'liferay.com', '-1', 'Joe', '', 'Bloggs', 'Duke', TRUE, '01/01/1970');
insert into EmailAddress (emailAddressId, companyId, userId, className, classPK, address, typeId, primary_) values ('3', 'liferay.com', 'liferay.com.1', 'com.liferay.portal.model.Contact', 'liferay.com.1', 'test@liferay.com', '11003', TRUE);

insert into Group_ (groupId, companyId, className, classPK, parentGroupId, name, friendlyURL) values ('15', 'liferay.com', 'com.liferay.portal.model.User', 'liferay.com.1', '-1', '15', '/joebloggs');
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, themeId, colorSchemeId, pageCount) values ('PRI.15', 'liferay.com', '15', '15', TRUE, 'classic', '01', 1.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, themeId, colorSchemeId, pageCount) values ('PUB.15', 'liferay.com', '15', '15', FALSE, 'classic', '01', 0.0);
insert into Layout (layoutId, ownerId, companyId, parentLayoutId, name, type_, typeSettings, hidden_, friendlyURL, priority) values ('1', 'PRI.15', 'liferay.com', '-1', '<?xml version="1.0"?>\n\n<root>\n  <name>Home A1</name>\n</root>', 'portlet', 'column-1=71_INSTANCE_OY0d,61,65,\ncolumn-2=9,29,79,8,\nlayout-template-id=2_columns_ii\n', FALSE, '', 0.0);

insert into Users_Roles values ('liferay.com.1', '1');
insert into Users_Roles values ('liferay.com.1', '3');
insert into Users_Roles values ('liferay.com.1', '4');