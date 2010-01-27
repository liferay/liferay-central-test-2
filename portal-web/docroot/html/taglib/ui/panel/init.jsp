<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%
boolean collapsible = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:panel:collapsible"));
String cssClass = (String)request.getAttribute("liferay-ui:panel:cssClass");
String defaultState = (String)request.getAttribute("liferay-ui:panel:defaultState");
boolean extended = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:panel:extended"));
String id = (String)request.getAttribute("liferay-ui:panel:id");
boolean persistState = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:panel:persistState"));
String title = (String)request.getAttribute("liferay-ui:panel:title");

IntegerWrapper panelCount = (IntegerWrapper)request.getAttribute("liferay-ui:panel-container:panel-count");

if (panelCount != null) {
	panelCount.increment();

	Boolean panelContainerExtended = (Boolean)request.getAttribute("liferay-ui:panel-container:extended");

	if (panelContainerExtended != null) {
		extended = panelContainerExtended.booleanValue();
	}
}

String panelState = GetterUtil.getString(SessionClicks.get(request, id, null), defaultState);

if (collapsible) {
	cssClass += " lfr-collapsible";
}

if (!panelState.equals("open")) {
	cssClass += " lfr-collapsed";
}

if (extended) {
	cssClass += " lfr-extended";
}
else {
	cssClass += " lfr-panel-basic";
}
%>