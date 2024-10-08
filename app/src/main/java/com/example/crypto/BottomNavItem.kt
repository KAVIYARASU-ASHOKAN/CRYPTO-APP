package com.iyke.crypto_trackersealed

import com.example.crypto.R


open  class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.ic_baseline_home_24,"home")
    object Wallet: BottomNavItem("Wallet",R.drawable.ic_baseline_account_balance_wallet_24,"wallet")
    object Trade: BottomNavItem("Transaction",R.drawable.transaction,"Transaction")
    object Profile: BottomNavItem("Setting",R.drawable.ic_baseline_person_24,"Setting")
}