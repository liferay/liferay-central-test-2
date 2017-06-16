/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.tasks.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.tasks.service.TasksEntryLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Ryan Park
 * @generated
 */
@ProviderType
public class TasksEntryLocalServiceClpInvoker {
	public TasksEntryLocalServiceClpInvoker() {
		_methodName0 = "addTasksEntry";

		_methodParameterTypes0 = new String[] {
				"com.liferay.tasks.model.TasksEntry"
			};

		_methodName1 = "createTasksEntry";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteTasksEntry";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteTasksEntry";

		_methodParameterTypes3 = new String[] {
				"com.liferay.tasks.model.TasksEntry"
			};

		_methodName4 = "dynamicQuery";

		_methodParameterTypes4 = new String[] {  };

		_methodName5 = "dynamicQuery";

		_methodParameterTypes5 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery"
			};

		_methodName6 = "dynamicQuery";

		_methodParameterTypes6 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery", "int", "int"
			};

		_methodName7 = "dynamicQuery";

		_methodParameterTypes7 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery", "int", "int",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName8 = "dynamicQueryCount";

		_methodParameterTypes8 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery"
			};

		_methodName9 = "dynamicQueryCount";

		_methodParameterTypes9 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery",
				"com.liferay.portal.kernel.dao.orm.Projection"
			};

		_methodName10 = "fetchTasksEntry";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getTasksEntry";

		_methodParameterTypes11 = new String[] { "long" };

		_methodName12 = "getActionableDynamicQuery";

		_methodParameterTypes12 = new String[] {  };

		_methodName13 = "getIndexableActionableDynamicQuery";

		_methodParameterTypes13 = new String[] {  };

		_methodName15 = "deletePersistedModel";

		_methodParameterTypes15 = new String[] {
				"com.liferay.portal.kernel.model.PersistedModel"
			};

		_methodName16 = "getPersistedModel";

		_methodParameterTypes16 = new String[] { "java.io.Serializable" };

		_methodName17 = "getTasksEntries";

		_methodParameterTypes17 = new String[] { "int", "int" };

		_methodName18 = "getTasksEntriesCount";

		_methodParameterTypes18 = new String[] {  };

		_methodName19 = "updateTasksEntry";

		_methodParameterTypes19 = new String[] {
				"com.liferay.tasks.model.TasksEntry"
			};

		_methodName56 = "getOSGiServiceIdentifier";

		_methodParameterTypes56 = new String[] {  };

		_methodName61 = "addTasksEntry";

		_methodParameterTypes61 = new String[] {
				"long", "java.lang.String", "int", "long", "int", "int", "int",
				"int", "int", "boolean",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName62 = "deleteTasksEntry";

		_methodParameterTypes62 = new String[] { "long" };

		_methodName63 = "deleteTasksEntry";

		_methodParameterTypes63 = new String[] {
				"com.liferay.tasks.model.TasksEntry"
			};

		_methodName64 = "getAssigneeTasksEntries";

		_methodParameterTypes64 = new String[] { "long", "int", "int" };

		_methodName65 = "getAssigneeTasksEntriesCount";

		_methodParameterTypes65 = new String[] { "long" };

		_methodName66 = "getGroupAssigneeTasksEntries";

		_methodParameterTypes66 = new String[] { "long", "long", "int", "int" };

		_methodName67 = "getGroupAssigneeTasksEntriesCount";

		_methodParameterTypes67 = new String[] { "long", "long" };

		_methodName68 = "getGroupResolverTasksEntries";

		_methodParameterTypes68 = new String[] { "long", "long", "int", "int" };

		_methodName69 = "getGroupResolverTasksEntriesCount";

		_methodParameterTypes69 = new String[] { "long", "long" };

		_methodName70 = "getGroupUserTasksEntries";

		_methodParameterTypes70 = new String[] { "long", "long", "int", "int" };

		_methodName71 = "getGroupUserTasksEntriesCount";

		_methodParameterTypes71 = new String[] { "long", "long" };

		_methodName72 = "getResolverTasksEntries";

		_methodParameterTypes72 = new String[] { "long", "int", "int" };

		_methodName73 = "getResolverTasksEntriesCount";

		_methodParameterTypes73 = new String[] { "long" };

		_methodName74 = "getTasksEntries";

		_methodParameterTypes74 = new String[] { "long", "int", "int" };

		_methodName75 = "getTasksEntries";

		_methodParameterTypes75 = new String[] {
				"long", "long", "int", "long", "int", "long[][]", "long[][]",
				"int", "int"
			};

		_methodName76 = "getTasksEntriesCount";

		_methodParameterTypes76 = new String[] { "long" };

		_methodName77 = "getTasksEntriesCount";

		_methodParameterTypes77 = new String[] {
				"long", "long", "int", "long", "int", "long[][]", "long[][]"
			};

		_methodName78 = "getTasksEntry";

		_methodParameterTypes78 = new String[] { "long" };

		_methodName79 = "getUserTasksEntries";

		_methodParameterTypes79 = new String[] { "long", "int", "int" };

		_methodName80 = "getUserTasksEntriesCount";

		_methodParameterTypes80 = new String[] { "long" };

		_methodName81 = "updateAsset";

		_methodParameterTypes81 = new String[] {
				"long", "com.liferay.tasks.model.TasksEntry", "long[][]",
				"java.lang.String[][]"
			};

		_methodName82 = "updateTasksEntry";

		_methodParameterTypes82 = new String[] {
				"long", "java.lang.String", "int", "long", "long", "int", "int",
				"int", "int", "int", "boolean", "int",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName83 = "updateTasksEntryStatus";

		_methodParameterTypes83 = new String[] {
				"long", "long", "int",
				"com.liferay.portal.kernel.service.ServiceContext"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return TasksEntryLocalServiceUtil.addTasksEntry((com.liferay.tasks.model.TasksEntry)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return TasksEntryLocalServiceUtil.createTasksEntry(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return TasksEntryLocalServiceUtil.deleteTasksEntry(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return TasksEntryLocalServiceUtil.deleteTasksEntry((com.liferay.tasks.model.TasksEntry)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return TasksEntryLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return TasksEntryLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return TasksEntryLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return TasksEntryLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator<?>)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return TasksEntryLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return TasksEntryLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				(com.liferay.portal.kernel.dao.orm.Projection)arguments[1]);
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return TasksEntryLocalServiceUtil.fetchTasksEntry(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getTasksEntry(((Long)arguments[0]).longValue());
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getActionableDynamicQuery();
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getIndexableActionableDynamicQuery();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return TasksEntryLocalServiceUtil.deletePersistedModel((com.liferay.portal.kernel.model.PersistedModel)arguments[0]);
		}

		if (_methodName16.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes16, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName17.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes17, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getTasksEntries(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName18.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes18, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getTasksEntriesCount();
		}

		if (_methodName19.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes19, parameterTypes)) {
			return TasksEntryLocalServiceUtil.updateTasksEntry((com.liferay.tasks.model.TasksEntry)arguments[0]);
		}

		if (_methodName56.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes56, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getOSGiServiceIdentifier();
		}

		if (_methodName61.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes61, parameterTypes)) {
			return TasksEntryLocalServiceUtil.addTasksEntry(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1],
				((Integer)arguments[2]).intValue(),
				((Long)arguments[3]).longValue(),
				((Integer)arguments[4]).intValue(),
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue(),
				((Integer)arguments[7]).intValue(),
				((Integer)arguments[8]).intValue(),
				((Boolean)arguments[9]).booleanValue(),
				(com.liferay.portal.kernel.service.ServiceContext)arguments[10]);
		}

		if (_methodName62.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes62, parameterTypes)) {
			return TasksEntryLocalServiceUtil.deleteTasksEntry(((Long)arguments[0]).longValue());
		}

		if (_methodName63.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes63, parameterTypes)) {
			return TasksEntryLocalServiceUtil.deleteTasksEntry((com.liferay.tasks.model.TasksEntry)arguments[0]);
		}

		if (_methodName64.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes64, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getAssigneeTasksEntries(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName65.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes65, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getAssigneeTasksEntriesCount(((Long)arguments[0]).longValue());
		}

		if (_methodName66.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes66, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getGroupAssigneeTasksEntries(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Integer)arguments[2]).intValue(),
				((Integer)arguments[3]).intValue());
		}

		if (_methodName67.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes67, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getGroupAssigneeTasksEntriesCount(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName68.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes68, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getGroupResolverTasksEntries(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Integer)arguments[2]).intValue(),
				((Integer)arguments[3]).intValue());
		}

		if (_methodName69.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes69, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getGroupResolverTasksEntriesCount(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName70.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes70, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getGroupUserTasksEntries(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Integer)arguments[2]).intValue(),
				((Integer)arguments[3]).intValue());
		}

		if (_methodName71.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes71, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getGroupUserTasksEntriesCount(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName72.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes72, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getResolverTasksEntries(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName73.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes73, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getResolverTasksEntriesCount(((Long)arguments[0]).longValue());
		}

		if (_methodName74.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes74, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getTasksEntries(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName75.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes75, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getTasksEntries(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Integer)arguments[2]).intValue(),
				((Long)arguments[3]).longValue(),
				((Integer)arguments[4]).intValue(), (long[])arguments[5],
				(long[])arguments[6], ((Integer)arguments[7]).intValue(),
				((Integer)arguments[8]).intValue());
		}

		if (_methodName76.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes76, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getTasksEntriesCount(((Long)arguments[0]).longValue());
		}

		if (_methodName77.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes77, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getTasksEntriesCount(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Integer)arguments[2]).intValue(),
				((Long)arguments[3]).longValue(),
				((Integer)arguments[4]).intValue(), (long[])arguments[5],
				(long[])arguments[6]);
		}

		if (_methodName78.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes78, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getTasksEntry(((Long)arguments[0]).longValue());
		}

		if (_methodName79.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes79, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getUserTasksEntries(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName80.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes80, parameterTypes)) {
			return TasksEntryLocalServiceUtil.getUserTasksEntriesCount(((Long)arguments[0]).longValue());
		}

		if (_methodName81.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes81, parameterTypes)) {
			TasksEntryLocalServiceUtil.updateAsset(((Long)arguments[0]).longValue(),
				(com.liferay.tasks.model.TasksEntry)arguments[1],
				(long[])arguments[2], (java.lang.String[])arguments[3]);

			return null;
		}

		if (_methodName82.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes82, parameterTypes)) {
			return TasksEntryLocalServiceUtil.updateTasksEntry(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1],
				((Integer)arguments[2]).intValue(),
				((Long)arguments[3]).longValue(),
				((Long)arguments[4]).longValue(),
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue(),
				((Integer)arguments[7]).intValue(),
				((Integer)arguments[8]).intValue(),
				((Integer)arguments[9]).intValue(),
				((Boolean)arguments[10]).booleanValue(),
				((Integer)arguments[11]).intValue(),
				(com.liferay.portal.kernel.service.ServiceContext)arguments[12]);
		}

		if (_methodName83.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes83, parameterTypes)) {
			return TasksEntryLocalServiceUtil.updateTasksEntryStatus(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.service.ServiceContext)arguments[3]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName0;
	private String[] _methodParameterTypes0;
	private String _methodName1;
	private String[] _methodParameterTypes1;
	private String _methodName2;
	private String[] _methodParameterTypes2;
	private String _methodName3;
	private String[] _methodParameterTypes3;
	private String _methodName4;
	private String[] _methodParameterTypes4;
	private String _methodName5;
	private String[] _methodParameterTypes5;
	private String _methodName6;
	private String[] _methodParameterTypes6;
	private String _methodName7;
	private String[] _methodParameterTypes7;
	private String _methodName8;
	private String[] _methodParameterTypes8;
	private String _methodName9;
	private String[] _methodParameterTypes9;
	private String _methodName10;
	private String[] _methodParameterTypes10;
	private String _methodName11;
	private String[] _methodParameterTypes11;
	private String _methodName12;
	private String[] _methodParameterTypes12;
	private String _methodName13;
	private String[] _methodParameterTypes13;
	private String _methodName15;
	private String[] _methodParameterTypes15;
	private String _methodName16;
	private String[] _methodParameterTypes16;
	private String _methodName17;
	private String[] _methodParameterTypes17;
	private String _methodName18;
	private String[] _methodParameterTypes18;
	private String _methodName19;
	private String[] _methodParameterTypes19;
	private String _methodName56;
	private String[] _methodParameterTypes56;
	private String _methodName61;
	private String[] _methodParameterTypes61;
	private String _methodName62;
	private String[] _methodParameterTypes62;
	private String _methodName63;
	private String[] _methodParameterTypes63;
	private String _methodName64;
	private String[] _methodParameterTypes64;
	private String _methodName65;
	private String[] _methodParameterTypes65;
	private String _methodName66;
	private String[] _methodParameterTypes66;
	private String _methodName67;
	private String[] _methodParameterTypes67;
	private String _methodName68;
	private String[] _methodParameterTypes68;
	private String _methodName69;
	private String[] _methodParameterTypes69;
	private String _methodName70;
	private String[] _methodParameterTypes70;
	private String _methodName71;
	private String[] _methodParameterTypes71;
	private String _methodName72;
	private String[] _methodParameterTypes72;
	private String _methodName73;
	private String[] _methodParameterTypes73;
	private String _methodName74;
	private String[] _methodParameterTypes74;
	private String _methodName75;
	private String[] _methodParameterTypes75;
	private String _methodName76;
	private String[] _methodParameterTypes76;
	private String _methodName77;
	private String[] _methodParameterTypes77;
	private String _methodName78;
	private String[] _methodParameterTypes78;
	private String _methodName79;
	private String[] _methodParameterTypes79;
	private String _methodName80;
	private String[] _methodParameterTypes80;
	private String _methodName81;
	private String[] _methodParameterTypes81;
	private String _methodName82;
	private String[] _methodParameterTypes82;
	private String _methodName83;
	private String[] _methodParameterTypes83;
}