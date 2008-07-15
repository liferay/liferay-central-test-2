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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portlet.workflow.service;

import com.liferay.portal.service.BaseServiceTestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="SAWWorkflowServiceTestCase.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ganesh
 * @author Brian Wing Shun Chan
 *
 */

public class SAWWorkflowServiceTestCase extends BaseServiceTestCase {

	protected String deployPD() throws Exception {

		final String xml = "<?xml version=\"1.0\"?>\r\n" +
		"\r\n" +
		"<process-definition name=\"testPD\">\r\n" +
		"\r\n" +
		"	<!-- Event Logging -->\r\n" +
		"\r\n" +
		"	<event type=\"node-enter\">\r\n" +
		"		<script>\r\n" +
		"			System.out.println(\"Entering node \" + node.getName());" +
		"\r\n" +
		"		</script>\r\n" +
		"	</event>\r\n" +
		"\r\n" +
		"	<event type=\"node-leave\">\r\n" +
		"		<script>\r\n" +
		"			System.out.println(\"Leaving node \" + node.getName());" +
		"\r\n" +
		"		</script>\r\n" +
		"	</event>\r\n" +
		"\r\n" +
		"	<!-- Swimlanes -->\r\n" +
		"\r\n" +
		"	<swimlane name=\"user\" />\r\n" +
		"\r\n" +
		"	<!-- Nodes -->\r\n" +
		"\r\n" +
		"	<start-state name=\"enter-data\">\r\n" +
		"		<task swimlane=\"user\">\r\n" +
		"			<controller>\r\n" +
		"				<variable name=\"text:name\" " +
		"access=\"read,write,required\" />\r\n" +
		"				<variable name=\"password:password\" " +
		"access=\"read,write,required\" />\r\n" +
		"				<variable name=\"date:birthday\" " +
		"access=\"read,write,required\" />\r\n" +
		"				<variable name=\"number:age\" />\r\n" +
		"				<variable name=\"email:email-address\" />\r\n" +
		"				<variable name=\"phone:phone-number\" " +
		"access=\"read,write,required\" />\r\n" +
		"<variable name=\"select:favorite-color:red,blue,purple,"
		+
		"yellow,orange,white,black\" />\r\n" +
		"				<variable " +
		"name=\"radio:are-you-hungry:yes,no,a-little-bit\" " +
		"access=\"read,write,required\" />\r\n" +
		"				<variable " +
		"name=\"checkbox:this-portlet-is-cool:yes\" />\r\n" +
		"				<variable name=\"checkbox:liferay-rocks:yes\" " +
		"access=\"read,write,required\" />\r\n" +
		"				<variable name=\"textarea:comments\" />\r\n" +
		"			</controller>\r\n" +
		"		</task>\r\n" +
		"		<transition name=\"save\" to=\"view-data\" />\r\n" +
		"	</start-state>\r\n" +
		"\r\n" +
		"	<task-node name=\"view-data\">\r\n" +
		"		<task swimlane=\"user\">\r\n" +
		"			<controller>\r\n" +
		"				<variable name=\"text:name\" access=\"read\" />\r\n" +
		"				<variable name=\"password:password\" " +
		"access=\"read\" />\r\n" +
		"<variable name=\"date:birthday\" access=\"read\" />\r\n"
		+
		"				<variable name=\"number:age\" access=\"read\" />\r\n" +
		"				<variable name=\"email:email-address\" " +
		"access=\"read\" />\r\n" +
		"				<variable name=\"phone:phone-number\" " +
		"access=\"read\" />\r\n" +
		"<variable name=\"select:favorite-color:red,blue,purple,"
		+
		"yellow,orange,white,black\" access=\"read\" />\r\n" +
		"				<variable name=\"radio:are-you-hungry:yes,no," +
		"a-little-bit\" access=\"read\" />\r\n" +
		"<variable name=\"checkbox:this-portlet-is-cool:yes\" " +
		"access=\"read\" />\r\n" +
		"				<variable name=\"checkbox:liferay-rocks:yes\" " +
		"access=\"read\" />\r\n" +
		"				<variable name=\"textarea:comments\" " +
		"access=\"read\" />\r\n" +
		"			</controller>\r\n" +
		"		</task>\r\n" +
		"		<transition name=\"finished\" to=\"end\" />\r\n" +
		"	</task-node>\r\n" +
		"\r\n" +
		"	<end-state name=\"end\" />\r\n" +
		"</process-definition>";

		return SAWWorkflowLocalServiceUtil.deploy(xml);

	}

	protected String getDefinitionXml() throws Exception{

		String  pd = SAWWorkflowLocalServiceUtil.getDefinitionXml(new Long(8));

		return pd;

	}

	protected String getDefinitionsXml() throws Exception{

		return SAWWorkflowLocalServiceUtil.getDefinitionsXml(0, "%", 0, 10);
	}

	protected String getDefinitionsCountXml() throws Exception{

		return SAWWorkflowLocalServiceUtil.getDefinitionsCountXml(0, "%");
	}

	protected String startWorkflow() throws Exception{

		return SAWWorkflowLocalServiceUtil.startWorkflow(new Long(2));
	}

	//processdefn id is set
	protected String getInstancesXml1() throws Exception{

		return SAWWorkflowLocalServiceUtil.getInstancesXml(
			new Long(1).longValue(),
				new Long(0).longValue(), null, null,
					null, null, null, null, null, false, false, false, 0, 0);
	}

	//processdefn id is NOT set but process instance id is set.
	protected String getInstancesXml2() throws Exception{

		return SAWWorkflowLocalServiceUtil.getInstancesXml(
			new Long(0).longValue()
				, new Long(1).longValue(), null, null, null, null,
					null, null, null, false, false, false, 0, 0);
	}

	//processdefn id is NOT set and process instance id NOT set.
	protected String getInstancesXml3() throws Exception{

		return SAWWorkflowLocalServiceUtil.getInstancesXml(
			new Long(0).longValue(),
			new Long(0).longValue(), "test", null, null, null, null, null,
				"10129", true, true, false, 0, 10);
	}

	protected String getInstancesCountXml() throws Exception{

		return SAWWorkflowLocalServiceUtil.getInstancesCountXml(new Long(0),
			new Long(0), null, "%", null, null, null,
				null, "10129", true, true, true);
	}

	protected String getTaskXml() throws Exception{

		return SAWWorkflowLocalServiceUtil.getTaskXml(new Long(1).longValue());
	}

	protected String getTaskFormElementsXml() throws Exception{

		return SAWWorkflowLocalServiceUtil.getTaskFormElementsXml(
			new Long(1).longValue());
	}

	protected String getTaskTransitionsXml() throws Exception{

		return SAWWorkflowLocalServiceUtil.getTaskTransitionsXml(
			new Long(1).longValue());
	}

	// only pi is set.
	protected String getUserTasksXml1() throws Exception{

		return SAWWorkflowLocalServiceUtil.getUserTasksXml(
			new Long(5).longValue(),
			null, null, null, null, null, null, null, null,
				null, "10129", false, false, 0, 0);
	}

	// process instance and processdefn is also set.
	protected String getUserTasksXml2() throws Exception{

		return SAWWorkflowLocalServiceUtil.getUserTasksXml(
			new Long(5).longValue(),
				"test", null, null, null, null, null, null,
					null, null, "10129", true, true, 0, 10);
	}

	protected String getUserTasksCountXml() throws Exception{

		return SAWWorkflowLocalServiceUtil.getUserTasksCountXml(new Long(0),
			"test", null,null, null, null,
				null, null, null, null, true, true);
	}

	protected String getCurrentTasksXml() throws Exception{

		return SAWWorkflowLocalServiceUtil.getCurrentTasksXml(new Long(5),
			new Long(5),"10129");
	}

	protected void signalToken() throws Exception{

		SAWWorkflowLocalServiceUtil.signalToken(new Long(5), new Long(5));
	}

	protected String updateTask() throws Exception{

		Map<String,String> parameterMap = new HashMap<String,String>();
		parameterMap.put("startdate", "07/12/2008");
		parameterMap.put("enddate", "07/15/2008");

		return SAWWorkflowLocalServiceUtil.updateTaskXml(
			new Long(26), "save", "10153", parameterMap);
	}

}