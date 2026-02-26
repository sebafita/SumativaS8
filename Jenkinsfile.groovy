pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build WAR') {
      steps {
        sh 'chmod +x mvnw || true'
        sh './mvnw clean package -DskipTests'
        sh 'ls -lah target || true'
      }
    }

    stage('Docker Build') {
      steps {
        sh 'docker build -t vehiculos-app:latest .'
      }
    }

    stage('Deploy Tomcat') {
      steps {
        sh 'docker rm -f vehiculos-app || true'
        sh 'docker run -d --name vehiculos-app -p 9090:8080 vehiculos-app:latest'
        sh 'docker ps'
      }
    }

    stage('Smoke test') {
      steps {
        sh 'sleep 10'
        sh 'curl -I http://localhost:9090/ || true'
      }
    }
  }
}
