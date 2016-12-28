#! /bin/sh

set -e

TEMP_FILE=$(mktemp)

cd adaptive-media-image-test

../scripts/run.sh ../gradlew clean testIntegration 2>&1 | tee ${TEMP_FILE}

cd ../adaptive-media-image-impl-test

../scripts/run.sh ../gradlew clean testIntegration 2>&1 | tee -a ${TEMP_FILE}

cd ../adaptive-media-image-jax-rs-test

../scripts/run.sh ../gradlew clean testIntegration 2>&1 | tee -a ${TEMP_FILE}

grep 'There were failing tests' ${TEMP_FILE} && exit 1 || exit 0