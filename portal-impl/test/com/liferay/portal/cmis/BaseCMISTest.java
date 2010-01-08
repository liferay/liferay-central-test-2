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

package com.liferay.portal.cmis;

import com.liferay.portal.cmis.model.CMISConstants;
import com.liferay.portal.cmis.model.CMISRepositoryInfo;

import junit.framework.TestCase;

import org.apache.abdera.model.Service;
import org.apache.abdera.model.Workspace;

/**
 * <a href="BaseCMISTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public abstract class BaseCMISTest extends TestCase {

	public void testService() throws Exception {
		Service service = getService();

		assertNotNull(service);

		for (Workspace workspace : service.getWorkspaces()) {
			assertNotNull(workspace);

			CMISRepositoryInfo cmisRepositoryInfo = workspace.getFirstChild(
				getConstants().REPOSITORY_INFO);

			assertNotNull(cmisRepositoryInfo);
			assertNotNull(cmisRepositoryInfo.getId());
			assertEquals(
				getConstants().VERSION,
				cmisRepositoryInfo.getVersionSupported());

			String rootChildrenUrl = CMISUtil.getCollectionUrl(
				workspace, getConstants().COLLECTION_ROOT);

			assertNotNull(rootChildrenUrl);
		}
	}

	protected abstract CMISConstants getConstants();

	protected abstract Service getService() throws Exception;

}