spotbugs 集成成功

## spotbugs 版本

```
classpath "com.github.spotbugs.snom:spotbugs-gradle-plugin:4.7.10"

```

## 对用使用spotbugs 插件的版本

```

spotbugs 'com.github.spotbugs:spotbugs:4.5.0'

```

## 使用方式

### 方式一

在 app 目录下 build.gradle 直接使用

定义 task 的几种方式

1. task 创建
2. tasks.reg

```
import com.github.spotbugs.snom.SpotBugsTask

apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'com.github.spotbugs'


android {

    compileSdkVersion 29
    buildToolsVersion "29.0.3"

    defaultConfig {
        applicationId "com.coding.events.bugs"
        minSdkVersion 21
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation 'androidx.appcompat:appcompat:1.3.0'
    implementation 'com.google.android.material:material:1.3.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    // 可用可不用，根据需求
    //spotbugs configurations.spotbugsPlugins.dependencies
    //spotbugsPlugins 'com.h3xstream.findsecbugs:findsecbugs-plugin:1.8.0'
}

def qualityConfigDir = "$project.rootDir/config/quality"
def reportsConfigDir = "$project.buildDir/reports"
spotbugs {
    toolVersion = '4.5.0'
    //忽略失败，如果检测到bug,task会执行失败，设置为true会让task继续执行
    ignoreFailures = false
    showStackTraces = true
    showProgress = false
    // 等级分为 min default max
    effort = "min"
    // 检测bug的等级 low  medium  high 等级越高检测越严重
    reportLevel = "medium"

    // 引入过滤
    //includeFilter

    // excludeFilter 排除过滤
    excludeFilter = file("$qualityConfigDir/spotbugs/android-exclude-filter.xml")

    maxHeapSize = '512m'
}

// 方式一：task创建，生成 spotbugsMain task
/*task spotbugsMain(type: SpotBugsTask) {
    dependsOn 'assembleDebug'
    group = "spotbugs"
    classes = files("${project.buildDir}/intermediates/javac") + files("${project.buildDir}/tmp/kotlin-classes")
    //classes = files("${project.buildDir}/intermediates/javac")

    println "-----$projectDir.absolutePath"

    reports {
        xml.enabled = false

        html.configure {
            required = true
            outputLocation = file("$reportsConfigDir/spotbugs/spotbugs.html")
            stylesheet = 'fancy-hist.xsl'
        }
    }
}*/


// 方式二：tasks.withType 创建，会生成
//tasks.withType(SpotBugsTask) {


// 方式三：tasks.create 创建，会生成 spotbugsMain task
//tasks.create('spotbugsMain',SpotBugsTask) {

// 方式三：tasks.register 创建，会生成 spotbugsMain task
tasks.register('spotbugsMain', SpotBugsTask) {
    dependsOn 'assembleDebug'
    group = "spotbugs"
    // 检测二进制文件路径
    classes = files("${project.buildDir}/intermediates/javac") + files("${project.buildDir}/tmp/kotlin-classes")

    // Only one report format is supported. Html is easier to read, so let's use that
    // (xml is the one that's enabled by default).
    reports {

        xml.enabled = false

        html.configure {
            required = true
            outputLocation = file("$reportsConfigDir/spotbugs/spotbugs.html")
            stylesheet = 'fancy-hist.xsl'
        }
    }
}


```

### 方式二

在 app 目录下 新建 spotbugs.gradle 进行集成

主要注意：在应用插件的地方需要设置

```
project.extensions.extraProperties.set('SpotBugsTask', SpotBugsTask)
```

具体使用

```

app

build.gradle

project.extensions.extraProperties.set('SpotBugsTask', SpotBugsTask)
apply plugin: 'com.github.spotbugs'
apply from: '../app/spotbugs.gradle'


spotbugs.gradle

dependencies {
    //spotbugs configurations.spotbugsPlugins.dependencies
    //spotbugsPlugins 'com.h3xstream.findsecbugs:findsecbugs-plugin:1.12.0'
}

def qualityConfigDir = "$project.rootDir/config/quality"
def reportsConfigDir = "$project.buildDir/reports"

spotbugs {
    toolVersion = '4.5.0'
    //忽略失败，如果检测到bug,task会执行失败，设置为true会让task继续执行
    ignoreFailures = false
    showStackTraces = true
    showProgress = false
    // 等级分为 min default max
    effort = "min"
    // 检测bug的等级 low  medium  high 等级越高检测越严重
    reportLevel = "medium"

    // 引入过滤
    //includeFilter

    // excludeFilter 排除过滤
    excludeFilter = new File("$qualityConfigDir/spotbugs/android-exclude-filter.xml")

    maxHeapSize = '512m'
}

task spotbugsMain(type: SpotBugsTask) {
    dependsOn 'assembleDebug'
    group = "spotbugs"
    // 检测二进制文件路径
    classes = files("${project.buildDir}/intermediates/javac") + files("${project.buildDir}/tmp/kotlin-classes")

    // Only one report format is supported. Html is easier to read, so let's use that
    // (xml is the one that's enabled by default).
    reports {
        xml.enabled = false

        html.configure {
            required = true
            outputLocation = file("$reportsConfigDir/spotbugs/spotbugs.html")
            stylesheet = 'fancy-hist.xsl'
        }
    }
}


```


## 参考

0. [官方文档](https://spotbugs.readthedocs.io/en/latest/gradle.html)
1. [report xml or html](https://github.com/spotbugs/spotbugs-gradle-plugin/issues/95)
2. [xml or html report](https://github.com/spotbugs/spotbugs-gradle-plugin/issues/235)
3. [demo use 重要](https://github.com/spotbugs/spotbugs-gradle-plugin/issues/90)
4. [demo use 参考](https://github.com/spotbugs/spotbugs-gradle-plugin/issues/426)
5. [spot gradle config other1](https://github.com/AEFeinstein/mtg-familiar/blob/master/mobile/build.gradle)
6. [spot gradle config other2](https://gitlab.com/fred.grott/droikotlinkit)
7. [spot gradle config other3](https://gist.github.com/mik9/fdde79052fef7f03c4325734701a39d7)
8. [将SpotBugs添加到我的项目](https://www.it1352.com/2267113.html)
9. [Android Studio 的最小工作 SpotBugs 设置](https://www.it1352.com/2593270.html)
10. [findbugs](https://github.com/vincentbrison/vb-android-app-quality/blob/master/config/quality.gradle)

