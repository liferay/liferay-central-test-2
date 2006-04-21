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

<%@ include file="init.jsp" %>

<div class="portlet-container">
	<div class="portlet-header-bar">
		<c:if test="<%= Validator.isNotNull(portletDisplay.getTitle()) %>">
			<div class="portlet-title">
				<span><%= portletDisplay.getTitle() %></span>
			</div>
		</c:if>

		<div class="portlet-small-icon-bar">
			<c:if test="<%= portletDisplay.isShowEditIcon() %>">
				<script language="JavaScript">
					loadImage("p_<%= portletDisplay.getId() %>_edit", "<%= themeDisplay.getPathThemeImage() %>/portlet/<%= portletDisplay.isModeEdit() ? "leave_" : "" %>edit_on.gif", "<%= themeDisplay.getPathThemeImage() %>/portlet/<%= portletDisplay.isModeEdit() ? "leave_" : "" %>edit_off.gif");
				</script>

				<span class="portlet-small-icon"><a href="<%= portletDisplay.getURLEdit() %>"><img border="0" height="14" hspace="0" name="p_<%= portletDisplay.getId() %>_edit" src="<%= themeDisplay.getPathThemeImage() %>/portlet/<%= portletDisplay.isModeEdit() ? "leave_" : "" %>edit_off.gif" title="<%= LanguageUtil.get(pageContext, (portletDisplay.isModeEdit() ? "leave-" : "") + "edit-preferences") %>" vspace="0" width="14" onMouseOut="offRollOver();" onMouseOver="onRollOver('p_<%= portletDisplay.getId() %>_edit');"></a></span>
			</c:if>

			<c:if test="<%= portletDisplay.isShowHelpIcon() %>">
				<script language="JavaScript">
					loadImage("p_<%= portletDisplay.getId() %>_help", "<%= themeDisplay.getPathThemeImage() %>/portlet/<%= portletDisplay.isModeHelp() ? "leave_" : "" %>help_on.gif", "<%= themeDisplay.getPathThemeImage() %>/portlet/<%= portletDisplay.isModeHelp() ? "leave_" : "" %>help_off.gif");
				</script>

				<span class="portlet-small-icon"><a href="<%= portletDisplay.getURLHelp() %>"><img border="0" height="14" hspace="0" name="p_<%= portletDisplay.getId() %>_help" src="<%= themeDisplay.getPathThemeImage() %>/portlet/<%= portletDisplay.isModeHelp() ? "leave_" : "" %>help_off.gif" title="<%= LanguageUtil.get(pageContext, "help") %>" vspace="0" width="14" onMouseOut="offRollOver();" onMouseOver="onRollOver('p_<%= portletDisplay.getId() %>_help');"></a></span>
			</c:if>

			<c:if test="<%= portletDisplay.isShowPrintIcon() %>">
				<script language="JavaScript">
					loadImage("p_<%= portletDisplay.getId() %>_print", "<%= themeDisplay.getPathThemeImage() %>/portlet/<%= portletDisplay.isModePrint() ? "leave_" : "" %>print_on.gif", "<%= themeDisplay.getPathThemeImage() %>/portlet/<%= portletDisplay.isModePrint() ? "leave_" : "" %>print_off.gif");
				</script>

				<span class="portlet-small-icon"><a href="<%= portletDisplay.getURLPrint() %>"><img border="0" height="14" hspace="0" name="p_<%= portletDisplay.getId() %>_print" src="<%= themeDisplay.getPathThemeImage() %>/portlet/<%= portletDisplay.isModePrint() ? "leave_" : "" %>print_off.gif" title="<%= LanguageUtil.get(pageContext, "print") %>" vspace="0" width="14" onMouseOut="offRollOver();" onMouseOver="onRollOver('p_<%= portletDisplay.getId() %>_print');"></a></span>
			</c:if>

			<c:if test="<%= portletDisplay.isShowMoveIcon() %>">
				<script language="JavaScript">
					loadImage("p_<%= portletDisplay.getId() %>_up", "<%= themeDisplay.getPathThemeImage() %>/portlet/up_on.gif", "<%= themeDisplay.getPathThemeImage() %>/portlet/up_off.gif");
					loadImage("p_<%= portletDisplay.getId() %>_down", "<%= themeDisplay.getPathThemeImage() %>/portlet/down_on.gif", "<%= themeDisplay.getPathThemeImage() %>/portlet/down_off.gif");
				</script>

				<span class="portlet-small-icon"><a href="javascript: movePortletUp('<%= layoutId %>', '<%= portletDisplay.getId() %>', '<%= portletDisplay.getColumnId() %>');"><img border="0" height="14" hspace="0" name="p_<%= portletDisplay.getId() %>_up" src="<%= themeDisplay.getPathThemeImage() %>/portlet/up_off.gif" title="<%= LanguageUtil.get(pageContext, "up") %>" vspace="0" width="14" onMouseOut="offRollOver();" onMouseOver="onRollOver('p_<%= portletDisplay.getId() %>_up');"></a></span>

				<span class="portlet-small-icon"><a href="javascript: movePortletDown('<%= layoutId %>', '<%= portletDisplay.getId() %>', '<%= portletDisplay.getColumnId() %>');"><img border="0" height="14" hspace="0" name="p_<%= portletDisplay.getId() %>_down" src="<%= themeDisplay.getPathThemeImage() %>/portlet/down_off.gif" title="<%= LanguageUtil.get(pageContext, "down") %>" vspace="0" width="14" onMouseOut="offRollOver();" onMouseOver="onRollOver('p_<%= portletDisplay.getId() %>_down');"></a></span>
			</c:if>

			<c:if test="<%= portletDisplay.isShowMinIcon() %>">
				<script language="JavaScript">
					loadImage("p_<%= portletDisplay.getId() %>_min", "<%= themeDisplay.getPathThemeImage() %>/portlet/min_on.gif", "<%= themeDisplay.getPathThemeImage() %>/portlet/min_off.gif");
					loadImage("p_<%= portletDisplay.getId() %>_restore", "<%= themeDisplay.getPathThemeImage() %>/portlet/restore_on.gif", "<%= themeDisplay.getPathThemeImage() %>/portlet/restore_off.gif");
				</script>

				<span id="p_p_body_<%= portletDisplay.getId() %>_min_buttons" rowspan="3">
					<c:if test="<%= !portletDisplay.isStateMin() %>">
						<span class="portlet-small-icon"><a href="javascript: minimizePortlet('<%= layoutId %>', '<%= portletDisplay.getId() %>', false);"><img border="0" height="14" hspace="0" name="p_<%= portletDisplay.getId() %>_min" src="<%= themeDisplay.getPathThemeImage() %>/portlet/min_off.gif" title="<%= LanguageUtil.get(pageContext, "minimize") %>" vspace="0" width="14" onMouseOut="offRollOver();" onMouseOver="onRollOver('p_<%= portletDisplay.getId() %>_min');"></a></span>
					</c:if>

					<c:if test="<%= portletDisplay.isStateMin() %>">
						<span class="portlet-small-icon"><a href="javascript: minimizePortlet('<%= layoutId %>', '<%= portletDisplay.getId() %>', true);"><img border="0" height="14" hspace="0" name="p_<%= portletDisplay.getId() %>_restore" src="<%= themeDisplay.getPathThemeImage() %>/portlet/restore_off.gif" title="<%= LanguageUtil.get(pageContext, "restore") %>" vspace="0" width="14" onMouseOut="offRollOver();" onMouseOver="onRollOver('p_<%= portletDisplay.getId() %>_restore');"></a></span>
					</c:if>
				</span>
			</c:if>

			<c:if test="<%= portletDisplay.isShowMaxIcon() %>">
				<script language="JavaScript">
					loadImage("p_<%= portletDisplay.getId() %>_max", "<%= themeDisplay.getPathThemeImage() %>/portlet/max_on.gif", "<%= themeDisplay.getPathThemeImage() %>/portlet/max_off.gif");
					loadImage("p_<%= portletDisplay.getId() %>_restore", "<%= themeDisplay.getPathThemeImage() %>/portlet/restore_on.gif", "<%= themeDisplay.getPathThemeImage() %>/portlet/restore_off.gif");
				</script>

				<span id="p_p_body_<%= portletDisplay.getId() %>_max_buttons" rowspan="3">
					<c:if test="<%= !portletDisplay.isStateMax() %>">
						<span class="portlet-small-icon"><a href="<%= portletDisplay.getURLMax() %>"><img border="0" height="14" hspace="0" name="p_<%= portletDisplay.getId() %>_max" src="<%= themeDisplay.getPathThemeImage() %>/portlet/max_off.gif" title="<%= LanguageUtil.get(pageContext, "maximize") %>" vspace="0" width="14" onMouseOut="offRollOver();" onMouseOver="onRollOver('p_<%= portletDisplay.getId() %>_max');"></a></span>
					</c:if>

					<c:if test="<%= portletDisplay.isStateMax() %>">
						<span class="portlet-small-icon"><a href="<%= portletDisplay.getURLMax() %>"><img border="0" height="14" hspace="0" name="p_<%= portletDisplay.getId() %>_restore" src="<%= themeDisplay.getPathThemeImage() %>/portlet/restore_off.gif" title="<%= LanguageUtil.get(pageContext, "restore") %>" vspace="0" width="14" onMouseOut="offRollOver();" onMouseOver="onRollOver('p_<%= portletDisplay.getId() %>_restore');"></a></span>
					</c:if>
				</span>
			</c:if>

			<c:if test="<%= portletDisplay.isShowCloseIcon() %>">
				<script language="JavaScript">
					loadImage("p_<%= portletDisplay.getId() %>_close", "<%= themeDisplay.getPathThemeImage() %>/portlet/close_on.gif", "<%= themeDisplay.getPathThemeImage() %>/portlet/close_off.gif");
				</script>

				<span class="portlet-small-icon"><a href="javascript: closePortlet('<%= layoutId %>', '<%= portletDisplay.getId() %>', '<%= portletDisplay.getColumnId() %>');"><img border="0" height="14" hspace="0" name="p_<%= portletDisplay.getId() %>_close" src="<%= themeDisplay.getPathThemeImage() %>/portlet/close_off.gif" title="<%= LanguageUtil.get(pageContext, "remove") %>" vspace="0" width="14" onMouseOut="offRollOver();" onMouseOver="onRollOver('p_<%= portletDisplay.getId() %>_close');"></a></span>
			</c:if>
		</div>
	</div>

	<div class="portlet-box">
		<div id="p_p_body_<%= portletDisplay.getId() %>" class="portlet-content" <%= (portletDisplay.isStateMin()) ? "style=\"display: none;\"" : "" %>>
