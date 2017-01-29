node('BuildSlave') {
    stage 'Checkout'
    git credentialsId: '0cb838df-e595-40c3-99fd-547bb51819f0', url: 'https://github.com/haru-ron/JLead'
    
    stage 'Clenup'
    sh '/opt/gradle/gradle-2.14/bin/gradle clean'
    
    stage 'Build'
    sh '/opt/gradle/gradle-2.14/bin/gradle jar'
    archiveArtifacts 'build/libs/*.jar'
    
    stage 'Test'
    sh '/opt/gradle/gradle-2.14/bin/gradle jacoco'

    stage 'Document'
    sh '/opt/gradle/gradle-2.14/bin/gradle javadoc'
    publishHTML (target: [
      allowMissing: false,
      alwaysLinkToLastBuild: false,
      keepAll: true,
      reportDir: 'build/docs/javadoc',
      reportFiles: 'index.html',
      reportName: "API Reference"
    ])
    publishHTML (target: [
      allowMissing: false,
      alwaysLinkToLastBuild: false,
      keepAll: true,
      reportDir: 'build/reports/tests',
      reportFiles: 'index.html',
      reportName: "Unit Test Report"
    ])
    publishHTML (target: [
      allowMissing: false,
      alwaysLinkToLastBuild: false,
      keepAll: true,
      reportDir: 'build/reports/jacoco/jacoco/html',
      reportFiles: 'index.html',
      reportName: "Coverage Report"
    ])
}