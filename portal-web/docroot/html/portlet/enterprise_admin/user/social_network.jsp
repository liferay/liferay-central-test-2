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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
Contact selContact = (Contact)request.getAttribute("user.selContact");
%>

<h3><liferay-ui:message key="social-network" /></h3>

<c:choose>
	<c:when test="<%= selContact != null %>">
		<fieldset class="exp-block-labels">
			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />facebookSn"><liferay-ui:message key="facebook" /></label>

				<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="facebookSn" />

				<img alt="<liferay-ui:message key="facebook" />" class="social-network-logo" src="<%= themeDisplay.getPathThemeImages() %>/enterprise_admin/facebook.jpg" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />mySpaceSn"><liferay-ui:message key="myspace" /></label>

				<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="mySpaceSn" />

				<img alt="<liferay-ui:message key="myspace" />" class="social-network-logo" src="<%= themeDisplay.getPathThemeImages() %>/enterprise_admin/myspace.jpg" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />twitterSn"><liferay-ui:message key="twitter" /></label>

				<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="twitterSn" />

				<img alt="<liferay-ui:message key="twitter" />" class="social-network-logo" src="<%= themeDisplay.getPathThemeImages() %>/enterprise_admin/twitter.jpg" />
			</div>
		</fieldset>
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<liferay-ui:message key="this-section-will-be-editable-after-creating-the-user" />
		</div>
	</c:otherwise>
</c:choose>