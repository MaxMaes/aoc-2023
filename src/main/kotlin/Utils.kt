import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/inputs/$name.txt").readLines()

/**
 * Reads the full text from the given input txt file.
 */
fun readFullInput(name: String) = Path("src/inputs/$name.txt").readText()