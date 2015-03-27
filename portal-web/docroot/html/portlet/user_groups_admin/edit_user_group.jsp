<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/user_groups_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

UserGroup userGroup = (UserGroup)request.getAttribute(WebKeys.USER_GROUP);

long userGroupId = BeanParamUtil.getLong(userGroup, request, "userGroupId");

boolean hasUserGroupUpdatePermission = true;

if (userGroup != null) {
	hasUserGroupUpdatePermission = UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.UPDATE);
}
%>

<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveUserGroup();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="userGroupId" type="hidden" value="<%= userGroupId %>" />

	<liferay-ui:header
		backURL="<%= backURL %>"
		localizeTitle="<%= (userGroup == null) %>"
		title='<%= (userGroup == null) ? "new-user-group" : userGroup.getName() %>'
	/>

	<liferay-ui:error exception="<%= DuplicateUserGroupException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= RequiredUserGroupException.class %>" message="this-is-a-required-user-group" />
	<liferay-ui:error exception="<%= UserGroupNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= userGroup %>" model="<%= UserGroup.class %>" />

	<aui:fieldset>
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" disabled="<%= !hasUserGroupUpdatePermission %>" label='<%= (userGroup != null) ? "new-name" : "name" %>' name="name" />

		<aui:input disabled="<%= !hasUserGroupUpdatePermission %>" name="description" />

		<liferay-ui:custom-attributes-available className="<%= UserGroup.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= UserGroup.class.getName() %>"
				classPK="<%= userGroupId %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

	</aui:fieldset>

	<%
	Group userGroupGroup = null;

	if (userGroup != null) {
		userGroupGroup = userGroup.getGroup();
	}

	LayoutSet privateLayoutSet = null;
	LayoutSetPrototype privateLayoutSetPrototype = null;
	boolean privateLayoutSetPrototypeLinkEnabled = true;

	LayoutSet publicLayoutSet = null;
	LayoutSetPrototype publicLayoutSetPrototype = null;
	boolean publicLayoutSetPrototypeLinkEnabled = true;

	if (userGroupGroup != null) {
		try {
			LayoutLocalServiceUtil.getLayouts(userGroupGroup.getGroupId(), false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(userGroupGroup.getGroupId(), true);

			privateLayoutSetPrototypeLinkEnabled = privateLayoutSet.isLayoutSetPrototypeLinkEnabled();

			String layoutSetPrototypeUuid = privateLayoutSet.getLayoutSetPrototypeUuid();

			if (Validator.isNotNull(layoutSetPrototypeUuid)) {
				privateLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuidAndCompanyId(layoutSetPrototypeUuid, company.getCompanyId());
			}
		}
		catch (Exception e) {
		}

		try {
			LayoutLocalServiceUtil.getLayouts(userGroupGroup.getGroupId(), true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(userGroupGroup.getGroupId(), false);

			publicLayoutSetPrototypeLinkEnabled = publicLayoutSet.isLayoutSetPrototypeLinkEnabled();

			String layoutSetPrototypeUuid = publicLayoutSet.getLayoutSetPrototypeUuid();

			if (Validator.isNotNull(layoutSetPrototypeUuid)) {
				publicLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuidAndCompanyId(layoutSetPrototypeUuid, company.getCompanyId());
			}
		}
		catch (Exception e) {
		}
	}

	List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
	%>

	<c:if test="<%= (userGroupGroup != null) || !layoutSetPrototypes.isEmpty() %>">
		<aui:fieldset helpMessage="user-group-site-help" label="user-group-site">

			<%
			boolean hasUnlinkLayoutSetPrototypePermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE);

			boolean hasUpdateSitePermission = false;

			if (userGroupGroup != null) {
				hasUpdateSitePermission = GroupPermissionUtil.contains(permissionChecker, userGroupGroup, ActionKeys.UPDATE);
			}
			else {
				for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
					if (GroupPermissionUtil.contains(permissionChecker, layoutSetPrototype.getGroup(), ActionKeys.UPDATE)) {
						hasUpdateSitePermission = true;
					}
				}
			}
			%>

			<c:choose>
				<c:when test="<%= ((userGroupGroup == null) || ((publicLayoutSetPrototype == null) && (userGroupGroup.getPublicLayoutsPageCount() == 0))) && !layoutSetPrototypes.isEmpty() %>">
					<aui:select disabled="<%= !hasUpdateSitePermission || !hasUserGroupUpdatePermission %>" label="public-pages" name="publicLayoutSetPrototypeId">
						<aui:option label="none" selected="<%= true %>" value="" />

						<%
						for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
						%>

							<aui:option value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>"><%= HtmlUtil.escape(layoutSetPrototype.getName(locale)) %></aui:option>

						<%
						}
						%>

					</aui:select>

					<c:choose>
						<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
							<div class="hide" id="<portlet:namespace />publicLayoutSetPrototypeIdOptions">
								<aui:input helpMessage="enable-propagation-of-changes-from-the-site-template-help" label="enable-propagation-of-changes-from-the-site-template" name="publicLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
							</div>
						</c:when>
						<c:otherwise>
							<aui:input name="publicLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<aui:field-wrapper label="public-pages">
						<c:choose>
							<c:when test="<%= userGroupGroup != null %>">
								<c:choose>
									<c:when test="<%= userGroupGroup.getPublicLayoutsPageCount() > 0 %>">
										<liferay-ui:icon
											iconCssClass="icon-search"
											label="<%= true %>"
											message="open-pages"
											method="get"
											target="_blank"
											url="<%= userGroupGroup.getDisplayURL(themeDisplay, false) %>"
										/>
									</c:when>
									<c:otherwise>
										<liferay-ui:message key="this-user-group-does-not-have-any-public-pages" />
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="<%= (publicLayoutSetPrototype != null) && hasUnlinkLayoutSetPrototypePermission %>">
										<aui:input label='<%= LanguageUtil.format(request, "enable-propagation-of-changes-from-the-site-template-x", HtmlUtil.escape(publicLayoutSetPrototype.getName(locale)), false) %>' name="publicLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
									</c:when>
									<c:when test="<%= publicLayoutSetPrototype != null %>">
										<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(publicLayoutSetPrototype.getName(locale))} %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

										<aui:input name="layoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
									</c:when>
								</c:choose>
							</c:when>
						</c:choose>
					</aui:field-wrapper>
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="<%= ((userGroup == null) || ((privateLayoutSetPrototype == null) && (userGroupGroup.getPrivateLayoutsPageCount() == 0))) && !layoutSetPrototypes.isEmpty() %>">
					<aui:select disabled="<%= !hasUpdateSitePermission || !hasUserGroupUpdatePermission %>" label="private-pages" name="privateLayoutSetPrototypeId">
						<aui:option label="none" selected="<%= true %>" value="" />

						<%
						for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
						%>

							<aui:option value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>"><%= HtmlUtil.escape(layoutSetPrototype.getName(locale)) %></aui:option>

						<%
						}
						%>

					</aui:select>

					<c:choose>
						<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
							<div class="hide" id="<portlet:namespace />privateLayoutSetPrototypeIdOptions">
								<aui:input helpMessage="enable-propagation-of-changes-from-the-site-template-help" label="enable-propagation-of-changes-from-the-site-template" name="privateLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
							</div>
						</c:when>
						<c:otherwise>
							<aui:input name="privateLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<aui:field-wrapper label="private-pages">
						<c:choose>
							<c:when test="<%= userGroupGroup != null %>">
								<c:choose>
									<c:when test="<%= userGroupGroup.getPrivateLayoutsPageCount() > 0 %>">
										<liferay-ui:icon
											iconCssClass="icon-search"
											label="<%= true %>"
											message="open-pages"
											method="get"
											target="_blank"
											url="<%= userGroupGroup.getDisplayURL(themeDisplay, true) %>"
										/>
									</c:when>
									<c:otherwise>
										<liferay-ui:message key="this-user-group-does-not-have-any-private-pages" />
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="<%= (privateLayoutSetPrototype != null) && hasUnlinkLayoutSetPrototypePermission %>">
										<aui:input label='<%= LanguageUtil.format(request, "enable-propagation-of-changes-from-the-site-template-x", HtmlUtil.escape(privateLayoutSetPrototype.getName(locale)), false) %>' name="privateLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
									</c:when>
									<c:when test="<%= privateLayoutSetPrototype != null %>">
										<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(privateLayoutSetPrototype.getName(locale))} %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

										<aui:input name="layoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
									</c:when>
								</c:choose>
							</c:when>
						</c:choose>
					</aui:field-wrapper>
				</c:otherwise>
			</c:choose>
		</aui:fieldset>
	</c:if>

	<aui:button-row>
		<aui:button disabled="<%= !hasUserGroupUpdatePermission %>" type="submit" />

		<aui:button disabled="<%= !hasUserGroupUpdatePermission %>" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />isVisible(currentValue, value) {
		return currentValue != '';
	}

	function <portlet:namespace />saveUserGroup() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= (userGroup == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/users_admin/edit_user_group" /></portlet:actionURL>');
	}

	Liferay.Util.toggleSelectBox('<portlet:namespace />publicLayoutSetPrototypeId', <portlet:namespace />isVisible, '<portlet:namespace />publicLayoutSetPrototypeIdOptions');
	Liferay.Util.toggleSelectBox('<portlet:namespace />privateLayoutSetPrototypeId', <portlet:namespace />isVisible, '<portlet:namespace />privateLayoutSetPrototypeIdOptions');
</aui:script>

<%
if (userGroup != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, userGroup.getName(), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-user-group"), currentURL);
}
%>