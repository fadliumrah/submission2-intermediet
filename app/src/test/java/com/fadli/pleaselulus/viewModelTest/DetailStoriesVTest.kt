package com.fadli.pleaselulus.viewModelTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fadli.pleaselulus.data.DetailUserResponse
import com.fadli.pleaselulus.data.ResultData
import com.fadli.pleaselulus.dataTest.DispacherRule
import com.fadli.pleaselulus.dataTest.failedDetailTest
import com.fadli.pleaselulus.dataTest.successDetailTest
import com.fadli.pleaselulus.dataTest.getOrAwaitValue
import com.fadli.pleaselulus.model.DetailV
import com.fadli.pleaselulus.repository.DetailStoriesR
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailStoriesVTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispacherRule = DispacherRule()

    @Mock
    private lateinit var detailStoriesR: DetailStoriesR
    private lateinit var detailV: DetailV


    companion object{
        private const val EXIST_ID = "ID"
        private const val WRONG_ID = "WRONG ID"
    }

    @Before
    fun setUp(){
        detailV = DetailV(detailStoriesR)
    }

    @Test
    fun `Detail Stories return Success`(){
        val dummy = successDetailTest()
        val expected = MutableLiveData<ResultData<DetailUserResponse>>()
        expected.value = ResultData.Success(dummy)

        `when`(detailStoriesR.getDetailStories(EXIST_ID)).thenReturn(expected)
        val actualValues = detailV.getDetailStories(EXIST_ID).getOrAwaitValue()
        Mockito.verify(detailStoriesR).getDetailStories(EXIST_ID)

        assertNotNull(actualValues)
        assertTrue(actualValues is ResultData.Success)
        assertEquals((expected.value as ResultData.Success).data?.dataStory, actualValues.data?.dataStory)
    }

    @Test
    fun `Detail Stories return Failed`(){
        val dummy = failedDetailTest()
        val expected = MutableLiveData<ResultData<DetailUserResponse>>()
        expected.value = ResultData.Error(data = dummy, code = 401)

        `when`(detailStoriesR.getDetailStories(WRONG_ID)).thenReturn(expected)
        val actualValues = detailV.getDetailStories(WRONG_ID).getOrAwaitValue()
        Mockito.verify(detailStoriesR).getDetailStories(WRONG_ID)

        assertNotNull(actualValues)
        assertTrue(actualValues is ResultData.Error)
        assertEquals((expected.value as ResultData.Error).data?.message, actualValues.data?.message)
    }
}