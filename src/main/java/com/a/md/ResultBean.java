package com.a.md;

import java.util.List;

public class ResultBean {
    private String date;//对应json数据中的date键
    private List<StoriesBean> stories; //对应json数据中的stories数组
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public List<StoriesBean> getStories() {
        return stories;
    }
    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }
    public static class StoriesBean{
        private List<String> images;
        private String type;
        private String id;
        private String title;

        public List<String> getImages() {
            return images;
        }
        public void setImages(List<String> images) {
            this.images = images;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "StoriesBean{" +
                    "images=" + images +
                    ", type='" + type + '\'' +
                    ", id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                '}';
    }
}
