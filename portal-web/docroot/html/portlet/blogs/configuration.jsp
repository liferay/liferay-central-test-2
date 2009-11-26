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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" >
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<div class="portlet-msg-info">
		<liferay-ui:message key="set-the-display-styles-used-to-display-blogs-when-viewed-via-as-a-regular-page-or-as-an-rss" />
	</div>

	<aui:fieldset>
		<aui:legend label="page" />

		<aui:select label="maximum-items-to-display" name="pageDelta">
			<aui:option label="1" selected="<%= pageDelta == 1 %>" />
			<aui:option label="2" selected="<%= pageDelta == 2 %>" />
			<aui:option label="3" selected="<%= pageDelta == 3 %>" />
			<aui:option label="4" selected="<%= pageDelta == 4 %>" />
			<aui:option label="5" selected="<%= pageDelta == 5 %>" />
			<aui:option label="10" selected="<%= pageDelta == 10 %>" />
			<aui:option label="15" selected="<%= pageDelta == 15 %>" />
			<aui:option label="20" selected="<%= pageDelta == 20 %>" />
			<aui:option label="25" selected="<%= pageDelta == 25 %>" />
			<aui:option label="30" selected="<%= pageDelta == 30 %>" />
			<aui:option label="40" selected="<%= pageDelta == 40 %>" />
			<aui:option label="50" selected="<%= pageDelta == 50 %>" />
			<aui:option label="60" selected="<%= pageDelta == 60 %>" />
			<aui:option label="70" selected="<%= pageDelta == 70 %>" />
			<aui:option label="80" selected="<%= pageDelta == 80 %>" />
			<aui:option label="90" selected="<%= pageDelta == 90 %>" />
			<aui:option label="100" selected="<%= pageDelta == 100 %>" />
		</aui:select>

		<aui:select label="display-style" name="pageDisplayStyle">
			<aui:option label="<%= RSSUtil.DISPLAY_STYLE_FULL_CONTENT %>" selected="<%= pageDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT) %>" />
			<aui:option label="<%= RSSUtil.DISPLAY_STYLE_ABSTRACT %>" selected="<%= pageDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT) %>" />
			<aui:option label="<%= RSSUtil.DISPLAY_STYLE_TITLE %>" selected="<%= pageDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE) %>" />
		</aui:select>

		<aui:input inlineLabel="left" name="enableFlags" type="checkbox" value="<%= enableFlags %>" />

		<aui:input inlineLabel="left" name="enableRatings" type="checkbox" value="<%= enableRatings %>" />

		<c:if test="<%= PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED %>">
			<aui:input inlineLabel="left" name="enableComments" type="checkbox" value="<%= enableComments %>" />

			<aui:input inlineLabel="left" name="enableCommentRatings" type="checkbox" value="<%= enableCommentRatings %>" />
		</c:if>
	</aui:fieldset>

	<aui:fieldset>
		<aui:legend label="rss" />

		<aui:select label="maximum-items-to-display" name="rssDelta">
			<aui:option label="1" selected="<%= rssDelta == 1 %>" />
			<aui:option label="2" selected="<%= rssDelta == 2 %>" />
			<aui:option label="3" selected="<%= rssDelta == 3 %>" />
			<aui:option label="4" selected="<%= rssDelta == 4 %>" />
			<aui:option label="5" selected="<%= rssDelta == 5 %>" />
			<aui:option label="10" selected="<%= rssDelta == 10 %>" />
			<aui:option label="15" selected="<%= rssDelta == 15 %>" />
			<aui:option label="20" selected="<%= rssDelta == 20 %>" />
			<aui:option label="25" selected="<%= rssDelta == 25 %>" />
			<aui:option label="30" selected="<%= rssDelta == 30 %>" />
			<aui:option label="40" selected="<%= rssDelta == 40 %>" />
			<aui:option label="50" selected="<%= rssDelta == 50 %>" />
			<aui:option label="60" selected="<%= rssDelta == 60 %>" />
			<aui:option label="70" selected="<%= rssDelta == 70 %>" />
			<aui:option label="80" selected="<%= rssDelta == 80 %>" />
			<aui:option label="90" selected="<%= rssDelta == 90 %>" />
			<aui:option label="100" selected="<%= rssDelta == 100 %>" />
		</aui:select>

		<aui:select label="display-style" name="rssDisplayStyle">
			<aui:option label="<%= RSSUtil.DISPLAY_STYLE_FULL_CONTENT %>" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT) %>" />
			<aui:option label="<%= RSSUtil.DISPLAY_STYLE_ABSTRACT %>" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT) %>" />
			<aui:option label="<%= RSSUtil.DISPLAY_STYLE_TITLE %>" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE) %>" />
		</aui:select>

		<aui:select label="format" name="rssFormat">
			<aui:option label="RSS 1.0" selected='<%= rssFormat.equals("rss10") %>' value="rss10" />
			<aui:option label="RSS 2.0" selected='<%= rssFormat.equals("rss20") %>' value="rss20" />
			<aui:option label="Atom 1.0" selected='<%= rssFormat.equals("atom10") %>' value="atom10" />
		</aui:select>
	</aui:fieldset>

	<aui:button-row>
		<aui:button name="saveButton" type="submit" value="save" />

		<aui:button name="cancelButton" onClick="<%= redirect %>" type="button" value="cancel" />
	</aui:button-row>
</aui:form>