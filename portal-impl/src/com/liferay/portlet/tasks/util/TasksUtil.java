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

package com.liferay.portlet.tasks.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="TasksUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class TasksUtil {

	public static int getNumberOfApprovalStages(long companyId, long groupId) {
		String numberOfApprovalStages = PropsUtil.get(
				PropsUtil.TASKS_NUMBER_OF_APPROVAL_STAGES);

		try {
			PortletPreferences prefs = getPreferences(companyId, groupId);

			return GetterUtil.getInteger(
				prefs.getValue("number-of-approval-stages",
					numberOfApprovalStages));
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return GetterUtil.getInteger(numberOfApprovalStages);
	}

	public static String[] getApprovalRoleNames(long companyId, long groupId) {
		String[] approvalRoleNames = PropsUtil.getArray(
				PropsUtil.TASKS_APPROVAL_ROLE_NAMES);

		try {
			PortletPreferences prefs = getPreferences(companyId, groupId);

			return prefs.getValues("approval-role-names",
					approvalRoleNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return approvalRoleNames;
	}

	public static PortletPreferences getPreferences(
			long companyId, long groupId)
		throws PortalException, SystemException {

		int ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
		long plid = PortletKeys.PREFS_PLID_SHARED;
		String portletId = PortletKeys.TASKS;
		String defaultPreferences = null;

		return PortletPreferencesLocalServiceUtil.getPreferences(
			companyId, groupId, ownerType, plid,
			portletId, defaultPreferences);
	}

	private static Log _log = LogFactory.getLog(TasksUtil.class);

}
