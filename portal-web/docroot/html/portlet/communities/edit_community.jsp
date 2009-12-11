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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

Group group = (Group)request.getAttribute(WebKeys.GROUP);

long groupId = BeanParamUtil.getLong(group, request, "groupId");

int type = BeanParamUtil.getInteger(group, request, "type");
String friendlyURL = BeanParamUtil.getString(group, request, "friendlyURL");
%>

<script type="text/javascript">
	function <portlet:namespace />saveGroup() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= group == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<portlet:actionURL var="editCommunityURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/communities/edit_community" />
</portlet:actionURL>

<aui:form action="<%= editCommunityURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveGroup(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="friendlyURL" type="hidden" value="<%= friendlyURL %>" />

	<c:if test="<%= !portletName.equals(PortletKeys.ADMIN_SERVER) %>">
		<liferay-util:include page="/html/portlet/communities/toolbar.jsp">
			<liferay-util:param name="toolbarItem" value='<%= (group == null) ? "add" : "view-all" %>' />
		</liferay-util:include>
	</c:if>

	<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= GroupNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= RequiredGroupException.class %>" message="old-group-name-is-a-required-system-group" />

	<aui:model-context bean="<%= group %>" model="<%= Group.class %>" />

	<aui:fieldset>
		<c:if test="<%= group != null %>">
			<aui:field-wrapper label="group-id">
				<%= groupId %>
			</aui:field-wrapper>
		</c:if>

		<aui:input name="name" />

		<aui:input name="description" />

		<aui:select name="type">
			<aui:option label="open" selected="<%= (type == GroupConstants.TYPE_COMMUNITY_OPEN) %>" value="<%= GroupConstants.TYPE_COMMUNITY_OPEN %>" />
			<aui:option label="restricted" selected="<%= (type == GroupConstants.TYPE_COMMUNITY_RESTRICTED) %>" value="<%= GroupConstants.TYPE_COMMUNITY_RESTRICTED %>" />
			<aui:option label="private" selected="<%= (type == GroupConstants.TYPE_COMMUNITY_PRIVATE) %>" value="<%= GroupConstants.TYPE_COMMUNITY_PRIVATE %>" />
		</aui:select>

		<aui:input inlineLabel="left" name="active" value="<%= true %>" />

		<aui:input name="categories" type="assetCategories" />

		<aui:input name="tags" type="assetTags" />

		<%
		List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
		%>

		<c:if test="<%= (group != null) || !layoutSetPrototypes.isEmpty() %>">
			<c:choose>
				<c:when test="<%= ((group == null) || (group.getPublicLayoutsPageCount() == 0)) && !layoutSetPrototypes.isEmpty() %>">
					<aui:select label="public-pages" name="publicLayoutSetPrototypeId">
						<aui:option label="none" selected="<%= true %>" value="" />

						<%
						for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
						%>

							<aui:option value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>"><%= layoutSetPrototype.getName(user.getLanguageId()) %></aui:option>

						<%
						}
						%>

					</aui:select>
				</c:when>
				<c:otherwise>
					<aui:field-wrapper label="public-pages">
						<c:choose>
							<c:when test="<%= (group != null) && (group.getPublicLayoutsPageCount() > 0) %>">
								<liferay-portlet:actionURL var="publicPagesURL" portletName="<%= PortletKeys.MY_PLACES %>">
									<portlet:param name="struts_action" value="/my_places/view" />
									<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
									<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
								</liferay-portlet:actionURL>

								<liferay-ui:icon image="view" message="open-public-pages" url="<%= publicPagesURL.toString() %>" method="get" target="_blank" label="<%= true %>" /> (<liferay-ui:message key="new-window" />)
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="this-community-does-not-have-any-public-pages" />
							</c:otherwise>
						</c:choose>
					</aui:field-wrapper>
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="<%= ((group == null) || (group.getPrivateLayoutsPageCount() == 0)) && !layoutSetPrototypes.isEmpty() %>">
					<aui:select label="private-pages" name="privateLayoutSetPrototypeId">
						<aui:option label="none" selected="<%= true %>" value="" />

						<%
						for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
						%>

							<aui:option value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>"><%= layoutSetPrototype.getName(user.getLanguageId()) %></aui:option>

						<%
						}
						%>

					</aui:select>
				</c:when>
				<c:otherwise>
					<aui:field-wrapper label="private-pages">
						<c:choose>
							<c:when test="<%= (group != null) && (group.getPrivateLayoutsPageCount() > 0) %>">
								<liferay-portlet:actionURL var="privatePagesURL" portletName="<%= PortletKeys.MY_PLACES %>">
									<portlet:param name="struts_action" value="/my_places/view" />
									<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
									<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
								</liferay-portlet:actionURL>

								<liferay-ui:icon image="view" message="open-private-pages" url="<%= privatePagesURL.toString() %>" method="get" target="_blank" label="<%= true %>" /> (<liferay-ui:message key="new-window" />)
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="this-community-does-not-have-any-private-pages" />
							</c:otherwise>
						</c:choose>
					</aui:field-wrapper>
				</c:otherwise>
			</c:choose>
		</c:if>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</script>
</c:if>

<%
if (group != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-community"), currentURL);
}
%>