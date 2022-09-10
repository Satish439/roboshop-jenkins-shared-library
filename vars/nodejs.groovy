def call() {
    node() {
        stage( 'download nodejs depenceices') {
            sh 'ls -ltr'
            sh 'npm install'

        }
    }
}