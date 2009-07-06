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

package com.liferay.portal.kernel.workflow.proxy;

import com.liferay.portal.kernel.messaging.DefaultMessageBus;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.ParallelDestination;
import com.liferay.portal.kernel.messaging.sender.DefaultMessageSender;
import com.liferay.portal.kernel.messaging.sender.DefaultSingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.DefaultSingleDestinationSynchronousMessageSender;
import com.liferay.portal.kernel.messaging.sender.DefaultSynchronousMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.uuid.PortalUUIDImpl;

/**
 * <a href="WorkflowManagerProxyTestCase.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class WorkflowManagerProxyTestCase extends TestCase {

	@Override
	protected void setUp() throws Exception {
		messageBus = new DefaultMessageBus();
		DefaultMessageSender sender = new DefaultMessageSender();
		sender.setMessageBus(messageBus);
		SynchronousMessageSender syncSender =
			new DefaultSynchronousMessageSender(
			messageBus, new PortalUUIDImpl(), 120 * 1000);
		messageBus.addDestination(
			new ParallelDestination(DestinationNames.WORKFLOW_DEFINITION));
		messageBus.addDestination(
			new ParallelDestination(DestinationNames.WORKFLOW_INSTANCE));
		messageBus.addDestination(
			new ParallelDestination(DestinationNames.WORKFLOW_TASK));
		messageBus.addDestination(
			new ParallelDestination(
			DestinationNames.MESSAGE_BUS_DEFAULT_RESPONSE));
		definitionMessageSender =
			new DefaultSingleDestinationMessageSender(
			DestinationNames.WORKFLOW_DEFINITION, sender);
		instanceMessageSender =
			new DefaultSingleDestinationMessageSender(
			DestinationNames.WORKFLOW_INSTANCE, sender);
		instanceSynchronousMessageSender =
			new DefaultSingleDestinationSynchronousMessageSender(
			DestinationNames.WORKFLOW_INSTANCE, syncSender);
		taskSynchronousMessageSender =
			new DefaultSingleDestinationSynchronousMessageSender(
			DestinationNames.WORKFLOW_TASK, syncSender);
	}

	@Override
	protected void tearDown() throws Exception {
		messageBus.shutdown();
	}

	protected MessageBus messageBus;
	protected SingleDestinationMessageSender definitionMessageSender;
	protected SingleDestinationMessageSender instanceMessageSender;
	protected SingleDestinationSynchronousMessageSender
		instanceSynchronousMessageSender;
	protected SingleDestinationSynchronousMessageSender
		taskSynchronousMessageSender;

}