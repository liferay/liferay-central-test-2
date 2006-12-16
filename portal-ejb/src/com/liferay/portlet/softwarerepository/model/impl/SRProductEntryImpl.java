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
import com.liferay.portlet.softwarerepository.NoSuchProductEntryException;
import com.liferay.portlet.softwarerepository.model.SRLicense;
import com.liferay.portlet.softwarerepository.model.SRProductEntry;
import com.liferay.portlet.softwarerepository.service.SRProductEntryLocalServiceUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="SRProductEntryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRProductEntryImpl extends SRProductEntryModelImpl
	implements SRProductEntry {
	public SRProductEntryImpl() {
	}

	public List getLicenseIds()
		throws SystemException, NoSuchProductEntryException {
		List licenses =  SRProductEntryLocalServiceUtil.
			getSRLicenses(getPrimaryKey());
		List licenseIds = new ArrayList();
		for (Iterator iterator = licenses.iterator(); iterator.hasNext();) {
			SRLicense l = (SRLicense) iterator.next();
			licenseIds.add(new Long(l.getLicenseId()));
		}
		return licenseIds;
	}

	public String getLicenseNames()
		throws SystemException, NoSuchProductEntryException {
		List licenses =  SRProductEntryLocalServiceUtil.
			getSRLicenses(getPrimaryKey());
		StringBuffer names = new StringBuffer();
		for (Iterator iterator = licenses.iterator(); iterator.hasNext();) {
			SRLicense l = (SRLicense) iterator.next();
			names.append(l.getName());
			if (iterator.hasNext()) {
				names.append(StringPool.COMMA + StringPool.SPACE);
			}
		}
		return names.toString();

	}

}