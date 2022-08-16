package com.example.geth.data

import com.example.geth.Contracts_Dragon721_sol_Dragon721

data class ArtworkToken(
    val index: Int = -1,
    val context: Contracts_Dragon721_sol_Dragon721.Artwork = Contracts_Dragon721_sol_Dragon721.Artwork("", "", ""),
) {
    open class LoadingTokenState {
        object Start : LoadingTokenState() {}
        object Uri : LoadingTokenState() {}
        object ImageUri : LoadingTokenState() {}
        object Done : LoadingTokenState() {}
    }
}
