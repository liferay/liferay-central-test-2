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

import com.lyrisoft.chat.client.ChatClientApplet;
import com.lyrisoft.chat.client.gui.IChatRoom;
import com.lyrisoft.chat.client.gui.IConsole;
import com.lyrisoft.chat.client.gui.ILogin;
import com.lyrisoft.chat.client.gui.awt102.AppletGUIFactory;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Image;

/**
 * <a href="LiferayGUIFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jesus Perez
 * @author  Jorge Ferrer
 * @author  Brian Wing Shun Chan
 *
 */
public class LiferayGUIFactory extends AppletGUIFactory {

	public LiferayGUIFactory() {
		super();
	}

	public void setApplet(ChatClientApplet applet) {
		super.setApplet(applet);

		Container container = applet;

		while (container != null) {
			if (container instanceof Frame) {
				_frame = (Frame)container;

				break;
			}

			container = container.getParent();
		}

		_frame = new Frame();
	}

	public Image getImage(String name) {
		return null;
	}

	public void playAudioClip(String name) {
	}

	public IChatRoom createChatRoom(String name) {
		return new LiferayChatPanel(this, name, _inputReceiver);
	}

	public ILogin createLoginDialog() {
		return null;
	}

	public void hide(ILogin component) {
	}

	public void show(ILogin component) {
	}

	public void hide(IConsole console) {
	}

	public void show(IConsole console) {
	}

	public synchronized void hide(IChatRoom chatRoom) {
		_applet.removeView((Component)chatRoom);
	}

	public synchronized void show(IChatRoom chatRoom) {
		_applet.setView((Component)chatRoom);
		_mainGui.setStatusGui(chatRoom);
	}

	private Frame _frame;
	private IConsole _console;

}