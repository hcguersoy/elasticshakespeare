/**
 * Copyright © 2016 H.-C.Guersoy (hcguersoy@gmail.com)
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
package de.github.hcguersoy.elasticshakespeare;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Documents of the type
 *  {
 * "_index" : "shakespeare",
 * "_type" : "line",
 * "_id" : "94933",
 * "_score" : 0.9703632,
 * "_source":{"lineid":94934,
 *            "playname":"Timon of Athens",
 *            "speechnumber":3,
 *            "line_umber":"3.3.28",
 *            "speaker":"SEMPRONIUS",
 *            "textentry":"Id such a courage to do him good. But now return,"}
 }
 *
 */
@Data
@Document(type = "line", indexName="shakespeare")
public class Citation {

    @Id
    public Integer id;

    public String linenumber;

    public String playname;

    public String speaker;

    public String speechnumber;

    public String textentry;

}
