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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <a href="BrowserLauncher.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BrowserLauncher implements Runnable {

	public void run() {
		if (Validator.isNull(PropsValues.BROWSER_LAUNCHER_URL)) {
			return;
		}

		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(3000);
			}
			catch (InterruptedException ie) {
			}

			try {
				URL url = new URL(PropsValues.BROWSER_LAUNCHER_URL);

				HttpURLConnection urlc =
					(HttpURLConnection)url.openConnection();

				int responseCode = urlc.getResponseCode();

				if (responseCode == HttpURLConnection.HTTP_OK) {
					try {
						launchBrowser();
					}
					catch (Exception e2) {
					}

					break;
				}
			}
			catch (Exception e1) {
			}
		}
	}

	protected void launchBrowser() throws Exception {
		String os = System.getProperty("os.name").toLowerCase();

		Runtime runtime = Runtime.getRuntime();

		if (os.indexOf("mac") >= 0) {
			launchBrowserApple(runtime);
		}
		else if (os.indexOf("win") >= 0) {
			launchBrowserWindows(runtime);
		}
		else {
			launchBrowserUnix(runtime);
		}
	}

	protected void launchBrowserApple(Runtime runtime) throws Exception {
		runtime.exec("open " + PropsValues.BROWSER_LAUNCHER_URL);
	}

	protected void launchBrowserUnix(Runtime runtime) throws Exception {
		if (_BROWSERS.length == 0) {
			runtime.exec(new String[] {"sh", "-c", StringPool.BLANK});
		}

		StringBundler sb = new StringBundler(_BROWSERS.length * 5 - 1);
		
		for (int i = 0; i < _BROWSERS.length; i++) {
			if (i != 0) {
				sb.append(" || ");
			}

			sb.append(_BROWSERS[i]);
			sb.append(" \"");
			sb.append(PropsValues.BROWSER_LAUNCHER_URL);
			sb.append("\" ");
		}

		runtime.exec(new String[] {"sh", "-c", sb.toString()});
	}

	protected void launchBrowserWindows(Runtime runtime) throws Exception {
		runtime.exec("cmd.exe /c start " + PropsValues.BROWSER_LAUNCHER_URL);
	}

	private static final String[] _BROWSERS = {
		"firefox", "mozilla", "konqueror", "opera"
	};

}