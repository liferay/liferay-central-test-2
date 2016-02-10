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

<%@ include file="/message_boards/init.jsp" %>

<%
String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName", "/message_boards/view");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

String displayStyle = BeanPropertiesUtil.getString(category, "displayStyle", MBCategoryConstants.DEFAULT_DISPLAY_STYLE);

MBCategoryDisplay categoryDisplay = new MBCategoryDisplayImpl(scopeGroupId, categoryId);

Set<Long> categorySubscriptionClassPKs = null;
Set<Long> threadSubscriptionClassPKs = null;

if (themeDisplay.isSignedIn()) {
	categorySubscriptionClassPKs = MBUtil.getCategorySubscriptionClassPKs(user.getUserId());
	threadSubscriptionClassPKs = MBUtil.getThreadSubscriptionClassPKs(user.getUserId());
}

long groupThreadsUserId = ParamUtil.getLong(request, "groupThreadsUserId");

String assetTagName = ParamUtil.getString(request, "tag");

boolean useAssetEntryQuery = Validator.isNotNull(assetTagName);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);
portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));

String keywords = ParamUtil.getString(request, "keywords");

if (Validator.isNotNull(keywords)) {
	portletURL.setParameter("keywords", keywords);
}

request.setAttribute("view.jsp-categoryDisplay", categoryDisplay);

request.setAttribute("view.jsp-categorySubscriptionClassPKs", categorySubscriptionClassPKs);
request.setAttribute("view.jsp-threadSubscriptionClassPKs", threadSubscriptionClassPKs);

request.setAttribute("view.jsp-categoryId", categoryId);
request.setAttribute("view.jsp-viewCategory", Boolean.TRUE.toString());
request.setAttribute("view.jsp-portletURL", portletURL);
%>

<portlet:actionURL name="/message_boards/edit_category" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<liferay-util:include page="/message_boards/top_links.jsp" servletContext="<%= application %>" />

<c:choose>
	<c:when test="<%= useAssetEntryQuery %>">
		<liferay-ui:categorization-filter
			assetType="threads"
			portletURL="<%= portletURL %>"
		/>

		<%@ include file="/message_boards/view_threads.jspf" %>

	</c:when>
	<c:when test='<%= mvcRenderCommandName.equals("/message_boards/search") || mvcRenderCommandName.equals("/message_boards/view") || mvcRenderCommandName.equals("/message_boards/view_category") %>'>
		<liferay-portlet:renderURL varImpl="backURL">
			<portlet:param name="mvcRenderCommandName" value="/message_boards/view" />
		</liferay-portlet:renderURL>

		<c:if test="<%= Validator.isNotNull(keywords) %>">
			<liferay-ui:header
				backURL="<%= backURL.toString() %>"
				title="search"
			/>
		</c:if>

		<%
		boolean showAddCategoryButton = MBCategoryPermission.contains(permissionChecker, scopeGroupId, categoryId, ActionKeys.ADD_CATEGORY);
		boolean showAddMessageButton = MBCategoryPermission.contains(permissionChecker, scopeGroupId, categoryId, ActionKeys.ADD_MESSAGE);
		boolean showPermissionsButton = MBPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);

		if (showAddMessageButton && !themeDisplay.isSignedIn()) {
			if (!allowAnonymousPosting) {
				showAddMessageButton = false;
			}
		}
		%>

		<c:if test="<%= showAddCategoryButton || showAddMessageButton || showPermissionsButton %>">
			<div class="category-buttons">
				<c:if test="<%= showAddCategoryButton %>">
					<portlet:renderURL var="editCategoryURL">
						<portlet:param name="mvcRenderCommandName" value="/message_boards/edit_category" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="parentCategoryId" value="<%= String.valueOf(categoryId) %>" />
					</portlet:renderURL>

					<aui:button href="<%= editCategoryURL %>" value='<%= (category == null) ? "add-category[message-board]" : "add-subcategory[message-board]" %>' />
				</c:if>

				<c:if test="<%= showAddMessageButton %>">
					<portlet:renderURL var="editMessageURL">
						<portlet:param name="mvcRenderCommandName" value="/message_boards/edit_message" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="mbCategoryId" value="<%= String.valueOf(categoryId) %>" />
					</portlet:renderURL>

					<aui:button href="<%= editMessageURL %>" value="post-new-thread" />
				</c:if>

				<c:if test="<%= showPermissionsButton %>">

					<%
					String modelResource = "com.liferay.portlet.messageboards";
					String modelResourceDescription = themeDisplay.getScopeGroupName();
					String resourcePrimKey = String.valueOf(scopeGroupId);

					if (category != null) {
						modelResource = MBCategory.class.getName();
						modelResourceDescription = category.getName();
						resourcePrimKey = String.valueOf(category.getCategoryId());
					}
					%>

					<liferay-security:permissionsURL
						modelResource="<%= modelResource %>"
						modelResourceDescription="<%= HtmlUtil.escape(modelResourceDescription) %>"
						resourcePrimKey="<%= resourcePrimKey %>"
						var="permissionsURL"
						windowState="<%= LiferayWindowState.POP_UP.toString() %>"
					/>

					<aui:button href="<%= permissionsURL %>" useDialog="<%= true %>" value="permissions" />
				</c:if>
			</div>

			<%@ include file="/message_boards/category_subscriptions.jspf" %>
		</c:if>

		<c:if test="<%= category != null %>">
			<div class="category-subscription category-subscription-types">
				<c:if test="<%= enableRSS %>">
					<liferay-ui:rss
						delta="<%= rssDelta %>"
						displayStyle="<%= rssDisplayStyle %>"
						feedType="<%= rssFeedType %>"
						url="<%= MBUtil.getRSSURL(plid, category.getCategoryId(), 0, 0, themeDisplay) %>"
					/>
				</c:if>

				<c:if test="<%= MBCategoryPermission.contains(permissionChecker, category, ActionKeys.SUBSCRIBE) && (mbGroupServiceSettings.isEmailMessageAddedEnabled() || mbGroupServiceSettings.isEmailMessageUpdatedEnabled()) %>">
					<c:choose>
						<c:when test="<%= (categorySubscriptionClassPKs != null) && categorySubscriptionClassPKs.contains(category.getCategoryId()) %>">
							<portlet:actionURL name="/message_boards/edit_category" var="unsubscribeURL">
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								iconCssClass="icon-remove-sign"
								label="<%= true %>"
								message="unsubscribe"
								url="<%= unsubscribeURL %>"
							/>
						</c:when>
						<c:otherwise>
							<portlet:actionURL name="/message_boards/edit_category" var="subscribeURL">
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								iconCssClass="icon-ok-sign"
								label="<%= true %>"
								message="subscribe"
								url="<%= subscribeURL %>"
							/>
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>

			<%
			long parentCategoryId = category.getParentCategoryId();
			String parentCategoryName = LanguageUtil.get(request, "message-boards-home");

			if (!category.isRoot()) {
				MBCategory parentCategory = MBCategoryLocalServiceUtil.getCategory(parentCategoryId);

				parentCategoryId = parentCategory.getCategoryId();
				parentCategoryName = parentCategory.getName();
			}

			if (parentCategoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
				backURL.setParameter("mvcRenderCommandName", "/message_boards/view_category");
				backURL.setParameter("mbCategoryId", String.valueOf(parentCategoryId));
			}
			%>

			<liferay-ui:header
				backLabel="<%= parentCategoryName %>"
				backURL="<%= backURL.toString() %>"
				localizeTitle="<%= false %>"
				title="<%= category.getName() %>"
			/>
		</c:if>

		<%
		SearchContainer entriesSearchContainer = new SearchContainer(renderRequest, null, null, "cur1", 0, SearchContainer.DEFAULT_DELTA, portletURL, null, "there-are-no-threads-nor-categories");

		entriesSearchContainer.setId("mbEntries");

		MBListDisplayContext mbListDisplayContext = mbDisplayContextProvider.getMbListDisplayContext(request, response, categoryId);

		mbListDisplayContext.populateResultsAndTotal(entriesSearchContainer);

		request.setAttribute("view.jsp-displayStyle", "descriptive");
		request.setAttribute("view.jsp-entriesSearchContainer", entriesSearchContainer);
		%>

		<liferay-util:include page='<%= "/message_boards_admin/view_entries.jsp" %>' servletContext="<%= application %>" />

		<%
		if (category != null) {
			PortalUtil.setPageSubtitle(category.getName(), request);
			PortalUtil.setPageDescription(category.getDescription(), request);
		}
		%>

	</c:when>
	<c:when test='<%= mvcRenderCommandName.equals("/message_boards/view_my_posts") || mvcRenderCommandName.equals("/message_boards/view_recent_posts") %>'>

		<%
		if (mvcRenderCommandName.equals("/message_boards/view_my_posts") && themeDisplay.isSignedIn()) {
			groupThreadsUserId = user.getUserId();
		}

		if (groupThreadsUserId > 0) {
			portletURL.setParameter("groupThreadsUserId", String.valueOf(groupThreadsUserId));
		}
		%>

		<c:if test='<%= mvcRenderCommandName.equals("/message_boards/view_recent_posts") && (groupThreadsUserId > 0) %>'>
			<div class="alert alert-info">
				<liferay-ui:message key="filter-by-user" />: <%= HtmlUtil.escape(PortalUtil.getUserName(groupThreadsUserId, StringPool.BLANK)) %>
			</div>
		</c:if>

		<%
		Calendar calendar = Calendar.getInstance();

		int entriesTotal = 0;

		if (mvcRenderCommandName.equals("/message_boards/view_my_posts")) {
			entriesTotal = MBThreadServiceUtil.getGroupThreadsCount(scopeGroupId, groupThreadsUserId, WorkflowConstants.STATUS_ANY);
		}
		else if (mvcRenderCommandName.equals("/message_boards/view_recent_posts")) {
			int offset = GetterUtil.getInteger(recentPostsDateOffset);

			calendar.add(Calendar.DATE, -offset);

			entriesTotal = MBThreadServiceUtil.getGroupThreadsCount(scopeGroupId, groupThreadsUserId, calendar.getTime(), WorkflowConstants.STATUS_APPROVED);
		}

		String entriesEmptyResultsMessage = "you-do-not-have-any-posts";

		if (mvcRenderCommandName.equals("/message_boards/view_recent_posts")) {
			entriesEmptyResultsMessage = "there-are-no-recent-posts";
		}

		SearchContainer entriesSearchContainer = new SearchContainer(renderRequest, null, null, "cur1", 0, SearchContainer.DEFAULT_DELTA, portletURL, null, entriesEmptyResultsMessage);

		entriesSearchContainer.setId("mbEntries");

		entriesSearchContainer.setTotal(entriesTotal);

		List entriesResults = null;

		if (mvcRenderCommandName.equals("/message_boards/view_my_posts")) {
			entriesResults = MBThreadServiceUtil.getGroupThreads(scopeGroupId, groupThreadsUserId, WorkflowConstants.STATUS_ANY, entriesSearchContainer.getStart(), entriesSearchContainer.getEnd());
		}
		else if (mvcRenderCommandName.equals("/message_boards/view_recent_posts")) {
			entriesResults = MBThreadServiceUtil.getGroupThreads(scopeGroupId, groupThreadsUserId, calendar.getTime(), WorkflowConstants.STATUS_APPROVED, entriesSearchContainer.getStart(), entriesSearchContainer.getEnd());
		}

		entriesSearchContainer.setResults(entriesResults);

		request.setAttribute("view.jsp-displayStyle", "descriptive");
		request.setAttribute("view.jsp-entriesSearchContainer", entriesSearchContainer);
		%>

		<c:if test='<%= enableRSS && mvcRenderCommandName.equals("/message_boards/view_recent_posts") %>'>
			<br />

			<liferay-ui:rss
				delta="<%= rssDelta %>"
				displayStyle="<%= rssDisplayStyle %>"
				feedType="<%= rssFeedType %>"
				message="subscribe-to-recent-posts"
				url="<%= MBUtil.getRSSURL(plid, 0, 0, groupThreadsUserId, themeDisplay) %>"
			/>
		</c:if>

		<liferay-util:include page='<%= "/message_boards_admin/view_entries.jsp" %>' servletContext="<%= application %>">
			<liferay-util:param name="showBreadcrumb" value="<%= Boolean.FALSE.toString() %>" />
		</liferay-util:include>

		<%
		String pageSubtitle = null;

		if (mvcRenderCommandName.equals("/message_boards/view_my_posts")) {
			pageSubtitle = "my-posts";
		}
		else if (mvcRenderCommandName.equals("/message_boards/view_my_subscriptions")) {
			pageSubtitle = "my-subscriptions";
		}
		else if (mvcRenderCommandName.equals("/message_boards/view_recent_posts")) {
			pageSubtitle = "recent-posts";
		}

		PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace(pageSubtitle, StringPool.UNDERLINE, StringPool.DASH)), request);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format(pageSubtitle, TextFormatter.O)), portletURL.toString());
		%>

	</c:when>
	<c:when test='<%= mvcRenderCommandName.equals("/message_boards/view_my_subscriptions") %>'>

		<%
		if (themeDisplay.isSignedIn()) {
			groupThreadsUserId = user.getUserId();
		}

		if (groupThreadsUserId > 0) {
			portletURL.setParameter("groupThreadsUserId", String.valueOf(groupThreadsUserId));
		}
		%>

		<liferay-ui:search-container
			curParam="cur1"
			deltaConfigurable="<%= false %>"
			emptyResultsMessage="you-are-not-subscribed-to-any-categories"
			headerNames="category,categories,threads,posts"
			iteratorURL="<%= portletURL %>"
			total="<%= MBCategoryServiceUtil.getSubscribedCategoriesCount(scopeGroupId, user.getUserId()) %>"
		>
			<liferay-ui:search-container-results
				results="<%= MBCategoryServiceUtil.getSubscribedCategories(scopeGroupId, user.getUserId(), searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.message.boards.kernel.model.MBCategory"
				escapedModel="<%= true %>"
				keyProperty="categoryId"
				modelVar="curCategory"
			>
				<liferay-ui:search-container-row-parameter name="categorySubscriptionClassPKs" value="<%= categorySubscriptionClassPKs %>" />

				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/message_boards/view_category" />
					<portlet:param name="mbCategoryId" value="<%= String.valueOf(curCategory.getCategoryId()) %>" />
				</liferay-portlet:renderURL>

				<%@ include file="/message_boards/subscribed_category_columns.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator type="more" />
		</liferay-ui:search-container>

		<%@ include file="/message_boards/view_threads.jspf" %>

		<%
		PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace("my-subscriptions", StringPool.UNDERLINE, StringPool.DASH)), request);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format("my-subscriptions", TextFormatter.O)), portletURL.toString());
		%>

	</c:when>
</c:choose>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_message_boards_web.message_boards.view_jsp");
%>