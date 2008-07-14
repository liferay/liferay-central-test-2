/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.workflow.service;

/**
 * <a href="SAWWorkflowServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ganesh
 * @author Brian Wing Shun Chan
 *
 */
public class SAWWorkflowServiceTest extends SAWWorkflowServiceTestCase {

	/*public void testAddEntry() throws Exception {
		String defnId = deployPD();
		System.out.println("defn id is: "+ defnId);
	}*/

	/*public void testGetDefinitionXml() throws Exception{

		String pd = getDefinitionXml();
		System.out.println("Process is: "+ pd);

	}*/

	/*public void testGetDefinitionsXml() throws Exception{

		String pds = getDefinitionsXml();
		System.out.println("Processes are: "+ pds);
	}*/

	/*public void testGetDefinitionsCountXml() throws Exception{

		String count = getDefinitionsCountXml();
		System.out.println("Count: "+ count);
	}*/

	public void testStartWorkflow() throws Exception{

		String pi = startWorkflow();
		System.out.println("ProcessInstance: "+ pi);
	}

	/*public void testGetInstancesXml1() throws Exception{

		String pi = getInstancesXml1();
		System.out.println("ProcessInstances are: "+ pi);
	}
	*/
	/*public void testGetInstancesXml2() throws Exception{

		String pi = getInstancesXml2();
		System.out.println("ProcessInstances are: "+ pi);
	}*/

	/*public void testGetInstancesXml3() throws Exception{

		String pi = getInstancesXml3();
		System.out.println("ProcessInstances are: "+ pi);
	}*/

	/*public void testGetInstancesCountXml() throws Exception{

		String count = getInstancesCountXml();
		System.out.println("ProcessInstances Count: "+ count);
	}*/

	/*public void testGetTaskXml() throws Exception{

		String task = getTaskXml();
		System.out.println("Task is: "+ task);
	}*/

	/*public void testGetTaskFormElementsXml() throws Exception{

		String taskFormElements = getTaskFormElementsXml();
		System.out.println("Task is: "+ taskFormElements);
	}*/

	/*public void testGetTaskTransitionsXml() throws Exception{

		String taskTransitions = getTaskTransitionsXml();
		System.out.println("Task is: "+ taskTransitions);
	}*/

	/*public void testGetUserTasksXml1() throws Exception{

		String tasks = getUserTasksXml1();
		System.out.println("Tasks are: "+ tasks);
	}
*/

	/*public void testGetUserTasksXml2() throws Exception{

		String tasks = getUserTasksXml2();
		System.out.println("Tasks are: "+ tasks);
	}*/

	/*public void testGetUserTasksCountXml() throws Exception{

		String countTasks = getUserTasksCountXml();
		System.out.println("Count Tasks are: "+ countTasks);
	}*/

	/*public void testGetCurrentTasksXml() throws Exception{

		String countTasks = getCurrentTasksXml();
		System.out.println("Count Tasks are: "+ countTasks);
	}*/

/*	public void testSignalToken() throws Exception{

		signalToken();

	}*/

	/*public void testUpdateTask() throws Exception{

		String updateTaskXml = updateTask();
		System.out.println(updateTaskXml);

	}
*/






}