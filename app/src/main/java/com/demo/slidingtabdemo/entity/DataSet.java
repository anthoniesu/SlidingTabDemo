package com.demo.slidingtabdemo.entity;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by Anthonie Su on 2017/11/18.
 */

// Annotation by LoganSquare json library.

@JsonObject
public class DataSet {

    @JsonField(name = "Data")
    public List<Data> datas;

    @JsonObject
    public static class Data {
        @JsonField
        private String title;
        @JsonField
        private String content;
        @JsonField
        private String imageLink;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImageLink() {
            return imageLink;
        }

        public void setImageLink(String imageLink) {
            this.imageLink = imageLink;
        }
    }
}