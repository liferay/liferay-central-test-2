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

package com.liferay.portlet.webform.action;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.webform.util.WebFormUtil;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="DeleteDataAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class DeleteDataAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		PortletConfigImpl configImpl = (PortletConfigImpl)config;

		String portletId = configImpl.getPortletId();

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getPortletSetup(req, portletId);

        String databaseTableName = 
	        prefs.getValue("databaseTableName", StringPool.BLANK);

		if (Validator.isNotNull(databaseTableName)) {
			ExpandoTableLocalServiceUtil.deleteTable(
				WebFormUtil.class.getName(), databaseTableName);
		}
		
		sendRedirect(req, res);
	}

}