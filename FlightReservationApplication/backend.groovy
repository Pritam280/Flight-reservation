pipeline{
    agent any 
    stages{
        stage('Code-pull'){
            steps{
                git branch: 'main', url: 'https://github.com/Pritam280/flight-reservation.git'
            }
        }
        stage('Build'){
            steps{
                sh '''
                    cd FlightReservationApplication
                    mvn clean package
                '''
            }
        }
        stage('QA-TEST') {
            steps{
                withSonarQubeEnv(installationName: 'sonar', credentialsId: 'sonar-cred') {
                    sh '''
                    cd FlightReservationApplication
                    mvn sonar:sonar -Dsonar.projectKey=flight-reservation-backend
                '''
                }
            }
        }
        stage('Docker-build'){
            steps{
                sh '''
                    cd FlightReservationApplication
                    docker build -t alexaa123/flight-reservation:latest
                    docker push alexaa123/flight-reservation:latest
                    docker rmi alexaa123/flight-reservation:latest
                '''
            }
        }
        stage('Depoly'){
            sh '''
                cd FlightReservationApplication
                kubectl apply -f k8s/
            '''
        }
    }
}