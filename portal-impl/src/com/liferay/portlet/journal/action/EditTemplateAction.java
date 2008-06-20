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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.journal.DuplicateTemplateIdException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.RequiredTemplateException;
import com.liferay.portlet.journal.TemplateDescriptionException;
import com.liferay.portlet.journal.TemplateIdException;
import com.liferay.portlet.journal.TemplateNameException;
import com.liferay.portlet.journal.TemplateSmallImageNameException;
import com.liferay.portlet.journal.TemplateSmallImageSizeException;
import com.liferay.portlet.journal.TemplateXslException;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.journal.service.JournalTemplateServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.JS;

import java.io.File;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditTemplateAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EditTemplateAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		JournalTemplate template = null;

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				template = updateTemplate(req);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteTemplates(req);
			}

			String redirect = ParamUtil.getString(req, "redirect");

			if (template != null) {
				boolean saveAndContinue = ParamUtil.getBoolean(
					req, "saveAndContinue");

				if (saveAndContinue) {
					redirect = getSaveAndContinueRedirect(
						config, req, template, redirect);
				}
			}

			sendRedirect(req, res, redirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchTemplateException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.journal.error");
			}
			else if (e instanceof DuplicateTemplateIdException ||
					 e instanceof RequiredTemplateException ||
					 e instanceof TemplateDescriptionException ||
					 e instanceof TemplateIdException ||
					 e instanceof TemplateNameException ||
					 e instanceof TemplateSmallImageNameException ||
					 e instanceof TemplateSmallImageSizeException ||
					 e instanceof TemplateXslException) {

				SessionErrors.add(req, e.getClass().getName());

				if (e instanceof RequiredTemplateException) {
					res.sendRedirect(ParamUtil.getString(req, "redirect"));
				}
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

		try {
			String cmd = ParamUtil.getString(req, Constants.CMD);

			if (!cmd.equals(Constants.ADD)) {
				ActionUtil.getTemplate(req);
			}
		}
		catch (NoSuchTemplateException nsse) {

			// Let this slide because the user can manually input a template id
			// for a new template that does not yet exist.

		}
		catch (Exception e) {
			if (//e instanceof NoSuchTemplateException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.journal.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.journal.edit_template"));
	}

	protected void deleteTemplates(ActionRequest req) throws Exception {
		long groupId = ParamUtil.getLong(req, "groupId");

		String[] deleteTemplateIds = StringUtil.split(
			ParamUtil.getString(req, "deleteTemplateIds"));

		for (int i = 0; i < deleteTemplateIds.length; i++) {
			JournalTemplateServiceUtil.deleteTemplate(
				groupId, deleteTemplateIds[i]);

			JournalUtil.removeRecentTemplate(req, deleteTemplateIds[i]);
		}
	}

	protected String getSaveAndContinueRedirect(
			PortletConfig config, ActionRequest req, JournalTemplate template,
			String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		String originalRedirect = ParamUtil.getString(req, "originalRedirect");

		PortletURLImpl portletURL = new PortletURLImpl(
			(ActionRequestImpl)req, config.getPortletName(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(WindowState.MAXIMIZED);

		portletURL.setParameter("struts_action", "/journal/edit_template");
		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter("originalRedirect", originalRedirect, false);
		portletURL.setParameter(
			"groupId", String.valueOf(template.getGroupId()), false);
		portletURL.setParameter("templateId", template.getTemplateId(), false);

		return portletURL.toString();
	}

	protected JournalTemplate updateTemplate(ActionRequest req)
		throws Exception {

		UploadPortletRequest uploadReq = PortalUtil.getUploadPortletRequest(
			req);

		String cmd = ParamUtil.getString(uploadReq, Constants.CMD);

		Layout layout = (Layout)uploadReq.getAttribute(WebKeys.LAYOUT);

		long groupId = ParamUtil.getLong(uploadReq, "groupId");

		String templateId = ParamUtil.getString(uploadReq, "templateId");
		boolean autoTemplateId = ParamUtil.getBoolean(
			uploadReq, "autoTemplateId");

		String structureId = ParamUtil.getString(uploadReq, "structureId");
		String name = ParamUtil.getString(uploadReq, "name");
		String description = ParamUtil.getString(uploadReq, "description");

		String xsl = ParamUtil.getString(uploadReq, "xsl");
		String xslContent = JS.decodeURIComponent(
			ParamUtil.getString(uploadReq, "xslContent"));
		boolean formatXsl = ParamUtil.getBoolean(uploadReq, "formatXsl");

		if (Validator.isNull(xsl)) {
			xsl = xslContent;
		}

		String langType = ParamUtil.getString(
			uploadReq, "langType", JournalTemplateImpl.LANG_TYPE_XSL);

		boolean cacheable = ParamUtil.getBoolean(uploadReq, "cacheable");

		boolean smallImage = ParamUtil.getBoolean(uploadReq, "smallImage");
		String smallImageURL = ParamUtil.getString(uploadReq, "smallImageURL");
		File smallFile = uploadReq.getFile("smallFile");

		String[] communityPermissions = uploadReq.getParameterValues(
			"communityPermissions");
		String[] guestPermissions = uploadReq.getParameterValues(
			"guestPermissions");

		JournalTemplate template = null;

		if (cmd.equals(Constants.ADD)) {

			// Add template

			template = JournalTemplateServiceUtil.addTemplate(
				templateId, autoTemplateId, layout.getPlid(), structureId, name,
				description, xsl, formatXsl, langType, cacheable, smallImage,
				smallImageURL, smallFile, communityPermissions,
				guestPermissions);
		}
		else {

			// Update template

			template = JournalTemplateServiceUtil.updateTemplate(
				groupId, templateId, structureId, name, description, xsl,
				formatXsl, langType, cacheable, smallImage, smallImageURL,
				smallFile);
		}

		// Recent templates

		JournalUtil.addRecentTemplate(req, template);

		return template;
	}

}