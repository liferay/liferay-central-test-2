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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.GetterUtil;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ResourcePrimKeyUpgradeColumnImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourcePrimKeyUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public ResourcePrimKeyUpgradeColumnImpl(
		TempUpgradeColumnImpl nameColumn,
		ResourceCodeIdUpgradeColumnImpl codeIdColumn,
		ValueMapper groupIdMapper, Map classPKContainers) {

		super("primKey");

		_nameColumn = nameColumn;
		_codeIdColumn = codeIdColumn;
		_groupIdMapper = groupIdMapper;
		_classPKContainers = classPKContainers;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String primKey = (String)oldValue;

		int scope = _codeIdColumn.getScope();

		if (scope == ResourceImpl.SCOPE_COMPANY) {
			return primKey;
		}
		else if (scope == ResourceImpl.SCOPE_GROUP) {
			return String.valueOf(_groupIdMapper.getNewValue(
				new Long(GetterUtil.getLong(primKey))));
		}
		else if (scope == ResourceImpl.SCOPE_INDIVIDUAL) {
			String name = (String)_nameColumn.getTemp();

			if (name.startsWith("com.liferay.")) {
				Long classNameIdObj = new Long(PortalUtil.getClassNameId(name));

				ClassPKContainer classPKContainer =
					(ClassPKContainer)_classPKContainers.get(classNameIdObj);

				if (classPKContainer != null) {
					ValueMapper valueMapper = classPKContainer.getValueMapper();

					if (classPKContainer.isLong()) {
						primKey = String.valueOf(valueMapper.getNewValue(
							new Long(GetterUtil.getLong((String)oldValue))));
					}
					else {
						primKey = String.valueOf(
							valueMapper.getNewValue(oldValue));
					}
				}
				else {
					_log.error("Name " + name + " is invalid");
				}
			}

			return primKey;
		}
		else {
			throw new UpgradeException("Scope " + scope + " is invalid");
		}
	}

	private static Log _log =
		LogFactory.getLog(ResourcePrimKeyUpgradeColumnImpl.class);

	private TempUpgradeColumnImpl _nameColumn;
	private ResourceCodeIdUpgradeColumnImpl _codeIdColumn;
	private ValueMapper _groupIdMapper;
	private Map _classPKContainers;

}