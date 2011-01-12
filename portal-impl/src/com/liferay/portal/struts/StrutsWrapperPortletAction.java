/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.struts;

import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Mika Koivisto
 */
public class StrutsWrapperPortletAction extends PortletAction {

	public StrutsWrapperPortletAction(StrutsPortletAction action) {
		_strutsAction = action;
	}

	public void processAction(
		ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
		ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_strutsAction.processAction(
			portletConfig, actionRequest, actionResponse);
	}


	public ActionForward render(
		ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
		RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		String forward = _strutsAction.render(
			portletConfig, renderRequest, renderResponse);

		if (Validator.isNotNull(forward)) {
			ActionForward actionForward = mapping.findForward(forward);

			if (actionForward == null) {
				actionForward = new ActionForward(forward);
			}

			return actionForward;
		}

		return null;
	}


	public void serveResource(
		ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
		ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		_strutsAction.serveResource(
			portletConfig, resourceRequest, resourceResponse);
	}

	private StrutsPortletAction _strutsAction;
}
