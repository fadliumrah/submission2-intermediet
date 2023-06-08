package com.fadli.pleaselulus.viewModelTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fadli.pleaselulus.data.RegisterUserResponse
import com.fadli.pleaselulus.data.ResultData
import com.fadli.pleaselulus.dataTest.failedRegisterTest
import com.fadli.pleaselulus.dataTest.successRegisterTest
import com.fadli.pleaselulus.dataTest.getOrAwaitValue
import com.fadli.pleaselulus.model.RegisterV
import com.fadli.pleaselulus.repository.RegisterR
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RegisterVTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var registerR: RegisterR
    private lateinit var registerV: RegisterV
    companion object{
        private const val NAME = "Acenk090909"
        private const val EMAIL = "Acenkz090909@gmail.com"
        private const val EXIST_EMAIL = "acenkz123@gmail.com"
        private const val PASSWORD = "123456"
    }

    @Before
    fun setUp(){
        registerV = RegisterV(registerR)
    }

    @Test
    fun `Login return Result Success`(){
        val successResponse = successRegisterTest()

        val expected = MutableLiveData<ResultData<RegisterUserResponse>>()
        expected.value = ResultData.Success(successResponse)

        `when`(
            registerR.userRegister(
                NAME,
                EMAIL,
                PASSWORD
            )
        ).thenReturn(expected)

        val actualResponse = registerV.userRegister(
            NAME,
            EMAIL,
            PASSWORD
        ).getOrAwaitValue()

        Mockito.verify(registerR).userRegister(
            NAME,
            EMAIL,
            PASSWORD
        )
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is ResultData.Success)
        Assert.assertEquals(expected.value, actualResponse)

    }

    @Test
    fun `Login return Result Error`(){
        val failedResponse = failedRegisterTest()

        val expected = MutableLiveData<ResultData<RegisterUserResponse>>()
        expected.value = ResultData.Error(code = 400, data = failedResponse)

        `when`(
            registerR.userRegister(
                NAME,
                EXIST_EMAIL,
                PASSWORD
            )
        ).thenReturn(expected)

        val actualResponse = registerV.userRegister(
            NAME,
            EXIST_EMAIL,
            PASSWORD
        ).getOrAwaitValue()

        Mockito.verify(registerR).userRegister(
            NAME,
            EXIST_EMAIL,
            PASSWORD
        )
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is ResultData.Error)
        Assert.assertEquals((expected.value as ResultData.Error).data?.message, actualResponse.data?.message)

    }

}