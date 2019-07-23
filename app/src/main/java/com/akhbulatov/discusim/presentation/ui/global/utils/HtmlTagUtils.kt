package com.akhbulatov.discusim.presentation.ui.global.utils

object HtmlTagUtils {
    /**
     * Regex для получения тега `<a>` из `HTML`-строки.
     * *Пример*:
     * **Input**:
     * ```
     * <p>Description of <a href="https://uploads.disquscdn.com/images/f27f72367.gif">image link</a>
     * <br> And new text again.</p>
     * ```
     * **Output**:
     * ```
     * <a href="https://uploads.disquscdn.com/images/f27f72367.gif">image link</a>
     * ```
     */
    private val aTagRegex = "<a.+?>.+?</a>".toRegex()
    /**
     * Regex для получения текста в двойных кавычках (включая кавычки).
     * *Пример*:
     * **Input**:
     * ```
     *  <a href="https://uploads.disquscdn.com/images/f27f72367.gif"</a>
     * ```
     * **Output**:
     * ```
     *  "https://uploads.disquscdn.com/images/f27f72367.gif"
     * ```
     */
    private val doubleQuotesRegex = "\".+?\"".toRegex()

    private val imagesExtensions = arrayOf(".jpg", ".jpeg", ".png", ".gif")

    /**
     * Заменяет ссылки на изображения с тэга `<a>` на `<img>` для ссылок с расширением изображения
     * (т.е. для тех, которые заканчиваются на: **.gif**, **.png**, **.jpg** и т.д.).
     * *Пример*:
     * **Input**:
     * ```
     * <a href="https://uploads.disquscdn.com/images/f27f72367.gif"</a>
     * ```
     * **Output**:
     * ```
     * <img src="https://uploads.disquscdn.com/images/f27f72367.gif"/>
     * ```
     *
     * @param url URL c HTML-текстом.
     * @return HTML-текст с тэгами `<img>` вместо `<a>` или входную строку без изменений,
     * если нет ссылок, содержащих изображения.
     */
    fun replaceLinkTagsToImg(url: String): String {
        val result = StringBuilder(url)
        aTagRegex.findAll(url).forEach {
            val aTag = it.value
            val linkMatchResult = doubleQuotesRegex.find(aTag)

            if (linkMatchResult != null) {
                val linkWithQuotes = linkMatchResult.value
                val linkWithoutQuotes = linkWithQuotes.replace("\"", "")

                for (i in imagesExtensions.indices) {
                    if (linkWithoutQuotes.endsWith(imagesExtensions[i])) {
                        // Заменяет тэг <a> на <img>
                        val imgLink = "<img src=$linkWithQuotes/>"
                        val startIndex = result.indexOf(aTag)
                        val endIndex = startIndex + aTag.length
                        result.replace(startIndex, endIndex, imgLink)
                        break
                    }
                }
            }
        }
        return result.toString()
    }
}