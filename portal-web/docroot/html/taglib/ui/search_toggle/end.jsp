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

<%@ include file="/html/taglib/ui/search_toggle/init.jsp" %>

		<c:if test="<%= Validator.isNotNull(buttonLabel) %>">
			<aui:button type="submit" value="<%= buttonLabel %>" />
		</c:if>

		<div class="toggle-link">
			<aui:a href="javascript:;" tabindex="-1">&laquo; <liferay-ui:message key="basic" /></aui:a>
		</div>
	</div>
</div>

<script type="text/javascript">
	AUI().ready(
		'io-request',
		function (A) {
			var <%= id %>curClickValue = "<%= clickValue %>";

			var basicForm = A.one("#<%= id %>basic");
			var advancedForm = A.one("#<%= id %>advanced");

			var basicControls = basicForm.all('input, select');
			var advancedControls = advancedForm.all('input, select');

			if (<%= id %>curClickValue == "basic") {
				advancedControls.attr('disabled', 'disabled');
			}
			else {
				basicControls.attr('disabled', 'disabled');
			}

			AUI().all('.toggle-link a').on(
				'click',
				function() {
					basicForm.toggle();
					advancedForm.toggle();

					var advancedSearchObj = A.one("#<%= namespace %><%= id %><%= displayTerms.ADVANCED_SEARCH %>");

					if (<%= id %>curClickValue == "basic") {
						<%= id %>curClickValue = "advanced";

						advancedSearchObj.val(true);

						basicControls.attr('disabled', 'disabled');
						advancedControls.attr('disabled', '');
					}
					else {
						<%= id %>curClickValue = "basic";

						advancedSearchObj.val(false);

						basicControls.attr('disabled', '');
						advancedControls.attr('disabled', 'disabled');
					}

					A.io.request(
						'<%= themeDisplay.getPathMain() %>/portal/session_click',
						{
							data: {
								'<%= id %>': <%= id %>curClickValue
							}
						}
					);
				}
			);
		}
	);
</script>