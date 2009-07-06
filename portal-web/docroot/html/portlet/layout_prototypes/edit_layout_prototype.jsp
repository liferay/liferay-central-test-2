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

<%@ include file="/html/portlet/layout_prototypes/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

LayoutPrototype layoutPrototype = (LayoutPrototype)request.getAttribute(WebKeys.LAYOUT_PROTOTYPE);

if (layoutPrototype == null) {
	layoutPrototype = new LayoutPrototypeImpl();

	layoutPrototype.setNew(true);
	layoutPrototype.setActive(true);
}

long layoutPrototypeId = BeanParamUtil.getLong(layoutPrototype, request, "layoutPrototypeId");

Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

Locale[] locales = LanguageUtil.getAvailableLocales();
%>

<script type="text/javascript">
	function <portlet:namespace />saveLayoutPrototype() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= layoutPrototype == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/layout_prototypes/edit_layout_prototype" /></portlet:actionURL>");
	}
</script>

<liferay-util:include page="/html/portlet/layout_prototypes/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value='<%= layoutPrototype.isNew() ? "add" : "view-all" %>' />
</liferay-util:include>

<form class="aui-form" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveLayoutPrototype(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />layoutPrototypeId" type="hidden" value="<%= layoutPrototypeId %>" />

<fieldset class="aui-block-labels">
	<div class="aui-ctrl-holder">
		<label><liferay-ui:message key="name" /></label>

		<liferay-ui:input-field model="<%= LayoutPrototype.class %>" bean="<%= layoutPrototype %>" field="name" />
	</div>

	<div class="aui-ctrl-holder">
		<label><liferay-ui:message key="description" /></label>

		<liferay-ui:input-field model="<%= LayoutPrototype.class %>" bean="<%= layoutPrototype %>" field="description" />
	</div>

	<div class="aui-ctrl-holder">
		<label><%= LanguageUtil.get(pageContext, "active") %></label>

		<liferay-ui:input-field model="<%= LayoutPrototype.class %>" bean="<%= layoutPrototype %>" field="active" />
	</div>

	<c:if test="<%= !layoutPrototype.isNew() %>">
		<div class="aui-ctrl-holder">
			<label><liferay-ui:message key="configuration" /></label>

			<liferay-portlet:actionURL var="viewURL"  portletName="<%= PortletKeys.MY_PLACES %>">
				<portlet:param name="struts_action" value="/my_places/view" />
				<portlet:param name="groupId" value="<%= String.valueOf(layoutPrototype.getGroup().getGroupId()) %>" />
				<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon image="view" message="open-page-template" url="<%= viewURL %>" method="get" target="_blank" label="<%= true %>" /> (<liferay-ui:message key="new-window" />)
		</div>
	</c:if>

	<div class="aui-button-holder">
		<input type="submit" value="<liferay-ui:message key="save" />" />

		<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />
	</div>
</fieldset>

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</script>
</c:if>