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

<%@ include file="/html/portlet/address_book/init.jsp" %>

<%
String listId = request.getParameter("list_id");

ABList list = null;

if (Validator.isNotNull(listId)) {
	list = ABListServiceUtil.getList(listId);
}

String name = request.getParameter("list_name");
if ((name == null) || (name.equals(StringPool.NULL))) {
	name = "";

	if (list != null) {
		name = list.getName();
	}
}

// Available contacts

List contacts = ABContactServiceUtil.getContacts();

// Current contacts

String[] selContactIds = StringUtil.split(ParamUtil.getString(request, "contact_ids"));
String selContactIdsParam = request.getParameter("contact_ids");
if (selContactIdsParam == null) {
	if (list != null) {
		List listContacts = ABListServiceUtil.getContacts(listId);

		selContactIds = new String[listContacts.size()];

		for (int i = 0; i < listContacts.size(); i++) {
			ABContact contact = (ABContact)listContacts.get(i);

			selContactIds[i] = contact.getContactId();
		}
	}
	else {
		selContactIds = new String[0];
	}
}
Map selContacts = new LinkedHashMap();
for (int i = 0; i < selContactIds.length; i++) {
	selContacts.put(selContactIds[i], ABContactServiceUtil.getContact(selContactIds[i]));
}
%>

<script type="text/javascript">
	var <portlet:namespace />contactIdToEmailAddress = new Array();

	<%
	for (int i = 0; i < contacts.size(); i++) {
		ABContact contact = (ABContact)contacts.get(i);
	%>

		<portlet:namespace />contactIdToEmailAddress["<%= contact.getContactId() %>"] = "<%= contact.getEmailAddress() %>";

	<%
	}
	%>

	function <portlet:namespace />saveList() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= list == null ? Constants.ADD : Constants.UPDATE %>";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL><portlet:param name="struts_action" value="/address_book/view_all" /></portlet:renderURL>";
		document.<portlet:namespace />fm.<portlet:namespace />contact_ids.value = listSelect(document.<portlet:namespace />fm.<portlet:namespace />sel_contacts2);
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/address_book/edit_list" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveList(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="">
<input name="<portlet:namespace />list_id" type="hidden" value="<%= listId %>">
<input name="<portlet:namespace />contact_ids" type="hidden" value="">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_br_wrap_content" value="false" />

	<c:if test="<%= list == null %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />saveList();">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save-and-add-another") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.ADD %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/address_book/edit_list" /></portlet:actionURL>'; document.<portlet:namespace />fm.<portlet:namespace />contact_ids.value = listSelect(document.<portlet:namespace />fm.<portlet:namespace />sel_contacts2); submitForm(document.<portlet:namespace />fm);">
	</c:if>

	<c:if test="<%= list != null %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update") %>' onClick="<portlet:namespace />saveList();">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this-list") %>')) { document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/address_book/view_all" /></portlet:renderURL>'; submitForm(document.<portlet:namespace />fm); }">
	</c:if>

	<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<portlet:renderURL><portlet:param name="struts_action" value="/address_book/view_all" /></portlet:renderURL>';">
</liferay-ui:box>

<br>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= (list == null) ? LanguageUtil.get(pageContext, "add-list") : LanguageUtil.get(pageContext, "edit-list") %>' />

	<table border="0" cellpadding="0" cellspacing="0">

	<c:if test="<%= SessionErrors.contains(renderRequest, ListNameException.class.getName()) %>">
		<tr>
			<td colspan="3">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-list-name") %></font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td>
			<font class="portlet-font" style="font-size: small;"><%= LanguageUtil.get(pageContext, "list-name") %></font>
		</td>
		<td width="10">
			&nbsp;
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />list_name" type="text" value="<%= name %>">
		</td>
	</tr>
	</table>

	<br>

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>

			<%
			List leftKVPs = new ArrayList();

			Iterator itr = selContacts.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry entry = (Map.Entry)itr.next();

				ABContact contact = (ABContact)entry.getValue();

				leftKVPs.add(new KeyValuePair(contact.getContactId(), contact.getFullName()));
			}

			request.setAttribute(WebKeys.MOVE_BOXES_LEFT_LIST, leftKVPs);

			List rightKVPs = new ArrayList();

			itr = contacts.iterator();

			while (itr.hasNext()) {
				ABContact contact = (ABContact)itr.next();

				if (list == null || !selContacts.containsKey(contact.getContactId())) {
					rightKVPs.add(new KeyValuePair(contact.getContactId(), contact.getFullName()));
				}
			}

			request.setAttribute(WebKeys.MOVE_BOXES_RIGHT_LIST, rightKVPs);
			%>

			<liferay-util:include page="/html/common/move_boxes.jsp">
				<liferay-util:param name="form_name" value='<%= renderResponse.getNamespace() + "fm" %>' />
				<liferay-util:param name="left_title" value='<%= LanguageUtil.get(pageContext, "current") %>' />
				<liferay-util:param name="right_title" value='<%= LanguageUtil.get(pageContext, "available") %>' />
				<liferay-util:param name="left_box_name" value='<%= renderResponse.getNamespace() + "sel_contacts2" %>' />
				<liferay-util:param name="right_box_name" value='<%= renderResponse.getNamespace() + "sel_contacts" %>' />
				<liferay-util:param name="left_on_change" value='<%= "document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "contact_detail.value = this[this.selectedIndex].text + \' &#60;\' + " + renderResponse.getNamespace() + "contactIdToEmailAddress[this[this.selectedIndex].value] + \'&#62;\';" %>' />
				<liferay-util:param name="right_on_change" value='<%= "document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "contact_detail.value = this[this.selectedIndex].text + \' &#60;\' + " + renderResponse.getNamespace() + "contactIdToEmailAddress[this[this.selectedIndex].value] + \'&#62;\';" %>' />
			</liferay-util:include>
		</td>
	</tr>
	<tr>
		<td>
			<br>

			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "selected-contact") %></b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<input class="form-text" name="<portlet:namespace />contact_detail" size="55" type="text">
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</liferay-ui:box>

<br>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_br_wrap_content" value="false" />

	<c:if test="<%= list == null %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />saveList();">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save-and-add-another") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.ADD %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/address_book/edit_list" /></portlet:actionURL>'; document.<portlet:namespace />fm.<portlet:namespace />contact_ids.value = listSelect(document.<portlet:namespace />fm.<portlet:namespace />sel_contacts2); submitForm(document.<portlet:namespace />fm);">
	</c:if>

	<c:if test="<%= list != null %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update") %>' onClick="<portlet:namespace />saveList();">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this-list") %>')) { document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/address_book/view_all" /></portlet:renderURL>'; submitForm(document.<portlet:namespace />fm); }">
	</c:if>

	<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<portlet:renderURL><portlet:param name="struts_action" value="/address_book/view_all" /></portlet:renderURL>';">
</liferay-ui:box>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />list_name.focus();
</script>