<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
IntegerWrapper count = (IntegerWrapper)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_COUNT);
Integer depth = (Integer)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_DEPTH);

String elInstanceId = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_INSTANCE_ID);
String elName = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_NAME);
String elType = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_TYPE);
String elIndexType = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_INDEX_TYPE);
boolean elRepeatable = GetterUtil.getBoolean((String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_REPEATABLE));
boolean elRepeatablePrototype = GetterUtil.getBoolean((String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_REPEATABLE_PROTOTYPE));
String elContent = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_CONTENT);
String elLanguageId = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_LANGUAGE_ID);
String elParentStructureId = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_PARENT_ID);

Map<String, String> elMetaData = (Map<String, String>)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_META_DATA);

String metaData = _buildMetaDataHTMLAttributes(elMetaData, elName);

String elDisplayAsTooltip = elMetaData.get("displayAsTooltip");
String elInstructions = elMetaData.get("instructions");
String elLabel = elMetaData.get("label");
String elPredefinedValue = elMetaData.get("predefinedValue");

boolean displayAsTooltip = false;

if (Validator.isNotNull(elDisplayAsTooltip)) {
	displayAsTooltip = GetterUtil.getBoolean(elDisplayAsTooltip);
}

if (Validator.isNull(elLabel)) {
	elLabel = elName;
}

if (Validator.isNull(elPredefinedValue)) {
	elPredefinedValue = StringPool.BLANK;
}

StringBundler css = new StringBundler(2);

if (!elRepeatablePrototype) {
	css.append(" repeated-field ");
}

String parentStructureData = StringPool.BLANK;

if (Validator.isNotNull(elParentStructureId)) {
	parentStructureData = "dataParentStructureId='".concat(elParentStructureId).concat("'");

	css.append(" parent-structure-field ");
}

if (Validator.isNull(elContent) && Validator.isNotNull(elPredefinedValue)) {
	elContent = elPredefinedValue;
}

Element contentEl = (Element)request.getAttribute(WebKeys.JOURNAL_ARTICLE_CONTENT_EL);
%>

<li class="structure-field <%= css.toString().trim() %>" <%= parentStructureData %> dataInstanceId='<%= elInstanceId %>' dataName='<%= elName %>' dataRepeatable='<%= elRepeatable %>' dataType='<%= elType %>' dataIndexType='<%= elIndexType %>' <%= metaData %>>
	<span class="journal-article-close"></span>

	<span class="folder">
		<div class="field-container">
			<input class="journal-article-localized" type="hidden" value='<%= !elLanguageId.equals(StringPool.BLANK) ? languageId : "false" %>' />

			<div class="journal-article-move-handler"></div>

			<label class="journal-article-field-label">
				<span><%= elLabel %></span>

				<c:if test="<%= (Validator.isNotNull(elInstructions) && displayAsTooltip) %>">
					<img align="top" class="journal-article-instructions-container" src="/html/themes/classic/images/portlet/help.png" />
				</c:if>
			</label>

			<div class="journal-article-component-container">
				<c:if test='<%= elType.equals("text") %>'>
					<aui:input cssClass="lfr-input-text-container" label="" name="text" size="55" type="text" value="<%= elContent %>" />
				</c:if>

				<c:if test='<%= elType.equals("text_box") %>'>
					<aui:input cssClass="lfr-textarea-container" cols="60" label="" name="textArea" rows="10" type="textarea" value="<%= elContent %>" />
				</c:if>

				<c:if test='<%= elType.equals("text_area") %>'>
					<liferay-ui:input-editor
						name='<%= renderResponse.getNamespace() + "structure_el_" + elInstanceId + "_content" %>'
						editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>"
						toolbarSet="liferay-article"
						initMethod='<%= renderResponse.getNamespace() + "initEditor" + elInstanceId %>'
						onChangeMethod='<%= renderResponse.getNamespace() + "editorContentChanged" %>'
						height="250"
						width="500"
					/>

					<aui:script>
						function <portlet:namespace />initEditor<%= elInstanceId %>() {
							return "<%= UnicodeFormatter.toString(elContent) %>";
						}
					</aui:script>
				</c:if>

				<c:if test='<%= elType.equals("image") %>'>
					<aui:input cssClass="journal-image-field lfr-input-text-container flexible" label="" name="image"  size="40" type="file" />

					<br />

					<c:if test="<%= Validator.isNotNull(elContent) %>">
						<span class="journal-image-show-hide">
							[ <aui:a cssClass="journal-image-link" href="javascript:void(0);"><span class="show-label"><liferay-ui:message key="show" /></span><span class="hide-label aui-helper-hidden"><liferay-ui:message key="hide" /></span></aui:a> ]
						</span>

						<div class="journal-image-preview aui-helper-hidden">
							<aui:input name="journalImageContent" type="hidden" value="<%= elContent %>" />

							<aui:input name="journalImageDelete" type="hidden" value="" />

							<aui:input name="journalImageDeleteButton" type="button" value="Delete" />

							<br /><br />

							<div class="journal-image-wrapper results-grid">
								<img class="journal-image" hspace="0" src="<%= elContent %>" vspace="0" />
							</div>
						</div>
					</c:if>
				</c:if>

				<c:if test='<%= elType.equals("image_gallery") %>'>
					<aui:input cssClass="lfr-input-text-container" inlineField="<%= true %>" label="" name="journalImagegallery" size="55" type="text" value="<%= elContent %>" />

					<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="selectIGURL">
						<portlet:param name="struts_action" value="/journal/select_image_gallery" />
						<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
					</portlet:renderURL>

					<aui:button cssClass="journal-imagegallery-button" dataImagegalleryUrl="<%= selectIGURL %>" value="select" />
				</c:if>

				<c:if test='<%= elType.equals("document_library") %>'>
					<aui:input cssClass="lfr-input-text-container" inlineField="<%= true %>" label="" name="journalDocumentlibrary" size="55" type="text" value="<%= elContent %>" />

					<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="selectDLURL">
						<portlet:param name="struts_action" value="/journal/select_document_library" />
						<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
					</portlet:renderURL>

					<aui:button cssClass="journal-documentlibrary-button" dataDocumentlibraryUrl="<%= selectDLURL %>" value="select" />
				</c:if>

				<c:if test='<%= elType.equals("boolean") %>'>
					<div class="journal-subfield">
						<aui:input cssClass="journal-article-field-label" label="<%= elLabel %>" name="<%= elName %>" type="checkbox" value='<%= elContent.equals("true") %>' />
					</div>
				</c:if>

				<c:if test='<%= elType.equals("list") %>'>
					<div class="journal-list-subfield">
						<aui:select label="" name="list">

							<%
							Iterator<Element> itr = el.elements().iterator();

							while (itr.hasNext()) {
								Element child = itr.next();

								String listElName = JS.decodeURIComponent(child.attributeValue("name", StringPool.BLANK));
								String listElValue = JS.decodeURIComponent(child.attributeValue("type", StringPool.BLANK));

								if (Validator.isNull(listElName) && Validator.isNull(listElValue)) {
									continue;
								}
							%>

								<aui:option label="<%= listElName %>" name="<%= listElValue %>" selected="<%= elContent.equals(listElName) %>" />

							<%
							}
							%>

						</aui:select>

						<span class="journal-icon-button journal-delete-field">
							<liferay-ui:icon image="delete" /><liferay-ui:message key="delete-selected-value" />
						</span>

						<div class="journal-edit-field-control">
							<br /><br />

							<input class="journal-list-key" size="15" title="<liferay-ui:message key="new-item" />" type="text" value="<liferay-ui:message key="new-item" />" />

							<input class="journal-list-value" size="15" title="<liferay-ui:message key="item-value" />" type="text" value="value" />

							<span class="journal-icon-button journal-add-field">
								<liferay-ui:icon image="add" /> <liferay-ui:message key="add-to-list" />
							</span>
						</div>
					</div>
				</c:if>

				<c:if test='<%= elType.equals("multi-list") %>'>
					<div class="journal-list-subfield">
						<aui:select label="" multiple="true" name="multiList">

							<%
							Iterator<Element> itr = el.elements().iterator();

							while (itr.hasNext()) {
								Element child = itr.next();

								String listElName = JS.decodeURIComponent(child.attributeValue("name", StringPool.BLANK));
								String listElValue = JS.decodeURIComponent(child.attributeValue("type", StringPool.BLANK));

								boolean contains = false;

								Element dynConEl = contentEl.element("dynamic-content");

								if (dynConEl != null) {
									Iterator itr2 = dynConEl.elements("option").iterator();

									while (itr2.hasNext()) {
										Element option = (Element)itr2.next();

										if (listElValue.equals(option.getText())) {
											contains = true;
										}
									}
								}

								if (Validator.isNull(listElName) && Validator.isNull(listElValue)) {
									continue;
								}
							%>

								<aui:option label="<%= listElName %>" selected="<%= contains %>" value="<%= listElValue %>" />

							<%
							}
							%>

						</aui:select>

						<span class="journal-icon-button journal-delete-field">
							<liferay-ui:icon image="delete" /><liferay-ui:message key="delete-selected-value" />
						</span>

						<div class="journal-edit-field-control">
							<br /><br />

							<input class="journal-list-key" size="15" title="<liferay-ui:message key="new-item" />" type="text" value="<liferay-ui:message key="new-item" />" />

							<input class="journal-list-value" size="15" title="<liferay-ui:message key="item-value" />" type="text" value="value" />

							<span class="journal-icon-button journal-add-field">
								<liferay-ui:icon image="add" /> <liferay-ui:message key="add-to-list" />
							</span>
						</div>
					</div>

				</c:if>

				<c:if test='<%= elType.equals("link_to_layout") %>'>
					<aui:select label="" name='<%= "structure_el" + count.getValue() + "_content" %>' onChange='<%= renderResponse.getNamespace() + "contentChanged();" %>' showEmptyOption="<%= true %>">

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
					</aui:select>
				</c:if>
			</div>

			<div class="journal-article-required-message portlet-msg-error">
				<liferay-ui:message key="this-field-is-required" />
			</div>

			<c:if test='<%= (Validator.isNotNull(elInstructions) && !displayAsTooltip) %>'>
				<div class="journal-article-instructions-container journal-article-instructions-message portlet-msg-info">
					<%= elInstructions %>
				</div>
			</c:if>

			<div class="journal-article-buttons">
				<input class="edit-button" type="button" value="<liferay-ui:message key="edit-options" />">

				<input class="repeatable-button" type="button" value="<liferay-ui:message key="repeat" />" />
			</div>

			<c:if test="<%= elRepeatable %>">
				<span class="repeatable-field-image"><liferay-ui:icon image="add" /></span>
			</c:if>
		</div>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.edit_article_content_xsd_el.jsp";

private String _buildMetaDataHTMLAttributes(Map<String, String> elMetaData, String elName) {
	if (elMetaData.isEmpty()) {
		return StringPool.BLANK;
	}

	StringBundler sb = new StringBundler(elMetaData.size() * 5);

	Iterator<String> keys = elMetaData.keySet().iterator();

	while (keys.hasNext()) {
		String name = keys.next();

		String content = elMetaData.get(name);

		sb.append("data");
		sb.append(name);
		sb.append("='");
		sb.append(HtmlUtil.escapeAttribute(content));
		sb.append("' ");
	}

	return sb.toString();
}
%>