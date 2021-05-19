package com.util

class Constants {
    public static final String IC = 'IC'
    public static final String RELEASE = 'RELEASE'
    public static final String MAVEN = 'maven'
    public static final String GRADLE = 'gradle'
    public static final STAGES = [STAGE_BUILD, STAGE_TEST, STAGE_JAR, STAGE_SONAR, STAGE_RUN, STAGE_TESTING, STAGE_NEXUS]
    public static final STAGES_MAVEN = [STAGE_BUILD, STAGE_TEST, STAGE_JAR, STAGE_SONAR, STAGE_RUNJAR, STAGE_REST, STAGE_NEXUSCI]
    public static final STAGES_GRADLE = [STAGE_BUILDANDTEST, STAGE_SONAR, STAGE_RUNJAR, STAGE_REST, STAGE_NEXUSCI]
    public static final STAGES_RELEASE = [STAGE_DOWNLOADNEXUS, STAGE_RUNDOWNLOADEDNEXUS, STAGE_REST, STAGE_NEXUSCD]
    public static final String SPLIT_SYMBOL = ';'
    public static final String STAGE_BUILD = 'build'
    public static final String STAGE_BUILDANDTEST = 'buildAndTest'
    public static final String STAGE_TEST = 'test'
    public static final String STAGE_JAR = 'jar'
    public static final String STAGE_RUNJAR = 'runJar'
    public static final String STAGE_SONAR = 'sonar'
    public static final String STAGE_RUN = 'run'
    public static final String STAGE_TESTING = 'testing'
    public static final String STAGE_NEXUS = 'nexus'
    public static final String STAGE_REST = 'rest'
    public static final String STAGE_NEXUSCI = 'nexusCI'
    public static final String STAGE_DOWNLOADNEXUS = 'downloadNexus'
    public static final String STAGE_RUNDOWNLOADEDNEXUS = 'runDownloadedNexus'
    public static final String STAGE_NEXUSCD = 'nexusCD'
}