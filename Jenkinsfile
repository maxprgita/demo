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
                    docker.withRegistry('https://registry.hub.docker.com', '8a94e3c3-161a-49ae-8697-3b9a38d73fa5') {
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
