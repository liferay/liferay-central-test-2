/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.bi.rules;

import com.liferay.portal.kernel.resource.ResourceRetriever;

import java.io.Serializable;

/**
 * <a href="AdminRequestMessage.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class AdminRequestMessage implements Serializable {

	public static AdminRequestMessage add(
		String domainName, ResourceRetriever resourceRetriever) {

		return new AdminRequestMessage(
			AdminRequestType.ADD, domainName, resourceRetriever);
	}

	public static AdminRequestMessage remove(String domainName) {
		return new AdminRequestMessage(
			AdminRequestType.REMOVE, domainName, null);
	}

	public static AdminRequestMessage update(
		String domainName, ResourceRetriever resourceRetriever) {

		return new AdminRequestMessage(
			AdminRequestType.UPDATE, domainName, resourceRetriever);
	}

	public String getDomainName() {
		return _domainName;
	}

	public ResourceRetriever getResourceRetriever() {
		return _resourceRetriever;
	}

	public AdminRequestType getType() {
		return _adminRequestType;
	}

	private AdminRequestMessage(
		AdminRequestType adminRequestType, String domainName,
		ResourceRetriever resourceRetriever) {

		_adminRequestType = adminRequestType;
		_domainName = domainName;
		_resourceRetriever = resourceRetriever;
	}

	private AdminRequestType _adminRequestType;
	private String _domainName;
	private ResourceRetriever _resourceRetriever;

}