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

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.messaging.proxy.ProxyMode;

import java.util.Map;

@MessagingProxy(mode = ProxyMode.SYNC)
/**
 * <a href="WorkflowEngineManager.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This manager gives support information about the workflow engine
 * implementation.
 * </p>
 *
 * @author Micha Kiener
 */
public interface WorkflowEngineManager {

	/**
	 * Returns a map containing engine specific information. Make sure the
	 * <code>toString</code> method of the objects within the map return a human
	 * readable string representing the information in that object.
	 *
	 * @return any optional information about the engine (could be an empty map
	 *		   but must never be <code>null</code>)
	 */
	public Map<String, Object> getAdditionalInformation();

	/**
	 * Returns the underlying provider object for the workflow engine to get
	 * access to its API, if available. The result of this method, however, is
	 * implementation specific and not guaranteed to be supported. It should be
	 * the engine, a configuration, a factory or whatsoever is needed as the
	 * entry point into the workflow system. If the engine is not running within
	 * the same VM, this method could possibly return a proxy object or
	 * <code>null</code> if not supported.
	 *
	 * @return the underlying workflow engine provider or <code>null</code> if
	 *		   not supported
	 */
	public Object getDelegate();

	/**
	 * @return the version of the underlying workflow engine
	 */
	public String getVersion();

	/**
	 * Returns the unique key of the workflow engine which could by used to
	 * create related names based on conventions (e.g. xxx-test.jar). This is a
	 * technical name rather than a human readable name. The key must only
	 * contain lower case letters without spaces and numbers.
	 *
	 * @return the technical name of the underlying engine
	 */
	public String getWorkflowEngineKey();

	/**
	 * Returns the name of the workflow engine in a human readable fashion (e.g.
	 * Edoras, Intalio, jBPM, etc).
	 *
	 * @return the name of the underlying engine
	 */
	public String getWorkflowEngineName();

	/**
	 * Returns <code>true</code>, if the underlying engine supports activities
	 * to be executed globally. A global activity is a node which can be
	 * triggered at any time, regardless the current state and it will actually
	 * not alter the current state. If the engine does not support such global
	 * activities, it must throw an exception if attempting to execute one using
	 * the method {@link
	 * WorkflowInstanceManager#signalWorkflowInstanceByActivity(long, String,
	 * Map, long, Map)}.
	 *
	 * @return <code>true</code>, if the engine supports global activities
	 */
	public boolean isSupportsGlobalActivities();

	/**
	 * Returns <code>true</code>, if the underlying workflow system supports
	 * versioning of workflow definitions. If versioning is not supported, the
	 * version number of a workflow definition is most likely ignored by the
	 * engine.
	 *
	 * @return <code>true</code>, if the workflow engine supports versioning
	 */
	public boolean isSupportsWorkflowDefinitionVersioning();

}