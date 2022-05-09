#!groovy

@Library('github.com/ayudadigital/jenkins-pipeline-library@v6.3.0') _

// Initialize global config
cfg = jplConfig('zipi', 'backend' ,'', [email: 'pedro.rodriguez+kevops@kairosds.com'])
cfg.commitValidation.enabled = false

pipeline {
    agent { label 'docker' }

    stages {
        stage ('Initialize') {
            steps  {
                jplStart(cfg)
            }
        }
//        stage('Sonar analysis') {
//            agent { label 'docker' }
//            when { branch 'develop' }
//            steps {
//                jplSonarScanner(cfg)
//            }
//        }
        stage ("Compile") {
            steps {
                sh "bin/devcontrol.sh compile"
            }
        }
        stage ("Test") {
            steps {
                sh "devcontrol test"
            }
            post {
                always {
                    sh "bin/devcontrol.sh destroy"
                }
            }
        }
        stage ("Build jar") {
            steps {
                sh "bin/devcontrol.sh build-jar"
            }
        }
        stage ("Build docker image") {
            when {
                anyOf {
                    branch 'PR-*';
                    branch 'main'
                }
            }
            steps {
                sh "bin/devcontrol.sh build-docker"
            }
        }
        stage ("Deploy") {
            when { branch 'main' }
            steps {
                sshagent (credentials: ['jpl-ssh-credentials']) {
                    sh "bin/devcontrol.sh deploy ubuntu@zipi.app.kevops.academy"
                }
            }
        }

    }

    post {
        always {
            jplPostBuild(cfg)
        }
    }

    options {
        timestamps()
        ansiColor('xterm')
        buildDiscarder(logRotator(artifactNumToKeepStr: '20',artifactDaysToKeepStr: '30'))
        disableConcurrentBuilds()
        timeout(time: 30, unit: 'MINUTES')
    }
}
