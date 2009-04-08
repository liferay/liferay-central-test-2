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

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultPKMapper;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.ShoppingCartItemIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ShoppingCartItemIdsUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ShoppingCartTable;
import com.liferay.portal.upgrade.v4_3_0.util.ShoppingCategoryTable;
import com.liferay.portal.upgrade.v4_3_0.util.ShoppingCouponLimitCategoriesUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ShoppingCouponTable;
import com.liferay.portal.upgrade.v4_3_0.util.ShoppingItemPriceTable;
import com.liferay.portal.upgrade.v4_3_0.util.ShoppingItemTable;
import com.liferay.portal.upgrade.v4_3_0.util.ShoppingOrderItemTable;
import com.liferay.portal.upgrade.v4_3_0.util.ShoppingOrderTable;

import java.sql.Types;

/**
 * <a href="UpgradeShopping.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeShopping extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void doUpgrade() throws Exception {

		// ShoppingCategory

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", AvailableMappersUtil.getGroupIdMapper());

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		PKUpgradeColumnImpl upgradePKColumn = new PKUpgradeColumnImpl(
			"categoryId", true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingCategoryTable.TABLE_NAME, ShoppingCategoryTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeGroupIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(ShoppingCategoryTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper categoryIdMapper = new DefaultPKMapper(
			upgradePKColumn.getValueMapper());

		AvailableMappersUtil.setShoppingCategoryIdMapper(categoryIdMapper);

		UpgradeColumn upgradeParentCategoryIdColumn = new SwapUpgradeColumnImpl(
			"parentCategoryId", categoryIdMapper);

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingCategoryTable.TABLE_NAME, ShoppingCategoryTable.TABLE_COLUMNS,
			upgradeParentCategoryIdColumn);

		upgradeTable.updateTable();

		UpgradeColumn upgradeCategoryIdColumn = new SwapUpgradeColumnImpl(
			"categoryId", categoryIdMapper);

		// ShoppingItem

		upgradePKColumn = new PKUpgradeColumnImpl("itemId", true);

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingItemTable.TABLE_NAME, ShoppingItemTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeCategoryIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(ShoppingItemTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper itemIdMapper = upgradePKColumn.getValueMapper();

		AvailableMappersUtil.setShoppingItemIdMapper(itemIdMapper);

		UpgradeColumn upgradeItemIdColumn = new SwapUpgradeColumnImpl(
			"itemId", itemIdMapper);

		// ShoppingItemField

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingItemTable.TABLE_NAME,
			ShoppingItemTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("itemFieldId", false), upgradeItemIdColumn);

		upgradeTable.setCreateSQL(ShoppingItemTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// ShoppingItemPrice

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingItemPriceTable.TABLE_NAME,
			ShoppingItemPriceTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("itemPriceId", false), upgradeItemIdColumn);

		upgradeTable.setCreateSQL(ShoppingItemPriceTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// ShoppingOrder

		upgradePKColumn = new PKUpgradeColumnImpl(
			"orderId", new Integer(Types.VARCHAR), true);

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingOrderTable.TABLE_NAME, ShoppingOrderTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeGroupIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(ShoppingOrderTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper orderIdMapper = upgradePKColumn.getValueMapper();

		UpgradeColumn upgradeOrderIdColumn = new SwapUpgradeColumnImpl(
			"orderId", new Integer(Types.VARCHAR), orderIdMapper);

		// ShoppingOrderItem

		UpgradeColumn upgradeCartItemIdColumn =
			new ShoppingCartItemIdUpgradeColumnImpl(itemIdMapper);

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingOrderItemTable.TABLE_NAME,
			ShoppingOrderItemTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("orderItemId", false),
			upgradeOrderIdColumn, upgradeCartItemIdColumn);

		upgradeTable.setCreateSQL(ShoppingOrderItemTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// ShoppingCart

		UpgradeColumn upgradeItemIdsColumn =
			new ShoppingCartItemIdsUpgradeColumnImpl(itemIdMapper);

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingCartTable.TABLE_NAME, ShoppingCartTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl(
				"cartId", new Integer(Types.VARCHAR), false),
			upgradeGroupIdColumn, upgradeUserIdColumn, upgradeItemIdsColumn);

		upgradeTable.setCreateSQL(ShoppingCartTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// ShoppingCoupon

		UpgradeColumn upgradeLimitCategoriesColumn =
			new ShoppingCouponLimitCategoriesUpgradeColumnImpl(
				categoryIdMapper);

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingCouponTable.TABLE_NAME, ShoppingCouponTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl(
				"couponId", new Integer(Types.VARCHAR), false),
			upgradeGroupIdColumn, upgradeUserIdColumn,
			upgradeLimitCategoriesColumn);

		upgradeTable.setCreateSQL(ShoppingCouponTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeShopping.class);

}