<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<table border="0" cellpadding="0" cellspacing="0" <%= portletDisplay.isStateMax() ? "width=\"100%\"" : "" %>>
<tr>
	<td>
		<div class="portlet-header-bar">
			<div class="portlet-shadow-tl"><div></div></div>
			<div class="portlet-shadow-tr"><div></div></div>
			<div class="portlet-shadow-tc"><liferay-portlet:header-bar /></div>
		</div>
	</td>
</tr>
<tr>
	<td>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td class="portlet-shadow-ml"><div></div></td>
			<td class="portlet-container" width="100%">

			<div id="p_p_body_<%= portletDisplay.getId() %>" style="overflow: hidden; <%= (portletDisplay.isStateMin()) ? "height: 1px;" : "" %>">
    		    <div class="slide-maximize-reference">