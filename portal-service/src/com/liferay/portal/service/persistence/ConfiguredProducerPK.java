/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * <a href="ConfiguredProducerPK.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ConfiguredProducerPK implements Comparable<ConfiguredProducerPK>,
	Serializable {
	public String portalId;
	public String configuredProducerId;

	public ConfiguredProducerPK() {
	}

	public ConfiguredProducerPK(String portalId, String configuredProducerId) {
		this.portalId = portalId;
		this.configuredProducerId = configuredProducerId;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getConfiguredProducerId() {
		return configuredProducerId;
	}

	public void setConfiguredProducerId(String configuredProducerId) {
		this.configuredProducerId = configuredProducerId;
	}

	public int compareTo(ConfiguredProducerPK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		value = portalId.compareTo(pk.portalId);

		if (value != 0) {
			return value;
		}

		value = configuredProducerId.compareTo(pk.configuredProducerId);

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ConfiguredProducerPK pk = null;

		try {
			pk = (ConfiguredProducerPK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((portalId.equals(pk.portalId)) &&
				(configuredProducerId.equals(pk.configuredProducerId))) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (String.valueOf(portalId) +
		String.valueOf(configuredProducerId)).hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(StringPool.OPEN_CURLY_BRACE);

		sb.append("portalId");
		sb.append(StringPool.EQUAL);
		sb.append(portalId);

		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("configuredProducerId");
		sb.append(StringPool.EQUAL);
		sb.append(configuredProducerId);

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}
}