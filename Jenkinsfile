node {
    stage('clean') {
        sh "mvn clean"
    }

    stage('backend tests') {
        sh "mvnw test"
    }

    stage('packaging') {
        sh "mvnw package -DskipTests"
    }
}