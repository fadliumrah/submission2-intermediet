package com.fadli.pleaselulus.viewModelTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fadli.pleaselulus.data.ResultData
import com.fadli.pleaselulus.data.StoriesUserResponse
import com.fadli.pleaselulus.dataTest.DispacherRule
import com.fadli.pleaselulus.dataTest.failedLocationTest
import com.fadli.pleaselulus.dataTest.successLocationTest
import com.fadli.pleaselulus.dataTest.getOrAwaitValue
import com.fadli.pleaselulus.model.MapsV
import com.fadli.pleaselulus.repository.MapsR
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class MapsVTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispacherRule = DispacherRule()

    @Mock
    private lateinit var mapsR: MapsR
    private lateinit var mapsV: MapsV

    @Before
    fun setUp(){
        mapsV = MapsV(mapsR)
    }

    @Test
    fun `AllStories with lat lon return Success`(){
        val dummy = successLocationTest()
        val expected = MutableLiveData<ResultData<StoriesUserResponse>>()
        expected.value = ResultData.Success(dummy)

        `when`(mapsR.getStoriesLocation()).thenReturn(expected)

        val actualValues = mapsV.getStoriesLocation().getOrAwaitValue()
        Mockito.verify(mapsR).getStoriesLocation()

        assertNotNull(actualValues)
        assertTrue(actualValues is ResultData.Success)
        assertNotNull(actualValues.data?.listDataStory?.get(0)?.lat)
        assertNotNull(actualValues.data?.listDataStory?.get(0)?.lon)
        assertEquals((expected.value as ResultData.Success).data?.listDataStory?.size, actualValues.data?.listDataStory?.size)
        assertEquals((expected.value as ResultData.Success).data?.listDataStory?.get(0), actualValues.data?.listDataStory?.get(0))

    }

    @Test
    fun `AllStories with lat lon return Failed`(){
        val dummy = failedLocationTest()
        val expected = MutableLiveData<ResultData<StoriesUserResponse>>()
        expected.value = ResultData.Error(data = dummy, code = null)

        `when`(mapsR.getStoriesLocation()).thenReturn(expected)

        val actualValues = mapsV.getStoriesLocation().getOrAwaitValue()
        Mockito.verify(mapsR).getStoriesLocation()

        assertNotNull(actualValues)
        assertTrue(actualValues is ResultData.Error)
        Assert.assertNull(actualValues.data?.listDataStory?.get(0)?.lat)
        Assert.assertNull(actualValues.data?.listDataStory?.get(0)?.lon)
    }
}