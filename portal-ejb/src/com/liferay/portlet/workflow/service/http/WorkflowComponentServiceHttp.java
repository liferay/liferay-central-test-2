/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.workflow.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.workflow.service.WorkflowComponentServiceUtil;

/**
 * <a href="WorkflowComponentServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WorkflowComponentServiceHttp {
	public static java.util.List getCurrentTasks(HttpPrincipal httpPrincipal,
		long instanceId, long tokenId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(instanceId);
			Object paramObj1 = new LongWrapper(tokenId);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getCurrentTasks", new Object[] { paramObj0, paramObj1 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.List)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.lang.String getCurrentTasksXml(
		HttpPrincipal httpPrincipal, long instanceId, long tokenId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(instanceId);
			Object paramObj1 = new LongWrapper(tokenId);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getCurrentTasksXml", new Object[] { paramObj0, paramObj1 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.lang.String deploy(HttpPrincipal httpPrincipal,
		java.lang.String xml)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = xml;

			if (xml == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"deploy", new Object[] { paramObj0 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.util.List getDefinitions(HttpPrincipal httpPrincipal,
		long definitionId, java.lang.String name, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(definitionId);
			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new IntegerWrapper(begin);
			Object paramObj3 = new IntegerWrapper(end);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getDefinitions",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.List)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.lang.String getDefinitionsXml(
		HttpPrincipal httpPrincipal, long definitionId, java.lang.String name,
		int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(definitionId);
			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new IntegerWrapper(begin);
			Object paramObj3 = new IntegerWrapper(end);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getDefinitionsXml",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static int getDefinitionsCount(HttpPrincipal httpPrincipal,
		long definitionId, java.lang.String name)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(definitionId);
			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getDefinitionsCount", new Object[] { paramObj0, paramObj1 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.lang.String getDefinitionsCountXml(
		HttpPrincipal httpPrincipal, long definitionId, java.lang.String name)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(definitionId);
			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getDefinitionsCountXml",
					new Object[] { paramObj0, paramObj1 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.util.List getInstances(HttpPrincipal httpPrincipal,
		long definitionId, long instanceId, java.lang.String definitionName,
		java.lang.String definitionVersion, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks,
		boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(definitionId);
			Object paramObj1 = new LongWrapper(instanceId);
			Object paramObj2 = definitionName;

			if (definitionName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = definitionVersion;

			if (definitionVersion == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = gtStartDate;

			if (gtStartDate == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = ltStartDate;

			if (ltStartDate == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = gtEndDate;

			if (gtEndDate == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = ltEndDate;

			if (ltEndDate == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = new BooleanWrapper(hideEndedTasks);
			Object paramObj9 = new BooleanWrapper(andOperator);
			Object paramObj10 = new IntegerWrapper(begin);
			Object paramObj11 = new IntegerWrapper(end);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getInstances",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.List)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static int getInstancesCount(HttpPrincipal httpPrincipal,
		long definitionId, long instanceId, java.lang.String definitionName,
		java.lang.String definitionVersion, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(definitionId);
			Object paramObj1 = new LongWrapper(instanceId);
			Object paramObj2 = definitionName;

			if (definitionName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = definitionVersion;

			if (definitionVersion == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = gtStartDate;

			if (gtStartDate == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = ltStartDate;

			if (ltStartDate == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = gtEndDate;

			if (gtEndDate == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = ltEndDate;

			if (ltEndDate == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = new BooleanWrapper(hideEndedTasks);
			Object paramObj9 = new BooleanWrapper(andOperator);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getInstancesCount",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.lang.String getInstancesCountXml(
		HttpPrincipal httpPrincipal, long definitionId, long instanceId,
		java.lang.String definitionName, java.lang.String definitionVersion,
		java.lang.String gtStartDate, java.lang.String ltStartDate,
		java.lang.String gtEndDate, java.lang.String ltEndDate,
		boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(definitionId);
			Object paramObj1 = new LongWrapper(instanceId);
			Object paramObj2 = definitionName;

			if (definitionName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = definitionVersion;

			if (definitionVersion == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = gtStartDate;

			if (gtStartDate == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = ltStartDate;

			if (ltStartDate == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = gtEndDate;

			if (gtEndDate == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = ltEndDate;

			if (ltEndDate == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = new BooleanWrapper(hideEndedTasks);
			Object paramObj9 = new BooleanWrapper(andOperator);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getInstancesCountXml",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.lang.String getInstancesXml(
		HttpPrincipal httpPrincipal, long definitionId, long instanceId,
		java.lang.String definitionName, java.lang.String definitionVersion,
		java.lang.String gtStartDate, java.lang.String ltStartDate,
		java.lang.String gtEndDate, java.lang.String ltEndDate,
		boolean hideEndedTasks, boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(definitionId);
			Object paramObj1 = new LongWrapper(instanceId);
			Object paramObj2 = definitionName;

			if (definitionName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = definitionVersion;

			if (definitionVersion == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = gtStartDate;

			if (gtStartDate == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = ltStartDate;

			if (ltStartDate == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = gtEndDate;

			if (gtEndDate == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = ltEndDate;

			if (ltEndDate == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = new BooleanWrapper(hideEndedTasks);
			Object paramObj9 = new BooleanWrapper(andOperator);
			Object paramObj10 = new IntegerWrapper(begin);
			Object paramObj11 = new IntegerWrapper(end);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getInstancesXml",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.util.List getTaskFormElements(
		HttpPrincipal httpPrincipal, long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(taskId);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getTaskFormElements", new Object[] { paramObj0 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.List)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.lang.String getTaskFormElementsXml(
		HttpPrincipal httpPrincipal, long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(taskId);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getTaskFormElementsXml", new Object[] { paramObj0 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.util.List getTaskTransitions(
		HttpPrincipal httpPrincipal, long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(taskId);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getTaskTransitions", new Object[] { paramObj0 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.List)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.lang.String getTaskTransitionsXml(
		HttpPrincipal httpPrincipal, long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(taskId);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getTaskTransitionsXml", new Object[] { paramObj0 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.util.List getUserTasks(HttpPrincipal httpPrincipal,
		long instanceId, java.lang.String taskName,
		java.lang.String definitionName, java.lang.String assignedTo,
		java.lang.String gtCreateDate, java.lang.String ltCreateDate,
		java.lang.String gtStartDate, java.lang.String ltStartDate,
		java.lang.String gtEndDate, java.lang.String ltEndDate,
		boolean hideEndedTasks, boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(instanceId);
			Object paramObj1 = taskName;

			if (taskName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = definitionName;

			if (definitionName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = assignedTo;

			if (assignedTo == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = gtCreateDate;

			if (gtCreateDate == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = ltCreateDate;

			if (ltCreateDate == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = gtStartDate;

			if (gtStartDate == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = ltStartDate;

			if (ltStartDate == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = gtEndDate;

			if (gtEndDate == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = ltEndDate;

			if (ltEndDate == null) {
				paramObj9 = new NullWrapper("java.lang.String");
			}

			Object paramObj10 = new BooleanWrapper(hideEndedTasks);
			Object paramObj11 = new BooleanWrapper(andOperator);
			Object paramObj12 = new IntegerWrapper(begin);
			Object paramObj13 = new IntegerWrapper(end);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getUserTasks",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.List)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static int getUserTasksCount(HttpPrincipal httpPrincipal,
		long instanceId, java.lang.String taskName,
		java.lang.String definitionName, java.lang.String assignedTo,
		java.lang.String gtCreateDate, java.lang.String ltCreateDate,
		java.lang.String gtStartDate, java.lang.String ltStartDate,
		java.lang.String gtEndDate, java.lang.String ltEndDate,
		boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(instanceId);
			Object paramObj1 = taskName;

			if (taskName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = definitionName;

			if (definitionName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = assignedTo;

			if (assignedTo == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = gtCreateDate;

			if (gtCreateDate == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = ltCreateDate;

			if (ltCreateDate == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = gtStartDate;

			if (gtStartDate == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = ltStartDate;

			if (ltStartDate == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = gtEndDate;

			if (gtEndDate == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = ltEndDate;

			if (ltEndDate == null) {
				paramObj9 = new NullWrapper("java.lang.String");
			}

			Object paramObj10 = new BooleanWrapper(hideEndedTasks);
			Object paramObj11 = new BooleanWrapper(andOperator);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getUserTasksCount",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.lang.String getUserTasksCountXml(
		HttpPrincipal httpPrincipal, long instanceId,
		java.lang.String taskName, java.lang.String definitionName,
		java.lang.String assignedTo, java.lang.String gtCreateDate,
		java.lang.String ltCreateDate, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(instanceId);
			Object paramObj1 = taskName;

			if (taskName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = definitionName;

			if (definitionName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = assignedTo;

			if (assignedTo == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = gtCreateDate;

			if (gtCreateDate == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = ltCreateDate;

			if (ltCreateDate == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = gtStartDate;

			if (gtStartDate == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = ltStartDate;

			if (ltStartDate == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = gtEndDate;

			if (gtEndDate == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = ltEndDate;

			if (ltEndDate == null) {
				paramObj9 = new NullWrapper("java.lang.String");
			}

			Object paramObj10 = new BooleanWrapper(hideEndedTasks);
			Object paramObj11 = new BooleanWrapper(andOperator);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getUserTasksCountXml",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.lang.String getUserTasksXml(
		HttpPrincipal httpPrincipal, long instanceId,
		java.lang.String taskName, java.lang.String definitionName,
		java.lang.String assignedTo, java.lang.String gtCreateDate,
		java.lang.String ltCreateDate, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks,
		boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(instanceId);
			Object paramObj1 = taskName;

			if (taskName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = definitionName;

			if (definitionName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = assignedTo;

			if (assignedTo == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = gtCreateDate;

			if (gtCreateDate == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = ltCreateDate;

			if (ltCreateDate == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = gtStartDate;

			if (gtStartDate == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = ltStartDate;

			if (ltStartDate == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = gtEndDate;

			if (gtEndDate == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = ltEndDate;

			if (ltEndDate == null) {
				paramObj9 = new NullWrapper("java.lang.String");
			}

			Object paramObj10 = new BooleanWrapper(hideEndedTasks);
			Object paramObj11 = new BooleanWrapper(andOperator);
			Object paramObj12 = new IntegerWrapper(begin);
			Object paramObj13 = new IntegerWrapper(end);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"getUserTasksXml",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static void signalInstance(HttpPrincipal httpPrincipal,
		long instanceId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(instanceId);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"signalInstance", new Object[] { paramObj0 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static void signalToken(HttpPrincipal httpPrincipal,
		long instanceId, long tokenId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(instanceId);
			Object paramObj1 = new LongWrapper(tokenId);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"signalToken", new Object[] { paramObj0, paramObj1 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.lang.String startWorkflow(HttpPrincipal httpPrincipal,
		long definitionId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(definitionId);
			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"startWorkflow", new Object[] { paramObj0 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.util.Map updateTask(HttpPrincipal httpPrincipal,
		long taskId, java.lang.String transition, java.util.Map parameterMap)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(taskId);
			Object paramObj1 = transition;

			if (transition == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = parameterMap;

			if (parameterMap == null) {
				paramObj2 = new NullWrapper("java.util.Map");
			}

			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"updateTask",
					new Object[] { paramObj0, paramObj1, paramObj2 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.Map)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.lang.String updateTaskXml(HttpPrincipal httpPrincipal,
		long taskId, java.lang.String transition, java.util.Map parameterMap)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(taskId);
			Object paramObj1 = transition;

			if (transition == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = parameterMap;

			if (parameterMap == null) {
				paramObj2 = new NullWrapper("java.util.Map");
			}

			MethodWrapper methodWrapper = new MethodWrapper(WorkflowComponentServiceUtil.class.getName(),
					"updateTaskXml",
					new Object[] { paramObj0, paramObj1, paramObj2 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.jbi.WorkflowComponentException) {
					throw (com.liferay.portal.kernel.jbi.WorkflowComponentException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(WorkflowComponentServiceHttp.class);
}