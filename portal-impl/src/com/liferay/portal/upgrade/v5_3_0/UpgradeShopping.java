/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v5_3_0;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v5_3_0.util.ShoppingCategoryView;
import com.liferay.portlet.shopping.model.impl.ShoppingCategoryImpl;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * <a href="UpgradeShopping.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class UpgradeShopping extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		BufferedReader categoryReader = null;

		ShoppingCategoryView categoryView =
			new ShoppingCategoryView(ShoppingCategoryImpl.TABLE_NAME);

		String categoryViewFile = categoryView.generateTempFile();

		try {
			categoryReader =
				new BufferedReader(new FileReader(categoryViewFile));

			String line = null;

			while (Validator.isNotNull(line = categoryReader.readLine())) {
				String[] values = StringUtil.split(line);

				long categoryId = ShoppingCategoryView.getCategoryId(values);
				long groupId = ShoppingCategoryView.getGroupId(values);

				String sql = StringUtil.replace(
					_UPDATE_SQL,
					new String [] {"_GROUP_ID_", "_CATEGORY_ID_"},
					new String [] {
						Long.toString(groupId),
						Long.toString(categoryId)});

				DBUtil.getInstance().runSQL(sql);
			}
		}
		finally {
			if (categoryReader != null) {
				categoryReader.close();
			}

			FileUtil.delete(categoryViewFile);
		}
	}

	private static final String _UPDATE_SQL =
		"UPDATE ShoppingItem SET groupId = _GROUP_ID_ where categoryId = " +
		"_CATEGORY_ID_";

}