@echo off

set J2H_PARAMETER=

:getParameter
	if "%1" == "" goto end

	set J2H_PARAMETER=%J2H_PARAMETER% %1

	shift

	goto getParameter

:end

set CLASSPATH=../lib/j2h.jar;../../lib/j2h.jar

java j2h %J2H_PARAMETER%