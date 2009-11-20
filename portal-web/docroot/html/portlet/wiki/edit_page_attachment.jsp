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

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);
%>

<script type="text/javascript">
	AUI().ready(
		'liferay-upload',
		function() {
			new Liferay.Upload(
				{
					allowedFileTypes: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
					container: '#<portlet:namespace />fileUpload',
					fileDescription: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
					fallbackContainer: '#<portlet:namespace />fallback',
					maxFileSize: <%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) %> / 1024,
					namespace: '<portlet:namespace />',
					uploadFile: '<liferay-portlet:actionURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/wiki/edit_page_attachment" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" /><portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" /><portlet:param name="title" value="<%= wikiPage.getTitle() %>" /></liferay-portlet:actionURL><liferay-ui:input-permissions-params modelName="<%= WikiPage.class.getName() %>" />'
				}
			);
		}
	);
</script>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-util:include page="/html/portlet/wiki/page_tabs.jsp">
	<liferay-util:param name="tabs1" value="attachments" />
</liferay-util:include>

<portlet:actionURL var="editPageAttachmentURL">
	<portlet:param name="struts_action" value="/wiki/edit_page_attachment" />
</portlet:actionURL>

<aui:form action="<%= editPageAttachmentURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="nodeId" type="hidden" value="<%= String.valueOf(node.getNodeId()) %>" />
	<aui:input name="title" type="hidden" value="<%= wikiPage.getTitle() %>" />
	<aui:input name="numOfFiles" type="hidden" value="3" />

	<div class="lfr-dynamic-uploader">
		<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
	</div>

	<div class="lfr-fallback" id="<portlet:namespace />fallback">
		<aui:fieldset>
			<aui:legend label="upload-files" />

			<aui:input label='<%= LanguageUtil.get(pageContext, "file") + " 1" %>' name="file1" type="file" />

			<aui:input label='<%= LanguageUtil.get(pageContext, "file") + " 2" %>' name="file2" type="file" />

			<aui:input label='<%= LanguageUtil.get(pageContext, "file") + " 3" %>' name="file3" type="file" />
		</aui:fieldset>

		<aui:button-row>
			<aui:button type="submit" value="save" />

			<%
			String taglibOnClick = "parent.location = '" + HtmlUtil.escape(redirect) + "';";
			%>

			<aui:button onClick="<%= taglibOnClick %>" value="cancel" />
		</aui:button-row>
	</div>
</aui:form>

<script type="text/javascript">
	AUI().ready(
		function() {
			for (var i = 1; i < 4; i++) {
				jQuery("#<portlet:namespace />file" + i).change(
					function() {
						var value = jQuery(this).val();

						if ((value != null) && (value != "")) {
							var extension = value.substring(value.lastIndexOf(".")).toLowerCase();

							var validExtensions = new Array('<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), "', '") %>');

							if ((jQuery.inArray("*", validExtensions) == -1) && (jQuery.inArray(extension, validExtensions) == -1)) {
								alert('<%= UnicodeLanguageUtil.get(pageContext, "document-names-must-end-with-one-of-the-following-extensions") %> <%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), StringPool.COMMA_AND_SPACE) %>');

								jQuery(this).val("");
							}
						}
					}
				).change();
			}
		}
	);
</script>