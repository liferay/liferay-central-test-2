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

<%@ include file="/html/portlet/announcements/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

AnnouncementsEntry entry = (AnnouncementsEntry)request.getAttribute(WebKeys.ANNOUNCEMENTS_ENTRY);

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

String type = BeanParamUtil.getString(entry, request, "type");

Calendar displayDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

if (entry != null) {
	if (entry.getDisplayDate() != null) {
		displayDate.setTime(entry.getDisplayDate());
	}
}

Calendar expirationDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

expirationDate.add(Calendar.MONTH, 1);

if (entry != null) {
	if (entry.getExpirationDate() != null) {
		expirationDate.setTime(entry.getExpirationDate());
	}
}

int priority = BeanParamUtil.getInteger(entry, request, "priority");
%>

<aui:form method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveEntry(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="entryId" type="hidden" value="<%= entryId %>" />
	<aui:input name="alert" type="hidden" value="<%= portletName.equals(PortletKeys.ALERTS) %>" />

	<liferay-ui:tabs
		names="entry"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<liferay-ui:error exception="<%= EntryContentException.class %>" message="please-enter-valid-content" />
	<liferay-ui:error exception="<%= EntryDisplayDateException.class %>" message="please-enter-a-valid-display-date" />
	<liferay-ui:error exception="<%= EntryExpirationDateException.class %>" message="please-enter-a-valid-expiration-date" />
	<liferay-ui:error exception="<%= EntryTitleException.class %>" message="please-enter-a-valid-title" />

	<aui:model-context bean="<%= entry %>" model="<%= AnnouncementsEntry.class %>" />

	<aui:fieldset>
		<c:choose>
			<c:when test="<%= entry != null %>">

				<%
				boolean showScopeName = true;
				%>

				<%@ include file="/html/portlet/announcements/entry_scope.jspf" %>

			</c:when>
			<c:otherwise>

				<%
				String distributionScope = ParamUtil.getString(request, "distributionScope");

				long classNameId = -1;
				long classPK = -1;

				String[] distributionScopeArray = StringUtil.split(distributionScope);

				if (distributionScopeArray.length == 2) {
					classNameId = GetterUtil.getLong(distributionScopeArray[0]);
					classPK = GetterUtil.getLong(distributionScopeArray[1]);
				}

				boolean submitOnChange = false;
				%>

				<%@ include file="/html/portlet/announcements/entry_select_scope.jspf" %>

			</c:otherwise>
		</c:choose>

		<aui:input name="title" />

		<aui:input name="url" />

		<aui:input name="content" />

		<aui:select name="type">

			<%
			for (String curType : AnnouncementsEntryConstants.TYPES) {
			%>

				<aui:option label="<%= curType %>" selected="<%= type.equals(curType) %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select name="priority">
			<aui:option label="normal" selected="<%= priority == 0 %>" value="0" />
			<aui:option label="important" selected="<%= priority == 1 %>" value="1" />
		</aui:select>

		<aui:input name="displayDate" value="<%= displayDate %>" />

		<aui:input name="expirationDate" value="<%= expirationDate %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick='<%= renderResponse.getNamespace() + "previewEntry();" %>' type="button" value="preview" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />previewEntry() {
		document.<portlet:namespace />fm.action = '<portlet:actionURL><portlet:param name="struts_action" value="/announcements/preview_entry" /></portlet:actionURL>';
		document.<portlet:namespace />fm.target = '_blank';
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.PREVIEW %>";
		document.<portlet:namespace />fm.submit();
	}

	function <portlet:namespace />saveEntry() {
		document.<portlet:namespace />fm.action = '<portlet:actionURL><portlet:param name="struts_action" value="/announcements/edit_entry" /></portlet:actionURL>';
		document.<portlet:namespace />fm.target = '';
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= entry == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />title);
	</c:if>
</aui:script>