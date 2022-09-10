def call() {
    node() {

        common.pipelineInit()

    stage('Download Dependencies') {
        sh '''
               ls -l
               npm install 
             '''
    }
}
}