/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.linecorp.bot.model.message;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonCreator;


import lombok.Value;

/**
 * Image message
 */
@Value
@JsonTypeName("image")
public class ImageMessage implements Message {
    private final String originalContentUrl;
    private final String previewImageUrl;
    
    @JsonCreator
    // Constructor which has only one argument needs Jackson Annotation.
    // see MessageJsonReconstructionTest for detail.
    public ImageMessage(final String imageUrl) {
        this.originalContentUrl = imageUrl;
        this.previewImageUrl = imageUrl;   
    }
    
    
    
}
