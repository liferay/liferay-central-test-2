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

<%@ include file="/html/portlet/sample_struts_portlet/init.jsp" %>

<font class="portlet-font" style="font-size: x-small;">

Window State:&nbsp;

<html:link page="/sample_struts_portlet/view?windowState=maximized">Maximize</html:link> &nbsp;|&nbsp;

<html:link page="/sample_struts_portlet/view?windowState=normal">Normal</html:link><br><br>

Portlet Mode:&nbsp;

<html:link page="/sample_struts_portlet/edit?portletMode=edit">Edit</html:link> &nbsp;|&nbsp;

<html:link page="/sample_struts_portlet/help?portletMode=help">Help</html:link> &nbsp;|&nbsp;

<html:link page="/sample_struts_portlet/print?portletMode=print">Print</html:link> &nbsp;|&nbsp;

<html:link page="/sample_struts_portlet/view?portletMode=view">View</html:link><br><br>

View Page:&nbsp;

<html:link page="/sample_struts_portlet/view">Default</html:link> &nbsp;|&nbsp;

<html:link page="/sample_struts_portlet/x">X</html:link> &nbsp;|&nbsp;

<html:link page="/sample_struts_portlet/y?hello=Hello+World%21">Y</html:link> &nbsp;|&nbsp;

<%
Map zParams = new HashMap();

zParams.put("hello", "Hello World!");
zParams.put("hi", "Hi Mom!");

pageContext.setAttribute("zParams", zParams);
%>

<html:link name="zParams" page="/sample_struts_portlet/z">Z</html:link><br><br>

Portlet URL:&nbsp;

<a href="<portlet:actionURL><portlet:param name="struts_action" value="/sample_struts_portlet/x" /><portlet:param name="x_param" value="bad_x_value" /></portlet:actionURL>">Action</a> &nbsp;|&nbsp;

<a href="<portlet:renderURL><portlet:param name="struts_action" value="/sample_struts_portlet/x" /></portlet:renderURL>">Render</a><br><br>

User Information:&nbsp;

<html:link page="/sample_struts_portlet/user_attributes">Portlet and Custom User Attributes</html:link><br><br>

Shared Sessions:&nbsp;

<html:link page="/sample_struts_portlet/portlet_session_attributes">Portlet Session Attributes</html:link> &nbsp;|&nbsp;

<a href="<%= request.getContextPath() %>/test_session/servlet_session_attributes">Servlet Session Attributes</a><br><br>

Portlet Display:&nbsp;

<html:link page="/sample_struts_portlet/portlet_display_attributes">Portlet Display Attributes</html:link><br><br>

Chart:&nbsp;

<a href="javascript: var viewChartWindow = window.open('<%= request.getContextPath() %>/portlet_action/sample_struts_portlet/view_chart?chart_type=area', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();">Area</a> &nbsp;|&nbsp;

<a href="javascript: var viewChartWindow = window.open('<%= request.getContextPath() %>/portlet_action/sample_struts_portlet/view_chart?chart_type=horizontal_bar', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();">Horizontal Bar</a> &nbsp;|&nbsp;

<a href="javascript: var viewChartWindow = window.open('<%= request.getContextPath() %>/portlet_action/sample_struts_portlet/view_chart?chart_type=line', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();">Line</a> &nbsp;|&nbsp;

<a href="javascript: var viewChartWindow = window.open('<%= request.getContextPath() %>/portlet_action/sample_struts_portlet/view_chart?chart_type=pie', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();">Pie</a> &nbsp;|&nbsp;

<a href="javascript: var viewChartWindow = window.open('<%= request.getContextPath() %>/portlet_action/sample_struts_portlet/view_chart?chart_type=vertical_bar', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();">Vertical Bar</a><br><br>

Struts Form:&nbsp;

<html:link page="/sample_struts_portlet/subscribe?firstName=John&lastName=Wayne&emailAddress=test@liferay.com">Subscribe</html:link> &nbsp;|&nbsp;

<html:link page="/sample_struts_portlet/unsubscribe?firstName=John&lastName=Wayne&emailAddress=test@liferay.com">Unsubscribe</html:link> &nbsp;|&nbsp;

<html:link page="/sample_struts_portlet/upload">Upload</html:link><br><br>

Struts Exception:&nbsp;

<a href="<portlet:actionURL><portlet:param name="struts_action" value="/sample_struts_portlet/x" /><portlet:param name="action_exception" value="true" /></portlet:actionURL>">Action</a> &nbsp;|&nbsp;

<a href="<portlet:renderURL><portlet:param name="struts_action" value="/sample_struts_portlet/x" /><portlet:param name="render_exception" value="true" /></portlet:renderURL>">Render</a>

</font>