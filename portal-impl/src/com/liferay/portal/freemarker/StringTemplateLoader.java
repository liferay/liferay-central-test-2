/*
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
 */

package com.liferay.portal.freemarker;

import freemarker.cache.TemplateLoader;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class StringTemplateLoader implements TemplateLoader {
	public void closeTemplateSource(Object templateSource) {
	}

	public Object findTemplateSource(String name) {
		return _templates.get(name);
	}

	public long getLastModified(Object templateSource) {
		return ((StringTemplateSource)templateSource)._lastModified;
	}

	public Reader getReader(Object templateSource, String encoding) {
		return new StringReader(((StringTemplateSource)templateSource)._source);
	}

    public void putTemplate(String name, String templateSource) {
        putTemplate(name, templateSource, System.currentTimeMillis());
    }

    public void putTemplate(
		String name, String templateSource, long lastModified) {

        _templates.put(
			name, new StringTemplateSource(name, templateSource, lastModified));
    }

	public void removeTemplate(String name) {
		_templates.remove(name);
	}

    private static class StringTemplateSource {
		private String _name;
        private String _source;
        private long _lastModified;

        StringTemplateSource(String name, String source, long lastModified) {
            if(name == null) {
                throw new IllegalArgumentException("name == null");
            }
            if(source == null) {
                throw new IllegalArgumentException("source == null");
            }
            if(lastModified < -1L) {
                throw new IllegalArgumentException("lastModified < -1L");
            }
            this._name = name;
            this._source = source;
            this._lastModified = lastModified;
        }

        public boolean equals(Object obj) {
            if(obj instanceof StringTemplateSource) {
                return _name.equals(((StringTemplateSource)obj)._name);
            }
            return false;
        }

        public int hashCode() {
            return _name.hashCode();
        }
    }

	private Map _templates = new HashMap();

}
