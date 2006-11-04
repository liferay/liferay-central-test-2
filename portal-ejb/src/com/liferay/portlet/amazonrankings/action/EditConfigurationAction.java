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

package com.liferay.portlet.amazonrankings.action;

import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.Constants;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionMessages;

import java.util.Arrays;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditConfigurationAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EditConfigurationAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(req, Constants.CMD);

			if (!cmd.equals(Constants.UPDATE)) {
				return;
			}

			String[] isbns = StringUtil.split(
				ParamUtil.getString(req, "isbns").toUpperCase(),
				StringPool.SPACE);

			Arrays.sort(isbns);

			String portletResource = ParamUtil.getString(
				req, "portletResource");

			PortletPreferences prefs =
				PortletPreferencesFactory.getPortletSetup(
					req, portletResource, true, true);

			prefs.setValues("isbns", isbns);

			prefs.store();

			SessionMessages.add(req, config.getPortletName() + ".doConfigure");
		}
		catch (ValidatorException ve) {
			SessionErrors.add(req, ValidatorException.class.getName(), ve);
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		return mapping.findForward(
			"portlet.amazon_rankings.edit_configuration");
	}

}