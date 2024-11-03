pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'boot_mix_tech_uno'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/maxprgita/demo.git'
            }
        }

        stage('Build') {
            steps {
                bat './mvnw clean install -DskipTests'
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
            bat "docker rmi ${env.DOCKER_IMAGE}:${env.BUILD_ID} || exit 0"
        }
    }
}
