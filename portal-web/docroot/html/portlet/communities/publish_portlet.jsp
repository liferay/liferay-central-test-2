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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String tabs2 = "staging";

String pagesRedirect = ParamUtil.getString(request, "pagesRedirect");

long proposalId = ParamUtil.getLong(request, "proposalId");

TasksProposal proposal = TasksProposalLocalServiceUtil.getProposal(proposalId);

String classPK = proposal.getClassPK();

int pos = classPK.indexOf(PortletConstants.LAYOUT_SEPARATOR);

String selPortletId = classPK.substring(pos + PortletConstants.LAYOUT_SEPARATOR.length());

Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), selPortletId);

String selPlid = classPK.substring(0, pos);

long groupId = ParamUtil.getLong(request, "groupId");

Group selGroup = GroupLocalServiceUtil.getGroup(groupId);

Group liveGroup = selGroup.getLiveGroup();
Group stagingGroup = selGroup;

long liveGroupId = liveGroup.getGroupId();
long stagingGroupId = stagingGroup.getGroupId();

PortletURL portletURL = renderResponse.createActionURL();

portletURL.setParameter("struts_action", "/communities/edit_proposal");
portletURL.setParameter(Constants.CMD, Constants.PUBLISH);
portletURL.setParameter("proposalId", String.valueOf(proposalId));
portletURL.setParameter("groupId", String.valueOf(liveGroupId));
portletURL.setParameter("plid", selPlid);

boolean supportsLAR = Validator.isNotNull(selPortlet.getPortletDataHandlerClass());
boolean supportsSetup = Validator.isNotNull(selPortlet.getConfigurationActionClass());

response.setHeader("Ajax-ID", request.getHeader("Ajax-ID"));
%>

<script type="text/javascript">
	function <portlet:namespace />publish() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-publish-this-portlet") %>')) {
			submitForm(document.<portlet:namespace />fm);
		}
	}
</script>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "publish(); return false;" %>'>
	<aui:input name="pagesRedirect" type="hidden" value="<%= pagesRedirect %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />

	<%@ include file="/html/portlet/portlet_configuration/export_import_options.jspf" %>

	<aui:button-row>
		<aui:button name="publishBtn" type="submit" value="publish" />

		<aui:button onClick="AUI().DialogManager.closeByChild(this);" type="cancel" />
	</aui:button-row>
</aui:form>

<%@ include file="/html/portlet/communities/render_controls.jspf" %>