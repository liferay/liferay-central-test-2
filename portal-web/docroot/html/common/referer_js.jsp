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

<%@ include file="/html/common/referer_common.jsp" %>

<script type="text/javascript">

	<%
	Boolean logout = (Boolean)request.getAttribute("logout");

	if (logout == null) {
		logout = false;
	}

	boolean isNtlmEnabled = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), PropsKeys.NTLM_AUTH_ENABLED, PropsValues.NTLM_AUTH_ENABLED);
	%>

	<c:if test="<%= logout && isNtlmEnabled && BrowserSnifferUtil.isIe(request) %>">
	    document.execCommand("ClearAuthenticationCache");
	</c:if>

	location.href = '<%= HtmlUtil.escapeJS(referer) %>';
</script>