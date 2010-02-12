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

<%@ include file="/html/taglib/init.jsp" %>

<%
String name = GetterUtil.getString((String)request.getAttribute("aui:form:name"));
String onSubmit = GetterUtil.getString((String)request.getAttribute("aui:form:onSubmit"));
%>

</form>

<aui:script use="node">
	var form = A.one('#<%= namespace + name %>');

	if (form) {
		form.on(
			'submit',
			function(event) {
				event.preventDefault();
	
				<c:choose>
					<c:when test="<%= Validator.isNull(onSubmit) %>">
						submitForm(document.<%= namespace + name %>);
					</c:when>
					<c:otherwise>
						<%= onSubmit %>
					</c:otherwise>
				</c:choose>
			}
		);

		form.delegate(
			'focus',
			function(event) {
				var row = event.currentTarget.ancestor('.aui-field');

				if (row) {
					row.addClass('aui-field-focused');
				}
			},
			'button,input,select,textarea'
		);

		form.delegate(
			'blur',
			function(event) {
				var row = event.currentTarget.ancestor('.aui-field');

				if (row) {
					row.removeClass('aui-field-focused');
				}
			},
			'button,input,select,textarea'
		);
	}
</aui:script>