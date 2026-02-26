pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build & Test') {
      steps {
        sh 'chmod +x mvnw'
        sh './mvnw -v'
        sh './mvnw clean test'
      }
    }

    stage('Package') {
      steps {
        sh './mvnw clean package -DskipTests'
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true, allowEmptyArchive: true
      junit '**/target/surefire-reports/*.xml'
    }
  }
}