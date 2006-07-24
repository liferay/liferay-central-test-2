/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.util;

import com.germinus.easyconf.ComponentProperties;

import com.liferay.util.ExtPropertiesLoader;

import java.util.Properties;

/**
 * <a href="PropsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PropsUtil {

	static {
		InitUtil.init();
	}

	// Portal Release

	public static final String PORTAL_RELEASE = "portal.release";

	// Portal Context

	public static final String PORTAL_CTX = "portal.ctx";

	public static final String PORTAL_INSTANCES = "portal.instances";

	// Error

	public static final String ERROR_MESSAGE_LOG = "error.message.log";

	public static final String ERROR_MESSAGE_PRINT = "error.message.print";

	public static final String ERROR_MESSAGE_SHOW = "error.message.show";

	public static final String ERROR_STACK_TRACE_LOG = "error.stack.trace.log";

	public static final String ERROR_STACK_TRACE_PRINT = "error.stack.trace.print";

	public static final String ERROR_STACK_TRACE_SHOW = "error.stack.trace.show";

	// TCK

	public static final String TCK_URL = "tck.url";

	public static final String TCK_PORTLET_URL_APPEND_PARAMETERS = "tck.portlet.url.append.parameters";

	// Upgrade

	public static final String UPGRADE_PROCESSES = "upgrade.processes";

	// Auto Deploy

	public static final String AUTO_DEPLOY_ENABLED = "auto.deploy.enabled";

	public static final String AUTO_DEPLOY_DEPLOY_DIR = "auto.deploy.deploy.dir";

	public static final String AUTO_DEPLOY_DEST_DIR = "auto.deploy.dest.dir";

	public static final String AUTO_DEPLOY_INTERVAL = "auto.deploy.interval";

	public static final String AUTO_DEPLOY_UNPACK_WAR = "auto.deploy.unpack.war";

	public static final String AUTO_DEPLOY_TOMCAT_LIB_DIR = "auto.deploy.tomcat.lib.dir";

	// Resource Actions

	public static final String RESOURCE_ACTIONS_CONFIGS = "resource.actions.configs";

	// Model Hints

	public static final String MODEL_HINTS_CONFIGS = "model.hints.configs";

	// Spring

	public static final String SPRING_CONFIGS = "spring.configs";

	public static final String SPRING_HIBERNATE_SESSION_FACTORY = "spring.hibernate.session.factory";

	// Hibernate

	public static final String HIBERNATE_CONFIGS = "hibernate.configs";

	public static final String HIBERNATE_DIALECT = "hibernate.dialect.";

	// Custom SQL

	public static final String CUSTOM_SQL_CONFIGS = "custom.sql.configs";

	public static final String CUSTOM_SQL_FUNCTION_ISNULL = "custom.sql.function.isnull";

	public static final String CUSTOM_SQL_VENDOR_ORACLE = "custom.sql.vendor.oracle";

	// JavaScript

	public static final String JAVASCRIPT_FAST_LOAD = "javascript.fast.load";

	// Company

	public static final String COMPANY_TYPES = "company.types";

	public static final String COMPANY_SECURITY_AUTH_TYPE = "company.security.auth.type";

	public static final String COMPANY_SECURITY_AUTO_LOGIN = "company.security.auto.login";

	public static final String COMPANY_SECURITY_STRANGERS = "company.security.strangers";

	// Users

	public static final String USERS_DELETE = "users.delete";

	public static final String USERS_ID_ALWAYS_AUTOGENERATE = "users.id.always.autogenerate";

	public static final String USERS_ID_GENERATOR = "users.id.generator";

	public static final String USERS_ID_VALIDATOR = "users.id.validator";

	public static final String USERS_IMAGE_MAX_SIZE = "users.image.max.size";

	// Groups and Roles

	public static final String SYSTEM_GROUPS = "system.groups";

	public static final String SYSTEM_ROLES = "system.roles";

	public static final String OMNIADMIN_USERS = "omniadmin.users";

	public static final String UNIVERSAL_PERSONALIZATION = "universal.personalization";

	public static final String TERMS_OF_USE_REQUIRED = "terms.of.use.required";

	// Languages and Time Zones

	public static final String LOCALES = "locales";

	public static final String LOCALE_DEFAULT_REQUEST = "locale.default.request";

	public static final String STRUTS_CHAR_ENCODING = "struts.char.encoding";

	public static final String TIME_ZONES = "time.zones";

	// Organizations and Locations

	public static final String ORGANIZATIONS_PARENT_ORGANIZATION_REQUIRED = "organizations.parent.organization.required";

	public static final String ORGANIZATIONS_LOCATION_REQUIRED = "organizations.location.required";

	// Look and Feel

	public static final String LOOK_AND_FEEL_MODIFIABLE = "look.and.feel.modifiable";

	public static final String DEFAULT_THEME_ID = "default.theme.id";

	public static final String DEFAULT_COLOR_SCHEME_ID = "default.color.scheme.id";

	// Session

	public static final String SESSION_TIMEOUT = "session.timeout";

	public static final String SESSION_TIMEOUT_WARNING = "session.timeout.warning";

	public static final String SESSION_ENABLE_PERSISTENT_COOKIES = "session.enable.persistent.cookies";

	public static final String SERVLET_SESSION_CREATE_EVENTS = "servlet.session.create.events";

	public static final String SERVLET_SESSION_DESTROY_EVENTS = "servlet.session.destroy.events";

	public static final String SESSION_TRACKER_MEMORY_ENABLED = "session.tracker.memory.enabled";

	public static final String SESSION_TRACKER_PERSISTENCE_ENABLED = "session.tracker.persistence.enabled";

	// JAAS

	public static final String PRINCIPAL_FINDER = "principal.finder";

	public static final String PORTAL_CONFIGURATION = "portal.configuration";

	public static final String PORTAL_JAAS_ENABLE = "portal.jaas.enable";

	public static final String PORTAL_JAAS_IMPL = "portal.jaas.impl";

	// Authentication Pipeline

	public static final String AUTH_PIPELINE_PRE = "auth.pipeline.pre";

	public static final String AUTH_PIPELINE_POST = "auth.pipeline.post";

	public static final String AUTH_PIPELINE_ENABLE_LIFERAY_CHECK = "auth.pipeline.enable.liferay.check";

	public static final String AUTH_IMPL_LDAP_ENABLED = "auth.impl.ldap.enabled";

	public static final String AUTH_IMPL_LDAP_REQUIRED = "auth.impl.ldap.required";

	public static final String AUTH_IMPL_LDAP_FACTORY_INITIAL = "auth.impl.ldap.factory.initial";

    public static final String AUTH_IMPL_LDAP_PROVIDER_URL = "auth.impl.ldap.provider.url";

	public static final String AUTH_IMPL_LDAP_SECURITY_PRINCIPAL = "auth.impl.ldap.security.principal";

	public static final String AUTH_IMPL_LDAP_SECURITY_CREDENTIALS = "auth.impl.ldap.security.credentials";

	public static final String AUTH_IMPL_LDAP_SEARCH_FILTER = "auth.impl.ldap.search.filter";

	public static final String AUTH_IMPL_LDAP_USER_MAPPINGS = "auth.impl.ldap.user.mappings";

	public static final String AUTH_FAILURE = "auth.failure";

	public static final String AUTH_MAX_FAILURES = "auth.max.failures";

	public static final String AUTH_MAX_FAILURES_LIMIT = "auth.max.failures.limit";

	public static final String AUTH_SIMULTANEOUS_LOGINS = "auth.simultaneous.logins";

	public static final String AUTH_FORWARD_BY_LAST_PATH = "auth.forward.by.last.path";

	public static final String AUTH_FORWARD_LAST_PATH = "auth.forward.last.path.";

	public static final String AUTH_PUBLIC_PATH = "auth.public.path.";

	// Auto Login

	public static final String AUTO_LOGIN_HOOKS = "auto.login.hooks";

	public static final String AUTO_LOGIN_DISABLED_PATH = "auto.login.disabled.path.";

	// Passwords

	public static final String PASSWORDS_ENCRYPTED = "passwords.encrypted";

	public static final String PASSWORDS_TOOLKIT = "passwords.toolkit";

	public static final String PASSWORDS_REGEXPTOOLKIT_PATTERN = "passwords.regexptoolkit.pattern";

	public static final String PASSWORDS_ALLOW_DICTIONARY_WORD = "passwords.allow.dictionary.word";

	public static final String PASSWORDS_CHANGE_ON_FIRST_USE = "passwords.change.on.first.use";

	public static final String PASSWORDS_LIFESPAN = "passwords.lifespan";

	public static final String PASSWORDS_RECYCLE = "passwords.recycle";

	// Permissions

	public static final String PERMISSIONS_CHECKER = "permissions.checker";

	// Captcha

	public static final String CAPTCHA_MAX_CHALLENGES = "captcha.max.challenges";

	// Startup Events

	public static final String GLOBAL_STARTUP_EVENTS = "global.startup.events";

	public static final String APPLICATION_STARTUP_EVENTS = "application.startup.events";

	// Shutdown Events

	public static final String GLOBAL_SHUTDOWN_EVENTS = "global.shutdown.events";

	public static final String APPLICATION_SHUTDOWN_EVENTS = "application.shutdown.events";

	// Portal Events

	public static final String SERVLET_SERVICE_EVENTS_PRE = "servlet.service.events.pre";

	public static final String SERVLET_SERVICE_EVENTS_PRE_ERROR_PAGE = "servlet.service.events.pre.error.page";

	public static final String SERVLET_SERVICE_EVENTS_POST = "servlet.service.events.post";

	public static final String LOGIN_EVENTS_PRE = "login.events.pre";

	public static final String LOGIN_EVENTS_POST = "login.events.post";

	public static final String LOGOUT_EVENTS_PRE = "logout.events.pre";

	public static final String LOGOUT_EVENTS_POST = "logout.events.post";

	// Default Guest Layout

	public static final String DEFAULT_GUEST_LAYOUT_NAME = "default.guest.layout.name";

	public static final String DEFAULT_GUEST_LAYOUT_TEMPLATE_ID = "default.guest.layout.template.id";

	public static final String DEFAULT_GUEST_LAYOUT_COLUMN = "default.guest.layout.column-";

	public static final String DEFAULT_GUEST_LAYOUT_RESOLUTION = "default.guest.layout.resolution";

	// Default User Layout

	public static final String DEFAULT_USER_LAYOUT_NAME = "default.user.layout.name";

	public static final String DEFAULT_USER_LAYOUT_TEMPLATE_ID = "default.user.layout.template.id";

	public static final String DEFAULT_USER_LAYOUT_COLUMN = "default.user.layout.column-";

	public static final String DEFAULT_USER_LAYOUT_RESOLUTION = "default.user.layout.resolution";

	// Layouts

	public static final String LAYOUT_TYPES = "layout.types";

	public static final String LAYOUT_EDIT_PAGE = "layout.edit.page";

	public static final String LAYOUT_VIEW_PAGE = "layout.view.page";

	public static final String LAYOUT_URL = "layout.url";

	public static final String LAYOUT_URL_FRIENDLIABLE = "layout.url.friendliable";

	public static final String LAYOUT_PARENTABLE = "layout.parentable";

	public static final String LAYOUT_STATIC_PORTLETS_START = "layout.static.portlets.start.";

	public static final String LAYOUT_STATIC_PORTLETS_END = "layout.static.portlets.end.";

	public static final String LAYOUT_ALLOW_ONE_COLUMN = "layout.allow.one.column";

	public static final String LAYOUT_ALLOW_TWO_COLUMN = "layout.allow.two.column";

	public static final String LAYOUT_ALLOW_THREE_COLUMN = "layout.allow.three.column";

	public static final String LAYOUT_FRIENDLY_URL_PRIVATE_SERVLET_MAPPING = "layout.friendly.url.private.servlet.mapping";

	public static final String LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING = "layout.friendly.url.public.servlet.mapping";

	public static final String LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND = "layout.friendly.url.page.not.found";

	public static final String LAYOUT_FRIENDLY_URL_KEYWORDS = "layout.friendly.url.keywords";

	public static final String LAYOUT_ADD_PORTLETS = "layout.add.portlets";

	public static final String LAYOUT_NAME_MAX_LENGTH = "layout.name.max.length";

	public static final String LAYOUT_TABS_PER_ROW = "layout.tabs.per.row";

	public static final String LAYOUT_REMEMBER_SESSION_WINDOW_STATE_MAXIMIZED = "layout.remember.session.window.state.maximized";

	public static final String LAYOUT_REMEMBER_REQUEST_WINDOW_STATE_MAXIMIZED = "layout.remember.request.window.state.maximized";

	public static final String LAYOUT_GUEST_SHOW_MAX_ICON = "layout.guest.show.max.icon";

	public static final String LAYOUT_GUEST_SHOW_MIN_ICON = "layout.guest.show.min.icon";

	public static final String LAYOUT_SHOW_PORTLET_ACCESS_DENIED = "layout.show.portlet.access.denied";

	public static final String LAYOUT_SHOW_PORTLET_INACTIVE = "layout.show.portlet.inactive";

	public static final String LAYOUT_DEFAULT_TEMPLATE_ID = "layout.default.template.id";

	// Preferences

	public static final String PREFERENCE_VALIDATE_ON_STARTUP = "preference.validate.on.startup";

	// Struts

	public static final String STRUTS_PORTLET_REQUEST_PROCESSOR = "struts.portlet.request.processor";

	// Images

	public static final String IMAGE_DEFAULT_SPACER = "image.default.spacer";

	public static final String IMAGE_DEFAULT_USER_PORTRAIT = "image.default.user.portrait";

	// Fields

	public static final String FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE = "field.enable.com.liferay.portal.model.Contact.male";

	public static final String FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY = "field.enable.com.liferay.portal.model.Contact.birthday";

	// Amazon License Keys

	public static final String AMAZON_LICENSE = "amazon.license.";

	// Google License Keys

	public static final String GOOGLE_LICENSE = "google.license.";

	// Instant Messenger

	public static final String AIM_LOGIN = "aim.login";

	public static final String AIM_PASSWORD = "aim.password";

	public static final String ICQ_JAR = "icq.jar";

	public static final String ICQ_LOGIN = "icq.login";

	public static final String ICQ_PASSWORD = "icq.password";

	public static final String MSN_LOGIN = "msn.login";

	public static final String MSN_PASSWORD = "msn.password";

	public static final String YM_LOGIN = "ym.login";

	public static final String YM_PASSWORD = "ym.password";

	// Lucene Search

	public static final String INDEX_ON_STARTUP = "index.on.startup";

	public static final String INDEX_WITH_THREAD = "index.with.thread";

	public static final String LUCENE_STORE_TYPE= "lucene.store.type";
	
	public static final String LUCENE_STORE_JDBC_DIALECT = "lucene.store.jdbc.dialect.";

	public static final String LUCENE_DIR = "lucene.dir";

	public static final String LUCENE_FILE_EXTRACTOR = "lucene.file.extractor";

	public static final String LUCENE_ANALYZER = "lucene.analyzer";

	// Value Object

	public static final String VALUE_OBJECT_CACHEABLE = "value.object.cacheable";

	// Last Modified

	public static final String LAST_MODIFIED_CHECK = "last.modified.check";

	public static final String LAST_MODIFIED_PATH = "last.modified.path.";

	// XSS (Cross Site Scripting)

	public static final String XSS_ALLOW = "xss.allow";

	// JCR

	public static final String JCR_INITIALIZE_ON_STARTUP = "jcr.initialize.on.startup";

	public static final String JCR_WORKSPACE_NAME = "jcr.workspace.name";

	public static final String JCR_NODE_DOCUMENTLIBRARY = "jcr.node.documentlibrary";

	public static final String JCR_JACKRABBIT_REPOSITORY_ROOT = "jcr.jackrabbit.repository.root";

	public static final String JCR_JACKRABBIT_CONFIG_FILE_PATH = "jcr.jackrabbit.config.file.path";

	public static final String JCR_JACKRABBIT_REPOSITORY_HOME = "jcr.jackrabbit.repository.home";

	public static final String JCR_JACKRABBIT_CREDENTIALS_USERNAME = "jcr.jackrabbit.credentials.username";

	public static final String JCR_JACKRABBIT_CREDENTIALS_PASSWORD = "jcr.jackrabbit.credentials.password";

	// Web Server

	public static final String WEB_SERVER_HTTP_PORT = "web.server.http.port";

	public static final String WEB_SERVER_HTTPS_PORT = "web.server.https.port";

	public static final String WEB_SERVER_HOST = "web.server.host";

	public static final String WEB_SERVER_PROTOCOL = "web.server.protocol";

	// Address Book Portlet

	public static final String ADDRESS_BOOK_CONTACT_JOB_CLASSES = "address.book.contact.job.classes";

	// Admin Portlet

    public static final String ADMIN_DEFAULT_GROUP_NAMES = "admin.default.group.names";

	public static final String ADMIN_DEFAULT_ROLE_NAMES = "admin.default.role.names";

    public static final String ADMIN_MAIL_HOST_NAMES = "admin.mail.host.names";

    public static final String ADMIN_RESERVED_EMAIL_ADDRESSES = "admin.reserved.email.addresses";

	public static final String ADMIN_RESERVED_USER_IDS = "admin.reserved.user.ids";

	public static final String ADMIN_EMAIL_FROM_NAME = "admin.email.from.name";

	public static final String ADMIN_EMAIL_FROM_ADDRESS = "admin.email.from.address";

    public static final String ADMIN_EMAIL_USER_ADDED_ENABLED = "admin.email.user.added.enabled";

    public static final String ADMIN_EMAIL_USER_ADDED_SUBJECT = "admin.email.user.added.subject";

	public static final String ADMIN_EMAIL_USER_ADDED_BODY = "admin.email.user.added.body";

    public static final String ADMIN_EMAIL_PASSWORD_SENT_ENABLED = "admin.email.password.sent.enabled";

    public static final String ADMIN_EMAIL_PASSWORD_SENT_SUBJECT = "admin.email.password.sent.subject";

	public static final String ADMIN_EMAIL_PASSWORD_SENT_BODY = "admin.email.password.sent.body";

	// Calendar Portlet

	public static final String CALENDAR_EVENT_TYPES = "calendar.event.types";

	public static final String CALENDAR_EMAIL_FROM_NAME = "calendar.email.from.name";

	public static final String CALENDAR_EMAIL_FROM_ADDRESS = "calendar.email.from.address";

    public static final String CALENDAR_EMAIL_EVENT_REMINDER_ENABLED = "calendar.email.event.reminder.enabled";

    public static final String CALENDAR_EMAIL_EVENT_REMINDER_SUBJECT = "calendar.email.event.reminder.subject";

	public static final String CALENDAR_EMAIL_EVENT_REMINDER_BODY = "calendar.email.event.reminder.body";

	// Chat Portlet

	public static final String CHAT_AVAILABLE = "chat.available";

	public static final String CHAT_SERVER_DEFAULT_PORT = "chat.server.default.port";

	// Document Library Portlet

	public static final String DL_ROOT_DIR = "dl.root.dir";

	public static final String DL_VERSION_ROOT_DIR = "dl.version.root.dir";

	public static final String DL_FILE_MAX_SIZE = "dl.file.max.size";

	public static final String DL_FILE_EXTENSIONS = "dl.file.extensions";

	public static final String DL_VERSION_CACHE_DIRECTORY_VIEWS = "dl.version.cache.directory.views";

	// Image Gallery Portlet

	public static final String IG_IMAGE_MAX_SIZE = "ig.image.max.size";

	public static final String IG_IMAGE_EXTENSIONS = "ig.image.extensions";

	public static final String IG_IMAGE_THUMBNAIL_MAX_HEIGHT = "ig.image.thumbnail.max.height";

	public static final String IG_IMAGE_THUMBNAIL_MAX_WIDTH = "ig.image.thumbnail.max.width";

	// Journal Portlet

	public static final String JOURNAL_ARTICLE_TYPES = "journal.article.types";

	public static final String JOURNAL_ARTICLE_FORCE_INCREMENT_VERSION = "journal.article.force.increment.version";

	public static final String JOURNAL_IMAGE_SMALL_MAX_SIZE = "journal.image.small.max.size";

	public static final String JOURNAL_IMAGE_EXTENSIONS = "journal.image.extensions";

	public static final String JOURNAL_TRANSFORMER_LISTENER = "journal.transformer.listener";

	public static final String JOURNAL_SYNC_CONTENT_SEARCH_ON_STARTUP = "journal.sync.content.search.on.startup";

	public static final String JOURNAL_EMAIL_FROM_NAME = "journal.email.from.name";

	public static final String JOURNAL_EMAIL_FROM_ADDRESS = "journal.email.from.address";

	public static final String JOURNAL_EMAIL_ARTICLE_APPROVAL_DENIED_ENABLED = "journal.email.article.approval.denied.enabled";

	public static final String JOURNAL_EMAIL_ARTICLE_APPROVAL_DENIED_SUBJECT = "journal.email.article.approval.denied.subject";
	
	public static final String JOURNAL_EMAIL_ARTICLE_APPROVAL_DENIED_BODY = "journal.email.article.approval.denied.body";

	public static final String JOURNAL_EMAIL_ARTICLE_APPROVAL_GRANTED_ENABLED = "journal.email.article.approval.granted.enabled";

	public static final String JOURNAL_EMAIL_ARTICLE_APPROVAL_GRANTED_SUBJECT = "journal.email.article.approval.granted.subject";
	
	public static final String JOURNAL_EMAIL_ARTICLE_APPROVAL_GRANTED_BODY = "journal.email.article.approval.granted.body";

	public static final String JOURNAL_EMAIL_ARTICLE_APPROVAL_REQUESTED_ENABLED = "journal.email.article.approval.requested.enabled";

	public static final String JOURNAL_EMAIL_ARTICLE_APPROVAL_REQUESTED_SUBJECT = "journal.email.article.approval.requested.subject";
	
	public static final String JOURNAL_EMAIL_ARTICLE_APPROVAL_REQUESTED_BODY = "journal.email.article.approval.requested.body";

	public static final String JOURNAL_EMAIL_ARTICLE_REVIEW_ENABLED = "journal.email.article.review.enabled";

	public static final String JOURNAL_EMAIL_ARTICLE_REVIEW_SUBJECT = "journal.email.article.review.subject";
	
	public static final String JOURNAL_EMAIL_ARTICLE_REVIEW_BODY = "journal.email.article.review.body";

	// Journal Articles Portlet

	public static final String JOURNAL_ARTICLES_PAGE_DELTA_VALUES = "journal.articles.page.delta.values";

	// Mail Portlet

	public static final String MAIL_MX_UPDATE = "mail.mx.update";

	public static final String MAIL_HOOK_IMPL = "mail.hook.impl";

	public static final String MAIL_HOOK_CYRUS_ADD_USER = "mail.hook.cyrus.add.user";

	public static final String MAIL_HOOK_CYRUS_DELETE_USER = "mail.hook.cyrus.delete.user";

	public static final String MAIL_HOOK_CYRUS_HOME = "mail.hook.cyrus.home";

	public static final String MAIL_HOOK_SENDMAIL_ADD_USER = "mail.hook.sendmail.add.user";

	public static final String MAIL_HOOK_SENDMAIL_CHANGE_PASSWORD = "mail.hook.sendmail.change.password";

	public static final String MAIL_HOOK_SENDMAIL_DELETE_USER = "mail.hook.sendmail.delete.user";

	public static final String MAIL_HOOK_SENDMAIL_HOME = "mail.hook.sendmail.home";

	public static final String MAIL_HOOK_SENDMAIL_VIRTUSERTABLE = "mail.hook.sendmail.virtusertable";

	public static final String MAIL_HOOK_SENDMAIL_VIRTUSERTABLE_REFRESH = "mail.hook.sendmail.virtusertable.refresh";

	public static final String MAIL_HOOK_SHELL_SCRIPT = "mail.hook.shell.script";

	public static final String MAIL_BOX_STYLE = "mail.box.style";

	public static final String MAIL_INBOX_NAME = "mail.inbox.name";

	public static final String MAIL_JUNK_NAME = "mail.junk.name";

	public static final String MAIL_SENT_NAME = "mail.sent.name";

	public static final String MAIL_DRAFTS_NAME = "mail.drafts.name";

	public static final String MAIL_TRASH_NAME = "mail.trash.name";

	public static final String MAIL_JUNK_MAIL_WARNING_SIZE = "mail.junk-mail.warning.size";

	public static final String MAIL_TRASH_WARNING_SIZE = "mail.trash.warning.size";

	public static final String MAIL_USERNAME_REPLACE = "mail.username.replace";

	public static final String MAIL_SMTP_DEBUG = "mail.smtp.debug";

	public static final String MAIL_AUDIT_TRAIL = "mail.audit.trail";

	public static final String MAIL_ATTACHMENTS_MAX_SIZE = "mail.attachments.max.size";

	public static final String MAIL_ACCOUNT_FINDER = "mail.account.finder";

	// Message Boards Portlet

	public static final String MESSAGE_BOARDS_EMAIL_FROM_NAME = "message.boards.email.from.name";

	public static final String MESSAGE_BOARDS_EMAIL_FROM_ADDRESS = "message.boards.email.from.address";

    public static final String MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_ENABLED = "message.boards.email.message.added.enabled";

    public static final String MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SUBJECT = "message.boards.email.message.added.subject";

	public static final String MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_BODY = "message.boards.email.message.added.body";

    public static final String MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_ENABLED = "message.boards.email.message.updated.enabled";

    public static final String MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SUBJECT = "message.boards.email.message.updated.subject";

	public static final String MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_BODY = "message.boards.email.message.updated.body";

	// Shopping Portlet

	public static final String SHOPPING_CART_MIN_QTY_MULTIPLE = "shopping.cart.min.qty.multiple";

	public static final String SHOPPING_CATEGORY_FORWARD_TO_CART = "shopping.category.forward.to.cart";

	public static final String SHOPPING_CATEGORY_SHOW_SPECIAL_ITEMS = "shopping.category.show.special.items";

	public static final String SHOPPING_ITEM_SHOW_AVAILABILITY = "shopping.item.show.availability";

	public static final String SHOPPING_IMAGE_SMALL_MAX_SIZE = "shopping.image.small.max.size";

	public static final String SHOPPING_IMAGE_MEDIUM_MAX_SIZE = "shopping.image.medium.max.size";

	public static final String SHOPPING_IMAGE_LARGE_MAX_SIZE = "shopping.image.large.max.size";

	public static final String SHOPPING_IMAGE_EXTENSIONS = "shopping.image.extensions";

	public static final String SHOPPING_EMAIL_FROM_NAME = "shopping.email.from.name";

	public static final String SHOPPING_EMAIL_FROM_ADDRESS = "shopping.email.from.address";

	public static final String SHOPPING_EMAIL_ORDER_CONFIRMATION_ENABLED = "shopping.email.order.confirmation.enabled";

	public static final String SHOPPING_EMAIL_ORDER_CONFIRMATION_SUBJECT = "shopping.email.order.confirmation.subject";
	
	public static final String SHOPPING_EMAIL_ORDER_CONFIRMATION_BODY = "shopping.email.order.confirmation.body";

	public static final String SHOPPING_EMAIL_ORDER_SHIPPING_ENABLED = "shopping.email.order.shipping.enabled";

	public static final String SHOPPING_EMAIL_ORDER_SHIPPING_SUBJECT = "shopping.email.order.shipping.subject";
	
	public static final String SHOPPING_EMAIL_ORDER_SHIPPING_BODY = "shopping.email.order.shipping.body";

	// Translator Portlet

	public static final String TRANSLATOR_DEFAULT_LANGUAGES = "translator.default.languages";

	// Wiki Portlet

	public static final String WIKI_FRONT_PAGE_NAME = "wiki.front.page.name";

	public static final String WIKI_PERMISSION_CHECKER = "wiki.permission.checker";

	public static boolean containsKey(String key) {
		return _getInstance().containsKey(key);
	}

	public static String get(String key) {
		return _getInstance().get(key);
	}

	public static void set(String key, String value) {
		_getInstance().set(key, value);
	}

	public static String[] getArray(String key) {
		return _getInstance().getArray(key);
	}

	public static Properties getProperties() {
		return _getInstance().getProperties();
	}

	public static ComponentProperties getComponentProperties() {
		return _getInstance().getComponentProperties();
	}

	private static ExtPropertiesLoader _getInstance() {
		return ExtPropertiesLoader.getInstance(PropsFiles.PORTAL);
	}

}