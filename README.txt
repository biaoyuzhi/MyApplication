该项目主要用于理解NDK开发流程和JNI文件的编写
主要参考网上博客https://blog.csdn.net/allen315410/article/details/42456661
这里采用了CMake + Gradle的配置方法，替代了网上的ndk-build + Android.mk的方式。

前期准备：
Tools->SDK Manager->SDK Tools选项卡，勾选LLDB，NDK，CMake安装。
File->Project Structure...->SDK Location选项卡，指定Android SDK location，Android NDK location，JDK location。

本项目使用的主要配置如下：
Android Studio3.5.1，JDK1.8，gradle5.4.1，ndk20.0.5594570
本APP项目主要包括的内容：
MyApplication
    |
    |- app
    |    |
    |    |- src
    |    |    |-  main
    |    |           |-java
    |    |           |   |-MainActivity：页面启动类、native方法的声明、动态库的引入与调用。
    |    |           |
    |    |           |-jni
    |    |           |   |-com_example_myapplication_MainActivity.h：在src/main/java路径下使用cmd执行命令：javah com.example.myapplication.MainActivity生成的头文件
    |    |           |   |-convert.c：手动实现的JNI逻辑文件，核心文件，是java和C语言文件的桥梁
    |    |           |   |-*.c和*.h：直接来自LAME压缩包里的C文件，其中的ieee754_float32_t数据类型全部改为float类型
    |    |           |
    |    |           |-res
    |    |           |   |-layout：这里制作2个按钮和输入框，指定按钮的触发事件和输入框的id号。
    |    |           |
    |    |           |-AndroidManifest.xml：添加2条权限。
    |    |
    |    |-  build.gradle：配置CMake工具和CMakeLists.txt路径
    |    |-  CMakeLists.txt：指定C语言文件的路径和最终生成so文件的名称
    |

点击Build->Rebuild Project，会在路径app/build/intermediates/cmake/debug/obj下生成适用不同内核的.so文件，并且该so文件已经被打包在app-debug.apk文件中了。