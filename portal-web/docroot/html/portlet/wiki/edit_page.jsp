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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String originalRedirect = ParamUtil.getString(request, "originalRedirect", StringPool.BLANK);

if (originalRedirect.equals(StringPool.BLANK)) {
	originalRedirect = redirect;
}
else {
	redirect = originalRedirect;
}

boolean followRedirect = false;

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

WikiPage redirectPage = null;

long nodeId = BeanParamUtil.getLong(wikiPage, request, "nodeId");
String title = BeanParamUtil.getString(wikiPage, request, "title");

boolean editTitle = ParamUtil.getBoolean(request, "editTitle");

String content = BeanParamUtil.getString(wikiPage, request, "content");
String format = BeanParamUtil.getString(wikiPage, request, "format", WikiPageConstants.DEFAULT_FORMAT);
String parentTitle = BeanParamUtil.getString(wikiPage, request, "parentTitle");

String[] attachments = new String[0];

boolean preview = ParamUtil.getBoolean(request, "preview");

boolean newPage = false;

if (wikiPage == null) {
	newPage = true;
}

boolean editable = false;

if (wikiPage != null) {
	attachments = wikiPage.getAttachmentsFiles();

	editable = true;
}
else if (Validator.isNotNull(title)) {
	try {
		WikiPageLocalServiceUtil.validateTitle(title);

		editable = true;
	}
	catch (PortalException pe) {
	}
}
else if ((wikiPage == null) && editTitle) {
	editable = true;

	wikiPage = new WikiPageImpl();

	wikiPage.setNew(true);
	wikiPage.setNodeId(node.getNodeId());
	wikiPage.setFormat(format);
	wikiPage.setParentTitle(parentTitle);
}

long templateNodeId = ParamUtil.getLong(request, "templateNodeId");
String templateTitle = ParamUtil.getString(request, "templateTitle");

WikiPage templatePage = null;

if ((templateNodeId > 0) && Validator.isNotNull(templateTitle)) {
	try {
		templatePage = WikiPageServiceUtil.getPage(templateNodeId, templateTitle);

		if (Validator.isNull(parentTitle)) {
			parentTitle = templatePage.getParentTitle();

			if (wikiPage.isNew()) {
				format = templatePage.getFormat();

				wikiPage.setContent(templatePage.getContent());
				wikiPage.setFormat(format);
				wikiPage.setParentTitle(parentTitle);
			}
		}
	}
	catch (Exception e) {
	}
}

PortletURL viewPageURL = renderResponse.createRenderURL();

viewPageURL.setParameter("struts_action", "/wiki/view");
viewPageURL.setParameter("nodeName", node.getName());
viewPageURL.setParameter("title", title);

PortletURL editPageURL = renderResponse.createRenderURL();

editPageURL.setParameter("struts_action", "/wiki/edit_page");
editPageURL.setParameter("redirect", currentURL);
editPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
editPageURL.setParameter("title", title);

if (Validator.isNull(redirect)) {
	redirect = viewPageURL.toString();
}
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<c:choose>
	<c:when test="<%= !newPage %>">
		<liferay-util:include page="/html/portlet/wiki/page_tabs.jsp">
			<liferay-util:param name="tabs1" value="content" />
		</liferay-util:include>
	</c:when>
	<c:otherwise>
		<%@ include file="/html/portlet/wiki/page_name.jspf" %>
	</c:otherwise>
</c:choose>

<c:if test="<%= preview %>">

	<%
	if (wikiPage == null) {
		wikiPage = new WikiPageImpl();
	}

	wikiPage.setContent(content);
	wikiPage.setFormat(format);
	%>

	<liferay-ui:message key="preview" />:

	<div class="preview">
		<%@ include file="/html/portlet/wiki/view_page_content.jspf" %>
	</div>

	<br />
</c:if>

<script type="text/javascript">
	function <portlet:namespace />changeFormat(formatSel) {
		if (window.<portlet:namespace />editor) {
			document.<portlet:namespace />fm.<portlet:namespace />content.value = window.<portlet:namespace />editor.getHTML();
		}

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />getSuggestionsContent() {
		var content = '';

		content += document.<portlet:namespace />fm.<portlet:namespace/>title.value + ' ';
		content += document.<portlet:namespace />fm.<portlet:namespace />content.value;

		return content;
	}

	function <portlet:namespace />previewPage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />preview.value = "true";

		if (window.<portlet:namespace />editor) {
			document.<portlet:namespace />fm.<portlet:namespace />content.value = window.<portlet:namespace />editor.getHTML();
		}

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />savePage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= newPage ? Constants.ADD : Constants.UPDATE %>";

		if (window.<portlet:namespace />editor) {
			document.<portlet:namespace />fm.<portlet:namespace />content.value = window.<portlet:namespace />editor.getHTML();
		}

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveAndContinuePage() {
		document.<portlet:namespace />fm.<portlet:namespace />saveAndContinue.value = "true";
		<portlet:namespace />savePage();
	}
</script>

<portlet:actionURL var="editPageActionURL">
	<portlet:param name="struts_action" value="/wiki/edit_page" />
</portlet:actionURL>

<aui:form action="<%= editPageActionURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "savePage(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="originalRedirect" type="hidden" value="<%= originalRedirect %>" />
	<aui:input name="nodeId" type="hidden" value="<%= nodeId %>" />

	<c:if test="<%= !editTitle %>">
		<aui:input name="title" type="hidden" value="<%= title %>" />
	</c:if>

	<aui:input name="parentTitle" type="hidden" value="<%= parentTitle %>" />
	<aui:input name="editTitle" type="hidden" value="<%= editTitle %>" />

	<c:if test="<%= wikiPage != null %>">
		<aui:input name="version" type="hidden" value="<%= wikiPage.getVersion() %>" />
	</c:if>

	<aui:input name="preview" type="hidden" value="<%= preview %>" />
	<aui:input name="saveAndContinue" type="hidden" value="" />

	<liferay-ui:error exception="<%= DuplicatePageException.class %>" message="there-is-already-a-page-with-the-specified-title" />
	<liferay-ui:error exception="<%= PageContentException.class %>" message="the-content-is-not-valid" />
	<liferay-ui:error exception="<%= PageTitleException.class %>" message="please-enter-a-valid-title" />
	<liferay-ui:error exception="<%= PageVersionException.class %>" message="another-user-has-made-changes-since-you-started-editing-please-copy-your-changes-and-try-again" />
	<liferay-ui:asset-tags-error />

	<c:if test="<%= newPage %>">
		<c:choose>
			<c:when test="<%= editable %>">
				<div class="portlet-msg-info">
					<liferay-ui:message key="this-page-does-not-exist-yet-use-the-form-below-to-create-it" />
				</div>
			</c:when>
			<c:otherwise>
				<div class="portlet-msg-error">
					<liferay-ui:message key="this-page-does-not-exist-yet-and-the-title-is-not-valid" />
				</div>

				<input type="button" value="<liferay-ui:message key="cancel" />" onClick="document.location = '<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>'" />
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="<%= editable %>">
		<aui:model-context bean="<%= !newPage ? wikiPage : templatePage %>" model="<%= WikiPage.class %>" />

		<aui:fieldset>
			<c:if test="<%= editTitle %>">
				<aui:input name="title" size="30" value="<%= title %>" />
			</c:if>

			<c:if test="<%= Validator.isNotNull(parentTitle) %>">
				<aui:field-wrapper label="parent">
					<%= parentTitle %>
				</aui:field-wrapper>
			</c:if>

			<c:choose>
				<c:when test="<%= (WikiPageConstants.FORMATS.length > 1) %>">
					<aui:select name="format" onChange='<%= renderResponse.getNamespace() + "changeFormat(this);" %>'>

						<%
						for (int i = 0; i < WikiPageConstants.FORMATS.length; i++) {
						%>

							<aui:option label='<%= LanguageUtil.get(pageContext, "wiki.formats." + WikiPageConstants.FORMATS[i]) %>' selected="<%= format.equals(WikiPageConstants.FORMATS[i]) %>" value="<%= WikiPageConstants.FORMATS[i] %>" />

						<%
						}
						%>

					</aui:select>

				</c:when>
				<c:otherwise>
					<aui:input name="format" type="hidden" value="<%= format %>" />
				</c:otherwise>
			</c:choose>
		</aui:fieldset>

		<div>

			<%
			request.setAttribute("edit_page.jsp-wikiPage", wikiPage);
			%>

			<liferay-util:include page="<%= WikiUtil.getEditPage(format) %>" />
		</div>

		<c:if test="<%= wikiPage != null %>">
			<liferay-ui:custom-attributes-available className="<%= WikiPage.class.getName() %>">
				<aui:fieldset>
					<liferay-ui:custom-attribute-list
						className="<%= WikiPage.class.getName() %>"
						classPK="<%= (page != null) ? wikiPage.getResourcePrimKey() : 0 %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</aui:fieldset>
			</liferay-ui:custom-attributes-available>
		</c:if>

		<aui:fieldset>
			<c:if test="<%= attachments.length > 0 %>">
				<aui:field-wrapper label="attachments">

					<%
					for (int i = 0; i < attachments.length; i++) {
						String fileName = FileUtil.getShortFileName(attachments[i]);
						long fileSize = DLServiceUtil.getFileSize(company.getCompanyId(), CompanyConstants.SYSTEM, attachments[i]);
					%>

						<portlet:actionURL var="getPageAttachmentURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
							<portlet:param name="struts_action" value="/wiki/get_page_attachment" />
							<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
							<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
							<portlet:param name="fileName" value="<%= fileName %>" />
						</portlet:actionURL>

						<aui:a href="<%= getPageAttachmentURL %>"><%= fileName %></aui:a> (<%= TextFormatter.formatKB(fileSize, locale) %>k)<%= (i < (attachments.length - 1)) ? ", " : "" %>

					<%
					}
					%>

				</aui:field-wrapper>
			</c:if>

			<%
			long resourcePrimKey = 0;

			if (!newPage) {
				resourcePrimKey = wikiPage.getResourcePrimKey();
			}
			else if (templatePage != null) {
				resourcePrimKey = templatePage.getResourcePrimKey();
			}
			%>

			<aui:input classPK="<%= resourcePrimKey %>" name="categories" type="assetCategories" />

			<aui:input classPK="<%= resourcePrimKey %>" name="tags" type="assetTags" />

			<aui:model-context bean="<%= new WikiPageImpl() %>" model="<%= WikiPage.class %>" />

			<aui:input name="summary" />

			<c:if test="<%= !newPage %>">
				<aui:input inlineLabel="true" label="this-is-a-minor-edit" name="minorEdit" />
			</c:if>

			<c:if test="<%= newPage %>">
				<aui:field-wrapper label="permissions">
					<liferay-ui:input-permissions
						modelName="<%= WikiPage.class.getName() %>"
					/>
				</aui:field-wrapper>
			</c:if>

			<aui:button-row>
				<aui:button type="submit" />

				<aui:button name="saveAndContinueButton" onClick='<%= renderResponse.getNamespace() + "saveAndContinuePage();" %>' type="button" value="save-and-continue" />

				<aui:button name="previewButton" onClick='<%= renderResponse.getNamespace() + "previewPage();" %>' type="button" value="preview" />

				<aui:button onClick="<%= redirect %>" type="cancel" />
			</aui:button-row>
		</aui:fieldset>
	</c:if>
</aui:form>

<c:if test="<%= editable && !preview %>">
	<script type="text/javascript">
		if (!window.<portlet:namespace />editor) {
			Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= editTitle ? "title" : "content" %>);
		}
	</script>
</c:if>

<%
if (wikiPage != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), viewPageURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-page"), currentURL);
}
%>