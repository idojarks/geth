package com.example.geth

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.geth.data.AccountRepository
import com.example.geth.data.add
import com.example.geth.data.delete
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAccountRepository() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val repo = AccountRepository(appContext, "accountsTable")
        assertEquals(0, repo.getAccounts().size)

        repo add EtherAccount("john", "0x00", "0x00")
        repo delete EtherAccount("john", "0x00", "0x00")

        assertEquals(0, repo.getAccounts().size)
    }
}