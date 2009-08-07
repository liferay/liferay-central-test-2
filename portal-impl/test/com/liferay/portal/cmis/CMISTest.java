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
import com.liferay.portal.kernel.util.StringPool;

import java.io.FileInputStream;
import java.io.InputStream;

import java.util.List;

import junit.framework.TestCase;

import org.apache.abdera.model.Collection;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Service;
import org.apache.abdera.model.Workspace;

/**
 * <a href="CMISTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class CMISTest extends TestCase {

	public void testService() throws Exception {
		Service service = CMISUtil.getService();

		assertNotNull(service);

		for (Workspace ws : service.getWorkspaces()) {
			assertNotNull(ws);

			CMISRepositoryInfo info =
				ws.getFirstChild(_constants.REPOSITORY_INFO);

			assertNotNull(info.getId());
			assertEquals(_constants.VERSION, info.getVersionSupported());

			boolean found = false;

			for (Collection col : ws.getCollections()) {
				String type = col.getAttributeValue(_constants.COLLECTION_TYPE);

				if (_constants.COLLECTION_ROOT_CHILDREN.equals(type)) {
					found = true;
				}
			}

			assertEquals(true, found);
		}
	}

	public void testCRUD() throws Exception {
		FileInputStream fis = new FileInputStream(_TEST_FILE);

		Entry folder =
			CMISUtil.createFolder(_BASE_CHILDREN_URL, _LIFERAY_TEST);

		Entry file = CMISUtil.createDocument(folder, "test.txt", fis);

		assertNotNull(file);

		String repositoryFile = _readStream(CMISUtil.getInputStream(file));

		String originalFile = _readStream(new FileInputStream(_TEST_FILE));

		assertEquals(originalFile, repositoryFile);

		List<String> list = CMISUtil.getFolderList(folder);

		assertEquals(1, list.size());

		assertEquals("test.txt", list.get(0));

		CMISUtil.delete(file);
		CMISUtil.delete(folder);
	}

	protected void setUp() throws Exception {
		Entry folder = CMISUtil.getEntry(
			_BASE_CHILDREN_URL, _LIFERAY_TEST, _constants.BASE_TYPE_FOLDER);

		if (folder != null) {
			CMISUtil.delete(folder);
		}
	}

	private String _readStream(InputStream is) throws Exception {
		String str = null;

		try {
			byte[] bytes = new byte[is.available()];

			is.read(bytes);

			str = new String(bytes, StringPool.UTF8);
		}
		finally {
			if (is != null) {
				is.close();
			}
		}

		return str;
	}

	private static final CMISConstants _constants = CMISUtil.getConstants();

	private static final String _BASE_CHILDREN_URL =
		"http://localhost:8080/alfresco/service/api/path/workspace/SpacesStore/Company%20Home/descendants";

	private static final String _TEST_FILE =
		"portal-impl/test/com/liferay/portal/cmis/dependencies/test.txt";

	private static final String _LIFERAY_TEST = "Liferay Test";

}