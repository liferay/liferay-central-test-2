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

package com.liferay.applets.chat;

import com.liferay.lawt.AppletFrame;

import com.lyrisoft.chat.client.ChatClientApplet;

import java.io.File;
import java.io.FileInputStream;

import java.util.Properties;

/**
 * <a href="LiferayChat.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jesus Perez
 * @author  Jorge Ferrer
 * @author  Brian Wing Shun Chan
 *
 */
public class LiferayChat extends ChatClientApplet {

	public static void main(String args[]) {
		Properties props = new Properties();

		try {
			File file = new File("chat.properties");

			props.load(new FileInputStream(file));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		new AppletFrame(new LiferayChat(), props, 600, 400);
	}

	public Properties getProperties(String name) {
		Properties props = new Properties();

		try {
			File file = new File(
				"../../portal-web/docroot/applets/chat/resources/" + name);

			props.load(new FileInputStream(file));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return props;
	}

}