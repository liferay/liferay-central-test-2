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

<%@ include file="/html/taglib/init.jsp" %>

<%
String randomNamespace = DeterminateKeyGenerator.generate("taglib_ui_webdav_page") + StringPool.UNDERLINE;

String path = (String)request.getAttribute("liferay-ui:webdav:path");
%>

<div class="taglib-webdav" id="<%= randomNamespace %>webdav">
	<a class="show-webdav" href="javascript:;"><liferay-ui:message key="access-from-my-desktop" /></a>

	<table class="lfr-table">
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="webdav-url" />
			</td>
			<td>
				<liferay-ui:input-resource
					url='<%= themeDisplay.getPortalURL() + "/tunnel-web/secure/webdav/" + company.getWebId() + themeDisplay.getScopeGroup().getFriendlyURL() + path %>'
				/>
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript">
	AUI().ready(
		function (A) {
			var webdavDiv = A.one('#<%= randomNamespace %>webdav');

			if (webdavDiv) {
				var webdavLink = webdavDiv.all('.show-webdav');

				if (webdavLink) {
					webdavLink.on(
						'click',
						function(event) {
							webdavDiv.toggleClass('visible');
						}
					);
				}
			}
		}
	);
</script>