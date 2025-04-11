pipeline {
    agent {
        label 'slave-agent'
    }

    environment {
        MAVEN_HOME = '/usr/share/maven'
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64' // Adjust for your environment
        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
        IMAGE_NAME = 'order-service'
        AWS_REGION = 'us-east-1' // Change this to your AWS region
        ECR_REGISTRY = '203918864735.dkr.ecr.us-east-1.amazonaws.com/order-repo'
        ECR_REGISTRY_URL = '203918864735.dkr.ecr.us-east-1.amazonaws.com'
        ECR_REPO_NAME = 'order-repo'
        ECR_REPO_URI = "${ECR_REGISTRY_URL}/${ECR_REPO_NAME}"

}

    stages {
        stage('Checkout stage') {
            steps {
                git url: 'https://github.com/Supriyaram/order-service', branch: 'main'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Docker Tag') {
            steps {
                sh 'docker tag $IMAGE_NAME $ECR_REPO_URI:latest'
            }
        }

        stage('Docker Login to ECR') {
            steps {
                sh 'aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ECR_REGISTRY'
            }
        }

        stage('Push to ECR') {
            steps {
                sh 'docker push $ECR_REPO_URI:latest'
            }
        }
        

    }


    post {
        success {
            echo 'Build and archive successful!'
        }
        failure {
            echo 'Something went wrong during the build.'
        }
    }
}