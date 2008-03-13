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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
long proposalId = ParamUtil.getLong(request, "proposalId", 0);

TasksProposal proposal = TasksProposalLocalServiceUtil.getProposal(proposalId);

String classPK = proposal.getClassPK();

String portletId = classPK.substring(classPK.indexOf(PortletImpl.LAYOUT_SEPARATOR) + PortletImpl.LAYOUT_SEPARATOR.length());

Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletId);

String selPortletPlid = classPK.substring(0, classPK.indexOf(PortletImpl.LAYOUT_SEPARATOR));

boolean supportsLAR = Validator.isNotNull(selPortlet.getPortletDataHandlerClass());
boolean supportsSetup = Validator.isNotNull(selPortlet.getConfigurationActionClass());

String pagesRedirect = ParamUtil.getString(request, "pagesRedirect");

long groupId = ParamUtil.getLong(request, "groupId");

Group selGroup = GroupLocalServiceUtil.getGroup(groupId);

Group liveGroup = null;
Group stagingGroup = null;

if (selGroup.isStagingGroup()) {
	liveGroup = selGroup.getLiveGroup();
	stagingGroup = selGroup;
}
else {
	liveGroup = selGroup;

	if (selGroup.hasStagingGroup()) {
		stagingGroup = selGroup.getStagingGroup();
	}
}

long selGroupId = selGroup.getGroupId();

long liveGroupId = liveGroup.getGroupId();

long stagingGroupId = 0;

if (stagingGroup != null) {
	stagingGroupId = stagingGroup.getGroupId();
}

String popupId = "publish-to-live";

PortletURL portletURL = renderResponse.createActionURL();

portletURL.setParameter("struts_action", "/communities/edit_proposal");
portletURL.setParameter(Constants.CMD, Constants.PUBLISH);
portletURL.setParameter("proposalId", String.valueOf(proposalId));
portletURL.setParameter("groupId", String.valueOf(liveGroupId));
portletURL.setParameter("plid", selPortletPlid);

response.setHeader("Ajax-ID", request.getHeader("Ajax-ID"));

String tabs2 = "staging";
%>

<form action="<%= portletURL.toString() %>" method="post" name="<portlet:namespace />fm2">
<input name="<portlet:namespace />pagesRedirect" type="hidden" value="<%= pagesRedirect %>">
<input name="<portlet:namespace />stagingGroupId" type="hidden" value="<%= stagingGroupId %>">

<%@ include file="/html/portlet/portlet_configuration/export_import_options.jspf" %>

<br />

<input id="publishBtn" type="button" value='<liferay-ui:message key="publish" />' onClick='if (confirm("<liferay-ui:message key='<%= "are-you-sure-you-want-to-publish-this-portlet" %>' />")) { submitForm(document.<portlet:namespace />fm2); }' />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="Liferay.Popup.close(this);" />

</form>

<%@ include file="/html/portlet/communities/render_controls.jspf" %>