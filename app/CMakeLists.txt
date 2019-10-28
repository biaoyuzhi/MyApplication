# Sets the minimum version of CMake required to build the native
# library. You should either keep the default value or only pass a
# value of 3.4.0 or lower.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds it for you.
# Gradle automatically packages shared libraries with your APK.
# 用于指定头文件的目录
#include_directories(src/main/jni/)
# 设置通配符
file(GLOB xxx "src/main/jni/*.c")
# 设置so文件名称和类型.
add_library(# Sets the name of the library.
        wuzh
        # Sets the library as a shared library.
        SHARED
        # Provides a relative path to your source file(s).
        # 引用上面设置的通配符
        ${xxx}
        )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because system libraries are included in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.
# 引用android系统的系统库
find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in the
# build script, prebuilt third-party libraries, or system libraries.

# 制定目标库.
target_link_libraries(# 链接所有的库，比如上面我们添加的第三方库，添加的本地log库
        # 通过add_library添加的直接写设置的名字
        wuzh
        # 链接本地NDK里面的库
        ${log-lib}
        )