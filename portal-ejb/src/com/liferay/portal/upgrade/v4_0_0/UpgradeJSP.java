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

package com.liferay.portal.upgrade.v4_0_0;

import com.liferay.util.FileUtil;
import com.liferay.util.StringUtil;

import java.io.File;

import org.apache.tools.ant.DirectoryScanner;

/**
 * <a href="UpgradeJSP.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeJSP {

	public static void main(String[] args) {
		try {
			_renameTagLibaries();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void _renameTagLibaries() throws Exception {
		String basedir = "../portal-web/";

		DirectoryScanner ds = new DirectoryScanner();

		ds.setIncludes(new String[] {"**\\*.jsp"});
		ds.setBasedir(basedir);

		ds.scan();

		String[] files = ds.getIncludedFiles();

		for (int i = 0; i < files.length; i++) {
			File file = new File(basedir + files[i]);

			String content = FileUtil.read(file);

			if (content.indexOf("<liferay:") != -1) {
				content = StringUtil.replace(
					content,
					new String[] {
						"liferay:actionURL",
						"liferay:renderURL",
						"liferay:runtime-portlet",
						"liferay:encrypt",
						"liferay:permissionsURL",
						"liferay:box",
						"liferay:breadcrumb",
						"liferay:calendar",
						"liferay:error",
						"liferay:icon",
						"liferay:input",
						"liferay:journal-content-search",
						"liferay:page-iterator",
						"liferay:search",
						"liferay:success",
						"liferay:table-iterator",
						"liferay:toggle",
						"liferay:upload-progress",
						"liferay:include",
						"liferay:param"
					},
					new String[] {
						"liferay-portlet:actionURL",
						"liferay-portlet:renderURL",
						"liferay-portlet:runtime",
						"liferay-security:encrypt",
						"liferay-security:permissionsURL",
						"liferay-ui:box",
						"liferay-ui:breadcrumb",
						"liferay-ui:calendar",
						"liferay-ui:error",
						"liferay-ui:icon",
						"liferay-ui:input",
						"liferay-ui:journal-content-search",
						"liferay-ui:page-iterator",
						"liferay-ui:search",
						"liferay-ui:success",
						"liferay-ui:table-iterator",
						"liferay-ui:toggle",
						"liferay-ui:upload-progress",
						"liferay-util:include",
						"liferay-util:param"
					});

				FileUtil.write(file, content, true);
			}

			if (content.indexOf("liferay:search") != -1) {
				content = StringUtil.replace(
					content, "liferay:search", "liferay-ui:search");

				FileUtil.write(file, content, true);
			}

			if (content.indexOf("<theme:") != -1) {
				content = StringUtil.replace(
					content,
					new String[] {
						"<theme:", "</theme:"
					},
					new String[] {
						"<liferay-theme:", "</liferay-theme:"
					});

				FileUtil.write(file, content, true);
			}
		}
	}

}