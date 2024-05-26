package ai.constructn.aaas.cryptoapplication




data class Stats(
    val total: Int,
    val totalCoins: Int,
    val totalMarkets: Int,
    val totalExchanges: Int,
    val totalMarketCap: String,
    val total24hVolume: String
)

data class Coin(
    val uuid: String,
    val symbol: String,
    val name: String,
    val color: String,
    val iconUrl: String,
    val marketCap: String,
    val price: String,
    val listedAt: Long,
    val change: String,
    val rank: Int,
    val sparkline: List<String>,
    val coinrankingUrl: String,
    val `24hVolume`: String,
    val btcPrice: String,
    val contractAddresses: List<String>? // This field is optional as it's not present in every object
)

data class Data(
    val stats: Stats,
    val coins: List<Coin>
)

data class ApiResponse(
    val status: String,
    val data: Data
)
