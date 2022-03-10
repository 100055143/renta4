pipeline {
    agent any
      
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Sonar') {
            steps {
                sh '/home/curso/CURSOS/SOFTWARE/sonar-scanner-4.7.0.2747-linux/bin/sonar-scanner -Dsonar.projectKey=SpringBean -Dsonar.sources=. -Dsonar.java.libraries=. -Dsonar.java.binaries=. -Dsonar.host.url=http://192.168.99.241:9000 -Dsonar.login=c88a35a64f85a9592f73d89b80f5035846f84e00'
            }
        }
    }
}
