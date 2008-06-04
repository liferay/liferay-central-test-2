/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package com.liferay.portal.search.lucene;

import junit.framework.TestCase;
import com.liferay.portal.kernel.search.IndexWriterMessage;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.util.JSONUtil;

/**
 * <a href="TestIndexWriterMessageSerializer.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class TestIndexWriterMessageSerializer extends TestCase {

    public void testSerialize() {
        try {
            String test2 =
                    "{\"companyId\":0,\"javaClass\":\"com.liferay.portal.kernel.search.IndexWriterMessage\",\"command\":\"update\",\"document\":{\"javaClass\":\"com.liferay.util.search.DocumentImpl\",\"fields\":{\"javaClass\":\"java.util.HashMap\",\"map\":{\"modified-date\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"20080409233347\",\"tokenized\":false,\"values\":[\"20080409233347\"],\"name\":\"modified-date\"},\"type\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"theme\",\"tokenized\":false,\"values\":[\"theme\"],\"name\":\"type\"},\"title\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"Leaves v.1\",\"tokenized\":true,\"values\":[\"Leaves v.1\"],\"name\":\"title\"},\"uid\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"PluginPackageIndexer_PORTLET_liferay/leaves-v1-theme/5.0.0.1/war\",\"tokenized\":false,\"values\":[\"PluginPackageIndexer_PORTLET_liferay/leaves-v1-theme/5.0.0.1/war\"],\"name\":\"uid\"},\"content\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"Leaves v.1 bartosz A nice square leafy theme. \",\"tokenized\":true,\"values\":[\"Leaves v.1 bartosz A nice square leafy theme. \"],\"name\":\"content\"},\"status\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"notInstalled\",\"tokenized\":false,\"values\":[\"notInstalled\"],\"name\":\"status\"},\"changeLog\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"Adapted to the latest version of Liferay.\",\"tokenized\":false,\"values\":[\"Adapted to the latest version of Liferay.\"],\"name\":\"changeLog\"},\"version\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"5.0.0.1\",\"tokenized\":false,\"values\":[\"5.0.0.1\"],\"name\":\"version\"},\"license\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"Unknown\",\"tokenized\":false,\"values\":[\"Unknown\"],\"name\":\"license\"},\"modified\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"20080604153014\",\"tokenized\":false,\"values\":[\"20080604153014\"],\"name\":\"modified\"},\"groupId\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"liferay\",\"tokenized\":false,\"values\":[\"liferay\"],\"name\":\"groupId\"},\"repositoryURL\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"http://plugins.liferay.com/community\",\"tokenized\":false,\"values\":[\"http://plugins.liferay.com/community\"],\"name\":\"repositoryURL\"},\"shortDescription\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"A nice square leafy theme.\",\"tokenized\":false,\"values\":[\"A nice square leafy theme.\"],\"name\":\"shortDescription\"},\"moduleId\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"liferay/leaves-v1-theme/5.0.0.1/war\",\"tokenized\":false,\"values\":[\"liferay/leaves-v1-theme/5.0.0.1/war\"],\"name\":\"moduleId\"},\"tag\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"oswd\",\"tokenized\":false,\"values\":[\"oswd\",\"1-column\"],\"name\":\"tag\"},\"osi-approved-license\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"true\",\"tokenized\":false,\"values\":[\"true\"],\"name\":\"osi-approved-license\"},\"portletId\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"PluginPackageIndexer\",\"tokenized\":false,\"values\":[\"PluginPackageIndexer\"],\"name\":\"portletId\"},\"artifactId\":{\"javaClass\":\"com.liferay.portal.kernel.search.Field\",\"value\":\"leaves-v1-theme\",\"tokenized\":false,\"values\":[\"leaves-v1-theme\"],\"name\":\"artifactId\"}}}},\"id\":\"PluginPackageIndexer_PORTLET_liferay/leaves-v1-theme/5.0.0.1/war\"}";
            IndexWriterMessage iwm2 =
                    (IndexWriterMessage) JSONUtil.deserialize(test2);
            assertEquals(test2, JSONUtil.serialize(iwm2));
        } catch (Exception e) {
            fail(StackTraceUtil.getStackTrace(e));
        }

    }
    protected void setUp() throws Exception {

    }

    protected void tearDown() throws Exception {

    }
}
