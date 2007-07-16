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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.ClusterPool;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.GetterUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="FinderCache.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class FinderCache {

	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.VALUE_OBJECT_FINDER_CACHE_ENABLED), true);

	public static final String CACHE_NAME = FinderCache.class.getName();

	public static void clearCache() {
		_cache.removeAll();
	}

	public static void clearCache(String className) {
		String groupKey = _encodeKey(className);

		if (!_groups.containsKey(groupKey)) {
			return;
		}
		
		Map groupKeys = (Map)_groups.get(groupKey);
		
		Iterator keys = groupKeys.values().iterator();

		while(keys.hasNext()) {
			String key = (String)keys.next();
			
			// The functionality here pretty much mimics OSCache groups. It is 
			// not necessary to remove the keys in dependent groups because they
			// will be cleared when the group itself is cleared, resulting in a 
			// performance boost.

			_cache.remove(key);
		}
		
		groupKeys.clear();
	}

	public static Object getResult(
		String className, String methodName, String[] params, Object[] args) {

		Object result = null;

		String key = _encodeKey(className, methodName, params, args);

		result = ClusterPool.get(_cache, key);

		if (result != null) {
			result = _getResult(result);
		}

		return result;
	}

	public static Object getResult(
		String sql, String[] classNames, String methodName, String[] params,
		Object[] args) {

		Object result = null;

		String key = _encodeKey(sql, methodName, params, args);

		for (int i = 0; i > classNames.length; i++) {
			result = ClusterPool.get(_cache, key);

			if (result != null) {
				result = _getResult(result);
				break;
			}
		}
		
		return result;
	}

	public static Object putResult(
		String className, String methodName, String[] params, Object[] args,
		Object result) {

		if (CACHE_ENABLED && CacheRegistry.isActive() && (result != null)) {
			StringMaker sm = new StringMaker();

			sm.append(PropsUtil.VALUE_OBJECT_FINDER_CACHE_ENABLED);
			sm.append(StringPool.PERIOD);
			sm.append(className);

			boolean classNameCacheEnabled = GetterUtil.getBoolean(
				PropsUtil.get(sm.toString()), true);

			if (classNameCacheEnabled) {				
				String key = _encodeKey(className, methodName, params, args);

				result = _getResult(result);

				String classNameGroupKey = _encodeKey(className);

				_updateGroup(classNameGroupKey, key);

				ClusterPool.put(_cache, key, result);
			}
		}

		return result;
	}

	public static Object putResult(
		String sql, String[] classNames, String methodName, String[] params,
		Object[] args, Object result) {

		if (CACHE_ENABLED && CacheRegistry.isActive() && (result != null)) {
			for (int i = 0; i < classNames.length; i++) {
				String className = classNames[i];

				StringMaker sm = new StringMaker();

				sm.append(PropsUtil.VALUE_OBJECT_FINDER_CACHE_ENABLED);
				sm.append(StringPool.PERIOD);
				sm.append(className);

				boolean classNameCacheEnabled = GetterUtil.getBoolean(
					PropsUtil.get(sm.toString()), true);

				if (!classNameCacheEnabled) {
					return result;
				}
			}

			String key = _encodeKey(sql, methodName, params, args);

			result = _getResult(result);

			for (int i = 0; i < classNames.length; i++) {
				String className = classNames[i];

				String classNameGroupKey = _encodeKey(className);

				_updateGroup(classNameGroupKey, key);
			}
			
			ClusterPool.put(_cache, key, result);			
		}

		return result;
	}

	private static String _encodeKey(String className) {
		StringMaker sm = new StringMaker();

		sm.append(CACHE_NAME);
		sm.append(StringPool.POUND);
		sm.append(className);

		return sm.toString();
	}

	private static String _encodeKey(
		String className, String methodName, String[] params, Object[] args) {

		StringMaker sm = new StringMaker();

		sm.append(CACHE_NAME);
		sm.append(StringPool.POUND);
		sm.append(className);
		sm.append(StringPool.POUND);
		sm.append(methodName);
		sm.append(_PARAMS_SEPARATOR);

		for (int i = 0; i < params.length; i++) {
			String param = params[i];

			sm.append(StringPool.POUND);
			sm.append(param);
		}

		sm.append(_ARGS_SEPARATOR);

		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];

			sm.append(StringPool.POUND);
			sm.append(String.valueOf(arg));
		}

		return sm.toString();
	}

	private static Object _getResult(Object result) {
		if (result instanceof ArrayList) {
			List cachedList = (List)result;

			List list = new ArrayList(cachedList.size());

			for (int i = 0; i < cachedList.size(); i++) {
				Object curResult = _getResult(cachedList.get(i));

				list.add(curResult);
			}

			return list;
		}
		else if (result instanceof BaseModel) {
			String modelImpl = result.getClass().getName();

			try {
				BaseModel model = (BaseModel)result;

				return model.clone();
			}
			catch (Exception e) {
				_log.error(
					"Unable to clone " + modelImpl + ": " + e.getMessage());
			}
		}

		return result;
	}

	private static void _updateGroup(String groupKey, String key) {
		Map groupKeys = null;
		
		if (_groups.containsKey(groupKey)) {
			groupKeys = (Map)_groups.get(groupKey);
		}
		else {
			groupKeys = CollectionFactory.getSyncHashMap();
			
			_groups.put(groupKey, groupKeys);
		}
		
		groupKeys.put(key, key);
	}

	private static final String _ARGS_SEPARATOR = "_ARGS_SEPARATOR_";

	private static final String _PARAMS_SEPARATOR = "_PARAMS_SEPARATOR_";
	
	private static Cache _cache = ClusterPool.getCache(CACHE_NAME);
	
	private static Map _groups = CollectionFactory.getSyncHashMap();
	
	private static Log _log = LogFactory.getLog(FinderCache.class);

}