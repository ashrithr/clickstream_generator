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

import com.typesafe.config.{ConfigValue, ConfigObject, Config}
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._

/**
 * class to hold all settings
 * @author ashrith 
 */
class Settings(config: Config) {
  val output = config.getString("output.dest")
  val path = config.getString("output.path")
  val format = config.getString("output.format")
  val eventsCount = config.getLong("events.count")
  val authId = config.getStringList("properties.auth_id").toList
  val eventList = config.getStringList("properties.event_list").toList
  val pageName = config.getStringList("properties.page_name").toList
  val channel = config.getStringList("properties.channel").toList
  val screenName = config.getStringList("properties.screen_name").toList
  val prop49 = config.getStringList("properties.prop49").toList

  val test_weighted: Map[String, Int] = {
    val list: Iterable[ConfigObject] = config.getObjectList("weighted_properties.test").asScala
    (for {
      item: ConfigObject <- list
      entry: java.util.Map.Entry[String, ConfigValue] <- item.entrySet().asScala
      key = entry.getKey
      weight = entry.getValue.unwrapped().toString.toInt
    } yield (key, weight)).toMap
  }

  val prop07 = config.getString("regex_properties.prop07")
  val regex_r1 = config.getString("regex_properties.r1")
  val regex_r2 = config.getString("regex_properties.r2")
  val regex_r3 = config.getString("regex_properties.r3")
  val regex_r4 = config.getString("regex_properties.r4")
  val regex_r5 = config.getString("regex_properties.r5")
}
