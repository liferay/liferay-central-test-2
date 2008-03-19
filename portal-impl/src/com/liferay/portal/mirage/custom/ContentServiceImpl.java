/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.mirage.custom;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.exception.TemplateNotFoundException;
import com.sun.portal.cms.mirage.model.core.User;
import com.sun.portal.cms.mirage.model.custom.Content;
import com.sun.portal.cms.mirage.model.custom.VersionableContent;
import com.sun.portal.cms.mirage.service.custom.ContentService;

import java.util.List;

/**
 * <a href="ContentServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class ContentServiceImpl implements ContentService {

	public void createContent(Content content, String username)
		throws CMSException, TemplateNotFoundException {
	}

	public void deleteContent(String contentName, String contentTypeName)
		throws CMSException {
	}

	public Content getContentByNameAndType(
			String contentName, String contentTypeName, String username)
		throws CMSException {

		return null;
	}

	public Content getContentByUUID(String uuid, User user)
		throws CMSException {

		return null;
	}

	public List<String> getContentNamesByType(String contentTypeName)
		throws CMSException {

		return null;
	}

	public void checkinContent(Content content, String username)
		throws CMSException, TemplateNotFoundException {
	}

	public VersionableContent checkoutContent(
			String contentName, String contentTypeName, String username)
		throws CMSException {

		return null;
	}

	public VersionableContent getContentByVersion(
			String contentName, String contentTypeName, String versionName,
			User user)
		throws CMSException {

		return null;
	}

	public String getContentURL(String appURL, String UUID) {
		return null;
	}

	public List<Content> getContentsByType(
		String contentTypeUUID, String username) throws CMSException {

		return null;
	}

	public List<String> getVersionNames(
			String contentName, String contentTypeName, User user)
		throws CMSException {

		return null;
	}

	public List<VersionableContent> getVersions(
			String contentName, String contentTypeName, User user)
		throws CMSException {

		return null;
	}

	public void unCheckoutContent(
			String contentName, String contentTypeName, String user)
		throws CMSException {
	}

	public void updateContent(Content content, String username)
		throws CMSException, TemplateNotFoundException {
	}

}