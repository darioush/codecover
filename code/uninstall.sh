#!/bin/sh

# $Id$

set -ex

EXEC="$0"
POS="${EXEC%/*}"
cd "$POS"

umask 022

rm -rf /usr/local/share/java/codecover

rm -f /etc/bash_completion.d/codecover

# Has to be in /usr, not /usr/local :-(
rm -f /usr/share/ant/lib/codecover-*.jar

rm -f /usr/local/bin/codecover
