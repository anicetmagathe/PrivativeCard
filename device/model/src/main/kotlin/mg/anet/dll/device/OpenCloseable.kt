package mg.anet.dll.device

import java.io.Closeable

interface OpenCloseable : Closeable {
    fun open()
}

inline fun <R, C : OpenCloseable> C.use(block: C.() -> R): R {
    try {
        open()
        return block()
    } finally {
        close()
    }
}