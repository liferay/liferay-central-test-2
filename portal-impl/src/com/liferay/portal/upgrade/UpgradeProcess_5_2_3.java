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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v5_2_3.UpgradeBookmarks;
import com.liferay.portal.upgrade.v5_2_3.UpgradeCalendar;
import com.liferay.portal.upgrade.v5_2_3.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v5_2_3.UpgradeDuplicates;
import com.liferay.portal.upgrade.v5_2_3.UpgradeGroup;
import com.liferay.portal.upgrade.v5_2_3.UpgradeImageGallery;
import com.liferay.portal.upgrade.v5_2_3.UpgradeLayout;
import com.liferay.portal.upgrade.v5_2_3.UpgradeMessageBoards;
import com.liferay.portal.upgrade.v5_2_3.UpgradeResource;
import com.liferay.portal.upgrade.v5_2_3.UpgradeResourceCode;
import com.liferay.portal.upgrade.v5_2_3.UpgradeRole;
import com.liferay.portal.upgrade.v5_2_3.UpgradeSchema;
import com.liferay.portal.upgrade.v5_2_3.UpgradeSoftwareCatalog;
import com.liferay.portal.upgrade.v5_2_3.UpgradeTags;
import com.liferay.portal.upgrade.v5_2_3.UpgradeUser;
import com.liferay.portal.upgrade.v5_2_3.UpgradeWiki;

/**
 * <a href="UpgradeProcess_5_2_3.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpgradeProcess_5_2_3 extends UpgradeProcess {

	public int getThreshold() {
		return ReleaseInfo.RELEASE_5_2_3_BUILD_NUMBER;
	}

	protected void doUpgrade() throws Exception {
		upgrade(UpgradeSchema.class);
		upgrade(UpgradeBookmarks.class);
		upgrade(UpgradeCalendar.class);
		upgrade(UpgradeDocumentLibrary.class);
		upgrade(UpgradeGroup.class);
		upgrade(UpgradeImageGallery.class);
		upgrade(UpgradeLayout.class);
		upgrade(UpgradeMessageBoards.class);
		upgrade(UpgradeResource.class);
		upgrade(UpgradeResourceCode.class);
		upgrade(UpgradeRole.class);
		upgrade(UpgradeSoftwareCatalog.class);
		upgrade(UpgradeTags.class);
		upgrade(UpgradeUser.class);
		upgrade(UpgradeWiki.class);
		upgrade(UpgradeDuplicates.class);
		upgrade(DropIndexes.class);
	}

}