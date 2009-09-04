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
User selUser = (User)request.getAttribute("user.selUser");
%>

<h3><liferay-ui:message key="pages" /></h3>

<fieldset class="aui-block-labels">

	<%
	List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
	%>

	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />publicLayoutPrototypeId"><liferay-ui:message key="public-pages" /></label>

		<c:choose>
			<c:when test="<%= ((selUser == null) || (selUser.getPublicLayoutsPageCount() == 0)) && !layoutSetPrototypes.isEmpty() %>">
				<select id="<portlet:namespace />publicLayoutPrototypeId" name="<portlet:namespace />publicLayoutSetPrototypeId">
					<option selected value="">(<liferay-ui:message key='<%= selUser == null ? "default" : "none" %>' />)</option>

					<%
					for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
					%>

						<option value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>"><%= layoutSetPrototype.getName(user.getLanguageId()) %></option>

					<%
					}
					%>

				</select>
			</c:when>
			<c:when test="<%= (selUser != null) && (selUser.getPublicLayoutsPageCount() > 0) %>">
				<liferay-portlet:actionURL var="publicPagesURL"  portletName="<%= PortletKeys.MY_PLACES %>">
					<portlet:param name="struts_action" value="/my_places/view" />
					<portlet:param name="groupId" value="<%= String.valueOf(selUser.getGroup().getGroupId()) %>" />
					<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
				</liferay-portlet:actionURL>

				<liferay-ui:icon image="view" message="open-public-pages" url="<%= publicPagesURL.toString() %>" method="get" target="_blank" label="<%= true %>" /> (<liferay-ui:message key="new-window" />)
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="this-user-does-not-have-any-public-pages" />
			</c:otherwise>
		</c:choose>
	</div>

	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />privateLayoutPrototypeId"><liferay-ui:message key="private-pages" /></label>

		<c:choose>
			<c:when test="<%= ((selUser == null) || (selUser.getPrivateLayoutsPageCount() == 0)) && !layoutSetPrototypes.isEmpty() %>">
				<select id="<portlet:namespace />privateLayoutPrototypeId" name="<portlet:namespace />privateLayoutSetPrototypeId">
					<option selected value="">(<liferay-ui:message key='<%= selUser == null ? "default" : "none" %>'/>)</option>

					<%
					for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
					%>

						<option value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>"><%= layoutSetPrototype.getName(user.getLanguageId()) %></option>

					<%
					}
					%>

				</select>
			</c:when>
			<c:when test="<%= (selUser != null) && (selUser.getPrivateLayoutsPageCount() > 0) %>">

				<%
				PortletURL privatePagesURL = renderResponse.createActionURL();

				privatePagesURL.setWindowState(WindowState.NORMAL);

				privatePagesURL.setParameter("struts_action", "/communities/page");
				privatePagesURL.setParameter("redirect", currentURL);

				privatePagesURL.setParameter("groupId", String.valueOf(selUser.getGroup().getGroupId()));
				privatePagesURL.setParameter("privateLayout", Boolean.TRUE.toString());
				%>

				<liferay-ui:icon image="view" message="open-private-pages" url="<%= privatePagesURL.toString() %>" method="get" target="_blank" label="<%= true %>" /> (<liferay-ui:message key="new-window" />)
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="this-user-does-not-have-any-private-pages" />
			</c:otherwise>
		</c:choose>
	</div>
</fieldset>

<%
if ((selUser == null)  && layoutSetPrototypes.isEmpty()) {
	request.setAttribute(WebKeys.FORM_NAVIGATOR_SECTION_SHOW + "pages", Boolean.FALSE);
}
%>