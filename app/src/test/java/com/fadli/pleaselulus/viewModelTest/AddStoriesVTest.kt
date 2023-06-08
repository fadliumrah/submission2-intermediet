package com.fadli.pleaselulus.viewModelTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fadli.pleaselulus.dataTest.DispacherRule
import com.fadli.pleaselulus.dataTest.failedAddStoriesTest
import com.fadli.pleaselulus.dataTest.successAddStoriesTest
import com.fadli.pleaselulus.dataTest.getOrAwaitValue
import com.fadli.pleaselulus.data.ResultData
import com.fadli.pleaselulus.data.UploadStoriesResponse
import com.fadli.pleaselulus.model.AddStoryV
import com.fadli.pleaselulus.repository.AddStoriesR
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
import java.io.File


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddStoriesVTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispacherRule = DispacherRule()

    @Mock
    private lateinit var addStoriesR: AddStoriesR
    private lateinit var addStoryV: AddStoryV

    @Mock
    private lateinit var file: File
    companion object{
        private const val desc = "desc"
    }

    @Before
    fun setUp(){
        addStoryV = AddStoryV(addStoriesR)
    }

    @Test
    fun `AddStories return Result Success`(){
        val dummy = successAddStoriesTest()

        val expected = MutableLiveData<ResultData<UploadStoriesResponse>>()
        expected.value = ResultData.Success(dummy)

        `when`(addStoriesR.addStory(desc, file)).thenReturn(expected)

        val actualValues = addStoryV.addStory(desc, file).getOrAwaitValue()
        Mockito.verify(addStoriesR).addStory(desc, file)

        assertNotNull(actualValues)
        assertTrue(actualValues is ResultData.Success)
        assertEquals((expected.value as ResultData.Success).data, actualValues.data)
    }

    @Test
    fun `AddStories return Result Failed`(){
        val dummy = failedAddStoriesTest()

        val expected = MutableLiveData<ResultData<UploadStoriesResponse>>()
        expected.value = ResultData.Error(data = dummy, code = 400)

        `when`(addStoriesR.addStory(desc, file)).thenReturn(expected)

        val actualValues = addStoryV.addStory(desc, file).getOrAwaitValue()
        Mockito.verify(addStoriesR).addStory(desc, file)

        assertNotNull(actualValues)
        assertTrue(actualValues is ResultData.Error)
        assertEquals((expected.value as ResultData.Error).data?.message, actualValues.data?.message)
    }
}