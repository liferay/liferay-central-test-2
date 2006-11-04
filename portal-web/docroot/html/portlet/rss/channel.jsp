<%
/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

<%
try {
	ObjectValuePair ovp = RSSUtil.getChannel(url);

	channel = (ChannelIF)ovp.getValue();
}
catch (Exception e) {
}
%>

<c:choose>
	<c:when test="<%= (url != null) && (channel != null) %>">

		<%
		ItemIF[] items = (ItemIF[])channel.getItems().toArray(new ItemIF[0]);

		Arrays.sort(items, new ItemComparator());
		%>

		<tr>
			<td>
				<b><a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/rss/view" /><portlet:param name="url" value="<%= url %>" /></portlet:renderURL>">
				<%= channel.getTitle() %>
				</a></b>
			</td>
		</tr>
		<tr>
			<td>
				<br>
			</td>
		</tr>

		<%
		for (int j = 0; j < items.length; j++) {
			ItemIF item = items[j];
		%>

			<tr>
				<td>
					<a href="<%= item.getLink() %>" target="_blank"><%= item.getTitle() %></a><br>

					<span style="font-size: xx-small;">

					<c:if test="<%= item.getDate() != null %>">
						<%= dateFormatDateTime.format(item.getDate()) %><br>
					</c:if>

					<%= item.getDescription() %>

					</span>
				</td>
			</tr>

			<c:if test="<%= ((i + 1) < urls.length) || ((j + 1) < itemsPerChannel) %>">
				<tr>
					<td>
						<br>
					</td>
				</tr>
			</c:if>

		<%
			if ((j + 1) >= itemsPerChannel) {
				break;
			}
		}
		%>

	</c:when>
	<c:otherwise>
		<tr>
			<td>
				<b><%= urls[i] %></b>

				<br><br>

				<span class="portlet-msg-error">
				<%= LanguageUtil.format(pageContext, "cannot-be-found", urls[i], false) %>
				</span>
			</td>
		</tr>

		<c:if test="<%= (i + 1) < urls.length %>">
			<tr>
				<td>
					<br>
				</td>
			</tr>
		</c:if>
	</c:otherwise>
</c:choose>