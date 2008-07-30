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

 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 **/

package com.liferay.taglib.ruon;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="UserCommunicationTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Murali Krishna Reddy
 */
public class UserCommunicationTag extends TagSupport{

	public int doStartTag() throws JspException {
		try {
			String communicationWays = "";

			JSONObject ruonCommunicationJSON =
				JSONFactoryUtil.createJSONObject();

			JSONObject communicationWaysRequestJSON =
				JSONFactoryUtil.createJSONObject();

			communicationWaysRequestJSON.put("userId",_userId);
			communicationWaysRequestJSON.put("loggedInUserId",_loggedInUserId);

			ruonCommunicationJSON.put(
				"communicationWaysRequest",communicationWaysRequestJSON);

			String ruonCommunicationResponse =
				MessageBusUtil.sendSynchronizedMessage(
					DestinationNames.RUON, ruonCommunicationJSON.toString());

			if(ruonCommunicationResponse != null){

				JSONObject ruonCommunicationResponseJSON =
					JSONFactoryUtil.createJSONObject(ruonCommunicationResponse);

				JSONObject communicationWaysResponseJSON =
					ruonCommunicationResponseJSON.getJSONObject(
						"communicationWaysResponse");

				if(communicationWaysResponseJSON != null){
					communicationWays =
						communicationWaysResponseJSON.getString(
							"communicationWays");
				}
			}

				pageContext.getOut().print(communicationWays);

		}
		catch (Exception e) {
			throw new JspException(e);
		}

		return SKIP_BODY;
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

	public void setUserId(Long userId) {
		_userId = userId;
	}

	public void setLoggedInUserId(Long loggedInUserId){
		_loggedInUserId = loggedInUserId;
	}

	private Long _userId;
	private Long _loggedInUserId;

}
