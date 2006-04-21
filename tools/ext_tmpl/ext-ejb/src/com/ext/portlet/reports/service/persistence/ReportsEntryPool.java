package com.ext.portlet.reports.service.persistence;

import com.ext.portlet.reports.model.ReportsEntry;

import com.liferay.portal.util.ClusterPool;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ReportsEntryPool {
    public static final String GROUP_NAME = ReportsEntryPool.class.getName();
    public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };
    private static Log _log = LogFactory.getLog(ReportsEntryPool.class);
    private static ReportsEntryPool _instance = new ReportsEntryPool();
    private GeneralCacheAdministrator _cache;
    private boolean _cacheable;

    private ReportsEntryPool() {
        _cacheable = ReportsEntry.CACHEABLE;
        _cache = ClusterPool.getCache();
        ClusterPool.registerPool(ReportsEntryPool.class.getName());
    }

    public static void clear() {
        _log.debug("Clear");
        _instance._clear();
    }

    public static ReportsEntry get(String entryId) {
        ReportsEntry reportsEntry = _instance._get(entryId);
        _log.info("Get " + entryId + " is " +
            ((reportsEntry == null) ? "NOT " : "") + "in cache");

        return reportsEntry;
    }

    public static ReportsEntry put(String entryId, ReportsEntry obj) {
        _log.info("Put " + entryId);

        return _instance._put(entryId, obj, false);
    }

    public static ReportsEntry remove(String entryId) {
        _log.info("Remove " + entryId);

        return _instance._remove(entryId);
    }

    public static ReportsEntry update(String entryId, ReportsEntry obj) {
        _log.info("Update " + entryId);

        return _instance._put(entryId, obj, true);
    }

    private void _clear() {
        _cache.flushGroup(GROUP_NAME);
    }

    private ReportsEntry _get(String entryId) {
        if (!_cacheable) {
            return null;
        } else if (entryId == null) {
            return null;
        } else {
            ReportsEntry obj = null;
            String key = _encodeKey(entryId);

            if (Validator.isNull(key)) {
                return null;
            }

            try {
                obj = (ReportsEntry) _cache.getFromCache(key);
            } catch (NeedsRefreshException nfe) {
            } finally {
                if (obj == null) {
                    _cache.cancelUpdate(key);
                }
            }

            return obj;
        }
    }

    private String _encodeKey(String entryId) {
        String entryIdString = String.valueOf(entryId);

        if (Validator.isNull(entryIdString)) {
            _log.debug("Key is null");

            return null;
        } else {
            String key = GROUP_NAME + StringPool.POUND + entryIdString;
            _log.debug("Key " + key);

            return key;
        }
    }

    private ReportsEntry _put(String entryId, ReportsEntry obj, boolean flush) {
        if (!_cacheable) {
            return obj;
        } else if (entryId == null) {
            return obj;
        } else {
            String key = _encodeKey(entryId);

            if (Validator.isNotNull(key)) {
                if (flush) {
                    _cache.flushEntry(key);
                }

                _cache.putInCache(key, obj, GROUP_NAME_ARRAY);
            }

            return obj;
        }
    }

    private ReportsEntry _remove(String entryId) {
        if (!_cacheable) {
            return null;
        } else if (entryId == null) {
            return null;
        } else {
            ReportsEntry obj = null;
            String key = _encodeKey(entryId);

            if (Validator.isNull(key)) {
                return null;
            }

            try {
                obj = (ReportsEntry) _cache.getFromCache(key);
                _cache.flushEntry(key);
            } catch (NeedsRefreshException nfe) {
            } finally {
                if (obj == null) {
                    _cache.cancelUpdate(key);
                }
            }

            return obj;
        }
    }
}
