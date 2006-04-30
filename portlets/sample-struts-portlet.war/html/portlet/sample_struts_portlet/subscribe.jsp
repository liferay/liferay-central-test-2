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

<bean:define id="firstName" name="subscribeForm" property="firstName" type="java.lang.String" />
<bean:define id="lastName" name="subscribeForm" property="lastName" type="java.lang.String" />
<bean:define id="emailAddress" name="subscribeForm" property="emailAddress" type="java.lang.String" />

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<font class="portlet-msg-error" style="font-size: x-small;">
		<html:errors />
		</font>
	</td>
</tr>
</table>

<table border="0" cellpadding="0" cellspacing="0">

<html:form action="/sample_struts_portlet/action/subscribe" method="post" focus="firstName">

<tr>
	<td>
		<font class="portlet-font" style="font-size: x-small;">First Name</font>
	</td>
	<td width="10">
		&nbsp;
	</td>
	<td>
		<html:text name="subscribeForm" property="firstName" size="23" styleClass="form-text" />
	</td>
</tr>
<tr>
	<td colspan="3">
		<br>
	</td>
</tr>
<tr>
	<td>
		<font class="portlet-font" style="font-size: x-small;">Last Name</font>
	</td>
	<td width="10">
		&nbsp;
	</td>
	<td>
		<html:text name="subscribeForm" property="lastName" size="23" styleClass="form-text" />
	</td>
</tr>
<tr>
	<td colspan="3">
		<br>
	</td>
</tr>
<tr>
	<td>
		<font class="portlet-font" style="font-size: x-small;">Email Address</font>
	</td>
	<td width="10">
		&nbsp;
	</td>
	<td>
		<html:text name="subscribeForm" property="emailAddress" size="23" styleClass="form-text" />
	</td>
</tr>
<tr>
	<td colspan="3">
		<br>
	</td>
</tr>
<tr>
	<td colspan="2"></td>
	<td>
		<html:submit>Subscribe</html:submit>
	</td>
</tr>

</html:form>

</table>