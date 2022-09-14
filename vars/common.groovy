def pipelineInit() {
    stage('initiate repo') {
        sh 'rm -rf *'
        git branch: 'main', url: "https://github.com/Satish439/${COMPONENT}.git"
    }
}

def publishArtifacts() {
    stage('Prepare Artifacts if tags are there') {
        if (env.APP_TYPE == "nodejs") {
            sh 'zip -r ${COMPONENT}-{TAG_NAME}.zip node_modules server.js'
        }

        if (env.APP_TYPE == 'maven') {

            sh 'mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar'
            sh 'zip -r ${COMPONENT}-{TAG-NAME}.zip ${COMPONENT}.jar'

        }
    }


    stage('Push Artifacts to Nexus') {
        withCredentials([usernamePassword(credentialsId: 'NEXUS', passwordVariable: 'pass', usernameVariable: 'user')]) {
            sh """
        curl -u ${user}:${pass} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://34.201.251.207:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
      """
        }
    }
}