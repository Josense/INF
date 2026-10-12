cmake_minimum_required(VERSION 3.22.1)
project(app_native)

# app/src/main/cpp -> app -> MyApp -> third_party/SDL
add_subdirectory(
        "${CMAKE_CURRENT_LIST_DIR}/../../../../third_party/SDL"
        "${CMAKE_CURRENT_BINARY_DIR}/SDL"
)

add_library(main SHARED
        main.cpp
)

target_link_libraries(main
        PRIVATE SDL3::SDL3
)