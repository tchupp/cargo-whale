node {
    def maven = docker.image("maven:3.3.9-jdk-8")

    maven.inside {

        stage('Install Tools') {
//            sh 'apt-get'
        }

        stage('Check Tools') {
//            sh 'mvn clean'
        }

        stage('Checkout') {
            git 'https://github.com/tclchiam/cargo-whale-docker'
        }

        stage('Clean') {
            sh 'mvn clean'
        }

        stage('Test') {
            sh 'mvn test'
        }

        stage('Package') {
            sh 'mvn package -Pprod -DskipTests'
        }

        stage('Build Docker') {
            sh 'mvn docker:build'
        }
    }
}