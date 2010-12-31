/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.webdav.methods;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.WebDAVProps;
import com.liferay.portal.service.WebDAVPropsLocalServiceUtil;
import com.liferay.util.servlet.ServletResponseUtil;
import com.liferay.util.xml.DocUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Chow
 */
public abstract class BasePropMethodImpl implements Method {

	public static final String ALLPROP = "allprop";

	public static final Tuple ALLPROP_PAIR = new Tuple(
		ALLPROP, WebDAVUtil.DAV_URI);

	public static final String CREATIONDATE = "creationdate";

	public static final Tuple CREATIONDATE_PAIR = new Tuple(
		CREATIONDATE, WebDAVUtil.DAV_URI);

	public static final String DISPLAYNAME = "displayname";

	public static final Tuple DISPLAYNAME_PAIR = new Tuple(
		DISPLAYNAME, WebDAVUtil.DAV_URI);

	public static final String GETCONTENTLENGTH = "getcontentlength";

	public static final Tuple GETCONTENTLENGTH_PAIR = new Tuple(
		GETCONTENTLENGTH, WebDAVUtil.DAV_URI);

	public static final String GETCONTENTTYPE = "getcontenttype";

	public static final Tuple GETCONTENTTYPE_PAIR = new Tuple(
		GETCONTENTTYPE, WebDAVUtil.DAV_URI);

	public static final String GETLASTMODIFIED = "getlastmodified";

	public static final Tuple GETLASTMODIFIED_PAIR = new Tuple(
		GETLASTMODIFIED, WebDAVUtil.DAV_URI);

	public static final String LOCKDISCOVERY = "lockdiscovery";

	public static final Tuple LOCKDISCOVERY_PAIR = new Tuple(
		LOCKDISCOVERY, WebDAVUtil.DAV_URI);

	public static final String RESOURCETYPE = "resourcetype";

	public static final Tuple RESOURCETYPE_PAIR = new Tuple(
		RESOURCETYPE, WebDAVUtil.DAV_URI);

	protected Element addElement(Element element, String name) {
		QName qName = SAXReaderUtil.createQName(name, WebDAVUtil.DAV_URI);

		return element.addElement(qName);
	}

	protected Element addElement(Element element, String name1, String name2) {
		Element childElement = addElement(element, name1);

		return addElement(childElement, name2);
	}

	protected void addResponse(String href, Element multistatusElement)
		throws Exception {

		Element responseElement = DocUtil.add(
			multistatusElement, "response", WebDAVUtil.DAV_URI);

		DocUtil.add(responseElement, "href", WebDAVUtil.DAV_URI, href);

		Element propstatElement = DocUtil.add(
			responseElement, "propstat", WebDAVUtil.DAV_URI);

		DocUtil.add(
			propstatElement, "status", WebDAVUtil.DAV_URI,
			"HTTP/1.1 404 Not Found");
	}

	protected void addResponse(
			WebDAVRequest webDavRequest, Resource resource, Set<Tuple> props,
			Element multistatus)
		throws Exception {

		// Make a deep copy of the props

		props = new HashSet<Tuple>(props);

		// Start building multistatus response

		Element responseElement = DocUtil.add(
			multistatus, "response", WebDAVUtil.DAV_URI);

		DocUtil.add(
			responseElement, "href", WebDAVUtil.DAV_URI, resource.getHREF());

		// Build success and failure propstat elements

		Element successStatElement = DocUtil.add(
			responseElement, "propstat", WebDAVUtil.DAV_URI);
		Element successPropElement = DocUtil.add(
			successStatElement, "prop", WebDAVUtil.DAV_URI);
		Element failureStatElement = DocUtil.add(
			responseElement, "propstat", WebDAVUtil.DAV_URI);
		Element failurePropElement = DocUtil.add(
			failureStatElement, "prop", WebDAVUtil.DAV_URI);

		boolean hasSuccess = false;
		boolean hasFailure = false;

		// Check DAV properties

		if (props.contains(ALLPROP_PAIR)) {
			props.remove(ALLPROP_PAIR);

			if (resource.isCollection()) {
				props.addAll(_ALL_COLLECTION_PROPS);
			}
			else {
				props.addAll(_ALL_SIMPLE_PROPS);
			}
		}

		if (props.contains(CREATIONDATE_PAIR)) {
			props.remove(CREATIONDATE_PAIR);

			DocUtil.add(
				successPropElement, CREATIONDATE, WebDAVUtil.DAV_URI,
				resource.getCreateDate());

			hasSuccess = true;
		}

		if (props.contains(DISPLAYNAME_PAIR)) {
			props.remove(DISPLAYNAME_PAIR);

			DocUtil.add(
				successPropElement, DISPLAYNAME, WebDAVUtil.DAV_URI,
				resource.getDisplayName());

			hasSuccess = true;
		}

		if (props.contains(GETLASTMODIFIED_PAIR)) {
			props.remove(GETLASTMODIFIED_PAIR);

			DocUtil.add(
				successPropElement, GETLASTMODIFIED, WebDAVUtil.DAV_URI,
				resource.getModifiedDate());

			hasSuccess = true;
		}

		if (props.contains(GETCONTENTTYPE_PAIR)) {
			props.remove(GETCONTENTTYPE_PAIR);

			DocUtil.add(
				successPropElement, GETCONTENTTYPE, WebDAVUtil.DAV_URI,
				resource.getContentType());

			hasSuccess = true;
		}

		if (props.contains(GETCONTENTLENGTH_PAIR)) {
			props.remove(GETCONTENTLENGTH_PAIR);

			if (!resource.isCollection()) {
				DocUtil.add(
					successPropElement, GETCONTENTLENGTH, WebDAVUtil.DAV_URI,
					resource.getSize());

				hasSuccess = true;
			}
			else {
				DocUtil.add(
					failurePropElement, GETCONTENTLENGTH, WebDAVUtil.DAV_URI);

				hasFailure = true;
			}
		}

		if (props.contains(LOCKDISCOVERY_PAIR)) {
			props.remove(LOCKDISCOVERY_PAIR);

			Lock lock = resource.getLock();

			if (lock != null) {
				long now = System.currentTimeMillis();

				long timeRemaining =
					(lock.getExpirationDate().getTime() - now) / Time.SECOND;

				if (timeRemaining <= 0) {
					timeRemaining = 1;
				}

				Element lockDiscoveryElement = addElement(
					successPropElement, LOCKDISCOVERY);

				Element activeLockElement = addElement(
					lockDiscoveryElement, "activelock");

				addElement(activeLockElement, "locktype", "write");
				addElement(activeLockElement, "lockscope", "exclusive");

				if (resource.isCollection()) {
					DocUtil.add(
						activeLockElement, "depth", WebDAVUtil.DAV_URI,
						"Infinity");
				}

				DocUtil.add(
					activeLockElement, "owner", WebDAVUtil.DAV_URI,
					lock.getOwner());
				DocUtil.add(
					activeLockElement, "timeout", WebDAVUtil.DAV_URI,
					"Second-" + timeRemaining);

				if (webDavRequest.getUserId() == lock.getUserId()) {
					Element lockTokenElement = addElement(
						activeLockElement, "locktoken", "href");

					lockTokenElement.addText(
						"opaquelocktoken:" + lock.getUuid());
				}

				hasSuccess = true;
			}
			else {
				DocUtil.add(
					failurePropElement, LOCKDISCOVERY, WebDAVUtil.DAV_URI);

				hasFailure = true;
			}
		}

		if (props.contains(RESOURCETYPE_PAIR)) {
			props.remove(RESOURCETYPE_PAIR);

			Element resourceTypeElement = DocUtil.add(
				successPropElement, RESOURCETYPE, WebDAVUtil.DAV_URI);

			if (resource.isCollection()) {
				DocUtil.add(
					resourceTypeElement, "collection", WebDAVUtil.DAV_URI);
			}

			hasSuccess = true;
		}

		// Check remaining properties against custom properties

		WebDAVProps webDavProps = WebDAVPropsLocalServiceUtil.getWebDAVProps(
			webDavRequest.getCompanyId(), resource.getClassName(),
			resource.getPrimaryKey());

		Set<Tuple> customProps = webDavProps.getPropsSet();

		for (Tuple tuple : props) {
			String name = (String)tuple.getObject(0);
			Namespace namespace = (Namespace)tuple.getObject(1);

			String prefix = namespace.getPrefix();
			String uri = namespace.getURI();

			if (customProps.contains(tuple)) {
				String text = webDavProps.getText(name, prefix, uri);

				DocUtil.add(successPropElement, name, namespace, text);

				hasSuccess = true;
			}
			else {
				DocUtil.add(failurePropElement, name, namespace);

				hasFailure = true;
			}
		}

		// Clean up propstats

		if (hasSuccess) {
			DocUtil.add(
				successStatElement, "status", WebDAVUtil.DAV_URI,
				"HTTP/1.1 200 OK");
		}
		else {
			responseElement.remove(successStatElement);
		}

		if (!hasSuccess && hasFailure) {
			DocUtil.add(
				failureStatElement, "status", WebDAVUtil.DAV_URI,
				"HTTP/1.1 404 Not Found");
		}
		else {
			responseElement.remove(failureStatElement);
		}
	}

	protected void addResponse(
			WebDAVStorage storage, WebDAVRequest webDavRequest,
			Resource resource, Set<Tuple> props, Element multistatusElement,
			long depth)
		throws Exception {

		addResponse(webDavRequest, resource, props, multistatusElement);

		if (resource.isCollection() && (depth != 0)) {
			Iterator<Resource> itr = storage.getResources(
				webDavRequest).iterator();

			while (itr.hasNext()) {
				resource = itr.next();

				addResponse(
					webDavRequest, resource, props, multistatusElement);
			}
		}
	}

	protected int writeResponseXML(
			WebDAVRequest webDavRequest, Set<Tuple> props)
		throws Exception {

		WebDAVStorage storage = webDavRequest.getWebDAVStorage();

		long depth = WebDAVUtil.getDepth(webDavRequest.getHttpServletRequest());

		Document document = SAXReaderUtil.createDocument();

		Element multistatusElement = SAXReaderUtil.createElement(
			SAXReaderUtil.createQName("multistatus", WebDAVUtil.DAV_URI));

		document.setRootElement(multistatusElement);

		Resource resource = storage.getResource(webDavRequest);

		if (resource != null) {
			addResponse(
				storage, webDavRequest, resource, props, multistatusElement,
				depth);

			String xml = document.formattedString(StringPool.FOUR_SPACES);

			if (_log.isDebugEnabled()) {
				_log.debug("Response XML\n" + xml);
			}

			// Set the status prior to writing the XML

			int status = WebDAVUtil.SC_MULTI_STATUS;

			HttpServletResponse response =
				webDavRequest.getHttpServletResponse();

			response.setContentType(ContentTypes.TEXT_XML_UTF8);
			response.setStatus(status);

			try {
				ServletResponseUtil.write(response, xml);

				response.flushBuffer();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}

			return status;
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No resource found for " + storage.getRootPath() +
						webDavRequest.getPath());
			}

			return HttpServletResponse.SC_NOT_FOUND;
		}
	}

	private static final List<Tuple> _ALL_COLLECTION_PROPS = Arrays.asList(
		new Tuple[] {
			CREATIONDATE_PAIR, DISPLAYNAME_PAIR, GETLASTMODIFIED_PAIR,
			GETCONTENTTYPE_PAIR, LOCKDISCOVERY_PAIR, RESOURCETYPE_PAIR
		});

	private static final List<Tuple> _ALL_SIMPLE_PROPS = Arrays.asList(
		new Tuple[] {
			CREATIONDATE_PAIR, DISPLAYNAME_PAIR, GETLASTMODIFIED_PAIR,
			GETCONTENTTYPE_PAIR, GETCONTENTLENGTH_PAIR, LOCKDISCOVERY_PAIR,
			RESOURCETYPE_PAIR
		});

	private static Log _log = LogFactoryUtil.getLog(BasePropMethodImpl.class);

}