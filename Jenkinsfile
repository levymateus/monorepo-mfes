pipeline {
    agent any
    stages {
        stage('Create jobs') {
            steps {
                script {
                    def files = findFiles(glob: ' **/Jenkinsfile')
                    for (int i = 1; i < files.size(); i++) {
                        echo files[i].name
                        def filePath = files[i].path
                        def pathWithoutFile = filePath.replace('/Jenkinsfile', '')
                        def jobName = "monorepo-mfes-" + ( pathWithoutFile =~ /([^\/]+)\/?$/)[0][0]
                        echo filePath
                        echo jobName
                        def jobs = Jenkins.get().getItems()
                        def job = jobs.getItem(jobName)
                        // Instance, getItemMap is insecure.
                        if(job == null) {
                            echo "Job ${jobName} does not exist, creating..."
                            createJob(filePath, jobName)
                        } else {
                            echo "Job ${jobName} already exists."
                        }
                    }
                }
            }
        }

    }
}

def createJob(filePath, jobName){
    jobDsl  targets: '*.groovy',
    removedJobAction: 'IGNORE',
    removedViewAction: 'IGNORE',
    lookupStrategy: 'JENKINS_ROOT',
    additionalParameters: [jenkinsfile: filePath, Name: jobName]
}
