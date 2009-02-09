<#include "common.ftl">
<#list counters as counter>
UPDATE Counter SET currentId=${counter.currentValue} WHERE name='${counter.name}
</#list>
