/*
 * Copyright 2020 WinT 3794 (Manuel Diaz Rojo and Alexis Obed Garcia Hernandez)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.wint3794.ftc.pathfollower.io

import org.json.simple.JSONArray
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import org.wint3794.ftc.pathfollower.models.Path
import java.io.FileReader
import java.io.IOException
import java.net.URL

object PathReader {
    private val parser: JSONParser = JSONParser()

    fun readJSON(file: URL): Path? {
        var read: JSONArray? = null

        try {
            FileReader(file.file).use { reader -> read =
                parseObject(reader)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return PathProcessor.create(read)
    }

    @Throws(IOException::class, ParseException::class)
    private fun parseObject(reader: FileReader): JSONArray {
        val obj: Any = parser.parse(reader)
        return obj as JSONArray
    }
}