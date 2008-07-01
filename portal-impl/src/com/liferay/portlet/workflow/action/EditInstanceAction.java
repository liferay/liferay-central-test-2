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

package com.liferay.portlet.workflow.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.workflow.model.WorkflowInstance;
import com.liferay.portlet.workflow.service.WorkflowInstanceServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditInstanceAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EditInstanceAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				addInstance(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.SIGNAL)) {
				signalInstance(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.workflow.error");
			}
			else {
				throw e;
			}
		}
	}

	protected void addInstance(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long definitionId = ParamUtil.getLong(actionRequest, "definitionId");

		WorkflowInstance instance =
			WorkflowInstanceServiceUtil.addInstance(definitionId);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		redirect += "&instanceId=" + instance.getInstanceId();

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	protected void signalInstance(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long instanceId = ParamUtil.getLong(actionRequest, "instanceId");
		long tokenId = ParamUtil.getLong(actionRequest, "tokenId");

		if (tokenId <= 0) {
			WorkflowInstanceServiceUtil.signalInstance(instanceId);
		}
		else {
			WorkflowInstanceServiceUtil.signalToken(instanceId, tokenId);
		}

		sendRedirect(actionRequest, actionResponse);
	}

}