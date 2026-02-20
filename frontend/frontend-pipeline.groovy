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
                sh''' 
                    cd frontend
                    npm install
                    npm run build
                '''
            }
        }
        stage('Deploy'){
            steps{
                sh '''
                    cd frontend
                    aws s3 sync dist/ s3://cblkdfsfdsc-front12end-project-bux/
                '''    
            }
        }
    }
}