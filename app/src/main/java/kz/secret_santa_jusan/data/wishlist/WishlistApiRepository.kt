package kz.secret_santa_jusan.data.wishlist

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.wishlist.models.CreateWishlistModel

class WishlistApiRepository(private val api: WishlistApiKtor) : BaseApiClient() {
    suspend fun createWishlist(createWishlistModel: CreateWishlistModel): KtorResponse<String> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.createWishlist(createWishlistModel)
            }
        }
    }
}