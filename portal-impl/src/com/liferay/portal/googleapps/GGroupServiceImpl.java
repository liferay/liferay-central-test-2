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
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="GGroupServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class GGroupServiceImpl
	extends GBaseServiceImpl implements GGroupService {

	public GGroupServiceImpl(GAuthenticator gAuthenticator) {
		super(gAuthenticator);

		groupURL = APPS_URL + "/group/2.0/" + gAuthenticator.getDomain();
	}

	public void deleteGroup(String emailAddress) throws GoogleAppsException {
		try {
			doDeleteGroup(emailAddress);
		}
		catch (Exception e) {
			throw new GoogleAppsException(e);
		}
	}

	public List<GGroup> getGroups() throws GoogleAppsException {
		try {
			return doGetGroups();
		}
		catch (Exception e) {
			throw new GoogleAppsException(e);
		}
	}

	public List<GGroup> getGroups(long userId, boolean directOnly)
		throws GoogleAppsException {

		try {
			return doGetGroups(userId, directOnly);
		}
		catch (Exception e) {
			throw new GoogleAppsException(e);
		}
	}

	protected void doDeleteGroup(String emailAddress) throws Exception {
		Http.Options options = getOptions();

		options.setDelete(true);
		options.setLocation(getGroupURL(emailAddress));

		HttpUtil.URLtoString(options);
	}

	protected List<GGroup> doGetGroups() throws Exception {
		List<GGroup> gGroups = new ArrayList<GGroup>();

		getGroups(gGroups, groupURL);

		return gGroups;
	}

	protected List<GGroup> doGetGroups(long userId, boolean directOnly)
		throws Exception {

		List<GGroup> gGroups = new ArrayList<GGroup>();

		getGroups(
			gGroups,
			groupURL + "?member=" + userId + "&directOnly=" + directOnly);

		return gGroups;
	}

	protected void getGroups(List<GGroup> gGroups, String url)
		throws Exception {

		Http.Options options = getOptions();

		options.setLocation(url);

		String xml = HttpUtil.URLtoString(options);

		Document document = SAXReaderUtil.read(new UnsyncStringReader(xml));

		Element atomFeedElement = document.getRootElement();

		List<Element> atomEntryElements = atomFeedElement.elements(
			getAtomQName("entry"));

		for (Element atomEntryElement : atomEntryElements) {
			GGroup gGroup = new GGroup();

			gGroups.add(gGroup);

			List<Element> appsPropertyElements = atomEntryElement.elements(
				getAppsQName("property"));

			for (Element appsPropertyElement : appsPropertyElements) {
				String name = appsPropertyElement.attributeValue("name");
				String value = appsPropertyElement.attributeValue("value");

				if (name.equals("description")) {
					gGroup.setDescription(value);
				}
				else if (name.equals("emailPermission")) {
					gGroup.setEmailPermission(value);
				}
				else if (name.equals("groupId")) {
					gGroup.setEmailAddress(value);
				}
				else if (name.equals("groupName")) {
					gGroup.setName(value);
				}
				else if (name.equals("permissionPreset")) {
					gGroup.setPermissionPreset(value);
				}
			}
		}

		List<Element> atomLinkElements = atomFeedElement.elements(
			getAtomQName("link"));

		for (Element atomLinkElement : atomLinkElements) {
			String rel = atomLinkElement.attributeValue("rel");

			if (rel.equals("next")) {
				String href = atomLinkElement.attributeValue("href");

				if (!href.equals(url)) {
					getGroups(gGroups, href);
				}

				break;
			}
		}
	}

	protected String getGroupURL(String emailAddress) {
		return groupURL + StringPool.SLASH + emailAddress;
	}

	protected String groupURL;

}