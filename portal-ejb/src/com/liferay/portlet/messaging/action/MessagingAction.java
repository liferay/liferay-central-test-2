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

package com.liferay.portlet.messaging.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.liferay.portal.model.User;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messaging.util.MessagingUtil;
import com.liferay.util.ParamUtil;

/**
 * <a href="MessagingAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ming-Gih Lam
 *
 */
public class MessagingAction extends JSONAction {

	public String getJSON(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);
		JSONObject jo = new JSONObject();

		if ("getChats".equals(cmd)) {
			jo = MessagingUtil.getChatMessages(req.getSession());
		}
		else if ("sendChat".equals(cmd)) {
			jo = sendMessage(req);
		}

		return jo.toString();
	}

	protected JSONObject sendMessage(HttpServletRequest req) {
		JSONObject jo = new JSONObject();

		try {
			String bodyText = ParamUtil.getString(req, "text");
			String tempId = ParamUtil.getString(req, "tempId", null);
			String toId = ParamUtil.getString(req, "toId");
			String toAddr = ParamUtil.getString(req, "toAddr", null);
			String companyId = PortalUtil.getCompanyId(req);
			User fromUser = PortalUtil.getUser(req);
			User toUser;

			if (toAddr != null) {
				toUser = UserLocalServiceUtil.getUserByEmailAddress(companyId,
					toAddr);
				toId = toUser.getUserId();
			}
			else {
				toUser = UserLocalServiceUtil.getUserById(toId);
			}

			MessagingUtil.sendMessage(req.getSession(), PortalUtil
				.getUserId(req), fromUser.getFullName(), toId, toUser
				.getFullName(), bodyText);

			jo.put("status", "success");
			jo.put("toId", toId);
			jo.put("toName", toUser.getFullName());
			jo.put("fromId", PortalUtil.getUserId(req));
			jo.put("fromName", fromUser.getFullName());
			if (tempId != null) {
				jo.put("tempId", tempId);
			}
		}
		catch (Exception e) {
			jo.put("status", "failure");
		}

		return jo;
	}

}