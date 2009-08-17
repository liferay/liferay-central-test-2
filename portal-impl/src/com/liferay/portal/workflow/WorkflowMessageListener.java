
package com.liferay.portal.workflow;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowException;

import java.util.Formatter;

/**
 * <a href="WorkflowMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * The generic message listener for workflow messages. The object (manager) to
 * invoke the method on has to be injected using constructor injection as well
 * as the expected request class to be handled by this listener.
 * </p>
 *
 * @author Micha Kiener
 */
public class WorkflowMessageListener implements MessageListener {

	/**
	 * Constructor using the manager to invoke methods on and the expected
	 * request class this listener will be able to handle.
	 *
	 * @param manager the manager instance to invoke methods on given by the
	 *            payload of the message
	 * @param messageBus the message bus used to receive and send messages
	 */
	public WorkflowMessageListener(Object manager, MessageBus messageBus) {
		_manager = manager;
		_messageBus=messageBus;
	}

	/**
	 * @see com.liferay.portal.kernel.messaging.MessageListener#
	 *		receive(com.liferay.portal.kernel.messaging.Message)
	 */
	public void receive(Message message) {

		Object payload = message.getPayload();
		String responseDestination = message.getResponseDestinationName();

		WorkflowResultContainer resultContainer = new WorkflowResultContainer();
		WorkflowException workflowException = null;
		try {
			// there must always be a payload having the request
			if (payload == null) {
				workflowException =
					new WorkflowException(MISSING_REQUEST_ERROR);
				_log.error(MISSING_REQUEST_ERROR);
			}
			// check for being the right request type
			else if (!WorkflowRequest.class.isAssignableFrom(payload.getClass())) {
				String errorMessage =
					new Formatter().format(
						WRONG_REQUEST_ERROR, WorkflowRequest.class.getName(),
						payload.getClass().getName()).
					toString();

				workflowException = new WorkflowException(errorMessage);
				_log.error(errorMessage);
			}
			else {

				WorkflowRequest request = (WorkflowRequest) payload;
				resultContainer.setResult(request.execute(_manager));
			}
		}
		catch (WorkflowException ex) {
			workflowException = new WorkflowException(EXECUTE_ERROR);
			_log.error(EXECUTE_ERROR, ex);

		}
		finally {
			if (Validator.isNotNull(responseDestination)) {
				Message responseMessage =
					MessageBusUtil.createResponseMessage(message);
				resultContainer.setException(workflowException);
				responseMessage.setPayload(resultContainer);
				_messageBus.sendMessage(
					responseDestination, responseMessage);
			}

		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(WorkflowMessageListener.class);
	private static String MISSING_REQUEST_ERROR = "Missing workflow request.";
	private static String WRONG_REQUEST_ERROR =
		"Payload type is not from expected type [%s], but was [%s]";
	private static String EXECUTE_ERROR = "Unable to execute request.";
	private final Object _manager;
	private final MessageBus _messageBus;

}