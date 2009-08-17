
package com.liferay.portal.workflow;

import com.liferay.portal.kernel.workflow.UserCredential;

/**
 * <a href="WorkflowCallingUser.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This class represents the {@link UserCredential} attached to a
 * {@link ThreadLocal}, made available through
 * {@link #getCallingUserCredential()}. Most workflow API invocations provides
 * the calling user however, some read only methods do not, so the calling user
 * might not be present for all requests and thus the method
 * {@link #getCallingUserCredential()} could return <code>null</code>.
 * </p>
 *
 * @author Micha Kiener
 *
 * @see WorkflowRequest WorkflowRequest for more information about the transport
 *      mechanism used for the credential
 */
public class WorkflowCallingUser {

	/**
	 * @return the calling user's credential, if available, <code>null</code>
	 *		   otherwise
	 */
	public static UserCredential getCallingUserCredential() {
		return _callingUser.get();
	}

	/**
	 * This method should only be used by the {@link WorkflowRequest} to set the
	 * calling user credential before invoking the method on the underlying
	 * workflow engine. <b>IMPORTANT</b> It has to make sure, to remove the
	 * credential in a finally after the method execution by invoking this
	 * method again by passing in <code>null</code>.
	 *
	 * @param userCredential the current user credential to be made available
	 */
	public static void setCallingUserCredential(UserCredential userCredential) {
		_callingUser.set(userCredential);
	}

	/**
	 * The thread local holding the calling user's credential, if any.
	 */
	private static final ThreadLocal<UserCredential> _callingUser =
		new ThreadLocal<UserCredential>();

}