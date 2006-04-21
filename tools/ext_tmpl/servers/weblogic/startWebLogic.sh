#!/bin/sh

# WARNING: This file is created by the Configuration Wizard.
# Any changes to this script may be lost when adding extensions to this configuration.

# *************************************************************************
# This script is used to start WebLogic Server for the domain in the
# current working directory.  This script simply sets the SERVER_NAME
# variable and starts server.
# 
# To create your own start script for your domain, all you need to set is
# SERVER_NAME, then starts the server.
# 
# Other variables that startWLS takes are:
# 
# WLS_USER     - cleartext user for server startup
# WLS_PW       - cleartext password for server startup
# PRODUCTION_MODE    - true for production mode servers, false for
#                development mode
# JAVA_OPTIONS - Java command-line options for running the server. (These
#                will be tagged on to the end of the JAVA_VM and MEM_ARGS)
# JAVA_VM      - The java arg specifying the VM to run.  (i.e. -server,
#                -hotspot, etc.)
# MEM_ARGS     - The variable to override the standard memory arguments
#                passed to java
# 
# For additional information, refer to the WebLogic Server Administration
# Console Online Help(http://e-docs.bea.com/wls/docs81/ConsoleHelp/startstop.html)
# *************************************************************************

# Initialize the common environment.

WL_HOME="C:/Program Files/BEA/weblogic81"

PRODUCTION_MODE=""

JAVA_VENDOR="BEA"

JAVA_HOME="C:/Program Files/BEA/jrockit81sp4_142_05"

# Call commEnv here AFTER setting the java_vendor to get common environmental settings.

. ${WL_HOME}/common/bin/commEnv.sh

# Set SERVER_NAME to the name of the server you wish to start up.

SERVER_NAME="myserver"

CLASSPATH="${WEBLOGIC_CLASSPATH}:${POINTBASE_CLASSPATH}:${JAVA_HOME}/jre/lib/rt.jar:${WL_HOME}/server/lib/webservices.jar:${CLASSPATH}"
export CLASSPATH

# Call WebLogic Server

echo "."
echo "CLASSPATH=${CLASSPATH}"
echo "."
echo "PATH=${PATH}"
echo "."
echo "***************************************************"
echo "*  To start WebLogic Server, use a username and   *"
echo "*  password assigned to an admin-level user.  For *"
echo "*  server administration, use the WebLogic Server *"
echo "*  console at http://[hostname]:[port]/console    *"
echo "***************************************************"

${JAVA_HOME}/bin/java ${JAVA_VM} ${MEM_ARGS} ${JAVA_OPTIONS} -Dweblogic.Name=${SERVER_NAME} -Dweblogic.ProductionModeEnabled=${PRODUCTION_MODE} -Djava.security.policy="${WL_HOME}/server/lib/weblogic.policy" weblogic.Server

