<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portal/init.jsp" %>

<%
String ppid = ParamUtil.getString(request, "p_p_id");

if (ppid.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	ppid = ParamUtil.getString(request, PortalUtil.getPortletNamespace(ppid) + "portletResource");
}

if (ppid.equals(PortletKeys.EXPANDO)) {
	String modelResource = ParamUtil.getString(request, PortalUtil.getPortletNamespace(ppid) + "modelResource");

	if (modelResource.equals(User.class.getName())) {
		ppid = PortletKeys.ENTERPRISE_ADMIN_USERS;
	}
	else if (modelResource.equals(Organization.class.getName())) {
		ppid = PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS;
	}
}

String category = PortalUtil.getControlPanelCategory(ppid);

if (Validator.isNull(category)) {
	category = PortletCategoryKeys.CONTENT;
}
%>

<c:if test="<%= !themeDisplay.isStateExclusive() && !themeDisplay.isStatePopUp() %>">

	<%
	String panelCategory = StringPool.BLANK;

	if (category.equals(PortletCategoryKeys.CONTENT)) {
		panelCategory = "panel-manage-content";
	}
	else if (category.equals(PortletCategoryKeys.MY)) {
		panelCategory = "panel-manage-my";
	}
	else if (category.equals(PortletCategoryKeys.PORTAL)) {
		panelCategory = "panel-manage-portal";
	}
	else if (category.equals(PortletCategoryKeys.SERVER)) {
		panelCategory = "panel-manage-server";
	}
	%>

	<div id="content-wrapper">
		<table class="lfr-ctrl-panel <%= panelCategory %>">
		<tr>
			<td class="panel-page-menu" valign="top">
				<liferay-portlet:runtime portletName="87" />
			</td>
			<td class="panel-page-content <%= (!layoutTypePortlet.hasStateMax()) ? "panel-page-frontpage" : "panel-page-application" %>" valign="top">
				<table class="panel-page-content-menu">
				<tr>
					<td>

						<%--
						<style type="text/css">
							.lfr-group-selector {
								background: url(<%= themeDisplay.getPathThemeImages() %>/arrows/05_down.png) no-repeat 100% 50%;
								padding: 2px;
								padding-right: 20px;
							}
						</style>
						<liferay-ui:message key="select-a-community" />: <a href="javascript: ;" class="lfr-group-selector">Joe Bloggs</a>
						<div class="lfr-panel-container lfr-floating-container" id="myPanel">
							<div class="lfr-panel lfr-collapsible">
								<div class="lfr-panel-titlebar lfr-has-button">
									<h3 class="lfr-panel-title"><span><liferay-ui:message key="communities" /></span></h3>
									<a href="javascript: ;" class="lfr-panel-button"></a>
								</div>
								<div class="lfr-panel-content">
									<ul>
										<li><a href="javascript: ;">Home</a></li>
										<li><a href="javascript: ;">Sign In</a></li>
										<li><a href="javascript: ;">Control Panel</a></li>
										<li><a href="javascript: ;">My Account</a></li>
										<li><a href="javascript: ;">My Places</a></li>
										<li><a href="javascript: ;">Ignore</a></li>
										<li><a href="javascript: ;">Options</a></li>
									</ul>
									<ul>
										<li><a href="javascript: ;">Hello another verysion 1</a></li>
										<li><a href="javascript: ;">Hello another verysion 2</a></li>
										<li><a href="javascript: ;">Hello another verysion 3</a></li>
										<li><a href="javascript: ;">Hello another verysion 4</a></li>
										<li><a href="javascript: ;">Hello another verysion 5</a></li>
										<li><a href="javascript: ;">Hello another verysion 6</a></li>
									</ul>
									<ul>
										<li><a href="javascript: ;">links version 1</a></li>
										<li><a href="javascript: ;">links version 2</a></li>
										<li><a href="javascript: ;">links version 3</a></li>
										<li><a href="javascript: ;">links version 4</a></li>
										<li><a href="javascript: ;">links version 5</a></li>
									</ul>
									<ul>
										<li><a href="javascript: ;">links last 1</a></li>
										<li><a href="javascript: ;">links last 2</a></li>
									</ul>
								</div>
							</div>
							<div class="lfr-panel lfr-collapsible">
								<div class="lfr-panel-titlebar lfr-has-button">
									<h3 class="lfr-panel-title"><span><liferay-ui:message key="organizatons" /></span></h3>
									<a href="javascript: ;" class="lfr-panel-button"></a>
								</div>
								<div class="lfr-panel-content">
									<ul>
										<li><a href="javascript: ;">Home</a></li>
										<li><a href="javascript: ;">Sign In</a></li>
										<li><a href="javascript: ;">Control Panel</a></li>
										<li><a href="javascript: ;">My Account</a></li>
										<li><a href="javascript: ;">My Places</a></li>
										<li><a href="javascript: ;">Ignore</a></li>
										<li><a href="javascript: ;">Options</a></li>
									</ul>
								</div>
							</div>
							<div class="lfr-panel lfr-collapsible">
								<div class="lfr-panel-titlebar lfr-has-button">
									<h3 class="lfr-panel-title"><span><liferay-ui:message key="user-groups" /></span></h3>
									<a href="javascript: ;" class="lfr-panel-button"></a>
								</div>
								<div class="lfr-panel-content">
									<ul>
										<li><a href="javascript: ;">Home</a></li>
										<li><a href="javascript: ;">Sign In</a></li>
										<li><a href="javascript: ;">Control Panel</a></li>
										<li><a href="javascript: ;">My Account</a></li>
										<li><a href="javascript: ;">My Places</a></li>
										<li><a href="javascript: ;">Ignore</a></li>
										<li><a href="javascript: ;">Options</a></li>
									</ul>
								</div>
							</div>
						</div>
						
						<script>
							jQuery(
								function () {
									new Liferay.FloatingPanel(
										{
											container: '#myPanel',
											trigger: '.lfr-group-selector',
											paging: true
										}
									);
								}
							);
						</script>
						--%>

					</td>
					<td align="right">

						<%
						String refererGroupDescriptiveName = null;
						String backURL = null;

						if (themeDisplay.getRefererPlid() > 0) {
							Layout refererLayout = LayoutLocalServiceUtil.getLayout(themeDisplay.getRefererPlid());

							Group refererGroup = refererLayout.getGroup();

							refererGroupDescriptiveName = refererGroup.getDescriptiveName();

							if (refererGroup.isUser()) {
								refererGroupDescriptiveName = LanguageUtil.get(pageContext, "my-community");
							}

							backURL = PortalUtil.getLayoutURL(refererLayout, themeDisplay);
						}
						else {
							refererGroupDescriptiveName = GroupConstants.GUEST;
							backURL = themeDisplay.getURLPortal() + PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING + "/guest";
						}
						%>

						<div>
							<a class="portlet-icon-back" href="<%= backURL %>"><%= LanguageUtil.format(pageContext, "back-to-x", refererGroupDescriptiveName) %></a>
						</div>

					</td>
				</tr>
				</table>
</c:if>

<%
if (themeDisplay.isStateExclusive() || themeDisplay.isStatePopUp() || layoutTypePortlet.hasStateMax()) {
	String content = null;

	if (themeDisplay.isStateExclusive()) {
		content = LayoutTemplateLocalServiceUtil.getContent("exclusive", true, theme.getThemeId());
	}
	else if (themeDisplay.isStatePopUp()) {
		content = LayoutTemplateLocalServiceUtil.getContent("pop_up", true, theme.getThemeId());
	}
	else {
		ppid = StringUtil.split(layoutTypePortlet.getStateMax())[0];

		content = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());
	}
%>

	<%= RuntimePortletUtil.processTemplate(application, request, response, pageContext, ppid, content) %>

<%
}
else {
	String description = StringPool.BLANK;

	if (Validator.isNull(description)) {
		description = LanguageUtil.get(pageContext, "please-select-a-tool-from-the-left-menu");
	}
%>

	<div class="portlet-msg-info">
		<%= description %>
	</div>

<%
}
%>

<c:if test="<%= !themeDisplay.isStateExclusive() && !themeDisplay.isStatePopUp() %>">
			</td>
		</tr>
		</table>
	</div>
</c:if>