/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarerepository.model.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.softwarerepository.NoSuchProductVersionException;
import com.liferay.portlet.softwarerepository.model.SRFrameworkVersion;
import com.liferay.portlet.softwarerepository.model.SRProductVersion;
import com.liferay.portlet.softwarerepository.service.SRProductVersionLocalServiceUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="SRProductVersionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRProductVersionImpl extends SRProductVersionModelImpl
	implements SRProductVersion {
	public SRProductVersionImpl() {
	}
	public List getFrameworkVersionIds()
		throws SystemException, NoSuchProductVersionException {
		List frameworkVersions =  SRProductVersionLocalServiceUtil.
			getSRFrameworkVersions(getPrimaryKey());
		List frameworkVersionIds = new ArrayList();
		for (
			Iterator iterator = frameworkVersions.iterator();
			 iterator.hasNext();) {
			SRFrameworkVersion l = (SRFrameworkVersion) iterator.next();
			frameworkVersionIds.add(new Long(l.getFrameworkVersionId()));
		}
		return frameworkVersionIds;
	}

	public String getFrameworkVersionNames()
		throws SystemException, NoSuchProductVersionException {
		List frameworkVersions =  SRProductVersionLocalServiceUtil.
			getSRFrameworkVersions(getPrimaryKey());
		StringBuffer names = new StringBuffer();
		for (Iterator iterator = frameworkVersions.iterator();
			 iterator.hasNext();) {
			SRFrameworkVersion l = (SRFrameworkVersion) iterator.next();
			names.append(l.getName());
			if (iterator.hasNext()) {
				names.append(StringPool.COMMA + StringPool.SPACE);
			}
		}
		return names.toString();

	}
}