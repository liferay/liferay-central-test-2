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

<%@ include file="/html/portlet/journal_content_search/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<div class="portlet-msg-info">
		<liferay-ui:message key="define-the-behavior-of-this-search" />
	</div>

	<aui:fieldset>
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

		<aui:input inlineLabel="left" label="only-show-results-for-web-content-listed-in-a-web-content-display-portlet" name="showListed" type="checkbox" value="<%= showListed %>" />

		<div class="<%= showListed ? StringPool.BLANK : " aui-helper-hidden" %>" id="<portlet:namespace />webContentDisplay">
			<aui:input cssClass="lfr-input-text-container" name="targetPortletId" />
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<script type="text/javascript">
	AUI().ready(
		function(A) {
			Liferay.Util.toggleBoxes('<portlet:namespace />showListedCheckbox','<portlet:namespace />webContentDisplay');
		}
	);
</script>