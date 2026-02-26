pipeline {
  agent any

  tools {
    // Si no tienes Maven configurado como tool en Jenkins, quitamos esta sección luego
    maven 'Maven'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Test') {
      steps {
        sh 'mvn -v'
        sh 'mvn clean test'
      }
    }

    stage('Package') {
      steps {
        sh 'mvn clean package -DskipTests'
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