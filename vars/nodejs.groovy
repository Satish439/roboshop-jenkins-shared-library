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


//if both are equal then it is definitely a tag//

    if (env.BRANCH_NAME == env.TAG_NAME)

    {
        common.publishArtifacts()
    }
}
