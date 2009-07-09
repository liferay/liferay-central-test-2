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
 * <a href="WorkflowDefinitionRegistry.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * The workflow definition registry is a manager used to map domain classes to
 * workflow definitions.<br/>
 * It might be used to register a domain or model class to be used in
 * conjunction with a certain workflow definition.
 * </p>
 * 
 * @author Micha Kiener
 * 
 */
public interface WorkflowDefinitionRegistry {
    /**
     * Returns the workflow definition id mapped to the given domain class, if
     * any, <code>null</code> otherwise.
     * 
     * @param domainClass the domain class to return its mapped workflow
     *            definition
     * @return the id of the workflow definition mapped to the given domain
     *         class or <code>null</code> if no such mapping available
     */
    String getWorkflowDefinitionId(Class<?> domainClass);

    /**
     * Specifies a new mapping between a domain class and a workflow definition
     * id.
     * 
     * @param domainClass the domain class to be mapped
     * @param workflowDefinitionId the workflow definition id as the mapping to
     *            the given domain class
     * @return the old workflow definition id, if any previously set,
     *         <code>null</code> otherwise
     */
    String setWorkflowDefinitionMapping(Class<?> domainClass,
            String workflowDefinitionId);

    /**
     * Returns <code>true</code>, if there is a mapping for the given domain
     * class, <code>false</code>, if no mapping was made.
     * 
     * @param domainClass the domain class to check for having a mapping to a
     *            workflow definition id
     * @return <code>true</code>, if there is a mapping between the given domain
     *         class and a workflow definition
     */
    boolean hasMapping(Class<?> domainClass);
}
