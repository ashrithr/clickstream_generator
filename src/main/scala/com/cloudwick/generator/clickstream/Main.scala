/**
 * Copyright 2013, Cloudwick, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloudwick.generator.clickstream

import java.io.{File, FileWriter}
import org.apache.avro.file.DataFileWriter
import org.apache.avro.generic.GenericRecord

/**
 * Starts the generator
 * @author ashrith 
 */
object Main extends App {
  val configParser = new ConfigParser()
  val settings = configParser.loadSettings
  val generator = new Generator(settings)
  val writer = new Writer
  var outputWriter: FileWriter = null
  var datafileWriter: DataFileWriter[GenericRecord] = null

  settings.output match {
    case "file" =>  {
      if (settings.format == "avro") {
        datafileWriter = writer.initializeAvroFile(new File(settings.path))
      } else {
        outputWriter = new FileWriter(settings.path)
      }
      try {
        (1L to settings.eventsCount).foreach { eventCount =>
          val fileFormatRegex = "tsv|csv".r
          settings.format match {
            case fileFormatRegex() => writer.writeToTextFile(outputWriter, generator.eventGenerate(eventCount), settings.format)
            case "avro" => writer.writeEventAsAvro(datafileWriter, generator.eventGenerate(eventCount))
          }
        }
      } catch {
        case e: Exception => e.printStackTrace
      } finally {
        if (outputWriter != null) {
          println("closing file")
          outputWriter.close()
        }
        writer.closeAvroFile(datafileWriter)
      }
      // REMOVE ME
      // if (settings.format == "avro") {
      //   writer.readFromAvro(settings.path)
      // }
    }
    case "stdout" => (1L to settings.eventsCount).foreach { eventCount =>
      writer.writeToConsole(generator.eventGenerate(eventCount), settings.format)
    }
    case _ => throw new RuntimeException("Invalid output format, supported formats: stdout, file")
  }
}
