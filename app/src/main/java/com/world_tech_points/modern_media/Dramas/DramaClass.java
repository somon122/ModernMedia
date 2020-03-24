package com.world_tech_points.modern_media.Dramas;

public class DramaClass {


    private String category;
    private String image_link;
    private String video_title;
    private String video_link;

    private String love_count;
    private String view_count;

    private String date_time;

    public DramaClass() {
    }

    public DramaClass(String category, String image_link, String video_title, String video_link, String love_count, String view_count, String date_time) {
        this.category = category;
        this.image_link = image_link;
        this.video_title = video_title;
        this.video_link = video_link;
        this.love_count = love_count;
        this.view_count = view_count;
        this.date_time = date_time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public String getLove_count() {
        return love_count;
    }

    public void setLove_count(String love_count) {
        this.love_count = love_count;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
