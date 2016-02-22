/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.polls.web.display.portlet.action;

import com.liferay.polls.constants.PollsWebKeys;
import com.liferay.polls.exception.NoSuchQuestionException;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.struts.PortletAction;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAction extends PortletAction {

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			PortletPreferences portletPreferences =
				renderRequest.getPreferences();

			long questionId = GetterUtil.getLong(
				portletPreferences.getValue("questionId", StringPool.BLANK));

			if (questionId > 0) {
				PollsQuestion question = PollsQuestionServiceUtil.getQuestion(
					questionId);

				renderRequest.setAttribute(
					PollsWebKeys.POLLS_QUESTION, question);
			}
		}
		catch (Exception e) {
			if (!(e instanceof NoSuchQuestionException)) {
				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.polls_display.error");
			}
		}

		return actionMapping.findForward("portlet.polls_display.view");
	}

}