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

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_community" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveGroup(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />groupId" type="hidden" value="<%= groupId %>" />
<input name="<portlet:namespace />friendlyURL" type="hidden" value="<%= HtmlUtil.escapeAttribute(friendlyURL) %>" />

<c:if test="<%= !portletName.equals(PortletKeys.ADMIN_SERVER) %>">
	<liferay-util:include page="/html/portlet/communities/toolbar.jsp">
		<liferay-util:param name="toolbarItem" value='<%= (group == null) ? "add" : "view-all" %>' />
	</liferay-util:include>
</c:if>

<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
<liferay-ui:error exception="<%= GroupNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= RequiredGroupException.class %>" message="old-group-name-is-a-required-system-group" />

<table class="lfr-table">

<c:if test="<%= group != null %>">
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="group-id" />
		</td>
		<td>
			<%= groupId %>
		</td>
	</tr>
</c:if>

<tr>
	<td class="lfr-label">
		<liferay-ui:message key="name" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= Group.class %>" bean="<%= group %>" field="name" />
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="description" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= Group.class %>" bean="<%= group %>" field="description" />
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="type" />
	</td>
	<td>
		<select name="<portlet:namespace />type">
			<option <%= (type == GroupConstants.TYPE_COMMUNITY_OPEN) ? "selected" : "" %> value="<%= GroupConstants.TYPE_COMMUNITY_OPEN %>"><liferay-ui:message key="open" /></option>
			<option <%= (type == GroupConstants.TYPE_COMMUNITY_RESTRICTED) ? "selected" : "" %> value="<%= GroupConstants.TYPE_COMMUNITY_RESTRICTED %>"><liferay-ui:message key="restricted" /></option>
			<option <%= (type == GroupConstants.TYPE_COMMUNITY_PRIVATE) ? "selected" : "" %> value="<%= GroupConstants.TYPE_COMMUNITY_PRIVATE %>"><liferay-ui:message key="private" /></option>
		</select>
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="active" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= Group.class %>" bean="<%= group %>" field="active" defaultValue="<%= Boolean.TRUE %>" />
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="categories" />
	</td>
	<td>
		<liferay-ui:asset-categories-selector
			className="<%= Group.class.getName() %>"
			classPK="<%= groupId %>"
		/>
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="tags" />
	</td>
	<td>
		<liferay-ui:asset-tags-selector
			className="<%= Group.class.getName() %>"
			classPK="<%= groupId %>"
		/>
	</td>
</tr>

<%
List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
%>

<c:if test="<%= (group != null) || !layoutSetPrototypes.isEmpty() %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="public-pages" />
		</td>
		<td colspan="2">
			<c:choose>
				<c:when test="<%= ((group == null) || (group.getPublicLayoutsPageCount() == 0)) && !layoutSetPrototypes.isEmpty() %>">
					<select id="<portlet:namespace />publicLayoutPrototypeId" name="<portlet:namespace />publicLayoutSetPrototypeId">
						<option selected value="">(<liferay-ui:message key="none" />)</option>

						<%
						for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
						%>

							<option value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>"><%= layoutSetPrototype.getName(user.getLanguageId()) %></option>

						<%
						}
						%>

					</select>
				</c:when>
				<c:when test="<%= (group != null) && (group.getPublicLayoutsPageCount() > 0) %>">
					<liferay-portlet:actionURL var="publicPagesURL"  portletName="<%= PortletKeys.MY_PLACES %>">
						<portlet:param name="struts_action" value="/my_places/view" />
						<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
						<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
					</liferay-portlet:actionURL>

					<liferay-ui:icon image="view" message="open-public-pages" url="<%= publicPagesURL %>" method="get" target="_blank" label="<%= true %>" /> (<liferay-ui:message key="new-window" />)
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="this-community-does-not-have-any-public-pages" />
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="private-pages" />
		</td>
		<td colspan="2">
			<c:choose>
				<c:when test="<%= ((group == null) || (group.getPrivateLayoutsPageCount() == 0)) && !layoutSetPrototypes.isEmpty() %>">
					<select id="<portlet:namespace />privateLayoutPrototypeId" name="<portlet:namespace />privateLayoutSetPrototypeId">
						<option selected value="">(<liferay-ui:message key="none" />)</option>

						<%
						for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
						%>

							<option value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>"><%= layoutSetPrototype.getName(user.getLanguageId()) %></option>

						<%
						}
						%>

					</select>
				</c:when>
				<c:when test="<%= (group != null) && (group.getPrivateLayoutsPageCount() > 0) %>">
					<liferay-portlet:actionURL var="privatePagesURL"  portletName="<%= PortletKeys.MY_PLACES %>">
						<portlet:param name="struts_action" value="/my_places/view" />
						<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
						<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
					</liferay-portlet:actionURL>

					<liferay-ui:icon image="view" message="open-private-pages" url="<%= privatePagesURL %>" method="get" target="_blank" label="<%= true %>" /> (<liferay-ui:message key="new-window" />)
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="this-community-does-not-have-any-private-pages" />
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</c:if>

</table>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</script>
</c:if>