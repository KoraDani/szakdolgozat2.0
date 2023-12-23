# Distributed under the OSI-approved BSD 3-Clause License.  See accompanying
# file Copyright.txt or https://cmake.org/licensing for details.

cmake_minimum_required(VERSION 3.5)

file(MAKE_DIRECTORY
  "C:/Users/Dani/esp/esp-idf/components/bootloader/subproject"
  "C:/cygwin64/home/Dani/szakdolgozat2.0/esp/blink/build/bootloader"
  "C:/cygwin64/home/Dani/szakdolgozat2.0/esp/blink/build/bootloader-prefix"
  "C:/cygwin64/home/Dani/szakdolgozat2.0/esp/blink/build/bootloader-prefix/tmp"
  "C:/cygwin64/home/Dani/szakdolgozat2.0/esp/blink/build/bootloader-prefix/src/bootloader-stamp"
  "C:/cygwin64/home/Dani/szakdolgozat2.0/esp/blink/build/bootloader-prefix/src"
  "C:/cygwin64/home/Dani/szakdolgozat2.0/esp/blink/build/bootloader-prefix/src/bootloader-stamp"
)

set(configSubDirs )
foreach(subDir IN LISTS configSubDirs)
    file(MAKE_DIRECTORY "C:/cygwin64/home/Dani/szakdolgozat2.0/esp/blink/build/bootloader-prefix/src/bootloader-stamp/${subDir}")
endforeach()
if(cfgdir)
  file(MAKE_DIRECTORY "C:/cygwin64/home/Dani/szakdolgozat2.0/esp/blink/build/bootloader-prefix/src/bootloader-stamp${cfgdir}") # cfgdir has leading slash
endif()
