package com.rothanak.grades.presenter;

import com.rothanak.grades.ui.login.LoginInteractor;
import com.rothanak.grades.ui.login.LoginPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock private LoginPresenter.View view;
    @Mock private LoginInteractor loginInteractor;

    private LoginPresenter presenter;

    @Before
    public void init() {
        // todo: handle subscribeOn and observeOn
        when(loginInteractor.login(anyString(), anyString())).thenReturn(Observable.just(false));
        when(loginInteractor.login("GoodUser", "GoodPass")).thenReturn(Observable.just(true));
        presenter = new LoginPresenter(loginInteractor);
    }

    @Test
    public void submitLogin_WithGoodCredentials_Redirects() {
        when(view.getUsernameField()).thenReturn("GoodUser");
        when(view.getPasswordField()).thenReturn("GoodPass");
        presenter.attachView(view);

        presenter.performLogin();

        verify(view, times(1)).showLoading();
        verify(view, times(1)).gotoDashboard();
        verify(view, never()).showError();
    }

    @Test
    public void submitLogin_WithBadCredentials_ShowsError() {
        when(view.getUsernameField()).thenReturn("BadUser");
        when(view.getPasswordField()).thenReturn("SomePassword");
        presenter.attachView(view);

        presenter.performLogin();

        verify(view, times(1)).showLoading();
        verify(view, times(1)).showError();
        verify(view, never()).gotoDashboard();
    }

}