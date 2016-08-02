#! /bin/sh

set -e

TOMCAT_HOME=../bundles/tomcat-8.0.32
LOG_FILE=${TOMCAT_HOME}/logs/catalina.out

SIGNALING_TEXT='Server startup in'

while ! grep -q "${SIGNALING_TEXT}" < ${LOG_FILE}; do
    sleep 5
done