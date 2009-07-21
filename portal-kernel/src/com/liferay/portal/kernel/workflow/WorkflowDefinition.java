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

import com.liferay.portal.kernel.resource.ResourceRetriever;

/**
 * <a href="WorkflowDefinition.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * The workflow definition information holds attributes of a workflow definition
 * acting as the model for a workflow.
 * </p>
 *
 * <p>
 * If the engine supports versioning, the workflow definition is identified by
 * its definition id and version, otherwise its id is the only unique
 * identification.
 * </p>
 *
 * @author Micha Kiener
 *
 */
public interface WorkflowDefinition {

	/**
	 * <p>
	 * A workflow definition is being deployed as an archive, having the
	 * definition file (usually an XML file) within a folder called META-INF and
	 * any optional classes the workflow engine would need in order to execute
	 * any actions and activities. The jar being returned as the resource will
	 * be deployed within the lib folder of the workflow plugin.
	 * </p>
	 *
	 * <p>
	 * This method returns <code>null</code>, if this definition has been
	 * queried through the {@link WorkflowDefinitionManager}.
	 * </p>
	 *
	 * @return the resource representing the workflow definition jar to be
	 * deployed
	 */
	public ResourceRetriever getJar();

	/**
	 * Returns the unique name of this workflow definition. If the underlying
	 * engine supports versioning, the name and the version must be unique,
	 * otherwise, the version is ignored.
	 *
	 * @return the workflow definition name
	 */
	public String getWorkflowDefinitionName();

	/**
	 * Returns the version of this workflow definition which builds a composite
	 * id with the definition id. The version is ignored, if the underlying
	 * engine does not support versioning.
	 *
	 * @return the version of this definition
	 */
	public int getWorkflowDefinitionVersion();

}