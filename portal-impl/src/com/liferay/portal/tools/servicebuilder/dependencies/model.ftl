package ${packagePath}.model;

<#if entity.hasCompoundPK()>
	import ${packagePath}.service.persistence.${entity.name}PK;
</#if>

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

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
 * @author    ${author}
 * @see       ${entity.name}
 * @see       ${packagePath}.model.impl.${entity.name}Impl
 * @see       ${packagePath}.model.impl.${entity.name}ModelImpl
 * @generated
 */
public interface ${entity.name}Model extends BaseModel<${entity.name}> {

	public ${entity.PKClassName} getPrimaryKey();

	public void setPrimaryKey(${entity.PKClassName} pk);

	<#list entity.regularColList as column>
		<#if column.name == "classNameId">
			public String getClassName();
		</#if>

		<#assign autoEscape = true>

		<#assign modelName = packagePath + ".model." + entity.name>

		<#if modelHintsUtil.getHints(modelName, column.name)??>
			<#assign hints = modelHintsUtil.getHints(modelName, column.name)>

			<#if hints.get("auto-escape")??>
				<#assign autoEscapeHintValue = hints.get("auto-escape")>

				<#if autoEscapeHintValue == "false">
					<#assign autoEscape = false>
				</#if>
			</#if>
		</#if>

		<#if autoEscape && (column.type == "String") && (column.localized == false) >
			@AutoEscape
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

	<#--
	Copy methods from com.liferay.portal.model.BaseModel and java.lang.Object to
	correctly generate wrappers.
	-->

	public boolean isNew();

	public boolean setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(${entity.name} ${entity.varName});

	public int hashCode();

	public String toString();

	public String toXmlString();

}