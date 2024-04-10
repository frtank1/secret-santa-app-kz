package kz.secret_santa_jusan.presentation.form.my_wishlist

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class MyWishlistRouter : ScreenProvider {
    class MyWishlistScreen(
        val id:String
    ) : MyWishlistRouter()
}