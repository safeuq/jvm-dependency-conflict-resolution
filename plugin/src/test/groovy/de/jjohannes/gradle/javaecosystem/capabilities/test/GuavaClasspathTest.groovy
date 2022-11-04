package de.jjohannes.gradle.javaecosystem.capabilities.test

import de.jjohannes.gradle.javaecosystem.capabilities.test.fixture.GradleBuild
import spock.lang.Specification
import spock.lang.Unroll

class GuavaClasspathTest extends Specification {

    @Delegate
    GradleBuild build = new GradleBuild()

    def setup() {
        // buildNextGuavaVersion() -- enable to test with https://github.com/google/guava/pull/3683
        settingsFile << 'rootProject.name = "test-project"'
    }

    String nextGuavaVersion = '32.0'

    static allGuavaVersions() {
        [
                // ['32.0'  , 'jre'    , [errorProne:  '2.7.1', j2objc: '1.3', jsr305: '3.0.2', checker: '3.12.0', failureaccess: '1.0.1']],
                // ['32.0'  , 'android', [errorProne:  '2.7.1', j2objc: '1.3', jsr305: '3.0.2', checker: '3.12.0', failureaccess: '1.0.1']],
                ['31.1'  , 'jre'    , [errorProne:  '2.11.0', j2objc: '1.3', jsr305: '3.0.2', checker: '3.12.0', failureaccess: '1.0.1']],
                ['31.1'  , 'android', [errorProne:  '2.11.0', j2objc: '1.3', jsr305: '3.0.2', checker: '3.12.0', failureaccess: '1.0.1']],
                ['31.0.1', 'jre'    , [errorProne:  '2.7.1', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker: '3.12.0', failureaccess: '1.0.1']],
                ['31.0.1', 'android', [errorProne:  '2.7.1', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker: '3.12.0', failureaccess: '1.0.1']],
                ['31.0'  , 'jre'    , [errorProne:  '2.7.1', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker: '3.12.0', failureaccess: '1.0.1']],
                ['31.0'  , 'android', [errorProne:  '2.7.1', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker: '3.12.0', failureaccess: '1.0.1']],
                ['30.1.1', 'jre'    , [errorProne:  '2.5.1', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker: '3.8.0', failureaccess: '1.0.1']],
                ['30.1.1', 'android', [errorProne:  '2.5.1', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker: '3.8.0', failureaccess: '1.0.1']],
                ['30.1'  , 'jre'    , [errorProne:  '2.3.4', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker: '3.5.0', failureaccess: '1.0.1']],
                ['30.1'  , 'android', [errorProne:  '2.3.4', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker: '3.5.0', failureaccess: '1.0.1']],
                ['30.0'  , 'jre'    , [errorProne:  '2.3.4', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker: '3.5.0', failureaccess: '1.0.1']],
                ['30.0'  , 'android', [errorProne:  '2.3.4', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker: '3.5.0', failureaccess: '1.0.1']],
                ['29.0'  , 'jre'    , [errorProne:  '2.3.4', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker: '2.11.1', failureaccess: '1.0.1']],
                ['29.0'  , 'android', [errorProne:  '2.3.4', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker: '2.11.1', failureaccess: '1.0.1']],
                ['28.2'  , 'jre'    , [errorProne:  '2.3.4', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker: '2.10.0', failureaccess: '1.0.1']],
                ['28.2'  , 'android', [errorProne:  '2.3.4', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker: '2.10.0', failureaccess: '1.0.1']],
                ['28.1'  , 'jre'    , [errorProne:  '2.3.2', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker:  '2.8.1', failureaccess: '1.0.1']],
                ['28.1'  , 'android', [errorProne:  '2.3.2', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker:  '2.8.1', failureaccess: '1.0.1']],
                ['28.0'  , 'jre'    , [errorProne:  '2.3.2', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker:  '2.8.1', failureaccess: '1.0.1']],
                ['28.0'  , 'android', [errorProne:  '2.3.2', j2objc: '1.3', jsr305: '3.0.2', checkerCompat:  '2.5.5', checker:  '2.8.1', failureaccess: '1.0.1']],
                ['27.1'  , 'jre'    , [errorProne:  '2.2.0', j2objc: '1.1', jsr305: '3.0.2', checkerCompat:  '2.5.2', checker:  '2.5.2', failureaccess: '1.0.1']],
                ['27.1'  , 'android', [errorProne:  '2.2.0', j2objc: '1.1', jsr305: '3.0.2', checkerCompat:  '2.5.2', checker:  '2.5.2', failureaccess: '1.0.1']],
                ['27.0.1', 'jre'    , [errorProne:  '2.2.0', j2objc: '1.1', jsr305: '3.0.2', checkerCompat:  '2.5.2', checker:  '2.5.2', failureaccess: '1.0.1']],
                ['27.0.1', 'android', [errorProne:  '2.2.0', j2objc: '1.1', jsr305: '3.0.2', checkerCompat:  '2.5.2', checker:  '2.5.2', failureaccess: '1.0.1']],
                ['27.0'  , 'jre'    , [errorProne:  '2.2.0', j2objc: '1.1', jsr305: '3.0.2', checkerCompat:  '2.5.2', checker:  '2.5.2', failureaccess: '1.0']],
                ['27.0'  , 'android', [errorProne:  '2.2.0', j2objc: '1.1', jsr305: '3.0.2', checkerCompat:  '2.5.2', checker:  '2.5.2', failureaccess: '1.0']],
                ['26.0'  , 'jre'    , [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '3.0.2', checkerCompat:  '2.5.2', checker:  '2.5.2']],
                ['26.0'  , 'android', [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '3.0.2', checkerCompat:  '2.5.2', checker:  '2.5.2']],
                ['25.1'  , 'jre'    , [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '3.0.2', checkerCompat:  '2.0.0', checker:  '2.0.0']],
                ['25.1'  , 'android', [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '3.0.2', checkerCompat:  '2.0.0', checker:  '2.0.0']],
                ['25.0'  , 'jre'    , [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '1.3.9', checkerCompat:  '2.0.0']],
                ['25.0'  , 'android', [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '1.3.9', checkerCompat:  '2.0.0']],
                ['24.1.1', 'jre'    , [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '1.3.9', checkerCompat:  '2.0.0']],
                ['24.1.1', 'android', [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '1.3.9', checkerCompat:  '2.0.0']],
                ['24.1'  , 'jre'    , [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '1.3.9', checkerCompat:  '2.0.0']],
                ['24.1'  , 'android', [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '1.3.9', checkerCompat:  '2.0.0']],
                ['24.0'  , 'jre'    , [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '1.3.9', checkerCompat:  '2.0.0']],
                ['24.0'  , 'android', [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '1.3.9', checkerCompat:  '2.0.0']],
                ['23.6.1', 'jre'    , [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '1.3.9', checkerCompat:  '2.0.0']],
                ['23.6.1', 'android', [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '1.3.9', checkerCompat:  '2.0.0']],
                ['23.6'  , 'jre'    , [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '1.3.9', checkerCompat:  '2.0.0']],
                ['23.6'  , 'android', [errorProne:  '2.1.3', j2objc: '1.1', jsr305: '1.3.9', checkerCompat:  '2.0.0']],
                ['23.5'  , 'jre'    , [errorProne: '2.0.18', j2objc: '1.1', jsr305: '1.3.9', checker:  '2.0.0']],
                ['23.5'  , 'android', [errorProne: '2.0.18', j2objc: '1.1', jsr305: '1.3.9', checker:  '2.0.0']],
                ['23.4'  , 'jre'    , [errorProne: '2.0.18', j2objc: '1.1', jsr305: '1.3.9']],
                ['23.4'  , 'android', [errorProne: '2.0.18', j2objc: '1.1', jsr305: '1.3.9']],
                ['23.3'  , 'jre'    , [errorProne: '2.0.18', j2objc: '1.1', jsr305: '1.3.9']],
                ['23.3'  , 'android', [errorProne: '2.0.18', j2objc: '1.1', jsr305: '1.3.9']],
                ['23.2'  , 'jre'    , [errorProne: '2.0.18', j2objc: '1.1', jsr305: '1.3.9']],
                ['23.2'  , 'android', [errorProne: '2.0.18', j2objc: '1.1', jsr305: '1.3.9']],
                ['23.1'  , 'jre'    , [errorProne: '2.0.18', j2objc: '1.1', jsr305: '1.3.9']],
                ['23.1'  , 'android', [errorProne: '2.0.18', j2objc: '1.1', jsr305: '1.3.9']],
                ['23.0'  , ''       , [errorProne: '2.0.18', j2objc: '1.1', jsr305: '1.3.9']],
                ['23.0'  , 'android', [errorProne: '2.0.18', j2objc: '1.1', jsr305: '1.3.9']],
                ['22.0'  , ''       , [errorProne: '2.0.18', j2objc: '1.1', jsr305: '1.3.9']],
                ['22.0'  , 'android', [errorProne: '2.0.18', j2objc: '1.1', jsr305: '1.3.9']],
                ['21.0'  , ''       , [:]],
                ['20.0'  , ''       , [:]],
                ['19.0'  , ''       , [:]],
                ['18.0'  , ''       , [:]],
                ['17.0'  , ''       , [:]],
                ['16.0.1', ''       , [:]],
                ['16.0'  , ''       , [:]],
                ['15.0'  , ''       , [:]],
                ['14.0.1', ''       , [:]],
                ['14.0'  , ''       , [:]],
                ['13.0.1', ''       , [:]],
                ['12.0.1', ''       , [jsr305: '1.3.9']],
                ['12.0'  , ''       , [jsr305: '1.3.9']],
                ['11.0.2', ''       , [jsr305: '1.3.9']],
                ['11.0.1', ''       , [jsr305: '1.3.9']],
                ['11.0'  , ''       , [jsr305: '1.3.9']],
                ['10.0.1', ''       , [jsr305: '1.3.9']],
                ['10.0'  , ''       , [jsr305: '1.3.9']]
        ]
    }

    static allGuavaCombinations(boolean useVersionForEnv) {
        def result = []
        allGuavaVersions().each {
            int majorGuavaVersion = it[0].substring(0, 2) as Integer
            if (useVersionForEnv || majorGuavaVersion < 31) {
                result.add([it[0], it[1], it[2], 'android', 'compileClasspath'])
                result.add([it[0], it[1], it[2], 'android', 'runtimeClasspath'])
                result.add([it[0], it[1], it[2], 'standard-jvm', 'compileClasspath'])
                result.add([it[0], it[1], it[2], 'standard-jvm', 'runtimeClasspath'])
            }
        }
        return result
    }

    @Unroll
    def "has correct classpath for Guava selected by target environment version #guavaVersion-#versionSuffix, #jvmEnv, #classpath"() {
        given:
        buildFile << """
            import org.gradle.api.attributes.java.TargetJvmEnvironment

            plugins {
                id("java-library")
                id("de.jjohannes.java-ecosystem-capabilities")
            }

            repositories {
                mavenCentral()
                ${guavaVersion == nextGuavaVersion? 'mavenLocal()' : ''}
            }

            configurations.compileClasspath {
                attributes.attribute(TargetJvmEnvironment.TARGET_JVM_ENVIRONMENT_ATTRIBUTE, objects.named("$jvmEnv"))
            }
            configurations.runtimeClasspath {
                attributes.attribute(TargetJvmEnvironment.TARGET_JVM_ENVIRONMENT_ATTRIBUTE, objects.named("$jvmEnv"))
            }

            dependencies {
                api("com.google.collections:google-collections:1.0")
                api("com.google.guava:listenablefuture:1.0")
                api("com.google.guava:guava:$guavaVersion${versionSuffix ? '-' : ''}$versionSuffix")
            }

            tasks.register("printJars") {
                doLast {
                    configurations.${classpath}.files.forEach { println(it.name) }
                }
            }
        """

        expect:
        expectedClasspath(guavaVersion, jvmEnv, classpath, dependencyVersions) == printJars().output.split('\n') as Set

        where:
        [guavaVersion, versionSuffix, dependencyVersions, jvmEnv, classpath] << allGuavaCombinations(true)
    }

    Set<String> expectedClasspath(String guavaVersion, String jvmEnv, String classpath, Map<String, String> dependencyVersions) {
        int majorGuavaVersion = guavaVersion.substring(0, 2) as Integer
        String jarSuffix = majorGuavaVersion < 22 ? '' : jvmEnv == 'android' ? 'android' : (guavaVersion == '22.0' || guavaVersion == '23.0') ? '' : 'jre'
        Set<String> result = ["guava-${guavaVersion}${jarSuffix? '-' : ''}${jarSuffix}.jar"]
        if (dependencyVersions.failureaccess) {
            result += "failureaccess-${dependencyVersions.failureaccess}.jar"
        }
        if (classpath == 'compileClasspath' || guavaVersion == nextGuavaVersion) { // Guava itself is planing to be more conservative with reducing the runtime classpath, so 'nextGuavaVersion' has more entries right now
            if (classpath == 'compileClasspath' && dependencyVersions.j2objc) {
                result += "j2objc-annotations-${dependencyVersions.j2objc}.jar"
            }
            if (dependencyVersions.jsr305) {
                result += "jsr305-${dependencyVersions.jsr305}.jar"
            }
            if (dependencyVersions.errorProne) {
                result += "error_prone_annotations-${dependencyVersions.errorProne}.jar"
            }
            if (dependencyVersions.checker && dependencyVersions.checkerCompat) {
                if (jvmEnv == 'android') {
                    result += "checker-compat-qual-${dependencyVersions.checkerCompat}.jar"
                    if (majorGuavaVersion > 30) {
                        result += "checker-qual-${dependencyVersions.checker}.jar"
                    }
                } else {
                    result += "checker-qual-${dependencyVersions.checker}.jar"
                }
            } else if (dependencyVersions.checker) {
                result += "checker-qual-${dependencyVersions.checker}.jar"
            } else if (dependencyVersions.checkerCompat) {
                result += "checker-compat-qual-${dependencyVersions.checkerCompat}.jar"
            }
        }
        return result
    }

    void buildNextGuavaVersion() {
        def guavaDir = new File('build/guava')
        if (!guavaDir.exists()) {
            print "git clone --depth 1 https://github.com/jjohannes/guava.git -b gradle-module-metadata".execute(null, guavaDir.parentFile).text
            print "util/set_version.sh $nextGuavaVersion".execute(null, guavaDir).text
            print "mvn clean install -DskipTests".execute(null, guavaDir).text
            print "mvn clean install -DskipTests".execute(null, new File(guavaDir, 'android')).text
        }
    }
}