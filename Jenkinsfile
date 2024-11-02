pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'BOOT_MIX_TECH_UNO'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/maxprgita/demo.git'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${env.DOCKER_IMAGE}:${env.BUILD_ID}")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credentials-id') {
                        docker.image("${env.DOCKER_IMAGE}:${env.BUILD_ID}").push("latest")
                    }
                }
            }
        }
    }

    post {
        always {
            sh "docker rmi ${env.DOCKER_IMAGE}:${env.BUILD_ID} || true"
        }
    }
}
