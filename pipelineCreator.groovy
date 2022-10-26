// A new UUID must be generated for the first run and re-used for your Job DSL, the plugin updates jobs based on ID
UUID uuid = UUID.fromString("5f8becfc-e881-4ca7-9e5e-06de8acfa15c") // generate one @ https://www.uuidgenerator.net

multibranchPipelineJob("${Name}") {
    displayName "${Name}"
    description "Builds ${Name}"
    configure {
        it / sources / 'data' / 'jenkins.branch.BranchSource' << {
            source(class: 'jenkins.plugins.git.GitSCMSource') {
                id(uuid)
                remote("<Git repo url>")
                credentialsId("<Credentials ID for Git repo>")
                includes('*')
                excludes('')
                ignoreOnPushNotifications('false')
                traits {
                    'jenkins.plugins.git.traits.BranchDiscoveryTrait'()
                }
            }
        }
        // customise the branch project factory
        it / factory(class: "org.jenkinsci.plugins.workflow.multibranch.WorkflowBranchProjectFactory") << {
            owner(class:"org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject", reference: "../..")
            scriptPath("${jenkinsfile}")
        }
    }
}