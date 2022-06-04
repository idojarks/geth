package com.example.geth

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.geth.ui.EtherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Accounts(
    private val ether: Ether
) : PagingSource<Int, String>() {
    override fun getRefreshKey(
        state: PagingState<Int, String>
    ): Int? {
        TODO(
            "Not yet implemented"
        )
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, String> {
        val curPage = params.key ?: 1
        val accounts = ether.getAccounts()

        val nextPage = if (curPage == 1) {
            if (accounts.size <= params.loadSize/3) {
                null
            }
            else {
                curPage.plus(1)
            }
        }
        else {
            if (accounts.size <= params.loadSize) {
                null
            }
            else {
                curPage.plus(1)
            }
        }

        return if (accounts.isEmpty()) {
            LoadResult.Error(
                Exception("accounts empty.")
            )
        }
        else {
            LoadResult.Page(
                data = accounts,
                prevKey = if (curPage == 1) null else curPage - 1,
                nextKey = nextPage
            )
        }
    }


}