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

<%@ include file="/html/taglib/init.jsp" %>

<%
Layout selLayout = (Layout)request.getAttribute("liferay-ui:breadcrumb:selLayout");

if (selLayout == null) {
	selLayout = layout;
}

String selLayoutParam = (String)request.getAttribute("liferay-ui:breadcrumb:selLayoutParam");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-ui:breadcrumb:portletURL");

int displayStyle = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:breadcrumb:displayStyle"));

if (displayStyle == 0) {
	displayStyle = 1;
}
%>

<%!
private void _buildGuestGroupBreadcrumb(ThemeDisplay themeDisplay, StringBuilder sb) throws Exception {
	if (!PropsValues.BREADCRUMB_SHOW_GUEST_GROUP) {
		return;
	}

	Group group = GroupLocalServiceUtil.getGroup(themeDisplay.getCompanyId(), GroupConstants.GUEST);

	if (group.getPublicLayoutsPageCount() > 0) {
		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(group.getGroupId(), false);

		String layoutSetFriendlyURL = PortalUtil.getLayoutSetFriendlyURL(layoutSet, themeDisplay);

		sb.append("<li>");
		sb.append("<a href=\"");
		sb.append(layoutSetFriendlyURL);
		sb.append("\">");
		sb.append(HtmlUtil.escape(themeDisplay.getAccount().getName()));
		sb.append("</a>");
		sb.append("</li>");
	}
}

private void _buildParentGroupsBreadcrumb(LayoutSet layoutSet, PortletURL portletURL, ThemeDisplay themeDisplay, StringBuilder sb) throws Exception {
	if (!PropsValues.BREADCRUMB_SHOW_PARENT_GROUPS) {
		return;
	}

	Group group = layoutSet.getGroup();

	if (group.isOrganization()) {
		Organization organization = OrganizationLocalServiceUtil.getOrganization(group.getClassPK());

		Organization parentOrganization = organization.getParentOrganization();

		if (parentOrganization != null) {
			Group parentGroup = parentOrganization.getGroup();

			LayoutSet parentLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(parentGroup.getGroupId(), layoutSet.isPrivateLayout());

			_buildParentGroupsBreadcrumb(parentLayoutSet, portletURL, themeDisplay, sb);
		}
	}
	else if (group.isUser()) {
		User groupUser = UserLocalServiceUtil.getUser(group.getClassPK());

		List<Organization> organizations = groupUser.getOrganizations();

		if (!organizations.isEmpty()) {
			Organization organization = organizations.get(0);

			Group parentGroup = organization.getGroup();

			LayoutSet parentLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(parentGroup.getGroupId(), layoutSet.isPrivateLayout());

			_buildParentGroupsBreadcrumb(parentLayoutSet, portletURL, themeDisplay, sb);
		}
	}

	int layoutsPageCount = 0;

	if (layoutSet.isPrivateLayout()) {
		layoutsPageCount = group.getPrivateLayoutsPageCount();
	}
	else {
		layoutsPageCount = group.getPublicLayoutsPageCount();
	}

	if ((layoutsPageCount > 0) && !group.getName().equals(GroupConstants.GUEST)) {
		String layoutSetFriendlyURL = PortalUtil.getLayoutSetFriendlyURL(layoutSet, themeDisplay);

		sb.append("<li>");
		sb.append("<a href=\"");
		sb.append(layoutSetFriendlyURL);
		sb.append("\">");
		sb.append(HtmlUtil.escape(group.getDescriptiveName()));
		sb.append("</a>");
		sb.append("</li>");
	}
}
%>