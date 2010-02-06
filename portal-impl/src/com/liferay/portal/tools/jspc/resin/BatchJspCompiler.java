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

package com.liferay.portal.tools.jspc.resin;

import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.util.FileImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tools.ant.DirectoryScanner;

/**
 * <a href="BatchJspCompiler.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BatchJspCompiler {

	public static void main(String[] args) {
		if (args.length == 2) {
			new BatchJspCompiler(args[0], args[1]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public BatchJspCompiler(String appDir, String classDir) {
		try {
			_appDir = appDir;
			_classDir = classDir;

			DirectoryScanner ds = new DirectoryScanner();

			ds.setBasedir(appDir);
			ds.setIncludes(new String[] {"**\\*.jsp"});

			ds.scan();

			String[] files = ds.getIncludedFiles();

			Arrays.sort(files);

			List<String> fileNames = new ArrayList<String>();

			for (int i = 0; i < files.length; i++) {
				String fileName = files[i];

				fileNames.add(fileName);

				if (((i > 0) && ((i % 200) == 0)) ||
					((i + 1) == files.length)) {

					_compile(fileNames);

					fileNames.clear();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _compile(List<String> fileNames) throws Exception {
		if (fileNames.size() == 0) {
			return;
		}

		List<String> args = new ArrayList<String>();

		args.add("-app-dir");
		args.add(_appDir);
		args.add("-class-dir");
		args.add(_classDir);
		args.addAll(fileNames);

		MethodWrapper methodWrapper = new MethodWrapper(
			"com.caucho.jsp.JspCompiler", "main",
			new Object[] {args.toArray(new String[args.size()])});

		try {
			MethodInvoker.invoke(methodWrapper);
		}
		catch (Exception e) {
			_fileUtil.write(
				_appDir + "/../jspc_error", StackTraceUtil.getStackTrace(e));
		}
	}

	private static FileImpl _fileUtil = FileImpl.getInstance();

	private String _appDir;
	private String _classDir;

}