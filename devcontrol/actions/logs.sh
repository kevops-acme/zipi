#!/bin/bash

# @description Show logs
#
# @example
#   logs
#
# @arg $1 Task: "brief", "help" or "exec"
#
# @exitcode The exit status of the statements of the action
#
# @stdout "Not implemented" message if the requested task is not implemented
#
function logs() {

    # Init
    local briefMessage="Show logs"
    local helpMessage="Show logs of the containers of the platform using 'docker-compose logs'"

    # Task choosing
    case "$1" in
        brief)
            showBriefMessage "${FUNCNAME[0]}" "$briefMessage"
            ;;
        help)
            showHelpMessage "${FUNCNAME[0]}" "$helpMessage"
            ;;
        exec)
            docker-compose -f docker-compose.prod.yaml logs --tail=100 -f
            ;;
        *)
            showNotImplemtedMessage "${FUNCNAME[0]}" "$1"
            return 1
    esac
}

# Main
logs "$@"