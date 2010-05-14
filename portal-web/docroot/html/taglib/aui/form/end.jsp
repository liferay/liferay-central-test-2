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

<%@ include file="/html/taglib/init.jsp" %>

<%
String name = GetterUtil.getString((String)request.getAttribute("aui:form:name"));
String onSubmit = GetterUtil.getString((String)request.getAttribute("aui:form:onSubmit"));
%>

</form>

<aui:script use="aui-base,aui-form-validator">
	var form = A.one('#<%= namespace + name %>');

	var validator = new A.FormValidator({
		boundingBox: '#<%= namespace + name %>'
	});

	if (validator) {
		validator.on(
			'submit',
			function(event) {
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