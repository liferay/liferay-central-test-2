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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String languageId = LanguageUtil.getLanguageId(request);

long groupId = GetterUtil.getLong((String)request.getAttribute(WebKeys.JOURNAL_ARTICLE_GROUP_ID));

Element el = (Element)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL);
boolean elRepeatable = GetterUtil.getBoolean((String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_REPEATABLE));
boolean elRepeatablePrototype = GetterUtil.getBoolean((String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_REPEATABLE_PROTOTYPE));
Integer depth = (Integer)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_DEPTH);
IntegerWrapper count = (IntegerWrapper)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_COUNT);
String elContent = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_CONTENT);
String elInstanceId = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_INSTANCE_ID);
String elLanguageId = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_LANGUAGE_ID);
String elName = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_NAME);
String elParentStructureId = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_PARENT_ID);
String elType = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_TYPE);

HashMap<String, String> metaDataEl = (HashMap<String, String>)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_META_DATA);
String metaData = _buildMetaDataHTMLAttributes(metaDataEl, elName);

String elDisplayAsTooltip = metaDataEl.get("displayAsTooltip");
String elInstructions = metaDataEl.get("instructions");
String elLabel = metaDataEl.get("label");
String elPredefinedValue = metaDataEl.get("predefinedValue");

boolean displayAsTooltip = false;

StringBuffer css = new StringBuffer();

StringBuffer parentStructureData = new StringBuffer();

if (Validator.isNull(elParentStructureId)) {
	css.append("structure-field");
}

if (elRepeatable && !elRepeatablePrototype) {
	css.append(" repeated-field ");
}

if (Validator.isNotNull(elDisplayAsTooltip)) {
	displayAsTooltip = Boolean.parseBoolean(elDisplayAsTooltip);
}

if (Validator.isNull(elLabel)) {
	elLabel = elName;
}

if (Validator.isNull(elPredefinedValue)) {
	elPredefinedValue = StringPool.BLANK;
}

if (Validator.isNotNull(elParentStructureId)) {
	parentStructureData.append("data-component-parentStructureId='");
	parentStructureData.append(elParentStructureId);
	parentStructureData.append("'");

	css.append(" parent-structure-field ");
}

if (Validator.isNull(elContent) && Validator.isNotNull(elPredefinedValue)) {
	elContent = elPredefinedValue;
}

Element contentEl = (Element)request.getAttribute(WebKeys.JOURNAL_ARTICLE_CONTENT_EL);
%>

<li class="<%= css.toString().trim()  %>" <%= parentStructureData.toString() %> data-component-instanceId='<%= elInstanceId %>' data-component-type='<%= elType %>' data-component-name='<%= elName %>' data-component-repeatable='<%= elRepeatable %>' <%= metaData %>>
	<span class="journal-article-close"></span>
	<span class="folder">
		<div class="field-container">
			<input class="journal-article-localized" id="<portlet:namespace />Localized" type="hidden" value='<%= !elLanguageId.equals(StringPool.BLANK) ? languageId : "false" %>' />

			<div class="journal-article-move-handler"></div>

			<label class="journal-article-field-label">
				<span><%= elLabel %></span>
				<c:if test='<%= (Validator.isNotNull(elInstructions) && displayAsTooltip) %>'>
					<img align="top" class="journal-article-instructions-container" src="/html/themes/classic/images/portlet/help.png" />
				</c:if>
			</label>

			<div class="journal-article-component-container">
				<c:if test='<%= elType.equals("text") %>'>
					<input class="principal-field-element lfr-input-text" type="text" value="<%= elContent %>" size="75" />
				</c:if>

				<c:if test='<%= elType.equals("text_box") %>'>
					<textarea class="principal-field-element lfr-textarea" rows="10" cols="80"><%= elContent %></textarea>
				</c:if>

				<c:if test='<%= elType.equals("text_area") %>'>
					<script type="text/javascript">
						function <portlet:namespace />initEditor<%= elName %>() {
							return "<%= UnicodeFormatter.toString(elContent) %>";
						}
					</script>

					<liferay-ui:input-editor
						name='<%= renderResponse.getNamespace() + "structure_el_" + elName + "_content" %>'
						editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>"
						toolbarSet="liferay-article"
						initMethod='<%= renderResponse.getNamespace() + "initEditor" + elName %>'
						onChangeMethod='<%= renderResponse.getNamespace() + "editorContentChanged" %>'
						height="250"
						width="600"
					/>
				</c:if>

				<c:if test='<%= elType.equals("image") %>'>
					<input class="principal-field-element lfr-input-text flexible" class="journal-image-field" size="75" type="file" />
					<br />
					<c:if test="<%= Validator.isNotNull(elContent) %>">
						<span class="journal-image-show-hide">
							[ <a class="journal-image-link" href="javascript:void(0);"><span class="show-label"><liferay-ui:message key="show" /></span><span class="hide-label"><liferay-ui:message key="hide" /></span></a> ]
						</span>
						<div class="journal-image-preview">
							<input class="journal-image-content" type="hidden" value="<%= elContent %>" />
							<input class="journal-image-delete" type="hidden" value="" />

							<input class="journal-image-delete-btn" type="button" value="<liferay-ui:message key="delete" />" /><br /><br />

							<div class="journal-image-wrapper results-grid">
								<img class="journal-image" hspace="0" src="<%= elContent %>" vspace="0" />
							</div>
						</div>
					</c:if>
				</c:if>

				<c:if test='<%= elType.equals("image_gallery") %>'>
					<input class="principal-field-element lfr-input-text journal-imagegallery-text" type="text" value="<%= elContent %>" size="75" />
					<input class="journal-imagegallery-button" type="button" value="<liferay-ui:message key="select" />" data-imagegallery-url="<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_image_gallery" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>" />
				</c:if>

				<c:if test='<%= elType.equals("document_library") %>'>
					<input class="principal-field-element lfr-input-text journal-documentlibrary-text" type="text" value="<%= elContent %>" size="75" />
					<input class="journal-documentlibrary-button" type="button" value="<liferay-ui:message key="select" />" data-documentlibrary-url="<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_document_library" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>" />
				</c:if>

				<c:if test='<%= elType.equals("boolean") %>'>
					<div class="journal-subfield">
						<input class="principal-field-element" type="checkbox" <%= elContent.equals("true") ? "checked" : "" %> id="<%= elName %>" />
						<label class="journal-article-field-label" for="<%= elName %>"><span><%= elLabel %></span></label>
					</div>
				</c:if>

				<c:if test='<%= elType.equals("list") %>'>

					<div class="journal-list-subfield">
						<select class="principal-field-element">
							<%
							Iterator itr = el.elements().iterator();

							while (itr.hasNext()) {
								Element child = (Element)itr.next();

								String listElName = JS.decodeURIComponent(child.attributeValue("name", StringPool.BLANK));
								String listElValue = JS.decodeURIComponent(child.attributeValue("type", StringPool.BLANK));

								if (listElName.equals("") && listElValue.equals("")) {
									continue;
								}
							%>

								<option <%= elContent.equals(listElName) ? "selected" : "" %> value="<%= listElValue %>"><%= listElName %></option>

							<%
							}
							%>
						</select>

						<span class="journal-icon-button journal-delete-field">
							<liferay-ui:icon image="delete" /><liferay-ui:message key="delete-selected-value" />
						</span>

						<div class="journal-edit-field-control">
							<br /><br />
							<input class="journal-list-key" type="text" value="<liferay-ui:message key="new-item" />" title="<liferay-ui:message key="new-item" />" size="15" />
							<input class="journal-list-value" type="text" value="value" title="<liferay-ui:message key="item-value" />" size="15" />
							<span class="journal-icon-button journal-add-field"><liferay-ui:icon image="add" /> <liferay-ui:message key="add-to-list" /></span>
						</div>
					</div>

				</c:if>

				<c:if test='<%= elType.equals("multi-list") %>'>

					<div class="journal-list-subfield">
						<select class="principal-field-element" multiple="true">
							<%
							Iterator itr1 = el.elements().iterator();

							while (itr1.hasNext()) {
								Element child = (Element)itr1.next();

								String listElName = JS.decodeURIComponent(child.attributeValue("name", StringPool.BLANK));
								String listElValue = JS.decodeURIComponent(child.attributeValue("type", StringPool.BLANK));

								boolean contains = false;

								Element dynConEl = contentEl.element("dynamic-content");

								if (dynConEl != null) {
									Iterator itr2 = dynConEl.elements("option").iterator();

									while (itr2.hasNext()) {
										Element option = (Element)itr2.next();

										if (listElName.equals(option.getText())) {
											contains = true;
										}
									}
								}

								if (listElName.equals(StringPool.BLANK) && listElValue.equals(StringPool.BLANK)) {
									continue;
								}
							%>

								<option <%= contains ? "selected" : "" %> value="<%= listElValue %>"><%= listElName %></option>

							<%
							}
							%>
						</select>

						<span class="journal-icon-button journal-delete-field">
							<liferay-ui:icon image="delete" /><liferay-ui:message key="delete-selected-value" />
						</span>

						<div class="journal-edit-field-control">
							<br /><br />
							<input class="journal-list-key" type="text" value="<liferay-ui:message key="new-item" />" title="<liferay-ui:message key="new-item" />" size="15" />
							<input class="journal-list-value" type="text" value="value" title="<liferay-ui:message key="item-value" />" size="15" />
							<span class="journal-icon-button journal-add-field"><liferay-ui:icon image="add" /> <liferay-ui:message key="add-to-list" /></span>
						</div>
					</div>

				</c:if>

				<c:if test='<%= elType.equals("link_to_layout") %>'>
					<select class="principal-field-element" id="<portlet:namespace />structure_el<%= count.getValue() %>_content" onChange="<portlet:namespace />contentChanged();">
						<option value=""></option>

						<%
						boolean privateLayout = false;

						LayoutLister layoutLister = new LayoutLister();

						LayoutView layoutView = null;

						List layoutList = null;
						%>

						<%@ include file="/html/portlet/journal/edit_article_content_xsd_el_link_to_layout.jspf" %>

						<%
						privateLayout = true;
						%>

						<%@ include file="/html/portlet/journal/edit_article_content_xsd_el_link_to_layout.jspf" %>

					</select>
				</c:if>
			</div>

			<div class="journal-article-required-message portlet-msg-error"><liferay-ui:message key="this-field-is-required" /></div>

			<c:if test='<%= (Validator.isNotNull(elInstructions) && !displayAsTooltip) %>'>
				<div class="journal-article-instructions-container journal-article-instructions-message portlet-msg-info">
					<%= elInstructions %>
				</div>
			</c:if>

			<div class="journal-article-buttons">
				<input type="button" value='<liferay-ui:message key="edit-options" />' class="edit-button">
				<input type="button" class="repeatable-button" value="<liferay-ui:message key="repeat" />" />
			</div>

			<script type="text/javascript">
				<portlet:namespace />count++;
			</script>

			<c:if test='<%= elRepeatable %>'>
				<span class="repeatable-field-image"><liferay-ui:icon image="add" /></span>
			</c:if>
		</div>
<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.edit_article_content_xsd_el.jsp";

private String _buildMetaDataHTMLAttributes(HashMap<String, String> metaDataEl, String elName) {
	StringBuffer sb = new StringBuffer();

	Iterator<String> keys = metaDataEl.keySet().iterator();

	while (keys.hasNext()) {
		String name = keys.next();
		String content = metaDataEl.get(name);

		sb.append("data-component-");
		sb.append(name);
		sb.append("='");
		sb.append(HtmlUtil.escapeAttribute(content));
		sb.append("' ");
	}

	return sb.toString();
}
%>