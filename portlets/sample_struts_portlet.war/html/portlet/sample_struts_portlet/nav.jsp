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

View Page:&nbsp;

<html:link page="/portlet_action/sample_struts_portlet/view">Default</html:link> &nbsp;|&nbsp;

<html:link page="/portlet_action/sample_struts_portlet/render/x">X</html:link> &nbsp;|&nbsp;

<html:link page="/portlet_action/sample_struts_portlet/render/y?hello=Hello+World%21">Y</html:link> &nbsp;|&nbsp;

<%
Map zParams = new HashMap();

zParams.put("hello", "Hello World!");
zParams.put("hi", "Hi Mom!");

pageContext.setAttribute("zParams", zParams);
%>

<html:link name="zParams" page="/portlet_action/sample_struts_portlet/z">Z</html:link><br><br>

Portlet URL:&nbsp;

<html:link actionURL="true" page="/portlet_action/sample_struts_portlet/action/x?x_param=bad_x_value">Action</html:link> &nbsp;|&nbsp;
<html:link page="/portlet_action/sample_struts_portlet/render/x">Render</html:link> <br><br>

Chart:&nbsp;

<a href="javascript: var viewChartWindow = window.open('<%= request.getContextPath() %>/portlet_action/sample_struts_portlet/view_chart?chart_type=area', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();">Area</a> &nbsp;|&nbsp;

<a href="javascript: var viewChartWindow = window.open('<%= request.getContextPath() %>/portlet_action/sample_struts_portlet/view_chart?chart_type=horizontal_bar', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();">Horizontal Bar</a> &nbsp;|&nbsp;

<a href="javascript: var viewChartWindow = window.open('<%= request.getContextPath() %>/portlet_action/sample_struts_portlet/view_chart?chart_type=line', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();">Line</a> &nbsp;|&nbsp;

<a href="javascript: var viewChartWindow = window.open('<%= request.getContextPath() %>/portlet_action/sample_struts_portlet/view_chart?chart_type=pie', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();">Pie</a> &nbsp;|&nbsp;

<a href="javascript: var viewChartWindow = window.open('<%= request.getContextPath() %>/portlet_action/sample_struts_portlet/view_chart?chart_type=vertical_bar', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();">Vertical Bar</a><br><br>

Struts Form:&nbsp;

<html:link page="/portlet_action/sample_struts_portlet/render/subscribe?firstName=John&lastName=Wayne&emailAddress=test@liferay.com">Subscribe</html:link> &nbsp;|&nbsp;

<html:link page="/portlet_action/sample_struts_portlet/render/unsubscribe?firstName=John&lastName=Wayne&emailAddress=test@liferay.com">Unsubscribe</html:link> &nbsp;|&nbsp;

<html:link page="/portlet_action/sample_struts_portlet/render/upload">Upload</html:link><br><br>

Struts Exception:&nbsp;
<html:link actionURL="true" page="/portlet_action/sample_struts_portlet/action/x?action_exception=true">Action</html:link> &nbsp;|&nbsp;

<html:link page="/portlet_action/sample_struts_portlet/render/x?render_exception=true">Render</html:link> <br><br>

</font>