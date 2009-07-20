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

package com.liferay.portal.upgrade.v5_0_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.v5_0_0.util.IGFolderNameColumnImpl;
import com.liferay.portal.upgrade.v5_0_0.util.IGImageNameColumnImpl;
import com.liferay.portlet.imagegallery.model.impl.IGFolderModelImpl;
import com.liferay.portlet.imagegallery.model.impl.IGImageModelImpl;

public class UpgradeImageGallery extends UpgradeProcess {

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

		// IGFolder

		UpgradeColumn groupIdColumn = new TempUpgradeColumnImpl("groupId");

		UpgradeColumn parentFolderIdColumn = new TempUpgradeColumnImpl(
			"parentFolderId");

		IGFolderNameColumnImpl igFolderNameColumn = new IGFolderNameColumnImpl(
			groupIdColumn, parentFolderIdColumn);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			IGFolderModelImpl.TABLE_NAME, IGFolderModelImpl.TABLE_COLUMNS,
			groupIdColumn, parentFolderIdColumn, igFolderNameColumn);

		upgradeTable.updateTable();

		// IGImage

		UpgradeColumn imageIdColumn = new TempUpgradeColumnImpl("imageId");

		IGImageNameColumnImpl imageNameColumn =
			new IGImageNameColumnImpl(imageIdColumn);

		upgradeTable = new DefaultUpgradeTableImpl(
			IGImageModelImpl.TABLE_NAME, IGImageModelImpl.TABLE_COLUMNS,
			imageIdColumn, imageNameColumn);

		upgradeTable.updateTable();
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeImageGallery.class);

}