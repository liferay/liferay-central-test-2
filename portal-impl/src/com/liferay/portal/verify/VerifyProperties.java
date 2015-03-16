/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyProperties extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {

		// system.properties

		for (String[] keys : _MIGRATED_SYSTEM_KEYS) {
			String oldKey = keys[0];
			String newKey = keys[1];

			verifyMigratedSystemProperty(oldKey, newKey);
		}

		for (String[] keys : _RENAMED_SYSTEM_KEYS) {
			String oldKey = keys[0];
			String newKey = keys[1];

			verifyRenamedSystemProperty(oldKey, newKey);
		}

		for (String key : _OBSOLETE_SYSTEM_KEYS) {
			verifyObsoleteSystemProperty(key);
		}

		// portal.properties

		for (String[] keys : _MIGRATED_PORTAL_KEYS) {
			String oldKey = keys[0];
			String newKey = keys[1];

			verifyMigratedPortalProperty(oldKey, newKey);
		}

		for (String[] keys : _RENAMED_PORTAL_KEYS) {
			String oldKey = keys[0];
			String newKey = keys[1];

			verifyRenamedPortalProperty(oldKey, newKey);
		}

		for (String key : _OBSOLETE_PORTAL_KEYS) {
			verifyObsoletePortalProperty(key);
		}

		for (String[] keys : _MODULARIZED_PORTAL_KEYS) {
			String oldKey = keys[0];
			String newKey = keys[1];
			String moduleName = keys[2];

			verifyModularizedPortalProperty(oldKey, newKey, moduleName);
		}

		// Document library

		StoreFactory.checkProperties();

		// LDAP

		verifyLDAPProperties();
	}

	protected boolean isPortalProperty(String key) {
		String value = PropsUtil.get(key);

		if (value != null) {
			return true;
		}

		return false;
	}

	protected void verifyLDAPProperties() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			UnicodeProperties properties = new UnicodeProperties();

			long[] ldapServerIds = StringUtil.split(
				PrefsPropsUtil.getString(companyId, "ldap.server.ids"), 0L);

			for (long ldapServerId : ldapServerIds) {
				String postfix = LDAPSettingsUtil.getPropertyPostfix(
					ldapServerId);

				for (String key : _LDAP_KEYS) {
					String value = PrefsPropsUtil.getString(
						companyId, key + postfix, null);

					if (value == null) {
						properties.put(key + postfix, StringPool.BLANK);
					}
				}
			}

			if (!properties.isEmpty()) {
				CompanyLocalServiceUtil.updatePreferences(
					companyId, properties);
			}
		}
	}

	protected void verifyMigratedPortalProperty(String oldKey, String newKey)
		throws Exception {

		if (isPortalProperty(oldKey)) {
			_log.error(
				"Portal property \"" + oldKey +
					"\" was migrated to the system property \"" + newKey +
						"\"");
		}
	}

	protected void verifyMigratedSystemProperty(String oldKey, String newKey)
		throws Exception {

		String value = SystemProperties.get(oldKey);

		if (value != null) {
			_log.error(
				"System property \"" + oldKey +
					"\" was migrated to the portal property \"" + newKey +
						"\"");
		}
	}

	protected void verifyModularizedPortalProperty(
			String oldKey, String newKey, String moduleName)
		throws Exception {

		if (isPortalProperty(oldKey)) {
			_log.error(
				"Portal property \"" + oldKey + "\" was modularized to " +
					moduleName + " as \"" + newKey);
		}
	}

	protected void verifyObsoletePortalProperty(String key) throws Exception {
		if (isPortalProperty(key)) {
			_log.error("Portal property \"" + key + "\" is obsolete");
		}
	}

	protected void verifyObsoleteSystemProperty(String key) throws Exception {
		String value = SystemProperties.get(key);

		if (value != null) {
			_log.error("System property \"" + key + "\" is obsolete");
		}
	}

	protected void verifyRenamedPortalProperty(String oldKey, String newKey)
		throws Exception {

		if (isPortalProperty(oldKey)) {
			_log.error(
				"Portal property \"" + oldKey + "\" was renamed to \"" +
					newKey + "\"");
		}
	}

	protected void verifyRenamedSystemProperty(String oldKey, String newKey)
		throws Exception {

		String value = SystemProperties.get(oldKey);

		if (value != null) {
			_log.error(
				"System property \"" + oldKey + "\" was renamed to \"" +
					newKey + "\"");
		}
	}

	private static final String[] _LDAP_KEYS = {
		PropsKeys.LDAP_CONTACT_CUSTOM_MAPPINGS, PropsKeys.LDAP_CONTACT_MAPPINGS,
		PropsKeys.LDAP_USER_CUSTOM_MAPPINGS
	};

	private static final String[][] _MIGRATED_PORTAL_KEYS = new String[][] {
		new String[] {
			"finalize.manager.thread.enabled",
			"com.liferay.portal.kernel.memory.FinalizeManager.thread.enabled"
		}
	};

	private static final String[][] _MIGRATED_SYSTEM_KEYS = new String[][] {
		new String[] {
			"com.liferay.filters.compression.CompressionFilter",
			"com.liferay.portal.servlet.filters.gzip.GZipFilter"
		},
		new String[] {
			"com.liferay.filters.strip.StripFilter",
			"com.liferay.portal.servlet.filters.strip.StripFilter"
		},
		new String[] {
			"com.liferay.util.Http.max.connections.per.host",
			"com.liferay.portal.util.HttpImpl.max.connections.per.host"
		},
		new String[] {
			"com.liferay.util.Http.max.total.connections",
			"com.liferay.portal.util.HttpImpl.max.total.connections"
		},
		new String[] {
			"com.liferay.util.Http.proxy.auth.type",
			"com.liferay.portal.util.HttpImpl.proxy.auth.type"
		},
		new String[] {
			"com.liferay.util.Http.proxy.ntlm.domain",
			"com.liferay.portal.util.HttpImpl.proxy.ntlm.domain"
		},
		new String[] {
			"com.liferay.util.Http.proxy.ntlm.host",
			"com.liferay.portal.util.HttpImpl.proxy.ntlm.host"
		},
		new String[] {
			"com.liferay.util.Http.proxy.password",
			"com.liferay.portal.util.HttpImpl.proxy.password"
		},
		new String[] {
			"com.liferay.util.Http.proxy.username",
			"com.liferay.portal.util.HttpImpl.proxy.username"
		},
		new String[] {
			"com.liferay.util.Http.timeout",
			"com.liferay.portal.util.HttpImpl.timeout"
		},
		new String[] {
			"com.liferay.util.format.PhoneNumberFormat",
			"phone.number.format.impl"
		},
		new String[] {
			"com.liferay.util.servlet.UploadServletRequest.max.size",
			"com.liferay.portal.upload.UploadServletRequestImpl.max.size"
		},
		new String[] {
			"com.liferay.util.servlet.UploadServletRequest.temp.dir",
			"com.liferay.portal.upload.UploadServletRequestImpl.temp.dir"
		},
		new String[] {
			"com.liferay.util.servlet.fileupload.LiferayFileItem." +
				"threshold.size",
			"com.liferay.portal.upload.LiferayFileItem.threshold.size"
		},
		new String[] {
			"com.liferay.util.servlet.fileupload.LiferayInputStream." +
				"threshold.size",
			"com.liferay.portal.upload.LiferayInputStream.threshold.size"
		}
	};

	private static final String[][] _MODULARIZED_PORTAL_KEYS = {

		// Asset

		new String[] {
			"asset.browser.search.with.database", "search.with.database",
			"com.liferay.asset.browser.web"
		},
		new String[] {
			"asset.categories.navigation.display.templates.config",
			"display.templates.config",
			"com.liferay.asset.categories.navigation.web"
		},
		new String[] {
			"asset.publisher.check.interval", "check.interval",
			"com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.email.from.name", "email.from.name",
			"com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.email.from.address", "email.from.address",
			"com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.email.asset.entry.added.enabled",
			"email.asset.entry.added.enabled", "com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.email.asset.entry.added.subject",
			"email.asset.entry.added.subject", "com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.email.asset.entry.added.body",
			"email.asset.entry.added.body", "com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.display.style.default", "display.style.default",
			"com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.display.styles", "display.styles",
			"com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.display.templates.config",
			"display.templates.config", "com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.dynamic.subscription.limit",
			"dynamic.subscription.limit", "com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.permission.checking.configurable",
			"permission.checking.configurable",
			"com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.query.form.configuration",
			"query.form.configuration", "com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.search.with.index", "search.with.index",
			"com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.tags.navigation.display.templates.config",
			"display.templates.config", "com.liferay.asset.tags.navigation.web"
		},

		// Bookmarks

		new String[] {
			"bookmarks.email.entry.added.body", "email.entry.added.body",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.email.entry.added.enabled", "email.entry.added.enabled",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.email.entry.added.subject", "email.entry.added.subject",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.email.entry.updated.body", "email.entry.updated.body",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.email.entry.updated.enabled",
			"email.entry.updated.enabled", "com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.email.entry.updated.subject",
			"email.entry.updated.subject", "com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.email.from.address", "email.from.address",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.email.from.name", "email.from.name",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.entry.columns", "entry.columns",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.folder.columns", "folder.columns",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.folders.search.visible", "folders.search.visible",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.related.assets.enabled", "related.assets.enabled",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.subfolders.visible", "subfolders.visible",
			"com.liferay.bookmarks.service"
		},

		// Breadcrumb

		new String[] {
			"breadcrumb.display.templates.config", "display.templates.config",
			"com.liferay.breadcrumb.web"
		},

		// CAS

		new String[] {
			"cas.auth.enabled", "enabled", "com.liferay.portal.sso.cas"
		},
		new String[] {
			"cas.import.from.ldap", "import.from.ldap",
			"com.liferay.portal.sso.cas"
		},
		new String[] {
			"cas.login.url", "login.url", "com.liferay.portal.sso.cas"
		},
		new String[] {
			"cas.logout.on.session.expiration", "logout.on.session.expiration",
			"com.liferay.portal.sso.cas"
		},
		new String[] {
			"cas.logout.url", "logout.url", "com.liferay.portal.sso.cas"
		},
		new String[] {
			"cas.no.such.user.redirect.url", "no.such.user.redirect.url",
			"com.liferay.portal.sso.cas"
		},
		new String[] {
			"cas.server.name", "server.name", "com.liferay.portal.sso.cas"
		},
		new String[] {
			"cas.server.url", "server.url", "com.liferay.portal.sso.cas"
		},
		new String[] {
			"cas.service.url", "service.url", "com.liferay.portal.sso.cas"
		},

		// Currency Converter

		new String[] {
			"currency.converter.symbols", "symbols",
			"com.liferay.currency.converter.web"
		},

		// FreeMarker Engine

		new String[] {
			"freemarker.engine.localized.lookup", "localized.lookup",
			"com.liferay.portal.template.freemarker"
		},
		new String[] {
			"freemarker.engine.macro.library", "macro.library",
			"com.liferay.portal.template.freemarker"
		},
		new String[] {
			"freemarker.engine.resource.modification.check.interval",
			"resource.modification.check",
			"com.liferay.portal.template.freemarker"
		},
		new String[] {
			"freemarker.engine.restricted.classes", "restricted.classes",
			"com.liferay.portal.template.freemarker"
		},
		new String[] {
			"freemarker.engine.restricted.packages", "restricted.packages",
			"com.liferay.portal.template.freemarker"
		},
		new String[] {
			"freemarker.engine.template.exception.handler",
			"template.exception.handler",
			"com.liferay.portal.template.freemarker"
		},
		new String[] {
			"freemarker.engine.template.parsers", "template.parsers",
			"com.liferay.portal.template.freemarker"
		},
		new String[] {
			"journal.template.freemarker.restricted.variables",
			"restricted.variables", "com.liferay.portal.template.freemarker"
		},

		// IFrame

		new String[] {
			"iframe.auth", "auth", "com.liferay.iframe.web"
		},
		new String[] {
			"iframe.auth-type", "auth.type", "com.liferay.iframe.web"
		},
		new String[] {
			"iframe.form-method", "form.method", "com.liferay.iframe.web"
		},
		new String[] {
			"iframe.hidden-variables", "hidden.variables",
			"com.liferay.iframe.web"
		},

		// Journal

		new String[] {
			"journal.content.publish.to.live.by.default",
			"publish.to.live.by.default", "com.liferay.journal.content.web"
		},

		new String[] {
			"journal.content.search.show.listed", "show.listed",
			"com.liferay.journal.content.search.web"
		},

		// Language

		new String[] {
			"language.display.templates.config", "display.templates.config",
			"com.liferay.language.web"
		},

		// LDAP

		new String[] {
			"ldap.auth.enabled", "enabled",
			"com.liferay.portal.authenticator.ldap"
		},
		new String[] {
			"ldap.auth.method", "method",
			"com.liferay.portal.authenticator.ldap"
		},
		new String[] {
			"ldap.auth.password.encryption.algorithm",
			"passwordEncryptionAlgorithm",
			"com.liferay.portal.authenticator.ldap"
		},
		new String[] {
			"ldap.auth.required", "required",
			"com.liferay.portal.authenticator.ldap"
		},
		new String[] {
			"ldap.export.enabled", "export.enabled", "com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.export.group.enabled", "export.group.enabled",
			"com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.factory.initial", "factory.initial", "com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.import.create.role.per.group", "import.create.role.per.group",
			"com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.import.enabled", "import.enabled", "com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.import.group.cache.enabled", "import.group.cache.enabled",
			"com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.import.group.search.filter.enabled",
			"import.group.search.filter.enabled", "com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.import.interval", "import.interval", "com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.import.lock.expiration.time", "import.lock.expiration.time",
			"com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.import.method", "import.method", "com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.import.on.startup", "import.on.startup",
			"com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.import.user.password.autogenerated",
			"import.user.password.autogenerated", "com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.import.user.password.default", "import.user.password.default",
			"com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.import.user.password.enabled", "import.user.password.enabled",
			"com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.page.size", "page.size", "com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.password.policy.enabled", "password.policy.enabled",
			"com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.range.size", "range.size", "com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.referral", "referral", "com.liferay.portal.ldap"
		},
		new String[] {
			"ldap.user.ignore.attributes", "user.ignore.attributes",
			"com.liferay.portal.ldap"
		},

		// Monitoring

		new String[] {
			"monitoring.portal.request", "monitor.portal.request",
			"com.liferay.portal.monitoring"
		},
		new String[] {
			"monitoring.portlet.action.request",
			"monitor.portlet.action.request", "com.liferay.portal.monitoring"
		},
		new String[] {
			"monitoring.portlet.event.request", "monitor.portlet.event.request",
			"com.liferay.portal.monitoring"
		},
		new String[] {
			"monitoring.portlet.render.request",
			"monitor.portlet.render.request", "com.liferay.portal.monitoring"
		},
		new String[] {
			"monitoring.portlet.resource.request",
			"monitor.portlet.resource.request", "com.liferay.portal.monitoring"
		},
		new String[] {
			"monitoring.show.per.request.data.sample",
			"show.per.request.data.sample", "com.liferay.portal.monitoring"
		},

		// Navigation

		new String[] {
			"navigation.display.style", "display.style",
			"com.liferay.navigation.web"
		},
		new String[] {
			"navigation.display.style.default", "display.style.default",
			"com.liferay.navigation.web"
		},
		new String[] {
			"navigation.display.style.options", "display.style.options",
			"com.liferay.navigation.web"
		},

		// Nested Portlets

		new String[] {
			"nested.portlets.layout.template.default",
			"layout.template.default", "com.liferay.nested.portlets.web"
		},
		new String[] {
			"nested.portlets.layout.template.unsupported",
			"layout.template.unsupported", "com.liferay.nested.portlets.web"
		},

		// NTLM

		new String[] {
			"ntlm.auth.enabled", "enabled", "com.liferay.portal.sso.ntlm"
		},
		new String[] {
			"ntlm.auth.domain", "domain", "com.liferay.portal.sso.ntlm"
		},
		new String[] {
			"ntlm.auth.domain.controller", "domain.controller",
			"com.liferay.portal.sso.ntlm"
		},
		new String[] {
			"ntlm.auth.domain.controller.name", "domain.controller.name",
			"com.liferay.portal.sso.ntlm"
		},
		new String[] {
			"ntlm.auth.negotiate.flags", "negotiate.flags",
			"com.liferay.portal.sso.ntlm"
		},
		new String[] {
			"ntlm.auth.service.account", "service.account",
			"com.liferay.portal.sso.ntlm"
		},
		new String[] {
			"ntlm.auth.service.password", "service.password",
			"com.liferay.portal.sso.ntlm"
		},

		// OpenSSO

		new String[] {
			"open.sso.auth.enabled", "enabled", "com.liferay.portal.sso.opensso"
		},
		new String[] {
			"open.sso.email.address.attr", "email.address.attr",
			"com.liferay.portal.sso.opensso"
		},
		new String[] {
			"open.sso.first.name.attr", "first.name.attr",
			"com.liferay.portal.sso.opensso"
		},
		new String[] {
			"open.sso.last.name.attr", "last.name.attr",
			"com.liferay.portal.sso.opensso"
		},
		new String[] {
			"open.sso.import.from.ldap", "import.from.ldap",
			"com.liferay.portal.sso.opensso"
		},
		new String[] {
			"open.sso.login.url", "login.url", "com.liferay.portal.sso.opensso"
		},
		new String[] {
			"open.sso.logout.on.session.expiration",
			"logout.on.session.expiration", "com.liferay.portal.sso.opensso"
		},
		new String[] {
			"open.sso.logout.url", "logout.url",
			"com.liferay.portal.sso.opensso"
		},
		new String[] {
			"open.sso.screen.name.attr", "screen.name.attr",
			"com.liferay.portal.sso.opensso"
		},
		new String[] {
			"open.sso.service.url", "service.url",
			"com.liferay.portal.sso.opensso"
		},

		// Polls

		new String[] {
			"polls.publish.to.live.by.default", "publish.to.live.by.default",
			"com.liferay.polls.service"
		},

		// RSS

		new String[] {
			"rss.display.templates.config", "display.templates.config",
			"com.liferay.rss.web"
		},

		// Search

		new String[] {
			"search.facet.configuration", "facet.configuration",
			"com.liferay.search.web"
		},

		// Site Map

		new String[] {
			"sitemap.display.templates.config", "display.templates.config",
			"com.liferay.site.map.web"
		},

		// Tags Compiler

		new String[] {
			"tags.compiler.enabled", "enabled",
			"com.liferay.asset.tags.compiler.web"
		},

		// Translator

		new String[] {
			"translator.default.languages", "translation.id",
			"com.liferay.translator.web"
		},
		new String[] {
			"translator.languages", "language.ids", "com.liferay.translator.web"
		},

		// Velocity Engine

		new String[] {
			"velocity.engine.directive.if.to.string.null.check",
			"directive.if.to.string.null.check",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.resource.parsers", "resource.parsers",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.resource.modification.check.interval",
			"resource.modification.check.interval",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.restricted.classes", "restricted.classes",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.restricted.packages", "restricted.packages",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.restricted.variables", "restricted.variables",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.velocimacro.library", "macro.library",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.logger", "logger",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.logger.category", "logger.category",
			"com.liferay.portal.template.velocity"
		},

		// XSL Content

		new String[] {
			"xsl.content.xml.doctype.declaration.allowed",
			"xml.doctype.declaration.allowed", "com.liferay.xsl.content.web"
		},
		new String[] {
			"xsl.content.xml.external.general.entities.allowed",
			"xml.external.general.entities.allowed",
			"com.liferay.xsl.content.web"
		},
		new String[] {
			"xsl.content.xml.external.parameter.entities.allowed",
			"xml.external.parameter.entities.allowed",
			"com.liferay.xsl.content.web"
		},
		new String[] {
			"xsl.content.xsl.secure.processing.enabled",
			"xsl.secure.processing.enabled", "com.liferay.xsl.content.web"
		},

		// XSL Engine

		new String[] {
			"xsl.template.secure.processing.enabled",
			"secure.processing.enabled", "com.liferay.portal.template.xsl"
		}
	};

	private static final String[] _OBSOLETE_PORTAL_KEYS = new String[] {
		"amazon.access.key.id", "amazon.associate.tag",
		"amazon.secret.access.key",
		"asset.entry.increment.view.counter.enabled",
		"asset.publisher.asset.entry.query.processors",
		"asset.publisher.filter.unlistable.entries",
		"asset.tag.permissions.enabled", "asset.tag.properties.default",
		"asset.tag.properties.enabled", "auth.max.failures.limit",
		"breadcrumb.display.style.options",
		"buffered.increment.parallel.queue.size",
		"buffered.increment.serial.queue.size", "cas.validate.url",
		"cluster.executor.heartbeat.interval",
		"com.liferay.filters.doubleclick.DoubleClickFilter",
		"com.liferay.portal.servlet.filters.doubleclick.DoubleClickFilter",
		"com.liferay.portal.servlet.filters.charbufferpool." +
			"CharBufferPoolFilter",
		"com.liferay.portal.servlet.filters.monitoring.MonitoringFilter",
		"com.liferay.portal.servlet.filters.validhtml.ValidHtmlFilter",
		"commons.pool.enabled", "convert.processes", "discussion.thread.view",
		"dl.file.entry.read.count.enabled",
		"dynamic.data.lists.template.language.parser[ftl]",
		"dynamic.data.lists.template.language.parser[vm]",
		"dynamic.data.lists.template.language.parser[xsl]",
		"dynamic.data.mapping.structure.private.field.names",
		"dynamic.data.mapping.structure.private.field.datatype[_fieldsDisplay]",
		"dynamic.data.mapping.structure.private.field.repeatable[" +
			"_fieldsDisplay]",
		"dynamic.data.mapping.template.language.types",
		"editor.wysiwyg.portal-web.docroot.html.portlet.asset_publisher." +
			"configuration.jsp",
		"editor.wysiwyg.portal-web.docroot.html.portlet.blogs.configuration." +
			"jsp",
		"editor.wysiwyg.portal-web.docroot.html.portlet.bookmarks." +
			"configuration.jsp",
		"editor.wysiwyg.portal-web.docroot.html.portlet.document_library." +
		"editor.wysiwyg.portal-web.docroot.html.portlet.invitation." +
			"configuration.jsp",
		"editor.wysiwyg.portal-web.docroot.html.portlet.journal." +
			"configuration.jsp",
		"editor.wysiwyg.portal-web.docroot.html.portlet.login.configuration." +
			"jsp",
		"editor.wysiwyg.portal-web.docroot.html.portlet.message_boards." +
			"configuration.jsp",
		"editor.wysiwyg.portal-web.docroot.html.portlet.portal_settings." +
			"email_notifications.jsp",
		"ehcache.statistics.enabled", "index.filter.search.limit",
		"invitation.email.max.recipients", "invitation.email.message.body",
		"invitation.email.message.subject", "javax.persistence.validation.mode",
		"jbi.workflow.url", "json.deserializer.strict.mode",
		"journal.article.form.translate", "journal.article.types",
		"journal.articles.page.delta.values",
		"journal.template.language.parser[css]",
		"journal.template.language.parser[ftl]",
		"journal.template.language.parser[vm]",
		"journal.template.language.parser[xsl]",
		"journal.template.language.types", "jpa.configs",
		"jpa.database.platform", "jpa.database.type", "jpa.load.time.weaver",
		"jpa.provider", "jpa.provider.property.eclipselink.allow-zero-id",
		"jpa.provider.property.eclipselink.logging.level",
		"jpa.provider.property.eclipselink.logging.timestamp", "layout.types",
		"lucene.analyzer", "lucene.store.jdbc.auto.clean.up",
		"lucene.store.jdbc.auto.clean.up.enabled",
		"lucene.store.jdbc.auto.clean.up.interval",
		"lucene.store.jdbc.dialect.db2", "lucene.store.jdbc.dialect.derby",
		"lucene.store.jdbc.dialect.hsqldb", "lucene.store.jdbc.dialect.jtds",
		"lucene.store.jdbc.dialect.microsoft",
		"lucene.store.jdbc.dialect.mysql", "lucene.store.jdbc.dialect.oracle",
		"lucene.store.jdbc.dialect.postgresql",
		"memory.cluster.scheduler.lock.cache.enabled",
		"message.boards.email.message.added.signature",
		"message.boards.email.message.updated.signature",
		"message.boards.thread.locking.enabled", "portal.ctx",
		"portal.security.manager.enable", "permissions.list.filter",
		"permissions.thread.local.cache.max.size",
		"permissions.user.check.algorithm", "persistence.provider",
		"ratings.max.score", "ratings.min.score", "scheduler.classes",
		"schema.run.minimal", "shard.available.names", "staging.lock.enabled",
		"webdav.storage.class", "webdav.storage.show.edit.url",
		"webdav.storage.show.view.url", "webdav.storage.tokens",
		"wiki.email.page.added.signature", "wiki.email.page.updated.signature",
		"xss.allow"
	};

	private static final String[] _OBSOLETE_SYSTEM_KEYS = new String[] {
		"com.liferay.util.Http.proxy.host", "com.liferay.util.Http.proxy.port",
		"com.liferay.util.XSSUtil.regexp.pattern"
	};

	private static final String[][] _RENAMED_PORTAL_KEYS = new String[][] {
		new String[] {
			"amazon.license.0", "amazon.access.key.id"
		},
		new String[] {
			"amazon.license.1", "amazon.access.key.id"
		},
		new String[] {
			"amazon.license.2", "amazon.access.key.id"
		},
		new String[] {
			"amazon.license.3", "amazon.access.key.id"
		},
		new String[] {
			"breadcrumb.display.style.default",
			"breadcrumb.ddm.template.key.default"
		},
		new String[] {
			"cdn.host", "cdn.host.http"
		},
		new String[] {
			"cluster.executor.debug.enabled", "cluster.link.debug.enabled"
		},
		new String[] {
			"com.liferay.portal.servlet.filters.compression.CompressionFilter",
			"com.liferay.portal.servlet.filters.gzip.GZipFilter"
		},
		new String[] {
			"default.guest.friendly.url",
			"default.guest.public.layout.friendly.url"
		},
		new String[] {
			"default.guest.layout.column", "default.guest.public.layout.column"
		},
		new String[] {
			"default.guest.layout.name", "default.guest.public.layout.name"
		},
		new String[] {
			"default.guest.layout.template.id",
			"default.guest.public.layout.template.id"
		},
		new String[] {
			"default.user.layout.column", "default.user.public.layout.column"
		},
		new String[] {
			"default.user.layout.name", "default.user.public.layout.name"
		},
		new String[] {
			"default.user.layout.template.id",
			"default.user.public.layout.template.id"
		},
		new String[] {
			"default.user.private.layout.lar",
			"default.user.private.layouts.lar"
		},
		new String[] {
			"default.user.public.layout.lar", "default.user.public.layouts.lar"
		},
		new String[] {
			"dl.hook.cmis.credentials.password",
			"dl.store.cmis.credentials.password"
		},
		new String[] {
			"dl.hook.cmis.credentials.username",
			"dl.store.cmis.credentials.username"
		},
		new String[] {
			"dl.hook.cmis.repository.url", "dl.store.cmis.repository.url"
		},
		new String[] {
			"dl.hook.cmis.system.root.dir", "dl.store.cmis.system.root.dir"
		},
		new String[] {
			"dl.hook.file.system.root.dir", "dl.store.file.system.root.dir"
		},
		new String[] {
			"dl.hook.impl", "dl.store.impl"
		},
		new String[] {
			"dl.hook.jcr.fetch.delay", "dl.store.jcr.fetch.delay"
		},
		new String[] {
			"dl.hook.jcr.fetch.max.failures", "dl.store.jcr.fetch.max.failures"
		},
		new String[] {
			"dl.hook.jcr.move.version.labels",
			"dl.store.jcr.move.version.labels"
		},
		new String[] {
			"dl.hook.s3.access.key", "dl.store.s3.access.key"
		},
		new String[] {
			"dl.hook.s3.bucket.name", "dl.store.s3.bucket.name"
		},
		new String[] {
			"dl.hook.s3.secret.key", "dl.store.s3.secret.key"
		},
		new String[] {
			"editor.wysiwyg.portal-web.docroot.html.portlet.calendar." +
				"edit_configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.calendar." +
				"configuration.jsp"
		},
		new String[] {
			"editor.wysiwyg.portal-web.docroot.html.portlet.invitation." +
				"edit_configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.invitation." +
				"configuration.jsp"
		},
		new String[] {
			"editor.wysiwyg.portal-web.docroot.html.portlet.journal." +
				"edit_configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.journal." +
				"configuration.jsp"
		},
		new String[] {
			"editor.wysiwyg.portal-web.docroot.html.portlet.message_boards." +
				"edit_configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.message_boards." +
				"configuration.jsp"
		},
		new String[] {
			"editor.wysiwyg.portal-web.docroot.html.portlet.shopping." +
				"edit_configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.shopping." +
				"configuration.jsp"
		},
		new String[] {
			"field.editable.com.liferay.portal.model.User.emailAddress",
			"field.editable.user.types"
		},
		new String[] {
			"field.editable.com.liferay.portal.model.User.screenName",
			"field.editable.user.types"
		},
		new String[] {
			"icon.menu.max.display.items", "menu.max.display.items"
		},
		new String[] {
			"journal.error.template.freemarker", "journal.error.template[ftl]"
		},
		new String[] {
			"journal.error.template.velocity", "journal.error.template[vm]"
		},
		new String[] {
			"journal.error.template.xsl", "journal.error.template[xsl]"
		},
		new String[] {
			"journal.template.velocity.restricted.variables",
			"velocity.engine.restricted.variables"
		},
		new String[] {
			"passwords.passwordpolicytoolkit.charset.lowercase",
			"passwords.passwordpolicytoolkit.validator.charset.lowercase"
		},
		new String[] {
			"passwords.passwordpolicytoolkit.charset.numbers",
			"passwords.passwordpolicytoolkit.validator.charset.numbers"
		},
		new String[] {
			"passwords.passwordpolicytoolkit.charset.symbols",
			"passwords.passwordpolicytoolkit.validator.charset.symbols"
		},
		new String[] {
			"passwords.passwordpolicytoolkit.charset.uppercase",
			"passwords.passwordpolicytoolkit.validator.charset.uppercase"
		},
		new String[] {
			"permissions.inline.sql.resource.block.query.threshhold",
			"permissions.inline.sql.resource.block.query.threshold"
		},
		new String[] {
			"portal.instance.http.port", "portal.instance.http.socket.address"
		},
		new String[] {
			"portal.instance.https.port", "portal.instance.http.socket.address"
		},
		new String[] {
			"referer.url.domains.allowed", "redirect.url.domains.allowed"
		},
		new String[] {
			"referer.url.ips.allowed", "redirect.url.ips.allowed"
		},
		new String[] {
			"referer.url.security.mode", "redirect.url.security.mode"
		},
		new String[] {
			"tags.asset.increment.view.counter.enabled",
			"asset.entry.increment.view.counter.enabled"
		}
	};

	private static final String[][] _RENAMED_SYSTEM_KEYS = new String[][] {
		new String[] {
			"com.liferay.portal.kernel.util.StringBundler.unsafe.create." +
				"threshold",
			"com.liferay.portal.kernel.util.StringBundler.threadlocal.buffer." +
				"limit"
		}
	};

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyProperties.class);

}