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

<%@ include file="/html/portlet/rss/init.jsp" %>

<%
String url = ParamUtil.getString(request, "url");
String title = StringPool.BLANK;
%>

<c:if test="<%= headerArticleResourcePrimKey > 0 %>">
	<liferay-ui:journal-article articleResourcePrimKey="<%= headerArticleResourcePrimKey %>" />
</c:if>

<%
for (int i = 0; i < urls.length; i++) {
	url = urls[i];

	if (i < titles.length) {
		title = titles[i];
	}
	else {
		title = StringPool.BLANK;
	}

	boolean last = false;

	if (i == urls.length - 1) {
		last = true;
	}
%>

	<%@ include file="/html/portlet/rss/feed.jspf" %>

<%
}
%>

<script type="text/javascript">
	jQuery(
		function() {
			var minusAlt = '<liferay-ui:message key="collapse" />';
			var minusImage = '01_minus.png';
			var plusAlt = '<liferay-ui:message key="expande" />';
			var plusImage = '01_plus.png';
			jQuery(".<portlet:namespace />entry-expander").click(
				function() {
					if (this.src.indexOf('minus.png') > -1) {
						jQuery(".feed-entry-content", this.parentNode).slideUp();

						this.alt = this.alt.replace(minusAlt, plusAlt);
						this.src = this.src.replace(minusImage, plusImage);// themeDisplay.getPathThemeImages() + "/arrows/01_plus.png";
					}
					else {
						jQuery(".feed-entry-content", this.parentNode).slideDown();

						this.alt = this.alt.replace(plusAlt, minusAlt);
						this.src = this.src.replace(plusImage, minusImage); //themeDisplay.getPathThemeImages() + "/arrows/01_minus.png";
					}
				}
			);
		}
	);
</script>

<c:if test="<%= footerArticleResourcePrimKey > 0 %>">
	<liferay-ui:journal-article articleResourcePrimKey="<%= footerArticleResourcePrimKey %>" />
</c:if>