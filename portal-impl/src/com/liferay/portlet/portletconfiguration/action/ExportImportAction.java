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

package com.liferay.portlet.portletconfiguration.action;

import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.PortletIdException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.communities.util.StagingUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.File;
import java.io.FileInputStream;

import java.util.Calendar;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ExportImportAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class ExportImportAction extends EditConfigurationAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(actionRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				actionRequest, PrincipalException.class.getName());

			setForward(actionRequest, "portlet.portlet_configuration.error");
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals("copy_from_live")) {
				StagingUtil.copyFromLive(actionRequest, portlet);

				sendRedirect(actionRequest, actionResponse);
			}
			else if (cmd.equals("export")) {
				exportData(actionRequest, actionResponse, portlet);
			}
			else if (cmd.equals("import")) {
				importData(actionRequest, portlet);

				sendRedirect(actionRequest, actionResponse);
			}
			else if (cmd.equals("publish_to_live")) {
				StagingUtil.publishToLive(actionRequest, portlet);

				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchLayoutException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(
					actionRequest, "portlet.portlet_configuration.error");
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return mapping.findForward("portlet.portlet_configuration.error");
		}

		renderResponse.setTitle(getTitle(portlet, renderRequest));

		return mapping.findForward(getForward(
			renderRequest, "portlet.portlet_configuration.export_import"));
	}

	protected void exportData(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Portlet portlet)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long plid = ParamUtil.getLong(actionRequest, "plid");
			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			String fileName = ParamUtil.getString(
				actionRequest, "exportFileName");
			boolean dateRange = ParamUtil.getBoolean(
				actionRequest, "dateRange");
			Date startDate = null;
			Date endDate = null;

			if (dateRange) {
				int startDateMonth = ParamUtil.getInteger(
					actionRequest, "startDateMonth");
				int startDateDay = ParamUtil.getInteger(
					actionRequest, "startDateDay");
				int startDateYear = ParamUtil.getInteger(
					actionRequest, "startDateYear");
				int startDateHour = ParamUtil.getInteger(
					actionRequest, "startDateHour");
				int startDateMinute = ParamUtil.getInteger(
					actionRequest, "startDateMinute");
				int startDateAmPm = ParamUtil.getInteger(
					actionRequest, "startDateAmPm");

				if (startDateAmPm == Calendar.PM) {
					startDateHour += 12;
				}

				startDate = PortalUtil.getDate(
					startDateMonth, startDateDay, startDateYear, startDateHour,
					startDateMinute, themeDisplay.getTimeZone(),
					new PortalException());

				int endDateMonth = ParamUtil.getInteger(
					actionRequest, "endDateMonth");
				int endDateDay = ParamUtil.getInteger(
					actionRequest, "endDateDay");
				int endDateYear = ParamUtil.getInteger(
					actionRequest, "endDateYear");
				int endDateHour = ParamUtil.getInteger(
					actionRequest, "endDateHour");
				int endDateMinute = ParamUtil.getInteger(
					actionRequest, "endDateMinute");
				int endDateAmPm = ParamUtil.getInteger(
					actionRequest, "endDateAmPm");

				if (endDateAmPm == Calendar.PM) {
					endDateHour += 12;
				}

				endDate = PortalUtil.getDate(
					endDateMonth, endDateDay, endDateYear, endDateHour,
					endDateMinute, themeDisplay.getTimeZone(),
					new PortalException());
			}

			File file = LayoutServiceUtil.exportPortletInfoAsFile(
				plid, groupId, portlet.getPortletId(),
				actionRequest.getParameterMap(), startDate, endDate);

			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			ServletResponseUtil.sendFile(
				response, fileName, new FileInputStream(file),
				ContentTypes.APPLICATION_ZIP);

			setForward(actionRequest, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void importData(ActionRequest actionRequest, Portlet portlet)
		throws Exception {

		try {
			UploadPortletRequest uploadRequest =
				PortalUtil.getUploadPortletRequest(actionRequest);

			long plid = ParamUtil.getLong(uploadRequest, "plid");
			long groupId = ParamUtil.getLong(uploadRequest, "groupId");
			File file = uploadRequest.getFile("importFileName");

			if (!file.exists()) {
				throw new LARFileException("Import file does not exist");
			}

			LayoutServiceUtil.importPortletInfo(
				plid, groupId, portlet.getPortletId(),
				actionRequest.getParameterMap(), file);

			SessionMessages.add(actionRequest, "request_processed");
		}
		catch (Exception e) {
			if ((e instanceof LARFileException) ||
				(e instanceof LARTypeException) ||
				(e instanceof PortletIdException)) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else {
				_log.error(e, e);

				SessionErrors.add(
					actionRequest, LayoutImportException.class.getName());
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ExportImportAction.class);

}