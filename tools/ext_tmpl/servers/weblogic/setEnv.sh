#!/bin/sh
# *************************************************************************
# This script is used to set up your environment for development with 
# WebLogic Server. It simply calls the commEnv.sh script under 
# C:/Program Files/BEA/weblogic81/common/bin.  Add domain specific configuration in this script below.
# *************************************************************************

# set up WL_HOME, the root directory of your WebLogic installation
WL_HOME="C:/Program Files/BEA/weblogic81"

# set up common environment
# Set Production Mode.  When this is set to true, the server starts up in
# production mode.  When set to false, the server starts up in development
# mode.  If it is not set, it will default to false.
PRODUCTION_MODE=""

# Set JAVA_VENDOR to java virtual machine you want to run on server side.
JAVA_VENDOR="BEA"

# Set JAVA_HOME to java virtual machine you want to run on server side.
JAVA_HOME="C:/Program Files/BEA/jrockit81sp2_141_05"

. "${WL_HOME}/common/bin/commEnv.sh"

CLASSPATH="${WEBLOGIC_CLASSPATH}${CLASSPATHSEP}${POINTBASE_CLASSPATH}${CLASSPATHSEP}${JAVA_HOME}/jre/lib/rt.jar${CLASSPATHSEP}${WL_HOME}/server/lib/webservices.jar"
export CLASSPATH
