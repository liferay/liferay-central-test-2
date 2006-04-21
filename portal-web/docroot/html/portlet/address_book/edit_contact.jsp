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
String contactId = request.getParameter("contact_id");

ABContact contact = null;

if (Validator.isNotNull(contactId)) {
	contact = ABContactServiceUtil.getContact(contactId);
}

String firstName = request.getParameter("contact_first_name");
if ((firstName == null) || (firstName.equals(StringPool.NULL))) {
	firstName = "";

	if (contact != null) {
		firstName = contact.getFirstName();
	}
}

String middleName = request.getParameter("contact_middle_name");
if ((middleName == null) || (middleName.equals(StringPool.NULL))) {
	middleName = "";

	if (contact != null) {
		middleName = contact.getMiddleName();
	}
}

String lastName = request.getParameter("contact_last_name");
if ((lastName == null) || (lastName.equals(StringPool.NULL))) {
	lastName = "";

	if (contact != null) {
		lastName = contact.getLastName();
	}
}

String nickName = request.getParameter("contact_nick_name");
if ((nickName == null) || (nickName.equals(StringPool.NULL))) {
	nickName = "";

	if (contact != null) {
		nickName = contact.getNickName();
	}
}

String emailAddress = request.getParameter("contact_email_address");
if ((emailAddress == null) || (emailAddress.equals(StringPool.NULL))) {
	emailAddress = "";

	if (contact != null) {
		emailAddress = contact.getEmailAddress();
	}
}

String hStreet = request.getParameter("contact_home_street");
if ((hStreet == null) || (hStreet.equals(StringPool.NULL))) {
	hStreet = "";

	if (contact != null) {
		hStreet = contact.getHomeStreet();
	}
}

String hCity = request.getParameter("contact_home_city");
if ((hCity == null) || (hCity.equals(StringPool.NULL))) {
	hCity = "";

	if (contact != null) {
		hCity = contact.getHomeCity();
	}
}

String hState = request.getParameter("contact_home_state");
if ((hState == null) || (hState.equals(StringPool.NULL))) {
	hState = "";

	if (contact != null) {
		hState = contact.getHomeState();
	}
}

String hZip = request.getParameter("contact_home_zip");
if ((hZip == null) || (hZip.equals(StringPool.NULL))) {
	hZip = "";

	if (contact != null) {
		hZip = contact.getHomeZip();
	}
}

String hCountry = request.getParameter("contact_home_country");
if ((hCountry == null) || (hCountry.equals(StringPool.NULL))) {
	hCountry = "";

	if (contact != null) {
		hCountry = contact.getHomeCountry();
	}
}

String hPhone = request.getParameter("contact_home_phone");
if ((hPhone == null) || (hPhone.equals(StringPool.NULL))) {
	hPhone = "";

	if (contact != null) {
		hPhone = contact.getHomePhone();
	}
}

String hFax = request.getParameter("contact_home_fax");
if ((hFax == null) || (hFax.equals(StringPool.NULL))) {
	hFax = "";

	if (contact != null) {
		hFax = contact.getHomeFax();
	}
}

String hCell = request.getParameter("contact_home_cell");
if ((hCell == null) || (hCell.equals(StringPool.NULL))) {
	hCell = "";

	if (contact != null) {
		hCell = contact.getHomeCell();
	}
}

String hPager = request.getParameter("contact_home_pager");
if ((hPager == null) || (hPager.equals(StringPool.NULL))) {
	hPager = "";

	if (contact != null) {
		hPager = contact.getHomePager();
	}
}

String hTollFree = request.getParameter("contact_home_toll_free");
if ((hTollFree == null) || (hTollFree.equals(StringPool.NULL))) {
	hTollFree = "";

	if (contact != null) {
		hTollFree = contact.getHomeTollFree();
	}
}

String hEmailAddress = request.getParameter("contact_home_email_address");
if ((hEmailAddress == null) || (hEmailAddress.equals(StringPool.NULL))) {
	hEmailAddress = "";

	if (contact != null) {
		hEmailAddress = contact.getHomeEmailAddress();
	}
}

String bCompany = request.getParameter("contact_biz_company");
if ((bCompany == null) || (bCompany.equals(StringPool.NULL))) {
	bCompany = "";

	if (contact != null) {
		bCompany = contact.getBusinessCompany();
	}
}

String bStreet = request.getParameter("contact_biz_street");
if ((bStreet == null) || (bStreet.equals(StringPool.NULL))) {
	bStreet = "";

	if (contact != null) {
		bStreet = contact.getBusinessStreet();
	}
}

String bCity = request.getParameter("contact_biz_city");
if ((bCity == null) || (bCity.equals(StringPool.NULL))) {
	bCity = "";

	if (contact != null) {
		bCity = contact.getBusinessCity();
	}
}

String bState = request.getParameter("contact_biz_state");
if ((bState == null) || (bState.equals(StringPool.NULL))) {
	bState = "";

	if (contact != null) {
		bState = contact.getBusinessState();
	}
}

String bZip = request.getParameter("contact_biz_zip");
if ((bZip == null) || (bZip.equals(StringPool.NULL))) {
	bZip = "";

	if (contact != null) {
		bZip = contact.getBusinessZip();
	}
}

String bCountry = request.getParameter("contact_biz_country");
if ((bCountry == null) || (bCountry.equals(StringPool.NULL))) {
	bCountry = "";

	if (contact != null) {
		bCountry = contact.getBusinessCountry();
	}
}

String bPhone = request.getParameter("contact_biz_phone");
if ((bPhone == null) || (bPhone.equals(StringPool.NULL))) {
	bPhone = "";

	if (contact != null) {
		bPhone = contact.getBusinessPhone();
	}
}

String bFax = request.getParameter("contact_biz_fax");
if ((bFax == null) || (bFax.equals(StringPool.NULL))) {
	bFax = "";

	if (contact != null) {
		bFax = contact.getBusinessFax();
	}
}

String bCell = request.getParameter("contact_biz_cell");
if ((bCell == null) || (bCell.equals(StringPool.NULL))) {
	bCell = "";

	if (contact != null) {
		bCell = contact.getBusinessCell();
	}
}

String bPager = request.getParameter("contact_biz_pager");
if ((bPager == null) || (bPager.equals(StringPool.NULL))) {
	bPager = "";

	if (contact != null) {
		bPager = contact.getBusinessPager();
	}
}

String bTollFree = request.getParameter("contact_biz_toll_free");
if ((bTollFree == null) || (bTollFree.equals(StringPool.NULL))) {
	bTollFree = "";

	if (contact != null) {
		bTollFree = contact.getBusinessTollFree();
	}
}

String bEmailAddress = request.getParameter("contact_biz_email_address");
if ((bEmailAddress == null) || (bEmailAddress.equals(StringPool.NULL))) {
	bEmailAddress = "";

	if (contact != null) {
		bEmailAddress = contact.getBusinessEmailAddress();
	}
}

String employeeNumber = request.getParameter("contact_employee_number");
if ((employeeNumber == null) || (employeeNumber.equals(StringPool.NULL))) {
	employeeNumber = "";

	if (contact != null) {
		employeeNumber = contact.getEmployeeNumber();
	}
}

String jobTitle = request.getParameter("contact_job_title");
if ((jobTitle == null) || (jobTitle.equals(StringPool.NULL))) {
	jobTitle = "";

	if (contact != null) {
		jobTitle = contact.getJobTitle();
	}
}

String jobClass = request.getParameter("contact_job_class");
if ((jobClass == null) || (jobClass.equals(StringPool.NULL))) {
	jobClass = "";

	if (contact != null) {
		jobClass = GetterUtil.getString(contact.getJobClass());
	}
}

String hoursOfOperation = request.getParameter("contact_hop");
if ((hoursOfOperation == null) || (hoursOfOperation.equals(StringPool.NULL))) {
	hoursOfOperation = "";

	if (contact != null) {
		hoursOfOperation = contact.getHoursOfOperation();
	}
}

Calendar birthday = null;

if (contact != null) {
	if (contact.getBirthday() != null) {
		birthday = new GregorianCalendar();
		birthday.setTime(contact.getBirthday());
	}
}

String bdMonth = request.getParameter("contact_birthday_month");
if ((bdMonth == null) || (bdMonth.equals(StringPool.NULL))) {
	bdMonth = "";

	if (birthday != null) {
		bdMonth = Integer.toString(birthday.get(Calendar.MONTH));
	}
}

String bdDay = request.getParameter("contact_birthday_day");
if ((bdDay == null) || (bdDay.equals(StringPool.NULL))) {
	bdDay = "";

	if (birthday != null) {
		bdDay = Integer.toString(birthday.get(Calendar.DATE));
	}
}

String bdYear = request.getParameter("contact_birthday_year");
if ((bdYear == null) || (bdYear.equals(StringPool.NULL))) {
	bdYear = "";

	if (birthday != null) {
		bdYear = Integer.toString(birthday.get(Calendar.YEAR));
	}
}

String timeZoneId = request.getParameter("contact_time_zone_id");
if ((timeZoneId == null) || (timeZoneId.equals(StringPool.NULL))) {
	timeZoneId = timeZone.getID();

	if (contact != null) {
		timeZoneId = GetterUtil.getString(contact.getTimeZoneId());
	}
}

String instantMessenger = request.getParameter("contact_im");
if ((instantMessenger == null) || (instantMessenger.equals(StringPool.NULL))) {
	instantMessenger = "";

	if (contact != null) {
		instantMessenger = contact.getInstantMessenger();
	}
}

String website = request.getParameter("contact_website");
if ((website == null) || (website.equals(StringPool.NULL))) {
	website = "";

	if (contact != null) {
		website = contact.getWebsite();
	}
}

String comments = request.getParameter("contact_comments");
if ((comments == null) || (comments.equals(StringPool.NULL))) {
	comments = "";

	if (contact != null) {
		comments = contact.getComments();
	}
}

String mailRedirect = request.getParameter("mail_redirect");
if (Validator.isNull(mailRedirect)) {
	mailRedirect = "";
}
%>

<script type="text/javascript">
	function <portlet:namespace />getContactHoursOfOperation() {
		var xsd = "<hours-of-operation>";

		for (var i = 0; i < 7; i++) {
			eval("var fromHour = document.<portlet:namespace />fm.<portlet:namespace />contact_hop_" + i + "_from_hour.value;");
			eval("var fromMinute = document.<portlet:namespace />fm.<portlet:namespace />contact_hop_" + i + "_from_minute.value;");
			eval("var fromAmPm = document.<portlet:namespace />fm.<portlet:namespace />contact_hop_" + i + "_from_am_pm.value;");

			eval("var toHour = document.<portlet:namespace />fm.<portlet:namespace />contact_hop_" + i + "_to_hour.value;");
			eval("var toMinute = document.<portlet:namespace />fm.<portlet:namespace />contact_hop_" + i + "_to_minute.value;");
			eval("var toAmPm = document.<portlet:namespace />fm.<portlet:namespace />contact_hop_" + i + "_to_am_pm.value;");

			eval("var available = document.<portlet:namespace />fm.<portlet:namespace />contact_hop_" + i + "_available.value;");

			xsd += "<day-" + i + " ";
			xsd += "from-hour='" + fromHour + "' ";
			xsd += "from-minute='" + fromMinute + "' ";
			xsd += "from-am-pm='" + fromAmPm + "' ";
			xsd += "to-hour='" + toHour + "' ";
			xsd += "to-minute='" + toMinute + "' ";
			xsd += "to-am-pm='" + toAmPm + "' ";
			xsd += "available='" + available + "' ";
			xsd += "/>";
		}

		xsd  += "</hours-of-operation>";

		return xsd;
	}

	function <portlet:namespace />saveContact() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= contact == null ? Constants.ADD : Constants.UPDATE %>";

		<c:if test="<%= Validator.isNull(mailRedirect) %>">
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL><portlet:param name="struts_action" value="/address_book/view_all" /></portlet:renderURL>";
		</c:if>

		<c:if test="<%= Validator.isNotNull(mailRedirect) %>">
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<%= mailRedirect %>";
		</c:if>

		document.<portlet:namespace />fm.<portlet:namespace />contact_hop.value = <portlet:namespace />getContactHoursOfOperation();
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/address_book/edit_contact" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveContact(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="">
<input name="<portlet:namespace />mail_redirect" type="hidden" value="<%= mailRedirect %>">
<input name="<portlet:namespace />contact_id" type="hidden" value="<%= contactId %>">
<input name="<portlet:namespace />contact_hop" type="hidden" value="">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_br_wrap_content" value="false" />

	<c:if test="<%= contact == null %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />saveContact();">

		<input class="portlet-form-button" type="button" value="<%= LanguageUtil.get(pageContext, "save-and-add-another") %>"
			onClick="
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.ADD %>';

				<c:if test="<%= Validator.isNull(mailRedirect) %>">
					document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/address_book/edit_contact" /></portlet:actionURL>';
				</c:if>

				<c:if test="<%= Validator.isNotNull(mailRedirect) %>">
					document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/address_book/edit_contact" /><portlet:param name="mail_redirect" value="<%= mailRedirect %>" /></portlet:actionURL>';
				</c:if>

				document.<portlet:namespace />fm.<portlet:namespace />contact_hop.value = <portlet:namespace />getContactHoursOfOperation();
				submitForm(document.<portlet:namespace />fm);"
		>
	</c:if>

	<c:if test="<%= contact != null %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update") %>' onClick="<portlet:namespace />saveContact();">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this-contact") %>')) { document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/address_book/view_all" /></portlet:renderURL>'; submitForm(document.<portlet:namespace />fm); }">
	</c:if>

	<input class="portlet-form-button" type="button" value="<%= LanguageUtil.get(pageContext, "cancel") %>"
		onClick="
			<c:if test="<%= Validator.isNull(mailRedirect) %>">
				self.location = '<portlet:renderURL><portlet:param name="struts_action" value="/address_book/view_all" /></portlet:renderURL>';
			</c:if>

			<c:if test="<%= Validator.isNotNull(mailRedirect) %>">
				self.location = '<%= mailRedirect %>';
			</c:if>"
	>
</liferay-ui:box>

<br>

<c:if test="<%= !SessionErrors.isEmpty(renderRequest) %>">
	<table border="0" cellpadding="0" cellspacing="0" width="95%">
	<tr>
		<td>
			<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "you-have-entered-invalid-data") %></font>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "basic") %>' />

	<table border="0" cellpadding="4" cellspacing="0" width="100%">

	<c:if test="<%= SessionErrors.contains(renderRequest, ContactFirstNameException.class.getName()) || SessionErrors.contains(renderRequest, ContactLastNameException.class.getName()) || SessionErrors.contains(renderRequest, ContactEmailAddressException.class.getName()) || SessionErrors.contains(renderRequest, DuplicateContactException.class.getName()) %>">
		<tr>
			<td colspan="4">
				<font class="portlet-msg-error" style="font-size: xx-small;">

				<c:if test="<%= SessionErrors.contains(renderRequest, ContactFirstNameException.class.getName()) %>">
					<%= LanguageUtil.get(pageContext, "please-enter-a-valid-first-name") %><br>
				</c:if>

				<c:if test="<%= SessionErrors.contains(renderRequest, ContactLastNameException.class.getName()) %>">
					<%= LanguageUtil.get(pageContext, "please-enter-a-valid-last-name") %><br>
				</c:if>

				<c:if test="<%= SessionErrors.contains(renderRequest, ContactEmailAddressException.class.getName()) %>">
					<%= LanguageUtil.get(pageContext, "please-enter-a-valid-email-address") %><br>
				</c:if>

				<c:if test="<%= SessionErrors.contains(renderRequest, DuplicateContactException.class.getName()) %>">
					<%= LanguageUtil.get(pageContext, "the-combination-of-first-name-last-name-and-email-address-is-already-taken") %><br>
				</c:if>

				</font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "first-name") %></b></font> <font class="portlet-msg-error" style="font-size: xx-small;">*</font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_first_name" size="25" type="text" value="<%= firstName %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "middle-name") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_middle_name" size="25" type="text" value="<%= middleName %>">
		</td>
	</tr>
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "last-name") %></b></font> <font class="portlet-msg-error" style="font-size: xx-small;">*</font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_last_name" size="25" type="text" value="<%= lastName %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "nickname") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_nick_name" size="25" type="text" value="<%= nickName %>">
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<br><div class="beta-separator"></div><br>
		</td>
	</tr>
	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "primary-email") %></b></font> <font class="portlet-msg-error" style="font-size: xx-small;">*</font>
		</td>
		<td colspan="3">
			<input class="form-text" name="<portlet:namespace />contact_email_address" size="25" type="text" value="<%= emailAddress %>">
		</td>
	</tr>
	</table>
</liferay-ui:box>

<br>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "home-address") %>' />

	<table border="0" cellpadding="4" cellspacing="0" width="100%">
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "street") %></b></font>
		</td>
		<td colspan="5">
			<input class="form-text" name="<portlet:namespace />contact_home_street" size="40" type="text" value="<%= hStreet %>">
		</td>
	</tr>
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "city") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_home_city" size="20" type="text" value="<%= hCity  %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "state") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_home_state" size="20" type="text" value="<%= hState %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "zip") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_home_zip" size="20" type="text" value="<%= hZip %>">
		</td>
	</tr>
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "country") %></b></font>
		</td>
		<td colspan="5">
			<input class="form-text" name="<portlet:namespace />contact_home_country" size="20" type="text" value="<%= hCountry %>">
		</td>
	</tr>
	<tr>
		<td colspan="6">
			<br><div class="beta-separator"></div><br>
		</td>
	</tr>
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "phone") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_home_phone" size="20" type="text" value="<%= hPhone %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "fax") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_home_fax" size="20" type="text" value="<%= hFax %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "cell") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_home_cell" size="20" type="text" value="<%= hCell %>">
		</td>
	</tr>
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "pager") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_home_pager" size="20" type="text" value="<%= hPager %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "toll-free") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_home_toll_free" size="20" type="text" value="<%= hTollFree %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "email") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_home_email_address" size="20" type="text" value="<%= hEmailAddress %>">
		</td>
	</tr>
	</table>
</liferay-ui:box>

<br>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "business-address") %>' />

	<table border="0" cellpadding="4" cellspacing="0" width="100%">
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "company") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_biz_company" size="20" type="text" value="<%= bCompany %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "employee-id") %></b></font>
		</td>
		<td colspan="3">
			<input class="form-text" name="<portlet:namespace />contact_employee_number" size="20" type="text" value="<%= employeeNumber %>">
		</td>
	</tr>
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "job-title") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_job_title" size="20" type="text" value="<%= jobTitle %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "job-class") %></b></font>
		</td>
		<td colspan="3">
			<select name="<portlet:namespace />contact_job_class">

				<%
				for (int i = 0; i < ABContact.JOB_CLASSES.length; i++) {
				%>

					<option <%= jobClass.equals(ABContact.JOB_CLASSES[i]) ? "selected" : "" %> value="<%= ABContact.JOB_CLASSES[i] %>"><%= LanguageUtil.get(pageContext, ABContact.JOB_CLASSES[i]) %></option>

				<%
				}
				%>

			</select>
		</td>
	</tr>
	<tr>
		<td colspan="6">
			<br><div class="beta-separator"></div><br>
		</td>
	</tr>
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "street") %></b></font>
		</td>
		<td colspan="5">
			<input class="form-text" name="<portlet:namespace />contact_biz_street" size="40" type="text" value="<%= bStreet %>">
		</td>
	</tr>
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "city") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_biz_city" size="20" type="text" value="<%= bCity %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "state") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_biz_state" size="20" type="text" value="<%= bState %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "zip") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_biz_zip" size="20" type="text" value="<%= bZip %>">
		</td>
	</tr>
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "country") %></b></font>
		</td>
		<td colspan="5">
			<input class="form-text" name="<portlet:namespace />contact_biz_country" size="20" type="text" value="<%= bCountry %>">
		</td>
	</tr>
	<tr>
		<td colspan="6">
			<br><div class="beta-separator"></div><br>
		</td>
	</tr>
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "phone") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_biz_phone" size="20" type="text" value="<%= bPhone %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "fax") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_biz_fax" size="20" type="text" value="<%= bFax %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "cell") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_biz_cell" size="20" type="text" value="<%= bCell %>">
		</td>
	</tr>
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "pager") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_biz_pager" size="20" type="text" value="<%= bPager %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "toll-free") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_biz_toll_free" size="20" type="text" value="<%= bTollFree %>">
		</td>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "email") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_biz_email_address" size="20" type="text" value="<%= bEmailAddress %>">
		</td>
	</tr>
	</table>
</liferay-ui:box>

<br>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "business-hours") %>' />

	<%
	SAXReader reader = new SAXReader();

	Document doc = null;
	try {
		doc = reader.read(new StringReader(hoursOfOperation));
	}
	catch (Exception e) {
		DocumentFactory docFactory = DocumentFactory.getInstance();

		doc = docFactory.createDocument(docFactory.createElement("hours-of-operation"));
	}

	Element root = doc.getRootElement();

	String[] days = CalendarUtil.getDays(locale);
	%>

	<table border="0" cellpadding="4" cellspacing="0" width="100%">

	<%
	int minuteInterval = 30;

	for (int i = 0; i < days.length; i++) {
		Element dayEl = root.element("day-" + i);

		int fromHourValue = 9;
		int fromMinuteValue = 0;
		int fromAmPmValue = Calendar.AM;

		int toHourValue = 5;
		int toMinuteValue = 0;
		int toAmPmValue = Calendar.PM;

		boolean available = true;
		if ((i == 0) || (i == 6)) {
			available = false;
		}

		if (dayEl != null) {
			fromHourValue = GetterUtil.get(dayEl.attributeValue("from-hour"), fromHourValue);
			fromMinuteValue = GetterUtil.get(dayEl.attributeValue("from-minute"), fromMinuteValue);
			fromAmPmValue = GetterUtil.get(dayEl.attributeValue("from-am-pm"), fromAmPmValue);

			toHourValue = GetterUtil.get(dayEl.attributeValue("to-hour"), toHourValue);
			toMinuteValue = GetterUtil.get(dayEl.attributeValue("to-minute"), toMinuteValue);
			toAmPmValue = GetterUtil.get(dayEl.attributeValue("to-am-pm"), toAmPmValue);

			available = GetterUtil.get(dayEl.attributeValue("available"), false);
		}
	%>

		<tr>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><b><%= days[i] %></b></font>
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "from") %></font>
			</td>
			<td>
				<liferay-ui:input-time
					hourParam='<%= renderResponse.getNamespace() + "contact_hop_" + i + "_from_hour" %>'
					hourValue='<%= fromHourValue %>'
					minuteParam='<%= renderResponse.getNamespace() + "contact_hop_" + i + "_from_minute" %>'
					minuteValue='<%= fromMinuteValue %>'
					minuteInterval='<%= minuteInterval %>'
					amPmParam='<%= renderResponse.getNamespace() + "contact_hop_" + i + "_from_am_pm" %>'
					amPmValue='<%= fromAmPmValue %>'
				/>
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "to") %></font>
			</td>
			<td>
				<liferay-ui:input-time
					hourParam='<%= renderResponse.getNamespace() + "contact_hop_" + i + "_to_hour" %>'
					hourValue='<%= toHourValue %>'
					minuteParam='<%= renderResponse.getNamespace() + "contact_hop_" + i + "_to_minute" %>'
					minuteValue='<%= toMinuteValue %>'
					minuteInterval='<%= minuteInterval %>'
					amPmParam='<%= renderResponse.getNamespace() + "contact_hop_" + i + "_to_am_pm" %>'
					amPmValue='<%= toAmPmValue %>'
				/>
			</td>
			<td>
				<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "available") %></font>
			</td>
			<td>
				<select name="<portlet:namespace />contact_hop_<%= i %>_available">
					<option <%= available ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "yes") %></option>
					<option <%= !available ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "no") %></option>
				</select>
			</td>
		</tr>

	<%
	}
	%>

	</table>
</liferay-ui:box>

<br>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "other-information") %>' />

	<table border="0" cellpadding="4" cellspacing="0" width="100%">
	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "birthday") %></b></font>
		</td>
		<td nowrap>

			<%
			Calendar cal = new GregorianCalendar();

			int currentYear = cal.get(Calendar.YEAR);

			int yearRangeStart = 0;
			int yearRangeEnd = 0;

			if (birthday == null) {
				yearRangeStart = currentYear - 100;
				yearRangeEnd = currentYear;
			}
			else {
				yearRangeStart = Integer.parseInt(bdYear) - 100;
				yearRangeEnd = currentYear;
			}
			%>

			<%--<liferay-ui:input-date
				formName='<%= renderResponse.getNamespace() + "fm" %>'
				monthParam='<%= renderResponse.getNamespace() + "contact_birthday_month" %>'
				monthValue='<%= GetterUtil.get(bdMonth, -1) %>'
				monthNullable='<%= true %>'
				dayParam='<%= renderResponse.getNamespace() + "contact_birthday_day" %>'
				dayValue='<%= GetterUtil.get(bdDay, -1) %>'
				dayNullable='<%= true %>'
				yearParam='<%= renderResponse.getNamespace() + "contact_birthday_year" %>'
				yearValue='<%= GetterUtil.get(bdYear, -1) %>'
				yearNullable='<%= true %>'
				yearRangeStart='<%= yearRangeStart %>'
				yearRangeEnd='<%= yearRangeEnd %>'
				firstDayOfWeek='<%= cal.getFirstDayOfWeek() - 1 %>'
				locale='<%= locale.toString() %>'
				calendarImage='<%= themeDisplay.getPathThemeImage() + "/calendar/calendar.gif" %>'
			/>--%>
		</td>
	</tr>
	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "time-zone") %></b></font>
		</td>
		<td>
			<%--<liferay-ui:input-time-zone
				name='<%= renderResponse.getNamespace() + "contact_time_zone_id" %>'
				value='<%= timeZoneId %>'
				locale='<%= locale.toString() %>'
			/>--%>
		</td>
	</tr>
	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "instant-messenger") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_im" size="25" type="text" value="<%= instantMessenger %>">
		</td>
	</tr>
	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "website") %></b></font>
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />contact_website" size="40" type="text" value="<%= website %>">
		</td>
	</tr>
	<tr>
		<td nowrap>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "comments") %></b></font>
		</td>
		<td>
			<textarea class="form-text" cols="70" name="<portlet:namespace />contact_comments" rows="10" wrap="soft"><%= comments %></textarea>
		</td>
	</tr>
	</table>
</liferay-ui:box>

<br>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_br_wrap_content" value="false" />

	<c:if test="<%= contact == null %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />saveContact();">

		<input class="portlet-form-button" type="button" value="<%= LanguageUtil.get(pageContext, "save-and-add-another") %>"
			onClick="
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.ADD %>';

				<c:if test="<%= Validator.isNull(mailRedirect) %>">
					document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/address_book/edit_contact" /></portlet:actionURL>';
				</c:if>

				<c:if test="<%= Validator.isNotNull(mailRedirect) %>">
					document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:actionURL><portlet:param name="struts_action" value="/address_book/edit_contact" /><portlet:param name="mail_redirect" value="<%= mailRedirect %>" /></portlet:actionURL>';
				</c:if>

				submitForm(document.<portlet:namespace />fm);"
		>
	</c:if>

	<c:if test="<%= contact != null %>">
		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update") %>' onClick="<portlet:namespace />saveContact();">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this-contact") %>')) { document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>'; document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<portlet:renderURL><portlet:param name="struts_action" value="/address_book/view_all" /></portlet:renderURL>'; submitForm(document.<portlet:namespace />fm); }">
	</c:if>

	<input class="portlet-form-button" type="button" value="<%= LanguageUtil.get(pageContext, "cancel") %>"
		onClick="
			<c:if test="<%= Validator.isNull(mailRedirect) %>">
				self.location = '<portlet:renderURL><portlet:param name="struts_action" value="/address_book/view_all" /></portlet:renderURL>';
			</c:if>

			<c:if test="<%= Validator.isNotNull(mailRedirect) %>">
				self.location = '<%= mailRedirect %>';
			</c:if>"
	>
</liferay-ui:box>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />contact_first_name.focus();
</script>