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

<%@ include file="/html/taglib/init.jsp" %>

<%
String title = (String)request.getAttribute("liferay-ui:panel:title");
String id = (String)request.getAttribute("liferay-ui:panel:id");
Boolean collapsible = (Boolean)request.getAttribute("liferay-ui:panel:collapsible");
String defaultState = (String)request.getAttribute("liferay-ui:panel:defaultState");
Boolean persistState = (Boolean)request.getAttribute("liferay-ui:panel:persistState");
String cssClass = (String)request.getAttribute("liferay-ui:panel:cssClass");
Boolean extended = (Boolean)request.getAttribute("liferay-ui:panel:extended");

String randomNamespace = portletResponse.getNamespace();

IntegerWrapper panelCount = (IntegerWrapper)request.getAttribute("liferay-ui:panel-container:panel-count");

if (Validator.isNotNull(id) && id.indexOf(randomNamespace) == -1) {
	id = randomNamespace + id;
}

if (Validator.isNotNull(panelCount)) {
	id += panelCount.increment();
	Boolean containerExtended = (Boolean)request.getAttribute("liferay-ui:panel-container:extended");

	if (Validator.isNotNull(containerExtended)) {
		extended = containerExtended;
	}
}

cssClass = "lfr-panel " + cssClass;

if (collapsible) {
	cssClass += " lfr-collapsible";
}

String panelState = GetterUtil.getString(SessionClicks.get(request, id, null), defaultState);

if (!panelState.equals("open")) {
	cssClass += " lfr-collapsed";
}

if (extended) {
	cssClass += " lfr-extended";
}

%>