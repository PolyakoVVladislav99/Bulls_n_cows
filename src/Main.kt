import kotlin.random.Random

fun main() {
    println("Игра 'Быки и коровы'")
    println("Компьютер загадал 4-значное число с неповторяющимися цифрами (возможно, начинается с 0).")
    println("Ваша задача — отгадать его!")

    val secret = generateSecretNumber()
    println("DEBUG: $secret") // можно раскомментировать для отладки

    var attempts = 0
    while (true) {
        print("Введите 4-значное число: ")
        val guess = readLine()?.trim() ?: continue

        if (!isValidGuess(guess)) {
            println("Ошибка: нужно ввести 4-значное число с неповторяющимися цифрами.")
            continue
        }

        attempts++
        val (bulls, cows) = checkGuess(secret, guess)

        println("Быки: $bulls, Коровы: $cows")

        if (bulls == 4) {
            println("Поздравляем! Вы угадали число $secret за $attempts попыток.")
            break
        }
    }
}

fun generateSecretNumber(): String {
    val digits = (0..9).toMutableList()
    digits.shuffle(Random(System.currentTimeMillis()))
    return digits.take(4).joinToString("")
}

fun isValidGuess(guess: String): Boolean {
    if (guess.length != 4) return false
    if (!guess.all { it.isDigit() }) return false
    if (guess.toSet().size != 4) return false
    return true
}

fun checkGuess(secret: String, guess: String): Pair<Int, Int> {
    var bulls = 0
    var cows = 0

    for (i in secret.indices) {
        if (guess[i] == secret[i]) {
            bulls++
        } else if (guess[i] in secret) {
            cows++
        }
    }
    return Pair(bulls, cows)
}
