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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
Contact selContact = (Contact)request.getAttribute("user.selContact");

String aim = selContact.getAimSn();
String icq = selContact.getIcqSn();
String jabber = selContact.getJabberSn();
String msn = selContact.getMsnSn();
String skype = selContact.getSkypeSn();
String ym = selContact.getYmSn();
%>

<c:if test="<%= Validator.isNotNull(aim) || Validator.isNotNull(icq) || Validator.isNotNull(jabber) || Validator.isNotNull(msn) || Validator.isNotNull(skype) || Validator.isNotNull(ym) %>">
	<h3><liferay-ui:message key="instant-messenger" /></h3>

	<dl class="property-list">
		<c:if test="<%= Validator.isNotNull(aim) %>">
			<dt>
				<liferay-ui:message key="aim" />
			</dt>
			<dd>
				<%= aim %>
			</dd>
		</c:if>

		<c:if test="<%= Validator.isNotNull(icq) %>">
			<dt>
				<liferay-ui:message key="icq" />
			</dt>
			<dd>
				<%= icq %>

				<img alt="" class="instant-messenger-logo" src="http://web.icq.com/whitepages/online?icq=<%= icq %>&img=5" />
			</dd>
		</c:if>

		<c:if test="<%= Validator.isNotNull(jabber) %>">
			<dt>
				<liferay-ui:message key="jabber" />
			</dt>
			<dd>
				<%= jabber %>
			</dd>
		</c:if>

		<c:if test="<%= Validator.isNotNull(msn) %>">
			<dt>
				<liferay-ui:message key="msn" />
			</dt>
			<dd>
				<%= msn %>
			</dd>
		</c:if>

		<c:if test="<%= Validator.isNotNull(skype) %>">
			<dt>
				<liferay-ui:message key="skype" />
			</dt>
			<dd>
				<%= skype %>
				<a href="callto://<%= skype %>"><img alt="<liferay-ui:message key="call-this-user" />" class="instant-messenger-logo" src="http://mystatus.skype.com/smallicon/<%= skype %>" /></a>
			</dd>
		</c:if>

		<c:if test="<%= Validator.isNotNull(ym) %>">
			<dt>
				<liferay-ui:message key="ym" />
			</dt>
			<dd>
				<%= ym %>
				<img alt="" class="instant-messenger-logo" src="http://opi.yahoo.com/online?u=<%= ym %>&m=g&t=0" />
			</dd>
		</c:if>
	</dl>
</c:if>