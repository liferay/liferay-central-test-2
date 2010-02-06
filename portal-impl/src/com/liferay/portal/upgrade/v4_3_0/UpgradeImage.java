/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.DefaultPKMapper;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.ImageHeightUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ImageSizeUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ImageTable;
import com.liferay.portal.upgrade.v4_3_0.util.ImageTextUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ImageTypeUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ImageWidthUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.WebIdUtil;

import java.sql.Types;

/**
 * <a href="UpgradeImage.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpgradeImage extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// Company

		String[] webIds = WebIdUtil.getWebIds();

		for (String webId : webIds) {
			runSQL("delete from Image where imageId = '" + webId + "'");

			runSQL("delete from Image where imageId = '" + webId + ".wbmp'");

			runSQL(
				"update Image set imageId = '" + webId + "' where imageId = '" +
					webId + ".png'");
		}

		// Image

		PKUpgradeColumnImpl upgradePKColumn = new PKUpgradeColumnImpl(
			"imageId", new Integer(Types.VARCHAR), true);

		ImageTextUpgradeColumnImpl upgradeTextColumn =
			new ImageTextUpgradeColumnImpl(upgradePKColumn);

		ImageTypeUpgradeColumnImpl upgradeTypeColumn =
			new ImageTypeUpgradeColumnImpl(upgradeTextColumn);

		ImageHeightUpgradeColumnImpl upgradeHeightColumn =
			new ImageHeightUpgradeColumnImpl(upgradeTextColumn);

		ImageWidthUpgradeColumnImpl upgradeWidthColumn =
			new ImageWidthUpgradeColumnImpl(upgradeTextColumn);

		ImageSizeUpgradeColumnImpl upgradeSizeColumn =
			new ImageSizeUpgradeColumnImpl(upgradeTextColumn);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			ImageTable.TABLE_NAME, ImageTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeTextColumn, upgradeTypeColumn,
			upgradeHeightColumn, upgradeWidthColumn, upgradeSizeColumn);

		upgradeTable.setCreateSQL(ImageTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper imageIdMapper = new DefaultPKMapper(
			upgradePKColumn.getValueMapper());

		AvailableMappersUtil.setImageIdMapper(imageIdMapper);
	}

}