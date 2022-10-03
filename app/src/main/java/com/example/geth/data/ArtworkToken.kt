package com.example.geth.data

import androidx.lifecycle.MutableLiveData
import com.example.geth.Contracts_Dragon721_sol_Dragon721
import java.math.BigInteger

data class ArtworkToken(
    val index: Int = -1,
    val context: Contracts_Dragon721_sol_Dragon721.Artwork = Contracts_Dragon721_sol_Dragon721.Artwork(
        "",
        "",
        "",
        "",
        "", BigInteger.valueOf(0),
        "",
    ),
    var loadingState: LoadingState = LoadingState.Uri,
    var loadingUri: String = "",
    val loadingStateLiveData: MutableLiveData<Pair<LoadingState, String>> = MutableLiveData(Pair(LoadingState.Uri, "")),
) {
    open class LoadingState {
        object Start : LoadingState() {}
        object Uri : LoadingState() {}
        object ImageUri : LoadingState() {}
        object Done : LoadingState() {}
    }
}
