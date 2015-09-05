package com.rothanak.grades.injection.module;

import com.rothanak.grades.network.focus.DuvalAuthProvider;
import com.rothanak.grades.network.focus.api.DuvalAuthApi;
import com.rothanak.grades.network.focus.api.FocusAuthApi;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

@Module
public class FocusAuthModule {

    @Provides DuvalAuthApi provideDuvalApi(OkClient client) {
        return new RestAdapter.Builder()
                .setEndpoint("https://fs.duvalschools.org/adfs/ls")
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setClient(client)
                .build().create(DuvalAuthApi.class);
    }

    @Provides FocusAuthApi provideFocusApi(OkClient client) {
        return new RestAdapter.Builder()
                .setEndpoint("https://duval.focusschoolsoftware.com/focus")
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setClient(client)
                .build().create(FocusAuthApi.class);
    }

    @Provides DuvalAuthProvider provideAuthService(FocusAuthApi focusApi, DuvalAuthApi duvalApi) {
        return new DuvalAuthProvider(focusApi, duvalApi);
    }

}
