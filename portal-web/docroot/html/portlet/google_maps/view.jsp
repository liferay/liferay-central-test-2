<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%@ include file="/html/portlet/google_maps/init.jsp" %>

<%
String key = PropsUtil.get(PropsUtil.GOOGLE_MAPS_LICENSE);
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(key) %>">

		<%
		sourceAddress = GetterUtil.getString((String)session.getAttribute(renderResponse.getNamespace() + "sourceAddress"), sourceAddress);
		destinationAddress = GetterUtil.getString((String)session.getAttribute(renderResponse.getNamespace() + "destinationAddress"), destinationAddress);
		%>

		<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=<%= key %>" type="text/javascript">
		</script>

		<script type="text/javascript">
			var <portlet:namespace />map;
			var <portlet:namespace />geocoder;

			function <portlet:namespace />load() {
				if (GBrowserIsCompatible()) {
					<portlet:namespace />map = new GMap2(document.getElementById("<portlet:namespace />map"));

					<portlet:namespace />map.addControl(new GSmallMapControl());
					<portlet:namespace />map.addControl(new GMapTypeControl());

					<portlet:namespace />geocoder = new GClientGeocoder();

					<portlet:namespace />getAddress("<%= sourceAddress %>");
				}
			}

			Event.addHandler(window, "onload", <portlet:namespace />load);
			Event.addHandler(window, "onunload", GUnload);

			function <portlet:namespace />getAddress(address) {
				<portlet:namespace />geocoder.getLatLng(
					address,
					function (point) {
						if (!point) {
							alert(address + " not found");
						}
						else {
							<portlet:namespace />map.setCenter(point, 13);

							var marker = new GMarker(point);

							<portlet:namespace />map.addOverlay(marker);

							marker.openInfoWindowHtml(address);
						}
					}
				);
			}

			function <portlet:namespace />getDirections() {
				<portlet:namespace />getMap();

				var destinationAddress = document.<portlet:namespace />fm.<portlet:namespace />destinationAddress.value;

				<portlet:namespace />saveDestinationAddress(destinationAddress);

				window.open("http://maps.google.com/maps?f=q&hl=en&q=" + encodeURIComponent(document.<portlet:namespace />fm.<portlet:namespace />sourceAddress.value) + "+to+" + encodeURIComponent(destinationAddress));
			}

			function <portlet:namespace />getMap() {
				var sourceAddress = document.<portlet:namespace />fm.<portlet:namespace />sourceAddress.value;

				<portlet:namespace />getAddress(sourceAddress);
				<portlet:namespace />saveSourceAddress(sourceAddress);
			}

			function <portlet:namespace />saveDestinationAddress(address) {
				loadPage(themeDisplay.getPathMain() + "/google_maps/save_destination_address", "namespace=" + encodeURIComponent("<%= renderResponse.getNamespace() %>") + "&destinationAddress=" + encodeURIComponent(address));
			}

			function <portlet:namespace />saveSourceAddress(address) {
				loadPage(themeDisplay.getPathMain() + "/google_maps/save_source_address", "namespace=" + encodeURIComponent("<%= renderResponse.getNamespace() %>") + "&sourceAddress=" + encodeURIComponent(address));
			}
		</script>

		<form name="<portlet:namespace />fm">

		<c:if test="<%= sourceInputEnabled %>">
			<input class="form-text" name="<portlet:namespace />sourceAddress" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= sourceAddress %>" onKeyPress="if (event.keyCode == 13) { <portlet:namespace />getMap(); return false; }">

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "get-map") %>' onClick="<portlet:namespace />getMap();">
		</c:if>

		<c:if test="<%= destinationInputEnabled %>">
			<input class="form-text" name="<portlet:namespace />destinationAddress" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= destinationAddress %>" onKeyPress="if (event.keyCode == 13) { <portlet:namespace />getDirections(); return false; }">

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "get-directions") %>' onClick="<portlet:namespace />getDirections();">
		</c:if>

		<c:if test="<%= sourceInputEnabled || destinationInputEnabled %>">
			<br><br>
		</c:if>

		<div id="<portlet:namespace />map" style="height: 300px; width: 100%;"></div>

		</form>
	</c:when>
	<c:otherwise>
		<%= LanguageUtil.get(pageContext, "please-contact-the-administrator-to-configure-a-google-license") %>
	</c:otherwise>
</c:choose>