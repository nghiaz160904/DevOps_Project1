pipeline {
    agent none

    environment {
        OTHER = ''
        DOCKER_HUB = credentials('docker-hub-cred')
        APP_NAME = 'spring-petclinic-microservices'
    }

    stages {
        stage('Check Changes') {
            agent { label 'built-in' }
            steps {
                script {
                    echo "Commit SHA: ${GIT_COMMIT}"
                    def changedFiles = sh(script: "git diff --name-only HEAD^", returnStdout: true).trim().split('\n')
                    def services = ['spring-petclinic-customers-service', 'spring-petclinic-visits-service', 'spring-petclinic-vets-service']
                    def detectedServices = services.findAll { svc -> changedFiles.any { it.startsWith("$svc/") } }

                    if (detectedServices.isEmpty()) {
                        echo "No relevant service changes detected. Skipping pipeline."
                        env.NO_SERVICES_TO_BUILD = 'true'
                        currentBuild.result = 'ABORTED'
                        return
                    }

                    env.NO_SERVICES_TO_BUILD = 'false'
                    env.SERVICE_CHANGED = detectedServices.join(',')
                    echo "Detected changed services: ${env.SERVICE_CHANGED}"
                }
            }
        }

        stage('Test & Coverage') {
            parallel {
                stage('Agent 1') {
                    agent { label 'agent1' }
                    when {
                        expression { env.NO_SERVICES_TO_BUILD == 'false' && (env.SERVICE_CHANGED.contains('customers') || env.SERVICE_CHANGED.contains('visits')) }
                    }
                    steps {
                        script {
                            def services = env.SERVICE_CHANGED.split(',').findAll { it.contains('customers') || it.contains('visits') }
                            for (service in services) {
                                echo "Running tests for ${service} on agent1"
                                sh "./mvnw clean verify -pl ${service} -am"
                                stash name: "${service}-coverage", includes: "${service}/target/site/jacoco/**"
                            }
                        }
                    }
                    post {
                        always {
                            script {
                                def services = env.SERVICE_CHANGED.split(',')
                                for (service in services) {
                                    junit "${service}/target/surefire-reports/*.xml"
                                }
                            }
                        }
                    }
                }

                stage('Agent 2') {
                    agent { label 'agent2' }
                    when {
                        expression { env.NO_SERVICES_TO_BUILD == 'false' && env.SERVICE_CHANGED.contains('vets') }
                    }
                    steps {
                        script {
                            echo "Running tests for vets-service on agent2"
                            sh "./mvnw clean verify -pl spring-petclinic-vets-service -am"
                            stash name: "spring-petclinic-vets-service-coverage", includes: "spring-petclinic-vets-service/target/site/jacoco/**"
                        }
                    }
                    post {
                        always {
                            junit "spring-petclinic-vets-service/target/surefire-reports/*.xml"
                        }
                    }
                }
            }
        }

        stage('Check Coverage') {
            agent { label 'built-in' }
            when {
                expression { env.NO_SERVICES_TO_BUILD == 'false' }
            }
            steps {
                script {
                    def services = env.SERVICE_CHANGED.split(',')
                    def failedCoverage = []

                    for (service in services) {
                        unstash "${service}-coverage"
                        def coverageHtml = sh(
                            script: "xmllint --html --xpath 'string(//table[@id=\"coveragetable\"]/tfoot/tr/td[3])' ${service}/target/site/jacoco/index.html 2>/dev/null",
                            returnStdout: true
                        ).trim()

                        def coverage = coverageHtml.replace('%', '').toFloat() / 100
                        echo "Coverage for ${service}: ${coverage * 100}%"
                        if (coverage < 0.7) failedCoverage << service
                    }

                    if (!failedCoverage.isEmpty()) {
                        error "Coverage < 70% for: ${failedCoverage.join(', ')}"
                    }
                }
            }
        }

        stage('Build & Push Docker Images') {
            agent { label 'built-in' }
            when {
                expression { env.NO_SERVICES_TO_BUILD == 'false' }
            }
            steps {
                script {
                    def commitId = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
                    def branch = env.GIT_BRANCH.replace('origin/', '')
                    def services = env.SERVICE_CHANGED.split(',')

                    withCredentials([usernamePassword(
                        credentialsId: 'docker-hub-cred',
                        usernameVariable: 'DOCKER_HUB_USR',
                        passwordVariable: 'DOCKER_HUB_PSW'
                    )]) {
                        sh "echo \"$DOCKER_HUB_PSW\" | docker login -u \"$DOCKER_HUB_USR\" --password-stdin"
                    }

                    for (service in services) {
                        def image = "nghiax1609/${service}:${commitId}"
                        echo "Building Docker image for ${service}"
                        sh "docker build -t ${image} ${service}"
                        sh "docker push ${image}"

                        if (branch == 'main') {
                            def latestImage = "nghiax1609/${service}:latest"
                            sh "docker tag ${image} ${latestImage}"
                            sh "docker push ${latestImage}"
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline completed successfully."
        }
        failure {
            echo "❌ Pipeline failed."
        }
        aborted {
            echo "⚠️ Pipeline was aborted."
        }
    }
}
