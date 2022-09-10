def info(message) {
    echo "INFO: ${message}"
}

def warning(message) {
    echo "WARNING: ${message}"
}


def call() {
  pipeline {
    agent any
    stages {
      stage('One') {
        steps {
          sh 'echo ${COMPONENT}'
        }
      }
      stage('Two') {
        steps {
          sh 'echo Two'
        }
      }
    }
  }
}

// scripted pipeline we will use node instead of pipeline and agent//
def call() {
  node() {
    stage('one') {
      sh 'echo ${COMPONENT}'
    }
    stage('Two') {
      sh 'echo ${COMPONENT}'
    }
  }

}
