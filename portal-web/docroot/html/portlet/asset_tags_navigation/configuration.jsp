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
String redirect = ParamUtil.getString(request, "redirect");

List<AssetRendererFactory> assetRendererFactories = AssetRendererFactoryRegistryUtil.getAssetRendererFactories();
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" >
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:fieldset>
		<aui:input inlineLabel="left" name="showAssetCount" type="checkbox" value="<%= showAssetCount %>" />

		<div class="aui-helper-hidden" id="<portlet:namespace />assetCountOptions">
			<aui:select helpMessage="asset-type-asset-count-help" label="asset-type" name="classNameId">
				<aui:option label="any" value="<%= classNameId == 0 %>" />

				<%
				for (AssetRendererFactory assetRendererFactory : assetRendererFactories) {
				%>

					<aui:option label='<%= "model.resource." + assetRendererFactory.getClassName() %>' selected="<%= classNameId == assetRendererFactory.getClassNameId() %>" value="<%= assetRendererFactory.getClassNameId() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:select name="displayStyle">
				<aui:option label="number" selected='<%= displayStyle.equals("number") %>' />
				<aui:option label="cloud" selected='<%= displayStyle.equals("cloud") %>' />
			</aui:select>

			<aui:input inlineLabel="left" label="show-tags-with-zero-assets" name="showZeroAssetCount" type="checkbox" value="<%= showZeroAssetCount %>" />
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button name="saveButton" type="submit" value="save" />

		<aui:button name="cancelButton" onClick="<%= redirect %>" type="button" value="cancel" />
	</aui:button-row>
</aui:form>

<script type="text/javascript">
	AUI().ready(
		function(A) {
			var showAssetCount = A.one('#<portlet:namespace />showAssetCountCheckbox');

			function showHiddenFields() {
				var assetCountOptions = A.one('#<portlet:namespace />assetCountOptions');

				if (showAssetCount && assetCountOptions) {
					if (showAssetCount.get('checked')) {
						assetCountOptions.show();
					}
					else {
						assetCountOptions.hide();
					}
				}
			}

			showHiddenFields();

			showAssetCount.on('change', showHiddenFields);
		}
	);
</script>