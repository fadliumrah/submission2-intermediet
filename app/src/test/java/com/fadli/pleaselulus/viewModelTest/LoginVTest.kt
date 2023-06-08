package com.fadli.pleaselulus.viewModelTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fadli.pleaselulus.data.LoginStoriesResponse
import com.fadli.pleaselulus.data.ResultData
import com.fadli.pleaselulus.dataTest.failedLoginTest
import com.fadli.pleaselulus.dataTest.successLoginTest
import com.fadli.pleaselulus.dataTest.getOrAwaitValue
import com.fadli.pleaselulus.model.LoginV
import com.fadli.pleaselulus.repository.LoginR
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LoginVTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginR: LoginR
    private lateinit var loginV: LoginV
    companion object{
        private const val LOGIN_EMAIL = "acenkz123@gmail.com"
        private const val PASSWORD = "12345678"
        private const val WRONG_PASSWORD = "123456"
    }

    @Before
    fun setUp(){
        loginV = LoginV(loginR)
    }

    @Test
    fun `Login return Result Success`(){
        val successResponse = successLoginTest()

        val expected = MutableLiveData<ResultData<LoginStoriesResponse>>()
        expected.value = ResultData.Success(data = successResponse)

        `when`(loginR.userLogin(LOGIN_EMAIL, PASSWORD)).thenReturn(expected)

        val actualResponse = loginV.userLogin(LOGIN_EMAIL, PASSWORD).getOrAwaitValue()

        Mockito.verify(loginR).userLogin(LOGIN_EMAIL, PASSWORD)
        assertNotNull(actualResponse)
        assertTrue(actualResponse is ResultData.Success)
        assertEquals(expected.value, actualResponse)

    }

    @Test
    fun `Login Return Result Error`(){
        val errorResponse = failedLoginTest()

        val expected = MutableLiveData<ResultData<LoginStoriesResponse>>()
        expected.value = ResultData.Error(code = 401, data = errorResponse)

        `when`(loginR.userLogin(LOGIN_EMAIL, WRONG_PASSWORD)).thenReturn(expected)

        val actualResponse = loginV.userLogin(LOGIN_EMAIL, WRONG_PASSWORD).getOrAwaitValue()
        Mockito.verify(loginR).userLogin(LOGIN_EMAIL, WRONG_PASSWORD)

        assertNotNull(actualResponse)
        assertTrue(actualResponse is ResultData.Error)
        assertEquals((expected.value as ResultData.Error).data?.message, actualResponse.data?.message)

    }
}