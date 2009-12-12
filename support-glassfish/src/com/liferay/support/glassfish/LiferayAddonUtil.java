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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.support.glassfish;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * <a href="LiferayAddonUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raju Uppalapati
 * @author Brian Wing Shun Chan
 */
public class LiferayAddonUtil {

	public static void copyFile(File sourceFile, File destFile)
		throws IOException {

		boolean doCopy = false;

		if (sourceFile.exists() && sourceFile.isFile()) {
			if (!sourceFile.toURI().equals(destFile.toURI())) {
				doCopy = true;

				if (destFile.exists()) {
					if (destFile.canWrite()) {
						destFile.delete();
					}
					else {
						doCopy = false;
					}
				}
			}
		}

		if (doCopy) {
			InputStream is = new FileInputStream(sourceFile);
			OutputStream os = new FileOutputStream(destFile);

			byte[] bytes = new byte[1024];
			int len = 0;

			while ((len = is.read(bytes)) > 0) {
				os.write(bytes, 0, len);
			}

			is.close();
			os.close();
		}
	}

	public static void copyFile(String sourceFile, String destFile)
		throws IOException {

		copyFile(new File(sourceFile), new File(destFile));
	}

	public static String getTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat(_TIMESTAMP);

		return sdf.format(new Date());
	}

	public static File getTmpDir() {
		File tmpDir = new File(
			System.getProperty("java.io.tmpdir") + "/" + getTimestamp());

		if (!tmpDir.exists()) {
			tmpDir.mkdirs();
		}

		return tmpDir;
	}

	private static final String _TIMESTAMP = "yyyyMMddHHmm";

}