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

import com.typesafe.config.{ConfigException, ConfigFactory}

/**
 * Loads configuration settings
 * @author ashrith 
 */
class ConfigParser {
  def loadSettings: Settings = {
    try {
      // load the generator.conf file
      val config = ConfigFactory.load("generator")
      val extractedConfig = config.getConfig("clickstream.generator")
      // validate the configuration against reference configuration file
      config.checkValid(ConfigFactory.defaultReference(), "clickstream.generator")
      new Settings(extractedConfig)
    } catch {
      case e: ConfigException => throw new RuntimeException(s"Configuration validation failed!: $e")
    }
  }

  def printSystemProperties() {
    val p = System.getProperties
    val keys = p.keys

    while (keys.hasMoreElements) {
      val k = keys.nextElement
      val v = p.get(k)
      println(k + ": " + v)
    }
  }
}
