<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<br>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
	<td>
		<table border="0" cellpadding="4" cellspacing="0" width="100%">
		<tr>
			<td class="beta-gradient">
				<b><%= LanguageUtil.get(pageContext, "meta-tags") %>:</b>
			</td>
			<td align="right" class="beta-gradient">
				<span style="font-size: xx-small;">
				[<a href="javascript: void(0);" onClick="Liferay.Util.toggleByIdSpan(this, '<portlet:namespace />metaTags'); self.focus();"><span><%= LanguageUtil.get(pageContext, "show") %></span><span style="display: none;"><%= LanguageUtil.get(pageContext, "hide") %></span></a>]
				</span>
			</td>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<div id="<portlet:namespace />metaTags" style="display: none;">
			<br>

			<%= LanguageUtil.get(pageContext, "meta-robots") %><br>

			<textarea name="TypeSettingsProperties(meta-robots)" style="height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px; width: 400px;" wrap="soft"><bean:write name="SEL_LAYOUT" property="typeSettingsProperties(meta-robots)" /></textarea>

			<br><br>

			<%= LanguageUtil.get(pageContext, "meta-description") %><br>

			<textarea name="TypeSettingsProperties(meta-description)" style="height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px; width: 400px;" wrap="soft"><bean:write name="SEL_LAYOUT" property="typeSettingsProperties(meta-description)" /></textarea>

			<br><br>

			<%= LanguageUtil.get(pageContext, "meta-keywords") %><br>

			<textarea name="TypeSettingsProperties(meta-keywords)" style="height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px; width: 400px;" wrap="soft"><bean:write name="SEL_LAYOUT" property="typeSettingsProperties(meta-keywords)" /></textarea>

			<br><br>
		</div>
	</td>
</tr>
<tr>
	<td>
		<table border="0" cellpadding="4" cellspacing="0" width="100%">
		<tr>
			<td class="beta-gradient">
				<b><%= LanguageUtil.get(pageContext, "javascript") %>:</b>
			</td>
			<td align="right" class="beta-gradient">
				<span style="font-size: xx-small;">
				[<a href="javascript: void(0);" onClick="Liferay.Util.toggleByIdSpan(this, '<portlet:namespace />javascript'); self.focus();"><span><%= LanguageUtil.get(pageContext, "show") %></span><span style="display: none;"><%= LanguageUtil.get(pageContext, "hide") %></span></a>]
				</span>
			</td>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<div id="<portlet:namespace />javascript" style="display: none;">
			<br>

			<%= LanguageUtil.get(pageContext, "javascript-1") %><br>

			<textarea name="TypeSettingsProperties(javascript-1)" style="height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px; width: 400px;" wrap="soft"><bean:write name="SEL_LAYOUT" property="typeSettingsProperties(javascript-1)" /></textarea>

			<br><br>

			<%= LanguageUtil.get(pageContext, "javascript-2") %><br>

			<textarea name="TypeSettingsProperties(javascript-2)" style="height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px; width: 400px;" wrap="soft"><bean:write name="SEL_LAYOUT" property="typeSettingsProperties(javascript-2)" /></textarea>

			<br><br>

			<%= LanguageUtil.get(pageContext, "javascript-3") %><br>

			<textarea name="TypeSettingsProperties(javascript-3)" style="height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px; width: 400px;" wrap="soft"><bean:write name="SEL_LAYOUT" property="typeSettingsProperties(javascript-3)" /></textarea>
		</div>
	</td>
</tr>

<c:if test="<%= PortalUtil.isLayoutSitemapable(selLayout) %>">
	<tr>
		<td>
			<table border="0" cellpadding="4" cellspacing="0" width="100%">
			<tr>
				<td class="beta-gradient">
					<b><%= LanguageUtil.get(pageContext, "sitemap-protocol") %>:</b>
				</td>
				<td align="right" class="beta-gradient">
					<span style="font-size: xx-small;">
					[<a href="javascript: void(0);" onClick="Liferay.Util.toggleByIdSpan(this, '<portlet:namespace />sitemap'); self.focus();"><span><%= LanguageUtil.get(pageContext, "show") %></span><span style="display: none;"><%= LanguageUtil.get(pageContext, "hide") %></span></a>]
					</span>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<div id="<portlet:namespace />sitemap" style="display: none;">
				<br>

				<%
				boolean include = GetterUtil.getBoolean(selLayout.getTypeSettingsProperties().getProperty("sitemap-include"), true);
				String changeFrequency = selLayout.getTypeSettingsProperties().getProperty("sitemap-changefreq", "daily");
				%>

				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<%= LanguageUtil.get(pageContext, "include") %>
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						<select name="TypeSettingsProperties(sitemap-include)">
							<option <%= (include) ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "yes") %></option>
							<option <%= (!include) ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "no") %></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<%= LanguageUtil.get(pageContext, "page-priority") %> (0.0 - 1.0)
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						<input name="TypeSettingsProperties(sitemap-priority)" size="3" type="text" value="<bean:write name="SEL_LAYOUT" property="typeSettingsProperties(sitemap-priority)" />">
					</td>
				</tr>
				<tr>
					<td>
						<%= LanguageUtil.get(pageContext, "change-frequency") %>
					</td>
					<td style="padding-left: 10px;"></td>
					<td>
						<select name="TypeSettingsProperties(sitemap-changefreq)">
							<option <%= (changeFrequency.equals("always")) ? "selected" : "" %> value="always"><%= LanguageUtil.get(pageContext, "always") %></option>
							<option <%= (changeFrequency.equals("hourly")) ? "selected" : "" %> value="hourly"><%= LanguageUtil.get(pageContext, "hourly") %></option>
							<option <%= (changeFrequency.equals("daily")) ? "selected" : "" %> value="daily"><%= LanguageUtil.get(pageContext, "daily") %></option>
							<option <%= (changeFrequency.equals("weekly")) ? "selected" : "" %> value="weekly"><%= LanguageUtil.get(pageContext, "weekly") %></option>
							<option <%= (changeFrequency.equals("monthly")) ? "selected" : "" %> value="monthly"><%= LanguageUtil.get(pageContext, "monthly") %></option>
							<option <%= (changeFrequency.equals("yearly")) ? "selected" : "" %> value="yearly"><%= LanguageUtil.get(pageContext, "yearly") %></option>
							<option <%= (changeFrequency.equals("never")) ? "selected" : "" %> value="never"><%= LanguageUtil.get(pageContext, "never") %></option>
						</select>
					</td>
				</tr>
				</table>
			</div>
		</td>
	</tr>
</c:if>

</table>