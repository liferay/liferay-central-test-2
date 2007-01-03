/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.documentlibrary.service.jms;

import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="IndexConsumer.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class IndexConsumer implements MessageListener {

	public void consume() {
		try {
			QueueConnectionFactory qcf = IndexQCFUtil.getQCF();
			QueueConnection con = qcf.createQueueConnection();

			QueueSession session = con.createQueueSession(
				false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = (Queue)IndexQueueUtil.getQueue();

			QueueReceiver subscriber = session.createReceiver(queue);

			subscriber.setMessageListener(this);

			con.start();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void onMessage(Message msg) {
		try {
			ObjectMessage objMsg = (ObjectMessage)msg;

			MethodWrapper methodWrapper = (MethodWrapper)objMsg.getObject();

			_onMessage(methodWrapper);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _onMessage(MethodWrapper methodWrapper) throws Exception {
		MethodInvoker.invoke(methodWrapper);
	}

	private static Log _log = LogFactory.getLog(IndexConsumer.class);

}