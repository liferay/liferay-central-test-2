/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.servlet.taglib.BaseBodyTagSupport;
import com.liferay.portal.kernel.servlet.taglib.aui.ValidatorTag;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.PwdGenerator;

import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class ValidatorTagImpl
	extends BaseBodyTagSupport implements BodyTag, ValidatorTag {

	public ValidatorTagImpl() {
	}

	public ValidatorTagImpl(String name, String errorMessage, String body) {
		_name = name;
		_errorMessage = errorMessage;
		_body = body;

		processCustom();
	}

	public void cleanUp() {
		_body = null;
		_custom = false;
		_errorMessage = null;
		_name = null;
	}

	public int doAfterBody() {
		BodyContent bodyContent = getBodyContent();

		if (bodyContent != null) {
			_body = bodyContent.getString();
		}

		return SKIP_BODY;
	}

	public int doStartTag() {
		InputTag inputTag = (InputTag)findAncestorWithClass(
			this, InputTag.class);

		ValidatorTag validatorTag = new ValidatorTagImpl(
			_name, _errorMessage, _body);

		inputTag.addValidatorTag(_name, validatorTag);

		return EVAL_BODY_BUFFERED;
	}

	public String getBody() {
		if (Validator.isNull(_body)) {
			return StringPool.DOUBLE_APOSTROPHE;
		}

		return _body.trim();
	}

	public String getErrorMessage() {
		if (_errorMessage == null) {
			return StringPool.BLANK;
		}

		return _errorMessage;
	}

	public String getName() {
		return _name;
	}

	public boolean isCustom() {
		return _custom;
	}

	public void setBody(String body) {
		_body = body;
	}

	public void setErrorMessage(String errorMessage) {
		_errorMessage = errorMessage;
	}

	public void setName(String name) {
		_name = name;
	}

	protected void processCustom() {
		if (_name.equals("custom")) {
			_custom = true;

			_name = _name.concat(StringPool.UNDERLINE).concat(
				PwdGenerator.getPassword(PwdGenerator.KEY3, 4));
		}
	}

	private String _body;
	private boolean _custom = false;
	private String _errorMessage;
	private String _name;

}