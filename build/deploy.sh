#!/bin/sh

printf '================================================================================================================\n'

printf 'Running activator and generating the war...\n\n'
activator clean war

printf 'Getting target file name...\n\n'

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

FILE_NAME=${NAME}'-'${VERSION}'.war'

printf 'Copying '${FILE_NAME}' to ROOT.war to transfer over...\n\n'
cp target/test-deploy-1.0-SNAPSHOT.war target/ROOT.war


printf 'change mode 777 to ROOT.war...\n\n'
chmod 777 target/ROOT.war

printf 'SCPing ROOT.war to ec2-user@54.68.168.234:/home/ec2-user...\n\n'
scp -i build/wistapp.pem target/ROOT.war ec2-user@54.68.168.234:/home/ec2-user

printf 'Executing build/deploy_remote to copy over deployed artifact into tomcat /webapps...\n\n'
ssh -i build/wistapp.pem ec2-user@54.68.168.234 'bash -s' < build/deploy_remote.sh

printf '================================================================================================================\n'