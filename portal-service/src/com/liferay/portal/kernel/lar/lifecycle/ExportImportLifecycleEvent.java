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

package com.liferay.portal.kernel.lar.lifecycle;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public interface ExportImportLifecycleEvent extends Serializable {

	public List<Serializable> getAttributes();

	public int getCode();

	public boolean getProcessFlag(int processFlag);

	public Map<Integer, Boolean> getProcessFlags();

	public void setAttributes(Serializable... attributes);

	public void setCode(int eventCode);

	public void setProcessFlags(Map<Integer, Boolean> processFlags);

}