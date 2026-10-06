//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    print("Количество товаров: ")
    val n = readLine()?.trim()?.toIntOrNull()

    if (n == null || n <= 0) {
        println("Ошибка: количество товаров должно быть целым числом больше 0")
        return
    }

    val prices = mutableListOf<Double>()
    for (i in 1..n) {
        prices.add(readPrice(i))
    }

    var total = 0.0
    var max = prices[0]
    var min = prices[0]

    for (price in prices) {
        total += price
        if (price > max) max = price
        if (price < min) min = price
    }

    val average = total / n

    var aboveAverageCount = 0
    for (price in prices) {
        if (price > average) aboveAverageCount++
    }

    val discountPercent = getDiscountPercent(total)
    val discountAmount = total * discountPercent / 100
    val finalTotal = total - discountAmount

    println()
    println("Товар   Цена")
    for ((index, price) in prices.withIndex()) {
        println("${index + 1}".padEnd(8) + formatMoney(price))
    }

    val report = """
        |
        |Итоговый отчёт:
        |Количество товаров: $n
        |Общая сумма: ${formatMoney(total)}
        |Средняя цена: ${formatMoney(average)}
        |Товаров дороже средней: $aboveAverageCount
        |Максимальная цена: ${formatMoney(max)}
        |Минимальная цена: ${formatMoney(min)}
        |Скидка: $discountPercent%
        |Сумма скидки: ${formatMoney(discountAmount)}
        |К оплате: ${formatMoney(finalTotal)}
    """.trimMargin()

    println(report)
}

fun readPrice(number: Int): Double {
    while (true) {
        print("Цена товара $number: ")
        val price = readLine()?.trim()?.replace(',', '.')?.toDoubleOrNull()

        if (price != null && price.isFinite() && price >= 0) {
            return price
        }
        println("Ошибка: введите число (0 или больше), попробуйте ещё раз")
    }
}

fun getDiscountPercent(total: Double): Int = when {
    total > 10000 -> 10
    total >= 5000 -> 5
    else -> 0
}

fun formatMoney(value: Double): String = "%.2f".format(value)