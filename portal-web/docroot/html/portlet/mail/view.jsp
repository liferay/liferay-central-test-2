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

<c:if test="<%= Validator.isNull(forwardAddress) %>">
	<c:if test="<%= user.hasCompanyMx() %>">

		<%
		Folder[] moveToFolders = MailUtil.getMoveToFolders(request);

		Folder folder = (Folder)session.getAttribute(WebKeys.MAIL_FOLDER);
		String folderName = folder.getName();

		Message[] messages = folder.getMessages();
		MailUtil.sort(folderName, messages, prefs);

		int unreadMsgCount = folder.getUnreadMessageCount();

		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		df.setTimeZone(timeZone);
		%>

		<table border="0" cellpadding="4" cellspacing="0" width="100%">
		<tr>
			<td class="beta-gradient" nowrap width="60%">

				<%
				String addressBookLayoutId = PortalUtil.getLayoutIdWithPortletId(layouts, PortletKeys.ADDRESS_BOOK, layoutId);

				PortletURL addressBookURL = new PortletURLImpl(request, PortletKeys.ADDRESS_BOOK, addressBookLayoutId, false);

				addressBookURL.setWindowState(WindowState.MAXIMIZED);
				addressBookURL.setPortletMode(PortletMode.VIEW);

				addressBookURL.setParameter("struts_action", "/address_book/view_all");
				%>

				<font class="beta" size="2"><a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/view_folder" /></portlet:renderURL>"><bean:message key="INBOX" /></a> - <a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/compose_message" /></portlet:renderURL>"><bean:message key="compose" /></a> - <a class="beta" href="<%= addressBookURL.toString() %>"><bean:message key="address-book" /></a> - <a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/view_folders" /></portlet:renderURL>"><bean:message key="folders" /></a> - <a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/search" /></portlet:renderURL>"><bean:message key="search" /></a> - <a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/pop_info" /></portlet:renderURL>"><bean:message key="pop-info" /></a></font>
			</td>
			<td align="right" class="beta-gradient" nowrap width="40%">
				<font class="beta" size="1"><b><a class="beta" href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/view_folder" /></portlet:renderURL>"><%= (MailUtil.isDefaultFolder(folderName)) ? LanguageUtil.get(pageContext, folderName) : folderName %></a> (<%= folder.getUnreadMessageCount() %> <%= LanguageUtil.get(pageContext, "new") %>)</b></font>
			</td>
		</tr>
		</table>

		<portlet:renderURL var="redirectURL" />

		<form method="post" name="<portlet:namespace />fm">
		<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirectURL %>">
		<input name="<portlet:namespace />folder_name_2" type="hidden" value="">

		<table border="0" cellpadding="4" cellspacing="0" width="100%">
		<tr>
			<td align="center">
				<table border="0" cellpadding="0" cellspacing="0" width="98%">
				<tr>
					<td nowrap valign="middle">
						<font size="2"><b>&nbsp;<a href="<portlet:actionURL><portlet:param name="struts_action" value="/mail/change_order" /><portlet:param name="redirect" value="<%= redirectURL %>" /><portlet:param name="order_by_col" value="n" /></portlet:actionURL>">*</a>&nbsp;</b></font>
					</td>
					<td width="5"><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="5"></td>
					<td>
						<img border="0" height="14" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/mail/clip.gif" vspace="0" width="10">
					</td>
					<td width="5"><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="5"></td>
					<td nowrap valign="middle">
						<input name="<portlet:namespace />msg_numbers_allbox" type="checkbox"
							onClick="
								for (var i = 0; i < document.<portlet:namespace />fm.elements.length; i++) {
									var e = document.<portlet:namespace />fm.elements[i];

									if ((e.name == '<portlet:namespace />msg_numbers') && (e.type == 'checkbox')) {
										e.checked = this.checked;

										<c:if test="<%= !BrowserSniffer.is_ns_4(request) %>">
											if (e.checked) {
												document.getElementById(e.value + '_top').className = 'bg';
												document.getElementById(e.value).className = 'bg';
												document.getElementById(e.value + '_bot').className = 'bg';
											}
											else {
												document.getElementById(e.value + '_top').className = 'gamma';
												document.getElementById(e.value).className = 'gamma';
												document.getElementById(e.value + '_bot').className = 'gamma';
											}
										</c:if>
									}
								}"
						>
					</td>
					<td width="10">
						&nbsp;
					</td>

					<%
					for (int i = 0; i < colOrder.length(); i++) {
					%>

						<td nowrap valign="middle">
							<font class="portlet-font" style="font-size: x-small;"><b>

							<c:if test="<%= orderByCol.charAt(0) == colOrder.charAt(i) %>">
								<i>
							</c:if>

							<a href="<portlet:actionURL><portlet:param name="struts_action" value="/mail/change_order" /><portlet:param name="redirect" value="<%= redirectURL %>" /><portlet:param name="order_by_col" value="<%= Character.toString(colOrder.charAt(i)) %>" /></portlet:actionURL>">

							<c:if test="<%= colOrder.charAt(i) == 'f' %>">
								<%= LanguageUtil.get(pageContext, "from") %>
							</c:if>

							<c:if test="<%= colOrder.charAt(i) == 's' %>">
								<%= LanguageUtil.get(pageContext, "subject") %>
							</c:if>

							<c:if test="<%= colOrder.charAt(i) == 'd' %>">
								<%= LanguageUtil.get(pageContext, "date") %>
							</c:if>

							<c:if test="<%= colOrder.charAt(i) == 'z' %>">
								<%= LanguageUtil.get(pageContext, "size") %>
							</c:if>

							</a>

							<c:if test="<%= orderByCol.charAt(0) == colOrder.charAt(i) %>">
								</i>
							</c:if>

							</b></font>
						</td>

						<c:if test="<%= (i + 1) < colOrder.length() %>">
							<td width="10">
								&nbsp;
							</td>
						</c:if>

					<%
					}
					%>

				</tr>

				<%
				for (int i = 1; (i <= folder.getMessageCount()) && (i <= messagesPerPortlet); i++) {
					Message msg = messages[i - 1];

					boolean seen = false;
					if (msg.getFlags().contains(Flags.Flag.SEEN)) {
						seen = true;
					}

					boolean answered = false;
					if (msg.getFlags().contains(Flags.Flag.ANSWERED)) {
						answered = true;
					}

					InternetAddress[] ia = (InternetAddress[])msg.getFrom();
				%>

					<tr id="<%= msg.getMessageNumber() %>_top">
						<td colspan="13"><img border="0" height="2" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
					</tr>
					<tr id="<%= msg.getMessageNumber() %>">
						<td colspan="2" nowrap valign="middle">
							<font class="portlet-font" style="font-size: xx-small;"><b>
							<%= (!seen) ? "&nbsp;N&nbsp;" : "" %>
							<%= (answered) ? "&nbsp;A&nbsp;" : "" %>
							</b></font>
						</td>
						<td colspan="2">
							<c:if test="<%= MailUtil.hasAttachment(msg) %>">
								<img border="0" height="14" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/mail/clip.gif" vspace="0" width="10">
							</c:if>
						</td>
						<td colspan="2" nowrap valign="middle">
							<input name="<portlet:namespace />msg_numbers" type="checkbox" value="<%= msg.getMessageNumber() %>"
								onClick="
									<c:if test="<%= !BrowserSniffer.is_ns_4(request) %>">
										if (this.checked) {
											document.getElementById('<%= msg.getMessageNumber() %>_top').className = 'bg';
											document.getElementById('<%= msg.getMessageNumber() %>').className = 'bg';
											document.getElementById('<%= msg.getMessageNumber() %>_bot').className = 'bg';
										}
										else {
											document.getElementById('<%= msg.getMessageNumber() %>_top').className = 'gamma';
											document.getElementById('<%= msg.getMessageNumber() %>').className = 'gamma';
											document.getElementById('<%= msg.getMessageNumber() %>_bot').className = 'gamma';
										}
									</c:if>

									checkAllBox(document.<portlet:namespace />fm, '<portlet:namespace />msg_numbers', document.<portlet:namespace />fm.<portlet:namespace />msg_numbers_allbox);"
							>
						</td>

						<%
						for (int j = 0; j < colOrder.length(); j++) {
						%>

							<td valign="middle">
								<font class="portlet-font" style="font-size: xx-small;">

								<c:if test="<%= !seen %>">
									<b>
								</c:if>

								<c:if test="<%= colOrder.charAt(j) == 'f' %>">
									<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/view_message" /><portlet:param name="folder_name" value="<%= folderName %>" /><portlet:param name="msg_num" value="<%= Integer.toString(msg.getMessageNumber()) %>" /><portlet:param name="msg_i" value="<%= Integer.toString(i - 1) %>" /></portlet:renderURL>">

									<c:if test="<%= ia != null && ia.length > 0 && ia[0] != null %>">
										<%= (ia[0].getPersonal() == null) ? ia[0].getAddress() : ia[0].getPersonal() %>
									</c:if>

									</a>
								</c:if>

								<c:if test="<%= colOrder.charAt(j) == 's' %>">
									<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mail/view_message" /><portlet:param name="folder_name" value="<%= folderName %>" /><portlet:param name="msg_num" value="<%= Integer.toString(msg.getMessageNumber()) %>" /><portlet:param name="msg_i" value="<%= Integer.toString(i - 1) %>" /></portlet:renderURL>">
									<%= Validator.isNull(msg.getSubject()) ? "" : msg.getSubject() %>
									</a>
								</c:if>

								<c:if test="<%= (colOrder.charAt(j) == 'd') && (msg.getReceivedDate() != null) %>">
									<%= df.format(msg.getReceivedDate()) %>
								</c:if>

								<c:if test="<%= colOrder.charAt(j) == 'z' %>">
									<%= TextFormatter.formatKB(MailUtil.getSize(msg), locale) %>k
								</c:if>

								<c:if test="<%= !seen %>">
									</b>
								</c:if>

								</font>
							</td>

							<c:if test="<%= (j + 1) < colOrder.length() %>">
								<td width="10">
									&nbsp;
								</td>
							</c:if>

						<%
						}
						%>

					</tr>
					<tr id="<%= msg.getMessageNumber() %>_bot">
						<td colspan="13"><img border="0" height="2" hspace="0" src="<%= themeDisplay.getPathThemeImage() %>/spacer.gif" vspace="0" width="1"></td>
					</tr>

				<%
				}
				%>

				</table>
			</td>
		</tr>
		<tr class="gamma">
			<td>
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td>
						<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/delete_message" /></portlet:actionURL>');">

						<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "block") %>' onClick="if (listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />msg_numbers_allbox') == '') { alert('<%= UnicodeLanguageUtil.get(pageContext, "you-did-not-select-any-messages") %>'); } else { if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-block-the-selected-senders") %>')) { submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/block_sender" /></portlet:actionURL>'); } }">

						<select name="<portlet:namespace />mail_f_l_1" onChange="if (listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />msg_numbers_allbox') == '') { alert('<%= UnicodeLanguageUtil.get(pageContext, "you-did-not-select-any-messages") %>'); this.selectedIndex = 0; } else { if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-move-selected-messages-to") %> ' + document.<portlet:namespace />fm.<portlet:namespace />mail_f_l_1[document.<portlet:namespace />fm.<portlet:namespace />mail_f_l_1.selectedIndex].text + '?')) { document.<portlet:namespace />fm.<portlet:namespace />folder_name_2.value = document.<portlet:namespace />fm.<portlet:namespace />mail_f_l_1.options[document.<portlet:namespace />fm.<portlet:namespace />mail_f_l_1.options.selectedIndex].value; submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mail/move_message" /></portlet:actionURL>'); } else { this.selectedIndex = 0; } }">
							<option value=""><%= LanguageUtil.get(pageContext, "move-to") %>...</option>

							<%
							for (int i = 0; i < moveToFolders.length; i++) {
								Folder moveToFolder = moveToFolders[i];
							%>

								<c:if test="<%= !folderName.equals(moveToFolder.getName()) %>">
									<option value="<%= moveToFolder.getName() %>"><%= LanguageUtil.get(pageContext, moveToFolder.getName()) %></option>
								</c:if>

							<%
							}

							for (int i = 0; i < extraFolders.length; i++) {
								Folder extraFolder = MailUtil.getFolder(request, extraFolders[i]);
							%>

								<c:if test="<%= !folderName.equals(extraFolder.getName()) %>">
									<option value="<%= extraFolder.getName() %>"><%= extraFolder.getName() %></option>
								</c:if>

							<%
							}
							%>

						</select>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		</table>

		</form>
	</c:if>

	<c:if test="<%= !user.hasCompanyMx() %>">
		<liferay-util:include page="/html/portlet/mail/register.jsp" />
	</c:if>
</c:if>

<c:if test="<%= Validator.isNotNull(forwardAddress) %>">
	<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" portletMode="<%= PortletMode.EDIT.toString() %>"><portlet:param name="struts_action" value="/mail/edit" /><portlet:param name="page" value="forward_address" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
	<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">
	<input name="<portlet:namespace />forward_address" type="hidden" value="">

	<table border="0" cellpadding="4" cellspacing="0" width="100%">
	<tr>
		<td align="center">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;">
					<%= LanguageUtil.format(pageContext, "all-email-from-x-is-being-forwarded-to-x", new Object[] {"<b>" + user.getEmailAddress() + "</b>", "<b>" + StringUtil.replace(forwardAddress, StringPool.SPACE, ", ") + "</b>"}, false) %>
					</font>
				</td>
			</tr>
			</table>

			<br>

			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "disable-forwarding") %>' onClick="submitForm(document.<portlet:namespace />fm);">
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>

	</form>
</c:if>