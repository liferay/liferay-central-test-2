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

package com.liferay.portlet.portletconfiguration.action;

import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.PortalException;
import com.liferay.portal.PortletIdException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.UploadRequestUtil;
import com.liferay.portlet.communities.util.StagingUtil;
import com.liferay.util.servlet.ServletResponseUtil;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionMessages;
import com.liferay.util.servlet.UploadPortletRequest;

import java.io.File;

import java.util.Calendar;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ExportImportAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class ExportImportAction extends EditConfigurationAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(req);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(req, PrincipalException.class.getName());

			setForward(req, "portlet.portlet_configuration.error");
		}

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals("copy_from_live")) {
				StagingUtil.copyFromLive(req, portlet);

				sendRedirect(req, res);
			}
			else if (cmd.equals("export")) {
				exportData(req, res, portlet);
			}
			else if (cmd.equals("import")) {
				importData(req, portlet);

				sendRedirect(req, res);
			}
			else if (cmd.equals("publish_to_live")) {
				StagingUtil.publishToLive(req, portlet);

				sendRedirect(req, res);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchLayoutException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.portlet_configuration.error");
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(req);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(req, PrincipalException.class.getName());

			return mapping.findForward("portlet.portlet_configuration.error");
		}

		res.setTitle(getTitle(portlet, req));

		return mapping.findForward(getForward(
			req, "portlet.portlet_configuration.export_import"));
	}

	protected void exportData(
			ActionRequest req, ActionResponse res, Portlet portlet)
		throws Exception {

		try {
			long plid = ParamUtil.getLong(req, "plid");
			String fileName = ParamUtil.getString(req, "exportFileName");
			boolean dateRange = ParamUtil.getBoolean(req, "dateRange");
			Date startDate = null;
			Date endDate = null;

			if (dateRange) {
				User user = PortalUtil.getUser(req);

				int startDateMonth =
					ParamUtil.getInteger(req, "startDateMonth");
				int startDateDay = ParamUtil.getInteger(req, "startDateDay");
				int startDateYear = ParamUtil.getInteger(req, "startDateYear");
				int startDateHour = ParamUtil.getInteger(req, "startDateHour");
				int startDateMinute =
					ParamUtil.getInteger(req, "startDateMinute");
				int startDateAmPm = ParamUtil.getInteger(req, "startDateAmPm");

				if (startDateAmPm == Calendar.PM) {
					startDateHour += 12;
				}

				startDate = PortalUtil.getDate(
					startDateMonth, startDateDay, startDateYear, startDateHour,
					startDateMinute, user.getTimeZone(), new PortalException());

				int endDateMonth = ParamUtil.getInteger(req, "endDateMonth");
				int endDateDay = ParamUtil.getInteger(req, "endDateDay");
				int endDateYear = ParamUtil.getInteger(req, "endDateYear");
				int endDateHour = ParamUtil.getInteger(req, "endDateHour");
				int endDateMinute = ParamUtil.getInteger(req, "endDateMinute");
				int endDateAmPm = ParamUtil.getInteger(req, "endDateAmPm");

				if (endDateAmPm == Calendar.PM) {
					endDateHour += 12;
				}

				endDate = PortalUtil.getDate(
					endDateMonth, endDateDay, endDateYear, endDateHour,
					endDateMinute, user.getTimeZone(), new PortalException());
			}

			byte[] byteArray = LayoutServiceUtil.exportPortletInfo(
				plid, portlet.getPortletId(), req.getParameterMap(), startDate,
				endDate);

			HttpServletResponse httpRes = PortalUtil.getHttpServletResponse(
				res);

			ServletResponseUtil.sendFile(httpRes, fileName, byteArray);

			setForward(req, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void importData(ActionRequest req, Portlet portlet)
		throws Exception {

		try {
			UploadPortletRequest uploadReq =
				UploadRequestUtil.getUploadPortletRequest(req);

			long plid = ParamUtil.getLong(uploadReq, "plid");
			File file = uploadReq.getFile("importFileName");

			if (!file.exists()) {
				throw new LARFileException("Import file does not exist");
			}

			LayoutServiceUtil.importPortletInfo(
				plid, portlet.getPortletId(), req.getParameterMap(), file);

			SessionMessages.add(req, "request_processed");
		}
		catch (Exception e) {
			if ((e instanceof LARFileException) ||
				(e instanceof LARTypeException) ||
				(e instanceof PortletIdException)) {

				SessionErrors.add(req, e.getClass().getName());
			}
			else {
				_log.error(e, e);

				SessionErrors.add(req, LayoutImportException.class.getName());
			}
		}
	}

	private static Log _log = LogFactory.getLog(ExportImportAction.class);

}