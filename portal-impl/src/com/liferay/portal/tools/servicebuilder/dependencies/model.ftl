package ${packagePath}.model;

<#if entity.hasCompoundPK()>
	import ${packagePath}.service.persistence.${entity.name}PK;
</#if>

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
 * This interface is a model that represents the <code>${entity.name}</code>
 * table in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see ${packagePath}.model.${entity.name}
 * @see ${packagePath}.model.impl.${entity.name}Impl
 * @see ${packagePath}.model.impl.${entity.name}ModelImpl
 *
 */
public interface ${entity.name}Model extends BaseModel<${entity.name}> {

	public ${entity.PKClassName} getPrimaryKey();

	public void setPrimaryKey(${entity.PKClassName} pk);

	<#list entity.regularColList as column>
		<#if column.name == "classNameId">
			public String getClassName();
		</#if>

		public ${column.type} get${column.methodName}();

        <#if column.localized == true>
			public ${column.type} get${column.methodName}(Locale locale);

			public ${column.type} get${column.methodName}(Locale locale, boolean useDefault);

			public ${column.type} get${column.methodName}(String localeLanguageId);

			public String get${column.methodName}(String localeLanguageId, boolean useDefault);

			public Map<Locale, String> get${column.methodName}sMap() ;
		</#if>

		<#if column.type == "boolean">
			public boolean is${column.methodName}();
		</#if>

		public void set${column.methodName}(${column.type} ${column.name});

        <#if column.localized == true>
			public void set${column.methodName}(String localizedValue, Locale locale);

            public void set${column.methodName}(Map localizedValues);
		</#if>
	</#list>

	public ${entity.name} toEscapedModel();

}