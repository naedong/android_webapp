package kr.co.project.config;

import kr.co.project.api.RetrofitAPI;

public class RetrofitConfig {
    private static RetrofitConfig instance = null;
    //        private static com.maina.main_project.spring.api.initMyApi initMyApi;
    private static RetrofitAPI RetrofitAPI;
    //사용하고 있는 서버 BASE 주소
    private static final String baseUrl = "http://192.168.0.47:83";


}
