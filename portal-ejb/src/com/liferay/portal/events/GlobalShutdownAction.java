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

package com.liferay.portal.events;

import com.liferay.portal.im.AIMConnector;
import com.liferay.portal.im.ICQConnector;
import com.liferay.portal.im.MSNConnector;
import com.liferay.portal.im.YMConnector;
import com.liferay.portal.jcr.JCRFactoryUtil;
import com.liferay.portal.job.JobScheduler;
import com.liferay.portal.kernel.log.Jdk14LogFactoryImpl;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.struts.ActionException;
import com.liferay.portal.struts.SimpleAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="GlobalShutdownAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GlobalShutdownAction extends SimpleAction {

	public void run(String[] ids) throws ActionException {

		// Disconnect AIM

		try {
			_log.debug("Shutting down AIM");

			AIMConnector.disconnect();
		}
		catch (Exception e) {
		}

		// Disconnect ICQ

		try {
			_log.debug("Shutting down ICQ");

			ICQConnector.disconnect();
		}
		catch (Exception e) {
		}

		// Disconnect MSN

		try {
			_log.debug("Shutting down MSN");

			MSNConnector.disconnect();
		}
		catch (Exception e) {
		}

		// Disconnect YM

		try {
			_log.debug("Shutting down YM");

			YMConnector.disconnect();
		}
		catch (Exception e) {
		}

		// Shutdown JCR

		try {
			_log.debug("Shutting down JCR");

			JCRFactoryUtil.shutdown();
		}
		catch (Exception e) {
		}

		// Scheduler

		try {
			JobScheduler.shutdown();
		}
		catch (Exception e) {
		}

		// Reset log to default JDK 1.4 logger. This will allow WARs dependent
		// on the portal to still log events after the portal WAR has been
		// destroyed.

		try {
			LogFactoryUtil.setLogFactory(new Jdk14LogFactoryImpl());
		}
		catch (Exception e) {
		}
	}

	private static Log _log = LogFactory.getLog(GlobalShutdownAction.class);

}