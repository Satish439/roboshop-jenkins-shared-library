def call() {
    node() {

        common.pipelineInit()
      stage ('Compile and build code') {
         sh 'mvn clean package'

      }
    }
}