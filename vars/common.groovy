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
        withCredentials([usernamePassword(credentialsId: 'NEXUS', passwordVariable: 'pass', usernameVariable: 'user')]) {
            sh """
        curl -v -u ${user}:${pass} --upload-file ${COMPONENT}${TAG_NAME}.zip http://44.201.247.109:8081//repository/${COMPONENT}/${COMPONENT}${TAG_NAME}.zip
      """
        }
    }
}