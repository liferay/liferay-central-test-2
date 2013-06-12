<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "export");

String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/portlet_configuration/export_import");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);

if (layout.isTypeControlPanel()) {
	Group scopeGroup = themeDisplay.getScopeGroup();

	if (scopeGroup.isLayout()) {
		layout = LayoutLocalServiceUtil.getLayout(scopeGroup.getClassPK());
	}
	else if (!scopeGroup.isCompany()) {
		long defaultPlid = LayoutLocalServiceUtil.getDefaultPlid(scopeGroupId);

		if (defaultPlid > 0) {
			layout = LayoutLocalServiceUtil.getLayout(defaultPlid);
		}
	}
}
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(selPortlet.getPortletDataHandlerClass()) || Validator.isNotNull(selPortlet.getConfigurationActionClass()) %>">

		<%
		String tabs2Names = "export,import";

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (scopeGroup.isStagingGroup()) {
			tabs2Names += ",staging";
		}
		else if (scopeGroup.isLayout()) {
			Group parentScopeGroup = GroupServiceUtil.getGroup(scopeGroup.getParentGroupId());

			if (parentScopeGroup.isStagingGroup()) {
				tabs2Names += ",staging";
			}
		}
		%>

		<liferay-ui:tabs
			backURL="<%= redirect %>"
			names="<%= tabs2Names %>"
			param="tabs2"
			url="<%= portletURL.toString() %>"
		/>

		<liferay-ui:error exception="<%= LARFileException.class %>" message="please-specify-a-lar-file-to-import" />

		<liferay-ui:error exception="<%= LARTypeException.class %>">

			<%
			LARTypeException lpe = (LARTypeException)errorException;
			%>

			<liferay-ui:message arguments="<%= lpe.getMessage() %>" key="please-import-a-lar-file-of-the-correct-type-x-is-not-valid" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />

		<liferay-ui:error exception="<%= LocaleException.class %>">

			<%
			LocaleException le = (LocaleException)errorException;
			%>

			<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLocales(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLocales(), StringPool.COMMA_AND_SPACE)} %>" key="the-available-languages-in-the-lar-file-x-do-not-match-the-portal's-available-languages-x" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= NoSuchLayoutException.class %>" message="an-error-occurred-because-the-live-group-does-not-have-the-current-page" />
		<liferay-ui:error exception="<%= PortletIdException.class %>" message="please-import-a-lar-file-for-the-current-portlet" />

		<liferay-ui:error exception="<%= PortletDataException.class %>">

			<%
			PortletDataException pde = (PortletDataException)errorException;
			%>

			<c:if test="<%= pde.getType() == PortletDataException.FUTURE_END_DATE %>">
				<liferay-ui:message key="please-enter-a-valid-end-date-that-is-in-the-past" />
			</c:if>

			<c:if test="<%= pde.getType() == PortletDataException.FUTURE_START_DATE %>">
				<liferay-ui:message key="please-enter-a-valid-start-date-that-is-in-the-past" />
			</c:if>

			<c:if test="<%= pde.getType() == PortletDataException.START_DATE_AFTER_END_DATE %>">
				<liferay-ui:message key="please-enter-a-start-date-that-comes-before-the-end-date" />
			</c:if>
		</liferay-ui:error>

		<liferay-ui:error exception="<%= RecordSetDuplicateRecordSetKeyException.class %>">

			<%
			RecordSetDuplicateRecordSetKeyException rsdrske = (RecordSetDuplicateRecordSetKeyException)errorException;
			%>

			<liferay-ui:message arguments="<%= rsdrske.getRecordSetKey() %>" key="dynamic-data-list-record-set-with-record-set-key-x-already-exists" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= StructureDuplicateStructureKeyException.class %>">

			<%
			StructureDuplicateStructureKeyException sdske = (StructureDuplicateStructureKeyException)errorException;
			%>

			<liferay-ui:message arguments="<%= sdske.getStructureKey() %>" key="dynamic-data-mapping-structure-with-structure-key-x-already-exists" />
		</liferay-ui:error>

		<portlet:actionURL var="exportImportPagesURL">
			<portlet:param name="struts_action" value="/portlet_configuration/export_import" />
		</portlet:actionURL>

		<aui:form action="<%= exportImportPagesURL %>" cssClass="lfr-export-dialog" method="post" name="fm1">
			<aui:input name="tabs1" type="hidden" value="export_import" />
			<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
			<aui:input name="plid" type="hidden" value="<%= layout.getPlid() %>" />
			<aui:input name="groupId" type="hidden" value="<%= themeDisplay.getScopeGroupId() %>" />
			<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<aui:fieldset>
				<c:choose>
					<c:when test='<%= tabs2.equals("export") %>'>
						<liferay-util:include page="/html/portlet/portlet_configuration/export_options.jsp" />
					</c:when>
					<c:when test='<%= tabs2.equals("import") %>'>
						<liferay-util:include page="/html/portlet/portlet_configuration/import_options.jsp" />
					</c:when>
					<c:when test='<%= tabs2.equals("staging") %>'>
						<liferay-util:include page="/html/portlet/portlet_configuration/staging_options.jsp" />
					</c:when>
				</c:choose>
			</aui:fieldset>
		</aui:form>
	</c:when>
	<c:otherwise>
		<%= LanguageUtil.format(locale, "the-x-portlet-does-not-have-any-data-that-can-be-exported-or-does-not-include-support-for-it", HtmlUtil.escape(PortalUtil.getPortletTitle(selPortlet, application, locale))) %>
	</c:otherwise>
</c:choose>