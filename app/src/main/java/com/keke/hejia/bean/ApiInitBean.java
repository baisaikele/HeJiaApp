package com.keke.hejia.bean;

public class ApiInitBean {

    /**
     * version_info : {"version":"1.0.0","upgrade_type":0,"download_url":"itms-services://?action=download-manifest&url=https://kk-h5-cdn.ikeke.ltd/app/ios/1.0.0/DZTopNews.plist","desc":""}
     * review_type : 1
     * introduce_video_url :
     */

    private VersionInfoBean version_info;   //版本更新
    private int review_type;   //审核状态
    private String introduce_video_url;  //视频地址

    public VersionInfoBean getVersion_info() {
        return version_info;
    }

    public void setVersion_info(VersionInfoBean version_info) {
        this.version_info = version_info;
    }

    public int getReview_type() {
        return review_type;
    }

    public void setReview_type(int review_type) {
        this.review_type = review_type;
    }

    public String getIntroduce_video_url() {
        return introduce_video_url;
    }

    public void setIntroduce_video_url(String introduce_video_url) {
        this.introduce_video_url = introduce_video_url;
    }

    public static class VersionInfoBean {
        /**
         * version : 1.0.0
         * upgrade_type : 0
         * download_url : itms-services://?action=download-manifest&url=https://kk-h5-cdn.ikeke.ltd/app/ios/1.0.0/DZTopNews.plist
         * desc :
         */

        private String version;  //版本
        private int upgrade_type;   //更新状态
        private String download_url;  //下载地址
        private String desc;   //下载介绍

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getUpgrade_type() {
            return upgrade_type;
        }

        public void setUpgrade_type(int upgrade_type) {
            this.upgrade_type = upgrade_type;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
