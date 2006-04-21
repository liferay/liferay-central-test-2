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

import com.lyrisoft.chat.client.gui.IChatClientInputReceiver;
import com.lyrisoft.chat.client.gui.IChatGUIFactory;
import com.lyrisoft.chat.client.gui.awt102.ChatPanel;

/**
 * <a href="LiferayChatPanel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jesus Perez
 * @author  Jorge Ferrer
 * @author  Brian Wing Shun Chan
 *
 */
public class LiferayChatPanel extends ChatPanel {

	public LiferayChatPanel(IChatGUIFactory factory, String room, String title,
							IChatClientInputReceiver inputReceiver) {

		super(factory, room, title, inputReceiver);
	}

	public LiferayChatPanel(IChatGUIFactory factory, String room,
							IChatClientInputReceiver inputReceiver) {

		this(factory, room, room, inputReceiver);
	}

}