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

<%@ include file="/html/portlet/init.jsp" %>


<script src="<%= themeDisplay.getPathJavaScript() %>/portlet/mailbox.js" type="text/javascript"></script>

<style type="text/css">
<%
String mailBgColor = "#f4f5eb";
String mailLineColor = "#b3b6b0";
%>
#p_p_body_<%= PortletKeys.MAIL %> { background-color: <%= mailBgColor %>; }
#portlet-mail-drag-indicator {
	color: <%= colorScheme.getLayoutBg() %>;
	font-weight: bold;
	padding: 2px 5px 2px 15px;
	position: absolute;
	background-color: <%= colorScheme.getLayoutTabSelectedText() %>;
	cursor: pointer;
}
#portlet-mail-folder-pane ul {
	cursor: pointer;
	margin: 8px;
	overflow: hidden;
	width: 80px;
}
.portlet-mail-folder-selected { background-color: <%= colorScheme.getLayoutTabBg() %>; }
#portlet-mail-folder-pane ul li { padding: 2px 2px 2px 10px; margin: 1px 0 1px 0}
#portlet-mail-folder-pane-td { border: 1px solid <%= mailLineColor %>; }
#portlet-mail-folder-pane { overflow: hidden; width: 100px; }
#portlet-mail-msgs-pane { width: 100%; }
.portlet-mail-msgs-pane-td { border: 1px solid <%= mailLineColor %>; }
#portlet-mail-handle { background-color: <%= mailBgColor %>; }
#portlet-mail-handle div { width: 5px; height: 1px; font-size: 0; }
#portlet-mail-msgs-preview-pane {
	height: 200px;
	overflow: hidden;
	<c:if test="<%= BrowserSniffer.is_ie(request) %>">
		overflow-y: auto;
	</c:if>
	<c:if test="<%= !BrowserSniffer.is_ie(request) %>">
		overflow: -moz-scrollbars-vertical;
	</c:if>
	width: 100%;
}
#portlet-mail-msgs-preview-pane table { cursor: pointer; }
#portlet-mail-msgs-handle {
	background-color: <%= mailBgColor %>;
	font-size: 0;
	height: 5px;
}

#portlet-mail-msgs-detailed-pane {
	height: 200px;
	overflow: hidden;
	<c:if test="<%= BrowserSniffer.is_ie(request) %>">
		overflow-y: auto;
	</c:if>
	<c:if test="<%= !BrowserSniffer.is_ie(request) %>">
		overflow: -moz-scrollbars-vertical;
	</c:if>
}

#portlet-mail-msgs-title-from { overflow: hidden; width: 150px; }
#portlet-mail-msgs-title-from div { padding: 2px 0 2px 5px; }
#portlet-mail-msgs-title-subject { overflow: hidden; width: 250px; }
#portlet-mail-msgs-title-subject div { padding: 2px 0 2px 5px; }
#portlet-mail-msgs-title-received { overflow: hidden; width: 200px; }
#portlet-mail-msgs-title-received div { padding: 2px 0 2px 5px; }
.portlet-mail-msgs-title { background-color: <%= colorScheme.getLayoutTabBg() %>; }

#portlet-mail-msgs-from-handle { }
#portlet-mail-msgs-subject-handle { }
.portlet-mail-msgs-title-handle { font-size: 0; height: 1px; width: 3px; }

#portlet-mail-msgs-from { overflow: hidden; width: 150px; }
#portlet-mail-msgs-from div { padding-left: 5px; width: 800px; position: relative; }
#portlet-mail-msgs-subject { overflow: hidden; width: 250px; }
#portlet-mail-msgs-subject div { padding-left: 5px; width: 800px; position: relative; }
#portlet-mail-msgs-received { overflow: hidden; width: 200px; }
#portlet-mail-msgs-received div { padding-left: 5px; width: 800px; position: relative; }

#portlet-mail-msg-body { margin: 5px 5px 5px 10px; }

</style>

<div style="padding: 5px">
	<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mailbox/edit_message" /></portlet:renderURL>">
		Compose
	</a> |
	<a href="javascript: Mailbox.submitCompose('reply', document.<portlet:namespace />fm)">
		Reply
	</a> |
	<a href="javascript: Mailbox.submitCompose('forward', document.<portlet:namespace />fm)">
		Forward
	</a> |
	Print |
	<a href="javascript:Mail.deleteSelectedMessages()">Delete</a> |
	Move to
	<select id="portlet-mail-folder-select">
	</select>
</div>

<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mailbox/edit_message" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm">
	<input type="hidden" id="portlet-mail-compose-action" name="<portlet:namespace />composeAction" />
	<input type="hidden" id="portlet-mail-message-id" name="<portlet:namespace />messageId" />
	<input type="hidden" id="portlet-mail-folder-id" name="<portlet:namespace />folderId" />
</form>

<table cellspacing="0" cellpadding="0" border="0" style="background-color: <%= colorScheme.getPortletBg() %>">
<tr>
	<td id="portlet-mail-folder-pane-td" valign="top">
		<div id="portlet-mail-folder-pane">
			<div style="text-align: center; padding-top: 50px">
				<img src="<%= themeDisplay.getPathThemeImage() %>/progress_bar/loading_animation.gif" />
			</div>
		</div>
	</td>
	<td id="portlet-mail-handle" style="cursor: e-resize">
		<div style="width: 5px; height: 30px; font-size: 0;"></div>
	</td>
	<td valign="top" width="100%">
		<div id="portlet-mail-msgs-pane">

			<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tr>
				<td class="portlet-mail-msgs-pane-td" width="100%">
					<div id="portlet-mail-msgs-preview-pane">
						<table cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="portlet-mail-msgs-title">
								<div id="portlet-mail-msgs-title-from">
									<div><%= LanguageUtil.get(pageContext, "from") %></div>
									<div style="display:none"><%= LanguageUtil.get(pageContext, "to") %></div>
								</div>
							</td>
							<td id="portlet-mail-msgs-from-handle" style="cursor: e-resize">
								<div class="portlet-mail-msgs-title-handle"></div>
							</td>
							<td class="portlet-mail-msgs-title">
								<div id="portlet-mail-msgs-title-subject">
									<div><%= LanguageUtil.get(pageContext, "subject") %></div>
								</div>
							</td>
							<td id="portlet-mail-msgs-subject-handle" style="cursor: e-resize">
								<div class="portlet-mail-msgs-title-handle"></div>
							</td>
							<td class="portlet-mail-msgs-title">
								<div id="portlet-mail-msgs-title-received">
									<div><%= LanguageUtil.get(pageContext, "date") %><img src="<%= themeDisplay.getPathThemeImage() %>/arrows/01_down.gif"</div>
								</div>
							</td>
						</tr>
						</table>

						<table cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td>
								<div id="portlet-mail-msgs-from">
								</div>
							</td>
							<td>
								<div class="portlet-mail-msgs-title-handle"></div>
							</td>
							<td>
								<div id="portlet-mail-msgs-subject">
								</div>
							</td>
							<td>
								<div class="portlet-mail-msgs-title-handle"></div>
							</td>
							<td>
								<div id="portlet-mail-msgs-received">
								</div>
							</td>
						</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="portlet-mail-msgs-handle" style="cursor: n-resize">
					</div>
				</td>
			</tr>
			<tr>
				<td class="portlet-mail-msgs-pane-td" width="100%">
					<div id="portlet-mail-msgs-detailed-pane">
						<div id="portlet-mail-msg-body">
						</div>
					</div>
				</td>
			</tr>
			</table>
		</div>
	</td>
</tr>
</table>

<%
	String folderId = (String)request.getAttribute("folderId");
	Long messageId = (Long)request.getAttribute("messageId");
%>

<script type="text/javascript">
	<c:if test="<%= folderId != null %>">
		Mailbox.currentFolderId = "<%= folderId %>";
	</c:if>
	<c:if test="<%= messageId != null %>">
		Mailbox.currentMessageId = <%= messageId.toString() %>;
	</c:if>

	Mailbox.highlightColor = "<%= colorScheme.getPortletMenuBg() %>";
	Mailbox.colorSelected = "<%= colorScheme.getLayoutTabBg() %>";
	Mailbox.init();
</script>