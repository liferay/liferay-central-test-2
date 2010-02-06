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

package com.liferay.portal.webdav.methods;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.webdav.InvalidRequestException;
import com.liferay.portal.webdav.WebDAVException;
import com.liferay.portal.webdav.WebDAVRequest;
import com.liferay.portal.webdav.WebDAVUtil;
import com.liferay.util.xml.XMLFormatter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="PropfindMethodImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class PropfindMethodImpl extends BasePropMethodImpl implements Method {

	public int process(WebDAVRequest webDavRequest) throws WebDAVException {
		try {
			Set<Tuple> props = getProps(webDavRequest);

			return writeResponseXML(webDavRequest, props);
		}
		catch (InvalidRequestException ire) {
			return HttpServletResponse.SC_BAD_REQUEST;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	protected Set<Tuple> getProps(WebDAVRequest webDavRequest)
		throws InvalidRequestException {

		try {
			Set<Tuple> props = new HashSet<Tuple>();

			HttpServletRequest request = webDavRequest.getHttpServletRequest();

			String xml = new String(
				FileUtil.getBytes(request.getInputStream()));

			if (Validator.isNull(xml)) {

				// Windows XP does not generate an xml request so the PROPFIND
				// must be generated manually. See LEP-4920.

				return generateProps(props);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Request XML: \n" +
						XMLFormatter.toString(xml, StringPool.FOUR_SPACES));
			}

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			if (root.element("allprop") != null) {

				// Generate props if <allprop> tag is used. See LEP-6162.

				return generateProps(props);
			}

			Element prop = root.element("prop");

			Iterator<Element> itr = prop.elements().iterator();

			while (itr.hasNext()) {
				Element el = itr.next();

				String prefix = el.getNamespacePrefix();
				String uri = el.getNamespaceURI();

				Namespace namespace = null;

				if (uri.equals(WebDAVUtil.DAV_URI.getURI())) {
					namespace = WebDAVUtil.DAV_URI;
				}
				else if (Validator.isNull(prefix)) {
					namespace = SAXReaderUtil.createNamespace(uri);
				}
				else {
					namespace = SAXReaderUtil.createNamespace(prefix, uri);
				}

				props.add(new Tuple(el.getName(), namespace));
			}

			return props;
		}
		catch (Exception e) {
			throw new InvalidRequestException(e);
		}
	}

	protected Set<Tuple> generateProps(Set<Tuple> props) {
		props.add(new Tuple("displayname", WebDAVUtil.DAV_URI));
		props.add(new Tuple("resourcetype", WebDAVUtil.DAV_URI));
		props.add(new Tuple("getcontenttype", WebDAVUtil.DAV_URI));
		props.add(new Tuple("getcontentlength", WebDAVUtil.DAV_URI));
		props.add(new Tuple("getlastmodified", WebDAVUtil.DAV_URI));
		props.add(new Tuple("lockdiscovery", WebDAVUtil.DAV_URI));
		props.add(new Tuple("checked-in", WebDAVUtil.DAV_URI));
		props.add(new Tuple("checked-out", WebDAVUtil.DAV_URI));
		props.add(new Tuple("version-name", WebDAVUtil.DAV_URI));

		return props;
	}

	private static Log _log = LogFactoryUtil.getLog(PropfindMethodImpl.class);

}