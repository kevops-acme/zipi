#!/bin/bash

set -e

# @description Test zipi app
#
# @example
#   build
#
# @arg $1 Task: "brief", "help" or "exec"
#
# @exitcode The exit code of the statements of the action
# @exitcode 1  If the task is not implemented
#
# @stdout "Not implemented" message if the requested task is not implemented
#
function test() {

    # Init
    local briefMessage="Test zipi app"
    local helpMessage="Test zipi app"

    # Task choosing
    case $1 in
        brief)
            showBriefMessage ${FUNCNAME[0]} "$briefMessage"
            ;;
        help)
            showHelpMessage ${FUNCNAME[0]} "$helpMessage"
            ;;
        exec)
            DCT="docker-compose -f docker-compose.test.yaml"
            $DCT down -v
            $DCT up -d
            until $DCT exec db pg_isready; do sleep 1; done
            export SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/zipi"
            mvn clean test
            $DCT down -v
            ;;
        *)
            showNotImplemtedMessage $1 ${FUNCNAME[0]}
            return 1
    esac
}

# Main
test "$@"