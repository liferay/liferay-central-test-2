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
String redirect = ParamUtil.getString(request, "redirect");

String originalRedirect = ParamUtil.getString(request, "originalRedirect", StringPool.BLANK);

if (originalRedirect.equals(StringPool.BLANK)) {
	originalRedirect = redirect;
}
else {
	redirect = originalRedirect;
}

JournalStructure structure = (JournalStructure)request.getAttribute(WebKeys.JOURNAL_STRUCTURE);

long groupId = BeanParamUtil.getLong(structure, request, "groupId", scopeGroupId);

Group group = GroupLocalServiceUtil.getGroup(groupId);

String structureId = BeanParamUtil.getString(structure, request, "structureId");
String newStructureId = ParamUtil.getString(request, "newStructureId");

JournalStructure parentStructure = null;

String parentStructureId = BeanParamUtil.getString(structure, request, "parentStructureId");

String parentStructureName = StringPool.BLANK;

if (Validator.isNotNull(parentStructureId)) {
	try {
		parentStructure = JournalStructureLocalServiceUtil.getStructure(groupId, parentStructureId);

		parentStructureName = parentStructure.getName();
	}
	catch(NoSuchStructureException nsse) {
	}
}

String xsd = ParamUtil.getString(request, "xsd");

if (Validator.isNull(xsd)) {
	xsd = "<root></root>";

	if (structure != null) {
		xsd = structure.getXsd();
	}
}

// Bug with dom4j requires you to remove "\r\n" and "  " or else root.elements()
// and root.content() will return different number of objects

xsd = StringUtil.replace(xsd, StringPool.RETURN_NEW_LINE, StringPool.BLANK);
xsd = StringUtil.replace(xsd, StringPool.DOUBLE_SPACE, StringPool.BLANK);

int tabIndex = 1;
%>

<script type="text/javascript">
	var xmlIndent = "<%= StringPool.DOUBLE_SPACE %>";

	function <portlet:namespace />downloadStructureContent() {
		document.<portlet:namespace />fm2.action = "<%= themeDisplay.getPathMain() %>/journal/get_structure_content";
		document.<portlet:namespace />fm2.target = "_self";
		document.<portlet:namespace />fm2.xml.value = <portlet:namespace />getXsd();
		document.<portlet:namespace />fm2.submit();
	}

	function <portlet:namespace />getXsd(cmd, elCount) {
		if (cmd == null) {
			cmd = "add";
		}

		var xsd = "<root>\n";

		if ((cmd == "add") && (elCount == -1)) {
			xsd += "<dynamic-element name='' type=''></dynamic-element>\n"
		}

		for (i = 0; i >= 0; i++) {
			var elDepth = document.getElementById("<portlet:namespace />structure_el" + i + "_depth");
			var elMetadataXML = document.getElementById("<portlet:namespace />structure_el" + i + "_metadata_xml");
			var elName = document.getElementById("<portlet:namespace />structure_el" + i + "_name");
			var elType = document.getElementById("<portlet:namespace />structure_el" + i + "_type");
			var elIndexType = document.getElementById("<portlet:namespace />structure_el" + i + "_index_type");
			var elRepeatable = document.getElementById("<portlet:namespace />structure_el" + i + "_repeatable");

			if ((elDepth != null) && (elName != null) && (elType != null)) {
				var elDepthValue = elDepth.value;
				var elNameValue = encodeURIComponent(elName.value);
				var elTypeValue = encodeURIComponent(elType.value);
				var elIndexTypeValue = (elIndexType != null) ? elIndexType.value : "";
				var elRepeatableValue = (elRepeatable != null) ? elRepeatable.checked : false;

				if ((cmd == "add") || ((cmd == "remove") && (elCount != i))) {
					for (var j = 0; j <= elDepthValue; j++) {
						xsd += xmlIndent;
					}

					xsd += "<dynamic-element name='" + elNameValue + "' type='" + elTypeValue + "' index-type='" + elIndexTypeValue + "' repeatable='" + elRepeatableValue + "'>";

					if ((cmd == "add") && (elCount == i)) {
						xsd += "<dynamic-element name='' type='' repeatable='false'></dynamic-element>\n";
					}
					else {
						if (elMetadataXML.value) {
							var metadataXML = decodeURIComponent(elMetadataXML.value).replace(/[+]/g, ' ');

							xsd += "\n";
							xsd += xmlIndent;
							xsd += metadataXML;
							xsd += "\n";
						}
					}

					var nextElDepth = document.getElementById("<portlet:namespace />structure_el" + (i + 1) + "_depth");

					if (nextElDepth != null) {
						var nextElDepthValue = nextElDepth.value;

						if (elDepthValue == nextElDepthValue) {
							for (var j = 0; j < elDepthValue; j++) {
								xsd += xmlIndent;
							}

							xsd += "</dynamic-element>\n";
						}
						else if (elDepthValue > nextElDepthValue) {
							var depthDiff = elDepthValue - nextElDepthValue;

							for (var j = 0; j <= depthDiff; j++) {
								if (j != 0) {
									for (var k = 0; k <= depthDiff - j; k++) {
										xsd += xmlIndent;
									}
								}

								xsd += "</dynamic-element>\n";
							}
						}
						else {
							xsd += "\n";
						}
					}
					else {
						for (var j = 0; j <= elDepthValue; j++) {
							if (j != 0) {
								for (var k = 0; k <= elDepthValue - j; k++) {
									xsd += xmlIndent;
								}
							}

							xsd += "</dynamic-element>\n";
						}
					}
				}
				else if ((cmd == "remove") && (elCount == i)) {
					var nextElDepth = document.getElementById("<portlet:namespace />structure_el" + (i + 1) + "_depth");

					if (nextElDepth != null) {
						var nextElDepthValue = nextElDepth.value;

						if (elDepthValue > nextElDepthValue) {
							var depthDiff = elDepthValue - nextElDepthValue;

							for (var j = 0; j < depthDiff; j++) {
								xsd += "</dynamic-element>\n";
							}
						}
					}
					else {
						for (var j = 0; j < elDepthValue; j++) {
							xsd += "</dynamic-element>\n";
						}
					}
				}
			}
			else {
				break;
			}
		}

		xsd += "</root>";

		return xsd;
	}

	function <portlet:namespace />editElement(cmd, elCount) {
		document.<portlet:namespace />fm1.scroll.value = "<portlet:namespace />xsd";
		document.<portlet:namespace />fm1.<portlet:namespace />xsd.value = <portlet:namespace />getXsd(cmd, elCount);
		submitForm(document.<portlet:namespace />fm1);
	}

	function <portlet:namespace />moveElement(moveUp, elCount) {
		document.<portlet:namespace />fm1.scroll.value = "<portlet:namespace />xsd";
		document.<portlet:namespace />fm1.<portlet:namespace />move_up.value = moveUp;
		document.<portlet:namespace />fm1.<portlet:namespace />move_depth.value = elCount;
		document.<portlet:namespace />fm1.<portlet:namespace />xsd.value = <portlet:namespace />getXsd();
		submitForm(document.<portlet:namespace />fm1);
	}

	function <portlet:namespace />removeParentStructure() {
		document.<portlet:namespace />fm1.<portlet:namespace />parentStructureId.value = "";

		var nameEl = document.getElementById("<portlet:namespace />parentStructureName");

		nameEl.href = "#";
		nameEl.innerHTML = "";

		document.getElementById("<portlet:namespace />removeParentStructureButton").disabled = true;
	}

	function <portlet:namespace />saveAndContinueStructure() {
		document.<portlet:namespace />fm1.<portlet:namespace />saveAndContinue.value = "1";
		<portlet:namespace />saveStructure();
	}

	function <portlet:namespace />saveStructure(addAnother) {
		document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= structure == null ? Constants.ADD : Constants.UPDATE %>";

		<c:if test="<%= structure == null %>">
			document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = document.<portlet:namespace />fm1.<portlet:namespace />newStructureId.value;
		</c:if>

		document.<portlet:namespace />fm1.<portlet:namespace />xsd.value = <portlet:namespace />getXsd();
		submitForm(document.<portlet:namespace />fm1);
	}

	function <portlet:namespace />selectStructure(parentStructureId, parentStructureName) {
		document.<portlet:namespace />fm1.<portlet:namespace />parentStructureId.value = parentStructureId;

		var nameEl = document.getElementById("<portlet:namespace />parentStructureName");

		nameEl.href = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_structure" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>&<portlet:namespace />parentStructureId=" + parentStructureId;
		nameEl.innerHTML = parentStructureName + "&nbsp;";

		document.getElementById("<portlet:namespace />removeParentStructureButton").disabled = false;
	}
</script>

<form method="post" name="<portlet:namespace />fm2">
<input name="xml" type="hidden" value="" />
</form>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_structure" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm1" onSubmit="<portlet:namespace />saveStructure(); return false;">
<input name="scroll" type="hidden" value="" />
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />originalRedirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(originalRedirect) %>" />
<input name="<portlet:namespace />groupId" type="hidden" value="<%= groupId %>" />
<input name="<portlet:namespace />structureId" type="hidden" value="<%= HtmlUtil.escapeAttribute(structureId) %>" />
<input name="<portlet:namespace />move_up" type="hidden" value="" />
<input name="<portlet:namespace />move_depth" type="hidden" value="" />
<input name="<portlet:namespace />saveAndContinue" type="hidden" value="" />

<liferay-ui:tabs
	names="structure"
	backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
/>

<liferay-ui:error exception="<%= DuplicateStructureIdException.class %>" message="please-enter-a-unique-id" />
<liferay-ui:error exception="<%= StructureDescriptionException.class %>" message="please-enter-a-valid-description" />
<liferay-ui:error exception="<%= StructureIdException.class %>" message="please-enter-a-valid-id" />
<liferay-ui:error exception="<%= StructureInheritanceException.class %>" message="this-structure-is-already-within-the-inheritance-path-of-the-selected-parent-please-select-another-parent-structure" />
<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="id" />
	</td>
	<td>
		<c:choose>
			<c:when test="<%= PropsValues.JOURNAL_STRUCTURE_FORCE_AUTOGENERATE_ID %>">
				<c:choose>
					<c:when test="<%= structure == null %>">
						<liferay-ui:message key="autogenerate-id" />

						<input name="<portlet:namespace />newStructureId" type="hidden" value="" />
						<input name="<portlet:namespace />autoStructureId" type="hidden" value="true" />
					</c:when>
					<c:otherwise>
						<%= structureId %>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<c:choose>
							<c:when test="<%= structure == null %>">
								<liferay-ui:input-field model="<%= JournalStructure.class %>" bean="<%= structure %>" field="structureId" fieldParam="newStructureId" defaultValue="<%= newStructureId %>" />
							</c:when>
							<c:otherwise>
								<%= structureId %>
							</c:otherwise>
						</c:choose>
					</td>
					<td style="padding-left: 30px;"></td>
					<td>
						<c:if test="<%= structure == null %>">
							<liferay-ui:input-checkbox param="autoStructureId" />

							<liferay-ui:message key="autogenerate-id" />
						</c:if>
					</td>
				</tr>
				</table>
			</c:otherwise>
		</c:choose>
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="name" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= JournalStructure.class %>" bean="<%= structure %>" field="name" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="description" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= JournalStructure.class %>" bean="<%= structure %>" field="description" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="parent-structure" />
	</td>
	<td>
		<input name="<portlet:namespace />parentStructureId" type="hidden" value="<%= parentStructureId %>" />

		<c:choose>
			<c:when test="<%= (structure == null) || (Validator.isNotNull(parentStructureId)) %>">
				<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_structure" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="parentStructureId" value="<%= parentStructureId %>" /></portlet:renderURL>" id="<portlet:namespace />parentStructureName">
				<%= parentStructureName %></a>
			</c:when>
			<c:otherwise>
				<a id="<portlet:namespace />parentStructureName"></a>
			</c:otherwise>
		</c:choose>

		<input type="button" value="<liferay-ui:message key="select" />" onClick="var structureWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_structure" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>', 'structure', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); structureWindow.focus();" />

		<input <%= Validator.isNull(parentStructureId) ? "disabled" : "" %> id="<portlet:namespace />removeParentStructureButton" type="button" value="<liferay-ui:message key="remove" />" onClick="<portlet:namespace />removeParentStructure();">
	</td>
</tr>

<c:if test="<%= structure != null %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="url" />
		</td>
		<td>
			<liferay-ui:input-resource
				url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/journal/get_structure?groupId=" + groupId + "&structureId=" + structureId %>'
			/>
		</td>
	</tr>

	<c:if test="<%= portletDisplay.isWebDAVEnabled() %>">
		<tr>
			<td>
				<liferay-ui:message key="webdav-url" />
			</td>
			<td>
				<liferay-ui:input-resource
					url='<%= themeDisplay.getPortalURL() + "/tunnel-web/secure/webdav/" + company.getWebId() + group.getFriendlyURL() + "/journal/Structures/" + structureId %>'
				/>
			</td>
		</tr>
	</c:if>
</c:if>

<c:if test="<%= structure == null %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="permissions" />
		</td>
		<td>
			<liferay-ui:input-permissions
				modelName="<%= JournalStructure.class.getName() %>"
			/>
		</td>
	</tr>
</c:if>

</table>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input name="save-and-continue" type="button" value="<liferay-ui:message key="save-and-continue" />" onClick="<portlet:namespace />saveAndContinueStructure();" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>';" />

<br /><br />

<liferay-ui:tabs names="xsd" />

<liferay-ui:error exception="<%= StructureXsdException.class %>" message="please-enter-a-valid-xsd" />

<input id="<portlet:namespace />xsd" name="<portlet:namespace />xsd" type="hidden" value="" />

<input type="button" value="<liferay-ui:message key="add-row" />" onClick="<portlet:namespace />editElement('add', -1);" />

<input id="<portlet:namespace />editorButton" type="button" value="<liferay-ui:message key="launch-editor" />" />

<c:if test="<%= structure != null %>">
	<input type="button" value="<liferay-ui:message key="download" />" onClick="<portlet:namespace />downloadStructureContent();" />
</c:if>

<br /><br />

<table class="taglib-search-iterator">

<%
Document doc = SAXReaderUtil.read(xsd);

Element root = doc.getRootElement();

String moveUpParam = request.getParameter("move_up");
String moveDepthParam = request.getParameter("move_depth");

if (Validator.isNotNull(moveUpParam) && Validator.isNotNull(moveDepthParam)) {
	_move(root, new IntegerWrapper(0), GetterUtil.getBoolean(moveUpParam), GetterUtil.getInteger(moveDepthParam), new BooleanWrapper(false));
}

IntegerWrapper tabIndexWrapper = new IntegerWrapper(tabIndex);

_format(root, new IntegerWrapper(0), new Integer(-1), tabIndexWrapper, pageContext, request);

tabIndex = tabIndexWrapper.getValue();
%>

</table>

</form>

<script type="text/javascript">
	Liferay.Util.inlineEditor(
		{
			url: '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/journal/edit_structure_xsd" /></portlet:renderURL>',
			button: '#<portlet:namespace />editorButton',
			textarea: '<portlet:namespace />xsdContent'
		}
	);

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		<c:choose>
			<c:when test="<%= PropsValues.JOURNAL_STRUCTURE_FORCE_AUTOGENERATE_ID %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />name);
			</c:when>
			<c:otherwise>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace /><%= (structure == null) ? "newStructureId" : "name" %>);
			</c:otherwise>
		</c:choose>
	</c:if>
</script>

<%!
private void _format(Element root, IntegerWrapper count, Integer depth, IntegerWrapper tabIndex, PageContext pageContext, HttpServletRequest request) throws Exception {
	depth = new Integer(depth.intValue() + 1);

	List children = root.elements();

	Boolean hasSiblings = null;

	if (children.size() > 1) {
		hasSiblings = Boolean.TRUE;
	}
	else {
		hasSiblings = Boolean.FALSE;
	}

	Iterator itr = children.iterator();

	while (itr.hasNext()) {
		Element el = (Element)itr.next();

		if (el.getName().equals("meta-data")) {
			continue;
		}

		request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL, el);
		request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_COUNT, count);
		request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_DEPTH, depth);
		request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_SIBLINGS, hasSiblings);
		request.setAttribute(WebKeys.TAB_INDEX, tabIndex);

		pageContext.include("/html/portlet/journal/edit_structure_xsd_el.jsp");

		count.increment();

		_format(el, count, depth, tabIndex, pageContext, request);
	}
}

private void _move(Element root, IntegerWrapper count, boolean up, int depth, BooleanWrapper halt) throws Exception {
	List children = root.elements();

	for (int i = 0; i < children.size(); i++) {
		Element el = (Element)children.get(i);

		String nodeName = el.getName();

		if (Validator.isNotNull(nodeName) && nodeName.equals("meta-data")) {
			continue;
		}

		if (halt.getValue()) {
			return;
		}

		if (count.getValue() == depth) {
			if (up) {
				if (i == 0) {
					children.remove(i);
					children.add(children.size(), el);
				}
				else {
					children.remove(i);
					children.add(i - 1, el);
				}
			}
			else {
				if ((i + 1) == children.size()) {
					children.remove(i);
					children.add(0, el);
				}
				else {
					children.remove(i);
					children.add(i + 1, el);
				}
			}

			halt.setValue(true);

			return;
		}

		count.increment();

		_move(el, count, up, depth, halt);
	}
}
%>