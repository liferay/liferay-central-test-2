/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.portal.controlpanel.users.user;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUserTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserannouncement.AddUserAnnouncementTests;
import com.liferay.portalweb.portal.controlpanel.users.user.addusercomment.AddUserCommentTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserdefaultuserassociationscommunity.AddUserDefaultUserAssociationsCommunityTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserdefaultuserassociationsrole.AddUserDefaultUserAssociationsRoleTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserdefaultuserassociationsusergroup.AddUserDefaultUserAssociationsUserGroupTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserdisplaysettings.AddUserDisplaySettingsTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduseremailaddressduplicate.AddUserEmailAddressDuplicateTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduseremailaddressinvalid.AddUserEmailAddressInvalidTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduseremailaddressnull.AddUserEmailAddressNullTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduseremailaddresspostmaster.AddUserEmailAddressPostmasterTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduseremailaddressreserved.AddUserEmailAddressReservedTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduseremailaddressroot.AddUserEmailAddressRootTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserfirstnamenull.AddUserFirstNameNullTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserinstantmessenger.AddUserInstantMessengerTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserlastnamenull.AddUserLastNameNullTests;
import com.liferay.portalweb.portal.controlpanel.users.user.addusermultiple.AddUserMultipleTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduseropenid.AddUserOpenIDTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserpassword.AddUserPasswordTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserpassword1null.AddUserPassword1NullTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserpassword2null.AddUserPassword2NullTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserpassworddifferent.AddUserPasswordDifferentTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserpasswordduplicate.AddUserPasswordDuplicateTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserscreennameanonymous.AddUserScreenNameAnonymousTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserscreennamecyrus.AddUserScreenNameCyrusTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserscreennameduplicate.AddUserScreenNameDuplicateTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserscreennameemail.AddUserScreenNameEmailTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserscreennameinvalid.AddUserScreenNameInvalidTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserscreennamenull.AddUserScreenNameNullTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserscreennamenumber.AddUserScreenNameNumberTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserscreennamepostfix.AddUserScreenNamePostfixTests;
import com.liferay.portalweb.portal.controlpanel.users.user.adduserscreennamereserved.AddUserScreenNameReservedTests;
import com.liferay.portalweb.portal.controlpanel.users.user.addusersms.AddUserSMSTests;
import com.liferay.portalweb.portal.controlpanel.users.user.addusersocialnetwork.AddUserSocialNetworkTests;
import com.liferay.portalweb.portal.controlpanel.users.user.advancedsearchuser.AdvancedSearchUserTests;
import com.liferay.portalweb.portal.controlpanel.users.user.deactivateuser.DeactivateUserTests;
import com.liferay.portalweb.portal.controlpanel.users.user.deleteuser.DeleteUserTests;
import com.liferay.portalweb.portal.controlpanel.users.user.restoreuser.RestoreUserTests;
import com.liferay.portalweb.portal.controlpanel.users.user.searchuser.SearchUserTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UserTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddUserTests.suite());
		testSuite.addTest(AddUserAnnouncementTests.suite());
		testSuite.addTest(AddUserCommentTests.suite());
		testSuite.addTest(AddUserDefaultUserAssociationsCommunityTests.suite());
		testSuite.addTest(AddUserDefaultUserAssociationsRoleTests.suite());
		testSuite.addTest(AddUserDefaultUserAssociationsUserGroupTests.suite());
		testSuite.addTest(AddUserDisplaySettingsTests.suite());
		testSuite.addTest(AddUserEmailAddressDuplicateTests.suite());
		testSuite.addTest(AddUserEmailAddressInvalidTests.suite());
		testSuite.addTest(AddUserEmailAddressNullTests.suite());
		testSuite.addTest(AddUserEmailAddressPostmasterTests.suite());
		testSuite.addTest(AddUserEmailAddressReservedTests.suite());
		testSuite.addTest(AddUserEmailAddressRootTests.suite());
		testSuite.addTest(AddUserFirstNameNullTests.suite());
		testSuite.addTest(AddUserInstantMessengerTests.suite());
		testSuite.addTest(AddUserLastNameNullTests.suite());
		testSuite.addTest(AddUserMultipleTests.suite());
		testSuite.addTest(AddUserOpenIDTests.suite());
		testSuite.addTest(AddUserPasswordTests.suite());
		testSuite.addTest(AddUserPassword1NullTests.suite());
		testSuite.addTest(AddUserPassword2NullTests.suite());
		testSuite.addTest(AddUserPasswordDifferentTests.suite());
		testSuite.addTest(AddUserPasswordDuplicateTests.suite());
		testSuite.addTest(AddUserScreenNameAnonymousTests.suite());
		testSuite.addTest(AddUserScreenNameCyrusTests.suite());
		testSuite.addTest(AddUserScreenNameDuplicateTests.suite());
		testSuite.addTest(AddUserScreenNameEmailTests.suite());
		testSuite.addTest(AddUserScreenNameInvalidTests.suite());
		testSuite.addTest(AddUserScreenNameNullTests.suite());
		testSuite.addTest(AddUserScreenNameNumberTests.suite());
		testSuite.addTest(AddUserScreenNamePostfixTests.suite());
		testSuite.addTest(AddUserScreenNameReservedTests.suite());
		testSuite.addTest(AddUserSMSTests.suite());
		testSuite.addTest(AddUserSocialNetworkTests.suite());
		testSuite.addTest(AdvancedSearchUserTests.suite());
		testSuite.addTest(DeactivateUserTests.suite());
		testSuite.addTest(DeleteUserTests.suite());
		testSuite.addTest(RestoreUserTests.suite());
		testSuite.addTest(SearchUserTests.suite());

		return testSuite;
	}

}