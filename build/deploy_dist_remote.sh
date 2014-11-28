#!/bin/sh

RUNNING_ID_LOCATION=$(find `pwd` -name RUNNING_PID)
if [ -n "$RUNNING_ID_LOCATION" ]; then
        kill `cat ${RUNNING_ID_LOCATION}`
fi

ZIP_FILE_NAME=${1}'-'${2}'.zip'
printf "ZIP_FILE_NAME = $ZIP_FILE_NAME\n\n"

unzip -oq ${ZIP_FILE_NAME}
chmod -R 777 ${1}'-'${2}
nohup ${1}'-'${2}/bin/test-deploy -Dhttp.port=80 > application.out 2>&1&