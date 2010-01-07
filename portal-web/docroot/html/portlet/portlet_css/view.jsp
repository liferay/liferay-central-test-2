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
			<fieldset class="aui-block-labels" id="portlet-config">
				<legend><liferay-ui:message key="portlet-configuration" /></legend>

				<span class="aui-field">
					<span class="aui-field-content">
						<label for="custom-title"><liferay-ui:message key="portlet-title" /></label>

						<input class="text-input" id="custom-title" type="text" name="custom-title" value="" />

						<select id="lfr-portlet-language" name="lfr-portlet-language">

							<%
							Locale[] locales = LanguageUtil.getAvailableLocales();

							for (int i = 0; i < locales.length; i++) {
							%>

								<option value="<%= LocaleUtil.toLanguageId(locales[i]) %>"><%= locales[i].getDisplayName(locale) %></option>

							<%
							}
							%>

						</select>	
					</span>
				</span>

				<span class="aui-field">
					<span class="aui-field-content">
						<label class="inline-label">
							<liferay-ui:message key="use-custom-title" /> <input id="use-custom-title-checkbox" name="use-custom-title-checkbox" type="checkbox" />
						</label>	
					</span>
				</span>

				<span class="aui-field">
					<span class="aui-field-content">
						<label for="lfr-point-links"><liferay-ui:message key="link-portlet-urls-to-page" /></label>

						<select id="lfr-point-links">
							<option value=""><liferay-ui:message key="current-page" /></option>

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

									<option <%= (linkableLayout.getLayoutId() == linkToLayoutId) ? "selected" : "" %> value="<%= linkableLayout.getLayoutId() %>"><%= name %></option>

							<%
								}
							}
							%>

						</select>	
					</span>
				</span>

				<span class="aui-field">
					<span class="aui-field-content">
						<label class="inline-label">
							<liferay-ui:message key="show-borders" /> <input id="show-borders" name="show-borders" type="checkbox" />
						</label>

						<span class="form-hint portlet-msg-info" id="border-note">
							<liferay-ui:message key="this-change-will-only-be-shown-after-you-refresh-the-page" />
						</span>	
					</span>
				</span>
			</fieldset>

			<fieldset class="aui-block-labels" id="text-styles">
				<legend><liferay-ui:message key="text-styles" /></legend>

				<div class="common aui-form-column">
					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-font-family"><liferay-ui:message key="font" /></label>

							<select id="lfr-font-family">
								<option value=""></option>
								<option value="Arial">Arial</option>
								<option value="Georgia">Georgia</option>
								<option value="Times New Roman">Times New Roman</option>
								<option value="Tahoma">Tahoma</option>
								<option value="Trebuchet MS">Trebuchet MS</option>
								<option value="Verdana">Verdana</option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label class="inline-label" for="lfr-font-bold"><liferay-ui:message key="bold" />
								<input type="checkbox" name="lfr-font-bold" id="lfr-font-bold" />
							</label>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label class="inline-label" for="lfr-font-italic"><liferay-ui:message key="italic" />
								<input type="checkbox" name="lfr-font-italic" id="lfr-font-italic" />
							</label>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-font-size"><liferay-ui:message key="size" /></label>

							<select id="lfr-font-size" name="lfr-font-size">
								<option value=""></option>
								<option value="0.1em">0.1em</option>
								<option value="0.2em">0.2em</option>
								<option value="0.3em">0.3em</option>
								<option value="0.4em">0.4em</option>
								<option value="0.5em">0.5em</option>
								<option value="0.6em">0.6em</option>
								<option value="0.7em">0.7em</option>
								<option value="0.8em">0.8em</option>
								<option value="0.9em">0.9em</option>
								<option value="1em">1em</option>
								<option value="1.1em">1.1em</option>
								<option value="1.2em">1.2em</option>
								<option value="1.3em">1.3em</option>
								<option value="1.4em">1.4em</option>
								<option value="1.5em">1.5em</option>
								<option value="1.6em">1.6em</option>
								<option value="1.7em">1.7em</option>
								<option value="1.8em">1.8em</option>
								<option value="1.9em">1.9em</option>
								<option value="2em">2em</option>
								<option value="2.1em">2.1em</option>
								<option value="2.2em">2.2em</option>
								<option value="2.3em">2.3em</option>
								<option value="2.4em">2.4em</option>
								<option value="2.5em">2.5em</option>
								<option value="2.6em">2.6em</option>
								<option value="2.7em">2.7em</option>
								<option value="2.8em">2.8em</option>
								<option value="2.9em">2.9em</option>
								<option value="3em">3em</option>
								<option value="3.1em">3.1em</option>
								<option value="3.2em">3.2em</option>
								<option value="3.3em">3.3em</option>
								<option value="3.4em">3.4em</option>
								<option value="3.5em">3.5em</option>
								<option value="3.6em">3.6em</option>
								<option value="3.7em">3.7em</option>
								<option value="3.8em">3.8em</option>
								<option value="3.9em">3.9em</option>
								<option value="4em">4em</option>
								<option value="4.1em">4.1em</option>
								<option value="4.2em">4.2em</option>
								<option value="4.3em">4.3em</option>
								<option value="4.4em">4.4em</option>
								<option value="4.5em">4.5em</option>
								<option value="4.6em">4.6em</option>
								<option value="4.7em">4.7em</option>
								<option value="4.8em">4.8em</option>
								<option value="4.9em">4.9em</option>
								<option value="5em">5em</option>
								<option value="5.1em">5.1em</option>
								<option value="5.2em">5.2em</option>
								<option value="5.3em">5.3em</option>
								<option value="5.4em">5.4em</option>
								<option value="5.5em">5.5em</option>
								<option value="5.6em">5.6em</option>
								<option value="5.7em">5.7em</option>
								<option value="5.8em">5.8em</option>
								<option value="5.9em">5.9em</option>
								<option value="6em">6em</option>
								<option value="6.1em">6.1em</option>
								<option value="6.2em">6.2em</option>
								<option value="6.3em">6.3em</option>
								<option value="6.4em">6.4em</option>
								<option value="6.5em">6.5em</option>
								<option value="6.6em">6.6em</option>
								<option value="6.7em">6.7em</option>
								<option value="6.8em">6.8em</option>
								<option value="6.9em">6.9em</option>
								<option value="7em">7em</option>
								<option value="7.1em">7.1em</option>
								<option value="7.2em">7.2em</option>
								<option value="7.3em">7.3em</option>
								<option value="7.4em">7.4em</option>
								<option value="7.5em">7.5em</option>
								<option value="7.6em">7.6em</option>
								<option value="7.7em">7.7em</option>
								<option value="7.8em">7.8em</option>
								<option value="7.9em">7.9em</option>
								<option value="8em">8em</option>
								<option value="8.1em">8.1em</option>
								<option value="8.2em">8.2em</option>
								<option value="8.3em">8.3em</option>
								<option value="8.4em">8.4em</option>
								<option value="8.5em">8.5em</option>
								<option value="8.6em">8.6em</option>
								<option value="8.7em">8.7em</option>
								<option value="8.8em">8.8em</option>
								<option value="8.9em">8.9em</option>
								<option value="9em">9em</option>
								<option value="9.1em">9.1em</option>
								<option value="9.2em">9.2em</option>
								<option value="9.3em">9.3em</option>
								<option value="9.4em">9.4em</option>
								<option value="9.5em">9.5em</option>
								<option value="9.6em">9.6em</option>
								<option value="9.7em">9.7em</option>
								<option value="9.8em">9.8em</option>
								<option value="9.9em">9.9em</option>
								<option value="10em">10em</option>
								<option value="10.1em">10.1em</option>
								<option value="10.2em">10.2em</option>
								<option value="10.3em">10.3em</option>
								<option value="10.4em">10.4em</option>
								<option value="10.5em">10.5em</option>
								<option value="10.6em">10.6em</option>
								<option value="10.7em">10.7em</option>
								<option value="10.8em">10.8em</option>
								<option value="10.9em">10.9em</option>
								<option value="11em">11em</option>
								<option value="11.1em">11.1em</option>
								<option value="11.2em">11.2em</option>
								<option value="11.3em">11.3em</option>
								<option value="11.4em">11.4em</option>
								<option value="11.5em">11.5em</option>
								<option value="11.6em">11.6em</option>
								<option value="11.7em">11.7em</option>
								<option value="11.8em">11.8em</option>
								<option value="11.9em">11.9em</option>
								<option value="12em">12em</option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-font-color"><liferay-ui:message key="color" /></label>

							<input id="lfr-font-color" name="lfr-font-color" class="text-input" type="text" size="9" value="" />	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-font-align"><liferay-ui:message key="alignment" /></label>

							<select id="lfr-font-align" name="lfr-font-align">
								<option value=""></option>
								<option value="justify"><liferay-ui:message key="justify" /></option>
								<option value="left"><liferay-ui:message key="left" /></option>
								<option value="right"><liferay-ui:message key="right" /></option>
								<option value="center"><liferay-ui:message key="center" /></option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-font-decoration"><liferay-ui:message key="text-decoration" /></label>

							<select id="lfr-font-decoration" name="lfr-font-decoration">
								<option value=""></option>
								<option value="none"><liferay-ui:message key="none" /></option>
								<option value="underline"><liferay-ui:message key="underline" /></option>
								<option value="overline"><liferay-ui:message key="overline" /></option>
								<option value="line-through"><liferay-ui:message key="strikethrough" /></option>
							</select>	
						</span>
					</span>
				</div>

				<div class="extra aui-form-column">
					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-font-space"><liferay-ui:message key="word-spacing" /></label>

							<select id="lfr-font-space" name="lfr-font-space">
								<option value=""></option>
								<option value="-1em">-1em</option>
								<option value="-0.95em">-0.95em</option>
								<option value="-0.9em">-0.9em</option>
								<option value="-0.85em">-0.85em</option>
								<option value="-0.8em">-0.8em</option>
								<option value="-0.75em">-0.75em</option>
								<option value="-0.7em">-0.7em</option>
								<option value="-0.65em">-0.65em</option>
								<option value="-0.6em">-0.6em</option>
								<option value="-0.55em">-0.55em</option>
								<option value="-0.5em">-0.5em</option>
								<option value="-0.45em">-0.45em</option>
								<option value="-0.4em">-0.4em</option>
								<option value="-0.35em">-0.35em</option>
								<option value="-0.3em">-0.3em</option>
								<option value="-0.25em">-0.25em</option>
								<option value="-0.2em">-0.2em</option>
								<option value="-0.15em">-0.15em</option>
								<option value="-0.1em">-0.1em</option>
								<option value="-0.05em">-0.05em</option>
								<option value="normal">normal</option>
								<option value="0.05em">0.05em</option>
								<option value="0.1em">0.1em</option>
								<option value="0.15em">0.15em</option>
								<option value="0.2em">0.2em</option>
								<option value="0.25em">0.25em</option>
								<option value="0.3em">0.3em</option>
								<option value="0.35em">0.35em</option>
								<option value="0.4em">0.4em</option>
								<option value="0.45em">0.45em</option>
								<option value="0.5em">0.5em</option>
								<option value="0.55em">0.55em</option>
								<option value="0.6em">0.6em</option>
								<option value="0.65em">0.65em</option>
								<option value="0.7em">0.7em</option>
								<option value="0.75em">0.75em</option>
								<option value="0.8em">0.8em</option>
								<option value="0.85em">0.85em</option>
								<option value="0.9em">0.9em</option>
								<option value="0.95em">0.95em</option>
								<option value="1em">1em</option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-font-leading"><liferay-ui:message key="line-height" /></label>

							<select id="lfr-font-leading" name="lfr-font-leading">
								<option value=""></option>
								<option value="0.1em">0.1em</option>
								<option value="0.2em">0.2em</option>
								<option value="0.3em">0.3em</option>
								<option value="0.4em">0.4em</option>
								<option value="0.5em">0.5em</option>
								<option value="0.6em">0.6em</option>
								<option value="0.7em">0.7em</option>
								<option value="0.8em">0.8em</option>
								<option value="0.9em">0.9em</option>
								<option value="1em">1em</option>
								<option value="1.1em">1.1em</option>
								<option value="1.2em">1.2em</option>
								<option value="1.3em">1.3em</option>
								<option value="1.4em">1.4em</option>
								<option value="1.5em">1.5em</option>
								<option value="1.6em">1.6em</option>
								<option value="1.7em">1.7em</option>
								<option value="1.8em">1.8em</option>
								<option value="1.9em">1.9em</option>
								<option value="2em">2em</option>
								<option value="2.1em">2.1em</option>
								<option value="2.2em">2.2em</option>
								<option value="2.3em">2.3em</option>
								<option value="2.4em">2.4em</option>
								<option value="2.5em">2.5em</option>
								<option value="2.6em">2.6em</option>
								<option value="2.7em">2.7em</option>
								<option value="2.8em">2.8em</option>
								<option value="2.9em">2.9em</option>
								<option value="3em">3em</option>
								<option value="3.1em">3.1em</option>
								<option value="3.2em">3.2em</option>
								<option value="3.3em">3.3em</option>
								<option value="3.4em">3.4em</option>
								<option value="3.5em">3.5em</option>
								<option value="3.6em">3.6em</option>
								<option value="3.7em">3.7em</option>
								<option value="3.8em">3.8em</option>
								<option value="3.9em">3.9em</option>
								<option value="4em">4em</option>
								<option value="4.1em">4.1em</option>
								<option value="4.2em">4.2em</option>
								<option value="4.3em">4.3em</option>
								<option value="4.4em">4.4em</option>
								<option value="4.5em">4.5em</option>
								<option value="4.6em">4.6em</option>
								<option value="4.7em">4.7em</option>
								<option value="4.8em">4.8em</option>
								<option value="4.9em">4.9em</option>
								<option value="5em">5em</option>
								<option value="5.1em">5.1em</option>
								<option value="5.2em">5.2em</option>
								<option value="5.3em">5.3em</option>
								<option value="5.4em">5.4em</option>
								<option value="5.5em">5.5em</option>
								<option value="5.6em">5.6em</option>
								<option value="5.7em">5.7em</option>
								<option value="5.8em">5.8em</option>
								<option value="5.9em">5.9em</option>
								<option value="6em">6em</option>
								<option value="6.1em">6.1em</option>
								<option value="6.2em">6.2em</option>
								<option value="6.3em">6.3em</option>
								<option value="6.4em">6.4em</option>
								<option value="6.5em">6.5em</option>
								<option value="6.6em">6.6em</option>
								<option value="6.7em">6.7em</option>
								<option value="6.8em">6.8em</option>
								<option value="6.9em">6.9em</option>
								<option value="7em">7em</option>
								<option value="7.1em">7.1em</option>
								<option value="7.2em">7.2em</option>
								<option value="7.3em">7.3em</option>
								<option value="7.4em">7.4em</option>
								<option value="7.5em">7.5em</option>
								<option value="7.6em">7.6em</option>
								<option value="7.7em">7.7em</option>
								<option value="7.8em">7.8em</option>
								<option value="7.9em">7.9em</option>
								<option value="8em">8em</option>
								<option value="8.1em">8.1em</option>
								<option value="8.2em">8.2em</option>
								<option value="8.3em">8.3em</option>
								<option value="8.4em">8.4em</option>
								<option value="8.5em">8.5em</option>
								<option value="8.6em">8.6em</option>
								<option value="8.7em">8.7em</option>
								<option value="8.8em">8.8em</option>
								<option value="8.9em">8.9em</option>
								<option value="9em">9em</option>
								<option value="9.1em">9.1em</option>
								<option value="9.2em">9.2em</option>
								<option value="9.3em">9.3em</option>
								<option value="9.4em">9.4em</option>
								<option value="9.5em">9.5em</option>
								<option value="9.6em">9.6em</option>
								<option value="9.7em">9.7em</option>
								<option value="9.8em">9.8em</option>
								<option value="9.9em">9.9em</option>
								<option value="10em">10em</option>
								<option value="10.1em">10.1em</option>
								<option value="10.2em">10.2em</option>
								<option value="10.3em">10.3em</option>
								<option value="10.4em">10.4em</option>
								<option value="10.5em">10.5em</option>
								<option value="10.6em">10.6em</option>
								<option value="10.7em">10.7em</option>
								<option value="10.8em">10.8em</option>
								<option value="10.9em">10.9em</option>
								<option value="11em">11em</option>
								<option value="11.1em">11.1em</option>
								<option value="11.2em">11.2em</option>
								<option value="11.3em">11.3em</option>
								<option value="11.4em">11.4em</option>
								<option value="11.5em">11.5em</option>
								<option value="11.6em">11.6em</option>
								<option value="11.7em">11.7em</option>
								<option value="11.8em">11.8em</option>
								<option value="11.9em">11.9em</option>
								<option value="12em">12em</option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-font-tracking"><liferay-ui:message key="letter-spacing" /></label>

							<select id="lfr-font-tracking" name="lfr-font-tracking">
								<option value=""></option>
								<option value="-10px">-10px</option>
								<option value="-9px">-9px</option>
								<option value="-8px">-8px</option>
								<option value="-7px">-7px</option>
								<option value="-6px">-6px</option>
								<option value="-5px">-5px</option>
								<option value="-4px">-4px</option>
								<option value="-3px">-3px</option>
								<option value="-2px">-2px</option>
								<option value="-1px">-1px</option>
								<option value="0">0</option>
								<option value="1px">1px</option>
								<option value="2px">2px</option>
								<option value="3px">3px</option>
								<option value="4px">4px</option>
								<option value="5px">5px</option>
								<option value="6px">6px</option>
								<option value="7px">7px</option>
								<option value="8px">8px</option>
								<option value="9px">9px</option>
								<option value="10px">10px</option>
								<option value="11px">11px</option>
								<option value="12px">12px</option>
								<option value="13px">13px</option>
								<option value="14px">14px</option>
								<option value="15px">15px</option>
								<option value="16px">16px</option>
								<option value="17px">17px</option>
								<option value="18px">18px</option>
								<option value="19px">19px</option>
								<option value="20px">20px</option>
								<option value="21px">21px</option>
								<option value="22px">22px</option>
								<option value="23px">23px</option>
								<option value="24px">24px</option>
								<option value="25px">25px</option>
								<option value="26px">26px</option>
								<option value="27px">27px</option>
								<option value="28px">28px</option>
								<option value="29px">29px</option>
								<option value="30px">30px</option>
								<option value="31px">31px</option>
								<option value="32px">32px</option>
								<option value="33px">33px</option>
								<option value="34px">34px</option>
								<option value="35px">35px</option>
								<option value="36px">36px</option>
								<option value="37px">37px</option>
								<option value="38px">38px</option>
								<option value="39px">39px</option>
								<option value="40px">40px</option>
								<option value="41px">41px</option>
								<option value="42px">42px</option>
								<option value="43px">43px</option>
								<option value="44px">44px</option>
								<option value="45px">45px</option>
								<option value="46px">46px</option>
								<option value="47px">47px</option>
								<option value="48px">48px</option>
								<option value="49px">49px</option>
								<option value="50px">50px</option>
							</select>	
						</span>
					</span>
				</div>
			</fieldset>

			<fieldset class="aui-block-labels" id="background-styles">
				<legend><liferay-ui:message key="background-styles" /></legend>

				<span class="aui-field">
					<span class="aui-field-content">
						<label for="lfr-bg-color"><liferay-ui:message key="background-color" /></label>

						<input class="text-input" id="lfr-bg-color" name="lfr-bg-color" size="9" type="text" value="#f00" />	
					</span>
				</span>
			</fieldset>

			<fieldset class="aui-block-labels" id="border-styles">
				<legend><liferay-ui:message key="border-styling" /></legend>

				<fieldset class="aui-form-column" id="lfr-border-width">
					<legend><liferay-ui:message key="border-width" /></legend>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-use-for-all-width">
								<liferay-ui:message key="same-for-all" />

								<input checked="checked" class="lfr-use-for-all" id="lfr-use-for-all-width" type="checkbox" />
							</label>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-border-width-top"><liferay-ui:message key="top" /></label>

							<input class="text-input" id="lfr-border-width-top" type="text" />

							<select id="lfr-border-width-top-unit">
								<option value="%">%</option>
								<option value="px">px</option>
								<option value="em">em</option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-border-width-right"><liferay-ui:message key="right" /></label>

							<input id="lfr-border-width-right" class="text-input" type="text" />

							<select id="lfr-border-width-right-unit">
								<option value="%">%</option>
								<option value="px">px</option>
								<option value="em">em</option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-border-width-bottom"><liferay-ui:message key="bottom" /></label>

							<input id="lfr-border-width-bottom" class="text-input" type="text" />

							<select id="lfr-border-width-bottom-unit">
								<option value="%">%</option>
								<option value="px">px</option>
								<option value="em">em</option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-border-width-width"><liferay-ui:message key="left" /></label>

							<input id="lfr-border-width-left" class="text-input" type="text" />

							<select id="lfr-border-width-left-unit">
								<option value="%">%</option>
								<option value="px">px</option>
								<option value="em">em</option>
							</select>	
						</span>
					</span>
				</fieldset>

				<fieldset class="aui-form-column" id="lfr-border-style">
					<legend><liferay-ui:message key="border-style" /></legend>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-use-for-all-style">
								<liferay-ui:message key="same-for-all" />

								<input checked="checked" class="lfr-use-for-all" id="lfr-use-for-all-style" type="checkbox" />
							</label>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-border-style-top"><liferay-ui:message key="top" /></label>

							<select id="lfr-border-style-top">
								<option value=""></option>
								<option value="dashed"><liferay-ui:message key="dashed" /></option>
								<option value="double"><liferay-ui:message key="double" /></option>
								<option value="dotted"><liferay-ui:message key="dotted" /></option>
								<option value="groove"><liferay-ui:message key="groove" /></option>
								<option value="hidden"><liferay-ui:message key="hidden[css]" /></option>
								<option value="inset"><liferay-ui:message key="inset" /></option>
								<option value="outset"><liferay-ui:message key="outset" /></option>
								<option value="ridge"><liferay-ui:message key="ridge" /></option>
								<option value="solid"><liferay-ui:message key="solid" /></option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-border-style-right"><liferay-ui:message key="right" /></label>

							<select id="lfr-border-style-right">
								<option value=""></option>
								<option value="dashed"><liferay-ui:message key="dashed" /></option>
								<option value="double"><liferay-ui:message key="double" /></option>
								<option value="dotted"><liferay-ui:message key="dotted" /></option>
								<option value="groove"><liferay-ui:message key="groove" /></option>
								<option value="hidden"><liferay-ui:message key="hidden[css]" /></option>
								<option value="inset"><liferay-ui:message key="inset" /></option>
								<option value="outset"><liferay-ui:message key="outset" /></option>
								<option value="ridge"><liferay-ui:message key="ridge" /></option>
								<option value="solid"><liferay-ui:message key="solid" /></option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-border-style-bottom"><liferay-ui:message key="bottom" /></label>

							<select id="lfr-border-style-bottom">
								<option value=""></option>
								<option value="dashed"><liferay-ui:message key="dashed" /></option>
								<option value="double"><liferay-ui:message key="double" /></option>
								<option value="dotted"><liferay-ui:message key="dotted" /></option>
								<option value="groove"><liferay-ui:message key="groove" /></option>
								<option value="hidden"><liferay-ui:message key="hidden[css]" /></option>
								<option value="inset"><liferay-ui:message key="inset" /></option>
								<option value="outset"><liferay-ui:message key="outset" /></option>
								<option value="ridge"><liferay-ui:message key="ridge" /></option>
								<option value="solid"><liferay-ui:message key="solid" /></option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-border-style-left"><liferay-ui:message key="left" /></label>

							<select id="lfr-border-style-left">
								<option value=""></option>
								<option value="dashed"><liferay-ui:message key="dashed" /></option>
								<option value="double"><liferay-ui:message key="double" /></option>
								<option value="dotted"><liferay-ui:message key="dotted" /></option>
								<option value="groove"><liferay-ui:message key="groove" /></option>
								<option value="hidden"><liferay-ui:message key="hidden[css]" /></option>
								<option value="inset"><liferay-ui:message key="inset" /></option>
								<option value="outset"><liferay-ui:message key="outset" /></option>
								<option value="ridge"><liferay-ui:message key="ridge" /></option>
								<option value="solid"><liferay-ui:message key="solid" /></option>
							</select>	
						</span>
					</span>
				</fieldset>

				<fieldset class="aui-form-column" id="lfr-border-color">
					<legend><liferay-ui:message key="border-color" /></legend>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-use-for-all-color">
								<liferay-ui:message key="same-for-all" />

								<input checked="checked" class="lfr-use-for-all" id="lfr-use-for-all-color" type="checkbox" />
							</label>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-border-color-top"><liferay-ui:message key="top" /></label>

							<input class="text-input" id="lfr-border-color-top" type="text" />	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-border-color-right"><liferay-ui:message key="right" /></label>

							<input class="text-input" id="lfr-border-color-right" type="text" />	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-border-color-bottom"><liferay-ui:message key="bottom" /></label>

							<input class="text-input" id="lfr-border-color-bottom" type="text" />	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-border-color-left"><liferay-ui:message key="left" /></label>

							<input class="text-input" id="lfr-border-color-left" type="text" />	
						</span>
					</span>
				</fieldset>
			</fieldset>

			<fieldset class="spacing aui-block-labels" id="spacing-styles">
				<legend><liferay-ui:message key="spacing" /></legend>

				<fieldset class="aui-form-column" id="lfr-padding">
					<legend><liferay-ui:message key="padding" /></legend>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-use-for-all-padding">
								<liferay-ui:message key="same-for-all" />

								<input checked="checked" class="lfr-use-for-all" id="lfr-use-for-all-padding" type="checkbox" />
							</label>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-padding-top"><liferay-ui:message key="top" /></label>

							<input class="text-input" id="lfr-padding-top" type="text" />

							<select id="lfr-padding-top-unit">
								<option value="%">%</option>
								<option value="px">px</option>
								<option value="em">em</option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-padding-right"><liferay-ui:message key="right" /></label>

							<input class="text-input" id="lfr-padding-right" type="text" />

							<select id="lfr-padding-right-unit">
								<option value="%">%</option>
								<option value="px">px</option>
								<option value="em">em</option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-padding-bottom"><liferay-ui:message key="bottom" /></label>

							<input class="text-input" id="lfr-padding-bottom" type="text" />

							<select id="lfr-padding-bottom-unit">
								<option value="%">%</option>
								<option value="px">px</option>
								<option value="em">em</option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-padding-left"><liferay-ui:message key="left" /></label>

							<input class="text-input" id="lfr-padding-left" type="text" />

							<select id="lfr-padding-left-unit">
								<option value="%">%</option>
								<option value="px">px</option>
								<option value="em">em</option>
							</select>	
						</span>
					</span>
				</fieldset>

				<fieldset class="aui-form-column" id="lfr-margin">
					<legend><liferay-ui:message key="margin" /></legend>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-use-for-all-margin">
								<liferay-ui:message key="same-for-all" />

								<input checked="checked" class="lfr-use-for-all" id="lfr-use-for-all-margin" type="checkbox" />
							</label>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-margin-top"><liferay-ui:message key="top" /></label>

							<input class="text-input" id="lfr-margin-top" type="text" />

							<select id="lfr-margin-top-unit">
								<option value="%">%</option>
								<option value="px">px</option>
								<option value="em">em</option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-margin-right"><liferay-ui:message key="right" /></label>

							<input class="text-input" id="lfr-margin-right" type="text" />

							<select id="lfr-margin-right-unit">
								<option value="%">%</option>
								<option value="px">px</option>
								<option value="em">em</option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-margin-bottom"><liferay-ui:message key="bottom" /></label>

							<input class="text-input" id="lfr-margin-bottom" type="text" />

							<select id="lfr-margin-bottom-unit">
								<option value="%">%</option>
								<option value="px">px</option>
								<option value="em">em</option>
							</select>	
						</span>
					</span>

					<span class="aui-field">
						<span class="aui-field-content">
							<label for="lfr-margin-left"><liferay-ui:message key="left" /></label>

							<input class="text-input" id="lfr-margin-left" type="text" />

							<select id="lfr-margin-left-unit">
								<option value="%">%</option>
								<option value="px">px</option>
								<option value="em">em</option>
							</select>	
						</span>
					</span>
				</fieldset>
			</fieldset>

			<fieldset class="aui-block-labels" id="css-styling">
				<legend><liferay-ui:message key="advanced-css-styling" /></legend>

				<span class="aui-field">
					<span class="aui-field-content">
						<label for="lfr-custom-css"><liferay-ui:message key="enter-your-custom-css" /></label>

						<textarea id="lfr-custom-css"></textarea>	
					</span>
				</span>
			</fieldset>

			<fieldset class="aui-block-labels" id="wap-styling">
				<legend><liferay-ui:message key="wap-styling" /></legend>

				<span class="aui-field">
					<span class="aui-field-content">
						<label for="lfr-wap-title"><liferay-ui:message key="title" /></label>

						<input class="text-input" id="lfr-wap-title" type="text" />	
					</span>
				</span>

				<span class="aui-field">
					<span class="aui-field-content">
						<label for="lfr-wap-initial-window-state"><liferay-ui:message key="initial-window-state" /></label>

						<select id="lfr-wap-initial-window-state">
							<option value="MINIMIZED"><liferay-ui:message key="minimized" /></option>
							<option value="NORMAL"><liferay-ui:message key="normal" /></option>
						</select>	
					</span>
				</span>
			</fieldset>

			<div class="aui-button-holder">
				<input id="lfr-lookfeel-save" type="button" value="<liferay-ui:message key="save" />" />
				<input id="lfr-lookfeel-reset" type="button" value="<liferay-ui:message key="reset" />" />
			</div>
		</div>

		</form>
	</div>
</div>