package com.example.muchaspeticiones

import java.io.File

class IOHelper {
    fun writefile(filename: String, text: String) {
        File(filename).writeText(text)
    }

    fun readfile(filename: String): String {
        return File(filename).readText(Charsets.UTF_8)
    }
}
