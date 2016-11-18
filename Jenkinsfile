node {
    def maven = docker.image("maven:3.3.9-jdk-8")

    maven.inside {
        try {
            stage('Verify Env') {
                fileExists 'pom.xml'
            }

            stage('Status - Pending') {
                step([$class: 'GitHubSetCommitStatusBuilder'])
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
        } finally {
            stage('Status - Final') {
                step([$class: 'GitHubCommitStatusSetter'])
            }
        }

    }
}