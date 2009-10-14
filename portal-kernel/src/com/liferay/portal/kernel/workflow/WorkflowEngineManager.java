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

import java.util.Map;

/**
 * <a href="WorkflowEngineManager.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * This manager is supporting information about the engine currently running.
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
	 *         but must never be <code>null</code>)
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
	 *         not supported
	 */
	public Object getDelegate();

	/**
	 * @return the version of the underlying workflow engine
	 */
	public String getVersion();

	/**
	 * Returns the name of the workflow engine which should be unique (e.g.
	 * edoras, jBPM, etc).
	 * 
	 * @return the name of the underlying engine
	 */
	public String getWorkflowEngineName();

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
