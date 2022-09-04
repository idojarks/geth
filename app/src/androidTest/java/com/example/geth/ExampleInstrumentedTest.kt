package com.example.geth

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.geth.data.EtherAccount
import com.example.geth.data.EtherContract
import com.example.geth.service.account.FileAccountRepository
import com.example.geth.service.account.add
import com.example.geth.service.account.delete
import com.example.geth.service.contract.ContractFileRepository
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    companion object {
        lateinit var contractRepo: ContractFileRepository
        lateinit var accountRepo: FileAccountRepository

        @BeforeClass
        @JvmStatic
        fun setUp() {
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext

            contractRepo = ContractFileRepository(
                context = appContext,
                filename = "contracts",
                showToast = false,
            )

            accountRepo = FileAccountRepository(appContext, "accountsTable")
        }

        @AfterClass
        fun tearDown() {

        }
    }

    @Before
    fun prepareTest() {
        contractRepo.deleteAll()
    }

    @After
    fun cleanupTest() {
        contractRepo.deleteAll()
    }

    @Test
    @Ignore("test contract now")
    fun useAccountRepository() {
        assertEquals(0, accountRepo.getAccounts().size)

        accountRepo add EtherAccount("john", "0x00", "0x00")
        accountRepo delete EtherAccount("john", "0x00", "0x00")

        assertEquals(0, accountRepo.getAccounts().size)
    }

    @Test
    @Ignore("test update now")
    fun runContractRepository() {
        val contract = EtherContract(
            name = "firstContract",
            address = "0xtest",
            isDefault = true,
        )

        assertTrue(contractRepo.add(contract))
        assertFalse(contractRepo.add(contract))
        assertTrue(contractRepo.delete(contract))
        assertFalse(contractRepo.delete(contract))
    }

    @Test
    @Ignore("ignore")
    fun updateContract() {
        val old = EtherContract(
            name = "firstContract",
            address = "0xtest",
            isDefault = true,
        )
        assertTrue(contractRepo.add(old))

        val new = EtherContract(
            name = "second",
            address = "0xsecond",
            isDefault = false,
        )
        assertTrue(contractRepo.replace(old, new))

        contractRepo.reload()

        contractRepo.contracts.forEach {
            assertEquals(it.name, "second")
        }
    }

    @Test
    fun setDefault() {
        val contract = EtherContract(
            name = "first",
            address = "0xfirst",
            isDefault = false,
        )
        assertTrue(contractRepo.add(contract))

        val otherContract = EtherContract(
            name = "second",
            address = "0xsecond",
            isDefault = true,
        )
        assertTrue(contractRepo.add(otherContract))

        assertTrue(contractRepo.setDefault(contract))

        assertEquals(contract.address, contractRepo.getDefault()?.address)
        assertEquals(0, contractRepo.contracts.indexOfFirst {
            it.isDefault
        })

        assertEquals(otherContract.address, contractRepo.contracts.find {
            !it.isDefault
        }?.address)
        assertEquals(1, contractRepo.contracts.indexOfFirst {
            !it.isDefault
        })
    }
}