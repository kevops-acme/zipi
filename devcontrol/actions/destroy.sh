#!/bin/bash

# @description Destroy the platform
#
# @example
#   destroy
#
# @arg $1 Task: "brief", "help" or "exec"
#
# @exitcode The exit code of the statements of the action
# @exitcode 1  If the task is not implemented
#
# @stdout "Not implemented" message if the requested task is not implemented
#
function destroy() {

    # Init
    local briefMessage="Production environment destroy"
    local helpMessage="Destroy the docker-compose prod platform. Preserve the database data files"

    # Task choosing
    case $1 in
        brief)
            showBriefMessage ${FUNCNAME[0]} "$briefMessage"
            ;;
        help)
            showHelpMessage ${FUNCNAME[0]} "$helpMessage"
            ;;
        exec)
            checkDocker
            docker-compose -f docker-compose.prod.yaml down
            ;;
        *)
            showNotImplemtedMessage $1 ${FUNCNAME[0]}
            return 1
    esac
}

# Main
destroy "$@"