/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.webdav;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.security.Principal;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.webdav.IWebdavStorage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="AbstractWebDAVStorage.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public abstract class AbstractWebDAVStorage implements IWebdavStorage {

	public void begin(Principal principal, Hashtable params)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Begin " + principal);

			//PropertiesUtil.list(params, System.out);
		}

		_principal = principal;
		_params = params;
	}

	public void checkAuthentication() throws SecurityException {
		if (_log.isDebugEnabled()) {
			//_log.debug("Check authentication");
		}
	}

	public void commit() throws IOException {
		if (_log.isDebugEnabled()) {
			//_log.debug("Commit");
		}
	}

	public void rollback() throws IOException {
		if (_log.isDebugEnabled()) {
			//_log.debug("Rollback");
		}
	}

	public boolean objectExists(String uri) throws IOException {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Check object " + uri);
			}

			String[] uriValues = getURIValues(uri);

			String companyId = uriValues[0];
			String groupId = uriValues[1];
			uri = uriValues[2];

			boolean returnValue = false;

			if ((companyId == null) || (groupId == null) ||
				(uri.equals(StringPool.SLASH))) {

				returnValue = true;
			}
			else {
				returnValue = isAvailable(companyId, groupId, uri);
			}

			if (_log.isDebugEnabled()) {
				if (returnValue) {
					_log.debug("Object exists");
				}
				else {
					_log.debug("Object does NOT exist");
				}
			}

			return returnValue;
		}
		catch (PortalException pe) {
			throw new IOException(pe.getMessage());
		}
		catch (SystemException se) {
			throw new IOException(se.getMessage());
		}
	}

	public boolean isFolder(String uri) throws IOException {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Check folder " + uri);
			}

			if (!objectExists(uri)) {
				return false;
			}

			String[] uriValues = getURIValues(uri);

			String companyId = uriValues[0];
			String groupId = uriValues[1];
			uri = uriValues[2];

			boolean returnValue = false;

			if ((companyId == null) || (groupId == null) ||
				(uri.equals(StringPool.SLASH))) {

				returnValue = true;
			}
			else {
				returnValue = isFolder(companyId, groupId, uri);
			}

			if (_log.isDebugEnabled()) {
				if (returnValue) {
					_log.debug("Object is a folder");
				}
				else {
					_log.debug("Object is NOT a folder");
				}
			}

			return returnValue;
		}
		catch (PortalException pe) {
			throw new IOException(pe.getMessage());
		}
		catch (SystemException se) {
			throw new IOException(se.getMessage());
		}
	}

	public boolean isResource(String uri) throws IOException {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Check resource " + uri);
			}

			if (!objectExists(uri)) {
				return false;
			}

			String[] uriValues = getURIValues(uri);

			String companyId = uriValues[0];
			String groupId = uriValues[1];
			uri = uriValues[2];

			boolean returnValue = false;

			if ((companyId == null) || (groupId == null) ||
				(uri.equals(StringPool.SLASH))) {

				returnValue = false;
			}
			else {
				returnValue = !isFolder(companyId, groupId, uri);
			}

			if (_log.isDebugEnabled()) {
				if (returnValue) {
					_log.debug("Object is a resource");
				}
				else {
					_log.debug("Object is NOT a resource");
				}
			}

			return returnValue;
		}
		catch (PortalException pe) {
			throw new IOException(pe.getMessage());
		}
		catch (SystemException se) {
			throw new IOException(se.getMessage());
		}
	}

	public void createFolder(String uri) throws IOException {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Add folder " + uri);
			}

			String[] uriValues = getURIValues(uri);

			String companyId = uriValues[0];
			String groupId = uriValues[1];
			uri = uriValues[2];

			addFolder(companyId, groupId, uri);
		}
		catch (PortalException pe) {
			throw new IOException(pe.getMessage());
		}
		catch (SystemException se) {
			throw new IOException(se.getMessage());
		}
	}

	public void createResource(String uri) throws IOException {
		if (_log.isDebugEnabled()) {
			_log.debug("Add resource " + uri);
		}
	}

	public void setResourceContent(
			String uri, InputStream content, String contentType,
			String characterEncoding)
		throws IOException {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Update resource " + uri);
			}

			String[] uriValues = getURIValues(uri);

			String companyId = uriValues[0];
			String groupId = uriValues[1];
			uri = uriValues[2];

			addResource(
				companyId, groupId, uri, content, contentType,
				characterEncoding);
		}
		catch (PortalException pe) {
			throw new IOException(pe.getMessage());
		}
		catch (SystemException se) {
			throw new IOException(se.getMessage());
		}
	}

	public Date getLastModified(String uri) throws IOException {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Get modified date " + uri);
			}

			String[] uriValues = getURIValues(uri);

			String companyId = uriValues[0];
			String groupId = uriValues[1];
			uri = uriValues[2];

			Date returnValue = null;

			if ((companyId == null) || (groupId == null) ||
				(uri.equals(StringPool.SLASH))) {

				returnValue = new Date();
			}
			else {
				returnValue = getModifiedDate(companyId, groupId, uri);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Modified date is " + returnValue);
			}

			return returnValue;
		}
		catch (PortalException pe) {
			throw new IOException(pe.getMessage());
		}
		catch (SystemException se) {
			throw new IOException(se.getMessage());
		}
	}

	public Date getCreationDate(String uri) throws IOException {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Get create date " + uri);
			}

			String[] uriValues = getURIValues(uri);

			String companyId = uriValues[0];
			String groupId = uriValues[1];
			uri = uriValues[2];

			Date returnValue = null;

			if ((companyId == null) || (groupId == null) ||
				(uri.equals(StringPool.SLASH))) {

				returnValue = new Date();
			}
			else {
				returnValue = getCreateDate(companyId, groupId, uri);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Create date is " + returnValue);
			}

			return returnValue;
		}
		catch (PortalException pe) {
			throw new IOException(pe.getMessage());
		}
		catch (SystemException se) {
			throw new IOException(se.getMessage());
		}
	}

	public String[] getChildrenNames(String uri) throws IOException {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Get children " + uri);
			}

			String[] uriValues = getURIValues(uri);

			String companyId = uriValues[0];
			String groupId = uriValues[1];
			uri = uriValues[2];

			if ((companyId != null) && (groupId == null)) {
				Map groupParams = new HashMap();

				groupParams.put("usersGroups", getUserId());

				List communities = GroupLocalServiceUtil.search(
					companyId, null, null, groupParams, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

				String[] childrenNames = new String[communities.size()];

				for (int i = 0; i < communities.size(); i++) {
					Group group = (Group)communities.get(i);

					childrenNames[i] = group.getName();
				}

				return childrenNames;
			}
			else if ((companyId != null) && (groupId != null)) {
				return getObjects(companyId, groupId, uri);
			}
			else {
				return new String[0];
			}
		}
		catch (PortalException pe) {
			throw new IOException(pe.getMessage());
		}
		catch (SystemException se) {
			throw new IOException(se.getMessage());
		}
	}

	public InputStream getResourceContent(String uri)
		throws IOException {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Get resource content " + uri);
			}

			String[] uriValues = getURIValues(uri);

			String companyId = uriValues[0];
			String groupId = uriValues[1];
			uri = uriValues[2];

			if ((companyId == null) || (groupId == null) ||
				(uri.equals(StringPool.SLASH))) {

				return new ByteArrayInputStream(new byte[0]);
			}
			else {
				return getResource(companyId, groupId, uri);
			}
		}
		catch (PortalException pe) {
			throw new IOException(pe.getMessage());
		}
		catch (SystemException se) {
			throw new IOException(se.getMessage());
		}
	}

	public long getResourceLength(String uri) throws IOException {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Get resource content length " + uri);
			}

			String[] uriValues = getURIValues(uri);

			String companyId = uriValues[0];
			String groupId = uriValues[1];
			uri = uriValues[2];

			long returnValue = 0;

			if ((companyId == null) || (groupId == null) ||
				(uri.equals(StringPool.SLASH))) {

				returnValue = 0;
			}
			else {
				returnValue = getResourceSize(companyId, groupId, uri);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Resource size is " + returnValue);
			}

			return returnValue;
		}
		catch (PortalException pe) {
			throw new IOException(pe.getMessage());
		}
		catch (SystemException se) {
			throw new IOException(se.getMessage());
		}
	}

	public void removeObject(String uri) throws IOException {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Delete object " + uri);
			}

			String[] uriValues = getURIValues(uri);

			String companyId = uriValues[0];
			String groupId = uriValues[1];
			uri = uriValues[2];

			deleteObject(companyId, groupId, uri);
		}
		catch (PortalException pe) {
			throw new IOException(pe.getMessage());
		}
		catch (SystemException se) {
			throw new IOException(se.getMessage());
		}
	}

	protected String getUserId() {
		return "liferay.com.1";
		//return _principal.getName();
	}

	protected String getPlid(String groupId) {
		return LayoutImpl.PUBLIC + groupId + ".1";
	}

	protected String[] getURIValues(String uri)
		throws PortalException, SystemException {

		String[] uriValues = new String[3];

		String prefix = (String)_params.get("uri-prefix");

		if (_log.isDebugEnabled()) {
			//_log.debug("URI " + uri);
		}

		int pos = uri.indexOf(prefix);

		if (pos != -1) {
			uri = uri.substring(pos + prefix.length(), uri.length());
		}

		if (!uri.startsWith(StringPool.SLASH)) {
			uri = StringPool.SLASH + uri;
		}

		if (!uri.endsWith(StringPool.SLASH)) {
			uri = uri + StringPool.SLASH;
		}

		if (uri.indexOf("//") != -1) {
			uri = StringUtil.replace(uri, "//", StringPool.SLASH);
		}

		String companyId = null;
		String groupId = null;

		if (!uri.equals(StringPool.SLASH)) {
			pos = uri.indexOf(StringPool.SLASH, 1);

			companyId = uri.substring(1, pos);

			uri = uri.substring(pos, uri.length());

			if (!uri.equals(StringPool.SLASH)) {
				pos = uri.indexOf(StringPool.SLASH, 1);

				String groupName = uri.substring(1, pos);

				Group group = GroupLocalServiceUtil.getGroup(
					companyId, groupName);

				groupId = group.getGroupId();

				uri = uri.substring(pos, uri.length());
			}
		}

		uriValues[0] = companyId;
		uriValues[1] = groupId;
		uriValues[2] = uri;

		return uriValues;
	}

	protected abstract void addFolder(
			String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException;

	protected abstract void addResource(
			String companyId, String groupId, String uri, InputStream content,
			String contentType, String characterEncoding)
		throws IOException, PortalException, SystemException;

	protected abstract void deleteObject(
			String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException;

	protected abstract Date getCreateDate(
			String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException;

	protected abstract Date getModifiedDate(
			String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException;

	protected abstract String[] getObjects(
			String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException;

	protected abstract InputStream getResource(
			String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException;

	protected abstract long getResourceSize(
			String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException;

	protected abstract boolean isAvailable(
			String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException;

	protected abstract boolean isFolder(
			String companyId, String groupId, String uri)
		throws IOException, PortalException, SystemException;

	private static Log _log = LogFactory.getLog(AbstractWebDAVStorage.class);

	private Principal _principal;
	private Hashtable _params;

}