<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%@ include file="/html/portlet/mail/init.jsp" %>

<%
DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
df.setTimeZone(timeZone);

List results = (List)request.getAttribute(WebKeys.MAIL_SEARCH_RESULTS);
%>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "search-results") %>' />

	<table border="0" cellpadding="0" cellspacing="0" width="100%">

	<c:if test="<%= (results == null) || (results.size() == 0) %>">
		<tr>
			<td align="center">
				<font class="portlet-font" style="font-size: x-small;">
				<%= LanguageUtil.get(pageContext, "no-messages-found") %>
				</font>
			</td>
		</tr>
	</c:if>

	<c:if test="<%= (results != null) && (results.size() > 0) %>">

		<%
		for (int i = 0; i < results.size(); i++) {
			SearchResult result = (SearchResult)results.get(i);
			Message[] messages = result.getMessages();

			String folderName = result.getFolderName();
			if (MailUtil.isDefaultFolder(folderName)) {
				folderName = LanguageUtil.get(pageContext, result.getFolderName());
			}
		%>

			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b>
					<%= folderName %><br>
					</b></font>

					<br>

					<table border="0" cellpadding="0" cellspacing="0" width="100%">

		<%
					for (int j = 0; j < messages.length; j++) {
						Message msg = messages[j];
		%>

						<tr>
							<td width="30"><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="30"></td>
							<td valign="top">
								<font class="portlet-font" style="font-size: x-small;"><a href="<portlet:renderURL><portlet:param name="struts_action" value="/mail/view_message" /><portlet:param name="folder_name" value="<%= result.getFolderName() %>" /><portlet:param name="msg_num" value="<%= Integer.toString(msg.getMessageNumber()) %>" /></portlet:renderURL>">
								<%= msg.getFrom()[0] %>
								</a></font>
							</td>
							<td width="10">
								&nbsp;
							</td>
							<td valign="top">
								<font class="portlet-font" style="font-size: x-small;"><a href="<portlet:renderURL><portlet:param name="struts_action" value="/mail/view_message" /><portlet:param name="folder_name" value="<%= result.getFolderName() %>" /><portlet:param name="msg_num" value="<%= Integer.toString(msg.getMessageNumber()) %>" /></portlet:renderURL>">
								<%= msg.getSubject() %>
								</a></font>
							</td>
							<td width="10">
								&nbsp;
							</td>
							<td valign="top">
								<font class="portlet-font" style="font-size: x-small;">

								<c:if test="<%= msg.getReceivedDate() != null %>">
									<%= df.format(msg.getReceivedDate()) %>
								</c:if>

								</font>
							</td>
							<td width="10">
								&nbsp;
							</td>
							<td valign="top">
								<font class="portlet-font" style="font-size: x-small;">
								<%= TextFormatter.formatKB(MailUtil.getSize(msg), locale) %>k
								</font>
							</td>
						</tr>
						<tr>
							<td colspan="8"><img border="0" height="4" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
						</tr>

		<%
					}

					if (messages.length == 0) {
		%>

						<tr>
							<td width="30"><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="30"></td>
							<td colspan="7">
								<font class="portlet-font" style="font-size: x-small;">
								<%= LanguageUtil.get(pageContext, "no-messages-found") %>
								</font>
							</td>
						</tr>

		<%
					}
		%>

					</table>
				</td>
			</tr>

			<c:if test="<%= i + 1 < results.size() %>">
				<tr>
					<td>
						<br>
					</td>
				</tr>
			</c:if>

		<%
		}
		%>

	</c:if>

	</table>
</liferay-ui:box>