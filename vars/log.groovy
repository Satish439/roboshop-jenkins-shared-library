def info(message) {
    echo "INFO: ${message}"
}

def warning(message) {
    echo "WARNING: ${message}"
}


def call() {
    pipeline {
        agent any {
        stages {
            stages('one')
            steps {
                sh 'echo one'
            }
        }
        stages {
            stages('one')
               steps {
                   sh 'echo two'
               }

        }
    }
}
