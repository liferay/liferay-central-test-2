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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.wiki.DuplicateNodeNameException;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.NodeNameException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.service.WikiNodeServiceUtil;
import com.liferay.portlet.wiki.util.WikiCacheThreadLocal;
import com.liferay.portlet.wiki.util.WikiCacheUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditNodeAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EditNodeAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateNode(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteNode(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribeNode(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribeNode(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.wiki.error");
			}
			else if (e instanceof DuplicateNodeNameException ||
					 e instanceof NodeNameException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
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

		try {
			ActionUtil.getNode(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.wiki.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.wiki.edit_node"));
	}

	protected void deleteNode(ActionRequest actionRequest) throws Exception {
		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");

		String oldName = getNodeName(nodeId);

		WikiCacheThreadLocal.setClearCache(false);

		WikiNodeServiceUtil.deleteNode(nodeId);

		WikiCacheUtil.clearCache(nodeId);

		WikiCacheThreadLocal.setClearCache(true);

		updatePreferences(actionRequest, oldName, StringPool.BLANK);
	}

	protected String getNodeName(long nodeId) throws Exception {
		WikiNode node = WikiNodeServiceUtil.getNode(nodeId);

		return node.getName();
	}

	protected void subscribeNode(ActionRequest actionRequest)
		throws Exception {

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");

		WikiNodeServiceUtil.subscribeNode(nodeId);
	}

	protected void unsubscribeNode(ActionRequest actionRequest)
		throws Exception {

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");

		WikiNodeServiceUtil.unsubscribeNode(nodeId);
	}

	protected void updateNode(ActionRequest actionRequest) throws Exception {
		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			WikiNode.class.getName(), actionRequest);

		if (nodeId <= 0) {

			// Add node

			WikiNodeServiceUtil.addNode(name, description, serviceContext);
		}
		else {

			// Update node

			String oldName = getNodeName(nodeId);

			WikiNodeServiceUtil.updateNode(nodeId, name, description);

			updatePreferences(actionRequest, oldName, name);
		}
	}

	protected void updatePreferences(
			ActionRequest actionRequest, String oldName, String newName)
		throws Exception {

		PortletPreferences preferences = actionRequest.getPreferences();

		String hiddenNodes = preferences.getValue(
			"hidden-nodes", StringPool.BLANK);
		String visibleNodes = preferences.getValue(
			"visible-nodes", StringPool.BLANK);

		String regex = oldName + ",?";

		preferences.setValue(
			"hidden-nodes", hiddenNodes.replaceFirst(regex, newName));
		preferences.setValue(
			"visible-nodes",
			visibleNodes.replaceFirst(regex, newName));

		preferences.store();
	}

}