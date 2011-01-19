<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portal/layout/edit/init.jsp" %>

<aui:button name="copyPortletsBtn" value="copy-portlets-from-page" />

<div id="<portlet:namespace />copyPortletsFromPage" class="aui-helper-hidden">
	<p><liferay-ui:message key="the-portlets-in-the-page-x-will-be-replaced-by-the-portlets-in-the-page-you-select-below" arguments="<%= selLayout.getName(locale) %>" /></p>

	<aui:select label="copy-from-page" name="copyLayoutId" showEmptyOption="<%= true %>">

		<%
		List layoutList = (List)request.getAttribute(WebKeys.LAYOUT_LISTER_LIST);

		for (int i = 0; i < layoutList.size(); i++) {

			// id | parentId | ls | obj id | name | img | depth

			String layoutDesc = (String)layoutList.get(i);

			String[] nodeValues = StringUtil.split(layoutDesc, "|");

			long objId = GetterUtil.getLong(nodeValues[3]);
			String name = nodeValues[4];

			int depth = 0;

			if (i != 0) {
				depth = GetterUtil.getInteger(nodeValues[6]);
			}

			name = HtmlUtil.escape(name);

			for (int j = 0; j < depth; j++) {
				name = "-&nbsp;" + name;
			}

			Layout copiableLayout = null;

			try {
				copiableLayout = LayoutLocalServiceUtil.getLayout(objId);
			}
			catch (Exception e) {
			}

			if (copiableLayout != null) {
		%>

				<aui:option disabled="<%= selLayout.getPlid() == copiableLayout.getPlid() %>" label="<%= name %>" value="<%= copiableLayout.getLayoutId() %>" />

		<%
			}
		}
		%>

	</aui:select>

	<aui:button-row>
		<aui:button name="copySubmitBtn" value="copy" />
	</aui:button-row>
</div>

<aui:script use="aui-dialog">
	var button = A.one('#<portlet:namespace />copyPortletsBtn');

	if (button) {
		button.on(
			'click',
			function(event) {
				var content = A.one('#<portlet:namespace />copyPortletsFromPage');

				var popup = new A.Dialog(
					{
						bodyContent: content,
						centered: true,
						title: '<liferay-ui:message key="copy-portlets-from-page" />',
						modal: true,
						width: 500
					}
				).render();

				content.show();

				var submitButton = popup.get('contentBox').one('#<portlet:namespace />copySubmitBtn');

				if (submitButton) {
					submitButton.on(
						'click',
						function(event) {
							popup.close();

							var form = A.one('#<portlet:namespace />fm');

							if (form) {
								form.append(content);
							}

							<portlet:namespace />savePage();
						}
					);
				}

				event.preventDefault();
			}
		);
	}


</aui:script>