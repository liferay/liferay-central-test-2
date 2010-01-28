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
Element el = (Element)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL);

String elName = JS.decodeURIComponent(el.attributeValue("name", StringPool.BLANK));
String elType = JS.decodeURIComponent(el.attributeValue("type", StringPool.BLANK));
String elIndexType = JS.decodeURIComponent(el.attributeValue("index-type", StringPool.BLANK));
boolean repeatable = GetterUtil.getBoolean(el.attributeValue("repeatable"));

String elMetadataXML = StringPool.BLANK;

Element metaDataEl = el.element("meta-data");

if (metaDataEl != null) {
	elMetadataXML = metaDataEl.asXML();
}

String parentElType = java.net.URLDecoder.decode(el.getParent().attributeValue("type", StringPool.BLANK));

IntegerWrapper count = (IntegerWrapper)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_COUNT);
Integer depth = (Integer)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_DEPTH);
Boolean hasSiblings = (Boolean)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_SIBLINGS);
IntegerWrapper tabIndex = (IntegerWrapper)request.getAttribute(WebKeys.TAB_INDEX);

String className = "portlet-section-alternate results-row alt";

if (MathUtil.isEven(count.getValue())) {
	className = "portlet-section-body results-row";
}
%>

<tr class="<%= className %>">
	<td>
		<aui:input name='<%= "structure_el" + count.getValue() + "_depth" %>' type="hidden" value="<%= depth %>" />
		<aui:input name='<%= "structure_el" + count.getValue() + "_metadata_xml" %>' type="hidden" value="<%= HttpUtil.encodeURL(elMetadataXML) %>" />

		<table class="lfr-table">
		<tr>
			<c:if test="<%= depth.intValue() > 0 %>">
				<td><img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/spacer.png" vspace="0" width="<%= depth.intValue() * 50 %>" /></td>
			</c:if>

			<td>
				<aui:input label="" name='<%= "structure_el" + count.getValue() + "_name" %>' tabindex="<%= tabIndex.getValue() %>" type="text" size="20" value="<%= elName %>" />
			</td>
			<td>
				<c:choose>
					<c:when test='<%= parentElType.equals("list") || parentElType.equals("multi-list") %>'>
						<aui:input label="" name='<%= "structure_el" + count.getValue() + "_type" %>' tabindex="<%= tabIndex.getValue() %>" type="text" size="20" value="<%= elType %>" />
					</c:when>
					<c:otherwise>
						<aui:column>
							<aui:select label="" name='<%= "structure_el" + count.getValue() + "_type" %>' showEmptyOption="<%= true %>" tabindex="<%= tabIndex.getValue() %>">
								<aui:option label="text" selected='<%= elType.equals("text") %>' />
								<aui:option label="text-box" selected='<%= elType.equals("text_box") %>' value="text_box" />
								<aui:option label="text-area" selected='<%= elType.equals("text_area") %>' value="text_area" />
								<aui:option label="image" selected='<%= elType.equals("image") %>' />
								<aui:option label="<%= PortalUtil.getPortletTitle(PortletKeys.IMAGE_GALLERY, user) %>" selected='<%= elType.equals("image_gallery") %>' value="image_gallery" />
								<aui:option label="<%= PortalUtil.getPortletTitle(PortletKeys.DOCUMENT_LIBRARY, user) %>" selected='<%= elType.equals("document_library") %>' value="document_library" />
								<aui:option label="boolean-flag" selected='<%= elType.equals("boolean") %>' value="boolean" />
								<aui:option label="selection-list" selected='<%= elType.equals("list") %>' value="list" />
								<aui:option label="multi-selection-list" selected='<%= elType.equals("multi-list") %>' value="multi-list" />
								<aui:option label="link-to-layout" selected='<%= elType.equals("link_to_layout") %>' value="link_to_layout" />
							</aui:select>
						</aui:column>

						<aui:column>
							<aui:select label="" name='<%= "structure_el" + count.getValue() + "_index_type" %>'>
								<aui:option label="not-searchable" />
								<aui:option label="searchable-keyword" selected='<%= elIndexType.equals("keyword") %>' value="keyword" />
								<aui:option label="searchable-text" selected='<%= elIndexType.equals("text") %>' value="text" />
							</aui:select>
						</aui:column>
					</c:otherwise>
				</c:choose>
			</td>

			<c:if test='<%= !parentElType.equals("list") && !parentElType.equals("multi-list") %>'>
				<td>
					<aui:input inlineLabel="right" label="repeatable" name='<%= "structure_el" + count.getValue() + "_repeatable" %>' tabindex="<%= tabIndex.getValue() %>" type="checkbox" value="<%= repeatable %>" />
				</td>
				<td>

					<%
					String taglibAddURL = "javascript:" + renderResponse.getNamespace() + "editElement('add', " + count.getValue() + ");";
					%>

					<liferay-ui:icon image="../arrows/01_plus" message="add" url="<%= taglibAddURL %>" />
				</td>
			</c:if>

			<c:if test="<%= el.elements().isEmpty() %>">
				<td>

					<%
					String taglibRemoveURL = "javascript:" + renderResponse.getNamespace() + "editElement('remove', " + count.getValue() + ");";
					%>

					<liferay-ui:icon image="../arrows/01_minus" message="remove" url="<%= taglibRemoveURL %>" />
				</td>
			</c:if>

			<c:if test="<%= hasSiblings.booleanValue() %>">
				<td>
					<liferay-ui:icon image="../arrows/01_up" message="up" url='<%= "javascript:" + renderResponse.getNamespace() + "moveElement(true, " + count.getValue() + ");" %>' />
				</td>
				<td>
					<liferay-ui:icon image="../arrows/01_down" message="down" url='<%= "javascript:" + renderResponse.getNamespace() + "moveElement(false, " + count.getValue() + ");" %>' />
				</td>
			</c:if>
		</tr>
		</table>
	</td>
</tr>

<%
tabIndex.setValue(tabIndex.getValue() + 2);
%>