#!/bin/bash

set -e

# @description Build zipi jar package
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
function build-jar() {

    # Init
    local briefMessage="Build zipi app jar package"
    local helpMessage="Build the zipi app jar package"

    # Task choosing
    case "$1" in
        brief)
            showBriefMessage "${FUNCNAME[0]}" "$briefMessage"
            ;;
        help)
            showHelpMessage "${FUNCNAME[0]}" "$helpMessage"
            ;;
        exec)
            mvn clean package -DskipTests
            ;;
        *)
            showNotImplemtedMessage "$1" "${FUNCNAME[0]}"
            return 1
    esac
}

# Main
build-jar "$@"