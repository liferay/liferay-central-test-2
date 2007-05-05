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
import com.liferay.portal.tools.util.DBUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.v4_3_0.util.ResourceUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.impl.WikiNodeImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeWiki.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeWiki extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeCounter() throws Exception {
		CounterLocalServiceUtil.reset(WikiNode.class.getName());
	}

	private void _upgrade() throws Exception {

		// WikiNode

		PKUpgradeColumnImpl pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			WikiNodeImpl.TABLE_NAME, WikiNodeImpl.TABLE_COLUMNS,
			pkUpgradeColumn);

		upgradeTable.updateTable();

		ValueMapper nodeIdMapper = pkUpgradeColumn.getValueMapper();

		UpgradeColumn upgradeNodeIdColumn = new SwapUpgradeColumnImpl(
			"nodeId", nodeIdMapper);

		// WikiPage

		upgradeTable = new DefaultUpgradeTableImpl(
			WikiPageImpl.TABLE_NAME, WikiPageImpl.TABLE_COLUMNS,
			upgradeNodeIdColumn);

		upgradeTable.updateTable();

		// Resource

		ResourceUtil.upgradePrimKey(nodeIdMapper, WikiNode.class.getName());

		// Counter

		CounterLocalServiceUtil.reset(WikiNode.class.getName());

		// Schema

		DBUtil.getInstance().executeSQL(_UPGRADE_SCHEMA);
	}

	private static final String[] _UPGRADE_SCHEMA = {
		"alter table WikiPage drop primary key;",
		"alter table WikiPage add primary key (pageId);"
	};

	private static Log _log = LogFactory.getLog(UpgradeWiki.class);

}