/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.messaging;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageListener;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MailingListMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 *
 */
public class MailingListMessageListener implements MessageListener {

	public void receive(Object message) {
		throw new UnsupportedOperationException();
	}

	public void receive(String message) {
		MailingListRequest mailingListRequest =
			(MailingListRequest)JSONFactoryUtil.deserialize(message);

		Folder folder = null;

		Message[] mailMessages = null;

		try {
			folder = openInbox(mailingListRequest);

			mailMessages = folder.getMessages();

			for (Message mailMessage : mailMessages) {
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			if ((folder != null) && folder.isOpen()) {
				try {
					folder.setFlags(
						mailMessages, new Flags(Flags.Flag.DELETED), true);
				}
				catch (Exception e) {
				}

				try {
					folder.close(true);
				}
				catch (Exception e) {
				}
			}
		}
	}

	protected Folder openInbox(MailingListRequest mailingListRequest) {
		return null;
	}

	private static Log _log =
		LogFactory.getLog(MailingListMessageListener.class);

}