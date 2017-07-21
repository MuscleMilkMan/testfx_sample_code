maven {
    // maven central
    centralUrl='http://10.16.12.201:8081/nexus/content/repositories/central'
    thirdPartyUrl='http://10.16.12.201:8081/nexus/content/repositories/thirdparty/'
}

testing {
    testKeyStorePath='config/testkeystore/testkeystore.jks'
    testKeyStorePassword='12345'
}

environments {
    dev {
        maven {
            // maven repository
            url='http://10.16.12.201:8081/nexus/content/repositories/snapshots/'
            username='deployment'
            password='deployment123'   
        }
    }
    
    qa {
        maven {
            // maven repository
            url='http://10.16.12.201:8081/nexus/content/repositories/releases/'
            username='deployment'
            password='deployment123'   
        }
    }
}
