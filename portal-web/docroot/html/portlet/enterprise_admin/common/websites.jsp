<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
List<Website> websites = (List<Website>)request.getAttribute("common.websites");
%>

<script type="text/javascript">
	var websiteIdsArray = new Array();
	var lastIndex;
</script>

<h3><liferay-ui:message key="websites"/></h3>

<fieldset class="block-labels"  >

	<input id="<portlet:namespace />websiteIds" name="<portlet:namespace />websiteIds" type="hidden" />

	<%
	String fieldParam = null;

	for(int i=0;i<websites.size();i++){
		Website website = websites.get(i);
		String id = ""+ ((i < 10) ? "0" + i : i);
	%>

		<div class="form-row"  style="display:none;">
			<div class="row-fields">

				<%
				fieldParam = "websiteId" + id ;
				%>
				<input id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace /><%= fieldParam %>" type="hidden" value="<%= website.getWebsiteId() %>" />
				<script type="text/javascript">
					websiteIdsArray.push('<%=id%>');
					lastIndex = <%=i%>;
				</script>

				<%
				fieldParam = "url" + id ;
				%>
				<div class="ctrl-holder" >
					<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="url" /></label>
					<liferay-ui:input-field model="<%= Website.class %>" bean="<%= website %>" field="url" fieldParam="<%= fieldParam %>" />
				</div>

				<%
				fieldParam = "typeId" + id ;
				%>
				<div class="ctrl-holder"  >
					<label for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="type" /></label>
					<select name="<portlet:namespace /><%= fieldParam %>" id="<portlet:namespace /><%= fieldParam %>">
						<%
						List<ListType> websiteTypes = ListTypeServiceUtil.getListTypes(Contact.class.getName() + ListTypeImpl.WEBSITE);

						for (ListType suffix : websiteTypes) {
						%>
							<option <%= suffix.getListTypeId() == website.getTypeId() ? "selected" : "" %> value="<%= String.valueOf(suffix.getListTypeId()) %>"><%= LanguageUtil.get(pageContext, suffix.getName()) %></option>
						<%
						}
						%>
					</select>
				</div>

				<%
				fieldParam = "primary" + id ;
				%>
				<div class="ctrl-holder primary-ctrl">
					<label class="inline-label" for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="primary" /></label>

					<liferay-ui:input-field model="<%=Website.class %>" bean="<%= website %>" field="primary" fieldParam="<%=fieldParam%>" />
				</div>
			</div>
		</div>
	<%
	}
	%>
</fieldset>

<script type="text/javascript">
	websiteIdsArray = [];

	jQuery(
		function () {
			Liferay.Websites = new Liferay.autoFields2({
				container: '#websites > fieldset',
				baseRows: '.row-fields',
				itemsArray: websiteIdsArray
			});
		}
		);
</script>