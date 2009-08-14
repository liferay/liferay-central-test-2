
package com.liferay.portal.workflow;

import com.liferay.portal.kernel.workflow.WorkflowException;

/**
 * <a href="WorkflowResultContainer.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * The workflow result container is used to return an argument or an exception
 * which was the result of the workflow engine invocation through the
 * {@link ManagerProxyAdvice} which is weaved into the abstract proxies.
 * </p>
 * 
 * @author Micha Kiener
 */
public class WorkflowResultContainer {

	public WorkflowResultContainer() {
	}

	public WorkflowResultContainer(Object result) {
		_result = result;
	}

	/**
	 * @return the exception which was thrown by invoking the method, if {@link
	 *		   #hasError()} returns <code>true</code>, otherwise this method
	 *		   just returns <code>null</code>
	 */
	public WorkflowException getException() {
		return _exception;
	}

	/**
	 * @return the result of the method invocation
	 */
	public Object getResult() {
		return _result;
	}

	/**
	 * @return <code>true</code>, if the method invocation produced an exception
	 *		   rather than returning a value
	 */
	public boolean hasError() {
		return _exception != null;
	}

	public void setException(WorkflowException exception) {
		_exception = exception;
	}

	public void setResult(Object result) {
		_result = result;
	}

	private WorkflowException _exception;
	private Object _result;

}