package ${packagePath}.model;

<#if entity.hasCompoundPK()>
	import ${packagePath}.service.persistence.${entity.name}PK;
</#if>

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.Date;

public class ${entity.name}Clp extends BaseModelImpl<${entity.name}> implements ${entity.name} {

	public ${entity.name}Clp() {
	}

	public ${entity.PKClassName} getPrimaryKey() {
		<#if entity.hasCompoundPK()>
			return new ${entity.PKClassName}(

			<#list entity.PKList as column>
				_${column.name}

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			return _${entity.PKList[0].name};
		</#if>
	}

	public void setPrimaryKey(${entity.PKClassName} pk) {
		<#if entity.hasCompoundPK()>
			<#list entity.PKList as column>
				set${column.methodName}(pk.${column.name});
			</#list>
		<#else>
			set${entity.PKList[0].methodName}(pk);
		</#if>
	}

	public Serializable getPrimaryKeyObj() {
		<#if entity.hasCompoundPK()>
			return new ${entity.PKClassName}(

			<#list entity.PKList as column>
				_${column.name}

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			return

			<#if entity.hasPrimitivePK()>
				new ${serviceBuilder.getPrimitiveObj("${entity.PKClassName}")} (
			</#if>

			_${entity.PKList[0].name}

			<#if entity.hasPrimitivePK()>
				)
			</#if>

			;
		</#if>
	}

	<#list entity.regularColList as column>
		<#if column.name == "classNameId">
			public String getClassName() {
				if (getClassNameId() <= 0) {
					return StringPool.BLANK;
				}

				return PortalUtil.getClassName(getClassNameId());
			}
		</#if>

		public ${column.type} get${column.methodName}() {
			return _${column.name};
		}

		<#if column.type== "boolean">
			public ${column.type} is${column.methodName}() {
				return _${column.name};
			}
		</#if>

		public void set${column.methodName}(${column.type} ${column.name}) {
			_${column.name} = ${column.name};
		}
	</#list>

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic()>
			public ${method.returns.value}${method.returnsGenericsName}${serviceBuilder.getDimensions("${method.returns.dimensions}")} ${method.name} (

			<#assign parameters = method.parameters>

			<#list parameters as parameter>
				${parameter.type.value}${parameter.genericsName}${serviceBuilder.getDimensions("${parameter.type.dimensions}")} ${parameter.name}<#if parameter_has_next>,</#if>
			</#list>

			)

			<#--<#list method.exceptions as exception>
				<#if exception_index == 0>
					throws
				</#if>

				${exception.value}

				<#if exception_has_next>
					,
				</#if>
			</#list>-->

			{
				throw new UnsupportedOperationException();
			}
		</#if>
	</#list>

	public ${entity.name} toEscapedModel() {
		if (isEscapedModel()) {
			return this;
		}
		else {
			${entity.name} model = new ${entity.name}Clp();

			model.setEscapedModel(true);

			<#list entity.regularColList as column>
				model.set${column.methodName}(

				<#if column.EJBName??>
					(${column.EJBName})get${column.methodName}().clone()
				<#else>
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

					<#if autoEscape && (column.type == "String")>
						HtmlUtil.escape(
					</#if>

					get${column.methodName}()

					<#if autoEscape && (column.type == "String")>
						)
					</#if>
				</#if>

				);
			</#list>

			model = (${entity.name})Proxy.newProxyInstance(${entity.name}.class.getClassLoader(), new Class[] {${entity.name}.class}, new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		 ${entity.name}Clp clone = new ${entity.name}Clp();

		<#list entity.regularColList as column>
			clone.set${column.methodName}(

			<#if column.EJBName??>
				(${column.EJBName})get${column.methodName}().clone()
			<#else>
				get${column.methodName}()
			</#if>

			);
		</#list>

		return clone;
	}

	public int compareTo(${entity.name} ${entity.varName}) {
		<#if entity.isOrdered()>
			int value = 0;

			<#list entity.order.columns as column>
				<#if column.isPrimitiveType()>
					<#if column.type == "boolean">
						<#assign ltComparator = "==">
						<#assign gtComparator = "!=">
					<#else>
						<#assign ltComparator = "<">
						<#assign gtComparator = ">">
					</#if>

					if (get${column.methodName}() ${ltComparator} ${entity.varName}.get${column.methodName}()) {
						value = -1;
					}
					else if (get${column.methodName}() ${gtComparator} ${entity.varName}.get${column.methodName}()) {
						value = 1;
					}
					else {
						value = 0;
					}
				<#else>
					<#if column.type == "Date">
						value = DateUtil.compareTo(get${column.methodName}(), ${entity.varName}.get${column.methodName}());
					<#else>
						<#if column.isCaseSensitive()>
							value = get${column.methodName}().compareTo(${entity.varName}.get${column.methodName}());
						<#else>
							value = get${column.methodName}().toLowerCase().compareTo(${entity.varName}.get${column.methodName}().toLowerCase());
						</#if>
					</#if>
				</#if>

				<#if !column.isOrderByAscending()>
					value = value * -1;
				</#if>

				if (value != 0) {
					return value;
				}
			</#list>

			return 0;
		<#else>
			${entity.PKClassName} pk = ${entity.varName}.getPrimaryKey();

			<#if entity.hasPrimitivePK()>
				if (getPrimaryKey() < pk) {
					return -1;
				}
				else if (getPrimaryKey() > pk) {
					return 1;
				}
				else {
					return 0;
				}
			<#else>
				return getPrimaryKey().compareTo(pk);
			</#if>
		</#if>
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		${entity.name}Clp ${entity.varName} = null;

		try {
			${entity.varName} = (${entity.name}Clp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		${entity.PKClassName} pk = ${entity.varName}.getPrimaryKey();

		<#if entity.hasPrimitivePK()>
			if (getPrimaryKey() == pk) {
		<#else>
			if (getPrimaryKey().equals(pk)) {
		</#if>

			return true;
		}
		else{
			return false;
		}
	}

	public int hashCode() {
		<#if entity.hasPrimitivePK()>
			<#if entity.PKClassName == "int">
				return getPrimaryKey();
			<#else>
				return (int)getPrimaryKey();
			</#if>
		<#else>
			return getPrimaryKey().hashCode();
		</#if>
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		<#list entity.regularColList as column>
			<#if column_index == 0>
				sb.append("{${column.name}=");
				sb.append(get${column.methodName}());
			<#elseif column_has_next>
				sb.append(", ${column.name}=");
				sb.append(get${column.methodName}());
			<#else>
				sb.append(", ${column.name}=");
				sb.append(get${column.methodName}());
				sb.append("}");
			</#if>
		</#list>

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append("${packagePath}.model.${entity.name}");
		sb.append("</model-name>");

		<#list entity.regularColList as column>
			sb.append("<column><column-name>${column.name}</column-name><column-value><![CDATA[");
			sb.append(get${column.methodName}());
			sb.append("]]></column-value></column>");
		</#list>

		sb.append("</model>");

		return sb.toString();
	}

	<#list entity.regularColList as column>
		private ${column.type} _${column.name};
	</#list>

}