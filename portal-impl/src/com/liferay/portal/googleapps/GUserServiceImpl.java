/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.googleapps;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="GUserServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class GUserServiceImpl extends GBaseServiceImpl implements GUserService {

	public GUserServiceImpl(GAuthenticator gAuthenticator) {
		super(gAuthenticator);

		userURL =
			APPS_URL + StringPool.SLASH + gAuthenticator.getDomain() +
				"/user/2.0";
	}

	public void addUser(
			long userId, String password, String firstName, String lastName)
		throws GoogleAppsException {

		try {
			doAddUser(userId, password, firstName, lastName);
		}
		catch (Exception e) {
			throw new GoogleAppsException(e);
		}
	}

	public void deleteUser(long userId) throws GoogleAppsException {
		try {
			doDeleteUser(userId);
		}
		catch (Exception e) {
			throw new GoogleAppsException(e);
		}
	}

	public List<GUser> getUsers() throws GoogleAppsException {
		try {
			return doGetUsers();
		}
		catch (Exception e) {
			throw new GoogleAppsException(e);
		}
	}

	public void updatePassword(long userId, String password)
		throws GoogleAppsException {

		try {
			doUpdatePassword(userId, password);
		}
		catch (Exception e) {
			throw new GoogleAppsException(e);
		}
	}

	protected void doAddUser(
			long userId, String password, String firstName, String lastName)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element atomEntryElement = addAtomEntry(document);

		addAtomCategory(atomEntryElement, "user");

		Element appsLoginElement = atomEntryElement.addElement("apps:login");

		appsLoginElement.addAttribute("password", password);
		appsLoginElement.addAttribute("userName", String.valueOf(userId));

		Element appsNameElement = atomEntryElement.addElement("apps:name");

		appsNameElement.addAttribute("familyName", lastName);
		appsNameElement.addAttribute("givenName", firstName);

		Http.Options options = getOptions();

		options.setBody(
			document.formattedString(), ContentTypes.APPLICATION_ATOM_XML,
			StringPool.UTF8);
		options.setLocation(userURL);
		options.setPost(true);

		HttpUtil.URLtoString(options);
	}

	protected void doDeleteUser(long userId) throws Exception {
		Http.Options options = getOptions();

		options.setDelete(true);
		options.setLocation(getUserURL(userId));

		HttpUtil.URLtoString(options);
	}

	protected List<GUser> doGetUsers() throws Exception {
		List<GUser> gUsers = new ArrayList<GUser>();

		getUsers(gUsers, userURL);

		return gUsers;
	}

	protected void doUpdatePassword(long userId, String password)
		throws Exception {

		String xml = getUserXML(userId);

		Document document = SAXReaderUtil.read(new UnsyncStringReader(xml));

		Element atomEntryElement = document.getRootElement();

		Element appsLoginElement = atomEntryElement.element(
			getAppsQName("login"));

		appsLoginElement.addAttribute("password", password);

		Http.Options options = getOptions();

		options.setBody(
			document.formattedString(), ContentTypes.APPLICATION_ATOM_XML,
			StringPool.UTF8);
		options.setLocation(getUserURL(userId));
		options.setPut(true);

		HttpUtil.URLtoString(options);
	}

	protected void getUsers(List<GUser> gUsers, String url) throws Exception {
		Http.Options options = getOptions();

		options.setLocation(url);

		String xml = HttpUtil.URLtoString(options);

		Document document = SAXReaderUtil.read(new UnsyncStringReader(xml));

		Element atomFeedElement = document.getRootElement();

		List<Element> atomEntryElements = atomFeedElement.elements(
			getAtomQName("entry"));

		for (Element atomEntryElement : atomEntryElements) {
			GUser gUser = new GUser();

			gUsers.add(gUser);

			Element appsLoginElement = atomEntryElement.element(
				getAppsQName("login"));
			Element appsNameElement = atomEntryElement.element(
				getAppsQName("name"));

			boolean active = !GetterUtil.getBoolean(
				appsLoginElement.attributeValue("suspended"));

			gUser.setActive(active);

			boolean administrator = GetterUtil.getBoolean(
				appsLoginElement.attributeValue("admin"));

			gUser.setAdministrator(administrator);

			boolean agreedToTermsOfUse = GetterUtil.getBoolean(
				appsLoginElement.attributeValue("agreedToTerms"));

			gUser.setAgreedToTermsOfUse(agreedToTermsOfUse);

			String firstName = appsNameElement.attributeValue("givenName");

			gUser.setFirstName(firstName);

			String lastName = appsNameElement.attributeValue("familyName");

			gUser.setLastName(lastName);

			long userId = GetterUtil.getLong(
				appsLoginElement.attributeValue("userName"));

			gUser.setUserId(userId);
		}

		List<Element> atomLinkElements = atomFeedElement.elements(
			getAtomQName("link"));

		for (Element atomLinkElement : atomLinkElements) {
			String rel = atomLinkElement.attributeValue("rel");

			if (rel.equals("next")) {
				String href = atomLinkElement.attributeValue("href");

				if (!href.equals(url)) {
					getUsers(gUsers, href);
				}

				break;
			}
		}
	}

	protected String getUserURL(long userId) {
		return userURL + StringPool.SLASH + userId;
	}

	protected String getUserXML(long userId) throws Exception {
		Http.Options options = getOptions();

		options.setLocation(getUserURL(userId));

		return HttpUtil.URLtoString(options);
	}

	protected String userURL;

}