package pl.training.goodweather.commons

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MockitoExamples {

    @Mock
    lateinit var mockedList: MutableList<Int>

    @Before
    fun setup() {
        // MockitoAnnotations.openMocks(MockitoExamples::class) // alternatywa dla użycia RunWith
    }

    @Test
    fun examples() {
        // val mockedList = mock<MutableList<Int>>() // ręczne stworzenie mocka

        // Przykłady uczenia

        //`when`(mockedList[1]).thenReturn(1)
        //`when`(mockedList[anyInt()]).thenReturn(1)
        //doThrow(IllegalStateException::class.java).`when`(mockedList).clear()

        // Przykłady weryfikacji wywołań

        mockedList.add(1)
        verify(mockedList).add(1)
        // verify(mockedList)[anyInt()]
        // verify(mockedList, times(2))[anyInt()]
        // verify(mockedList, atMost(5))[anyInt()]
        // verifyNoInteractions(mockedList)
    }

}