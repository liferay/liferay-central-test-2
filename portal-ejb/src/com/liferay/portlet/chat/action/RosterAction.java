/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.chat.action;

import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messaging.util.MessagingUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import org.json.JSONObject;

/**
 * <a href="RosterAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ming-Gih Lam
 *
 */
public class RosterAction extends JSONAction {

	public String getJSON(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);
		JSONObject jo = new JSONObject();

		if ("addEntry".equals(cmd)) {
			jo = addEntry(req);
		}
		else if ("getEntries".equals(cmd)) {
			jo = MessagingUtil.getRosterEntries(req.getSession());
		}
		else if ("deleteEntries".equals(cmd)) {
			jo = deleteEntries(req);
		}

		return jo.toString();
	}

	JSONObject addEntry(HttpServletRequest req) {
		JSONObject jo = new JSONObject();

		try {
			String companyId = PortalUtil.getCompanyId(req);

			String userId = ParamUtil.getString(req, "userId");
			String email = ParamUtil.getString(req, "email");

			User user = null;

			if (Validator.isNotNull(userId)) {
				user = UserLocalServiceUtil.getUserById(
					companyId, userId);
			}
			else {
				user = UserLocalServiceUtil.getUserByEmailAddress(
					companyId, email);
			}

			jo = MessagingUtil.addRosterEntry(req.getSession(), user);
		}
		catch (Exception e) {
			jo.put("status", "failure");
		}

		return jo;
	}

	protected JSONObject deleteEntries(HttpServletRequest req) {
		String rosterList = ParamUtil.getString(req, "entries");
		String entries[] = StringUtil.split(rosterList, ",");

		JSONObject jo = new JSONObject();

		try {
			MessagingUtil.deleteRosterEntries(req.getSession(), entries);
			jo.put("status", "success");
		}
		catch (Exception e) {
			jo.put("status", "failure");
		}

		return jo;
	}

}