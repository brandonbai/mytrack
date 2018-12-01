fun main(args: Array<String>) {

    println("Hello World!")

    val a = 0x02
    val b = 13.1
    println(a + b)

    val c: Int = a
    println(a == c)
    println(a === c)

    val d: Byte = 1
    val e: Int = d.toInt()
    println(e)

    println(a.shl(2))

    val f = Array(3, { i -> (i * 2) })
    println(f[1])


    val text = """
    多行字符串
    多行字符串
    """.trimStart()
    println(text)

    val s = "hi"
    val str = "$s.length is ${s.length}"
    println(str)


}