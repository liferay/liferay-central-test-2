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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.FileImpl;

import java.io.FileInputStream;

import java.util.List;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Service;
import org.apache.abdera.model.Workspace;

/**
 * <a href="CMISTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class CMISTest extends BaseCMISTest {

	public void testCRUD() throws Exception {
		String rootChildrenUrl = null;

		for (Workspace workspace : getService().getWorkspaces()) {
			rootChildrenUrl = CMISUtil.getCollectionUrl(
				workspace, getConstants().COLLECTION_ROOT);

			assertNotNull(rootChildrenUrl);

			Entry folder = CMISUtil.getEntry(
				rootChildrenUrl, _LIFERAY_TEST,
				_cmisConstants.BASE_TYPE_FOLDER);

			if (folder != null) {
				CMISUtil.delete(folder);
			}
		}

		FileInputStream fis = new FileInputStream(_TEST_FILE);

		Entry folderEntry = CMISUtil.createFolder(
			rootChildrenUrl, _LIFERAY_TEST);

		Entry fileEntry = CMISUtil.createDocument(folderEntry, "test.txt", fis);

		assertNotNull(fileEntry);

		String repositoryFileContent = StringUtil.read(
			CMISUtil.getInputStream(fileEntry));

		String originalFileContent = _fileUtil.read(_TEST_FILE);

		assertEquals(originalFileContent, repositoryFileContent);

		List<String> fileNames = CMISUtil.getFolders(folderEntry);

		assertEquals(1, fileNames.size());

		assertEquals("test.txt", fileNames.get(0));

		CMISUtil.delete(fileEntry);
		CMISUtil.delete(folderEntry);
	}

	protected CMISConstants getConstants() {
		return _cmisConstants;
	}

	protected Service getService() throws Exception {
		return CMISUtil.getService();
	}

	private static final String _LIFERAY_TEST = "Liferay Test";

	private static final String _TEST_FILE =
		"portal-impl/test/com/liferay/portal/cmis/dependencies/test.txt";

	private static CMISConstants _cmisConstants = CMISConstants.getInstance();
	private static FileImpl _fileUtil = FileImpl.getInstance();

}