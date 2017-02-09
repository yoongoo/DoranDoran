package com.doraesol.dorandoran.social;

/**
 * Created by YOONGOO on 2017-02-09.
 */

public class Cmt {

    public static class Post {

        int id;
        String uploader;
        String contents;
        int likes_count;
        boolean is_liked;
        int comments_count;
        String created_at;
        String updated_at;
        String img_url;

        public Post(int id, String uploader, String contents, int likes_count, boolean is_liked, int comments_count, String created_at, String updated_at, String img_url) {
            this.id = id;
            this.uploader = uploader;
            this.contents = contents;
            this.likes_count = likes_count;
            this.is_liked = is_liked;
            this.comments_count = comments_count;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.img_url = img_url;
        }


        public int getId() {
            return id;
        }

        public String getUploader() {
            return uploader;
        }

        public String getContents() {
            return contents;
        }

        public int getLikes_count() {
            return likes_count;
        }

        public boolean is_liked() {
            return is_liked;
        }

        public int getComments_count() {
            return comments_count;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getImg_url() {
            return img_url;
        }


    }
}
