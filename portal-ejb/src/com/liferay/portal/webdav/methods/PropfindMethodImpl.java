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

package com.liferay.portal.webdav.methods;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.webdav.BaseResourceImpl;
import com.liferay.portal.webdav.Resource;
import com.liferay.portal.webdav.WebDAVException;
import com.liferay.portal.webdav.WebDAVRequest;
import com.liferay.portal.webdav.WebDAVStorage;
import com.liferay.portal.webdav.WebDAVUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;
import com.liferay.util.xml.DocUtil;
import com.liferay.util.xml.XMLFormatter;

import java.io.OutputStream;
import java.io.StringReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

/**
 * <a href="PropfindMethodImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PropfindMethodImpl implements Method {

	public void process(WebDAVRequest webDavReq) throws WebDAVException {
		try {
			HttpServletResponse res = webDavReq.getHttpServletResponse();

			String xml = getResponseXML(webDavReq);

			byte[] byteArray = xml.getBytes("UTF-8");

			res.setContentLength(byteArray.length);
			res.setContentType("text/xml; charset=UTF-8");
			res.setStatus(WebDAVUtil.SC_MULTI_STATUS);

			OutputStream out = res.getOutputStream();

			try {
				if (!res.isCommitted()) {
					out.write(byteArray);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
			finally {
				try {
					out.flush();
				}
				catch (Exception e) {
					_log.warn(e);
				}

				try {
					out.close();
				}
				catch (Exception e) {
					_log.warn(e);
				}
			}
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	protected void addResponse(
			Resource resource, List props, Element multistatus,
			DateFormat createDateFormat, DateFormat modifiedDateFormat)
		throws Exception {

		Element response = multistatus.addElement("D:response");

		DocUtil.add(response, "D:href", resource.getHREF());

		Element propstat = response.addElement("D:propstat");

		Element prop = propstat.addElement("D:prop");

		DocUtil.add(prop, "D:displayname", resource.getDisplayName());
		DocUtil.add(
			prop, "D:creationdate",
			createDateFormat.format(resource.getCreateDate()));
		DocUtil.add(
			prop, "D:getlastmodified",
			modifiedDateFormat.format(resource.getModifiedDate()));

		if (resource.isFolder()) {
			DocUtil.add(prop, "D:getcontenttype", "httpd/unix-directory");
		}
		else {
			DocUtil.add(
				prop, "D:getcontentlength", String.valueOf(resource.getSize()));
		}

		Element resourcetype = prop.addElement("D:resourcetype");

		if (resource.isFolder()) {
			resourcetype.addElement("D:collection");
		}

		if (props.size() == 0) {
			DocUtil.add(prop, "D:getetag", "\"19504-0-a4075c30\"");

			Element supportedlock = prop.addElement("D:supportedlock");

			Element lockentry = supportedlock.addElement("D:lockentry");

			Element lockscope = lockentry.addElement("D:lockscope");

			lockscope.addElement("D:exclusive");

			Element locktype = lockentry.addElement("D:locktype");

			locktype.addElement("D:write");

			lockentry = supportedlock.addElement("D:lockentry");

			lockscope = lockentry.addElement("D:lockscope");

			lockscope.addElement("D:shared");

			locktype = lockentry.addElement("D:locktype");

			locktype.addElement("D:write");

			prop.addElement("D:lockdiscovery");
		}

		DocUtil.add(propstat, "D:status", "HTTP/1.1 200 OK");

		if (props.size() > 0) {
			propstat = response.addElement("D:propstat");

			prop = propstat.addElement("D:prop");

			for (int i = 0; i < props.size(); i++) {
				String propName = (String)props.get(i);

				prop.addElement("D:" + propName);
			}

			DocUtil.add(propstat, "D:status", "HTTP/1.1 404 Not Found");
		}
	}

	protected int getDepth(WebDAVRequest webDavReq) {
		HttpServletRequest req = webDavReq.getHttpServletRequest();

		String depth = req.getHeader("Depth");

		if (_log.isDebugEnabled()) {
			_log.debug("Depth " + depth);
		}

		return GetterUtil.getInteger(depth, 1);
	}

	protected DateFormat getCreateDateFormat() {
		return new SimpleDateFormat(_CREATE_DATE_FORMAT, Locale.US);
	}

	protected DateFormat getModifiedDateFormat() {
		return new SimpleDateFormat(_MODIFIED_DATE_FORMAT, Locale.US);
	}

	protected List getProps(WebDAVRequest webDavReq) throws Exception {
		HttpServletRequest req = webDavReq.getHttpServletRequest();

		List props = new ArrayList();

		String xml = WebDAVUtil.getRequestXML(req);

		if (Validator.isNull(xml)) {
			return props;
		}

		SAXReader reader = new SAXReader();

		Document doc = reader.read(new StringReader(xml));

		Element root = doc.getRootElement();

		Element prop = root.element("prop");

		Iterator itr = prop.elements().iterator();

		while (itr.hasNext()) {
			Element el = (Element)itr.next();

			props.add(el.getName());
		}

		return props;
	}

	protected String getResponseXML(WebDAVRequest webDavReq) throws Exception {
		WebDAVStorage storage = webDavReq.getWebDAVStorage();
		String companyId = webDavReq.getCompanyId();
		long groupId = webDavReq.getGroupId();
		DateFormat createDateFormat = getCreateDateFormat();
		DateFormat modifiedDateFormat = getModifiedDateFormat();

		List props = getProps(webDavReq);

		DocumentFactory docFactory = DocumentFactory.getInstance();

		Document doc = docFactory.createDocument();

		Element multistatus = docFactory.createElement(
			new QName("multistatus", new Namespace("D", "DAV:")));

		doc.setRootElement(multistatus);

		if (Validator.isNotNull(companyId)) {
			if (groupId == 0) {
				addResponse(
					new BaseResourceImpl(
						storage.getRootPath() + StringPool.SLASH + companyId,
						companyId, true),
					props, multistatus, createDateFormat, modifiedDateFormat);

				if (props.size() > 0) {
					Iterator itr = storage.getCommunities(webDavReq).iterator();

					while (itr.hasNext()) {
						Resource resource = (Resource)itr.next();

						addResponse(
							resource, props, multistatus, createDateFormat,
							modifiedDateFormat);
					}
				}
			}
			else {
				Iterator itr = storage.getResources(webDavReq).iterator();

				while (itr.hasNext()) {
					Resource resource = (Resource)itr.next();

					addResponse(
						resource, props, multistatus, createDateFormat,
						modifiedDateFormat);
				}
			}
		}

		String xml = XMLFormatter.toString(doc, "    ");

		if (_log.isDebugEnabled()) {
			_log.debug("Response XML\n" + xml);
		}

		return xml;
	}

	private static final String _CREATE_DATE_FORMAT =
		"yyyy-MM-dd'T'HH:mm:ss'Z'";

	private static final String _MODIFIED_DATE_FORMAT =
		"EEE, dd MMM yyyy HH:mm:ss zzz";

	private static Log _log = LogFactory.getLog(PropfindMethodImpl.class);

}