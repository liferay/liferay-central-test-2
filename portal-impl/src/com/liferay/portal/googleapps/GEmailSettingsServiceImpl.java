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

import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

/**
 * <a href="GEmailSettingsServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class GEmailSettingsServiceImpl
	extends GBaseServiceImpl implements GEmailSettingsService {

	public GEmailSettingsServiceImpl(GAuthenticator gAuthenticator) {
		super(gAuthenticator);

		emailSettingsURL =
			APPS_URL + "/emailsettings/2.0/" + gAuthenticator.getDomain();
	}

	public void addSendAs(long userId, String fullName, String emailAddress)
		throws GoogleAppsException {

		try {
			doAddSendAs(userId, fullName, emailAddress);
		}
		catch (Exception e) {
			throw new GoogleAppsException(e);
		}
	}

	protected void doAddSendAs(
			long userId, String fullName, String emailAddress)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element atomEntryElement = addAtomEntry(document);

		addAppsProperty(atomEntryElement, "name", fullName);
		addAppsProperty(atomEntryElement, "address", emailAddress);
		addAppsProperty(
			atomEntryElement, "makeDefault", Boolean.TRUE.toString());

		Http.Options options = getOptions();

		options.setBody(
			document.formattedString(), ContentTypes.APPLICATION_ATOM_XML,
			StringPool.UTF8);
		options.setLocation(getEmailSettingsURL(userId) + "/sendas");
		options.setPost(true);

		HttpUtil.URLtoString(options);
	}

	protected String getEmailSettingsURL(long userId) {
		return emailSettingsURL + StringPool.SLASH + userId;
	}

	protected String emailSettingsURL;

}