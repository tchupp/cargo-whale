node {
    def maven = docker.image("maven:3.3.9-jdk-8")

    maven.inside {
        stage('Checkout') {
            checkout scm
        }

        stage('Verify Env') {
            fileExists 'pom.xml'
        }

        stage('Clean') {
            sh 'mvn clean'
        }

        stage('Unit Test') {
            sh 'mvn test'
        }

        stage('Integration Tests') {
            sh 'mvn verify'
        }

        stage('Cleanup') {
            step([$class: 'WsCleanup'])
        }
    }
}