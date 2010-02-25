/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.ui;

import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.util.Collections;
import java.util.List;

/**
 * <a href="CustomAttributesAvailableTag.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class CustomAttributesAvailableTag
	extends ParamAndPropertyAncestorTagImpl {

	public int doStartTag() {
		long companyId = _companyId;

		if (companyId == 0) {
			companyId = CompanyThreadLocal.getCompanyId();
		}

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, _className);

		List<String> attributeNames = Collections.list(
			expandoBridge.getAttributeNames());

		if (attributeNames.isEmpty()) {
			return EVAL_PAGE;
		}
		else {
			return EVAL_BODY_INCLUDE;
		}
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	private String _className;
	private long _companyId;

}