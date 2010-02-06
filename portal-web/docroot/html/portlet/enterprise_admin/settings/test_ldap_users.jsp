<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
String baseProviderURL = ParamUtil.getString(request, "baseProviderURL");
String baseDN = ParamUtil.getString(request, "baseDN");
String principal = ParamUtil.getString(request, "principal");
String credentials = ParamUtil.getString(request, "credentials");

LdapContext ldapContext = PortalLDAPUtil.getContext(themeDisplay.getCompanyId(), baseProviderURL, principal, credentials);

if (ldapContext == null) {
%>

	<liferay-ui:message key="liferay-has-failed-to-connect-to-the-ldap-server" />

<%
	return;
}

if (Validator.isNull(ParamUtil.getString(request, "userMappingScreenName")) ||
	Validator.isNull(ParamUtil.getString(request, "userMappingPassword")) ||
	Validator.isNull(ParamUtil.getString(request, "userMappingEmailAddress")) ||
	Validator.isNull(ParamUtil.getString(request, "userMappingFirstName")) ||
	Validator.isNull(ParamUtil.getString(request, "userMappingLastName"))) {
%>

	<liferay-ui:message key="please-map-each-of-the-user-properties-screen-name,-password,-email-address,-first-name,-and-last-name-to-an-ldap-attribute" />

<%
	return;
}

String userFilter = ParamUtil.getString(request, "importUserSearchFilter");

List<SearchResult> results = PortalLDAPUtil.getUsers(themeDisplay.getCompanyId(), ldapContext, 20, baseDN, userFilter);

String userMappingsParams =
	"screenName=" + ParamUtil.getString(request, "userMappingScreenName") +
	"\npassword=" + ParamUtil.getString(request, "userMappingPassword") +
	"\nemailAddress=" + ParamUtil.getString(request, "userMappingEmailAddress") +
	"\nfullName=" + ParamUtil.getString(request, "userMappingFullName") +
	"\nfirstName=" + ParamUtil.getString(request, "userMappingFirstName") +
	"\nlastName=" + ParamUtil.getString(request, "userMappingLastName") +
	"\njobTitle=" + ParamUtil.getString(request, "userMappingJobTitle") +
	"\ngroup=" + ParamUtil.getString(request, "userMappingGroup");

Properties userMappings = PropertiesUtil.load(userMappingsParams);
%>

<liferay-ui:message key="test-ldap-users" />

<br /><br />

<liferay-ui:message key="a-subset-of-users-has-been-displayed-for-you-to-review" />

<br /><br />

<table class="lfr-table">

<%
boolean showMissingAttributeMessage = false;

int counter = 0;

for (SearchResult result : results) {
	Attributes attrs = result.getAttributes();

	String screenName = LDAPUtil.getAttributeValue(attrs, userMappings.getProperty("screenName")).toLowerCase();
	String password = LDAPUtil.getAttributeValue(attrs, userMappings.getProperty("password")).toLowerCase();
	String emailAddress = LDAPUtil.getAttributeValue(attrs, userMappings.getProperty("emailAddress"));
	String firstName = LDAPUtil.getAttributeValue(attrs, userMappings.getProperty("firstName"));
	String lastName = LDAPUtil.getAttributeValue(attrs, userMappings.getProperty("lastName"));
	String jobTitle = LDAPUtil.getAttributeValue(attrs, userMappings.getProperty("jobTitle"));
	Attribute attribute = attrs.get(userMappings.getProperty("group"));

	if (Validator.isNull(screenName) || Validator.isNull(password) || Validator.isNull(emailAddress) || Validator.isNull(firstName) || Validator.isNull(lastName)) {
		showMissingAttributeMessage = true;
	}

	if (counter == 0) {
%>

		<tr>
			<th>
				#
			</th>
			<th>
				<liferay-ui:message key="screen-name" />
			</th>
			<th>
				<liferay-ui:message key="email-address" />
			</th>
			<th>
				<liferay-ui:message key="first-name" />
			</th>
			<th>
				<liferay-ui:message key="last-name" />
			</th>
			<th>
				<liferay-ui:message key="password" />
			</th>
			<th>
				<liferay-ui:message key="job-title" />
			</th>
			<th>
				<liferay-ui:message key="group" />
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
			<%= screenName %>
		</td>
		<td>
			<%= emailAddress %>
		</td>
		<td>
			<%= firstName %>
		</td>
		<td>
			<%= lastName %>
		</td>
		<td>
			<c:if test="<%= Validator.isNotNull(password) %>">
				********
			</c:if>
		</td>
		<td>
			<%= jobTitle %>
		</td>
		<td>
			<%= (attribute == null) ? "0" : String.valueOf(attribute.size()) %>
		</td>
	</tr>

<%
}

if (counter == 0) {
%>

	<tr>
		<td colspan="7">
			<liferay-ui:message key="no-users-were-found" />
		</td>
	</tr>

<%
}
%>

</table>

<%
if (showMissingAttributeMessage) {
%>

	<div class="portlet-msg-info">
		<liferay-ui:message key="the-above-results-include-users-which-are-missing-the-required-attributes-(screen-name,-password,-email-address,-first-name,-and-last-name).-these-users-will-not-be-imported-until-these-attributes-are-filled-in" />
	</div>

<%
}

if (ldapContext != null) {
	ldapContext.close();
}
%>