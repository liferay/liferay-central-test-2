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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
LdapContext ctx = PortalLDAPUtil.getContext(themeDisplay.getCompanyId());

if (ctx == null) {
%>

	<liferay-ui:message key="liferay-has-failed-to-connect-to-the-ldap-server" />

<%
	return;
}

NamingEnumeration enu = PortalLDAPUtil.getGroups(themeDisplay.getCompanyId(), ctx);

Properties groupMappings = PortalLDAPUtil.getGroupMappings(themeDisplay.getCompanyId());
%>

<liferay-ui:message key="test-ldap-groups" />

<br /><br />

A small list of groups has been displayed for your to review.

<br /><br />

<table class="liferay-table">

<%
int counter = 0;

while (enu.hasMore()) {
	SearchResult result = (SearchResult)enu.next();

	Attributes attrs = result.getAttributes();

	String name = LDAPUtil.getAttributeValue(attrs, groupMappings.getProperty("groupName")).toLowerCase();
	String description = LDAPUtil.getAttributeValue(attrs, groupMappings.getProperty("description"));
	Attribute attribute = attrs.get(groupMappings.getProperty("user"));

	if (counter == 0) {
%>

		<tr>
			<th>
				#
			</th>
			<th>
				Name
			</th>
			<th>
				Description
			</th>
			<th>
				Members
			</th>
		</tr>

<%
	}

	counter++;
%>

	<tr>
		<td>
			<%= counter %>
		</td>
		<td>
			<%= name %>
		</td>
		<td>
			<%= description %>
		</td>
		<td>
			<%= (attribute == null) ? "0" : attribute.size() %>
		</td>
	</tr>

<%
	if (counter == 20) {
		break;
	}
}

if (counter == 0) {
%>

	<tr>
		<td colspan="6">
			No Groups Found
		</td>
	</tr>

<%
}
%>

</table>