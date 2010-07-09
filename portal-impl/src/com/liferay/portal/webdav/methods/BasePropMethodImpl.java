/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
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

	protected void addResponse(
			WebDAVStorage storage, WebDAVRequest webDavRequest,
			Resource resource, Set<Tuple> props, Element multistatus,
			long depth)
		throws Exception {

		addResponse(webDavRequest, resource, props, multistatus);

		if (resource.isCollection() && (depth != 0)) {
			Iterator<Resource> itr = storage.getResources(
				webDavRequest).iterator();

			while (itr.hasNext()) {
				resource = itr.next();

				addResponse(webDavRequest, resource, props, multistatus);
			}
		}
	}

	protected void addResponse(
			WebDAVRequest webDavRequest, Resource resource, Set<Tuple> props,
			Element multistatus)
		throws Exception {

		// Make a deep copy of the props

		props = new HashSet<Tuple>(props);

		// Start building multistatus response

		Element response = DocUtil.add(
			multistatus, "response", WebDAVUtil.DAV_URI);

		DocUtil.add(response, "href", WebDAVUtil.DAV_URI, resource.getHREF());

		// Build success and failure propstat elements

		Element successStat = DocUtil.add(
			response, "propstat", WebDAVUtil.DAV_URI);
		Element successProp = DocUtil.add(
			successStat, "prop", WebDAVUtil.DAV_URI);
		Element failureStat = DocUtil.add(
			response, "propstat", WebDAVUtil.DAV_URI);
		Element failureProp = DocUtil.add(
			failureStat, "prop", WebDAVUtil.DAV_URI);

		boolean hasSuccess = false;
		boolean hasFailure = false;

		// Check DAV properties

		if (props.contains(_ALL_PROPS_PAIR)) {
			props.remove(_ALL_PROPS_PAIR);

			if (resource.isCollection()) {
				props.addAll(_ALL_COLLECTION_PROPS);
			}
			else {
				props.addAll(_ALL_SIMPLE_PROPS);
			}
		}

		if (props.contains(_CREATIONDATE_PAIR)) {
			props.remove(_CREATIONDATE_PAIR);

			DocUtil.add(
				successProp, _CREATIONDATE, WebDAVUtil.DAV_URI,
				resource.getCreateDate());

			hasSuccess = true;
		}

		if (props.contains(_DISPLAYNAME_PAIR)) {
			props.remove(_DISPLAYNAME_PAIR);

			DocUtil.add(
				successProp, _DISPLAYNAME, WebDAVUtil.DAV_URI,
				resource.getDisplayName());

			hasSuccess = true;
		}

		if (props.contains(_GETLASTMODIFIED_PAIR)) {
			props.remove(_GETLASTMODIFIED_PAIR);

			DocUtil.add(
				successProp, _GETLASTMODIFIED, WebDAVUtil.DAV_URI,
				resource.getModifiedDate());

			hasSuccess = true;
		}

		if (props.contains(_GETCONTENTTYPE_PAIR)) {
			props.remove(_GETCONTENTTYPE_PAIR);

			DocUtil.add(
				successProp, _GETCONTENTTYPE, WebDAVUtil.DAV_URI,
				resource.getContentType());

			hasSuccess = true;
		}

		if (props.contains(_GETCONTENTLENGTH_PAIR)) {
			props.remove(_GETCONTENTLENGTH_PAIR);

			if (!resource.isCollection()) {
				DocUtil.add(
					successProp, _GETCONTENTLENGTH, WebDAVUtil.DAV_URI,
					resource.getSize());

				hasSuccess = true;
			}
			else {
				DocUtil.add(
					failureProp, _GETCONTENTLENGTH, WebDAVUtil.DAV_URI);

				hasFailure = true;
			}
		}

		if (props.contains(_RESOURCETYPE_PAIR)) {
			props.remove(_RESOURCETYPE_PAIR);

			Element resourceType =
				DocUtil.add(successProp, _RESOURCETYPE, WebDAVUtil.DAV_URI);

			if (resource.isCollection()) {
				DocUtil.add(resourceType, "collection", WebDAVUtil.DAV_URI);
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

				DocUtil.add(successProp, name, namespace, text);

				hasSuccess = true;
			}
			else {
				DocUtil.add(failureProp, name, namespace);

				hasFailure = true;
			}
		}

		// Clean up propstats

		if (hasSuccess) {
			DocUtil.add(
				successStat, "status", WebDAVUtil.DAV_URI, "HTTP/1.1 200 OK");
		}
		else {
			response.remove(successStat);
		}

		if (!hasSuccess && hasFailure) {
			DocUtil.add(
				failureStat, "status", WebDAVUtil.DAV_URI,
				"HTTP/1.1 404 Not Found");
		}
		else {
			response.remove(failureStat);
		}
	}

	protected void addResponse(String href, Element multistatus)
		throws Exception {

		Element response = DocUtil.add(
			multistatus, "response", WebDAVUtil.DAV_URI);

		DocUtil.add(response, "href", WebDAVUtil.DAV_URI, href);

		Element propstat = DocUtil.add(
			response, "propstat", WebDAVUtil.DAV_URI);

		DocUtil.add(
			propstat, "status", WebDAVUtil.DAV_URI, "HTTP/1.1 404 Not Found");
	}

	protected int writeResponseXML(
			WebDAVRequest webDavRequest, Set<Tuple> props)
		throws Exception {

		WebDAVStorage storage = webDavRequest.getWebDAVStorage();

		long depth = WebDAVUtil.getDepth(webDavRequest.getHttpServletRequest());

		Document doc = SAXReaderUtil.createDocument();

		Element multistatus = SAXReaderUtil.createElement(
			SAXReaderUtil.createQName("multistatus", WebDAVUtil.DAV_URI));

		doc.setRootElement(multistatus);

		Resource resource = storage.getResource(webDavRequest);

		if (resource != null) {
			addResponse(
				storage, webDavRequest, resource, props, multistatus, depth);

			String xml = doc.formattedString(StringPool.FOUR_SPACES);

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

	private static final String _ALLPROPS = "allprops";

	private static final String _CREATIONDATE = "creationdate";

	private static final String _DISPLAYNAME = "displayname";

	private static final String _GETLASTMODIFIED = "getlastmodified";

	private static final String _GETCONTENTTYPE = "getcontenttype";

	private static final String _GETCONTENTLENGTH = "getcontentlength";

	private static final String _RESOURCETYPE = "resourcetype";

	private static final Tuple _ALL_PROPS_PAIR =
		new Tuple(_ALLPROPS, WebDAVUtil.DAV_URI);

	private static final Tuple _CREATIONDATE_PAIR =
		new Tuple(_CREATIONDATE, WebDAVUtil.DAV_URI);

	private static final Tuple _DISPLAYNAME_PAIR =
		new Tuple(_DISPLAYNAME, WebDAVUtil.DAV_URI);

	private static final Tuple _GETLASTMODIFIED_PAIR =
		new Tuple(_GETCONTENTLENGTH, WebDAVUtil.DAV_URI);

	private static final Tuple _GETCONTENTTYPE_PAIR =
		new Tuple(_GETCONTENTTYPE, WebDAVUtil.DAV_URI);

	private static final Tuple _GETCONTENTLENGTH_PAIR =
		new Tuple(_GETLASTMODIFIED, WebDAVUtil.DAV_URI);

	private static final Tuple _RESOURCETYPE_PAIR =
		new Tuple(_RESOURCETYPE, WebDAVUtil.DAV_URI);

	private final List<Tuple> _ALL_COLLECTION_PROPS = Arrays.asList(
		new Tuple[] {
			_CREATIONDATE_PAIR, _DISPLAYNAME_PAIR, _GETLASTMODIFIED_PAIR,
			_GETCONTENTTYPE_PAIR, _RESOURCETYPE_PAIR
		});

	private final List<Tuple> _ALL_SIMPLE_PROPS = Arrays.asList(
		new Tuple[] {
			_CREATIONDATE_PAIR, _DISPLAYNAME_PAIR, _GETLASTMODIFIED_PAIR,
			_GETCONTENTTYPE_PAIR, _GETCONTENTLENGTH_PAIR, _RESOURCETYPE_PAIR
		});

	private static Log _log = LogFactoryUtil.getLog(BasePropMethodImpl.class);

}