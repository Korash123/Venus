package com.example.Venus.contants;

/*
    @created 10/06/2024 2:27 PM
    @project users
    @author biplaw.chaudhary
*/
public class URL_CONSTANTS {

    public static final String API_VERSION_V1 = "/api/v1";

    private URL_CONSTANTS(){

    }

    //------------- COMMON ---------------
    public static class COMMON{
        public static final String SAVE= "/save";
        public static final String GET_ALL= "/get/all";
        public static final String GET_BY_ID= "/getById";
        public static final String GET_BY_FIELD= "/get/{field}";
        public static final String UPDATE= "/update/{id}";
        public static final String GETBYID="/get/{id}";
        public static final String REFRESH_TOKEN ="/refreshToken";
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";
        public static final String REGISTER = "/register";
        public static final String SUMMARY = "/summary";
        public static final String RESET_PASSWORD = "/validPassword";
        public static final String SET_PASSWORD = "/setPassword";
        public static final String FORGOT_PASSWORD = "/forgot-password";
        public static final String DELETE_BY_ID = "/delete/{id}";
        public static final String DELETEBYID = "/delete";

    }
    public static class UserRegistration {
        public static final String USER_BASE_URL = API_VERSION_V1 + "/user-reg";
    }
    public static class Role {
        public static final String USER_BASE_URL = API_VERSION_V1 + "/role";
    }

    public static class EventNews{
        public static final String EVENT_BASE_URL = API_VERSION_V1 + "/event-news";
    }

    public static class Master{
        public static final String USER_BASE_URL = API_VERSION_V1 + "/master";
        public static final String MENU_ACTION = "/menus/and/actions/";

    }
    public static class FacultyStaffs{
        public static final String USER_BASE_URL = API_VERSION_V1 + "/faculty-staffs";
    }

    public static class Admin{
        public static final String USER_BASE_URL = API_VERSION_V1 + "/admin";
        public static final String COMPLETE_EKYE_USER="/completed_ekye_users";
        public static final String GET_SINGLE_COMPLETE_EKYE_USER= "/singleCompleteEkyeUser/{userId}";
        public static final String GET_SINGLE_COMPLETE_EKYE_USER_RCLID= "/singleCompleteEkyeUser/rclId/{rclId}";
    }
    public static class ProfilePicture{
        public static final String USER_BASE_URL = API_VERSION_V1 + "/profile-picture";
    }
    public static class Blog{
        public static final String USER_BASE_URL = API_VERSION_V1 + "/blog";
    }

    public static class Feedback{
        public static final String USER_BASE_URL = API_VERSION_V1 + "/feedback";
    }


    public static class PopOverModel{
        public static final String POP_OVER_MODEL_URL = API_VERSION_V1 + "/pop-over-model";
    }

    public static class Enquiry{
        public static final String ENQUIRY_BASE_URL = API_VERSION_V1 + "/enquiry";
    }

    public static class Banners{
        public static final String BANNER_BASE_URL = API_VERSION_V1 + "/banner";
    }


}
