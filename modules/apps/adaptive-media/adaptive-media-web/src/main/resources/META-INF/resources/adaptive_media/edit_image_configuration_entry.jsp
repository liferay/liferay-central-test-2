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

boolean configurationEntryEditable = GetterUtil.getBoolean(request.getAttribute(AdaptiveMediaWebKeys.CONFIGURATION_ENTRY_EDITABLE));
AdaptiveMediaImageConfigurationEntry configurationEntry = (AdaptiveMediaImageConfigurationEntry)request.getAttribute(AdaptiveMediaWebKeys.CONFIGURATION_ENTRY);

String configurationEntryUuid = ParamUtil.getString(request, "uuid", (configurationEntry != null) ? configurationEntry.getUUID() : StringPool.BLANK);

renderResponse.setTitle((configurationEntry != null) ? configurationEntry.getName() : LanguageUtil.get(request, "new-image-resolution"));

Map<String, String> properties = null;

if (configurationEntry != null) {
	properties = configurationEntry.getProperties();
}
%>

<div class="container-fluid-1280">
	<liferay-ui:error exception="<%= AdaptiveMediaImageConfigurationException.DuplicateAdaptiveMediaImageConfigurationNameException.class %>" message="a-configuration-with-this-name-already-exists" />
	<liferay-ui:error exception="<%= AdaptiveMediaImageConfigurationException.DuplicateAdaptiveMediaImageConfigurationUuidException.class %>" message="a-configuration-with-this-id-already-exists" />
	<liferay-ui:error exception="<%= AdaptiveMediaImageConfigurationException.InvalidHeightException.class %>" message="please-enter-a-max-height-value-larger-than-0" />
	<liferay-ui:error exception="<%= AdaptiveMediaImageConfigurationException.InvalidNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= AdaptiveMediaImageConfigurationException.InvalidUuidException.class %>" message="please-enter-a-valid-identifier" />
	<liferay-ui:error exception="<%= AdaptiveMediaImageConfigurationException.InvalidWidthException.class %>" message="please-enter-a-max-width-value-larger-than-0" />
	<liferay-ui:error exception="<%= AdaptiveMediaImageConfigurationException.RequiredWidthOrHeightException.class %>" message="please-enter-a-max-width-or-max-height-value-larger-than-0" />

	<portlet:actionURL name="/adaptive_media/edit_image_configuration_entry" var="editImageConfigurationEntryURL">
		<portlet:param name="mvcRenderCommandName" value="/adaptive_media/edit_image_configuration_entry" />
	</portlet:actionURL>

	<aui:form action="<%= editImageConfigurationEntryURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="uuid" type="hidden" value="<%= configurationEntryUuid %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<div class="alert alert-info">
					<c:choose>
						<c:when test="<%= configurationEntryEditable %>">
							<liferay-ui:message key="at-least-one-dimension-value-is-required" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="the-images-for-this-resolution-are-already-adapted" />
						</c:otherwise>
					</c:choose>
				</div>

				<div class="row">
					<div class="col-md-6">
						<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" required="<%= true %>" value="<%= (configurationEntry != null) ? configurationEntry.getName() : StringPool.BLANK %>" />

						<aui:input name="description" type="textarea" value="<%= (configurationEntry != null) ? configurationEntry.getDescription() : StringPool.BLANK %>" />
					</div>
				</div>

				<div class="row">
					<div class="col-md-3">

						<%
						String maxWidth = StringPool.BLANK;

						if (properties != null) {
							String curMaxWidth = properties.get("max-width");

							if (!curMaxWidth.equals("0")) {
								maxWidth = curMaxWidth;
							}
						}
						%>

						<aui:input disabled="<%= !configurationEntryEditable %>" label="max-width-px" min="1" name="maxWidth" type="number" value="<%= maxWidth %>">
							<aui:validator name="number" />
						</aui:input>
					</div>

					<div class="col-md-3">

						<%
						String maxHeight = StringPool.BLANK;

						if (properties != null) {
							String curMaxHeight = properties.get("max-height");

							if (!curMaxHeight.equals("0")) {
								maxHeight = curMaxHeight;
							}
						}
						%>

						<aui:input disabled="<%= !configurationEntryEditable %>" label="max-height-px" min="1" name="maxHeight" type="number" value="<%= maxHeight %>">
							<aui:validator name="number" />
						</aui:input>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<c:if test="<%= configurationEntry == null %>">
							<aui:input label="add-a-resolution-for-high-density-displays" name="addHighResolution" type="checkbox" />
						</c:if>
					</div>
				</div>

				<div class="form-group">

					<%
					boolean automaticUuid;

					if (configurationEntry == null) {
						automaticUuid = Validator.isNull(configurationEntryUuid);
					}
					else {
						automaticUuid = configurationEntryUuid.equals(FriendlyURLNormalizerUtil.normalize(configurationEntry.getName()));
					}

					automaticUuid = ParamUtil.getBoolean(request, "automaticUuid", automaticUuid);
					%>

					<h4>
						<liferay-ui:message key="identifier" />
					</h4>

					<div class="form-group" id="<portlet:namespace />idOptions">
						<aui:input checked="<%= automaticUuid %>" disabled="<%= !configurationEntryEditable %>" helpMessage="the-id-is-based-on-the-name-field" label="automatic" name="automaticUuid" type="radio" value="<%= true %>" />

						<aui:input checked="<%= !automaticUuid %>" disabled="<%= !configurationEntryEditable %>" label="custom" name="automaticUuid" type="radio" value="<%= false %>" />
					</div>

					<aui:input cssClass="input-medium" disabled="<%= automaticUuid || !configurationEntryEditable %>" label="id" name="newUuid" type="text" value="<%= configurationEntryUuid %>" />
				</div>
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<c:if test="<%= configurationEntryEditable %>">
	<aui:script require="adaptive-media-web/adaptive_media/js/EditAdaptiveMediaConfig.es">
		new adaptiveMediaWebAdaptive_mediaJsEditAdaptiveMediaConfigEs.default(
			{
				namespace: '<portlet:namespace />'
			}
		);
	</aui:script>
</c:if>