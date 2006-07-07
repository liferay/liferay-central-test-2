/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.GroupPermission;
import com.liferay.portal.service.permission.LayoutPermission;
import com.liferay.portal.service.permission.PortletPermission;
import com.liferay.portal.service.spring.PortletLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.util.ParamUtil;
import com.liferay.util.PropertiesUtil;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.DynaClass;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.util.LabelValueBean;

/**
 * <a href="EditLookAndFeelAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EditLookAndFeelAction extends PortletAction {

	static List listBorderWidths = new ArrayList();
	static List listBorderStyles = new ArrayList();
	static List listImageAttachments = new ArrayList();
	static List listImagePositions = new ArrayList();
	static List listImageTiling = new ArrayList();
	static List listFonts = new ArrayList();
	static List listFontSizes = new ArrayList();
	static List listFontDecorations = new ArrayList();

	static {
		listBorderWidths.add(new LabelValueBean("None", ""));
		listBorderWidths.add(new LabelValueBean("1px", "1px"));
		listBorderWidths.add(new LabelValueBean("2px", "2px"));
		listBorderWidths.add(new LabelValueBean("3px", "3px"));
		listBorderWidths.add(new LabelValueBean("4px", "4px"));

		listBorderStyles.add(new LabelValueBean("None", ""));
		listBorderStyles.add(new LabelValueBean("Dotted", "dotted"));
		listBorderStyles.add(new LabelValueBean("Dashed", "dashed"));
		listBorderStyles.add(new LabelValueBean("Solid", "solid"));
		listBorderStyles.add(new LabelValueBean("Double", "double"));
		listBorderStyles.add(new LabelValueBean("Groove", "groove"));
		listBorderStyles.add(new LabelValueBean("Ridge", "ridge"));
		listBorderStyles.add(new LabelValueBean("Inset", "inset"));
		listBorderStyles.add(new LabelValueBean("Outset", "outset"));

		listImageAttachments.add(new LabelValueBean("Default", ""));
		listImageAttachments.add(new LabelValueBean("Scroll", "scroll"));
		listImageAttachments.add(new LabelValueBean("Fixed", "fixed"));

		listImagePositions.add(new LabelValueBean("Auto", ""));
		listImagePositions.add(new LabelValueBean("Top left", "top left"));
		listImagePositions.add(new LabelValueBean("Top center", "top center"));
		listImagePositions.add(
			new LabelValueBean("Center left", "center left"));
		listImagePositions.add(new LabelValueBean("Center", "center center"));
		listImagePositions.add(
			new LabelValueBean("Center right", "center right"));
		listImagePositions.add(
			new LabelValueBean("Bottom left", "bottom left"));
		listImagePositions.add(
			new LabelValueBean("Bottom center", "bottom center"));
		listImagePositions.add(
			new LabelValueBean("Bottom right", "bottom right"));

		listImageTiling.add(new LabelValueBean("Full tile", ""));
		listImageTiling.add(new LabelValueBean("None", "no-repeat"));
		listImageTiling.add(new LabelValueBean("Horizontally", "repeat-x"));
		listImageTiling.add(new LabelValueBean("Vertically", "repeat-y"));

		listFonts.add(new LabelValueBean("Default", ""));
		listFonts.add(new LabelValueBean("Arial", "Arial"));
		listFonts.add(new LabelValueBean("Courier New", "\"Courier New\""));
		listFonts.add(new LabelValueBean("Georgia", "Georgia"));
		listFonts.add(new LabelValueBean("Helvetica", "Helvetica"));
		listFonts.add(
			new LabelValueBean("Lucida Console", "\"Lucida Console\""));
		listFonts.add(
			new LabelValueBean("Times New Roman", "\"Times New Roman\""));
		listFonts.add(new LabelValueBean("Verdana", "Verdana"));

		listFontSizes.add(new LabelValueBean("Default", ""));
		listFontSizes.add(new LabelValueBean("XXS", "0.76em"));
		listFontSizes.add(new LabelValueBean("XS", "0.84em"));
		listFontSizes.add(new LabelValueBean("Small", "0.92em"));
		listFontSizes.add(new LabelValueBean("Large", "1.8em"));
		listFontSizes.add(new LabelValueBean("XL", "1.16em"));
		listFontSizes.add(new LabelValueBean("XXL", "1.24em"));

		listFontDecorations.add(new LabelValueBean("None", ""));
		listFontDecorations.add(new LabelValueBean("Underline", "underline"));
		listFontDecorations.add(new LabelValueBean("Overline", "overline"));
		listFontDecorations.add(
			new LabelValueBean("Strike through", "line-through"));
	}

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		try {
			checkPermissions(req);
		}
		catch (PrincipalException pe) {
			return;
		}

		updateLookAndFeel(mapping, (DynaActionForm)form, req);

		String redirect = ParamUtil.getString(req, "lookAndFeelRedirect");

		sendRedirect(req, res, redirect);
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		try {
			checkPermissions(req);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(req, PrincipalException.class.getName());

			return mapping.findForward("portlet.portlet_configuration.error");
		}

		DynaActionForm stylesForm = (DynaActionForm)form;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		String portletResource = ParamUtil.getString(req, "portletResource");

		PortletPreferences portletSetup =
			PortletPreferencesFactory.getPortletSetup(
				req, portletResource, true, true);

		String portletCss = portletSetup.getValue(
			"portlet-setup-css", StringPool.BLANK);

		Properties portletProperties = PropertiesUtil.load(portletCss);

		Enumeration keys = portletProperties.keys();

		stylesForm.initialize(mapping);

		while (keys.hasMoreElements()) {
			String key = (String)keys.nextElement();

			if (!key.startsWith("list")) {
				stylesForm.set(key, portletProperties.getProperty(key));
			}
		}

		populateCollections(stylesForm);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletResource);

		ServletContext ctx =
			(ServletContext)req.getAttribute(WebKeys.CTX);

		res.setTitle(
			PortalUtil.getPortletTitle(portlet, ctx, themeDisplay.getLocale()));

		DynaClass dynaClass = stylesForm.getDynaClass();

		req.setAttribute(WebKeys.FORM_NAME, dynaClass.getName());

		return mapping.findForward(
			"portlet.portlet_configuration.edit_look_and_feel");
	}

	protected void checkPermissions(PortletRequest req) throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		String portletId = ParamUtil.getString(req, "portletResource");

		if (!GroupPermission.contains(
				permissionChecker, themeDisplay.getPortletGroupId(),
				ActionKeys.MANAGE_LAYOUTS) &&
			!LayoutPermission.contains(
				permissionChecker, themeDisplay.getLayout(),
				ActionKeys.UPDATE) &&
			!PortletPermission.contains(
				permissionChecker, themeDisplay.getPlid(), portletId,
				ActionKeys.CONFIGURATION)) {

			throw new PrincipalException();
		}
	}

	protected void populateCollections(DynaActionForm form) {
		form.set("listBorderWidths", listBorderWidths);
		form.set("listBorderStyles", listBorderStyles);
		form.set("listImageAttachments", listImageAttachments);
		form.set("listImagePositions", listImagePositions);
		form.set("listImageTiling", listImageTiling);
		form.set("listFonts", listFonts);
		form.set("listFontSizes", listFontSizes);
		form.set("listFontDecorations", listFontDecorations);
	}

	protected void updateLookAndFeel(
			ActionMapping mapping, DynaActionForm form, ActionRequest req)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		String portletResource = ParamUtil.getString(req, "portletResource");

		PortletPreferences portletSetup =
			PortletPreferencesFactory.getPortletSetup(
				req, portletResource, true, true);

		String title = ParamUtil.getString(req, "title");
		boolean showBorders = ParamUtil.getBoolean(req, "showBorders");

		if (cmd.equals(Constants.RESET)) {
			form.initialize(mapping);

			title = StringPool.BLANK;
			showBorders = true;
		}

		StringBuffer sb = new StringBuffer();

		Map styleMap = form.getMap();

		Iterator itr = styleMap.keySet().iterator();

		while (itr.hasNext()) {
			String key = (String)itr.next();

			if (!key.startsWith("list")) {
				String value = form.getString(key);

				if (Validator.isNotNull(value)) {
					sb.append(key + "=" + value + "\n");
				}
			}
		}

		portletSetup.setValue("portlet-setup-title", title);
		portletSetup.setValue(
			"portlet-setup-show-borders", String.valueOf(showBorders));
		portletSetup.setValue("portlet-setup-css", sb.toString());

		portletSetup.store();
	}

}