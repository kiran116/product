pipeline {
    agent any

    stages {
        stage('Show Workspace') {
            steps {
                echo "The workspace is located at: ${env.WORKSPACE}"
            }
        }

        stage('Checkout') {
            steps {
                git 'https://github.com/kiran116/product.git'
            }
        }

        stage('Build') {
            steps {
                // Replace the following line with your build command
                sh 'echo "Building project..."'
            }
        }
    }
}