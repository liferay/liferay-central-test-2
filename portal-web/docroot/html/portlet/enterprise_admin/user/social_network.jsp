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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
Contact selContact = (Contact)request.getAttribute("user.selContact");
%>

<h3><liferay-ui:message key="social-network" /></h3>

<fieldset class="block-labels">
	<div class="ctrl-holder">
		<label for="<portlet:namespace />facebookSn"><liferay-ui:message key="facebook" /></label>

		<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="facebookSn" />
		<img alt="Facebook" class="lfr-social-icon" height="25px" src="<%=themeDisplay.getPathThemeImages()%>/enterprise_admin/facebook_logo.jpg" />
	</div>

	<div class="ctrl-holder">
		<label for="<portlet:namespace />mySpaceSn"><liferay-ui:message key="myspace" /></label>

		<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="mySpaceSn" />
		<img alt="MySpace" class="lfr-social-icon" height="25px" src="<%=themeDisplay.getPathThemeImages()%>/enterprise_admin/myspace_logo.jpg" />
	</div>

	<div class="ctrl-holder">
		<label for="<portlet:namespace />twitterSn">	<liferay-ui:message key="twitter" /></label>

		<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="twitterSn" />
		<img alt="Twitter" class="lfr-social-icon" height="25px" src="<%=themeDisplay.getPathThemeImages()%>/enterprise_admin/twitter_logo.jpg" />
	</div>
</fieldset>