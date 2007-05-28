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

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.counter.service.CounterLocalServiceUtil;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.tools.util.DBUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.v4_3_0.util.DefaultParentIdMapper;
import com.liferay.portal.upgrade.v4_3_0.util.ResourceUtil;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.impl.ShoppingCategoryImpl;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.ShoppingItemField;
import com.liferay.portlet.shopping.model.impl.ShoppingItemFieldImpl;

import com.liferay.portlet.shopping.model.impl.ShoppingItemImpl;
import com.liferay.portlet.shopping.model.ShoppingItemPrice;
import com.liferay.portlet.shopping.model.impl.ShoppingItemPriceImpl;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderImpl;
import com.liferay.portlet.shopping.model.ShoppingOrderItem;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderItemImpl;
import com.liferay.portlet.shopping.model.ShoppingCart;
import com.liferay.portlet.shopping.model.impl.ShoppingCartImpl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.impl.AddressImpl;
import com.liferay.portal.tools.util.DBUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.v4_3_0.util.ClassNameUpgradeColumnImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
			_upgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgrade() throws Exception {

		// ShoppingCoupon

		// ShoppingCart

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingCartImpl.TABLE_NAME, ShoppingCartImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl());

		upgradeTable.updateTable();

		// ShoppingCategory

		PKUpgradeColumnImpl pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingCategoryImpl.TABLE_NAME, ShoppingCategoryImpl.TABLE_COLUMNS,
			pkUpgradeColumn);

		upgradeTable.updateTable();

		ValueMapper categoryIdMapper = new DefaultParentIdMapper(
			pkUpgradeColumn.getValueMapper());

		UpgradeColumn upgradeParentCategoryIdColumn = new SwapUpgradeColumnImpl(
			"parentCategoryId", categoryIdMapper);

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingCategoryImpl.TABLE_NAME, ShoppingCategoryImpl.TABLE_COLUMNS,
			upgradeParentCategoryIdColumn);

		upgradeTable.updateTable();

		UpgradeColumn upgradeCategoryIdColumn = new SwapUpgradeColumnImpl(
			"categoryId", categoryIdMapper);

		// ShoppingItem

		pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingItemImpl.TABLE_NAME, ShoppingItemImpl.TABLE_COLUMNS,
			pkUpgradeColumn, upgradeCategoryIdColumn);

		upgradeTable.updateTable();

		ValueMapper itemIdMapper = pkUpgradeColumn.getValueMapper();

		UpgradeColumn upgradeItemIdColumn = new SwapUpgradeColumnImpl(
			"itemId", itemIdMapper);

		// ShoppingItemField

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingItemFieldImpl.TABLE_NAME,
			ShoppingItemFieldImpl.TABLE_COLUMNS, new PKUpgradeColumnImpl(),
			upgradeItemIdColumn);

		upgradeTable.updateTable();

		// ShoppingItemPrice

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingItemPriceImpl.TABLE_NAME,
			ShoppingItemPriceImpl.TABLE_COLUMNS, new PKUpgradeColumnImpl(),
			upgradeItemIdColumn);

		upgradeTable.updateTable();

		// ShoppingOrder

		pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingOrderImpl.TABLE_NAME, ShoppingOrderImpl.TABLE_COLUMNS,
			pkUpgradeColumn);

		upgradeTable.updateTable();

		ValueMapper orderIdMapper = pkUpgradeColumn.getValueMapper();

		UpgradeColumn upgradeOrderIdColumn = new SwapUpgradeColumnImpl(
			"orderId", orderIdMapper);

		// ShoppingOrderItem

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingOrderItemImpl.TABLE_NAME,
			ShoppingOrderItemImpl.TABLE_COLUMNS, new PKUpgradeColumnImpl(),
			upgradeOrderIdColumn, upgradeItemIdColumn);

		upgradeTable.updateTable();

		// Resource

		ResourceUtil.upgradePrimKey(
			categoryIdMapper, ShoppingCategory.class.getName());
		ResourceUtil.upgradePrimKey(itemIdMapper, ShoppingItem.class.getName());

		// Counter

		CounterLocalServiceUtil.reset(ShoppingCategory.class.getName());
		CounterLocalServiceUtil.reset(ShoppingItem.class.getName());

		// Schema

		DBUtil.getInstance().executeSQL(_UPGRADE_SCHEMA);
	}

	private static final String[] _UPGRADE_SCHEMA = {
		"alter_column_type ShoppingCart cartId LONG;",

		"alter_column_type ShoppingCoupon couponId LONG;",

		"alter_column_type ShoppingOrder orderId LONG;",

		"alter table ShoppingOrderItem drop primary key;",
		"alter table ShoppingOrderItem add primary key (orderItemId);"
	};

	private static Log _log = LogFactory.getLog(UpgradeShopping.class);

}