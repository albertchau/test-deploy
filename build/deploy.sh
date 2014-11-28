#!/bin/sh

printf '================================================================================================================\n'

printf 'Running activator and generating the distribution...\n\n'
activator clean dist

printf 'Getting target file name and version...\n\n'

NAME=$(sed -n 's/name := \"\{3\}\(.*\)\"\{3\}/\1/p' build.sbt)

if [ -z "$NAME" ]
then
  printf "\$NAME is null.\n"
else
  printf "Name = $NAME\n"
fi

VERSION=$(sed -n 's/version := \"\(.*\)\"/\1/p' build.sbt)

if [ -z "$VERSION" ]
then
  printf "\$VERSION is null.\n\n"
else
  printf "Version = $VERSION\n\n"
fi

ZIP_FILE_NAME=${NAME}'-'${VERSION}'.zip'

chmod 700 build/wistapp.pem
printf 'SCPing target/universal/'${NAME}'-'${VERSION}' to ec2-user@54.68.168.234:/home/ec2-user...\n\n'
scp -i build/wistapp.pem target/universal/${ZIP_FILE_NAME} ec2-user@54.148.242.165:/home/ec2-user

printf 'Executing start script inside the distribution '${NAME}'-'${VERSION}'/bin/'${NAME}' folder...\n\n'
ssh -i build/wistapp.pem ec2-user@54.148.242.165 'sudo bash -s' -- < build/deploy_dist_remote.sh "${NAME}" "${VERSION}"

printf '================================================================================================================\n'