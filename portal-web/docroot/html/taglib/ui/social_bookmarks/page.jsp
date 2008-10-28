<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/social_bookmarks/init.jsp" %>

<%
String randomNamespace = PwdGenerator.getPassword(PwdGenerator.KEY3, 4) + StringPool.UNDERLINE;
%>

<div class="taglib-social-bookmarks-wrapper">

<a id="<%= randomNamespace %>showSocialBookmarks" href="javascript:void(0);"><liferay-ui:message key="bookmarks" /> &raquo;</a>

<ul id="<%= randomNamespace %>socialBookmarks" class="lfr-component taglib-social-bookmarks">

	<%
	for (int i = 0; i < typesArray.length; i++) {
	%>

		<li>
			<liferay-ui:social-bookmark type="<%= typesArray[i] %>" url="<%= url %>" title="<%= title %>" target="<%= target %>" />
		</li>

	<%
	}
	%>

</ul>
</div>

<script>
	var <%= randomNamespace %>linkSocialBookmarks = jQuery('#<%= randomNamespace %>showSocialBookmarks');
	var <%= randomNamespace %>socialBookmarks = jQuery('#<%= randomNamespace %>socialBookmarks');
	var <%= randomNamespace %>openedLinkLabel = '<liferay-ui:message key="bookmarks" /> &laquo;';
	var <%= randomNamespace %>closedLinkLabel = '<liferay-ui:message key="bookmarks" /> &raquo;';

	<%= randomNamespace %>linkSocialBookmarks.toggle(
		function() {
			<%= randomNamespace %>linkSocialBookmarks.html(<%= randomNamespace %>openedLinkLabel);
			<%= randomNamespace %>socialBookmarks.show('fast');
			return false;
		},
		function() {
			<%= randomNamespace %>linkSocialBookmarks.html(<%= randomNamespace %>closedLinkLabel);
			<%= randomNamespace %>socialBookmarks.hide();
			return false;
		}
	);
</script>