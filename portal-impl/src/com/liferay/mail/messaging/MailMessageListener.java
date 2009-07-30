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

package com.liferay.mail.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.mail.MailEngine;

import javax.mail.internet.InternetAddress;

/**
 * <a href="MailMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MailMessageListener implements MessageListener {

	public void receive(Message message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	public void doMailMessage(MailMessage mailMessage) throws Exception {
		InternetAddress[] auditTrail = InternetAddress.parse(
			PropsUtil.get(PropsKeys.MAIL_AUDIT_TRAIL));

		if (auditTrail.length > 0) {
			InternetAddress[] bcc = mailMessage.getBCC();

			if (bcc != null) {
				InternetAddress[] allBCC = new InternetAddress[
					bcc.length + auditTrail.length];

				ArrayUtil.combine(bcc, auditTrail, allBCC);

				mailMessage.setBCC(allBCC);
			}
			else {
				mailMessage.setBCC(auditTrail);
			}
		}

		MailEngine.send(mailMessage);
	}

	public void doMethodWrapper(MethodWrapper methodWrapper) throws Exception {
		MethodInvoker.invoke(methodWrapper);
	}

	public void doReceive(Message message) throws Exception {
		Object payload = message.getPayload();

		if (payload instanceof MailMessage) {
			doMailMessage((MailMessage)payload);
		}
		else if (payload instanceof MethodWrapper) {
			doMethodWrapper((MethodWrapper)payload);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MailMessageListener.class);

}