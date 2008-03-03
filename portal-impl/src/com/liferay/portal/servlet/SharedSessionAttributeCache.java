/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package com.liferay.portal.servlet;

import com.liferay.portal.util.PropsUtil;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="SharedSessionAttributeCache.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 * @version $Revision$
 */
public class SharedSessionAttributeCache {
    public static final String[] SHARED_SESSION_ATTRIBUTES =
            PropsUtil.getArray(PropsUtil.SESSION_SHARED_ATTRIBUTES);

    public static SharedSessionAttributeCache getInstance(HttpSession session) {
        synchronized (session) {
            SharedSessionAttributeCache cache =
                    (SharedSessionAttributeCache) session.getAttribute(
                            _SESSION_ID);
            if (cache == null) {
                cache = new SharedSessionAttributeCache();
                session.setAttribute(_SESSION_ID, cache);
            }
            return cache;
        }
    }

    public Map<String, Object> getValues() {
        final Map<String, Object> values = new HashMap<String, Object>();
        final Set<Map.Entry<String, Object>> entries = _attributes.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            values.put(entry.getKey(), entry.getValue());
        }
        return values;
    }

    public boolean contains(final String name) {
        return _attributes.containsKey(name);
    }

    public void setSessionAttribute(final String key, final Object value) {
        _attributes.put(key, value);
    }

    public void removeSessionAttribute(String key) {
        _attributes.remove(key);

    }

    private SharedSessionAttributeCache() {
        _attributes = new ConcurrentHashMap<String, Object>();

    }

    private static final String _SESSION_ID = "portal.session.attribute.cache;";
    private final Map<String, Object> _attributes;

}
