package ${packagePath}.model;

<#if entity.hasCompoundPK()>
	import ${packagePath}.service.persistence.${entity.name}PK;
</#if>

import com.liferay.portal.SystemException;
import com.liferay.portal.model.BaseModel;

import java.util.Date;

<#if entity.hasLocalizedColumn()>
	import java.util.Locale;
	import java.util.Map;
</#if>

/**
 * <a href="${entity.name}Model.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ${entity.table} table in the
 * database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    ${entity.name}
 * @see    ${packagePath}.model.impl.${entity.name}Impl
 * @see    ${packagePath}.model.impl.${entity.name}ModelImpl
 */
public interface ${entity.name}Model extends BaseModel<${entity.name}> {

	public ${entity.PKClassName} getPrimaryKey();

	public void setPrimaryKey(${entity.PKClassName} pk);

	<#list entity.regularColList as column>
		<#if column.name == "classNameId">
			public String getClassName();
		</#if>

		public ${column.type} get${column.methodName}();

        <#if column.localized>
			public ${column.type} get${column.methodName}(Locale locale);

			public ${column.type} get${column.methodName}(Locale locale, boolean useDefault);

			public ${column.type} get${column.methodName}(String languageId);

			public String get${column.methodName}(String languageId, boolean useDefault);

			public Map<Locale, String> get${column.methodName}Map();
		</#if>

		<#if column.type == "boolean">
			public boolean is${column.methodName}();
		</#if>

		public void set${column.methodName}(${column.type} ${column.name});

        <#if column.localized>
			public void set${column.methodName}(Locale locale, String ${column.name});

            public void set${column.methodName}Map(Map<Locale, String> ${column.name}Map);
		</#if>

		<#if column.userUuid>
			public String get${column.methodUserUuidName}() throws SystemException;

			public void set${column.methodUserUuidName}(String ${column.userUuidName});
		</#if>
	</#list>

	public ${entity.name} toEscapedModel();

}