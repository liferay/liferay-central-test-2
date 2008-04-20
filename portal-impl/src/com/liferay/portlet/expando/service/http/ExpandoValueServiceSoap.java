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

package com.liferay.portlet.expando.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.expando.service.ExpandoValueServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="ExpandoValueServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a SOAP utility for the
 * <code>com.liferay.portlet.expando.service.ExpandoValueServiceUtil</code> service
 * utility. The static methods of this class calls the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portlet.expando.model.ExpandoValueSoap</code>. If the method in the
 * service utility returns a <code>com.liferay.portlet.expando.model.ExpandoValue</code>,
 * that is translated to a <code>com.liferay.portlet.expando.model.ExpandoValueSoap</code>.
 * Methods that SOAP cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <code>tunnel.servlet.hosts.allowed</code> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.service.ExpandoValueServiceUtil
 * @see com.liferay.portlet.expando.service.http.ExpandoValueServiceHttp
 * @see com.liferay.portlet.expando.service.model.ExpandoValueSoap
 *
 */
public class ExpandoValueServiceSoap {
	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean[] data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date[] data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double[] data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float[] data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int[] data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long[] data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short[] data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String[] data)
		throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(className,
					tableName, columnName, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap addValue(
		long classNameId, long tableId, long columnId, long classPK,
		java.lang.String data) throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(classNameId,
					tableId, columnId, classPK, data);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteColumnValues(long columnId)
		throws RemoteException {
		try {
			ExpandoValueServiceUtil.deleteColumnValues(columnId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteRowValues(long rowId) throws RemoteException {
		try {
			ExpandoValueServiceUtil.deleteRowValues(rowId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteTableValues(long tableId)
		throws RemoteException {
		try {
			ExpandoValueServiceUtil.deleteTableValues(tableId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteValue(long valueId) throws RemoteException {
		try {
			ExpandoValueServiceUtil.deleteValue(valueId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteValues(java.lang.String className, long classPK)
		throws RemoteException {
		try {
			ExpandoValueServiceUtil.deleteValues(className, classPK);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteValues(long classNameId, long classPK)
		throws RemoteException {
		try {
			ExpandoValueServiceUtil.deleteValues(classNameId, classPK);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap[] getColumnValues(
		long columnId, int begin, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.expando.model.ExpandoValue> returnValue =
				ExpandoValueServiceUtil.getColumnValues(columnId, begin, end);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap[] getColumnValues(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, int begin, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.expando.model.ExpandoValue> returnValue =
				ExpandoValueServiceUtil.getColumnValues(className, tableName,
					columnName, begin, end);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap[] getColumnValues(
		long classNameId, java.lang.String tableName,
		java.lang.String columnName, int begin, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.expando.model.ExpandoValue> returnValue =
				ExpandoValueServiceUtil.getColumnValues(classNameId, tableName,
					columnName, begin, end);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getColumnValuesCount(long columnId)
		throws RemoteException {
		try {
			int returnValue = ExpandoValueServiceUtil.getColumnValuesCount(columnId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getColumnValuesCount(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName)
		throws RemoteException {
		try {
			int returnValue = ExpandoValueServiceUtil.getColumnValuesCount(className,
					tableName, columnName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getColumnValuesCount(long classNameId,
		java.lang.String tableName, java.lang.String columnName)
		throws RemoteException {
		try {
			int returnValue = ExpandoValueServiceUtil.getColumnValuesCount(classNameId,
					tableName, columnName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap[] getDefaultTableColumnValues(
		java.lang.String className, java.lang.String columnName, int begin,
		int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.expando.model.ExpandoValue> returnValue =
				ExpandoValueServiceUtil.getDefaultTableColumnValues(className,
					columnName, begin, end);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap[] getDefaultTableColumnValues(
		long classNameId, java.lang.String columnName, int begin, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.expando.model.ExpandoValue> returnValue =
				ExpandoValueServiceUtil.getDefaultTableColumnValues(classNameId,
					columnName, begin, end);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getDefaultTableColumnValuesCount(
		java.lang.String className, java.lang.String columnName)
		throws RemoteException {
		try {
			int returnValue = ExpandoValueServiceUtil.getDefaultTableColumnValuesCount(className,
					columnName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getDefaultTableColumnValuesCount(long classNameId,
		java.lang.String columnName) throws RemoteException {
		try {
			int returnValue = ExpandoValueServiceUtil.getDefaultTableColumnValuesCount(classNameId,
					columnName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean defaultData) throws RemoteException {
		try {
			boolean returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean[] defaultData) throws RemoteException {
		try {
			boolean[] returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.util.Date getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.util.Date defaultData) throws RemoteException {
		try {
			java.util.Date returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.util.Date[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.util.Date[] defaultData) throws RemoteException {
		try {
			java.util.Date[] returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static double getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double defaultData) throws RemoteException {
		try {
			double returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static double[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double[] defaultData) throws RemoteException {
		try {
			double[] returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static float getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float defaultData) throws RemoteException {
		try {
			float returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static float[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float[] defaultData) throws RemoteException {
		try {
			float[] returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int defaultData) throws RemoteException {
		try {
			int returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int[] defaultData) throws RemoteException {
		try {
			int[] returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long defaultData) throws RemoteException {
		try {
			long returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long[] defaultData) throws RemoteException {
		try {
			long[] returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static short getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short defaultData) throws RemoteException {
		try {
			short returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static short[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short[] defaultData) throws RemoteException {
		try {
			short[] returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String defaultData) throws RemoteException {
		try {
			java.lang.String returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String[] defaultData) throws RemoteException {
		try {
			java.lang.String[] returnValue = ExpandoValueServiceUtil.getData(className,
					tableName, columnName, classPK, defaultData);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap[] getRowValues(
		long rowId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.expando.model.ExpandoValue> returnValue =
				ExpandoValueServiceUtil.getRowValues(rowId);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap[] getRowValues(
		long rowId, int begin, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.expando.model.ExpandoValue> returnValue =
				ExpandoValueServiceUtil.getRowValues(rowId, begin, end);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap[] getRowValues(
		java.lang.String className, java.lang.String tableName, long classPK,
		int begin, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.expando.model.ExpandoValue> returnValue =
				ExpandoValueServiceUtil.getRowValues(className, tableName,
					classPK, begin, end);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap[] getRowValues(
		long classNameId, java.lang.String tableName, long classPK, int begin,
		int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.expando.model.ExpandoValue> returnValue =
				ExpandoValueServiceUtil.getRowValues(classNameId, tableName,
					classPK, begin, end);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getRowValuesCount(long rowId) throws RemoteException {
		try {
			int returnValue = ExpandoValueServiceUtil.getRowValuesCount(rowId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getRowValuesCount(java.lang.String className,
		java.lang.String tableName, long classPK) throws RemoteException {
		try {
			int returnValue = ExpandoValueServiceUtil.getRowValuesCount(className,
					tableName, classPK);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getRowValuesCount(long classNameId,
		java.lang.String tableName, long classPK) throws RemoteException {
		try {
			int returnValue = ExpandoValueServiceUtil.getRowValuesCount(classNameId,
					tableName, classPK);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap getValue(
		long valueId) throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.getValue(valueId);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap getValue(
		long columnId, long rowId) throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.getValue(columnId,
					rowId);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap getValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long rowId) throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.getValue(className,
					tableName, columnName, rowId);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValueSoap getValue(
		long classNameId, java.lang.String tableName,
		java.lang.String columnName, long rowId) throws RemoteException {
		try {
			com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.getValue(classNameId,
					tableName, columnName, rowId);

			return com.liferay.portlet.expando.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoValueServiceSoap.class);
}