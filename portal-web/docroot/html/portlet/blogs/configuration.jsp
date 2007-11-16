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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<fieldset>
	<legend><liferay-ui:message key="view"/></legend>
	<table class="liferay-table">
		<tr>
			<td>
				<liferay-ui:message key="maximum-items-to-display" />
			</td>
			<td>
				<select name="<portlet:namespace />delta">
					<option <%= (delta == 1) ? "selected" : "" %> value="1">1</option>
					<option <%= (delta == 2) ? "selected" : "" %> value="2">2</option>
					<option <%= (delta == 3) ? "selected" : "" %> value="3">3</option>
					<option <%= (delta == 4) ? "selected" : "" %> value="4">4</option>
					<option <%= (delta == 5) ? "selected" : "" %> value="5">5</option>
					<option <%= (delta == 10) ? "selected" : "" %> value="10">10</option>
					<option <%= (delta == 15) ? "selected" : "" %> value="15">15</option>
					<option <%= (delta == 20) ? "selected" : "" %> value="20">20</option>
					<option <%= (delta == 25) ? "selected" : "" %> value="25">25</option>
					<option <%= (delta == 30) ? "selected" : "" %> value="30">30</option>
					<option <%= (delta == 40) ? "selected" : "" %> value="40">40</option>
					<option <%= (delta == 50) ? "selected" : "" %> value="50">50</option>
					<option <%= (delta == 60) ? "selected" : "" %> value="60">60</option>
					<option <%= (delta == 70) ? "selected" : "" %> value="70">70</option>
					<option <%= (delta == 80) ? "selected" : "" %> value="80">80</option>
					<option <%= (delta == 90) ? "selected" : "" %> value="90">90</option>
					<option <%= (delta == 100) ? "selected" : "" %> value="100">100</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="display-style" />
			</td>
			<td>
				<select name="<portlet:namespace />displayStyle">
					<option <%= (displayStyle.equals("full-content")) ? "selected" : "" %> value="full-content"><liferay-ui:message key="full-content"/></option>
					<option <%= (displayStyle.equals("abstract")) ? "selected" : "" %> value="abstract"><liferay-ui:message key="abstract"/></option>
					<option <%= (displayStyle.equals("title")) ? "selected" : "" %> value="title"><liferay-ui:message key="title"/></option>
				</select>
			</td>
		</tr>
	</table>
</fieldset>

<br />

<fieldset>
	<legend><liferay-ui:message key="feed"/></legend>
	<table class="liferay-table">
		<tr>
			<td>
				<liferay-ui:message key="maximum-items-to-display" />
			</td>
			<td>
				<select name="<portlet:namespace />feedDelta">
					<option <%= (feedDelta == 1) ? "selected" : "" %> value="1">1</option>
					<option <%= (feedDelta == 2) ? "selected" : "" %> value="2">2</option>
					<option <%= (feedDelta == 3) ? "selected" : "" %> value="3">3</option>
					<option <%= (feedDelta == 4) ? "selected" : "" %> value="4">4</option>
					<option <%= (feedDelta == 5) ? "selected" : "" %> value="5">5</option>
					<option <%= (feedDelta == 10) ? "selected" : "" %> value="10">10</option>
					<option <%= (feedDelta == 15) ? "selected" : "" %> value="15">15</option>
					<option <%= (feedDelta == 20) ? "selected" : "" %> value="20">20</option>
					<option <%= (feedDelta == 25) ? "selected" : "" %> value="25">25</option>
					<option <%= (feedDelta == 30) ? "selected" : "" %> value="30">30</option>
					<option <%= (feedDelta == 40) ? "selected" : "" %> value="40">40</option>
					<option <%= (feedDelta == 50) ? "selected" : "" %> value="50">50</option>
					<option <%= (feedDelta == 60) ? "selected" : "" %> value="60">60</option>
					<option <%= (feedDelta == 70) ? "selected" : "" %> value="70">70</option>
					<option <%= (feedDelta == 80) ? "selected" : "" %> value="80">80</option>
					<option <%= (feedDelta == 90) ? "selected" : "" %> value="90">90</option>
					<option <%= (feedDelta == 100) ? "selected" : "" %> value="100">100</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="display-style" />
			</td>
			<td>
				<select name="<portlet:namespace />feedDisplayStyle">
					<option <%= (feedDisplayStyle.equals("full-content")) ? "selected" : "" %> value="full-content"><liferay-ui:message key="full-content"/></option>
					<option <%= (feedDisplayStyle.equals("abstract")) ? "selected" : "" %> value="abstract"><liferay-ui:message key="abstract"/></option>
					<option <%= (feedDisplayStyle.equals("title")) ? "selected" : "" %> value="title"><liferay-ui:message key="title"/></option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="format" />
			</td>
			<td>
				<select name="<portlet:namespace />feedFormat">
					<option <%= (feedFormat.equals("rss10")) ? "selected" : "" %> value="rss10">RSS 1.0</option>
					<option <%= (feedFormat.equals("rss20")) ? "selected" : "" %> value="rss20">RSS 2.0</option>
					<option <%= (feedFormat.equals("atom10")) ? "selected" : "" %> value="atom10">Atom 1.0</option>
				</select>
			</td>
		</tr>
	</table>
</fieldset>

<br />

<input type="button" value="<liferay-ui:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);" />

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />hangmanWordList);
	</script>
</c:if>