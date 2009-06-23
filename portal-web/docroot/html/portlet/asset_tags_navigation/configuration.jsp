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

<%@ include file="/html/portlet/asset_tags_navigation/init.jsp" %>

<%
AssetEntryType[] assetEntryTypes = AssetEntryServiceUtil.getEntryTypes(themeDisplay.getLocale().toString());
%>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<table class="lfr-table">

<tr>
	<td>
		<liferay-ui:message key="show-asset-count" />
	</td>
	<td>
		<liferay-ui:input-checkbox param="showAssetCount" defaultValue="<%= showAssetCount %>" />
	</td>
</tr>
<tr id="<portlet:namespace />classNameId">
	<td>
		<liferay-ui:message key="asset-type" /> <liferay-ui:icon-help message="asset-type-asset-count-help" />
	</td>
	<td>
		<select id="<portlet:namespace />classNameId" name="<portlet:namespace />classNameId">
			<option <%= classNameId == 0 %> value="0"><liferay-ui:message key="any" /></option>

			<%
			for (AssetEntryType assetEntryType : assetEntryTypes) {
			%>

				<option <%= (classNameId == assetEntryType.getClassNameId()) ? "selected" : "" %> value="<%= assetEntryType.getClassNameId() %>"><liferay-ui:message key='<%= "model.resource." + assetEntryType.getClassName() %>' /></option>

			<%
			}
			%>

		</select>
	</td>
</tr>
<tr id="<portlet:namespace />displayStyle">
	<td>
		<liferay-ui:message key="display-style" />
	</td>
	<td>
		<select name="<portlet:namespace />displayStyle">
			<option <%= (displayStyle.equals("number")) ? "selected" : "" %> value="number"><liferay-ui:message key="number" /></option>
			<option <%= (displayStyle.equals("cloud")) ? "selected" : "" %> value="cloud"><liferay-ui:message key="cloud" /></option>
		</select>
	</td>
</tr>
<tr id="<portlet:namespace />showZeroAssetCount">
	<td>
		<liferay-ui:message key="show-tags-with-zero-assets" />
	</td>
	<td>
		<liferay-ui:input-checkbox param="showZeroAssetCount" defaultValue="<%= showZeroAssetCount %>" />
	</td>
</tr>
</table>

<br />

<input type="button" value="<liferay-ui:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);" />

</form>

<script type="text/javascript">
	jQuery(
		function() {
			var showAssetCount = jQuery('#<portlet:namespace />showAssetCountCheckbox');

			function showHiddenFields() {
				var classNameId = jQuery('#<portlet:namespace />classNameId');
				var displayStyle = jQuery('#<portlet:namespace />displayStyle');
				var showZeroAssetCount = jQuery('#<portlet:namespace />showZeroAssetCount');

				if (showAssetCount.is(':checked')) {
					classNameId.show();
					displayStyle.show();
					showZeroAssetCount.show();
				}
				else {
					classNameId.hide();
					displayStyle.hide();
					showZeroAssetCount.hide();
				}
			}

			showHiddenFields();

			showAssetCount.change(
				function(event) {
					showHiddenFields();
				}
			);
		}
	);
</script>