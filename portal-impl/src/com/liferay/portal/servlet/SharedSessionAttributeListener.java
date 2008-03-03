/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package com.liferay.portal.servlet;

import com.liferay.portal.util.PropsUtil;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSession;

/**
 * <a href="SharedSessionAttributeListener.java.html"><b><i>View
 * Source</i></b></a>
 *
 * Listener used help manage shared session attributes into a cache.  This
 * cache is more thread safe than the HttpSession and leads to fewer problems
 * with shared session attributes being modified out of sequence.
 *
 * @author Michael C. Han
 * @version $Revision$
 */
public class SharedSessionAttributeListener
        implements HttpSessionAttributeListener {

    public static final String[] SHARED_SESSION_ATTRIBUTES =
        PropsUtil.getArray(PropsUtil.SESSION_SHARED_ATTRIBUTES);

    public void attributeAdded(final HttpSessionBindingEvent event) {
        final HttpSession session = event.getSession();
        final SharedSessionAttributeCache cache =
                SharedSessionAttributeCache.getInstance(session);
        final String attrName = event.getName();
        for (int i = 0; i < _LENGTH; i++) {
            if (attrName.startsWith(SHARED_SESSION_ATTRIBUTES[i])) {
                cache.setSessionAttribute(event.getName(), event.getValue());
                return;
            }
        }
    }

    public void attributeRemoved(final HttpSessionBindingEvent event) {
        final HttpSession session = event.getSession();
        final SharedSessionAttributeCache cache =
                SharedSessionAttributeCache.getInstance(session);
        cache.removeSessionAttribute(event.getName());
    }

    public void attributeReplaced(final HttpSessionBindingEvent event) {
        final HttpSession session = event.getSession();
        final SharedSessionAttributeCache cache =
                SharedSessionAttributeCache.getInstance(session);
        if (cache.contains(event.getName())) {
            cache.setSessionAttribute(event.getName(), event.getValue());
        }
    }

    private static final int _LENGTH = SHARED_SESSION_ATTRIBUTES.length;
}
