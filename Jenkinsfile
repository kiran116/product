pipeline {
    agent any

    stages {
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