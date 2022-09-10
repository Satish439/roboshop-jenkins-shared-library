def call() {
    node() {

        common.pipelineInit()
      stage ('Compile and build code') {
          sh 'yum install maven -y'
         sh 'mvn clean package'

      }
    }
}