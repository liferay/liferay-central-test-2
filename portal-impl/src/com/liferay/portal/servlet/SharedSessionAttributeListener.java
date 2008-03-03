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

package com.liferay.portal.servlet;

import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import java.util.Hashtable;
import java.util.Map;

/**
 * <a href="SharedSessionAttributeListener.java.html"><b><i>View Source</i></b>
 * </a>
 * <p/>
 * Listener used to help manage shared session attributes into a cache. This
 * cache is more thread safe than the HttpSession and leads to fewer problems
 * with shared session attributes being modified out of sequence.
 *
 * @author Michael C. Han
 */
public class SharedSessionAttributeListener
        implements HttpSessionAttributeListener, HttpSessionListener {

    public void attributeAdded(final HttpSessionBindingEvent event) {
        final HttpSession ses = event.getSession();

        //determine if session has been expired...
        if (!_sessionIds.containsKey(ses.getId())) {
            return;
        }

        final SharedSessionAttributeCache cache =
                SharedSessionAttributeCache.getInstance(ses);

        final String name = event.getName();

        for (String sharedName : PropsValues.SHARED_SESSION_ATTRIBUTES) {
            if (name.startsWith(sharedName)) {
                cache.setAttribute(name, event.getValue());

                return;
            }
        }
    }

    public void attributeRemoved(final HttpSessionBindingEvent event) {

        final HttpSession ses = event.getSession();

        //determine if session has been expired...
        if (!_sessionIds.containsKey(ses.getId())) {
            return;
        }

        final SharedSessionAttributeCache cache =
                SharedSessionAttributeCache.getInstance(ses);

        cache.removeAttribute(event.getName());
    }

    public void attributeReplaced(final HttpSessionBindingEvent event) {
        final HttpSession ses = event.getSession();

        //determine if session has been expired...
        if (!_sessionIds.containsKey(ses.getId())) {
            return;
        }
        
        final SharedSessionAttributeCache cache =
                SharedSessionAttributeCache.getInstance(ses);

        if (cache.contains(event.getName())) {
            cache.setAttribute(event.getName(), event.getValue());
        }
    }

    public void sessionCreated(final HttpSessionEvent event) {
        final HttpSession ses = event.getSession();

        SharedSessionAttributeCache.getInstance(ses);

        _sessionIds.put(ses.getId(), ses.getId());

    }

    public void sessionDestroyed(final HttpSessionEvent event) {
        final HttpSession ses = event.getSession();

        _sessionIds.remove(ses.getId());
    }

    private final Map<String, String> _sessionIds =
            new Hashtable<String, String>();
}