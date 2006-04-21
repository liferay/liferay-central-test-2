/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="NoteModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class NoteModel extends BaseModel {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Note"), XSS_ALLOW);
	public static boolean XSS_ALLOW_NOTEID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Note.noteId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Note.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Note.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Note.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSNAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Note.className"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSPK = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Note.classPK"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CONTENT = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Note.content"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.NoteModel"));

	public NoteModel() {
	}

	public String getPrimaryKey() {
		return _noteId;
	}

	public void setPrimaryKey(String pk) {
		setNoteId(pk);
	}

	public String getNoteId() {
		return GetterUtil.getString(_noteId);
	}

	public void setNoteId(String noteId) {
		if (((noteId == null) && (_noteId != null)) ||
				((noteId != null) && (_noteId == null)) ||
				((noteId != null) && (_noteId != null) &&
				!noteId.equals(_noteId))) {
			if (!XSS_ALLOW_NOTEID) {
				noteId = XSSUtil.strip(noteId);
			}

			_noteId = noteId;
			setModified(true);
		}
	}

	public String getCompanyId() {
		return GetterUtil.getString(_companyId);
	}

	public void setCompanyId(String companyId) {
		if (((companyId == null) && (_companyId != null)) ||
				((companyId != null) && (_companyId == null)) ||
				((companyId != null) && (_companyId != null) &&
				!companyId.equals(_companyId))) {
			if (!XSS_ALLOW_COMPANYID) {
				companyId = XSSUtil.strip(companyId);
			}

			_companyId = companyId;
			setModified(true);
		}
	}

	public String getUserId() {
		return GetterUtil.getString(_userId);
	}

	public void setUserId(String userId) {
		if (((userId == null) && (_userId != null)) ||
				((userId != null) && (_userId == null)) ||
				((userId != null) && (_userId != null) &&
				!userId.equals(_userId))) {
			if (!XSS_ALLOW_USERID) {
				userId = XSSUtil.strip(userId);
			}

			_userId = userId;
			setModified(true);
		}
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		if (((userName == null) && (_userName != null)) ||
				((userName != null) && (_userName == null)) ||
				((userName != null) && (_userName != null) &&
				!userName.equals(_userName))) {
			if (!XSS_ALLOW_USERNAME) {
				userName = XSSUtil.strip(userName);
			}

			_userName = userName;
			setModified(true);
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if (((createDate == null) && (_createDate != null)) ||
				((createDate != null) && (_createDate == null)) ||
				((createDate != null) && (_createDate != null) &&
				!createDate.equals(_createDate))) {
			_createDate = createDate;
			setModified(true);
		}
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (((modifiedDate == null) && (_modifiedDate != null)) ||
				((modifiedDate != null) && (_modifiedDate == null)) ||
				((modifiedDate != null) && (_modifiedDate != null) &&
				!modifiedDate.equals(_modifiedDate))) {
			_modifiedDate = modifiedDate;
			setModified(true);
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
			setModified(true);
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
			setModified(true);
		}
	}

	public String getContent() {
		return GetterUtil.getString(_content);
	}

	public void setContent(String content) {
		if (((content == null) && (_content != null)) ||
				((content != null) && (_content == null)) ||
				((content != null) && (_content != null) &&
				!content.equals(_content))) {
			if (!XSS_ALLOW_CONTENT) {
				content = XSSUtil.strip(content);
			}

			_content = content;
			setModified(true);
		}
	}

	public Object clone() {
		Note clone = new Note();
		clone.setNoteId(getNoteId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setClassName(getClassName());
		clone.setClassPK(getClassPK());
		clone.setContent(getContent());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		Note note = (Note)obj;
		int value = 0;
		value = getCompanyId().compareTo(note.getCompanyId());
		value = value * -1;

		if (value != 0) {
			return value;
		}

		value = getClassName().compareTo(note.getClassName());
		value = value * -1;

		if (value != 0) {
			return value;
		}

		value = getClassPK().compareTo(note.getClassPK());
		value = value * -1;

		if (value != 0) {
			return value;
		}

		value = getCreateDate().compareTo(note.getCreateDate());
		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Note note = null;

		try {
			note = (Note)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = note.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _noteId;
	private String _companyId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _className;
	private String _classPK;
	private String _content;
}