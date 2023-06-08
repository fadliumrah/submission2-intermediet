package com.fadli.pleaselulus.viewModelTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.fadli.pleaselulus.adapter.StoriesAdapter
import com.fadli.pleaselulus.data.DataStory
import com.fadli.pleaselulus.dataTest.DispacherRule
import com.fadli.pleaselulus.dataTest.successAllStoriesTest
import com.fadli.pleaselulus.dataTest.getOrAwaitValue
import com.fadli.pleaselulus.model.StoriesV
import com.fadli.pleaselulus.repository.AllStoryR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
class AllStoriesVTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispacherRule = DispacherRule()

    @Mock
    private lateinit var allStoryR: AllStoryR
    private lateinit var allStoryViewModel: StoriesV

    @Before
    fun setUp(){
        allStoryViewModel = StoriesV(allStoryR)
    }

    @Test
    fun `AllStories return Data Stories`() = runTest {
        val dummy = successAllStoriesTest()
        val paging = PageResource.snapshot(dummy)

        val expercted = MutableLiveData<PagingData<DataStory>>()
        expercted.value = paging

        `when`(allStoryR.showAllStories()).thenReturn(expercted)

        val actualValues: PagingData<DataStory> = allStoryViewModel.showAllStories().getOrAwaitValue()
        Mockito.verify(allStoryR).showAllStories()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(actualValues)

        assertNotNull(differ.snapshot())
        assertEquals(dummy.size, differ.snapshot().size)
        assertEquals(dummy, differ.snapshot())
        assertEquals(dummy[0], differ.snapshot()[0])
    }

    @Test
    fun `AllStories return Data Empty`() = runTest {
        val dummy = successAllStoriesTest()
        val paging = PageResource.snapshot(dummy)

        val expected = MutableLiveData<PagingData<DataStory>>()
        expected.value = paging

        `when`(allStoryR.showAllStories()).thenReturn(expected)

        val actualValues = allStoryR.showAllStories().getOrAwaitValue()
        Mockito.verify(allStoryR).showAllStories()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(actualValues)
        assertNotNull(differ.snapshot().isEmpty())
        assertEquals(dummy, differ.snapshot())

    }



    private val noopListUpdateCallback = object : ListUpdateCallback{
        override fun onInserted(position: Int, count: Int) {
        }

        override fun onRemoved(position: Int, count: Int) {
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
        }

    }
}