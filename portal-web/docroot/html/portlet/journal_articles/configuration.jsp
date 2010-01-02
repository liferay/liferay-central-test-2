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

<%@ include file="/html/portlet/journal_articles/init.jsp" %>

<%
String cur = ParamUtil.getString(request, "cur");

String redirect = ParamUtil.getString(request, "redirect");

groupId = ParamUtil.getLong(request, "groupId", groupId);

JournalStructure structure= null;

if (Validator.isNotNull(structureId)) {
	try {
		structure = JournalStructureLocalServiceUtil.getStructure(groupId, structureId);
	}
	catch (NoSuchStructureException nsse) {
		structureId = StringPool.BLANK;

		preferences.setValue("structure-id", structureId);

		preferences.store();
	}
}
%>

<liferay-portlet:renderURL portletConfiguration="true" varImpl="portletURL" />

<script type="text/javascript">
	function <portlet:namespace />removeStructure() {
		document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = "";

		AUI().one('#<portlet:namespace/>structure').html('<liferay-ui:message key="any" />');
	}

	function <portlet:namespace />selectStructure(structureId) {
		document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = structureId;

		AUI().one('#<portlet:namespace/>structure').html(structureId);
	}
</script>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value='<%= portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur %>' />
	<aui:input name="structureId" type="hidden" value="<%= structureId %>" />

	<liferay-ui:panel-container extended="<%= true %>" id="journalArticlesConfiguration" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="filter" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "filter") %>' >
			<aui:fieldset>
				<aui:select label="community" name="groupId">

					<%
					List<Group> myPlaces = user.getMyPlaces();

					for (int i = 0; i < myPlaces.size(); i++) {
						Group group = myPlaces.get(i);

						group = group.toEscapedModel();

						String groupName = group.getDescriptiveName();

						if (group.isUser()) {
							groupName = LanguageUtil.get(pageContext, "my-community");
						}
					%>

						<aui:option label="<%= groupName %>" selected="<%= groupId == group.getGroupId() %>" value="<%= group.getGroupId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select label="web-content-type" name="type">
					<aui:option value="" />

					<%
					for (int i = 0; i < JournalArticleConstants.TYPES.length; i++) {
					%>

						<aui:option label="<%= JournalArticleConstants.TYPES[i] %>" selected="<%= type.equals(JournalArticleConstants.TYPES[i]) %>" />

					<%
					}
					%>

				</aui:select>

				<aui:field-wrapper label="structure">

					<%
					String structureName = StringPool.BLANK;
					String structureDescription = StringPool.BLANK;

					if (structure != null) {
						structureName = structure.getName();
						structureDescription = structure.getDescription();
					}
					else {
						structureName = LanguageUtil.get(pageContext, "any");
					}
					%>

					<div id="<portlet:namespace/>structure">
						<%= structureName %>

						<c:if test="<%= Validator.isNotNull (structureDescription) %>">
							<em>(<%= structureDescription %>)</em>
						</c:if>
					</div>

					<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="selectStructureURL">
						<portlet:param name="struts_action" value="/portlet_configuration/select_structure" />
						<portlet:param name="structureId" value="<%= structureId %>" />
					</portlet:renderURL>

					<%
					String taglibOpenStructureWindow = "var folderWindow = window.open('" + selectStructureURL + "','structure', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); folderWindow.focus();";
					%>

					<aui:button onClick="<%= taglibOpenStructureWindow %>" value="select" />

					<aui:button name="removeStructureButton" onClick='<%= renderResponse.getNamespace() + "removeStructure();" %>' value="remove" />
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="displaySettings" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "display-settings") %>' >
			<aui:fieldset>
				<aui:select label="display-url" name="pageURL">
					<aui:option label="maximized" selected='<%= pageURL.equals("maximized") %>' />
					<aui:option label="normal" selected='<%= pageURL.equals("normal") %>' />
					<aui:option label="pop-up" selected='<%= pageURL.equals("popUp") %>' />
				</aui:select>

				<aui:select label="display-per-page" name="pageDelta">

					<%
					String[] pageDeltaValues = PropsUtil.getArray(PropsKeys.JOURNAL_ARTICLES_PAGE_DELTA_VALUES);

					for (int i = 0; i < pageDeltaValues.length; i++) {
					%>

						<aui:option label="<%= pageDeltaValues[i] %>" selected="<%= pageDelta == GetterUtil.getInteger(pageDeltaValues[i]) %>" />

					<%
					}
					%>

				</aui:select>

				<aui:field-wrapper label="order-by-column">
					<aui:select inlineField="<%= true %>" label="" name="orderByCol">
						<aui:option label="display-date" selected='<%= orderByCol.equals("display-date") %>' />
						<aui:option label="create-date" selected='<%= orderByCol.equals("create-date") %>' />
						<aui:option label="modified-date" selected='<%= orderByCol.equals("modified-date") %>' />
						<aui:option label="title" selected='<%= orderByCol.equals("title") %>' />
						<aui:option label="id" selected='<%= orderByCol.equals("id") %>' />
					</aui:select>

					<aui:select label="" name="orderByType">
						<aui:option label="ascending" selected='<%= orderByType.equals("asc") %>' />
						<aui:option label="descending" selected='<%= orderByType.equals("desc") %>' />
					</aui:select>
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>