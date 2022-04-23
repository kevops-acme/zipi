#!groovy

@Library('github.com/ayudadigital/jenkins-pipeline-library@v6.3.0') _

// Initialize global config
cfg = jplConfig('zipi', 'backend' ,'', [email: 'pedro.rodriguez+kevops@kairosds.com'])

pipeline {
    agent none

    stages {
        stage ('Initialize') {
            agent { label 'docker' }
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
            agent { label 'docker' }
            when { branch 'demo' }
            steps {
                sh "devcontrol compile"
            }
        }
        stage ("Test") {
            agent { label 'docker' }
            when { branch 'demo' }
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
            agent { label 'docker' }
            when { branch 'demo' }
            steps {
                sh "devcontrol build-jar"
            }
        }
        stage ("Build docker image") {
            agent { label 'docker' }
            when { branch 'demo' }
            steps {
                sh "devcontrol build-docker"
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
