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
	font-weight: bold;
	padding: 2px 5px 2px 15px;
	position: absolute;
	background-color: <%= colorScheme.getPortletTitleBg() %>;
	cursor: pointer;
}
#portlet-mail-folder-pane ul {
	cursor: pointer;
	margin: 8px;
	overflow: hidden;
	width: 80px;
}
#portlet-mail-folder-pane ul li { margin: 1px 0 1px 0; }
.portlet-mail-folder-selected {
	background: url(<%= themeDisplay.getPathThemeImage() %>/arrows/01_right.gif) scroll no-repeat 3px left;
}
#portlet-mail-folder-pane ul li { padding: 2px 2px 2px 15px; }
#portlet-mail-folder-pane-td { border: 1px solid <%= mailLineColor %>; }
#portlet-mail-folder-pane { overflow: hidden; }
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
#portlet-mail-msgs-preview-pane table {
	cursor: pointer;
}
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
.portlet-mail-msgs-title {
	background: url(<%= themeDisplay.getPathColorSchemeImage() %>/portlet_menu_bg_gradient.gif) repeat-x;
}

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
	<a href="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/mailbox/edit_message" /></portlet:actionURL>"
	>Compose</a> |
	Reply |
	Forward |
	Print |
	<a href="javascript:Mail.deleteSelectedMessages()">Delete</a> |
	Move to
	<select id="portlet-mail-folder-select">
	</select>
</div>

<table cellspacing="0" cellpadding="0" border="0" style="background-color: <%= colorScheme.getPortletBg() %>">
<tr>
	<td id="portlet-mail-folder-pane-td" valign="top">
		<div id="portlet-mail-folder-pane">
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
						<%--
						<%@ include file="/html/portlet/mini_gallery/preview_pane.jsp" %>
						--%>
						<table cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="portlet-mail-msgs-title">
								<div id="portlet-mail-msgs-title-from">
									<div>From</div>
								</div>
							</td>
							<td id="portlet-mail-msgs-from-handle" style="cursor: e-resize">
								<div class="portlet-mail-msgs-title-handle"></div>
							</td>
							<td class="portlet-mail-msgs-title">
								<div id="portlet-mail-msgs-title-subject">
									<div>Subject</div>
								</div>
							</td>
							<td id="portlet-mail-msgs-subject-handle" style="cursor: e-resize">
								<div class="portlet-mail-msgs-title-handle"></div>
							</td>
							<td class="portlet-mail-msgs-title">
								<div id="portlet-mail-msgs-title-received">
									<div>Received</div>
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

<script type="text/javascript">
	Mailbox.highlightColor = "<%= colorScheme.getPortletMenuBg() %>";
	Mailbox.userId = "<%= request.getRemoteUser() %>";
	Mailbox.init();
</script>
