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

package com.liferay.portal.cmis.model;

import com.liferay.portal.cmis.CMISUtil;

import javax.xml.namespace.QName;

import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.ElementWrapper;

/**
 * <a href="CMISRepositoryInfo.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class CMISRepositoryInfo extends ElementWrapper {

	public CMISRepositoryInfo(Element internal) {
		super(internal);

		_constants = CMISUtil.getConstants();
	}

	public CMISRepositoryInfo(Factory factory) {
		super(factory, CMISUtil.getConstants().REPOSITORY_INFO);

		_constants = CMISUtil.getConstants();
	}

	public String getId() {
		return getFirstChildText(_constants.REPOSITORY_ID);
	}

	public String getName() {
		return getFirstChildText(_constants.REPOSITORY_NAME);
	}

	public String getRelatonship() {
		return getFirstChildText(_constants.REPOSITORY_RELATIONSHIP);
	}

	public String getDescription() {
		return getFirstChildText(_constants.REPOSITORY_DESCRIPTION);
	}

	public String getVendorName() {
		return getFirstChildText(_constants.REPOSITORY_VENDOR_NAME);
	}

	public String getProductName() {
		return getFirstChildText(_constants.REPOSITORY_PRODUCT_NAME);
	}

	public String getProductVersion() {
		return getFirstChildText(_constants.REPOSITORY_PRODUCT_VERSION);
	}

	public String getRootFolderId() {
		return getFirstChildText(_constants.REPOSITORY_ROOT_FOLDER_ID);
	}

	public String getVersionSupported() {
		return getFirstChildText(_constants.REPOSITORY_VERSION_SUPPORTED);
	}

	public Element getSpecificInfo() {
		return getFirstChild(_constants.REPOSITORY_SPECIFIC_INFO);
	}

	protected String getFirstChildText(QName qname) {
		String text = null;

		Element child = getFirstChild(qname);

		if (child != null) {
			text = child.getText();
		}

		return text;
	}

	private CMISConstants _constants;

}