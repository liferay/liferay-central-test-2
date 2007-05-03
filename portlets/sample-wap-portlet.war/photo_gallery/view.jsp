<%@ include file="/init.jsp" %>

<%
WindowState windowState = renderRequest.getWindowState();

if (windowState.equals(WindowState.MAXIMIZED)) {
	String cur = ParamUtil.getString(request, "cur", "1");
%>

	<img src="<%= request.getContextPath() %>/photo_gallery/image/photo_<%= cur %>_large.jpg" /><br />

	<%
	if (cur.equals("1")) {
	%>

		<a href="<portlet:renderURL><portlet:param name="cur" value="2" /></portlet:renderURL>">Next</a><br />

	<%
	}
	else {
	%>

		<a href="<portlet:renderURL><portlet:param name="cur" value="1" /></portlet:renderURL>">Previous</a><br />

	<%
	}
	%>

<%
}
else {
%>

	<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" />">
	<img src="<%= request.getContextPath() %>/photo_gallery/image/photo_1_thumbnail.jpg" />
	</a>

<%
}
%>