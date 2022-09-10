def call() {
    node() {

        common.pipelineInit()
      stage ('Compile and build code') {
          sh 'sudo yum install maven -y'
          sh 'mvn clean package'

      }
    }
}