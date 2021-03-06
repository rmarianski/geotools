#!/bin/bash

# main script invoked by hudson

# sanity check parameters
[ -z $BRANCH ] && echo "BRANCH variable mandatory" && exit 1
[ -z $REV ] && echo "REV variable mandatory" && exit 1
[ -z $VERSION ] && echo "VERSION variable mandatory" && exit 1
[ -z $SVN_TAG ] && echo "SVN_TAG variable mandatory" && exit 1

if [ "$SVN_TAG" == "false" ]; then
  echo "skipping svn tag"
  export SKIP_SVN_TAG="true"
  export BUILD_FROM_BRANCH="true"
fi

OPTS="-b $BRANCH -r $REV"
if [ ! -z $SVN_USER ]; then
  OPTS="$OPTS -u $SVN_USER"
fi
if [ ! -z $SVN_PASSWD ]; then
  OPTS="$OPTS -p $SVN_PASSWD"
fi

if [ ! -z $JAVA_HOME ]; then
  export PATH=$JAVA_HOME/bin:$PATH
fi
./build_release.sh $OPTS $VERSION
