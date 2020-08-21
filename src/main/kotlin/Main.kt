import java.io.IOException
import com.ibm.icu.text.Transliterator
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        var zenkakuKana = "アアアアアア太郎"
        var result = Transliterator.getInstance("Fullwidth-Halfwidth").transliterate(zenkakuKana)
        //println(Transliterator.getAvailableIDs().toString())
        println(result)
    }
}