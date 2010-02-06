<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/toggle_area/init.jsp" %>

<div style="float: <%= align %>">
	<liferay-ui:toggle
		id="<%= id %>"
		showImage="<%= showImage %>"
		hideImage="<%= hideImage %>"
		showMessage="<%= showMessage %>"
		hideMessage="<%= hideMessage %>"
		defaultShowContent="<%= defaultShowContent %>"
		stateVar="<%= stateVar %>"
	/>
</div>

<%
String clickValue = SessionClicks.get(request, id, null);

if (clickValue == null) {
	if (defaultShowContent) {
		clickValue = "block";
	}
	else {
		clickValue = "none";
	}
}
else if (clickValue.equals(StringPool.BLANK)) {
	clickValue = "block";
}
%>

<div id="<%= id %>" style="display: <%= clickValue %>;">