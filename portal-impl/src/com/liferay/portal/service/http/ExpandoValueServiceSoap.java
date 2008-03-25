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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.ExpandoValueServiceUtil;

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
 * <code>com.liferay.portal.service.ExpandoValueServiceUtil</code> service
 * utility. The static methods of this class calls the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.model.ExpandoValueSoap</code>. If the method in the
 * service utility returns a <code>com.liferay.portal.model.ExpandoValue</code>,
 * that is translated to a <code>com.liferay.portal.model.ExpandoValueSoap</code>.
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
 * @see com.liferay.portal.service.ExpandoValueServiceUtil
 * @see com.liferay.portal.service.http.ExpandoValueServiceHttp
 * @see com.liferay.portal.service.model.ExpandoValueSoap
 *
 */
public class ExpandoValueServiceSoap {
	public static com.liferay.portal.model.ExpandoValueSoap addValue(
		long classPK, long columnId, java.lang.String value)
		throws RemoteException {
		try {
			com.liferay.portal.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(classPK,
					columnId, value);

			return com.liferay.portal.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.ExpandoValueSoap getValue(
		long classPK, long columnId) throws RemoteException {
		try {
			com.liferay.portal.model.ExpandoValue returnValue = ExpandoValueServiceUtil.getValue(classPK,
					columnId);

			return com.liferay.portal.model.ExpandoValueSoap.toSoapModel(returnValue);
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

	public static void deleteValue(long classPK, long columnId)
		throws RemoteException {
		try {
			ExpandoValueServiceUtil.deleteValue(classPK, columnId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteValues(long classPK) throws RemoteException {
		try {
			ExpandoValueServiceUtil.deleteValues(classPK);
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

	public static com.liferay.portal.model.ExpandoValueSoap[] getColumnValues(
		long columnId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.ExpandoValue> returnValue = ExpandoValueServiceUtil.getColumnValues(columnId);

			return com.liferay.portal.model.ExpandoValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.ExpandoValueSoap[] getColumnValues(
		long columnId, int begin, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.ExpandoValue> returnValue = ExpandoValueServiceUtil.getColumnValues(columnId,
					begin, end);

			return com.liferay.portal.model.ExpandoValueSoap.toSoapModels(returnValue);
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

	public static com.liferay.portal.model.ExpandoValueSoap[] getRowValues(
		long rowId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.ExpandoValue> returnValue = ExpandoValueServiceUtil.getRowValues(rowId);

			return com.liferay.portal.model.ExpandoValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.ExpandoValueSoap[] getRowValues(
		long rowId, int begin, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.ExpandoValue> returnValue = ExpandoValueServiceUtil.getRowValues(rowId,
					begin, end);

			return com.liferay.portal.model.ExpandoValueSoap.toSoapModels(returnValue);
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

	public static com.liferay.portal.model.ExpandoValueSoap[] getValues(
		long classPK) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.ExpandoValue> returnValue = ExpandoValueServiceUtil.getValues(classPK);

			return com.liferay.portal.model.ExpandoValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.ExpandoValueSoap[] getValues(
		long classPK, int begin, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.ExpandoValue> returnValue = ExpandoValueServiceUtil.getValues(classPK,
					begin, end);

			return com.liferay.portal.model.ExpandoValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getValuesCount(long classPK) throws RemoteException {
		try {
			int returnValue = ExpandoValueServiceUtil.getValuesCount(classPK);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.ExpandoValueSoap setValue(
		long classPK, long columnId, java.lang.String value)
		throws RemoteException {
		try {
			com.liferay.portal.model.ExpandoValue returnValue = ExpandoValueServiceUtil.setValue(classPK,
					columnId, value);

			return com.liferay.portal.model.ExpandoValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long setRowValues(long tableId,
		java.util.List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws RemoteException {
		try {
			long returnValue = ExpandoValueServiceUtil.setRowValues(tableId,
					expandoValues);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long setRowValues(long tableId, long rowId,
		java.util.List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws RemoteException {
		try {
			long returnValue = ExpandoValueServiceUtil.setRowValues(tableId,
					rowId, expandoValues);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoValueServiceSoap.class);
}