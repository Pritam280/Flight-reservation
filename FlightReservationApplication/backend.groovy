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
        stage('QA-test'){
            steps{
                withSonarQubeEnv(installationName: 'sonar',credentialsId: 'sonar-token') {
                    sh '''
                        cd FlightReservationApplication
                        mvn sonar:sonar -Dsonar.projectKey=flight-reservation 

                    '''
                }
            }
        }
        stage('Docker-build'){
            steps{
                sh '''
                    cd FlightReservationApplication
<<<<<<< HEAD
                    docker build -t alexaa123/flight-reservation:latest .
=======
                    docker build -t flight-reservation:latest .
>>>>>>> 979365ac01dc1ff26b3f0706ed423a51ef5bc35c
                    docker push alexaa123/flight-reservation:latest
                    docker rmi alexaa123/flight-reservation:latest
                '''
            }
        }
        stage('Deploy'){
            steps{
                sh '''
                    cd FlightReservationApplication
                    kubectl apply -f k8s 
                '''
            }
        }
    }
}
