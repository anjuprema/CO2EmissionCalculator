pipeline {
    agent any  // Runs on any available Jenkins agent

    tools {
        jdk 'JDK11'         // Use JDK configured in Jenkins
        maven 'Maven3'      // Use Maven configured in Jenkins
    }

    stages {
        
        stage('Build with Maven') {
            steps {
                powershell 'mvn -f D:/anju-workspace/co2-emission-calculator/pom.xml clean install'
            }
        }

        stage('Run Tests') {
            steps {
                powershell 'mvn -f D:/anju-workspace/co2-emission-calculator/pom.xml test'
            }
        }

        stage('Copy JAR to Workspace') {
            steps {
                powershell 'copy D:/anju-workspace/co2-emission-calculator/target/*.jar D:/WORK_SPACE'
            }
        }
    }
}