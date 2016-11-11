node {
    def maven = docker.image("maven:3.3.9-jdk-8")

    maven.inside {
        stage('Clean') {
            sh 'mvn clean'
        }

        stage('Test') {
            sh 'mvn test'
        }

        stage('Package') {
            sh 'mvn package -DskipTests'
        }

        stage('Build Docker') {
            sh 'mvn docker:build'
        }
    }
}