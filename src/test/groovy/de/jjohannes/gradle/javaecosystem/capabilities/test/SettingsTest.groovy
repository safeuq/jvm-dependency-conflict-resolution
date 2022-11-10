package de.jjohannes.gradle.javaecosystem.capabilities.test

import de.jjohannes.gradle.javaecosystem.capabilities.test.fixture.GradleBuild
import spock.lang.Specification
import spock.lang.Tag

class SettingsTest extends Specification {

    @Delegate
    GradleBuild build = new GradleBuild()

    @Tag("no-cross-version")
    def "plugin can be applied in settings file"() {
        given:
        settingsFile << """
            plugins {
                id("de.jjohannes.java-ecosystem-capabilities")
            }
        """
        buildFile << """
            plugins {
                id("java-library")
            }
            
            repositories.mavenCentral()
            
            configurations.all {
                resolutionStrategy.capabilitiesResolution {
                    withCapability("cglib:cglib") {
                        select("cglib:cglib:0")
                    }
                }
                resolutionStrategy.capabilitiesResolution {
                    withCapability("javax.mail:mail") {
                       select("com.sun.mail:jakarta.mail:0")
                    }
                }
                resolutionStrategy.capabilitiesResolution {
                    withCapability("javax.ws.rs:jsr311-api") {
                        select("org.jboss.resteasy:jaxrs-api:0")
                    }
                }
                resolutionStrategy.capabilitiesResolution {
                    withCapability("jakarta.servlet:jakarta.servlet-api") {
                        select("jakarta.servlet:jakarta.servlet-api:0")
                    }
                }
            }
            
            dependencies {
                implementation("cglib:cglib-nodep:3.2.10")
                implementation("cglib:cglib:3.2.10")
                implementation("com.sun.mail:jakarta.mail:1.6.7")
                implementation("com.sun.mail:mailapi:1.6.7")
                implementation("jakarta.servlet:jakarta.servlet-api:4.0.4")
                implementation("org.apache.tomcat:tomcat-servlet-api:10.0.18")
            }
        """

        expect:
        dependencies().output.contains '''
            compileClasspath - Compile classpath for source set 'main'.
            +--- cglib:cglib-nodep:3.2.10 -> cglib:cglib:3.2.10
            |    +--- org.ow2.asm:asm:7.0
            |    \\--- org.apache.ant:ant:1.10.3
            |         \\--- org.apache.ant:ant-launcher:1.10.3
            +--- cglib:cglib:3.2.10 (*)
            +--- com.sun.mail:jakarta.mail:1.6.7
            |    \\--- com.sun.activation:jakarta.activation:1.2.1
            +--- com.sun.mail:mailapi:1.6.7 -> com.sun.mail:jakarta.mail:1.6.7 (*)
            +--- jakarta.servlet:jakarta.servlet-api:4.0.4
            \\--- org.apache.tomcat:tomcat-servlet-api:10.0.18 -> jakarta.servlet:jakarta.servlet-api:4.0.4
        '''.stripIndent()
    }
}