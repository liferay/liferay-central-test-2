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

/**
 * <a href="WorkflowDefinitionMapper.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * The workflow definition mapper is a manager used to map domain classes to
 * workflow definitions.
 * </p>
 *
 * <p>
 * It might be used to map a domain or model class to be used in conjunction
 * with a certain workflow definition.
 * </p>
 *
 * <p>
 * The mapper works independently of the underlying engine, it is just a mapping
 * between domain classes as workflow definitions.
 * </p>
 *
 * @author Micha Kiener
 *
 */
public interface WorkflowDefinitionMapper {

	/**
	 * Returns the workflow definition name mapped to the given domain class, if
	 * any, <code>null</code> otherwise.
	 *
	 * @param domainClass the domain class to return its mapped workflow
	 * definition
	 * @return the name of the workflow definition mapped to the given domain
	 * class or <code>null</code> if no such mapping available
	 */
	public String getWorkflowDefinitionName(Class<?> domainClass);

	/**
	 * Returns <code>true</code>, if there is a mapping for the given domain
	 * class, <code>false</code>, if no mapping was made.
	 *
	 * @param domainClass the domain class to check for having a mapping to a
	 * workflow definition name
	 * @return <code>true</code>, if there is a mapping between the given domain
	 * class and a workflow definition
	 */
	public boolean hasWorkflowDefinitionMapping(Class<?> domainClass);

	/**
	 * Specifies a new mapping between a domain class and a workflow definition
	 * name.
	 *
	 * @param domainClass the domain class to be mapped
	 * @param workflowDefinitionId the workflow definition name as the mapping
	 * to the given domain class
	 * @return the old workflow definition name, if any previously set,
	 * <code>null</code> otherwise
	 */
	public String setWorkflowDefinitionMapping(
		Class<?> domainClass, String workflowDefinitionName);

}