def args = [:]
args.CLUSTER_NAME = "https://172.30.0.1:443"
args.PROJECT_NAME = "oc-springtraining"
args.SERVICE_NAME = "customer-service-raghav"
args.SERVICE_VERSION = "0.0.1-SNAPSHOT"

pipeline {
    agent any
        options {
            disableConcurrentBuilds()
            buildDiscarder(logRotator(numToKeepStr: '5'))
        }
        stages {
            stage('Preamble') {
                steps {
                    script {
                        openshift.withCluster() {
                            openshift.withProject() {
                                echo "Using Project: ${openshift.project()}"
                                echo "Using Cluster: ${openshift.cluster()}"
                            }
                        }
                    }
                }
            }
            stage ('Code Checkout') {
                steps {
                    script {
                        checkout scm
                    }
                }
            }
            stage ('Build') {
                steps {
                    script {
                        echo "Checkout completed. Starting the build"
                        withMaven(maven: 'maven-latest') {
                            sh 'mvn clean install package'
                            //stash name:"jar", includes:"target/customer-service-*.jar"
                        }
                    }
                }
            }
            stage('unit tests') {
                steps {
                    script {
                        withMaven(maven: 'maven-latest') {
                        sh 'mvn test'   
                        }
                    }
                }   
            }
            stage('Create Image Builder') {
                when {
                    expression {
                        openshift.withCluster(args.CLUSTER_NAME) {
                            openshift.withProject(args.PROJECT_NAME) {
                                return !openshift.selector("bc", "${args.SERVICE_NAME}").exists()
                            }
                        }
                    }
                }
                steps {
                    script {
                        openshift.withCluster(args.CLUSTER_NAME) {
                            openshift.withProject(args.PROJECT_NAME) {
                                openshift.newApp "registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift~./", "--name=${args.SERVICE_NAME}"
                                //openshift.newBuild("--name=${args.SERVICE_NAME}", "--image-stream=redhat-openjdk18-openshift:latest", "--binary=true")
                            }
                        }
                    }
                }               
            }
            stage('Build Image') {
                steps {
                    script {
                        openshift.withCluster(args.CLUSTER_NAME) {
                            openshift.withProject(args.PROJECT_NAME) {
                                def build = openshift.selector("bc", "${args.SERVICE_NAME}");
                                def startedBuild = build.startBuild("--from-file=\"./target/${args.SERVICE_NAME}-${args.SERVICE_VERSION}.jar\"");
                                startedBuild.logs('-f');
                                echo "Customer service build status: ${startedBuild.object().status}";
                            }
                        }
                    }
                }
            }
            stage('Tag Image') {
                steps {
                    script {
                        openshift.withCluster(args.CLUSTER_NAME) {
                            openshift.withProject(args.PROJECT_NAME) {
                                openshift.tag("${args.SERVICE_NAME}"+":latest", "${args.SERVICE_NAME}"+":dev")
                            }
                        }
                    }
                }
            }
            stage('Deploy STAGE') {
                steps {
                    script {
                        openshift.withCluster(args.CLUSTER_NAME) {
                            openshift.withProject(args.PROJECT_NAME) {
                                if (openshift.selector('dc', "${args.SERVICE_NAME}").exists()) {
                                    openshift.selector('dc', "${args.SERVICE_NAME}").delete()
                                    openshift.selector('svc', "${args.SERVICE_NAME}").delete()
                                    //openshift.selector('route', "${args.SERVICE_NAME}").delete()
                                }
                                if (openshift.selector('route', "${args.SERVICE_NAME}").exists()) {
                                    openshift.selector('route', "${args.SERVICE_NAME}").delete()
                                }
                                openshift.newApp("${args.SERVICE_NAME}").narrow("svc").expose()
                            }
                        }
                    }
                }
            }
        }
}
