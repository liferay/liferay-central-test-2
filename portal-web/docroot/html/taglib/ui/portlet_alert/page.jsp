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

<%@ include file="/html/taglib/ui/portlet_alert/init.jsp" %>

<aui:script use="liferay-portlet-alert">
	new Liferay.Portlet.Alert(
		{
			closeable: <%= closeable %>,
			delay: {
				hide: <%= timeout %>,
				show: 0
			},
			duration: <%= animationTime %>,
			message: '<%= content %>',
			type: '<%= type %>'
		}
	).render();
</aui:script>