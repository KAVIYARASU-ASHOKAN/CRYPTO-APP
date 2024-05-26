package com.iyke.crypto_tracker.model

import com.example.crypto.R


object Data {
    var transactionList = arrayListOf(
        Transaction(
            transactionID = 1,
            description = "Sold Bitcoin",
            amount = 0.5657F,
            currencyCode = "BTC",
            transactionType = "S",
            transactionDate = "12:20 12 Mar",
            R.drawable.bbtc
        ),
        Transaction(
            transactionID = 2,
            description = "Bought Ethereum",
            amount = 10.6734F,
            currencyCode = "ETH",
            transactionType = "B",
            transactionDate = "14:20 12 Apr",
            R.drawable.eth
        ),
        Transaction(
            transactionID = 3,
            description = "Sold Tether",
            amount = -567.8754F,
            currencyCode = "THT",
            transactionType = "S",
            transactionDate = "1:20 12 Jan",
            R.drawable.tether
        ),
        Transaction(
            transactionID = 4,
            description = "Bought Solona",
            amount = 2.0034F,
            currencyCode = "SOL",
            transactionType = "B",
            transactionDate = "14:20 12 Apr",
            R.drawable.tether
        ),
        Transaction(
            transactionID = 5,
            description = "Bought BNB",
            amount = 1.7864F ,
            currencyCode = "BNB",
            transactionType = "B",
            transactionDate = "14:20 12 Apr",
            R.drawable.bnb
        ),


        )
    var list = arrayListOf<PortfolioCoins>(
        PortfolioCoins(
            "Bitcoin",
            "$70000",
            "+2%",
            R.drawable.bbtc,
            "BTC",
            "USDT",
            "32,00",
            R.drawable.home_alk,
            "I",
            description = "Bitcoin is a cryptocurrency invented in 2008 by an unknown person or group of people using the name Satoshi Nakamoto. The currency began use in 2009 when its implementation was released as open-source software.",
            7.24F,
        ),
        PortfolioCoins(
            "Ethereum",
            "$3400",
            "+2%",
            R.drawable.eth,
            "ETH",
            "USDT",
            "25,00",
            R.drawable.home_ba,
            "I",
            description = "Ethereum is a decentralized, open-source blockchain featuring smart contract functionality. Ether is the native cryptocurrency of the platform. It is the second-largest cryptocurrency by market capitalization, after Bitcoin. Ethereum is the most actively used blockchain.",
            7.24F
        ),
        PortfolioCoins(
            "Tether",
            "$1.00",
            "+5%",
            R.drawable.tether,
            "TTH",
            "USDT",
            "0.01",
            R.drawable.home_ccl,
            "I",
            "USDT is a stablecoin (stable-value cryptocurrency) that mirrors the price of the U.S. dollar, issued by a Hong Kong-based company Tether. The token’s peg to the USD is achieved via maintaining a sum of commercial paper, fiduciary deposits, cash, reserve repo notes, and treasury bills in reserves that is equal in USD value to the number of USDT in circulation",
            2.23f

        ),
        PortfolioCoins(
            "Solona",
            "$170",
            "+5%",
            R.drawable.usdc,
            "SOL",
            "USDC",
            "76,00",
            R.drawable.home_dal,
            "I",
            "USD Coin (known by its ticker USDC) is a stablecoin that is pegged to the U.S. dollar on a 1:1 basis. Every unit of this cryptocurrency in circulation is backed up by \$1 that is held in reserve, in a mix of cash and short-term U.S. Treasury bonds. The Centre consortium, which is behind this asset, says USDC is issued by regulated financial institutions.",
            7.24F,

            ),
        PortfolioCoins(
            "BNB",
            "$600",
            "+4.19%",
            R.drawable.bnb,
            "BNB",
            "USDT",
            "11,20",
            R.drawable.home_eadsy,
            "I",
            "Launched in July 2017, Binance is the biggest cryptocurrency exchange globally based on daily trading volume. Binance aims to bring cryptocurrency exchanges to the forefront of financial activity globally. The idea behind Binance’s name is to show this new paradigm in global finance — Binary Finance, or Binance.",
            7.24F,
        ),
    )
}


class Constants {
    companion object {
        const val PADDING_SIDE_VALUE = 12
        const val ELEVATION_VALUE = 10
        const val SHOTNAME = "shotName"
        const val Id = "id"
    }
}