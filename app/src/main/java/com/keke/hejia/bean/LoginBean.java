package com.keke.hejia.bean;

import java.util.List;

public class LoginBean {
    public static class DataBean {
        /**
         * user : {"phone":"13552475673","gender":1,"image":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epHBwgnDP5azxGjWC6Dmk3H8WvJbOicCdRDmzuqicqBic8VKLgt8a1f2JwmXlljZUvqW3yicfibAnQqPqg/132","nickname":"明子","age":"","birth_timestamp":"","user_id":"2187","is_bind_wx":0}
         * is_new : false
         * sign :
         * bind_data : {"bind_type":"","bind_id":"","nickname":"","family_type":"","family_users_origin":[{"nickname":"","age":"","image":"","gender":"","family_type":""}],"family_users_new":[{"nickname":"","age":"","image":"","gender":"","family_type":""}]}
         */

        private UserBean user;
        private boolean is_new;
        private String sign;
        private BindDataBean bind_data;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public boolean isIs_new() {
            return is_new;
        }

        public void setIs_new(boolean is_new) {
            this.is_new = is_new;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public BindDataBean getBind_data() {
            return bind_data;
        }

        public void setBind_data(BindDataBean bind_data) {
            this.bind_data = bind_data;
        }

        public static class UserBean {
            /**
             * phone : 13552475673
             * gender : 1
             * image : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epHBwgnDP5azxGjWC6Dmk3H8WvJbOicCdRDmzuqicqBic8VKLgt8a1f2JwmXlljZUvqW3yicfibAnQqPqg/132
             * nickname : 明子
             * age :
             * birth_timestamp :
             * user_id : 2187
             * is_bind_wx : 0
             */

            private String phone;
            private int gender;
            private String image;
            private String nickname;
            private String age;
            private String birth_timestamp;
            private String user_id;
            private int is_bind_wx;

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getBirth_timestamp() {
                return birth_timestamp;
            }

            public void setBirth_timestamp(String birth_timestamp) {
                this.birth_timestamp = birth_timestamp;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public int getIs_bind_wx() {
                return is_bind_wx;
            }

            public void setIs_bind_wx(int is_bind_wx) {
                this.is_bind_wx = is_bind_wx;
            }
        }

        public static class BindDataBean {
            /**
             * bind_type :
             * bind_id :
             * nickname :
             * family_type :
             * family_users_origin : [{"nickname":"","age":"","image":"","gender":"","family_type":""}]
             * family_users_new : [{"nickname":"","age":"","image":"","gender":"","family_type":""}]
             */

            private String bind_type;
            private String bind_id;
            private String nickname;
            private String family_type;
            private List<FamilyUsersOriginBean> family_users_origin;
            private List<FamilyUsersNewBean> family_users_new;

            public String getBind_type() {
                return bind_type;
            }

            public void setBind_type(String bind_type) {
                this.bind_type = bind_type;
            }

            public String getBind_id() {
                return bind_id;
            }

            public void setBind_id(String bind_id) {
                this.bind_id = bind_id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getFamily_type() {
                return family_type;
            }

            public void setFamily_type(String family_type) {
                this.family_type = family_type;
            }

            public List<FamilyUsersOriginBean> getFamily_users_origin() {
                return family_users_origin;
            }

            public void setFamily_users_origin(List<FamilyUsersOriginBean> family_users_origin) {
                this.family_users_origin = family_users_origin;
            }

            public List<FamilyUsersNewBean> getFamily_users_new() {
                return family_users_new;
            }

            public void setFamily_users_new(List<FamilyUsersNewBean> family_users_new) {
                this.family_users_new = family_users_new;
            }

            public static class FamilyUsersOriginBean {
                /**
                 * nickname :
                 * age :
                 * image :
                 * gender :
                 * family_type :
                 */

                private String nickname;
                private String age;
                private String image;
                private String gender;
                private String family_type;

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getAge() {
                    return age;
                }

                public void setAge(String age) {
                    this.age = age;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getGender() {
                    return gender;
                }

                public void setGender(String gender) {
                    this.gender = gender;
                }

                public String getFamily_type() {
                    return family_type;
                }

                public void setFamily_type(String family_type) {
                    this.family_type = family_type;
                }
            }

            public static class FamilyUsersNewBean {
                /**
                 * nickname :
                 * age :
                 * image :
                 * gender :
                 * family_type :
                 */

                private String nickname;
                private String age;
                private String image;
                private String gender;
                private String family_type;

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getAge() {
                    return age;
                }

                public void setAge(String age) {
                    this.age = age;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getGender() {
                    return gender;
                }

                public void setGender(String gender) {
                    this.gender = gender;
                }

                public String getFamily_type() {
                    return family_type;
                }

                public void setFamily_type(String family_type) {
                    this.family_type = family_type;
                }
            }
        }
    }
}
