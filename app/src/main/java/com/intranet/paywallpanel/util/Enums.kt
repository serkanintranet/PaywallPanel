package com.intranet.paywallpanel.util

class Enums {
    enum class PaymentStatus(val paymentStatus: String) {
        Oluşturuldu("Oluşturuldu"), Başladı("Başladı"), FinansallastirmaBekliyor(
            "Finansallaştırma Bekliyor"
        ),
        Başarılı("Başarılı"), Başarısız("Başarısız")
    }

    enum class PaymentStatusColor(val paymentStatusColor: String) {
        Oluşturuldu("#90BE6D"), Başladı("#43AA8B"), FinansallaştırmaBekliyor(
            "#4D908E"
        ),
        Başarılı("#4CAF50"), Başarısız("#D30000");
    }
}