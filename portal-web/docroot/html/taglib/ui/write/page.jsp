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

<%@ include file="/html/taglib/init.jsp" %>

<%
Object bean = request.getAttribute("liferay-ui:write:bean");
String property = (String)request.getAttribute("liferay-ui:write:property");
%>

<c:choose>
	<c:when test="<%= bean instanceof Organization %>">

		<%
		Organization organization = (Organization)bean;
		%>

		<c:choose>
			<c:when test='<%= property.equals("country") %>'>

				<%
				Address address = organization.getAddress();

				Country country = address.getCountry();

				String countryName = country.getName();

				if (Validator.isNull(countryName)) {
					try {
						country = CountryServiceUtil.getCountry(organization.getCountryId());

						countryName = LanguageUtil.get(pageContext, country.getName());
					}
					catch (Exception e) {
					}
				}
				%>

				<%= countryName %>
			</c:when>
			<c:when test='<%= property.equals("region") %>'>

				<%
				Address address = organization.getAddress();

				Region region = address.getRegion();

				String regionName = region.getName();

				if (Validator.isNull(regionName)) {
					try {
						region = RegionServiceUtil.getRegion(organization.getRegionId());

						regionName = LanguageUtil.get(pageContext, region.getName());
					}
					catch (Exception e) {
					}
				}
				%>

				<%= regionName %>
			</c:when>
		</c:choose>
	</c:when>
	<c:when test="<%= bean instanceof User %>">

		<%
		User user2 = (User)bean;
		%>

		<c:choose>
			<c:when test='<%= property.equals("organizations") %>'>

				<%
				List<Organization> organizations = user2.getOrganizations();
				%>

				<%= HtmlUtil.escape(ListUtil.toString(organizations, "name", StringPool.COMMA_AND_SPACE)) %>
			</c:when>
		</c:choose>
	</c:when>
</c:choose>