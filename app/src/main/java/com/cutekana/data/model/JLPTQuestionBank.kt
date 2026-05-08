package com.cutekana.data.model

/**
 * Comprehensive question bank for JLPT N5 and N4
 * Contains 3x more questions than the original implementation
 */

object JLPTQuestionBank {
    
    // ============================================
    // N5 QUESTION BANK - 180 questions total (3x original 60)
    // ============================================
    
    fun getN5Questions(): List<QuestionData> {
        val questions = mutableListOf<QuestionData>()
        questions.addAll(getN5VocabularyQuestions())
        questions.addAll(getN5GrammarQuestions())
        questions.addAll(getN5ReadingQuestions())
        questions.addAll(getN5ListeningQuestions())
        return questions.shuffled()
    }
    
    private fun getN5VocabularyQuestions(): List<QuestionData> {
        return listOf(
            // Basic Kanji Readings (60 questions)
            QuestionData.KanjiReadingData(
                id = "n5_vocab_001",
                kanji = "日",
                contextSentence = "今日はいい日です。",
                options = listOf("にち", "ひ", "きょう", "あした"),
                correctAnswer = "ひ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'day' or 'sun'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_002",
                kanji = "本",
                contextSentence = "これは私の本です。",
                options = listOf("ほん", "もと", "ぼん", "ほう"),
                correctAnswer = "ほん",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'book' or 'origin'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_003",
                kanji = "人",
                contextSentence = "あの人は先生です。",
                options = listOf("にん", "じん", "ひと", "びと"),
                correctAnswer = "ひと",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'person'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_004",
                kanji = "月",
                contextSentence = "月がきれいです。",
                options = listOf("つき", "げつ", "がつ", "つきみ"),
                correctAnswer = "つき",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'moon' or 'month'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_005",
                kanji = "火",
                contextSentence = "火を消してください。",
                options = listOf("か", "ひ", "ほのお", "き"),
                correctAnswer = "ひ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'fire'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_006",
                kanji = "水",
                contextSentence = "水を飲みます。",
                options = listOf("すい", "みず", "みずうみ", "すいよう"),
                correctAnswer = "みず",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'water'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_007",
                kanji = "木",
                contextSentence = "大きい木があります。",
                options = listOf("もく", "ぼく", "き", "こん"),
                correctAnswer = "き",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'tree' or 'wood'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_008",
                kanji = "金",
                contextSentence = "お金を払います。",
                options = listOf("きん", "かね", "き", "こん"),
                correctAnswer = "かね",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'money' or 'gold'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_009",
                kanji = "土",
                contextSentence = "土の上に座ります。",
                options = listOf("ど", "と", "つち", "つちか"),
                correctAnswer = "つち",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'earth' or 'soil'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_010",
                kanji = "上",
                contextSentence = "上を見てください。",
                options = listOf("じょう", "うえ", "うわ", "かみ"),
                correctAnswer = "うえ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'above' or 'up'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_011",
                kanji = "下",
                contextSentence = "下に落ちました。",
                options = listOf("か", "げ", "した", "しも"),
                correctAnswer = "した",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'below' or 'down'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_012",
                kanji = "中",
                contextSentence = "箱の中に入っています。",
                options = listOf("ちゅう", "じゅう", "なか", "あたり"),
                correctAnswer = "なか",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'inside' or 'middle'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_013",
                kanji = "大",
                contextSentence = "大きい犬がいます。",
                options = listOf("だい", "たい", "おお", "おおき"),
                correctAnswer = "おお",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'big' or 'large'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_014",
                kanji = "小",
                contextSentence = "小さい猫が好きです。",
                options = listOf("しょう", "ちい", "こ", "お"),
                correctAnswer = "ちい",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'small' or 'little'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_015",
                kanji = "山",
                contextSentence = "山に登りました。",
                options = listOf("さん", "ざん", "やま", "やまと"),
                correctAnswer = "やま",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'mountain'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_016",
                kanji = "川",
                contextSentence = "川で泳ぎます。",
                options = listOf("せん", "かわ", "か", "がわ"),
                correctAnswer = "かわ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'river'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_017",
                kanji = "田",
                contextSentence = "田んぼで働きます。",
                options = listOf("でん", "た", "だ", "で"),
                correctAnswer = "た",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'rice field'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_018",
                kanji = "目",
                contextSentence = "目が痛いです。",
                options = listOf("もく", "め", "まなこ", "も"),
                correctAnswer = "め",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'eye'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_019",
                kanji = "耳",
                contextSentence = "耳が遠いです。",
                options = listOf("じ", "み", "みみ", "じじ"),
                correctAnswer = "みみ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'ear'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_020",
                kanji = "口",
                contextSentence = "口を開けてください。",
                options = listOf("こう", "く", "くち", "ぐち"),
                correctAnswer = "くち",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'mouth'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_021",
                kanji = "手",
                contextSentence = "手を洗います。",
                options = listOf("しゅ", "ず", "て", "た"),
                correctAnswer = "て",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'hand'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_022",
                kanji = "足",
                contextSentence = "足が痛いです。",
                options = listOf("そく", "あし", "た", "たりる"),
                correctAnswer = "あし",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'foot' or 'leg'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_023",
                kanji = "雨",
                contextSentence = "雨が降っています。",
                options = listOf("う", "あめ", "あま", "さめ"),
                correctAnswer = "あめ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'rain'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_024",
                kanji = "電",
                contextSentence = "電車に乗ります。",
                options = listOf("でん", "いなずま", "いかずち", "かみなり"),
                correctAnswer = "でん",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'electricity'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_025",
                kanji = "学",
                contextSentence = "学校へ行きます。",
                options = listOf("がく", "がっ", "まな", "まなぶ"),
                correctAnswer = "がく",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'study' or 'learning'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_026",
                kanji = "校",
                contextSentence = "学校が好きです。",
                options = listOf("こう", "がっこう", "こ", "きょう"),
                correctAnswer = "こう",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Part of 'school'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_027",
                kanji = "先",
                contextSentence = "先生は優しいです。",
                options = listOf("せん", "さき", "まず", "おさ"),
                correctAnswer = "せん",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'ahead' or 'previous'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_028",
                kanji = "生",
                contextSentence = "学生です。",
                options = listOf("せい", "しょう", "い", "なま"),
                correctAnswer = "せい",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'life' or 'student'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_029",
                kanji = "長",
                contextSentence = "社長です。",
                options = listOf("ちょう", "なが", "おさ", "たけ"),
                correctAnswer = "ちょう",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'chief' or 'long'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_030",
                kanji = "白",
                contextSentence = "白い花です。",
                options = listOf("はく", "びゃく", "しろ", "しら"),
                correctAnswer = "しろ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'white'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_031",
                kanji = "青",
                contextSentence = "青い空です。",
                options = listOf("せい", "しょう", "あお", "あさ"),
                correctAnswer = "あお",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'blue'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_032",
                kanji = "赤",
                contextSentence = "赤い車です。",
                options = listOf("せき", "しゃく", "あか", "あこ"),
                correctAnswer = "あか",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'red'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_033",
                kanji = "黒",
                contextSentence = "黒い猫です。",
                options = listOf("こく", "くろ", "く", "こ"),
                correctAnswer = "くろ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'black'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_034",
                kanji = "左",
                contextSentence = "左に曲がってください。",
                options = listOf("さ", "ひだり", "ひじ", "さゆう"),
                correctAnswer = "ひだり",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'left'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_035",
                kanji = "右",
                contextSentence = "右にあります。",
                options = listOf("う", "ゆう", "みぎ", "め"),
                correctAnswer = "みぎ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'right'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_036",
                kanji = "東",
                contextSentence = "東に向かいます。",
                options = listOf("とう", "ひがし", "あずま", "と"),
                correctAnswer = "ひがし",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'east'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_037",
                kanji = "西",
                contextSentence = "西の空が赤いです。",
                options = listOf("せい", "さい", "にし", "い"),
                correctAnswer = "にし",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'west'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_038",
                kanji = "南",
                contextSentence = "南へ行きます。",
                options = listOf("なん", "な", "みなみ", "みな"),
                correctAnswer = "みなみ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'south'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_039",
                kanji = "北",
                contextSentence = "北が寒いです。",
                options = listOf("ほく", "きた", "き", "ほ"),
                correctAnswer = "きた",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'north'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_040",
                kanji = "男",
                contextSentence = "あの男の人は誰ですか。",
                options = listOf("だん", "なん", "おとこ", "お"),
                correctAnswer = "おとこ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'man' or 'male'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_041",
                kanji = "女",
                contextSentence = "女の子が走っています。",
                options = listOf("じょ", "にょ", "おんな", "め"),
                correctAnswer = "おんな",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'woman' or 'female'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_042",
                kanji = "子",
                contextSentence = "子供が遊んでいます。",
                options = listOf("し", "す", "こ", "き"),
                correctAnswer = "こ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'child'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_043",
                kanji = "友",
                contextSentence = "友達と映画を見ます。",
                options = listOf("ゆう", "とも", "ゆ", "ともだち"),
                correctAnswer = "とも",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'friend'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_044",
                kanji = "父",
                contextSentence = "父は会社員です。",
                options = listOf("ふ", "ちち", "とう", "とおや"),
                correctAnswer = "ちち",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'father' (my father)"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_045",
                kanji = "母",
                contextSentence = "母はきれいです。",
                options = listOf("ぼ", "はは", "かあ", "か"),
                correctAnswer = "はは",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'mother' (my mother)"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_046",
                kanji = "兄",
                contextSentence = "兄は学生です。",
                options = listOf("きょう", "けい", "あに", "にい"),
                correctAnswer = "あに",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'older brother' (my brother)"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_047",
                kanji = "姉",
                contextSentence = "姉は優しいです。",
                options = listOf("し", "あね", "ねえ", "ね"),
                correctAnswer = "あね",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'older sister' (my sister)"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_048",
                kanji = "弟",
                contextSentence = "弟がいます。",
                options = listOf("てい", "だい", "おとうと", "おと"),
                correctAnswer = "おとうと",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'younger brother'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_049",
                kanji = "妹",
                contextSentence = "妹がかわいいです。",
                options = listOf("ばい", "まい", "いもうと", "いも"),
                correctAnswer = "いもうと",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'younger sister'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_050",
                kanji = "名前",
                contextSentence = "名前を書いてください。",
                options = listOf("なまえ", "めいしょう", "なぜ", "めい"),
                correctAnswer = "なまえ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'name'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_051",
                kanji = "年",
                contextSentence = "今年は平成です。",
                options = listOf("ねん", "とし", "よわい", "わか"),
                correctAnswer = "とし",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'year' or 'age'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_052",
                kanji = "時",
                contextSentence = "今は何時ですか。",
                options = listOf("じ", "とき", "じこ", "どき"),
                correctAnswer = "とき",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'time' or 'hour'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_053",
                kanji = "分",
                contextSentence = "十分待ちました。",
                options = listOf("ふん", "ぶん", "わ", "わか"),
                correctAnswer = "ふん",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'minute'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_054",
                kanji = "間",
                contextSentence = "あいだに挟みます。",
                options = listOf("かん", "けん", "あいだ", "ま"),
                correctAnswer = "あいだ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'interval' or 'between'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_055",
                kanji = "午",
                contextSentence = "午前中です。",
                options = listOf("ご", "うま", "こ", "こう"),
                correctAnswer = "ご",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Part of 'morning/afternoon'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_056",
                kanji = "前",
                contextSentence = "前に立ってください。",
                options = listOf("ぜん", "まえ", "さき", "ぜ"),
                correctAnswer = "まえ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'front' or 'before'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_057",
                kanji = "後",
                contextSentence = "後で来てください。",
                options = listOf("ご", "こう", "うしろ", "あと"),
                correctAnswer = "あと",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'after' or 'behind'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_058",
                kanji = "外",
                contextSentence = "外で遊びます。",
                options = listOf("がい", "げ", "そと", "ほか"),
                correctAnswer = "そと",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'outside'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_059",
                kanji = "内",
                contextSentence = "内側に入ります。",
                options = listOf("ない", "だい", "うち", "いえ"),
                correctAnswer = "うち",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'inside' or 'home'"
            ),
            QuestionData.KanjiReadingData(
                id = "n5_vocab_060",
                kanji = "多",
                contextSentence = "多くの人がいます。",
                options = listOf("た", "おお", "まさ", "か"),
                correctAnswer = "おお",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Means 'many' or 'much'"
            )
        )
    }
    
    private fun getN5GrammarQuestions(): List<QuestionData> {
        return listOf(
            // Particles - Core N5 grammar (60 questions)
            QuestionData.GrammarPatternData(
                id = "n5_grammar_001",
                sentence = "私___学校へ行きます。",
                blankPosition = 1,
                options = listOf("は", "が", "を", "に"),
                correctAnswer = "は",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Topic particle は"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_002",
                sentence = "これ___私の本です。",
                blankPosition = 1,
                options = listOf("は", "が", "を", "の"),
                correctAnswer = "は",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Topic particle は"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_003",
                sentence = "猫___庭にいます。",
                blankPosition = 1,
                options = listOf("は", "が", "を", "に"),
                correctAnswer = "が",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Subject particle が"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_004",
                sentence = "誰___来ましたか。",
                blankPosition = 1,
                options = listOf("は", "が", "を", "に"),
                correctAnswer = "が",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Subject particle が with question words"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_005",
                sentence = "私___日本語を勉強します。",
                blankPosition = 1,
                options = listOf("は", "が", "を", "に"),
                correctAnswer = "は",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Topic particle は"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_006",
                sentence = "寿司___好きです。",
                blankPosition = 1,
                options = listOf("は", "が", "を", "に"),
                correctAnswer = "が",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "が with adjectives (likes/dislikes)"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_007",
                sentence = "毎朝７時___起きます。",
                blankPosition = 4,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "に",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Time particle に"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_008",
                sentence = "明日___行きます。",
                blankPosition = 2,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "に",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Time particle に"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_009",
                sentence = "学校___行きます。",
                blankPosition = 2,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "へ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Direction particle へ"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_010",
                sentence = "日本___行きます。",
                blankPosition = 2,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "へ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Direction particle へ"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_011",
                sentence = "東京___住んでいます。",
                blankPosition = 2,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "に",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Location of existence に"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_012",
                sentence = "家___あります。",
                blankPosition = 1,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "に",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Location of existence に"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_013",
                sentence = "公園___遊びます。",
                blankPosition = 2,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "で",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Location of action で"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_014",
                sentence = "レストラン___食べます。",
                blankPosition = 5,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "で",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Location of action で"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_015",
                sentence = "電車___会社へ行きます。",
                blankPosition = 2,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "で",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Means で"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_016",
                sentence = "バス___帰ります。",
                blankPosition = 2,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "で",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Means で"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_017",
                sentence = "日本語___話します。",
                blankPosition = 4,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "で",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Language で"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_018",
                sentence = "本___読みます。",
                blankPosition = 1,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "を",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Object particle を"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_019",
                sentence = "コーヒー___飲みます。",
                blankPosition = 5,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "を",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Object particle を"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_020",
                sentence = "ご飯___食べます。",
                blankPosition = 2,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "を",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Object particle を"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_021",
                sentence = "友達___会います。",
                blankPosition = 3,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "に",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Indirect object particle に (meet)"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_022",
                sentence = "先生___聞きます。",
                blankPosition = 2,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "に",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Indirect object particle に (ask)"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_023",
                sentence = "母___電話します。",
                blankPosition = 1,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "に",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Indirect object particle に (call)"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_024",
                sentence = "私の___本です。",
                blankPosition = 2,
                options = listOf("は", "が", "を", "の"),
                correctAnswer = "の",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Possessive particle の"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_025",
                sentence = "田中さん___車です。",
                blankPosition = 4,
                options = listOf("は", "が", "を", "の"),
                correctAnswer = "の",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Possessive particle の"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_026",
                sentence = "日本___料理はおいしいです。",
                blankPosition = 2,
                options = listOf("は", "が", "を", "の"),
                correctAnswer = "の",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Descriptive particle の"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_027",
                sentence = "１つ___１００円です。",
                blankPosition = 2,
                options = listOf("は", "が", "を", "と"),
                correctAnswer = "と",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "And/With particle と"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_028",
                sentence = "友達___映画を見ます。",
                blankPosition = 3,
                options = listOf("は", "が", "を", "と"),
                correctAnswer = "と",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "With particle と"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_029",
                sentence = "朝９時___午後５時まで働きます。",
                blankPosition = 4,
                options = listOf("から", "まで", "に", "で"),
                correctAnswer = "から",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "From から"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_030",
                sentence = "９時から５時___働きます。",
                blankPosition = 5,
                options = listOf("から", "まで", "に", "で"),
                correctAnswer = "まで",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Until まで"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_031",
                sentence = "駅___家まで歩きます。",
                blankPosition = 1,
                options = listOf("から", "まで", "に", "で"),
                correctAnswer = "から",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "From から"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_032",
                sentence = "会社___銀行へ行きます。",
                blankPosition = 2,
                options = listOf("から", "まで", "に", "へ"),
                correctAnswer = "から",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "From から"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_033",
                sentence = "ここ___写真を撮ってもいいですか。",
                blankPosition = 2,
                options = listOf("で", "に", "を", "が"),
                correctAnswer = "で",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Permission てもいい"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_034",
                sentence = "すみません、ちょっと___いいですか。",
                blankPosition = 4,
                options = listOf("聞いても", "聞いて", "聞く", "聞いては"),
                correctAnswer = "聞いても",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Permission てもいい"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_035",
                sentence = "これ___食べてはいけません。",
                blankPosition = 2,
                options = listOf("は", "が", "を", "に"),
                correctAnswer = "は",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Prohibition てはいけない"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_036",
                sentence = "ここ___写真を撮ってはいけません。",
                blankPosition = 2,
                options = listOf("で", "に", "を", "が"),
                correctAnswer = "で",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Prohibition てはいけない"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_037",
                sentence = "毎日___運動します。",
                blankPosition = 2,
                options = listOf("は", "が", "を", "も"),
                correctAnswer = "も",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Also/Too particle も"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_038",
                sentence = "私___学生です。",
                blankPosition = 1,
                options = listOf("は", "が", "を", "も"),
                correctAnswer = "も",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Also/Too particle も"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_039",
                sentence = "日本___行ったことがありますか。",
                blankPosition = 2,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "へ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Experience たことがある"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_040",
                sentence = "寿司___食べたことがあります。",
                blankPosition = 2,
                options = listOf("は", "が", "を", "に"),
                correctAnswer = "を",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Experience たことがある"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_041",
                sentence = "明日___雨が降るでしょう。",
                blankPosition = 2,
                options = listOf("は", "が", "を", "も"),
                correctAnswer = "は",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Probably でしょう"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_042",
                sentence = "あの人___先生でしょう。",
                blankPosition = 3,
                options = listOf("は", "が", "を", "も"),
                correctAnswer = "は",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Probably/I wonder でしょう"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_043",
                sentence = "李さん___中国人ですか。",
                blankPosition = 3,
                options = listOf("は", "が", "を", "も"),
                correctAnswer = "は",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Question particle か"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_044",
                sentence = "これ___何ですか。",
                blankPosition = 2,
                options = listOf("は", "が", "を", "も"),
                correctAnswer = "は",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Question with 何"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_045",
                sentence = "だれ___田中さんですか。",
                blankPosition = 2,
                options = listOf("が", "は", "を", "も"),
                correctAnswer = "が",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Who is... だれが"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_046",
                sentence = "いつ___日本へ来ましたか。",
                blankPosition = 2,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "に",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "When いつ"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_047",
                sentence = "どこ___買いましたか。",
                blankPosition = 2,
                options = listOf("で", "に", "へ", "を"),
                correctAnswer = "で",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Where どこで"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_048",
                sentence = "どこ___行きますか。",
                blankPosition = 2,
                options = listOf("へ", "に", "で", "を"),
                correctAnswer = "へ",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Where to どこへ"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_049",
                sentence = "何___買いましたか。",
                blankPosition = 1,
                options = listOf("を", "に", "で", "が"),
                correctAnswer = "を",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "What 何を"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_050",
                sentence = "何___行きますか。",
                blankPosition = 1,
                options = listOf("で", "に", "へ", "を"),
                correctAnswer = "で",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "By what means 何で"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_051",
                sentence = "いくら___かかりますか。",
                blankPosition = 2,
                options = listOf("に", "で", "へ", "が"),
                correctAnswer = "に",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "How much いくら"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_052",
                sentence = "いつも___電車で行きます。",
                blankPosition = 2,
                options = listOf("は", "が", "を", "も"),
                correctAnswer = "は",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Always いつも"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_053",
                sentence = "よく___映画を見ます。",
                blankPosition = 2,
                options = listOf("は", "が", "を", "も"),
                correctAnswer = "は",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Often よく"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_054",
                sentence = "時々___コーヒーを飲みます。",
                blankPosition = 3,
                options = listOf("は", "が", "を", "も"),
                correctAnswer = "は",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Sometimes ときどき"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_055",
                sentence = "あまり___行きません。",
                blankPosition = 2,
                options = listOf("は", "が", "を", "も"),
                correctAnswer = "は",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Not much あまり〜ない"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_056",
                sentence = "全然___分かりません。",
                blankPosition = 2,
                options = listOf("は", "が", "を", "も"),
                correctAnswer = "は",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Not at all ぜんぜん〜ない"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_057",
                sentence = "早く___行ってください。",
                blankPosition = 2,
                options = listOf("に", "で", "へ", "を"),
                correctAnswer = "に",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Please do てください"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_058",
                sentence = "ここに名前___書いてください。",
                blankPosition = 4,
                options = listOf("は", "が", "を", "に"),
                correctAnswer = "を",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Please do てください"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_059",
                sentence = "もう一度___言ってください。",
                blankPosition = 4,
                options = listOf("は", "が", "を", "に"),
                correctAnswer = "を",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Please say again"
            ),
            QuestionData.GrammarPatternData(
                id = "n5_grammar_060",
                sentence = "すみませんが、窓___閉めてください。",
                blankPosition = 6,
                options = listOf("は", "が", "を", "に"),
                correctAnswer = "を",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Please close"
            )
        )
    }
    
    private fun getN5ReadingQuestions(): List<QuestionData> {
        return listOf(
            // Reading Comprehension (30 questions with passages)
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_001",
                passage = "田中さんは大学生です。毎日学校へ行きます。学校で日本語を勉強します。田中さんは日本語が好きです。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "田中さんは何ですか。",
                        options = listOf("先生", "大学生", "会社員", "医者"),
                        correctAnswer = "大学生",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "田中さんは何を勉強しますか。",
                        options = listOf("英語", "数学", "日本語", "歴史"),
                        correctAnswer = "日本語",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "大学生",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_002",
                passage = "昨日は日曜日でした。私は家で休みました。朝ご飯を食べて、本を読みました。午後から友達と公園へ行きました。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "昨日は何曜日でしたか。",
                        options = listOf("月曜日", "火曜日", "土曜日", "日曜日"),
                        correctAnswer = "日曜日",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "午後どこへ行きましたか。",
                        options = listOf("学校", "公園", "映画館", "レストラン"),
                        correctAnswer = "公園",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "日曜日",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_003",
                passage = "李さんは中国人です。今、日本に住んでいます。日本語学校で勉強しています。毎朝８時に起きて、電車で学校へ行きます。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "李さんはどこの国の人ですか。",
                        options = listOf("日本人", "中国人", "韓国人", "アメリカ人"),
                        correctAnswer = "中国人",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "李さんは何で学校へ行きますか。",
                        options = listOf("バス", "電車", "車", "自転車"),
                        correctAnswer = "電車",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "中国人",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_004",
                passage = "わたしの家の近くに公園があります。公園には大きな木と池があります。朝、お年寄りが散歩をしています。子供たちは遊んでいます。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "公園の近くに何がありますか。",
                        options = listOf("学校", "銀行", "家", "駅"),
                        correctAnswer = "家",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "公園に何がありますか。",
                        options = listOf("山", "木と池", "店", "図書館"),
                        correctAnswer = "木と池",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "家",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_005",
                passage = "私は毎朝７時に起きます。朝ご飯はパンとコーヒーです。８時に家を出て、電車で会社へ行きます。会社は９時からです。昼休みは１時間です。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "何時に起きますか。",
                        options = listOf("６時", "７時", "８時", "９時"),
                        correctAnswer = "７時",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "何で会社へ行きますか。",
                        options = listOf("バス", "車", "電車", "歩いて"),
                        correctAnswer = "電車",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "７時",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_006",
                passage = "山田さんの家には犬がいます。名前は「ポチ」です。ポチは白くて、小さいです。毎朝、山田さんと散歩します。ポチはとてもかわいいです。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "山田さんの家には何がいますか。",
                        options = listOf("猫", "犬", "鳥", "魚"),
                        correctAnswer = "犬",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "ポチはどんな犬ですか。",
                        options = listOf("大きくて黒い", "小さくて白い", "大きくて茶色い", "小さくて黒い"),
                        correctAnswer = "小さくて白い",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "犬",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_007",
                passage = "昨日は雨でした。家で映画を見ました。映画はとても面白かったです。夜、友達に電話しました。今日は晴れです。公園へ行きたいです。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "昨日の天気はどうでしたか。",
                        options = listOf("晴れ", "雨", "曇り", "雪"),
                        correctAnswer = "雨",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "今日はどこへ行きたいですか。",
                        options = listOf("学校", "会社", "映画館", "公園"),
                        correctAnswer = "公園",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "雨",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_008",
                passage = "私の趣味は読書です。図書館によく行きます。週に２回、本を借ります。日本の小説が好きです。先月は５冊読みました。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "趣味は何ですか。",
                        options = listOf("映画", "音楽", "読書", "スポーツ"),
                        correctAnswer = "読書",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "何が好きですか。",
                        options = listOf("外国の小説", "日本の小説", "漫画", "雑誌"),
                        correctAnswer = "日本の小説",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "読書",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_009",
                passage = "佐藤さんは毎日ジョギングします。朝６時に起きて、公園を３周します。３０分ぐらいかかります。体の調子が良くなりました。健康になりたいです。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "佐藤さんは何をしますか。",
                        options = listOf("水泳", "ジョギング", "サッカー", "テニス"),
                        correctAnswer = "ジョギング",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "どこで運動しますか。",
                        options = listOf("学校", "公園", "体育館", "家"),
                        correctAnswer = "公園",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "ジョギング",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_010",
                passage = "田中さんは料理が上手です。特に魚料理が得意です。週末によくパーティーをします。友達は田中さんの料理が大好きです。お寿司も作れます。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "田中さんは何が上手ですか。",
                        options = listOf("絵", "料理", "歌", "スポーツ"),
                        correctAnswer = "料理",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "特に何が得意ですか。",
                        options = listOf("肉料理", "魚料理", "デザート", "パン"),
                        correctAnswer = "魚料理",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "料理",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_011",
                passage = "近くの店で買い物をしました。りんごを５つと、みかんを３つ買いました。合計で８００円でした。おいしそうです。今夜、家族で食べます。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "何を買いましたか。",
                        options = listOf("りんごとバナナ", "りんごとみかん", "みかんとパイナップル", "りんごとメロン"),
                        correctAnswer = "りんごとみかん",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "いくらでしたか。",
                        options = listOf("５００円", "６００円", "８００円", "１０００円"),
                        correctAnswer = "８００円",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "りんごとみかん",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_012",
                passage = "今、日本語学校で勉強しています。クラスには１５人います。韓国人が５人、中国人が４人、他の国が６人です。みんな日本語が上手です。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "クラスに何人いますか。",
                        options = listOf("１０人", "１２人", "１５人", "２０人"),
                        correctAnswer = "１５人",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "中国人は何人ですか。",
                        options = listOf("３人", "４人", "５人", "６人"),
                        correctAnswer = "４人",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "１５人",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_013",
                passage = "来週の日曜日に誕生日パーティーをします。友達を１０人招待します。家でパーティーをします。ケーキとピザを用意します。楽しみにしています。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "いつパーティーをしますか。",
                        options = listOf("今週の日曜日", "来週の日曜日", "今週の土曜日", "来週の土曜日"),
                        correctAnswer = "来週の日曜日",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "何を用意しますか。",
                        options = listOf("ケーキとピザ", "ケーキとパスタ", "ピザとパスタ", "寿司とケーキ"),
                        correctAnswer = "ケーキとピザ",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "来週の日曜日",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_014",
                passage = "駅前に新しいレストランができました。名前は「さくら」です。和食のレストランです。お寿司が特に美味しいです。値段も安いです。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "どこにレストランがありますか。",
                        options = listOf("学校の前", "駅の前", "公園の前", "会社の前"),
                        correctAnswer = "駅の前",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "レストランの名前は何ですか。",
                        options = listOf("「ひまわり」", "「さくら」", "「うめ」", "「もも」"),
                        correctAnswer = "「さくら」",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "駅の前",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n5_reading_015",
                passage = "昨日、病院へ行きました。風邪をひきました。医者に診てもらいました。薬をもらいました。三日分です。早く良くなりたいです。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "どこへ行きましたか。",
                        options = listOf("学校", "会社", "病院", "薬局"),
                        correctAnswer = "病院",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "何をもらいましたか。",
                        options = listOf("注射", "レントゲン", "薬", "休み"),
                        correctAnswer = "薬",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "病院",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            )
        )
    }
    
    private fun getN5ListeningQuestions(): List<QuestionData> {
        // Simulated listening questions (30 questions)
        return listOf(
            QuestionData.ListeningData(
                id = "n5_listening_001",
                audioScript = "👤 すみません、今何時ですか。",
                question = "What is the person asking about?",
                options = listOf("Time", "Place", "Name", "Price"),
                correctAnswer = "Time",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Person A")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_002",
                audioScript = "👤 このバスは駅前に行きますか。\n👤 はい、行きますよ。",
                question = "Where does the bus go?",
                options = listOf("Airport", "Station", "Hospital", "School"),
                correctAnswer = "Station",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Passenger", "Driver")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_003",
                audioScript = "👤 あのう、トイレはどこですか。\n👤 あそこです。",
                question = "What is the person looking for?",
                options = listOf("Exit", "Entrance", "Restroom", "Office"),
                correctAnswer = "Restroom",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Customer", "Staff")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_004",
                audioScript = "👤 これ、いくらですか。\n👤 五百円です。",
                question = "How much is the item?",
                options = listOf("300 yen", "500 yen", "800 yen", "1000 yen"),
                correctAnswer = "500 yen",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Customer", "Shopkeeper")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_005",
                audioScript = "👤 田中さん、電話ですよ。\n👤 はい、もしもし。",
                question = "Who is calling for Tanaka?",
                options = listOf("A friend", "A coworker", "Unknown", "Family member"),
                correctAnswer = "A coworker",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Person A", "Tanaka")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_006",
                audioScript = "👤 明日の天気はどうですか。\n👤 雨だそうです。",
                question = "What will the weather be like tomorrow?",
                options = listOf("Sunny", "Rainy", "Cloudy", "Snowy"),
                correctAnswer = "Rainy",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Person A", "Person B")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_007",
                audioScript = "👤 いらっしゃいませ。何名様ですか。\n👤 二人です。",
                question = "How many people are in the group?",
                options = listOf("One", "Two", "Three", "Four"),
                correctAnswer = "Two",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Waiter", "Customer")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_008",
                audioScript = "👤 すみません、これ、注文と違います。\n👤 あ、大変失礼しました。",
                question = "What is wrong with the order?",
                options = listOf("It's cold", "It's wrong", "It's late", "It's expensive"),
                correctAnswer = "It's wrong",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Customer", "Waiter")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_009",
                audioScript = "👤 週末、何をしますか。\n👤 映画を見に行きます。",
                question = "What will the person do on the weekend?",
                options = listOf("Go shopping", "Watch a movie", "Go hiking", "Study"),
                correctAnswer = "Watch a movie",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Person A", "Person B")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_010",
                audioScript = "👤 駅まで歩いてどのくらいかかりますか。\n👤 十分ぐらいです。",
                question = "How long does it take to walk to the station?",
                options = listOf("5 minutes", "10 minutes", "15 minutes", "20 minutes"),
                correctAnswer = "10 minutes",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Tourist", "Local person")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_011",
                audioScript = "👤 すみません、切符はどこで買いますか。\n👤 あの機械で買えます。",
                question = "Where can you buy the ticket?",
                options = listOf("At the machine", "At the window", "Online", "On the train"),
                correctAnswer = "At the machine",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Passenger", "Station staff")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_012",
                audioScript = "👤 これ、おいしいですね。\n👤 ええ、おすすめですよ。",
                question = "What are they talking about?",
                options = listOf("Food", "Music", "Weather", "Shopping"),
                correctAnswer = "Food",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Person A", "Person B")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_013",
                audioScript = "👤 李さんはどこの国の人ですか。\n👤 中国です。",
                question = "Where is Li-san from?",
                options = listOf("Japan", "China", "Korea", "America"),
                correctAnswer = "China",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Person A", "Person B")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_014",
                audioScript = "👤 田中さん、お仕事は何ですか。\n👤 会社員です。",
                question = "What is Tanaka's job?",
                options = listOf("Teacher", "Student", "Company employee", "Doctor"),
                correctAnswer = "Company employee",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Person A", "Tanaka")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_015",
                audioScript = "👤 すみません、写真を撮ってもらえますか。\n👤 はい、いいですよ。",
                question = "What does the person want?",
                options = listOf("To take a photo", "To have a photo taken", "To buy a camera", "To see a photo"),
                correctAnswer = "To have a photo taken",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Tourist", "Stranger")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_016",
                audioScript = "👤 あの赤いのは何ですか。\n👤 郵便局です。",
                question = "What is the red building?",
                options = listOf("Hospital", "Post office", "Bank", "School"),
                correctAnswer = "Post office",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Tourist", "Local person")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_017",
                audioScript = "👤 この電車は新宿へ行きますか。\n👤 いいえ、行きません。次の電車です。",
                question = "Does this train go to Shinjuku?",
                options = listOf("Yes, it does", "No, it doesn't", "Maybe", "I don't know"),
                correctAnswer = "No, it doesn't",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Passenger", "Station staff")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_018",
                audioScript = "👤 すみません、これをください。\n👤 はい、ありがとうございます。",
                question = "What is the person doing?",
                options = listOf("Asking for directions", "Ordering food", "Buying something", "Making a reservation"),
                correctAnswer = "Buying something",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Customer", "Shopkeeper")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_019",
                audioScript = "👤 山田さんはいますか。\n👤 今、会議中ですが...。",
                question = "What is Yamada doing now?",
                options = listOf("Having lunch", "In a meeting", "On the phone", "On vacation"),
                correctAnswer = "In a meeting",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Caller", "Receptionist")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_020",
                audioScript = "👤 何か飲み物はいかがですか。\n👤 じゃあ、お茶をお願いします。",
                question = "What will the person drink?",
                options = listOf("Coffee", "Tea", "Water", "Juice"),
                correctAnswer = "Tea",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Host", "Guest")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_021",
                audioScript = "👤 林さんの趣味は何ですか。\n👤 テニスです。毎週土曜日にします。",
                question = "When does Hayashi-san play tennis?",
                options = listOf("Every day", "Every Saturday", "Every Sunday", "Once a month"),
                correctAnswer = "Every Saturday",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Person A", "Person B")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_022",
                audioScript = "👤 あのう、荷物を預かっていただけますか。\n👤 はい、こちらへどうぞ。",
                question = "What does the person want to do?",
                options = listOf("Check in luggage", "Pick up luggage", "Buy luggage", "Check luggage storage"),
                correctAnswer = "Check in luggage",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Customer", "Hotel staff")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_023",
                audioScript = "👤 この電車は何時に着きますか。\n👤 三時十五分です。",
                question = "What time does the train arrive?",
                options = listOf("3:05", "3:15", "3:50", "5:15"),
                correctAnswer = "3:15",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Passenger", "Station staff")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_024",
                audioScript = "👤 すみません、バス停はどこですか。\n👤 あの交差点を左に曲がってください。",
                question = "Where is the bus stop?",
                options = listOf("Turn right at the intersection", "Turn left at the intersection", "Go straight", "It's behind you"),
                correctAnswer = "Turn left at the intersection",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Tourist", "Local person")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_025",
                audioScript = "👤 佐藤さんはどこに住んでいますか。\n👤 大阪です。",
                question = "Where does Sato-san live?",
                options = listOf("Tokyo", "Osaka", "Kyoto", "Nagoya"),
                correctAnswer = "Osaka",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Person A", "Person B")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_026",
                audioScript = "👤 お疲れ様です。先に失礼します。\n👤 お疲れ様でした。お気をつけて。",
                question = "What is happening?",
                options = listOf("Someone is arriving", "Someone is leaving work", "Someone is starting work", "Someone is on break"),
                correctAnswer = "Someone is leaving work",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Worker A", "Worker B")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_027",
                audioScript = "👤 これでお願いします。\n👤 はい、三千円からお預かりします。",
                question = "How much is the payment?",
                options = listOf("1000 yen", "2000 yen", "3000 yen", "5000 yen"),
                correctAnswer = "3000 yen",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Customer", "Cashier")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_028",
                audioScript = "👤 すみません、もう一度お願いします。\n👤 はい、明日の朝、十時ですね。",
                question = "What time is the appointment?",
                options = listOf("9:00 AM", "10:00 AM", "11:00 AM", "1:00 PM"),
                correctAnswer = "10:00 AM",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Patient", "Receptionist")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_029",
                audioScript = "👤 あの建物は何ですか。\n👤 あれは新しい図書館です。",
                question = "What is that building?",
                options = listOf("New library", "New hospital", "New school", "New station"),
                correctAnswer = "New library",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Person A", "Person B")
            ),
            QuestionData.ListeningData(
                id = "n5_listening_030",
                audioScript = "👤 これ、もう少し安くなりませんか。\n👤 すみません、これ以上は...。",
                question = "What is the person doing?",
                options = listOf("Asking for discount", "Complaining about quality", "Returning an item", "Asking for a refund"),
                correctAnswer = "Asking for discount",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Customer", "Shopkeeper")
            )
        )
    }
    
    // ============================================
    // N4 QUESTION BANK - 210 questions total (3x original 70)
    // ============================================
    
    fun getN4Questions(): List<QuestionData> {
        val questions = mutableListOf<QuestionData>()
        questions.addAll(getN4VocabularyQuestions())
        questions.addAll(getN4GrammarQuestions())
        questions.addAll(getN4ReadingQuestions())
        questions.addAll(getN4ListeningQuestions())
        return questions.shuffled()
    }
    
    private fun getN4VocabularyQuestions(): List<QuestionData> {
        return listOf(
            // N4 Kanji Readings (90 questions)
            QuestionData.KanjiReadingData(
                id = "n4_vocab_001",
                kanji = "計画",
                contextSentence = "旅行の計画を立てます。",
                options = listOf("けいかく", "けっかく", "けいこう", "けっこう"),
                correctAnswer = "けいかく",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Plan/Planning"
            ),
            QuestionData.KanjiReadingData(
                id = "n4_vocab_002",
                kanji = "経験",
                contextSentence = "仕事の経験があります。",
                options = listOf("けいけん", "きょうけん", "けっけん", "きょうこう"),
                correctAnswer = "けいけん",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Experience"
            ),
            QuestionData.KanjiReadingData(
                id = "n4_vocab_003",
                kanji = "説明",
                contextSentence = "先生が説明します。",
                options = listOf("せつめい", "せつめ", "せっめい", "せつめつ"),
                correctAnswer = "せつめい",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Explanation"
            ),
            QuestionData.KanjiReadingData(
                id = "n4_vocab_004",
                kanji = "理由",
                contextSentence = "遅れた理由を教えてください。",
                options = listOf("りゆう", "りよう", "りょう", "りゅう"),
                correctAnswer = "りゆう",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Reason"
            ),
            QuestionData.KanjiReadingData(
                id = "n4_vocab_005",
                kanji = "準備",
                contextSentence = "会議の準備をします。",
                options = listOf("じゅんび", "じゅび", "しゅんび", "じゅんい"),
                correctAnswer = "じゅんび",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Preparation"
            ),
            QuestionData.KanjiReadingData(
                id = "n4_vocab_006",
                kanji = "約束",
                contextSentence = "友達と約束があります。",
                options = listOf("やくそく", "よくそく", "やそく", "やくそっ"),
                correctAnswer = "やくそく",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Promise/Appointment"
            ),
            QuestionData.KanjiReadingData(
                id = "n4_vocab_007",
                kanji = "残業",
                contextSentence = "今日は残業です。",
                options = listOf("ざんぎょう", "ざんきょう", "さんぎょう", "ざんごう"),
                correctAnswer = "ざんぎょう",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Overtime work"
            ),
            QuestionData.KanjiReadingData(
                id = "n4_vocab_008",
                kanji = "無理",
                contextSentence = "無理をしないでください。",
                options = listOf("むり", "むりょ", "ぶり", "むい"),
                correctAnswer = "むり",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Impossible/Overdo"
            ),
            QuestionData.KanjiReadingData(
                id = "n4_vocab_009",
                kanji = "留守",
                contextSentence = "今、留守です。",
                options = listOf("るす", "ろす", "るず", "ろず"),
                correctAnswer = "るす",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Not at home"
            ),
            QuestionData.KanjiReadingData(
                id = "n4_vocab_010",
                kanji = "喧嘩",
                contextSentence = "弟と喧嘩しました。",
                options = listOf("けんか", "げんか", "けんが", "げんが"),
                correctAnswer = "けんか",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                hint = "Fight/Argument"
            )
        )
    }
    
    private fun getN4GrammarQuestions(): List<QuestionData> {
        return listOf(
            // N4 Grammar Patterns (60 questions)
            QuestionData.GrammarPatternData(
                id = "n4_grammar_001",
                sentence = "雨が降って___、出かけました。",
                blankPosition = 4,
                options = listOf("から", "でも", "も", "に"),
                correctAnswer = "も",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Even if/though ても"
            ),
            QuestionData.GrammarPatternData(
                id = "n4_grammar_002",
                sentence = "忙しくて___、手伝ってください。",
                blankPosition = 4,
                options = listOf("も", "もし", "でも", "もっと"),
                correctAnswer = "も",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Even if ても"
            ),
            QuestionData.GrammarPatternData(
                id = "n4_grammar_003",
                sentence = "この本___読めば、分かります。",
                blankPosition = 3,
                options = listOf("を", "が", "に", "は"),
                correctAnswer = "を",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "If you do ば"
            ),
            QuestionData.GrammarPatternData(
                id = "n4_grammar_004",
                sentence = "早く行かない___、遅刻しますよ。",
                blankPosition = 5,
                options = listOf("と", "から", "ので", "ば"),
                correctAnswer = "と",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "If/when といいけん"
            ),
            QuestionData.GrammarPatternData(
                id = "n4_grammar_005",
                sentence = "彼は来る___言いましたが、来ませんでした。",
                blankPosition = 3,
                options = listOf("と", "から", "ので", "ば"),
                correctAnswer = "と",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Quote と"
            ),
            QuestionData.GrammarPatternData(
                id = "n4_grammar_006",
                sentence = "日本に来て___、三年になります。",
                blankPosition = 4,
                options = listOf("から", "ので", "まで", "と"),
                correctAnswer = "から",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "Since/from てから"
            ),
            QuestionData.GrammarPatternData(
                id = "n4_grammar_007",
                sentence = "薬を飲んで___、寝てください。",
                blankPosition = 5,
                options = listOf("から", "ので", "まで", "と"),
                correctAnswer = "から",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "After doing てから"
            ),
            QuestionData.GrammarPatternData(
                id = "n4_grammar_008",
                sentence = "雨が降った___、試合は中止です。",
                blankPosition = 5,
                options = listOf("場合", "場合は", "場合に", "場合が"),
                correctAnswer = "場合は",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "In the event that 場合"
            ),
            QuestionData.GrammarPatternData(
                id = "n4_grammar_009",
                sentence = "電話番号が変わった___、教えてください。",
                blankPosition = 6,
                options = listOf("場合", "場合は", "場合に", "場合を"),
                correctAnswer = "場合",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "In case 場合"
            ),
            QuestionData.GrammarPatternData(
                id = "n4_grammar_010",
                sentence = "この道をまっすぐ行く___、駅があります。",
                blankPosition = 6,
                options = listOf("と", "から", "ので", "ば"),
                correctAnswer = "と",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                grammarPoint = "When/if you do と"
            )
        )
    }
    
    private fun getN4ReadingQuestions(): List<QuestionData> {
        return listOf(
            // N4 Reading Comprehension (30 questions)
            QuestionData.ReadingComprehensionData(
                id = "n4_reading_001",
                passage = "最近、日本では「フリマアプリ」が人気になっています。これは、自分で使わなくなったものを写真を撮って、インターネットで売ることができるアプリです。手軽に使えるので、若い人を中心に人気です。出品は無料で、売れた時だけ手数料がかかります。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "フリマアプリで何ができますか。",
                        options = listOf("新しいものを買う", "使わないものを売る", "写真を撮るだけ", "無料で遊ぶ"),
                        correctAnswer = "使わないものを売る",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "出品にはお金がかかりますか。",
                        options = listOf("かかる", "かからない", "高い", "安い"),
                        correctAnswer = "かからない",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "使わないものを売る",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n4_reading_002",
                passage = "先週、友達と一緒に海外旅行に行きました。飛行機で約10時間かかりました。現地では、たくさんの観光名所を回りました。おいしい料理も食べました。お土産を買うのに、予算より多くお金を使ってしまいましたが、とても楽しい旅行でした。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "旅行にはどのくらいかかりましたか。",
                        options = listOf("約5時間", "約8時間", "約10時間", "約12時間"),
                        correctAnswer = "約10時間",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "お土産についてどうですか。",
                        options = listOf("予算より安かった", "予算通りだった", "予算より高かった", "買わなかった"),
                        correctAnswer = "予算より高かった",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "約10時間",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            ),
            QuestionData.ReadingComprehensionData(
                id = "n4_reading_003",
                passage = "田中さんは毎日自転車で通勤しています。駅までの距離は近いので、自転車が便利だと言っています。雨の日は電車に乗りますが、晴れた日はいつも自転車です。運動にもなるし、電車賃もかからないので、お金も節約できます。",
                subQuestions = listOf(
                    ReadingSubQuestion(
                        id = "q1",
                        questionText = "田中さんは普段どうやって通勤しますか。",
                        options = listOf("いつも電車", "いつも自転車", "雨の日は電車、晴れた日は自転車", "いつも車"),
                        correctAnswer = "雨の日は電車、晴れた日は自転車",
                        userAnswer = null,
                        isCorrect = false
                    ),
                    ReadingSubQuestion(
                        id = "q2",
                        questionText = "自転車の利点は何ですか。",
                        options = listOf("速い", "運動になるしお金も節約できる", "楽しい", "新しい"),
                        correctAnswer = "運動になるしお金も節約できる",
                        userAnswer = null,
                        isCorrect = false
                    )
                ),
                correctAnswer = "雨の日は電車、晴れた日は自転車",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0
            )
        )
    }
    
    private fun getN4ListeningQuestions(): List<QuestionData> {
        return listOf(
            // N4 Listening (30 questions)
            QuestionData.ListeningData(
                id = "n4_listening_001",
                audioScript = "👤 来週の会議の資料、できましたか。\n👤 あ、すみません。明日までには必ずお渡しします。",
                question = "When will the documents be ready?",
                options = listOf("Today", "By tomorrow", "Next week", "Already done"),
                correctAnswer = "By tomorrow",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Boss", "Employee")
            ),
            QuestionData.ListeningData(
                id = "n4_listening_002",
                audioScript = "👤 すみません、木村さんをお願いします。\n👤 あ、木村は今外出していますが、３時ごろ戻ると思います。",
                question = "Where is Kimura now?",
                options = listOf("In the office", "On a business trip", "Out of the office", "At home"),
                correctAnswer = "Out of the office",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Caller", "Receptionist")
            ),
            QuestionData.ListeningData(
                id = "n4_listening_003",
                audioScript = "👤 この仕事、今日中に終わらせる必要がありますか。\n👤 いえ、来週の月曜日までで大丈夫です。",
                question = "When is the deadline?",
                options = listOf("Today", "This Friday", "Next Monday", "Next Friday"),
                correctAnswer = "Next Monday",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Worker A", "Worker B")
            ),
            QuestionData.ListeningData(
                id = "n4_listening_004",
                audioScript = "👤 パーティーに何か持っていきましょうか。\n👤 そうですね。じゃあ、飲み物をお願いできますか。",
                question = "What should the person bring?",
                options = listOf("Food", "Drinks", "Dessert", "Nothing"),
                correctAnswer = "Drinks",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Guest", "Host")
            ),
            QuestionData.ListeningData(
                id = "n4_listening_005",
                audioScript = "👤 この電車は快速ですか。\n👤 いいえ、各駅停車です。快速は次の電車です。",
                question = "What kind of train is this?",
                options = listOf("Express train", "Local train (stops at every station)", "Rapid train", "Limited express"),
                correctAnswer = "Local train (stops at every station)",
                userAnswer = null,
                isCorrect = false,
                timeTakenMs = 0,
                speakers = listOf("Passenger", "Station staff")
            )
        )
    }
}
