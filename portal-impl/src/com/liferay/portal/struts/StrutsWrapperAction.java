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

import com.liferay.portal.kernel.struts.StrutsAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Mika Koivisto
 */
public class StrutsWrapperAction extends Action {

	public StrutsWrapperAction(StrutsAction action) {
		_strutsAction = action;
	}

	public ActionForward execute(
		ActionMapping mapping, ActionForm form, HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		String forward = _strutsAction.execute(request, response);

		if (forward != null) {
			ActionForward actionForward = mapping.findForward(forward);

			if (actionForward == null) {
				actionForward = new ActionForward(forward);
			}

			return actionForward;
		}

		return null;
	}

	private StrutsAction _strutsAction;
}