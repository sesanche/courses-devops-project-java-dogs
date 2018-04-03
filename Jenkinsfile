node {
    stage 'Checkout'
    checkout scm

    stage 'Compile'
    sh "./gradlew compileJava"

    stage 'Test unitario'
    sh "./gradlew test"

    stage 'Test Integracion'
    sh "./gradlew integrationTest"


    stage 'Build'
    sh "./gradlew build"
}