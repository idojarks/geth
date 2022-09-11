package com.example.geth.data

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
) {
    open class LoadingTokenState {
        object Start : LoadingTokenState() {}
        object Uri : LoadingTokenState() {}
        object ImageUri : LoadingTokenState() {}
        object Done : LoadingTokenState() {}
    }
}
