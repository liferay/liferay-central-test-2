<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
User selUser = PortalUtil.getSelectedUser(request);
%>

<c:choose>
	<c:when test='<%= SessionMessages.contains(renderRequest, "request_processed") %>'>
		<aui:script>
			window.close();
			opener.<portlet:namespace />changePortrait('<%= themeDisplay.getPathImage() %>/user_<%= selUser.isFemale() ? "female" : "male" %>_portrait?img_id=<%= selUser.getPortraitId() %>&t=<%= ImageServletTokenUtil.getToken(selUser.getPortraitId()) %>');
		</aui:script>
	</c:when>
	<c:otherwise>
		<portlet:actionURL var="editUserPortraitURL">
			<portlet:param name="struts_action" value="/enterprise_admin/edit_user_portrait" />
		</portlet:actionURL>

		<aui:form action="<%= editUserPortraitURL %>" enctype="multipart/form-data" method="post" name="fm">
			<aui:input name="p_u_i_d" type="hidden" value="<%= selUser.getUserId() %>" />

			<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />
			<liferay-ui:error exception="<%= UserPortraitSizeException.class %>" message="please-enter-a-file-with-a-valid-file-size" />
			<liferay-ui:error exception="<%= UserPortraitTypeException.class %>" message="please-enter-a-file-with-a-valid-file-type" />

			<aui:fieldset>
				<aui:input label='<%= LanguageUtil.format(pageContext, "upload-a-gif-or-jpeg-that-is-x-pixels-tall-and-x-pixels-wide", new Object[] {"120", "100"}, false) %>' name="fileName" size="50" type="file" />

				<aui:button-row>
					<aui:button type="submit" />

					<aui:button onClick="window.close();" type="cancel" value="close" />
				</aui:button-row>
			</aui:fieldset>
		</aui:form>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
			<aui:script>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />fileName);
			</aui:script>
		</c:if>
	</c:otherwise>
</c:choose>