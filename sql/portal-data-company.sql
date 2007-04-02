##
## Company
##

insert into Company (companyId, portalURL, homeURL, mx) values ('liferay.com', 'localhost:8080', 'localhost:8080', 'liferay.com');
insert into Account_ (accountId, companyId, userId, userName, createDate, modifiedDate, parentAccountId, name, legalName, legalId, legalType, sicCode, tickerSymbol, industry, type_, size_) values ('liferay.com', 'default', 'liferay.com.default', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '-1', 'Liferay', 'Liferay, LLC', '', '', '', '', '', '', '');

##
## Groups
##

insert into Group_ (groupId, companyId, parentGroupId, liveGroupId, name, friendlyURL, active_) values (1, 'liferay.com', -1, -1, 'Guest', '/guest', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.1', 'liferay.com', 1, '1', TRUE, FALSE, 'classic', '01', 0.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.1', 'liferay.com', 1, '1', FALSE, FALSE, 'brochure', '01', 89.0);

insert into Group_ (groupId, companyId, parentGroupId, liveGroupId, name, friendlyURL, active_) values (3, 'liferay.com', -1, -1, 'Support', '', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.3', 'liferay.com', 3, '3', TRUE, FALSE, 'classic', '01', 1.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.3', 'liferay.com', 3, '3', FALSE, FALSE, 'classic', '01', 0.0);
insert into Layout (layoutId, ownerId, companyId, parentLayoutId, name, type_, typeSettings, hidden_, friendlyURL, priority) values ('1', 'PRI.3', 'liferay.com', '-1', '<?xml version="1.0"?>\n\n<root>\n  <name>Support</name>\n</root>', 'portlet', 'layout-template-id=2_columns_ii\ncolumn-1=3,\ncolumn-2=19,', FALSE, '', 0.0);

##
## Organizations
##

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, recursable, regionId, countryId, statusId, comments) values ('1', 'liferay.com', '-1', 'Liferay USA', FALSE, '5', '19', 12017, '');

insert into Group_ (groupId, companyId, className, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (4, 'liferay.com', 'com.liferay.portal.model.Organization', '1', -1, -1, '4', '', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.4', 'liferay.com', 4, '4', TRUE, FALSE, 'classic', '01', 1.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.4', 'liferay.com', 4, '4', FALSE, FALSE, 'classic', '01', 0.0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, recursable, regionId, countryId, statusId, comments) values ('2', 'liferay.com', '1', 'Liferay Los Angeles', FALSE, '5', '19', 12017, '');

insert into Group_ (groupId, companyId, className, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (5, 'liferay.com', 'com.liferay.portal.model.Organization', '2', -1, -1, '5', '', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.5', 'liferay.com', 5, '5', TRUE, FALSE, 'classic', '01', 1.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.5', 'liferay.com', 5, '5', FALSE, FALSE, 'classic', '01', 0.0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, recursable, regionId, countryId, statusId, comments) values ('3', 'liferay.com', '1', 'Liferay San Francisco', FALSE, '5', '19', 12017, '');

insert into Group_ (groupId, companyId, className, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (6, 'liferay.com', 'com.liferay.portal.model.Organization', '3', -1, -1, '6', '', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.6', 'liferay.com', 6, '6', TRUE, FALSE, 'classic', '01', 1.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.6', 'liferay.com', 6, '6', FALSE, FALSE, 'classic', '01', 0.0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, recursable, regionId, countryId, statusId, comments) values ('4', 'liferay.com', '1', 'Liferay Chicago', FALSE, '14', '19', 12017, '');

insert into Group_ (groupId, companyId, className, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (7, 'liferay.com', 'com.liferay.portal.model.Organization', '4', -1, -1, '7', '', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.7', 'liferay.com', 7, '7', TRUE, FALSE, 'classic', '01', 1.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.7', 'liferay.com', 7, '7', FALSE, FALSE, 'classic', '01', 0.0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, recursable, regionId, countryId, statusId, comments) values ('5', 'liferay.com', '1', 'Liferay New York', FALSE, '33', '19', 12017, '');

insert into Group_ (groupId, companyId, className, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (8, 'liferay.com', 'com.liferay.portal.model.Organization', '5', -1, -1, '8', '', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.8', 'liferay.com', 8, '8', TRUE, FALSE, 'classic', '01', 1.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.8', 'liferay.com', 8, '8', FALSE, FALSE, 'classic', '01', 0.0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, recursable, regionId, countryId, statusId, comments) values ('6', 'liferay.com', '-1', 'Liferay Europe', FALSE, '', '15', 12017, '');

insert into Group_ (groupId, companyId, className, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (9, 'liferay.com', 'com.liferay.portal.model.Organization', '6', -1, -1, '9', '', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.9', 'liferay.com', 9, '9', TRUE, FALSE, 'classic', '01', 1.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.9', 'liferay.com', 9, '9', FALSE, FALSE, 'classic', '01', 0.0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, recursable, regionId, countryId, statusId, comments) values ('7', 'liferay.com', '6', 'Liferay London', FALSE, '', '18', 12017, '');

insert into Group_ (groupId, companyId, className, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (10, 'liferay.com', 'com.liferay.portal.model.Organization', '7', -1, -1, '10', '', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.10', 'liferay.com', 10, '10', TRUE, FALSE, 'classic', '01', 1.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.10', 'liferay.com', 10, '10', FALSE, FALSE, 'classic', '01', 0.0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, recursable, regionId, countryId, statusId, comments) values ('8', 'liferay.com', '6', 'Liferay Madrid', FALSE, '', '15', 12017, '');

insert into Group_ (groupId, companyId, className, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (11, 'liferay.com', 'com.liferay.portal.model.Organization', '8', -1, -1, '11', '', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.11', 'liferay.com', 11, '11', TRUE, FALSE, 'classic', '01', 1.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.11', 'liferay.com', 11, '11', FALSE, FALSE, 'classic', '01', 0.0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, recursable, regionId, countryId, statusId, comments) values ('9', 'liferay.com', '-1', 'Liferay Asia', FALSE, '', '2', 12017, '');

insert into Group_ (groupId, companyId, className, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (12, 'liferay.com', 'com.liferay.portal.model.Organization', '9', -1, -1, '12', '', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.12', 'liferay.com', 12, '12', TRUE, FALSE, 'classic', '01', 1.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.12', 'liferay.com', 12, '12', FALSE, FALSE, 'classic', '01', 0.0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, recursable, regionId, countryId, statusId, comments) values ('10', 'liferay.com', '9', 'Liferay Hong Kong', FALSE, '', '2', 12017, '');

insert into Group_ (groupId, companyId, className, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (13, 'liferay.com', 'com.liferay.portal.model.Organization', '10', -1, -1, '13', '', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.13', 'liferay.com', 13, '13', TRUE, FALSE, 'classic', '01', 1.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.13', 'liferay.com', 13, '13', FALSE, FALSE, 'classic', '01', 0.0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, recursable, regionId, countryId, statusId, comments) values ('11', 'liferay.com', '9', 'Liferay Shanghai', FALSE, '', '2', 12017, '');

insert into Group_ (groupId, companyId, className, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (14, 'liferay.com', 'com.liferay.portal.model.Organization', '11', -1, -1, '14', '', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.14', 'liferay.com', 14, '14', TRUE, FALSE, 'classic', '01', 1.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.14', 'liferay.com', 14, '14', FALSE, FALSE, 'classic', '01', 0.0);

##
## Roles
##

insert into Role_ (roleId, companyId, name, type_) values ('1', 'liferay.com', 'Administrator', 1);
insert into Role_ (roleId, companyId, name, type_) values ('2', 'liferay.com', 'Guest', 1);
insert into Role_ (roleId, companyId, name, type_) values ('3', 'liferay.com', 'Power User', 1);
insert into Role_ (roleId, companyId, name, type_) values ('4', 'liferay.com', 'User', 1);

##
## User (default)
##

insert into User_ (userId, companyId, createDate, modifiedDate, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, loginDate, failedLoginAttempts, agreedToTermsOfUse, active_) values ('liferay.com.default', 'default', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1000, 'password', FALSE, FALSE, 'liferay.com.default', 'default@liferay.com', 'Welcome!', CURRENT_TIMESTAMP, 0, TRUE, TRUE);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, accountId, parentContactId, firstName, middleName, lastName, nickName, male, birthday) values (1000, 'default', 'liferay.com.default', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'default', -1, '', '', '', '', TRUE, '01/01/1970');

##
## User (test@liferay.com)
##

insert into User_ (userId, companyId, createDate, modifiedDate, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, loginDate, failedLoginAttempts, agreedToTermsOfUse, active_) values ('liferay.com.1', 'liferay.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1001, 'qUqP5cyxm6YcTAhz05Hph5gvu9M=', TRUE, FALSE, 'joebloggs', 'test@liferay.com', 'Welcome Joe Bloggs!', CURRENT_TIMESTAMP, 0, TRUE, TRUE);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, accountId, parentContactId, firstName, middleName, lastName, nickName, male, birthday) values (1001, 'liferay.com', 'liferay.com.1', 'Joe Bloggs', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'liferay.com', -1, 'Joe', '', 'Bloggs', 'Duke', TRUE, '01/01/1970');

insert into Group_ (groupId, companyId, className, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (15, 'liferay.com', 'com.liferay.portal.model.User', 'liferay.com.1', -1, -1, '15', '', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.15', 'liferay.com', 15, '15', TRUE, FALSE, 'classic', '01', 1.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.15', 'liferay.com', 15, '15', FALSE, FALSE, 'classic', '01', 0.0);
insert into Layout (layoutId, ownerId, companyId, parentLayoutId, name, type_, typeSettings, hidden_, friendlyURL, priority) values ('1', 'PRI.15', 'liferay.com', '-1', '<?xml version="1.0"?>\n\n<root>\n  <name>Home A1</name>\n</root>', 'portlet', 'column-1=71_INSTANCE_OY0d,61,65,\ncolumn-2=9,29,79,8,\nlayout-template-id=2_columns_ii\n', FALSE, '', 0.0);

insert into Users_Groups values ('liferay.com.1', 1);
insert into Users_Groups values ('liferay.com.1', 3);

insert into Users_Orgs (userId, organizationId) values ('liferay.com.1', '1');
insert into Users_Orgs (userId, organizationId) values ('liferay.com.1', '2');

insert into Users_Roles values ('liferay.com.1', '1');
insert into Users_Roles values ('liferay.com.1', '3');
insert into Users_Roles values ('liferay.com.1', '4');

##
## User (test.mail@liferay.com)
##

insert into User_ (userId, companyId, createDate, modifiedDate, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, loginDate, failedLoginAttempts, agreedToTermsOfUse, active_) values ('liferay.com.35', 'liferay.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1002, 'qUqP5cyxm6YcTAhz05Hph5gvu9M=', TRUE, TRUE, 'testmail', 'test.mail@liferay.com', 'Welcome Test Mail!', CURRENT_TIMESTAMP, 0, TRUE, TRUE);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, accountId, parentContactId, firstName, middleName, lastName, nickName, male, birthday) values (1002, 'liferay.com', 'liferay.com.1', 'Joe Bloggs', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'liferay.com', -1, 'Test', '', 'Mail', '', TRUE, '01/01/1970');

insert into Group_ (groupId, companyId, className, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (16, 'liferay.com', 'com.liferay.portal.model.User', 'liferay.com.35', -1, -1, '16', '', TRUE);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PRI.16', 'liferay.com', 16, '16', TRUE, FALSE, 'classic', '01', 0.0);
insert into LayoutSet (ownerId, companyId, groupId, userId, privateLayout, logo, themeId, colorSchemeId, pageCount) values ('PUB.16', 'liferay.com', 16, '16', FALSE, FALSE, 'classic', '01', 0.0);

insert into Users_Orgs (userId, organizationId) values ('liferay.com.35', '1');
insert into Users_Orgs (userId, organizationId) values ('liferay.com.35', '2');

insert into Users_Roles values ('liferay.com.35', '3');
insert into Users_Roles values ('liferay.com.35', '4');