package com.rothanak.grades.network.focus;

import com.rothanak.grades.data.course.Course;
import com.rothanak.grades.data.course.CourseSource;

import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

public class FocusCourseSource implements CourseSource {

    private final GetCoursesApi api;

    public FocusCourseSource(GetCoursesApi api) {
        this.api = api;
    }

    @Override public Observable<Course> fetchCourses(int year) {
        api.fetchCourses(year);
        // parse html
        // transform into model
        return null;
    }

    private interface GetCoursesApi {

        @FormUrlEncoded @POST("/Modules.php?modname=misc/Portal.php")
        Observable<Response> fetchCourses(@Field("side_syear") int year);

    }
}

