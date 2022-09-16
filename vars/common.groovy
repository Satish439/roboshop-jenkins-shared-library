def pipelineInit() {
    stage('initiate repo') {
        sh 'rm -rf *'
        git branch: 'main', url: "https://github.com/Satish439/${COMPONENT}.git"
    }
}

def publishArtifacts() {
    stage('Prepare Artifacts if tags are there') {
        if (env.APP_TYPE == "nodejs") {
            sh 'zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js'
        }

        if (env.APP_TYPE == 'maven') {

            sh 'mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar'
            sh 'zip -r ${COMPONENT}-${TAG_NAME}.zip ${COMPONENT}.jar'

        }
    }


    stage('Push Artifacts to Nexus') {
        withCredentials([usernamePassword(credentialsId: 'NEXUS', passwordVariable: 'password', usernameVariable: 'username')]) {
            sh """
        curl -u ${USERNAME}:${PASSWORD} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://3.91.226.208:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
      """
        }
    }
}

def codechecks() {
    stage('Quality checks and Unit Tests') {
        parallel([
              qualitychecks: {
                  withCredentials([usernamePassword(credentialsId: 'SONAR', passwordVariable: 'password', usernameVariable: 'username')]) {
                      sh "sonar-scanner -Dsonar.projectkey=${COMPONENT} -Dsonar.host.url=http://52.201.103.29:9000 -Dsonar.login=${username} -Dsonar.password=${password}"
                  }
                  echo "hello"
              },
                unitTests: {
                    unitTests()

        }
        ])
    }
}

def unitTests() {
    if (evn.APP_TYPE == "nodejs") {
        sh 'npm run test'
        echo Run test cases
    }
}