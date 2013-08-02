/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.templateparser;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseTransformerListener implements TransformerListener {

	@Override
	public String getLanguageId() {
		return _languageId;
	}

	@Override
	public Map<String, String> getTokens() {
		return _tokens;
	}

	@Override
	public boolean isTemplateDriven() {
		return _templateDriven;
	}

	@Override
	public abstract String onOutput(String s);

	@Override
	public abstract String onScript(String s);

	@Override
	public abstract String onXml(String s);

	@Override
	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	@Override
	public void setTemplateDriven(boolean templateDriven) {
		_templateDriven = templateDriven;
	}

	@Override
	public void setTokens(Map<String, String> tokens) {
		_tokens = tokens;
	}

	private String _languageId;
	private boolean _templateDriven;
	private Map<String, String> _tokens;

}