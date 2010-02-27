<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/portlet_css/init.jsp" %>

<div id="lfr-look-and-feel">
	<div class="aui-tabview" id="portlet-set-properties">
		<ul class="aui-tabview-list">
			<li>
				<a href="#portlet-config"><liferay-ui:message key="portlet-configuration" /></a>
			</li>
			<li>
				<a href="#text-styles"><liferay-ui:message key="text-styles" /></a>
			</li>
			<li>
				<a href="#background-styles"><liferay-ui:message key="background-styles" /></a>
			</li>
			<li>
				<a href="#border-styles"><liferay-ui:message key="border-styles" /></a>
			</li>
			<li>
				<a href="#spacing-styles"><liferay-ui:message key="margin-and-padding" /></a>
			</li>
			<li>
				<a href="#css-styling"><liferay-ui:message key="advanced-styling" /></a>
			</li>
			<li>
				<a href="#wap-styling"><liferay-ui:message key="wap-styling" /></a>
			</li>
		</ul>

		<form class="aui-form" method="post">
		<input type="hidden" name="portlet-area" id="portlet-area" />
		<input type="hidden" name="portlet-boundary-id" id="portlet-boundary-id" />

		<div class="aui-tabview-content">
			<fieldset class="aui-fieldset" id="portlet-config">
				<legend><liferay-ui:message key="portlet-configuration" /></legend>

				<aui:input inlineField="<%= true %>" label="portlet-title" name="custom-title" />

				<aui:select inlineField="<%= true %>" label="portlet-title" name="lfr-portlet-language">

					<%
					Locale[] locales = LanguageUtil.getAvailableLocales();

					for (int i = 0; i < locales.length; i++) {
					%>

						<aui:option label="<%= locales[i].getDisplayName(locale) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input label="use-custom-title" name="use-custom-title" type="checkbox" />

				<aui:select label="link-portlet-urls-to-page" name="lfr-point-links">
					<aui:option label="current-page" value="" />

					<%
					long linkToLayoutId = 0;

					LayoutLister layoutLister = new LayoutLister();

					String rootNodeName = layout.getGroup().getName();
					LayoutView layoutView = layoutLister.getLayoutView(layout.getGroup().getGroupId(), layout.getPrivateLayout(), rootNodeName, locale);

					List layoutList = layoutView.getList();

					for (int i = 0; i < layoutList.size(); i++) {

						// id | parentId | ls | obj id | name | img | depth

						String layoutDesc = (String)layoutList.get(i);

						String[] nodeValues = StringUtil.split(layoutDesc, "|");

						long objId = GetterUtil.getLong(nodeValues[3]);
						String name = nodeValues[4];

						int depth = 0;

						if (i != 0) {
							depth = GetterUtil.getInteger(nodeValues[6]);
						}

						for (int j = 0; j < depth; j++) {
							name = "-&nbsp;" + name;
						}

						Layout linkableLayout = null;

						try {
							if (objId > 0) {
								linkableLayout = LayoutLocalServiceUtil.getLayout(objId);
							}
						}
						catch (Exception e) {
						}

						if (linkableLayout != null) {
					%>

							<aui:option label="<%= name %>" selected="<%= (linkableLayout.getLayoutId() == linkToLayoutId) %>" value="<%= linkableLayout.getLayoutId() %>" />

					<%
						}
					}
					%>

				</aui:select>

				<aui:input label="show-borders" name="show-borders" type="checkbox" />

				<span class="form-hint portlet-msg-info aui-helper-hidden" id="border-note">
					<liferay-ui:message key="this-change-will-only-be-shown-after-you-refresh-the-page" />
				</span>
			</fieldset>

			<fieldset class="aui-fieldset" id="text-styles">
				<legend><liferay-ui:message key="text-styles" /></legend>

				<div class="common aui-column aui-form-column">
					<div class="aui-column-content">
						<aui:select label="font" name="lfr-font-family">
							<aui:option label="" value="" />
							<aui:option label="Arial" value="Arial" />
							<aui:option label="Georgia" value="Georgia" />
							<aui:option label="Times New Roman" value="Times New Roman" />
							<aui:option label="Tahoma" value="Tahoma" />
							<aui:option label="Trebuchet MS" value="Trebuchet MS" />
							<aui:option label="Verdana" value="Verdana" />
						</aui:select>

						<aui:input label="bold" name="lfr-font-bold" type="checkbox" />

						<aui:input label="italic" name="lfr-font-italic" type="checkbox" />

						<aui:select label="size" name="lfr-font-size">
							<aui:option label="" value="" />

							<%
							DecimalFormat decimalFormat = new DecimalFormat("#.##em");

							for (double i = 0.1; i <= 12; i += 0.1) {
								String value = decimalFormat.format(i);
							%>

								<aui:option label="<%= value %>" value="<%= value %>" />

							<%
							}
							%>

						</aui:select>

						<aui:input label="color" name="lfr-font-color" />

						<aui:select label="alignment" name="lfr-font-align">
							<aui:option label="" value="" />
							<aui:option label="justify" value="justify" />
							<aui:option label="left" value="left" />
							<aui:option label="right" value="right" />
							<aui:option label="center" value="center" />
						</aui:select>

						<aui:select label="text-decoration" name="lfr-font-decoration">
							<aui:option label="" value="" />
							<aui:option label="none" value="none" />
							<aui:option label="underline" value="underline" />
							<aui:option label="overline" value="overline" />
							<aui:option label="strikethrough" value="line-through" />
						</aui:select>
					</div>
				</div>

				<div class="extra aui-column aui-form-column">
					<div class="aui-column-content">
						<aui:select label="word-spacing" name="lfr-font-space">
							<aui:option label="" value="" />

							<%
							DecimalFormat decimalFormat = new DecimalFormat("#.##em");

							for (double i = -1; i <= 1; i += 0.05) {
								String value = decimalFormat.format(i);

								if (value.equals("0em")) {
									value = "normal";
								}
							%>

								<aui:option label="<%= value %>" value="<%= value %>" />

							<%
							}
							%>

						</aui:select>

						<aui:select label="line-height" name="lfr-font-leading">
							<aui:option label="" value="" />

							<%
							DecimalFormat decimalFormat = new DecimalFormat("#.##em");

							for (double i = 0.1; i <= 12; i += 0.1) {
								String value = decimalFormat.format(i);
							%>

								<aui:option label="<%= value %>" value="<%= value %>" />

							<%
							}
							%>

						</aui:select>

						<aui:select label="letter-spacing" name="lfr-font-tracking">
							<aui:option label="" value="" />

							<%
								for (int i = -10; i <= 50; i++) {
									String value = i + "px";

									if (i == 0) {
										value = "0";
									}
								%>
									<aui:option label="<%= value %>" value="<%= value %>" />
								<%
								}
							%>

						</aui:select>
					</div>
				</div>
			</fieldset>

			<fieldset class="aui-fieldset" id="background-styles">
				<legend><liferay-ui:message key="background-styles" /></legend>

				<aui:input label="background-color" name="lfr-bg-color" />
			</fieldset>

			<fieldset class="aui-fieldset" id="border-styles">
				<legend><liferay-ui:message key="border-styling" /></legend>

				<fieldset class="aui-column aui-form-column" id="lfr-border-width">
					<legend><liferay-ui:message key="border-width" /></legend>

					<div class="aui-column-content">
						<aui:input checked="checked" cssClass="lfr-use-for-all" inlineField="<%= true %>" label="same-for-all" name="lfr-use-for-all-width" type="checkbox" />

						<span class="aui-field-row">
							<aui:input inlineField="<%= true %>" label="top" name="lfr-border-width-top" />

							<aui:select inlineField="<%= true %>" label="" name="lfr-border-width-top-unit">
								<aui:option label="%" value="%" />
								<aui:option label="px" value="px" />
								<aui:option label="em" value="em" />
							</aui:select>
						</span>

						<span class="aui-field-row">
							<aui:input inlineField="<%= true %>" label="right" name="lfr-border-width-right" />

							<aui:select inlineField="<%= true %>" label="" name="lfr-border-width-right-unit">
								<aui:option label="%" value="%" />
								<aui:option label="px" value="px" />
								<aui:option label="em" value="em" />
							</aui:select>
						</span>

						<span class="aui-field-row">
							<aui:input inlineField="<%= true %>" label="bottom" name="lfr-border-width-bottom" />

							<aui:select inlineField="<%= true %>" label="" name="lfr-border-width-bottom-unit">
								<aui:option label="%" value="%" />
								<aui:option label="px" value="px" />
								<aui:option label="em" value="em" />
							</aui:select>
						</span>

						<span class="aui-field-row">
							<aui:input inlineField="<%= true %>" label="left" name="lfr-border-width-left" />

							<aui:select inlineField="<%= true %>" label="" name="lfr-border-width-left-unit">
								<aui:option label="%" value="%" />
								<aui:option label="px" value="px" />
								<aui:option label="em" value="em" />
							</aui:select>
						</span>
					</div>
				</fieldset>

				<fieldset class="aui-column aui-form-column" id="lfr-border-style">
					<legend><liferay-ui:message key="border-style" /></legend>

					<div class="aui-column-content">
						<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="lfr-use-for-all-style" type="checkbox" />

						<aui:select label="top" name="lfr-border-style-top">
							<aui:option label="" value="" />
							<aui:option label="dashed" value="dashed" />
							<aui:option label="double" value="double" />
							<aui:option label="dotted" value="dotted" />
							<aui:option label="groove" value="groove" />
							<aui:option label="hidden[css]" value="hidden" />
							<aui:option label="inset" value="inset" />
							<aui:option label="outset" value="outset" />
							<aui:option label="ridge" value="ridge" />
							<aui:option label="solid" value="solid" />
						</aui:select>

						<aui:select label="right" name="lfr-border-style-right">
							<aui:option label="" value="" />
							<aui:option label="dashed" value="dashed" />
							<aui:option label="double" value="double" />
							<aui:option label="dotted" value="dotted" />
							<aui:option label="groove" value="groove" />
							<aui:option label="hidden[css]" value="hidden" />
							<aui:option label="inset" value="inset" />
							<aui:option label="outset" value="outset" />
							<aui:option label="ridge" value="ridge" />
							<aui:option label="solid" value="solid" />
						</aui:select>

						<aui:select label="bottom" name="lfr-border-style-bottom">
							<aui:option label="" value="" />
							<aui:option label="dashed" value="dashed" />
							<aui:option label="double" value="double" />
							<aui:option label="dotted" value="dotted" />
							<aui:option label="groove" value="groove" />
							<aui:option label="hidden[css]" value="hidden" />
							<aui:option label="inset" value="inset" />
							<aui:option label="outset" value="outset" />
							<aui:option label="ridge" value="ridge" />
							<aui:option label="solid" value="solid" />
						</aui:select>

						<aui:select label="left" name="lfr-border-style-left">
							<aui:option label="" value="" />
							<aui:option label="dashed" value="dashed" />
							<aui:option label="double" value="double" />
							<aui:option label="dotted" value="dotted" />
							<aui:option label="groove" value="groove" />
							<aui:option label="hidden[css]" value="hidden" />
							<aui:option label="inset" value="inset" />
							<aui:option label="outset" value="outset" />
							<aui:option label="ridge" value="ridge" />
							<aui:option label="solid" value="solid" />
						</aui:select>
					</div>
				</fieldset>

				<fieldset class="aui-column aui-form-column" id="lfr-border-color">
					<legend><liferay-ui:message key="border-color" /></legend>

					<div class="aui-column-content">
						<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="lfr-use-for-all-color" type="checkbox" />

						<aui:input label="top" name="lfr-border-color-top" />

						<aui:input label="right" name="lfr-border-color-right" />

						<aui:input label="bottom" name="lfr-border-color-bottom" />

						<aui:input label="left" name="lfr-border-color-left" />
					</div>
				</fieldset>
			</fieldset>

			<fieldset class="spacing aui-fieldset" id="spacing-styles">
				<legend><liferay-ui:message key="spacing" /></legend>

				<fieldset class="aui-column aui-form-column" id="lfr-padding">
					<legend><liferay-ui:message key="padding" /></legend>

					<div class="aui-column-content">
						<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="lfr-use-for-all-padding" type="checkbox" />

						<span class="aui-field-row">
							<aui:input inlineField="<%= true %>" label="top" name="lfr-padding-top" />

							<aui:select inlineField="<%= true %>" label="" name="lfr-padding-top-unit">
								<aui:option label="%" value="%" />
								<aui:option label="px" value="px" />
								<aui:option label="em" value="em" />
							</aui:select>
						</span>

						<span class="aui-field-row">
							<aui:input inlineField="<%= true %>" label="right" name="lfr-padding-right" />

							<aui:select inlineField="<%= true %>" label="" name="lfr-padding-right-unit">
								<aui:option label="%" value="%" />
								<aui:option label="px" value="px" />
								<aui:option label="em" value="em" />
							</aui:select>
						</span>

						<span class="aui-field-row">
							<aui:input inlineField="<%= true %>" label="bottom" name="lfr-padding-bottom" />

							<aui:select inlineField="<%= true %>" label="" name="lfr-padding-bottom-unit">
								<aui:option label="%" value="%" />
								<aui:option label="px" value="px" />
								<aui:option label="em" value="em" />
							</aui:select>
						</span>

						<span class="aui-field-row">
							<aui:input inlineField="<%= true %>" label="left" name="lfr-padding-left" />

							<aui:select inlineField="<%= true %>" label="" name="lfr-padding-left-unit">
								<aui:option label="%" value="%" />
								<aui:option label="px" value="px" />
								<aui:option label="em" value="em" />
							</aui:select>
						</span>
					</div>
				</fieldset>

				<fieldset class="aui-column aui-form-column" id="lfr-margin">
					<legend><liferay-ui:message key="margin" /></legend>

					<div class="aui-column-content">
						<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="lfr-use-for-all-margin" type="checkbox" />

						<span class="aui-field-row">
							<aui:input inlineField="<%= true %>" label="top" name="lfr-margin-top" />

							<aui:select inlineField="<%= true %>" label="" name="lfr-margin-top-unit">
								<aui:option label="%" value="%" />
								<aui:option label="px" value="px" />
								<aui:option label="em" value="em" />
							</aui:select>
						</span>

						<span class="aui-field-row">
							<aui:input inlineField="<%= true %>" label="right" name="lfr-margin-right" />

							<aui:select inlineField="<%= true %>" label="" name="lfr-margin-right-unit">
								<aui:option label="%" value="%" />
								<aui:option label="px" value="px" />
								<aui:option label="em" value="em" />
							</aui:select>
						</span>

						<span class="aui-field-row">
							<aui:input inlineField="<%= true %>" label="bottom" name="lfr-margin-bottom" />

							<aui:select inlineField="<%= true %>" label="" name="lfr-margin-bottom-unit">
								<aui:option label="%" value="%" />
								<aui:option label="px" value="px" />
								<aui:option label="em" value="em" />
							</aui:select>
						</span>

						<span class="aui-field-row">
							<aui:input inlineField="<%= true %>" label="left" name="lfr-margin-left" />

							<aui:select inlineField="<%= true %>" label="" name="lfr-margin-left-unit">
								<aui:option label="%" value="%" />
								<aui:option label="px" value="px" />
								<aui:option label="em" value="em" />
							</aui:select>
						</span>
					</div>
				</fieldset>
			</fieldset>

			<fieldset class="aui-fieldset" id="css-styling">
				<legend><liferay-ui:message key="advanced-css-styling" /></legend>

				<aui:input label="enter-your-custom-css" name="lfr-custom-css" type="textarea" />
			</fieldset>

			<fieldset class="aui-fieldset" id="wap-styling">
				<legend><liferay-ui:message key="wap-styling" /></legend>

				<aui:input label="title" name="lfr-wap-title" />

				<aui:select label="initial-window-state" name="lfr-wap-initial-window-state">
					<aui:option label="minimized" value="MINIMIZED" />
					<aui:option label="normal" value="NORMAL" />
				</aui:select>
			</fieldset>

			<aui:button-row>
				<aui:button name="lfr-lookfeel-save" value="save" />
				<aui:button name="lfr-lookfeel-reset" value="reset" />
			</aui:button-row>
		</div>

		</form>
	</div>
</div>