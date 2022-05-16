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
        stage ("Compile") {
            steps {
                sh "bin/devcontrol.sh compile"
            }
        }
        stage ("Test") {
            steps {
                sh "bin/devcontrol.sh test"
                withSonarQubeEnv ('SonarCloud') {
                    sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar"
                }
            }
            post {
                always {
                    sh "bin/devcontrol.sh destroy"
                }
            }
        }
        stage ("Wait for Quality Gate") {
            agent none

            steps {
                timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
                    def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
                    if (qg.status != 'OK') {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                    }
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
