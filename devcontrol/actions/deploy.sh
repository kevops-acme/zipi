#!/bin/bash

# @description Deploy the application
#
# @example
#   destroy
#
# @arg $1 Task: "brief", "help" or "exec"
# @arg $2 optional Target host: "ubuntu@zipi.app.kevops.academy"
#
# @exitcode The exit code of the statements of the action
# @exitcode 1  If the task is not implemented
#
# @stdout "Not implemented" message if the requested task is not implemented
#
function deploy() {

    # Init
    local briefMessage="Deploy to production"
    local helpMessage="Deploy the application to the EC2 instance target"

    # Task choosing
    case "$1" in
        brief)
            showBriefMessage "${FUNCNAME[0]}" "$briefMessage"
            ;;
        help)
            showHelpMessage "${FUNCNAME[0]}" "$helpMessage"
            ;;
        exec)
            TAG=$(getVersion)
            target=${2:-ubuntu@zipi.app.kevops.academy}
            ssh_opts="-o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null"
            ssh -T $ssh_opts "$target" "mkdir app"
            rsync -avpP -e "ssh $ssh_opts" docker-compose.prod.yaml "$target:~/app/docker-compose.yaml"
            rsync -avpP -e "ssh $ssh_opts" .env.dist "$target:~/app/.env"
            docker save "kevopsacademy/zipi-app:$TAG" | ssh -T $ssh_opts "$target" "docker load"
            ssh -T $ssh_opts "$target" "cd app && docker-compose up -d"
            ;;
        *)
            showNotImplemtedMessage "$1" "${FUNCNAME[0]}"
            return 1
    esac
}

# Main
deploy "$@"