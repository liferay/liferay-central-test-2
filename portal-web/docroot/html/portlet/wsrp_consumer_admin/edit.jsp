<%
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
%>

<%
/**
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
 */
%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="javax.portlet.PortletPreferences" %>
<%@page import="com.sun.portal.wsrp.consumer.portlets.AdminPortletAction"%>

<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<portlet:defineObjects/>
<fmt:setBundle basename="ConsumerAdminPortlet" />

<%
	String inaccessibleHostMBeanServer = (String)renderRequest.getPortletSession().getAttribute("consumer_mbeanServerNotAccessible");
	if(inaccessibleHostMBeanServer != null){
%>
		<span style="color:red">
			<fmt:message key="mbean.server.notstarted" />
			<br />
			<b><%=inaccessibleHostMBeanServer%></b>
		</span>
<%
	}
	PortletPreferences prefs = renderRequest.getPreferences();
	String consumerHost = prefs.getValue("consumerHost","localhost");
	String isLocal = prefs.getValue("isLocal", "true");
	String checkBoxStatus = "";
	if(isLocal.equals("true")){
		checkBoxStatus = "checked";
	}
%>

<form name="updatePrefForm" action="<%=renderResponse.createActionURL().toString()%>" method="post">
	<table border="0" width="75%" cellspacing="2" cellpadding="2">
		<thead style="text-align:left;">
			<tr>
				<th colspan="2" scope="col"><fmt:message key="consumer.text" /></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="labelStyle" width="35%"><label for="consumerHostId"><fmt:message key="consumer.host" /></label></td>
				<td><input type="text" class="textboxStyle" id="consumerHostId" name="consumerHost" value="<%=consumerHost%>" /></td>
			</tr>
			<tr>
				<td class="labelStyle" width="35%"><label for="isLocalId"><fmt:message key="isLocal" /></label></td>
				<td><input type="checkbox" class="checkboxStyle"  name="isLocal" "<%=checkBoxStatus%>" id="isLocalId" /></td>
			</tr>
		</tbody>
	</table>
<input type="hidden" name="action" value="<%=AdminPortletAction.CANCEL_EDIT%>" />
<input class="buttonStyle" type="submit" onClick="action.value='<%=AdminPortletAction.UPDATE_PREF%>';" value='<fmt:message key="submit" />'/>
<input class="buttonStyle" type="submit" onClick="action.value='<%=AdminPortletAction.CANCEL_EDIT%>';" value='<fmt:message key="cancel" />'/>
</form>