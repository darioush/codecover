#!/bin/sh

# $Id$

EXEC="$0"
POS="${EXEC%/*}"

exec java -Xmx512M -jar "$POS/lib/codecover-batch.jar" "$@"
