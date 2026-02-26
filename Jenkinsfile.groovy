pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build') {
      steps {
        sh 'chmod +x mvnw'
        sh './mvnw clean package -DskipTests'
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: '**/target/*.jar, **/target/*.war', allowEmptyArchive: true
    }
  }
}