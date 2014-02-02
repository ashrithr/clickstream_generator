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

/**
 * Case class for wrapping click stream event
 *
 * Format:
 * (long) as AUTH_ID, (To be restricted by a configurable list)
 * (long) as VISITOR_ID, (Incrementing globally)
 * (int) as VISIT_PAGE_NUM, (Same as sample file) => 1-30
 * (chararray) as VISIT_START_TIME_GMT, (Incrementing globally)
 * (chararray) as EVENT_LIST, (Same as sample file) => 20,104,111,125,141,142,103,111,127,128,209:16ef64496595,205:16ef6449659513
 * (chararray) as PAGENAME, (To be restricted by a configurable list)
 * (chararray) as CHANNEL, (To be restricted by a configurable list)
 * (chararray) as Screen_Name, (To be restricted by a configurable list)
 * (chararray) as HIT_TIMESTAMP, (Same format as sample file, globally incrementing)
 * (chararray) as EVAR04, (Same as sample file) !!! Not present in file
 * (chararray) as CID, (Same as sample file) !!! Nor present in file
 * (chararray) as DATE_TIME, (Same format as sample file, globally incrementing)
 * (chararray) as PROP49,(To be restricted by a configurable list)
 * (chararray) as PROP07; (To be restricted by a configurable list that follows a regex)
 *
 * @author ashrith 
 */
case class Event (authId: Long, visitorId: Long, visitPageNum: Int, visitStartTimeGMT: String, eventList: String,
                  pageName: String, channel: String, screenName: String, hitTimeStamp: String,
                  // cId: Long,
                  // dateTime: String,
                  prop49: String, prop07: String)
