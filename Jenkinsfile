node {
    def maven = docker.image("maven:3.3.9-jdk-8")

    maven.inside {
        try {
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
        } finally {
            stage('Status - Final') {
                /*step([$class      : 'GitHubPRBuildStatusPublisher',
                      buildMessage: [failureMsg: [content: 'Build failed..'],
                                     successMsg: [content: 'Build succeeded!']],
                      statusMsg   : [content: '${GITHUB_PR_COND_REF} run ended'],
                      unstableAs  : 'FAILURE']
                )*/
                step([$class       : 'GitHubCommitStatusSetter',
                      contextSource: [$class: 'ManuallyEnteredCommitContextSource', context: 'build_status'],
                      errorHandlers: [[$class: 'ShallowAnyErrorHandler']]])
            }
        }

    }
}