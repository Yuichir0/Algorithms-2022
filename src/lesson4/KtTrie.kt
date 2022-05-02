package lesson4

import java.util.*

/**
 * Префиксное дерево для строк
 */
class KtTrie : AbstractMutableSet<String>(), MutableSet<String> {

    private class Node {
        val children: SortedMap<Char, Node> = sortedMapOf()
    }

    private val root = Node()

    override var size: Int = 0
        private set

    override fun clear() {
        root.children.clear()
        size = 0
    }

    private fun String.withZero() = this + 0.toChar()

    private fun findNode(element: String): Node? {
        var current = root
        for (char in element) {
            current = current.children[char] ?: return null
        }
        return current
    }

    override fun contains(element: String): Boolean =
        findNode(element.withZero()) != null

    override fun add(element: String): Boolean {
        var current = root
        var modified = false
        for (char in element.withZero()) {
            val child = current.children[char]
            if (child != null) {
                current = child
            } else {
                modified = true
                val newChild = Node()
                current.children[char] = newChild
                current = newChild
            }
        }
        if (modified) {
            size++
        }
        return modified
    }

    override fun remove(element: String): Boolean {
        val current = findNode(element) ?: return false
        if (current.children.remove(0.toChar()) != null) {
            size--
            return true
        }
        return false
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: [java.util.Iterator] (Ctrl+Click по Iterator)
     *
     * Сложная
     */
    override fun iterator(): MutableIterator<String> = TrieIterator()
    inner class TrieIterator : MutableIterator<String> {
        private val allWords = mutableListOf<String>()

        // Трудоемкость: О(N - сумма длин всех слов в худшем случае)
        // Ресурсоемкость: О(V - количество слов)
        private fun createListOfWords(word: String, currentNode: Node) {
            for (char in currentNode.children)
                if (char.key != 0.toChar()) createListOfWords(word + char.key, char.value)
                else allWords.add(word)
        }

        init {
            createListOfWords("", root)
        }

        private val numberOfWords = allWords.size
        private var currentWord: String? = null
        private var currentWordNumber = 0

        // Трудоемкость: O(1)
        // Ресурсоемкость: О(1)
        override fun hasNext(): Boolean = currentWordNumber < numberOfWords

        // Трудоемкость: О(1)
        // Ресурсоемкость: О(1)
        override fun next(): String {
            if (hasNext()) {
                currentWord = allWords[currentWordNumber]
                currentWordNumber++
                return currentWord as String
            }
            throw NoSuchElementException()
        }

        // Трудоемкость: О(N - длина самого длинного слова в худшем случае)
        // Ресурсоемкость: О(1)
        override fun remove() {
            if (currentWord == null) throw IllegalStateException()
            remove(currentWord)
            currentWord = null
        }
    }
}