/*
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.cache.key;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="CacheKeyGeneratorUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class CacheKeyGeneratorUtil {

	public static String getCacheKey(String proposedKey, String cacheName) {
		CacheKeyGenerator cacheKeyGenerator = _cacheKeyGenerators.get(
			cacheName);

		if (cacheKeyGenerator == null) {
			cacheKeyGenerator = _defaultKeyGenerator;
		}

		if (cacheKeyGenerator != null) {
			return cacheKeyGenerator.getCacheKey(proposedKey);
		}

		return proposedKey;
	}

	public void setCacheKeyGenerators(
		Map<String, CacheKeyGenerator> cacheKeyGenerators) {

		_cacheKeyGenerators = cacheKeyGenerators;
	}

	public void setDefaultKeyGenerator(CacheKeyGenerator defaultKeyGenerator) {
		_defaultKeyGenerator = defaultKeyGenerator;
	}

	private static Map<String, CacheKeyGenerator> _cacheKeyGenerators =
		new HashMap<String, CacheKeyGenerator>();
	private static CacheKeyGenerator _defaultKeyGenerator;
}
