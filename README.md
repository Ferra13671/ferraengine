### Ferra2DEngine

A utility that simplifies 2D game creation. 

### What is included in this utility?

* Creating and editing a window.
* Rendering of basic shapes.
* Its own texture system. Load image to texture on specified path and ready method for their rendering.
* Handling keyboard and mouse clicks.
* Easily output various information to the console using the logger.
* Good event handling system. Creating and activating your own events.
* Proprietary maths that is slightly faster than standard maths.
* A couple of other auxiliary utilities.

### How to add to your project:

#### Gradle
```Gradle
import org.gradle.internal.os.OperatingSystem

switch (OperatingSystem.current()) {
    case OperatingSystem.LINUX:
        project.ext.lwjglNatives = "natives-linux"
        def osArch = System.getProperty("os.arch")
        if (osArch.startsWith("arm") || osArch.startsWith("aarch64")) {
            project.ext.lwjglNatives += osArch.contains("64") || osArch.startsWith("armv8") ? "-arm64" : "-arm32"
        } else if  (osArch.startsWith("ppc")) {
            project.ext.lwjglNatives += "-ppc64le"
        } else if  (osArch.startsWith("riscv")) {
            project.ext.lwjglNatives += "-riscv64"
        }
        break
    case OperatingSystem.MAC_OS:
        project.ext.lwjglNatives = "natives-macos"
        break
    case OperatingSystem.WINDOWS:
        project.ext.lwjglNatives = "natives-windows"
        break
}

repositories {
    maven { url 'https://jitpack.io' }
    mavenCentral()
}

dependencies {
    implementation 'com.github.Ferra13671:ferraengine:v1.0-alpha'

    runtimeOnly "org.lwjgl:lwjgl::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-glfw::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-meow::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-openal::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-opengl::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-opengles::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-stb::$lwjglNatives"
}
```
