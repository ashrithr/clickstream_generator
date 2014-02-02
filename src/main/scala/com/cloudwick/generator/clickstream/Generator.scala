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

import scala.util.Random
import java.text.SimpleDateFormat
import br.com.six2six.bfgex.RegexGen

/**
 * Generates random click stream data
 * @author ashrith 
 */
class Generator(settings: Settings) {
  val random = Random
  val dateFormatter = new SimpleDateFormat("dd-MMM-yy HH:mm:ss")

  def eventGenerate(globalCounter: Long) = {
    new Event(
      settings.authId(random.nextInt(settings.authId.size)).toLong,
      globalCounter,
      random.nextInt(30),
      System.currentTimeMillis().toString,
      settings.eventList(random.nextInt(settings.eventList.size)),
      settings.pageName(random.nextInt(settings.pageName.size)),
      settings.channel(random.nextInt(settings.channel.size)),
      settings.screenName(random.nextInt(settings.screenName.size)),
      dateFormatter.format(new java.util.Date()),
      settings.prop49(random.nextInt(settings.prop49.size)),
      RegexGen.of(settings.prop07)
    )
  }
}
