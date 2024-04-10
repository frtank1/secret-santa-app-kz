package kz.secret_santa_jusan.data.wishlist

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.data.wishlist.models.CreateWishlistModel


class WishlistApiKtor(private val httpClient: HttpClient) {

    suspend fun createWishlist(model: CreateWishlistModel): HttpResponse {
        return httpClient.post("/wishlist/${model.gameId}/create-wishlist") {
            header("Authorization", "Bearer ${GlobalStorage.access_token}")
            setBody(model.gifts)
        }
    }
}