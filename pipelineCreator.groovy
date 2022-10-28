// A new UUID must be generated for the first run and re-used for your Job DSL, the plugin updates jobs based on ID
UUID uuid = UUID.fromString("5f8becfc-e881-4ca7-9e5e-06de8acfa15c") // generate one @ https://www.uuidgenerator.net

multibranchPipelineJob("${Name}") {
    displayName "${Name}"
    description "Builds ${Name}"
    configure {
        it / 'extensions' / 'hudson.plugins.git.extensions.impl.PathRestriction' {
            includedRegions "./apps/${Name}/.*"
            excludedRegions "README.md\n\\.gitignore"
        }
        it / sources / 'data' / 'jenkins.branch.BranchSource' << {
            source(class: 'jenkins.plugins.git.GitSCMSource') {
                id(uuid)
                remote("https://github.com/levymateus/monorepo-mfes")
                // credentialsId("<Credentials ID for Git repo>")
                includes('master')
                excludes('')
                ignoreOnPushNotifications('false')
                includedRegions "./apps/${Name}/.*"
                excludedRegions "README.md\n\\.gitignore"
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