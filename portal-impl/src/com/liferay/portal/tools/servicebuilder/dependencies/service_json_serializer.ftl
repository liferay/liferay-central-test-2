package ${packagePath}.service.http;

import ${packagePath}.model.${entity.name};

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.JSONUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="${entity.name}JSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>${packagePath}.service.http.${entity.name}ServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see ${packagePath}.service.http.${entity.name}ServiceJSON
 *
 */
public class ${entity.name}JSONSerializer {

	public static JSONObject toJSONObject(${entity.name} model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		<#list entity.regularColList as column>
			<#if column.type == "Date">
				Date ${column.name} = model.get${column.methodName}();

				String ${column.name}JSON = StringPool.BLANK;

				if (${column.name} != null) {
					${column.name}JSON = String.valueOf(${column.name}.getTime());
				}

				jsonObj.put("${column.name}", ${column.name}JSON);
			<#else>
				jsonObj.put("${column.name}", model.get${column.methodName}());
			</#if>
		</#list>

		return jsonObj;
	}

	public static JSONArray toJSONArray(${packagePath}.model.${entity.name}[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (${entity.name} model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(${packagePath}.model.${entity.name}[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (${entity.name}[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(List<${packagePath}.model.${entity.name}> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (${entity.name} model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

}