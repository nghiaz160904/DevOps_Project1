pipeline {
    agent none

    environment {
        DOCKER_HUB = credentials('docker-hub-cred')
    }

    stages {
        stage('Detect Changed Services') {
            agent { label 'built-in' }
            steps {
                script {
                    def changedFiles = sh(script: "git diff --name-only HEAD^", returnStdout: true).trim().split('\n')
                    def services = ['spring-petclinic-customers-service', 'spring-petclinic-visits-service', 'spring-petclinic-vets-service']

                    def detected = services.findAll { service ->
                        changedFiles.any { it.startsWith(service + '/') }
                    }

                    if (detected.isEmpty()) {
                        currentBuild.result = 'ABORTED'
                        error("No service changes detected. Skipping pipeline.")
                    }

                    env.SERVICE_CHANGED = detected.join(',')
                    echo "Services changed: ${env.SERVICE_CHANGED}"
                }
            }
        }

        stage('Test and Coverage') {
            parallel {
                stage('Agent 1 - customers & visits') {
                    agent { label 'agent1' }
                    when {
                        expression {
                            env.SERVICE_CHANGED.contains('customers') || env.SERVICE_CHANGED.contains('visits')
                        }
                    }
                    steps {
                        script {
                            def services = env.SERVICE_CHANGED.split(',').findAll {
                                it.contains('customers') || it.contains('visits')
                            }
                            for (service in services) {
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
                stage('Agent 2 - vets') {
                    agent { label 'agent2' }
                    when {
                        expression {
                            env.SERVICE_CHANGED.contains('vets')
                        }
                    }
                    steps {
                        sh "./mvnw clean verify -pl spring-petclinic-vets-service -am"
                        stash name: "spring-petclinic-vets-service-coverage", includes: "spring-petclinic-vets-service/target/site/jacoco/**"
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
            steps {
                script {
                    def failed = []
                    def services = env.SERVICE_CHANGED.split(',')
                    services.each { service ->
                        unstash "${service}-coverage"
                        def coverage = sh(script: "xmllint --html --xpath 'string(//table[@id=\"coveragetable\"]/tfoot/tr/td[3])' ${service}/target/site/jacoco/index.html 2>/dev/null", returnStdout: true).trim().replace('%', '').toFloat()
                        echo "Coverage for ${service}: ${coverage}%"
                        if (coverage < 70) {
                            failed << service
                        }
                    }

                    if (!failed.isEmpty()) {
                        error("Coverage below 70% for: ${failed.join(', ')}")
                    }
                }
            }
        }

        stage('Build and Push Docker Images') {
            agent { label 'built-in' }
            steps {
                script {
                    def commit = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
                    def branch = env.GIT_BRANCH.replace('origin/', '')
                    def services = env.SERVICE_CHANGED.split(',')

                    withCredentials([usernamePassword(credentialsId: 'docker-hub-cred', passwordVariable: 'DOCKER_HUB_PSW', usernameVariable: 'DOCKER_HUB_USR')]) {
                        sh 'echo "$DOCKER_HUB_PSW" | docker login -u "$DOCKER_HUB_USR" --password-stdin'
                        
                        for (service in services) {
                            def imageName = "${DOCKER_HUB_USR}/${service}"
                            sh "docker build -t ${imageName}:${commit} ${service}"
                            sh "docker push ${imageName}:${commit}"
                            if (branch == 'main') {
                                sh "docker tag ${imageName}:${commit} ${imageName}:latest"
                                sh "docker push ${imageName}:latest"
                            }
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline success'
        }
        failure {
            echo 'Pipeline failed'
        }
        aborted {
            echo 'Pipeline aborted'
        }
    }
}
