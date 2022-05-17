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
                sh """
                sed -i "s/5432:5432/5432/g" docker-compose.test.yaml
                bin/devcontrol.sh test
                git checkout docker-compose.test.yaml
                """
            }
            post {
                always {
                    sh "bin/devcontrol.sh destroy"
                }
            }
        }
        stage ("Sonar: Regular Branch Check") {
            when { not { branch 'PR-*' } }
            steps {
                // Make analysis of the branch with SonarScanner and send it to SonarCloud
                withSonarQubeEnv ('SonarCloud') {
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar \
                        -Dsonar.organization="kevops-acme" \
                        -Dsonar.projectKey="kevops-acme_zipi" \
                        -Dsonar.branch.name="$BRANCH_NAME"'
                }
            }
        }
        stage ("Sonar: PR Check") {
            when { branch 'PR-*' }
            steps {
                // Make analysis of the PR with SonarScanner and send it to SonarCloud
                // Reference: https://blog.jdriven.com/2019/08/sonarcloud-github-pull-request-analysis-from-jenkins/
                withSonarQubeEnv ('SonarCloud') {
                    sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar \
                        -Dsonar.organization='kevops-acme' \
                        -Dsonar.projectKey='kevops-acme_zipi' \
                        -Dsonar.pullrequest.provider='GitHub' \
                        -Dsonar.pullrequest.github.repository='kevops-acme/zipi' \
                        -Dsonar.pullrequest.key='${env.CHANGE_ID}' \
                        -Dsonar.pullrequest.branch='${env.CHANGE_BRANCH}'"
                }
            }
        }
        stage ("Sonar: Wait for QG") {
            steps {
                // Wait for QuaityGate webhook result
                timeout(time: 1, unit: 'HOURS') {
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    waitForQualityGate abortPipeline: true
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
