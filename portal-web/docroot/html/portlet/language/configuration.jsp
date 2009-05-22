<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/language/init.jsp" %>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="languages" />
	</td>
	<td>
		<input name="<portlet:namespace />languageIds" type="hidden" value="" />

		<%
		Set availableLanguageIdsSet = SetUtil.fromArray(availableLanguageIds);

		// Left list

		List leftList = new ArrayList();

		for (int i = 0; i < languageIds.length; i++) {
			String languageId = languageIds[i];

			leftList.add(new KeyValuePair(languageId, LocaleUtil.fromLanguageId(languageId).getDisplayName()));
		}

		// Right list

		List rightList = new ArrayList();

		Arrays.sort(languageIds);

		Iterator itr = availableLanguageIdsSet.iterator();

		while (itr.hasNext()) {
			String languageId = (String)itr.next();

			if (Arrays.binarySearch(languageIds, languageId) < 0) {
				rightList.add(new KeyValuePair(languageId, LocaleUtil.fromLanguageId(languageId).getDisplayName()));
			}
		}

		rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
		%>

		<liferay-ui:input-move-boxes
			formName="fm"
			leftTitle="current"
			rightTitle="available"
			leftBoxName="currentLanguageIds"
			rightBoxName="availableLanguageIds"
			leftReorder="true"
			leftList="<%= leftList %>"
			rightList="<%= rightList %>"
		/>
	</td>
</tr>
</table>

<br />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="display-style" />
	</td>
	<td>
		<select name="<portlet:namespace />displayStyle">
			<option <%= (displayStyle == LanguageTag.LIST_ICON) ? "selected" : "" %> value="<%= LanguageTag.LIST_ICON %>"><liferay-ui:message key="icon" /></option>
			<option <%= (displayStyle == LanguageTag.LIST_LONG_TEXT) ? "selected" : "" %> value="<%= LanguageTag.LIST_LONG_TEXT %>"><liferay-ui:message key="long-text" /></option>
			<option <%= (displayStyle == LanguageTag.LIST_SHORT_TEXT) ? "selected" : "" %> value="<%= LanguageTag.LIST_SHORT_TEXT %>"><liferay-ui:message key="short-text" /></option>
			<option <%= (displayStyle == LanguageTag.SELECT_BOX) ? "selected" : "" %> value="<%= LanguageTag.SELECT_BOX %>"><liferay-ui:message key="select-box" /></option>
		</select>
	</td>
</tr>
</table>

<br />

<input type="button" value="<liferay-ui:message key="save" />" onClick="document.<portlet:namespace />fm.<portlet:namespace />languageIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentLanguageIds); submitForm(document.<portlet:namespace />fm);" />

</form>