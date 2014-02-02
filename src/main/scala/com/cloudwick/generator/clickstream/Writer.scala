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

import java.io.{File, BufferedWriter, FileWriter}
import java.lang.RuntimeException
import org.apache.avro.{file, Schema}
import org.apache.avro.io.DatumWriter
import org.apache.avro.generic.{GenericDatumReader, GenericData, GenericDatumWriter, GenericRecord}
import org.apache.avro.file.{DataFileReader, DataFileWriter}
import org.apache.avro.util.Utf8

/**
 * Formats event and writes out to specified destination, where destinations
 * could be stdout, file (csv, tsv, avro)
 * @author ashrith 
 */
class Writer {
  val schemaDesc ="""
    |{
    | "type":"record",
    | "name":"DAILY_CLICKSTREAM",
    | "fields":[
    |   {"name":"AUTH_ID","type":["long","null"]},
    |   {"name":"CLICKSTREAM", "type":
    |     {"type":"record",
    |      "name":"T",
    |      "fields":[
    |        {"name":"Visitor_Id","type":["long","null"]},
    |        {"name":"HitTimeStamp","type":["string","null"]},
    |        {"name":"Screen_Name","type":["string","null"]},
    |        {"name":"VISIT_START_TIME_GMT","type":["string","null"]},
    |        {"name":"VISIT_PAGE_NUM","type":["int","null"]},
    |        {"name":"EVENT_LIST","type":["string","null"]},
    |        {"name":"CHANNEL","type":["string","null"]},
    |        {"name":"PAGENAME","type":["string","null"]},
    |        {"name":"PROP49","type":["string","null"]},
    |        {"name":"PROP07","type":["string","null"]}
    |       ]
    |     }
    |   }
    | ]
    |}
    """.stripMargin

  def writeToTextFile(dest: FileWriter, event: Event, formatter: String) = {
    try {
      val bufferedWriter = new BufferedWriter(dest)
      bufferedWriter.write(formatEventToString(event, formatter))
      bufferedWriter.flush()
    } catch {
      case e: Exception => throw new RuntimeException("Problem writing to file, reason: " + e.printStackTrace())
    }
  }

  def initializeAvroFile(dest: File): DataFileWriter[GenericRecord] = {
    val schema = new Schema.Parser().parse(schemaDesc)
    val writer:DatumWriter[GenericRecord] = new GenericDatumWriter[GenericRecord](schema)
    val dataFileWriter:DataFileWriter[GenericRecord] = new file.DataFileWriter[GenericRecord](writer)
    dataFileWriter.create(schema, dest)
  }

  def writeEventAsAvro(dataFileWriter: DataFileWriter[GenericRecord], event: Event) = {
    try {
      val schema = new Schema.Parser().parse(schemaDesc)
      val datum = new GenericData.Record(schema)
      datum.put("AUTH_ID", event.authId)
      val csDatum = new GenericData.Record(schema.getField("CLICKSTREAM").schema())
      // val t = new GenericData.Array[GenericRecord](1, null)
      csDatum.put("Visitor_Id", event.visitorId)
      csDatum.put("HitTimeStamp", new Utf8(event.hitTimeStamp))
      csDatum.put("Screen_Name", new Utf8(event.screenName))
      csDatum.put("VISIT_START_TIME_GMT", new Utf8(event.visitStartTimeGMT))
      csDatum.put("VISIT_PAGE_NUM", event.visitPageNum)
      csDatum.put("EVENT_LIST", new Utf8(event.eventList))
      csDatum.put("CHANNEL", new Utf8(event.channel))
      csDatum.put("PAGENAME", new Utf8(event.pageName))
      csDatum.put("PROP49", new Utf8(event.prop49))
      csDatum.put("PROP07", new Utf8(event.prop07))
      // t.add(tDatum)
      datum.put("CLICKSTREAM", csDatum)
      dataFileWriter.append(datum)
    } catch {
      case e: Exception => throw new RuntimeException("Problem writing to file as avro event, reason: " + e.printStackTrace())
    }
  }

  def closeAvroFile(dataFileWriter: DataFileWriter[GenericRecord]) = {
    if (dataFileWriter != null) {
      println("Closing avro file")
      dataFileWriter.close()
    }
  }

  def writeToConsole(event: Event, formatter: String) = {
    printf(formatEventToString(event, formatter))
  }

  def formatEventToString(event: Event, formatter: String) = formatter match {
    case "csv" => event.authId + "," + event.visitorId + "," + event.visitPageNum + "," + event.visitStartTimeGMT +
      "," + event.eventList + "," + event.pageName + "," + event.channel + "," + event.screenName + "," +
      event.hitTimeStamp + "," + event.prop49 + "," + event.prop07 + "\n"
    case "tsv" => event.authId + "\t" + event.visitorId + "\t" + event.visitPageNum + "\t" + event.visitStartTimeGMT +
      "\t" + event.eventList + "\t" + event.pageName + "\t" + event.channel + "\t" + event.screenName + ",\t" +
      event.hitTimeStamp + "\t" + event.prop49 + "\t" + event.prop07 + "\n"
    case _ => throw new RuntimeException(s"Unsupported format for writing to text files!: ${formatter}")
  }

  def readFromAvro(filePath: String) = {
    val schema = new Schema.Parser().parse(schemaDesc)
    val reader = new GenericDatumReader[GenericRecord](schema)
    val dataFileReader = new DataFileReader[GenericRecord](new File(filePath), reader)
    var record: GenericRecord = null
    while (dataFileReader.hasNext) {
      record = dataFileReader.next(record)
      println(record.get("AUTH_ID").toString)
      println(record.get("CLICKSTREAM").toString)
    }

  }
}
