<%
/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%
String formName = namespace + request.getAttribute("liferay-ui:input-permissions:formName");

boolean addCommunityPermissions = ParamUtil.getBoolean(request, "addCommunityPermissions", true);
boolean addGuestPermissions = ParamUtil.getBoolean(request, "addGuestPermissions", true);
%>

<input name="<%= namespace %>addCommunityPermissions" type="hidden" value="<%= addCommunityPermissions %>">
<input name="<%= namespace %>addGuestPermissions" type="hidden" value="<%= addGuestPermissions %>">

<input <%= addCommunityPermissions ? "checked" : "" %> name="<%= namespace %>addCommunityPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= namespace %>addCommunityPermissions.value = this.checked; <%= namespace %>checkCommunityAndGuestPermissions();"> <%= LanguageUtil.get(pageContext, "assign-default-permissions-to-community") %><br>
<input <%= addGuestPermissions ? "checked" : "" %> name="<%= namespace %>addGuestPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= namespace %>addGuestPermissions.value = this.checked; <%= namespace %>checkCommunityAndGuestPermissions();"> <%= LanguageUtil.get(pageContext, "assign-default-permissions-to-guest") %><br>
<input <%= !addCommunityPermissions && !addGuestPermissions ? "checked" : "" %> name="<%= namespace %>addUserPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= namespace %>addCommunityPermissions.value = !this.checked; document.<%= formName %>.<%= namespace %>addGuestPermissions.value = !this.checked; <%= namespace %>checkUserPermissions();"> <%= LanguageUtil.get(pageContext, "only-assign-permissions-to-me") %>

<script type="text/javascript">
	function <%= namespace %>checkCommunityAndGuestPermissions() {
		if (document.<%= formName %>.<%= namespace %>addCommunityPermissionsBox.checked ||
			document.<%= formName %>.<%= namespace %>addGuestPermissionsBox.checked) {

			document.<%= formName %>.<%= namespace %>addUserPermissionsBox.checked = false;
		}
		else if (!document.<%= formName %>.<%= namespace %>addCommunityPermissionsBox.checked &&
				 !document.<%= formName %>.<%= namespace %>addGuestPermissionsBox.checked) {

			document.<%= formName %>.<%= namespace %>addUserPermissionsBox.checked = true;
		}
	}

	function <%= namespace %>checkUserPermissions() {
		if (document.<%= formName %>.<%= namespace %>addUserPermissionsBox.checked) {
			document.<%= formName %>.<%= namespace %>addCommunityPermissionsBox.checked = false;
			document.<%= formName %>.<%= namespace %>addGuestPermissionsBox.checked = false;
		}
		else {
			document.<%= formName %>.<%= namespace %>addCommunityPermissionsBox.checked = true;
			document.<%= formName %>.<%= namespace %>addGuestPermissionsBox.checked = true;
		}
	}
</script>