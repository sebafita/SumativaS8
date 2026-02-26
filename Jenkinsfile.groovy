pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build (skip tests)') {
      steps {
        sh './mvnw -v'
        sh './mvnw clean package -DskipTests'
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: '**/target/*.jar, **/target/*.war', fingerprint: true, allowEmptyArchive: true
    }
  }
}