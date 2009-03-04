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

<%@ include file="/html/common/init.jsp" %>

<%
String referer = null;

String refererParam = request.getParameter(WebKeys.REFERER);

if (Validator.isNotNull(refererParam) && HttpUtil.hasDomain(refererParam)) {
	try {
		String securityMode = PropsValues.REFERER_URL_SECURITY_MODE;

		String domain = StringUtil.split(HttpUtil.getDomain(refererParam), StringPool.COLON)[0];

		if (securityMode.equals("domain")) {
			String[] allowedDomains = PropsValues.REFERER_URL_DOMAIN_ALLOWED;

			if ((allowedDomains.length > 0) && !ArrayUtil.contains(allowedDomains, domain)) {
				refererParam = null;
			}
		}
		else if (securityMode.equals("ip")) {
			String[] allowedIp = PropsValues.REFERER_URL_IP_ALLOWED;

			String serverIp = request.getServerName();

			InetAddress inetAddress = InetAddress.getByName(domain);

			if ((allowedIp.length > 0) && !ArrayUtil.contains(allowedIp, inetAddress.getHostAddress())) {
				if (!serverIp.equals(inetAddress.getHostAddress()) || !ArrayUtil.contains(allowedIp, "SERVER_IP")) {
					refererParam = null;
				}
			}
		}
	}
	catch (UnknownHostException uhe) {
		refererParam = null;
	}
}

String refererRequest = (String)request.getAttribute(WebKeys.REFERER);
String refererSession = (String)session.getAttribute(WebKeys.REFERER);

if ((refererParam != null) && (!refererParam.equals(StringPool.NULL)) && (!refererParam.equals(StringPool.BLANK))) {
	referer = refererParam;
}
else if ((refererRequest != null) && (!refererRequest.equals(StringPool.NULL)) && (!refererRequest.equals(StringPool.BLANK))) {
	referer = refererRequest;
}
else if ((refererSession != null) && (!refererSession.equals(StringPool.NULL)) && (!refererSession.equals(StringPool.BLANK))) {
	referer = refererSession;
}
else {
	if (referer == null) {
		referer = themeDisplay.getPathMain();
	}
}
%>