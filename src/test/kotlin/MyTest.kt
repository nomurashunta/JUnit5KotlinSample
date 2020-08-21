import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class MyTest(){
    @ParameterizedTest
    @ValueSource(ints = [0,1,2,3,4])
    fun testIntsValueSource(x:Int){
        println(x * 10)
    }

    enum class Color{
        Black, White, Red
    }

    companion object {
        fun getList(): List<Int>{
            return listOf<Int>(1, 2, 3)
        }
    }


    @ParameterizedTest
    @CsvSource("1, 1, 2", "2, 3, 5")
    fun sum(a: Int, b: Int, sum: Int) {
        assertThat(sum).isEqualTo(a + b)
    }

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @ParameterizedTest
    @EnumSource(Color::class)
    fun testEnum(color: Color){
        println(color)
    }
    var list = listOf<Int>(1, 2, 3)


    @Tag("tag1")
    @Test
    fun testTag1(){
        println("tag1 test")
    }

    @Tag("tag2")
    @Test
    fun testTag2(){
        println("tag2 test")
    }

    @ParameterizedTest
    @MethodSource("getList")
    fun testList(i: Int){
        println(i)
    }

    @Test
    fun testMockito(){
        val mockClass = mock(MockClass::class.java)
        `when`(mockClass.getRandomString()).thenReturn("ABCDE01234")
        println(MockClass().getRandomString())
        println(mockClass.getRandomString())
    }


    @MockK
    lateinit var mockKClass: MockClass

    @SpyK
    var spyKClass: MockClass = MockClass()

    @Test
    fun testMockK(){
        every { mockKClass.getRandomString() }.returns("01234ABCDE")
        println(mockKClass.getRandomString())
    }

    @Test
    fun testMockK2(){
        val myMock = mockk<MockClass>()
        every { myMock.getRandomString() }.returns("9999")
        println(myMock.getRandomString())
    }

    @Test
    fun testSpyK(){
        every { spyKClass.getRandomString() }.returns("SPY VALUE")
        println(spyKClass.getRandomString())
        verify {
            spyKClass.getRandomString()
        }
        confirmVerified(spyKClass)
    }

    @Test
    fun testJacoco(){
        //println(SampleClass().GetText())
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = ["AAA", "BBB"])
    fun testHasEvenLength(str: String?) {
        println(str)
    }
}

open class MockClass(){
    open fun getRandomString():String{
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val randomString = (1..10)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");
        return randomString
    }
}

