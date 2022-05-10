@file:Suppress("UNUSED_PARAMETER")

package lesson7


/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */

// Трудоемкость: O(N * M), N - Длина первой строки, M - Длина второй строки
// Ресурсоемкость: O(N * M)
fun longestCommonSubSequence(first: String, second: String): String {
    if (first == second) return first
    if (first.isEmpty() || second.isEmpty()) return ""
    var n = first.length
    var m = second.length
    val answer = StringBuilder()
    val matrix = Array(first.length + 1) { IntArray(second.length + 1) }
    //Заполнение матрицы
    for (i in 1..first.length) {
        for (j in 1..second.length) {
            if (first[i - 1] == second[j - 1]) matrix[i][j] = matrix[i - 1][j - 1] + 1
            else matrix[i][j] = maxOf(matrix[i - 1][j], matrix[i][j - 1])
        }
    }
    // Восстановление результата
    while (n > 0 && m > 0) {
        if (matrix[n][m - 1] == matrix[n][m]) n++
        else if (matrix[n][m] == matrix[n - 1][m]) m++
        else answer.append(first[n - 1])
        n--
        m--
    }
    return answer.reverse().toString()
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */

// Трудоемкость: O(N * log(N))
// Ресурсоемкость: O(N)
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    if (list.isEmpty()) return list
    var maxLength = 0
    val previous = IntArray(list.size)
    val position = IntArray(list.size + 1)
    val d = IntArray(list.size + 1)
    d.fill(Int.MIN_VALUE)
    d[0] = Int.MAX_VALUE

    for (i in list.size - 1 downTo 0) {
        // Бинарный поиск
        var a = 1
        var b: Int
        var c = maxLength
        while (a <= c) {
            b = (a + c) / 2
            if (list[position[b]] >= list[i]) a = b + 1 else c = b - 1
        }
        if (d[a - 1] > list[i] && d[a] < list[i]) {
            d[a] = list[i]
            previous[i] = position[a - 1]
            position[a] = i
            if (a > maxLength) maxLength = a
        }
    }
    // Восстановление результата
    val answer = mutableListOf<Int>()
    var p = position[maxLength]
    for (i in maxLength - 1 downTo 0) {
        answer.add(list[p])
        p = previous[p]
    }
    return answer
}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    TODO()
}
