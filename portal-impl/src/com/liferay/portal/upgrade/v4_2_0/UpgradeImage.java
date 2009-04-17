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

package com.liferay.portal.upgrade.v4_2_0;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.png.PNGImageReader;

/**
 * <a href="UpgradeImage.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Ed Shin
 *
 */
public class UpgradeImage extends UpgradeProcess {

	public static final String TYPE_GIF = "gif";

	public static final String TYPE_JPEG = "jpeg";

	public static final String TYPE_PNG = "png";

	public static final String TYPE_NOT_AVAILABLE = "na";

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			doUpgrade();
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"SELECT imageId, text_ FROM Image ");

			rs = ps.executeQuery();

			while (rs.next()) {
				String imageId = rs.getString("imageId");
				String text_ = rs.getString("text_");

				String type_ = getType(text_, imageId);

				ps = con.prepareStatement(
					"UPDATE Image SET type_ = ? WHERE imageId = ?");

				ps.setString(1, type_);
				ps.setString(2, imageId);

				ps.executeUpdate();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	public String getType(String text_, String imageId) {
		if (_log.isDebugEnabled()) {
			_log.debug("Attempt to detect type for " + imageId);
		}

		byte[] textObj = (byte[])Base64.stringToObject(text_);

		if (textObj == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Abort attempt to detect type because image is null");
			}

			return null;
		}

		String type = TYPE_NOT_AVAILABLE;

		ByteArrayInputStream bais = null;
		MemoryCacheImageInputStream mcis = null;

		try {
			bais = new ByteArrayInputStream(textObj);
			mcis = new MemoryCacheImageInputStream(bais);

			Iterator<ImageReader> itr = ImageIO.getImageReaders(mcis);

			while (itr.hasNext()) {
				ImageReader reader = (ImageReader)itr.next();

				if (reader instanceof GIFImageReader) {
					type = TYPE_GIF;
				}
				else if (reader instanceof JPEGImageReader) {
					type = TYPE_JPEG;
				}
				else if (reader instanceof PNGImageReader) {
					type = TYPE_PNG;
				}

				reader.dispose();
			}
		}
		finally {
			if (bais != null) {
				try {
					bais.close();
				}
				catch (IOException ioe) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioe);
					}
				}
			}

			if (mcis != null) {
				try {
					mcis.close();
				}
				catch (IOException ioe) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioe);
					}
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Detected type " + type);
		}

		return type;
	}

	private static Log _log = LogFactory.getLog(UpgradeImage.class);

}