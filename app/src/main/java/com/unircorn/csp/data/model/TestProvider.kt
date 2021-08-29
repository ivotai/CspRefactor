package com.unircorn.csp.data.model

object TestProvider {

    fun provider(): Test {
        val question = Question(
            description = "1921年7月23日至8月初,中国共产党第一次全国代表大会先后在（）召开。",
            options = listOf(
                Option(description = "上海、天津"),
                Option(description = "上海、嘉兴"),
                Option(description = "北京、上海"),
                Option(description = "湖南、嘉兴")
            ),
            rightAnswer = 0
        )
        val test = Test(questions = ArrayList())
        (1..20).forEach { _ -> test.questions.add(question.copy()) }
        return test
    }

}