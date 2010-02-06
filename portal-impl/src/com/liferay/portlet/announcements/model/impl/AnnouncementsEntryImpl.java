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

package com.liferay.portlet.announcements.model.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;

/**
 * <a href="AnnouncementsEntryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class AnnouncementsEntryImpl
	extends AnnouncementsEntryModelImpl implements AnnouncementsEntry {

	public AnnouncementsEntryImpl() {
	}

	public long getGroupId() throws PortalException, SystemException {
		long groupId = 0;

		long classPK = getClassPK();

		if (classPK > 0) {
			String className = getClassName();

			if (className.equals(Group.class.getName())) {
				Group group = GroupLocalServiceUtil.getGroup(classPK);

				groupId = group.getGroupId();
			}
			else if (className.equals(Organization.class.getName())) {
				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(classPK);

				Group group = organization.getGroup();

				groupId = group.getGroupId();
			}
		}

		return groupId;
	}

}