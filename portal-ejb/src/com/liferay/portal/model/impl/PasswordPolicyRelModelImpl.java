/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

/**
 * <a href="PasswordPolicyRelModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>PasswordPolicyRel</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.PasswordPolicyRel
 * @see com.liferay.portal.service.model.PasswordPolicyRelModel
 * @see com.liferay.portal.service.model.impl.PasswordPolicyRelImpl
 *
 */
public class PasswordPolicyRelModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "PasswordPolicyRel";
	public static Object[][] TABLE_COLUMNS = {
			{ "passwordPolicyRelId", new Integer(Types.BIGINT) },
			{ "passwordPolicyId", new Integer(Types.BIGINT) },
			{ "className", new Integer(Types.VARCHAR) },
			{ "classPK", new Integer(Types.VARCHAR) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PasswordPolicyRel"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_CLASSNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PasswordPolicyRel.className"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSPK = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.PasswordPolicyRel.classPK"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.PasswordPolicyRelModel"));

	public PasswordPolicyRelModelImpl() {
	}

	public long getPrimaryKey() {
		return _passwordPolicyRelId;
	}

	public void setPrimaryKey(long pk) {
		setPasswordPolicyRelId(pk);
	}

	public long getPasswordPolicyRelId() {
		return _passwordPolicyRelId;
	}

	public void setPasswordPolicyRelId(long passwordPolicyRelId) {
		if (passwordPolicyRelId != _passwordPolicyRelId) {
			_passwordPolicyRelId = passwordPolicyRelId;
		}
	}

	public long getPasswordPolicyId() {
		return _passwordPolicyId;
	}

	public void setPasswordPolicyId(long passwordPolicyId) {
		if (passwordPolicyId != _passwordPolicyId) {
			_passwordPolicyId = passwordPolicyId;
		}
	}

	public String getClassName() {
		return GetterUtil.getString(_className);
	}

	public void setClassName(String className) {
		if (((className == null) && (_className != null)) ||
				((className != null) && (_className == null)) ||
				((className != null) && (_className != null) &&
				!className.equals(_className))) {
			if (!XSS_ALLOW_CLASSNAME) {
				className = XSSUtil.strip(className);
			}

			_className = className;
		}
	}

	public String getClassPK() {
		return GetterUtil.getString(_classPK);
	}

	public void setClassPK(String classPK) {
		if (((classPK == null) && (_classPK != null)) ||
				((classPK != null) && (_classPK == null)) ||
				((classPK != null) && (_classPK != null) &&
				!classPK.equals(_classPK))) {
			if (!XSS_ALLOW_CLASSPK) {
				classPK = XSSUtil.strip(classPK);
			}

			_classPK = classPK;
		}
	}

	public Object clone() {
		PasswordPolicyRelImpl clone = new PasswordPolicyRelImpl();
		clone.setPasswordPolicyRelId(getPasswordPolicyRelId());
		clone.setPasswordPolicyId(getPasswordPolicyId());
		clone.setClassName(getClassName());
		clone.setClassPK(getClassPK());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		PasswordPolicyRelImpl passwordPolicyRel = (PasswordPolicyRelImpl)obj;
		long pk = passwordPolicyRel.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		PasswordPolicyRelImpl passwordPolicyRel = null;

		try {
			passwordPolicyRel = (PasswordPolicyRelImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = passwordPolicyRel.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	private long _passwordPolicyRelId;
	private long _passwordPolicyId;
	private String _className;
	private String _classPK;
}