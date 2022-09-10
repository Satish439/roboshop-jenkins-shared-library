def call() {
    node() {

        common.pipelineInit()
      stage ('Compile and build code') {
          mvn clean package

      }
    }
}