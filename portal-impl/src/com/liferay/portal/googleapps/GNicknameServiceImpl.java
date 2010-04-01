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
 * <a href="GNicknameServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class GNicknameServiceImpl
	extends GBaseServiceImpl implements GNicknameService {

	public GNicknameServiceImpl(GAuthenticator gAuthenticator) {
		super(gAuthenticator);

		nicknameURL =
			APPS_URL + StringPool.SLASH + gAuthenticator.getDomain() +
				"/nickname/2.0";
	}

	public void addNickname(long userId, String nickname)
		throws GoogleAppsException {

		try {
			doAddNickname(userId, nickname);
		}
		catch (Exception e) {
			throw new GoogleAppsException(e);
		}
	}

	public void deleteNickname(String nickname) throws GoogleAppsException {
		try {
			doDeleteNickname(nickname);
		}
		catch (Exception e) {
			throw new GoogleAppsException(e);
		}
	}

	protected void doAddNickname(long userId, String nickname)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element atomEntryElement = addAtomEntry(document);

		addAtomCategory(atomEntryElement, "nickname");

		Element appsLoginElement = atomEntryElement.addElement("apps:login");

		appsLoginElement.addAttribute("userName", String.valueOf(userId));

		Element appsNicknameElement = atomEntryElement.addElement(
			"apps:nickname");

		appsNicknameElement.addAttribute("name", nickname);

		Http.Options options = getOptions();

		options.setBody(
			document.formattedString(), ContentTypes.APPLICATION_ATOM_XML,
			StringPool.UTF8);
		options.setLocation(nicknameURL);
		options.setPost(true);

		HttpUtil.URLtoString(options);
	}

	protected void doDeleteNickname(String nickname) throws Exception {
		Http.Options options = getOptions();

		options.setDelete(true);
		options.setLocation(getNicknameURL(nickname));

		HttpUtil.URLtoString(options);
	}

	protected String getNicknameURL(String nickname) {
		return nicknameURL + StringPool.SLASH + nickname;
	}

	protected String nicknameURL;

}