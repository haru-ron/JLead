node('BuildSlave') {
    stage 'Checkout'
    git branch: 'develop', credentialsId: '60bbefdc-0f0b-41f7-9944-fc9a8b6d6906', url: 'http://gitlab.local.arp-corp.jp/javajava/jlead.git'
    
    stage 'Clenup'
    sh '/opt/gradle/gradle-2.14/bin/gradle clean'
    
    stage 'Build'
    sh '/opt/gradle/gradle-2.14/bin/gradle jar'
    archiveArtifacts 'build/libs/*.jar'
    
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
}