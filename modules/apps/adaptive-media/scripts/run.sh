#! /bin/sh

set -e

TEMP_FILE=$(mktemp)

$* 2>&1 | tee ${TEMP_FILE}

grep 'BUILD FAILED' ${TEMP_FILE} && exit 1 || exit 0