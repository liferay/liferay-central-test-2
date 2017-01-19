<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/adaptive_media/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

ImageAdaptiveMediaConfigurationEntry configurationEntry = (ImageAdaptiveMediaConfigurationEntry)request.getAttribute(AdaptiveMediaWebKeys.CONFIGURATION_ENTRY);

renderResponse.setTitle((configurationEntry != null) ? configurationEntry.getName() : LanguageUtil.get(request, "new-image-resolution"));

Map<String, String> properties = null;

if (configurationEntry != null) {
	properties = configurationEntry.getProperties();
}
%>

<div class="container-fluid-1280">
	<liferay-ui:error exception="<%= ImageAdaptiveMediaConfigurationException.DuplicateImageAdaptiveMediaConfigurationEntryException.class %>" message="there-is-already-a-configuration-with-the-same-id" />
	<liferay-ui:error exception="<%= ImageAdaptiveMediaConfigurationException.InvalidHeightException.class %>" message="please-enter-a-max-height-value-larger-than-0" />
	<liferay-ui:error exception="<%= ImageAdaptiveMediaConfigurationException.InvalidWidthException.class %>" message="please-enter-a-max-width-value-larger-than-0" />

	<portlet:actionURL name="/adaptive_media/edit_image_configuration_entry" var="editImageConfigurationEntryURL">
		<portlet:param name="mvcRenderCommandName" value="/adaptive_media/edit_image_configuration_entry" />
	</portlet:actionURL>

	<aui:form action="<%= editImageConfigurationEntryURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="entryUuid" type="hidden" value="<%= (configurationEntry != null) ? configurationEntry.getUUID() : StringPool.BLANK %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" required="<%= true %>" value="<%= (configurationEntry != null) ? configurationEntry.getName() : StringPool.BLANK %>" />

				<aui:input name="uuid" required="<%= true %>" value="<%= (configurationEntry != null) ? configurationEntry.getUUID() : StringPool.BLANK %>" />

				<aui:input label="max-width-px" name="maxWidth" required="<%= true %>" value='<%= (properties != null) ? properties.get("width") : StringPool.BLANK %>' />

				<aui:input label="max-height-px" name="maxHeight" required="<%= true %>" value='<%= (properties != null) ? properties.get("height") : StringPool.BLANK %>' />
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>