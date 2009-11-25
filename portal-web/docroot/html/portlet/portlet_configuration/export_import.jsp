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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "export");

String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String portletResource = ParamUtil.getString(request, "portletResource");

Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

String selPortletPrimaryKey = PortletPermissionUtil.getPrimaryKey(layout.getPlid(), selPortlet.getPortletId());

String path = (String)request.getAttribute(WebKeys.CONFIGURATION_ACTION_PATH);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/portlet_configuration/export_import");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);

boolean supportsLAR = Validator.isNotNull(selPortlet.getPortletDataHandlerClass());

boolean supportsSetup = Validator.isNotNull(selPortlet.getConfigurationActionClass());

boolean controlPanel = false;

if (layout.getGroup().getName().equals(GroupConstants.CONTROL_PANEL)) {
	supportsSetup = false;

	controlPanel = true;

	layout = LayoutLocalServiceUtil.getLayout(LayoutLocalServiceUtil.getDefaultPlid(scopeGroupId));
}
%>

<script type="text/javascript">
	function <portlet:namespace />copyFromLive() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-copy-from-live-and-update-the-existing-staging-portlet-information") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "copy_from_live";

			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />exportData() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";

		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "export";

		submitForm(document.<portlet:namespace />fm, '<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/export_import" /></portlet:actionURL>&etag=0');
	}

	function <portlet:namespace />importData() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";

		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "import";

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />publishToLive() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-publish-to-live-and-update-the-existing-portlet-data") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "publish_to_live";

			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />toggleChildren(checkbox, parentDivId) {
		var parentDiv = AUI().one('#' + parentDivId);

		var enabled = checkbox.checked;

		if (parentDiv) {
			parentDiv.all('input').each(
				function(item, index, collection) {
					var disabled = !enabled;

					if (enabled && item.hasClass('disabled')) {
						disabled = true;
					}

					item.set('disabled', disabled);
				}
			);
		}
	}
</script>

<c:choose>
	<c:when test="<%= supportsLAR || supportsSetup %>">

		<%
		String tabs2Names = "export,import";

		if (themeDisplay.getScopeGroup().isStagingGroup()) {
			tabs2Names += ",staging";
		}
		%>

		<liferay-ui:tabs
			names="<%= tabs2Names %>"
			param="tabs2"
			url="<%= portletURL.toString() %>"
			backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
		/>

		<liferay-ui:error exception="<%= LARFileException.class %>" message="please-specify-a-lar-file-to-import" />
		<liferay-ui:error exception="<%= LARTypeException.class %>" message="please-import-a-lar-file-of-the-correct-type" />
		<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />
		<liferay-ui:error exception="<%= NoSuchLayoutException.class %>" message="an-error-occurred-because-the-live-group-does-not-have-the-current-page" />
		<liferay-ui:error exception="<%= PortletIdException.class %>" message="please-import-a-lar-file-for-the-current-portlet" />

		<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/export_import" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveData(); return false;">
		<input name="<portlet:namespace />tabs1" type="hidden" value="export_import">
		<input name="<portlet:namespace />tabs2" type="hidden" value="<%= HtmlUtil.escapeAttribute(tabs2) %>">
		<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
		<input name="<portlet:namespace />plid" type="hidden" value="<%= layout.getPlid() %>">
		<input name="<portlet:namespace />groupId" type="hidden" value="<%= themeDisplay.getScopeGroupId() %>">
		<input name="<portlet:namespace />portletResource" type="hidden" value="<%= HtmlUtil.escapeAttribute(portletResource) %>">
		<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(currentURL) %>">

		<c:choose>
			<c:when test='<%= tabs2.equals("export") %>'>
				<liferay-ui:message key="export-the-selected-data-to-the-given-lar-file-name" />

				<br /><br />

				<div>
					<input name="<portlet:namespace />exportFileName" size="50" type="text" value="<%= StringUtil.replace(selPortlet.getDisplayName(), " ", "_") %>-<%= Time.getShortTimestamp() %>.portlet.lar">
				</div>

				<br />

				<liferay-ui:message key="what-would-you-like-to-export" />

				<br /><br />

				<%@ include file="/html/portlet/portlet_configuration/export_import_options.jspf" %>

				<br />

				<input type="button" value='<liferay-ui:message key="export" />' onClick="<portlet:namespace />exportData();" />
			</c:when>
			<c:when test='<%= tabs2.equals("import") %>'>
				<liferay-ui:message key="import-a-lar-file-to-overwrite-the-selected-data" />

				<br /><br />

				<div>
					<input name="<portlet:namespace />importFileName" size="50" type="file" />
				</div>

				<br />

				<liferay-ui:message key="what-would-you-like-to-import" />

				<br /><br />

				<%@ include file="/html/portlet/portlet_configuration/export_import_options.jspf" %>

				<br />

				<input type="button" value="<liferay-ui:message key="import" />" onClick="<portlet:namespace />importData();">
			</c:when>
			<c:when test='<%= tabs2.equals("staging") %>'>

				<%
				String errorMessageKey = StringPool.BLANK;

				Group stagingGroup = themeDisplay.getScopeGroup();
				Group liveGroup = stagingGroup.getLiveGroup();

				Layout targetLayout = null;

				if (!controlPanel) {
					try {
						targetLayout = LayoutLocalServiceUtil.getLayout(liveGroup.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId());
					}
					catch (NoSuchLayoutException nsle) {
						errorMessageKey = "this-portlet-is-placed-in-a-page-that-does-not-exist-in-the-live-site-publish-the-page-first";
					}

					if (targetLayout != null) {
						LayoutType layoutType = targetLayout.getLayoutType();

						if (!(layoutType instanceof LayoutTypePortlet) || !((LayoutTypePortlet)layoutType).hasPortletId(selPortlet.getPortletId())) {
							errorMessageKey = "this-portlet-has-not-been-added-to-the-live-page-publish-the-page-first";
						}
					}
				}

				boolean workflowEnabled = liveGroup.isWorkflowEnabled();

				TasksProposal proposal = null;

				if (workflowEnabled) {
					try {
						proposal = TasksProposalLocalServiceUtil.getProposal(Portlet.class.getName(), selPortletPrimaryKey);
					}
					catch (NoSuchProposalException nspe) {
					}
				}
				%>

				<c:choose>
					<c:when test="<%= Validator.isNull(errorMessageKey) %>">
						<liferay-ui:message key="what-would-you-like-to-copy-from-live-or-publish-to-live" />

						<br /><br />

						<%@ include file="/html/portlet/portlet_configuration/export_import_options.jspf" %>

						<br />

						<c:choose>
							<c:when test="<%= workflowEnabled %>">
								<c:if test="<%= proposal == null %>">

									<%
									PortletURL proposePublicationURL = new PortletURLImpl(request, PortletKeys.LAYOUT_MANAGEMENT, layout.getPlid(), PortletRequest.ACTION_PHASE);

									proposePublicationURL.setWindowState(WindowState.MAXIMIZED);
									proposePublicationURL.setPortletMode(PortletMode.VIEW);

									proposePublicationURL.setParameter("struts_action", "/layout_management/edit_proposal");
									proposePublicationURL.setParameter(Constants.CMD, Constants.ADD);
									proposePublicationURL.setParameter("redirect", currentURL);
									proposePublicationURL.setParameter("groupId", String.valueOf(liveGroup.getGroupId()));
									proposePublicationURL.setParameter("className", Portlet.class.getName());
									proposePublicationURL.setParameter("classPK", selPortletPrimaryKey);

									String[] workflowRoleNames = StringUtil.split(liveGroup.getWorkflowRoleNames());

									JSONArray jsonReviewers = JSONFactoryUtil.createJSONArray();

									Role role = RoleLocalServiceUtil.getRole(company.getCompanyId(), workflowRoleNames[0]);

									LinkedHashMap userParams = new LinkedHashMap();

									if (liveGroup.isOrganization()) {
										userParams.put("usersOrgs", new Long(liveGroup.getClassPK()));
									}
									else {
										userParams.put("usersGroups", new Long(liveGroup.getGroupId()));
									}

									userParams.put("userGroupRole", new Long[] {new Long(liveGroup.getGroupId()), new Long(role.getRoleId())});

									List<User> reviewers = UserLocalServiceUtil.search(company.getCompanyId(), null, null, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, (OrderByComparator)null);

									if (reviewers.size() == 0) {
										if (liveGroup.isCommunity()) {
											role = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.COMMUNITY_OWNER);
										}
										else {
											role = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);
										}

										userParams.put("userGroupRole", new Long[] {new Long(liveGroup.getGroupId()), new Long(role.getRoleId())});

										reviewers = UserLocalServiceUtil.search(company.getCompanyId(), null, null, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, (OrderByComparator)null);
									}

									for (User reviewer : reviewers) {
										JSONObject jsonReviewer = JSONFactoryUtil.createJSONObject();

										jsonReviewer.put("userId", reviewer.getUserId());
										jsonReviewer.put("fullName", reviewer.getFullName());

										jsonReviewers.put(jsonReviewer);
									}
									%>

									<input type="button" value="<liferay-ui:message key="propose-publication" />" onClick="Liferay.LayoutExporter.proposeLayout({url: '<%= proposePublicationURL.toString() %>', namespace: '<%= PortalUtil.getPortletNamespace(PortletKeys.LAYOUT_MANAGEMENT) %>', reviewers: <%= StringUtil.replace(jsonReviewers.toString(), '"', '\'') %>, title: '<liferay-ui:message key="proposal-description" />'});" />
								</c:if>
							</c:when>
							<c:when test="<%= (themeDisplay.getURLPublishToLive() != null) || controlPanel %>">
								<input type="button" value="<liferay-ui:message key="publish-to-live" />" onClick="<portlet:namespace />publishToLive();" />
							</c:when>
						</c:choose>

						<input type="button" value="<liferay-ui:message key="copy-from-live" />" onClick="<portlet:namespace />copyFromLive();" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="<%= errorMessageKey %>" />
					</c:otherwise>
				</c:choose>
			</c:when>
		</c:choose>

		</form>

		<script type="text/javascript">
			AUI().ready(
				'selector-css3',
				function(A) {
					var toggleHandlerControl = function(item, index, collection) {
						var container = item.ancestor('.<portlet:namespace />handler-control').one('ul');

						if (container) {
							var action = 'hide';

							if (item.get('checked')) {
								action = 'show';
							}

							container[action]();
						}
					};

					var checkboxes = A.all('.<portlet:namespace />handler-control input[type=checkbox]');

					if (checkboxes) {
						var uncheckedBoxes = checkboxes.filter(':not(:checked)');

						if (uncheckedBoxes) {
							uncheckedBoxes.each(toggleHandlerControl);
						}

						checkboxes.detach('click');

						checkboxes.on(
							'click',
							function(event) {
								toggleHandlerControl(event.currentTarget);
							}
						);
					}
				}
			);
		</script>

	</c:when>
	<c:otherwise>
		<%= LanguageUtil.format(locale, "the-x-portlet-does-not-have-any-data-that-can-be-exported-or-does-not-include-support-for-it", PortalUtil.getPortletTitle(selPortlet, application, locale)) %>
	</c:otherwise>
</c:choose>

<%@ include file="/html/portlet/communities/render_controls.jspf" %>