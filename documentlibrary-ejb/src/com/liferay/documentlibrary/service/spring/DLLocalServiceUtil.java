/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.documentlibrary.service.spring;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.util.lucene.Hits;

import java.io.InputStream;

import javax.jcr.Node;
import javax.jcr.Session;

/**
 * <a href="DLLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLLocalServiceUtil {

	public static InputStream getFileAsStream(
			String companyId, String repositoryId, String fileName)
		throws PortalException, SystemException {

		try {
			DLLocalService dlLocalService = DLLocalServiceFactory.getService();

			return dlLocalService.getFileAsStream(
				companyId, repositoryId, fileName);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public static InputStream getFileAsStream(
			String companyId, String repositoryId, String fileName,
			double versionNumber)
		throws PortalException, SystemException {

		try {
			DLLocalService dlLocalService = DLLocalServiceFactory.getService();

			return dlLocalService.getFileAsStream(
				companyId, repositoryId, fileName, versionNumber);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public static Node getFileContentNode(
			Session session, String companyId, String repositoryId,
			String fileName, double versionNumber)
		throws PortalException, SystemException {

		try {
			DLLocalService dlLocalService = DLLocalServiceFactory.getService();

			return dlLocalService.getFileContentNode(
				session, companyId, repositoryId, fileName, versionNumber);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public static Hits search(
			String companyId, String portletId, String groupId,
			String[] repositoryIds, String keywords)
		throws PortalException, SystemException {

		try {
			DLLocalService dlLocalService = DLLocalServiceFactory.getService();

			return dlLocalService.search(
				companyId, portletId, groupId, repositoryIds, keywords);
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

}