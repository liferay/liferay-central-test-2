/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionMessages;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditConfigurationAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class EditConfigurationAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String portletResource = ParamUtil.getString(
			req, "portletResource");

		PortletPreferences prefs = PortletPreferencesFactory.getPortletSetup(
			req, portletResource, true, true);

		update(req, prefs);

		if (SessionErrors.isEmpty(req)) {
			prefs.store();

			SessionMessages.add(req, config.getPortletName() + ".doConfigure");
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		return mapping.findForward(
			"portlet.document_library.edit_configuration");
	}

	protected void update(ActionRequest req, PortletPreferences prefs)
		throws PortalException, ReadOnlyException, SystemException {

		String rootFolderId = ParamUtil.getString(req, "rootFolderId");
		String showSearch = ParamUtil.getString(req, "showSearch");
		String showBreadcrumbs = ParamUtil.getString(req, "showBreadcrumbs");
		String showSubfolders = ParamUtil.getString(req, "showSubfolders");

		String showColumnDownloads =
			ParamUtil.getString(req, "showColumnDownloads");
		String showColumnLocked = ParamUtil.getString(req, "showColumnLocked");
		String showColumnDate = ParamUtil.getString(req, "showColumnDate");
		String showColumnSize = ParamUtil.getString(req, "showColumnSize");

		String numberOfDocumentsPerPage =
			ParamUtil.getString(req, "numberOfDocumentsPerPage");

		if (Validator.isNull(rootFolderId)) {
			SessionErrors.add(req, "rootFolderIdInvalid");
			return;
		}

		try {
			if (!rootFolderId.equals(DLFolderImpl.DEFAULT_PARENT_FOLDER_ID)) {
				DLFolderLocalServiceUtil.getFolder(rootFolderId);
			}

			prefs.setValue("rootFolderId", rootFolderId);
			prefs.setValue("showSearch", showSearch);
			prefs.setValue("showBreadcrumbs", showBreadcrumbs);
			prefs.setValue("showSubfolders", showSubfolders);

			prefs.setValue("showColumnDownloads", showColumnDownloads);
			prefs.setValue("showColumnLocked", showColumnLocked);
			prefs.setValue("showColumnDate", showColumnDate);
			prefs.setValue("showColumnSize", showColumnSize);

			prefs.setValue(
				"numberOfDocumentsPerPage", numberOfDocumentsPerPage);
		}
		catch (NoSuchFolderException e) {
			SessionErrors.add(req, "rootFolderIdInvalid");
		}

	}

}