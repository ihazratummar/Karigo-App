package android.print

import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import java.io.File

class PdfPrint(private val printAttributes: PrintAttributes) {
    fun print(printAdapter: PrintDocumentAdapter, path: File, fileName: String, callback: (File?) -> Unit) {
        try {
            val file = File(path, fileName)
            val pfd = ParcelFileDescriptor.open(
                file,
                ParcelFileDescriptor.MODE_READ_WRITE or ParcelFileDescriptor.MODE_CREATE or ParcelFileDescriptor.MODE_TRUNCATE
            )

            printAdapter.onLayout(
                null,
                printAttributes,
                null,
                object : PrintDocumentAdapter.LayoutResultCallback() {
                    override fun onLayoutFinished(info: PrintDocumentInfo?, changed: Boolean) {
                        try {
                            printAdapter.onWrite(
                                arrayOf(PageRange.ALL_PAGES),
                                pfd,
                                CancellationSignal(),
                                object : PrintDocumentAdapter.WriteResultCallback() {
                                    override fun onWriteFinished(pages: Array<out PageRange>?) {
                                        try { pfd.close() } catch (e: Exception) {}
                                        callback(file)
                                    }

                                    override fun onWriteFailed(error: CharSequence?) {
                                        try { pfd.close() } catch (e: Exception) {}
                                        callback(null)
                                    }
                                }
                            )
                        } catch (e: Exception) {
                            try { pfd.close() } catch (ex: Exception) {}
                            callback(null)
                        }
                    }

                    override fun onLayoutFailed(error: CharSequence?) {
                        try { pfd.close() } catch (e: Exception) {}
                        callback(null)
                    }
                },
                null
            )
        } catch (e: Exception) {
            callback(null)
        }
    }
}
