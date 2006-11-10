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
String modelName = (String) request.getAttribute("liferay-ui:input-permissions:modelName");
%>

<c:choose>
<c:when test="<%= modelName != null %>">

	<%
	String supportedActions = (String) request.getAttribute("liferay-ui:input-permissions:supportedActions");
	String communityDefaultActions = (String) request.getAttribute("liferay-ui:input-permissions:communityDefaultActions");
	String guestDefaultActions = (String) request.getAttribute("liferay-ui:input-permissions:guestDefaultActions");
	String guestUnsupportedActions = (String) request.getAttribute("liferay-ui:input-permissions:guestUnsupportedActions");

	String[] labels = StringUtil.split(supportedActions);

	StringBuffer sb = new StringBuffer(labels.length);

	for(int i = 0; i < labels.length; i++) {

		sb.append(LanguageUtil.get(pageContext, "action." + labels[i]));

		if(i < labels.length - 1) {
			sb.append(",");
		}
	}
	%>
	
	<script type="text/javascript">
		function <%= namespace %>contains(permArray, permStr) {
			for(j = 0; j < permArray.length; j++) {
				if(permArray[j] == permStr) {
					return true;
				}
			}
			return false;
		}

		function <%= namespace %>toggleAdvance(button) {
			$("<%= namespace %>others_perm").style.display = is_ie ? "block" : "table-row-group";
			button.disabled = true;
		}
	</script>

	<table cellpadding="3" cellspacing="3" border="0">
		<tr>
			<th><%= LanguageUtil.get(pageContext, "actions") %></th>
			<th><%= LanguageUtil.get(pageContext, "community") %></th>
			<th><%= LanguageUtil.get(pageContext, "guest") %></th>
		</tr>
		<tbody id="<%= namespace %>default_perm">
		</tbody>
		<tbody style="display: none;" id="<%= namespace %>others_perm">
		</tbody>
		<tbody>
			<tr>
				<td colspan="3" style="text-align: right;">
					<input class="portlet-form-button" type="button" value="<%= LanguageUtil.get(pageContext, "advance") %>" onClick="<%= namespace %>toggleAdvance(this);" />
				</td>
			</tr>
		</tbody>
	</table>

	<script type="text/javascript">
		var <%= namespace %>labels = '<%= sb.toString() %>'.split(",");
		var <%= namespace %>supportedActions = '<%= supportedActions %>'.split(",");
		var <%= namespace %>communityDefaultActions = '<%= communityDefaultActions %>'.split(",");
		var <%= namespace %>guestDefaultActions = '<%= guestDefaultActions %>'.split(",");
		var <%= namespace %>guestUnsupportedActions = '<%= guestUnsupportedActions %>'.split(",");

		for(i = 0; i < <%= namespace %>supportedActions.length; i++) {
			newTR = document.createElement("tr");

			if(<%= namespace %>contains(<%= namespace %>communityDefaultActions, <%= namespace %>supportedActions[i])) {
				$("<%= namespace %>default_perm").appendChild(newTR);
			}
			else {
				$("<%= namespace %>others_perm").appendChild(newTR);
			}

			newTD = document.createElement("td");
			newTD.align = "right";
			newTD.className = "portlet-form-label";
			newTD.innerHTML = <%= namespace %>labels[i];
			newTR.appendChild(newTD);

			newTD = document.createElement("td");
			newTD.align = "center";
			newTR.appendChild(newTD);

			newINPUT = document.createElement("input");
			newINPUT.type = 'checkbox';
			newINPUT.name = '<%= namespace %>communityPermissions';
			newINPUT.value = <%= namespace %>supportedActions[i];
			newTD.appendChild(newINPUT);
			if(<%= namespace %>contains(<%= namespace %>communityDefaultActions, <%= namespace %>supportedActions[i])) {
				newINPUT.checked = true;
			}

			newTD = document.createElement("td");
			newTD.align = "center";
			newTR.appendChild(newTD);

			newINPUT = document.createElement("input");
			newINPUT.type = 'checkbox';
			newINPUT.name = '<%= namespace %>guestPermissions';
			newINPUT.value = <%= namespace %>supportedActions[i];
			newTD.appendChild(newINPUT);
			if(<%= namespace %>contains(<%= namespace %>guestDefaultActions, <%= namespace %>supportedActions[i])) {
				newINPUT.checked = true;
			}
			if(<%= namespace %>contains(<%= namespace %>guestUnsupportedActions, <%= namespace %>supportedActions[i])) {
				newINPUT.disabled = true;
			}
		}
	</script>

</c:when>
<c:otherwise>

	<%
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

</c:otherwise>
</c:choose>