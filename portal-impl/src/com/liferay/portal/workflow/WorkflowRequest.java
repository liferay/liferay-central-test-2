
package com.liferay.portal.workflow;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.aopalliance.intercept.MethodInvocation;

import com.liferay.portal.kernel.workflow.CallingUserId;
import com.liferay.portal.kernel.workflow.UserCredential;
import com.liferay.portal.kernel.workflow.UserCredentialFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;

/**
 * <a href="WorkflowRequest.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * The workflow request acts as the basic implementation of a workflow API
 * request transported through the message bus. It contains the {@link Method}
 * to be invoked on its target as well as the arguments needed for the
 * invocation.
 * </p>
 * 
 * <p>
 * Additionally, most methods provide a {@link UserCredential} representing the
 * calling user, some of its attributes as well as its role set to avoid the
 * underlying adapter or workflow engine to call back the portal to get those
 * information. Every method most likely being transitional (not read only)
 * should provide its calling user.
 * </p>
 * 
 * <p>
 * The calling user (if it was passed on) will be made available through
 * {@link WorkflowCallingUser#getCallingUserCredential()} before the method is
 * invoked and is immediately removed, if the method was finished.
 * </p>
 * 
 * @author Micha Kiener
 */
public class WorkflowRequest implements Serializable {

	/**
	 * Creates a new workflow request based on the given invocation provided by
	 * Spring using a method interceptor.
	 * 
	 * @param invocation
	 * @throws WorkflowException is thrown, if the request could not be
	 *             initialized
	 */
	public WorkflowRequest(MethodInvocation invocation)
		throws WorkflowException {
		_method = invocation.getMethod();
		_args = invocation.getArguments();
		_callingUserCredential = inspectForCallingUserCredential(invocation);
	}

	/**
	 * Inspects for an argument being the calling user id given by the
	 * {@link CallingUserId} annotation on the method, if available.
	 * 
	 * @param invocation the invocation creating this request
	 * @return the credential of the calling user, if available,
	 *         <code>null</code> otherwise
	 * @throws WorkflowException is thrown, if the credential could not be
	 *             created
	 */
	protected UserCredential inspectForCallingUserCredential(
		MethodInvocation invocation)
		throws WorkflowException {
		CallingUserId id =
			invocation.getMethod().getAnnotation(CallingUserId.class);
		if (id == null) {
			return null;
		}

		// get the calling user id as annotated and create a user credential out
		// of it through the factory
		return UserCredentialFactoryUtil.createCredential((Long) invocation.getArguments()[id.value()]);
	}

	/**
	 * Invokes the method being submitted by the request through reflection and
	 * returns its value. Before the method is being invoked, the calling user
	 * credential is attached, if available, and after the invocation removed
	 * again through a thread local.
	 * 
	 * @param implObject the object to invoke the method on
	 * @return the return value as being provided by the method invocation
	 * @throws WorkflowException is thrown, if any exception occurred within the
	 *             invocation
	 */
	public Object execute(Object implObject)
		throws WorkflowException {
		try {
			// attach the calling user credential, if available
			WorkflowCallingUser.setCallingUserCredential(
				_callingUserCredential);
			return _method.invoke(implObject, _args);
		}
		catch (Exception ex) {
			throw new WorkflowException(ex);
		}
		finally {
			// always make sure to remove the attached user credential as this
			// thread might be reused
			WorkflowCallingUser.setCallingUserCredential(null);
		}
	}

	/**
	 * @return the credential of the calling user, if any provided by the
	 *		   request, <code>null</code> otherwise
	 */
	public UserCredential getCallingUserCredential() {
		return _callingUserCredential;
	}

	public boolean hasReturnValue() {
		return _method.getReturnType() != Void.TYPE;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final WorkflowRequest other = (WorkflowRequest) obj;
		if (this._method != other._method
			&& (this._method == null || !this._method.equals(other._method))) {
			return false;
		}
		if (!Arrays.deepEquals(this._args, other._args)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + (this._method != null ? this._method.hashCode() : 0);
		hash = 97 * hash + Arrays.deepHashCode(this._args);
		return hash;
	}

	@Override
	public String toString() {
		return "RequestClass:" + getClass() +
			"[Method:" + _method + ", " +
			"Args:" + _args + "]";
	}

	private Object[] _args;
	private UserCredential _callingUserCredential;
	private Method _method;

}