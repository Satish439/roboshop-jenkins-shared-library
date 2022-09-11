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

    stage('Push Artifacts to Nexus') {
        withCredentials([usernamePassword(credentialsId: 'NEXUS', passwordVariable: 'password', usernameVariable: 'user')]) {
            sh 'curl -v -u --user <admin:admin>  --upload-file ${COMPONENT}-${TAG_NAME}.zip http://34.238.85.190:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip'

        }
    }
}