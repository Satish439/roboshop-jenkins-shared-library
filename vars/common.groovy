def pipelineInit() {
    stage('initiate repo') {
        sh 'rm -rf *'
        git branch: 'main', url: "https://github.com/Satish439/${COMPONENT}.git"
    }
}

def publishArtifacts() {
    stage('publish Artifacts if tags are there') {
        if (env.APP_TYPE == "nodejs") {
            sh 'zip -r ${COMPONENT}-{TAG_NAME}.zip node_modules server.js'
        }
    }
}