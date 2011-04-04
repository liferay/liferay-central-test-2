<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/short_url/init.jsp" %>

<aui:form>
	<aui:input type="text" name="url" />
	<br/>
	<input type="submit" value="<liferay-ui:message key="shorten" />" />
</aui:form>

<aui:script use="aui-io-request">
	var form = A.one('#<portlet:namespace />fm');
	
	form.on(
		'submit',
		function(event) {
			var input = A.one('#<portlet:namespace/>url');
			var url = input.get('value');
			
			A.io.request(
				'/tunnel-web/json', 
				{
					data: {
						serviceClassName: 'com.liferay.portlet.shorturl.service.ShortURLServiceUtil',
						serviceMethodName: 'addShortURL',
						serviceParameters: '[url]',
						url: url
					},
					method : 'post',
					dataType : 'json',
					on: {
						success: function(event, id, obj) {
							var responseData = this.get('responseData');
							var shortURL = '<%= themeDisplay.getURLPortal() %>' + '/u' + responseData.hash + responseData.descriptor;
							input.set('value', shortURL);
						}
					}
				}
			);
			event.halt();
		}
	);
</aui:script>