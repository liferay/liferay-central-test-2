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

<%@ include file="/html/portlet/quick_note/init.jsp" %>

<div id="<portlet:namespace />pad" style="background: <%= color %>;">
	<c:if test="<%= portletDisplay.isShowConfigurationIcon() %>">
		<div class="portlet-title-default">
			<table border="0" cellpadding="2" cellspacing="0" width="100%">
			<tr>
				<td>
					<span class="note-color yellow"></span>
					<span class="note-color green"></span>
					<span class="note-color blue"></span>
					<span class="note-color red"></span>
				</td>

				<c:if test="<%= portletDisplay.isShowCloseIcon() %>">
					<td>
						<a border="0" class="close-note" href="<%= portletDisplay.getURLClose() %>"><img alt="<liferay-ui:message key="close" />" src="<%= themeDisplay.getPathThemeImages() %>/portlet/close.png" /></a>
					</td>
				</c:if>
			</tr>
			</table>
		</div>
	</c:if>

	<div id="<portlet:namespace />note"><%= StringUtil.replace(HtmlUtil.escape(data), "&lt;br /&gt;", "<br />") %></div>
</div>

<c:if test="<%= portletDisplay.isShowConfigurationIcon() %>">
	<script type="text/javascript">
		AUI().ready(
			'editable',
			function(A) {
				var quickNotePad = A.one('#<portlet:namespace />pad');

				if (quickNotePad) {
					quickNotePad.all('.note-color').on(
						'click',
						function(event) {
							var box = event.currentTarget;

							var bgColor = box.getStyle('backgroundColor');

							quickNotePad.setStyle('backgroundColor', bgColor);

							A.io(
								'<%= themeDisplay.getPathMain() %>/quick_note/save',
								{
									data: A.toQueryString(
										{
											color: bgColor,
											portletId: '<%= portletDisplay.getId() %>',
											p_l_id: '<%= plid %>'
										}
									),
									method: 'POST'
								}
							);
						}
					);
				}

				new A.Editable(
					{
						node: '#<portlet:namespace />note',
						inputType: 'textarea',
						on: {
							contentTextChange: function(event) {
								var instance = this;

								if (!event.initial) {
									var newValue = event.newVal.replace(/\n/gi, '<br />');

									event.newVal = instance._toText(event.newVal);

									A.io(
										'<%= themeDisplay.getPathMain() %>/quick_note/save',
										{
											data: A.toQueryString(
												{
													p_l_id: '<%= plid %>',
													portletId: '<%= portletDisplay.getId() %>',
													data: newValue
												}
											),
											method: 'POST'
										}
									);
								}
							}
						}
					}
				);
			}
		);
	</script>
</c:if>