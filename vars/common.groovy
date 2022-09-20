def pipelineInit() {
    stage('initiate repo') {
        sh 'rm -rf *'
        git branch: 'main', url: "https://github.com/Satish439/${COMPONENT}.git"
    }
}

def publishArtifacts() {
    env.ENV= "dev"
    stage('Prepare Artifacts if tags are there') {
        if (env.APP_TYPE == "nodejs") {
            sh 'zip -r ${ENV}-${COMPONENT}-${TAG_NAME}.zip node_modules server.js'
        }

        if (env.APP_TYPE == 'maven') {

            sh 'mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar'
            sh 'zip -r ${COMPONENT}-${TAG_NAME}.zip ${COMPONENT}.jar'

        }
    }


    stage('Push Artifacts to Nexus') {
        withCredentials([usernamePassword(credentialsId: 'NEXUS', passwordVariable: 'password', usernameVariable: 'username')]) {
            sh """
        curl -u ${USERNAME}:${PASSWORD} --upload-file ${ENV}-${COMPONENT}-${TAG_NAME}.zip http://44.204.0.216:8081/repository/${COMPONENT}/${ENV}-${COMPONENT}-${TAG_NAME}.zip
      """
        }
    }
}

//    stage('Deploy to Dev Env') {
//        build job: 'deploy-to-any-env', parameters: [string(name: 'COMPONENT', value: "${COMPONENT}"), string(name: 'ENV', value: "${ENV}"), string(name: 'APP_VERSION', value: "${TAG_NAME}")]
//    }
//
//    stage('Run Smoke Tests on DEV') {
//        sh "echo Smoke tests"
//    }
//    promoteRelease("dev", "qa")
//
//    stage('Deploy to QA Env') {
//        //build job: 'deploy-to-any-env', parameters: [string(name: 'COMPONENT', value: "${COMPONENT}"), string(name: 'ENV', value: "qa"), string(name: 'APP_VERSION', value: "${TAG_NAME}")]
//        echo 'QA Deploy'
//    }
//
//    testRuns()
//
//    stage('Run Smoke Tests on QA') {
//        sh "echo Smoke tests"
//    }
//}

//
//    def codeChecks() {
//        stage('Quality Checks & Unit Tests') {
//            parallel([
//                    qualityChecks: {
//                        withCredentials([usernamePassword(credentialsId: 'SONAR', passwordVariable: 'pass', usernameVariable: 'user')]) {
//                            sh "sonar-scanner -Dsonar.projectKey=${COMPONENT} -Dsonar.host.url=http://44.204.0.216:9000 -Dsonar.login=${user} -Dsonar.password=${pass}"
//                            sh 'sonar-quality-gate.sh $(user) ${pass} 52.201.103.29 ${COMPONENT} '
//
//                        }
//                    },
//                    unitTests    : {
//                        unitTests()
//                    }
//            ])
//        }
//    }
//
//    def unitTests() {
//        if (env.APP_TYPE == "nodejs") {
//            sh """
//                npm run test
//                echo Run test cases
//      """
//
//        }
//    }
//}
.
.