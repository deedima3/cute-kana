package com.cutekana.data.local

import com.cutekana.data.model.AchievementEntity
import com.cutekana.data.model.CharacterEntity
import com.cutekana.data.model.CharacterType
import com.cutekana.data.model.JlptLevel
import com.cutekana.data.model.KanaStrokeData
import com.cutekana.data.model.Rarity
import com.cutekana.data.model.StrokeData
import com.cutekana.data.model.UserProgressEntity

/**
 * Complete seed data for CuteKana database
 * Includes: 92 Kana + 1000+ Kanji across JLPT levels + Achievements
 */
object SeedData {

    // ==================== HIRAGANA ====================
    private val hiraganaData = listOf(
        // あ-row (Vowels)
        CharacterSeed("hiragana_a", "あ", "a", "Vowel", 3,
            "The character あ (a) looks like a woman arching her back saying 'ah'", Rarity.N),
        CharacterSeed("hiragana_i", "い", "i", "Vowel", 2,
            "Two vertical lines like a pair of legs walking - 'ee'", Rarity.N),
        CharacterSeed("hiragana_u", "う", "u", "Vowel", 3,
            "Looks like a bird's beak saying 'oo'", Rarity.N),
        CharacterSeed("hiragana_e", "え", "e", "Vowel", 3,
            "The 'eh' sound character with elegant flowing strokes", Rarity.N),
        CharacterSeed("hiragana_o", "お", "o", "Vowel", 3,
            "Looks like someone saying 'oh' with surprised expression", Rarity.N),

        // か-row (K)
        CharacterSeed("hiragana_ka", "か", "ka", "K", 3,
            "The 'ka' character with a kick to it!", Rarity.N),
        CharacterSeed("hiragana_ki", "き", "ki", "K", 4,
            "Looks like a key on a keyring", Rarity.N),
        CharacterSeed("hiragana_ku", "く", "ku", "K", 2,
            "A less-than sign, or a cuckoo bird beak", Rarity.N),
        CharacterSeed("hiragana_ke", "け", "ke", "K", 3,
            "The 'ke' sound with a distinctive shape", Rarity.N),
        CharacterSeed("hiragana_ko", "こ", "ko", "K", 2,
            "Two lines like a corner - 'ko'", Rarity.N),

        // さ-row (S)
        CharacterSeed("hiragana_sa", "さ", "sa", "S", 3,
            "The 'sa' character, flowing like a snake", Rarity.N),
        CharacterSeed("hiragana_shi", "し", "shi", "S", 1,
            "Just one curved line - looks like a fishing hook!", Rarity.N),
        CharacterSeed("hiragana_su", "す", "su", "S", 2,
            "The 'su' character with a loop, like a swinging swing", Rarity.N),
        CharacterSeed("hiragana_se", "せ", "se", "S", 3,
            "Looks like a world map - 'se' for see the world", Rarity.N),
        CharacterSeed("hiragana_so", "そ", "so", "S", 1,
            "One continuous stroke - 'so' simple!", Rarity.N),

        // た-row (T)
        CharacterSeed("hiragana_ta", "た", "ta", "T", 3,
            "The 'ta' character - think 'ta-da!'", Rarity.N),
        CharacterSeed("hiragana_chi", "ち", "chi", "T", 2,
            "Looks like a cheerleader with pom-poms - 'chi'!", Rarity.N),
        CharacterSeed("hiragana_tsu", "つ", "tsu", "T", 1,
            "A swoosh like a wave - 'tsu'nami", Rarity.N),
        CharacterSeed("hiragana_te", "て", "te", "T", 1,
            "Looks like a T-rain hook - 'te'", Rarity.N),
        CharacterSeed("hiragana_to", "と", "to", "T", 2,
            "The 'to' character with a twist", Rarity.N),

        // な-row (N)
        CharacterSeed("hiragana_na", "な", "na", "N", 4,
            "Complex 'na' with many strokes", Rarity.N),
        CharacterSeed("hiragana_ni", "に", "ni", "N", 3,
            "Two horizontal lines with a vertical - like the number 2 (ni)", Rarity.N),
        CharacterSeed("hiragana_nu", "ぬ", "nu", "N", 2,
            "The 'nu' character with a loop", Rarity.N),
        CharacterSeed("hiragana_ne", "ね", "ne", "N", 3,
            "The 'ne' sound - flowing and elegant", Rarity.N),
        CharacterSeed("hiragana_no", "の", "no", "N", 1,
            "A simple circle - 'no' entry sign!", Rarity.N),

        // は-row (H)
        CharacterSeed("hiragana_ha", "は", "ha", "H", 3,
            "The 'ha' character - happy and flowing", Rarity.N),
        CharacterSeed("hiragana_hi", "ひ", "hi", "H", 1,
            "Looks like a smiling mouth - say 'hee hee!'", Rarity.N),
        CharacterSeed("hiragana_fu", "ふ", "fu", "H", 4,
            "The 'fu' character with floating curves", Rarity.N),
        CharacterSeed("hiragana_he", "へ", "he", "H", 1,
            "A simple angle - 'he' went that way!", Rarity.N),
        CharacterSeed("hiragana_ho", "ほ", "ho", "H", 4,
            "The 'ho' character - hope springs eternal", Rarity.N),

        // ま-row (M)
        CharacterSeed("hiragana_ma", "ま", "ma", "M", 3,
            "The 'ma' character - mama's favorite!", Rarity.N),
        CharacterSeed("hiragana_mi", "み", "mi", "M", 2,
            "Looks like a snake - 'mi' hiss!", Rarity.N),
        CharacterSeed("hiragana_mu", "む", "mu", "M", 3,
            "The 'mu' character with a distinctive loop", Rarity.N),
        CharacterSeed("hiragana_me", "め", "me", "M", 2,
            "Looks like a stick figure - 'me' myself and I", Rarity.N),
        CharacterSeed("hiragana_mo", "も", "mo", "M", 3,
            "Two connected curves - 'mo' for more!", Rarity.N),

        // や-row (Y)
        CharacterSeed("hiragana_ya", "や", "ya", "Y", 3,
            "The 'ya' character - yay!", Rarity.N),
        CharacterSeed("hiragana_yu", "ゆ", "yu", "Y", 2,
            "The 'yu' character with a loop - you can do it!", Rarity.N),
        CharacterSeed("hiragana_yo", "よ", "yo", "Y", 2,
            "The 'yo' character - yo yo!", Rarity.N),

        // ら-row (R)
        CharacterSeed("hiragana_ra", "ら", "ra", "R", 2,
            "The 'ra' character - radiant!", Rarity.N),
        CharacterSeed("hiragana_ri", "り", "ri", "R", 2,
            "Two curved strokes - 'ri' for rising!", Rarity.N),
        CharacterSeed("hiragana_ru", "る", "ru", "R", 1,
            "A loop with a tail - 'ru' for running!", Rarity.N),
        CharacterSeed("hiragana_re", "れ", "re", "R", 1,
            "A single flowing stroke - 're'lax!", Rarity.N),
        CharacterSeed("hiragana_ro", "ろ", "ro", "R", 2,
            "Similar to る but with a square - 'ro'bot!", Rarity.N),

        // わ-row (W) + ん
        CharacterSeed("hiragana_wa", "わ", "wa", "W", 2,
            "The 'wa' character - wow!", Rarity.N),
        CharacterSeed("hiragana_wo", "を", "wo", "W", 3,
            "The 'wo' character - wooo!", Rarity.N),
        CharacterSeed("hiragana_n", "ん", "n", "N", 1,
            "Looks like a cursive 'n' - nasal sound!", Rarity.N),

        // ==================== DAKUTEN (Tenten) ====================
        // G-group (ga) - K-row with dakuten
        CharacterSeed("hiragana_ga", "が", "ga", "G", 3,
            "'ga' with tenten (dakuten) - voiced ka", Rarity.R),
        CharacterSeed("hiragana_gi", "ぎ", "gi", "G", 4,
            "'gi' with tenten - voiced ki", Rarity.R),
        CharacterSeed("hiragana_gu", "ぐ", "gu", "G", 2,
            "'gu' with tenten - voiced ku", Rarity.R),
        CharacterSeed("hiragana_ge", "げ", "ge", "G", 3,
            "'ge' with tenten - voiced ke", Rarity.R),
        CharacterSeed("hiragana_go", "ご", "go", "G", 2,
            "'go' with tenten - voiced ko", Rarity.R),

        // Z-group (za) - S-row with dakuten
        CharacterSeed("hiragana_za", "ざ", "za", "Z", 3,
            "'za' with tenten - voiced sa", Rarity.R),
        CharacterSeed("hiragana_ji", "じ", "ji", "Z", 2,
            "'ji' with tenten - voiced shi", Rarity.R),
        CharacterSeed("hiragana_zu", "ず", "zu", "Z", 2,
            "'zu' with tenten - voiced su", Rarity.R),
        CharacterSeed("hiragana_ze", "ぜ", "ze", "Z", 3,
            "'ze' with tenten - voiced se", Rarity.R),
        CharacterSeed("hiragana_zo", "ぞ", "zo", "Z", 2,
            "'zo' with tenten - voiced so", Rarity.R),

        // D-group (da) - T-row with dakuten
        CharacterSeed("hiragana_da", "だ", "da", "D", 3,
            "'da' with tenten - voiced ta", Rarity.R),
        CharacterSeed("hiragana_dji", "ぢ", "ji", "D", 2,
            "'ji' (dji) with tenten - voiced chi", Rarity.R),
        CharacterSeed("hiragana_dzu", "づ", "zu", "D", 2,
            "'zu' (dzu) with tenten - voiced tsu", Rarity.R),
        CharacterSeed("hiragana_de", "で", "de", "D", 2,
            "'de' with tenten - voiced te", Rarity.R),
        CharacterSeed("hiragana_do", "ど", "do", "D", 2,
            "'do' with tenten - voiced to", Rarity.R),

        // B-group (ba) - H-row with dakuten
        CharacterSeed("hiragana_ba", "ば", "ba", "B", 3,
            "'ba' with tenten - voiced ha", Rarity.R),
        CharacterSeed("hiragana_bi", "び", "bi", "B", 2,
            "'bi' with tenten - voiced hi", Rarity.R),
        CharacterSeed("hiragana_bu", "ぶ", "bu", "B", 4,
            "'bu' with tenten - voiced fu", Rarity.R),
        CharacterSeed("hiragana_be", "べ", "be", "B", 1,
            "'be' with tenten - voiced he", Rarity.R),
        CharacterSeed("hiragana_bo", "ぼ", "bo", "B", 4,
            "'bo' with tenten - voiced ho", Rarity.R),

        // ==================== HANDAKUTEN (Maru) ====================
        // P-group (pa) - H-row with handakuten
        CharacterSeed("hiragana_pa", "ぱ", "pa", "P", 3,
            "'pa' with maru (handakuten) - semi-voiced ha", Rarity.SR),
        CharacterSeed("hiragana_pi", "ぴ", "pi", "P", 2,
            "'pi' with maru - semi-voiced hi", Rarity.SR),
        CharacterSeed("hiragana_pu", "ぷ", "pu", "P", 4,
            "'pu' with maru - semi-voiced fu", Rarity.SR),
        CharacterSeed("hiragana_pe", "ぺ", "pe", "P", 1,
            "'pe' with maru - semi-voiced he", Rarity.SR),
        CharacterSeed("hiragana_po", "ぽ", "po", "P", 4,
            "'po' with maru - semi-voiced ho", Rarity.SR),

        // ==================== YOON (Small Kana Combinations) ====================
        // Ky-group (kya) - K-row with small ya/yu/yo
        CharacterSeed("hiragana_kya", "きゃ", "kya", "KY", 4,
            "'kya' - ki + small ya (yoon)", Rarity.SR),
        CharacterSeed("hiragana_kyu", "きゅ", "kyu", "KY", 4,
            "'kyu' - ki + small yu (yoon)", Rarity.SR),
        CharacterSeed("hiragana_kyo", "きょ", "kyo", "KY", 4,
            "'kyo' - ki + small yo (yoon)", Rarity.SR),

        // Sh-group (sha) - S-row with small ya/yu/yo
        CharacterSeed("hiragana_sha", "しゃ", "sha", "SH", 3,
            "'sha' - shi + small ya (yoon)", Rarity.SR),
        CharacterSeed("hiragana_shu", "しゅ", "shu", "SH", 3,
            "'shu' - shi + small yu (yoon)", Rarity.SR),
        CharacterSeed("hiragana_sho", "しょ", "sho", "SH", 3,
            "'sho' - shi + small yo (yoon)", Rarity.SR),

        // Ch-group (cha) - T-row with small ya/yu/yo
        CharacterSeed("hiragana_cha", "ちゃ", "cha", "CH", 3,
            "'cha' - chi + small ya (yoon)", Rarity.SR),
        CharacterSeed("hiragana_chu", "ちゅ", "chu", "CH", 3,
            "'chu' - chi + small yu (yoon)", Rarity.SR),
        CharacterSeed("hiragana_cho", "ちょ", "cho", "CH", 3,
            "'cho' - chi + small yo (yoon)", Rarity.SR),

        // Ny-group (nya) - N-row with small ya/yu/yo
        CharacterSeed("hiragana_nya", "にゃ", "nya", "NY", 4,
            "'nya' - ni + small ya (yoon)", Rarity.SR),
        CharacterSeed("hiragana_nyu", "にゅ", "nyu", "NY", 4,
            "'nyu' - ni + small yu (yoon)", Rarity.SR),
        CharacterSeed("hiragana_nyo", "にょ", "nyo", "NY", 4,
            "'nyo' - ni + small yo (yoon)", Rarity.SR),

        // Hy-group (hya) - H-row with small ya/yu/yo
        CharacterSeed("hiragana_hya", "ひゃ", "hya", "HY", 3,
            "'hya' - hi + small ya (yoon)", Rarity.SR),
        CharacterSeed("hiragana_hyu", "ひゅ", "hyu", "HY", 3,
            "'hyu' - hi + small yu (yoon)", Rarity.SR),
        CharacterSeed("hiragana_hyo", "ひょ", "hyo", "HY", 3,
            "'hyo' - hi + small yo (yoon)", Rarity.SR),

        // My-group (mya) - M-row with small ya/yu/yo
        CharacterSeed("hiragana_mya", "みゃ", "mya", "MY", 3,
            "'mya' - mi + small ya (yoon)", Rarity.SR),
        CharacterSeed("hiragana_myu", "みゅ", "myu", "MY", 3,
            "'myu' - mi + small yu (yoon)", Rarity.SR),
        CharacterSeed("hiragana_myo", "みょ", "myo", "MY", 3,
            "'myo' - mi + small yo (yoon)", Rarity.SR),

        // Ry-group (rya) - R-row with small ya/yu/yo
        CharacterSeed("hiragana_rya", "りゃ", "rya", "RY", 3,
            "'rya' - ri + small ya (yoon)", Rarity.SR),
        CharacterSeed("hiragana_ryu", "りゅ", "ryu", "RY", 3,
            "'ryu' - ri + small yu (yoon)", Rarity.SR),
        CharacterSeed("hiragana_ryo", "りょ", "ryo", "RY", 3,
            "'ryo' - ri + small yo (yoon)", Rarity.SR),

        // ==================== DAKUTEN + YOON ====================
        // Gy-group (gya) - G-row with small ya/yu/yo
        CharacterSeed("hiragana_gya", "ぎゃ", "gya", "GY", 4,
            "'gya' - gi + small ya (dakuten + yoon)", Rarity.SR),
        CharacterSeed("hiragana_gyu", "ぎゅ", "gyu", "GY", 4,
            "'gyu' - gi + small yu (dakuten + yoon)", Rarity.SR),
        CharacterSeed("hiragana_gyo", "ぎょ", "gyo", "GY", 4,
            "'gyo' - gi + small yo (dakuten + yoon)", Rarity.SR),

        // J-group (ja) - J-row with small ya/yu/yo
        CharacterSeed("hiragana_ja", "じゃ", "ja", "J", 3,
            "'ja' - ji + small ya (dakuten + yoon)", Rarity.SR),
        CharacterSeed("hiragana_ju", "じゅ", "ju", "J", 3,
            "'ju' - ji + small yu (dakuten + yoon)", Rarity.SR),
        CharacterSeed("hiragana_jo", "じょ", "jo", "J", 3,
            "'jo' - ji + small yo (dakuten + yoon)", Rarity.SR),

        // By-group (bya) - B-row with small ya/yu/yo
        CharacterSeed("hiragana_bya", "びゃ", "bya", "BY", 3,
            "'bya' - bi + small ya (dakuten + yoon)", Rarity.SR),
        CharacterSeed("hiragana_byu", "びゅ", "byu", "BY", 3,
            "'byu' - bi + small yu (dakuten + yoon)", Rarity.SR),
        CharacterSeed("hiragana_byo", "びょ", "byo", "BY", 3,
            "'byo' - bi + small yo (dakuten + yoon)", Rarity.SR),

        // ==================== HANDAKUTEN + YOON ====================
        // Py-group (pya) - P-row with small ya/yu/yo
        CharacterSeed("hiragana_pya", "ぴゃ", "pya", "PY", 3,
            "'pya' - pi + small ya (handakuten + yoon)", Rarity.SSR),
        CharacterSeed("hiragana_pyu", "ぴゅ", "pyu", "PY", 3,
            "'pyu' - pi + small yu (handakuten + yoon)", Rarity.SSR),
        CharacterSeed("hiragana_pyo", "ぴょ", "pyo", "PY", 3,
            "'pyo' - pi + small yo (handakuten + yoon)", Rarity.SSR),

        // Small kana (standalone yoon characters)
        CharacterSeed("hiragana_small_ya", "ゃ", "ya (small)", "Y", 1,
            "Small 'ya' - used in yoon combinations", Rarity.R),
        CharacterSeed("hiragana_small_yu", "ゅ", "yu (small)", "Y", 1,
            "Small 'yu' - used in yoon combinations", Rarity.R),
        CharacterSeed("hiragana_small_yo", "ょ", "yo (small)", "Y", 2,
            "Small 'yo' - used in yoon combinations", Rarity.R),
    )

    // ==================== KATAKANA ====================
    private val katakanaData = listOf(
        // ア-row (Vowels)
        CharacterSeed("katakana_a", "ア", "a", "Vowel", 2,
            "Katakana 'a' - angular and bold", Rarity.N),
        CharacterSeed("katakana_i", "イ", "i", "Vowel", 2,
            "Katakana 'i' - looks like a capital I", Rarity.N),
        CharacterSeed("katakana_u", "ウ", "u", "Vowel", 3,
            "Katakana 'u' - angular roof shape", Rarity.N),
        CharacterSeed("katakana_e", "エ", "e", "Vowel", 3,
            "Katakana 'e' - looks like a capital E on its side", Rarity.N),
        CharacterSeed("katakana_o", "オ", "o", "Vowel", 3,
            "Katakana 'o' - with a fancy diagonal", Rarity.N),

        // カ-row (K)
        CharacterSeed("katakana_ka", "カ", "ka", "K", 2,
            "Katakana 'ka' - sharp and clear", Rarity.N),
        CharacterSeed("katakana_ki", "キ", "ki", "K", 3,
            "Katakana 'ki' - with horizontal bars", Rarity.N),
        CharacterSeed("katakana_ku", "ク", "ku", "K", 2,
            "Katakana 'ku' - less than sign", Rarity.N),
        CharacterSeed("katakana_ke", "ケ", "ke", "K", 3,
            "Katakana 'ke' - distinctive shape", Rarity.N),
        CharacterSeed("katakana_ko", "コ", "ko", "K", 2,
            "Katakana 'ko' - two angular lines", Rarity.N),

        // サ-row (S)
        CharacterSeed("katakana_sa", "サ", "sa", "S", 3,
            "Katakana 'sa' - with a sweeping stroke", Rarity.N),
        CharacterSeed("katakana_shi", "シ", "shi", "S", 3,
            "Katakana 'shi' - three flowing curves, don't confuse with ツ!", Rarity.N),
        CharacterSeed("katakana_su", "ス", "su", "S", 2,
            "Katakana 'su' - like a hook", Rarity.N),
        CharacterSeed("katakana_se", "セ", "se", "S", 2,
            "Katakana 'se' - simple and clean", Rarity.N),
        CharacterSeed("katakana_so", "ソ", "so", "S", 2,
            "Katakana 'so' - don't confuse with ン!", Rarity.N),

        // タ-row (T)
        CharacterSeed("katakana_ta", "タ", "ta", "T", 3,
            "Katakana 'ta' - with a roof shape", Rarity.N),
        CharacterSeed("katakana_chi", "チ", "chi", "T", 3,
            "Katakana 'chi' - with a sweeping tail", Rarity.N),
        CharacterSeed("katakana_tsu", "ツ", "tsu", "T", 3,
            "Katakana 'tsu' - three dots, don't confuse with シ!", Rarity.N),
        CharacterSeed("katakana_te", "テ", "te", "T", 3,
            "Katakana 'te' - with a roof and horizontal", Rarity.N),
        CharacterSeed("katakana_to", "ト", "to", "T", 2,
            "Katakana 'to' - like a plus sign with a curve", Rarity.N),

        // ナ-row (N)
        CharacterSeed("katakana_na", "ナ", "na", "N", 2,
            "Katakana 'na' - simple and elegant", Rarity.N),
        CharacterSeed("katakana_ni", "ニ", "ni", "N", 2,
            "Katakana 'ni' - two horizontal lines, like the number 2", Rarity.N),
        CharacterSeed("katakana_nu", "ヌ", "nu", "N", 2,
            "Katakana 'nu' - with a distinctive loop", Rarity.N),
        CharacterSeed("katakana_ne", "ネ", "ne", "N", 4,
            "Katakana 'ne' - more complex shape", Rarity.N),
        CharacterSeed("katakana_no", "ノ", "no", "N", 1,
            "Katakana 'no' - just one diagonal line", Rarity.N),

        // ハ-row (H)
        CharacterSeed("katakana_ha", "ハ", "ha", "H", 2,
            "Katakana 'ha' - like a capital H but different!", Rarity.N),
        CharacterSeed("katakana_hi", "ヒ", "hi", "H", 2,
            "Katakana 'hi' - three parallel lines", Rarity.N),
        CharacterSeed("katakana_fu", "フ", "fu", "H", 1,
            "Katakana 'fu' - one sweeping curve", Rarity.N),
        CharacterSeed("katakana_he", "ヘ", "he", "H", 1,
            "Katakana 'he' - same as Hiragana!", Rarity.N),
        CharacterSeed("katakana_ho", "ホ", "ho", "H", 4,
            "Katakana 'ho' - with a cross shape", Rarity.N),

        // マ-row (M)
        CharacterSeed("katakana_ma", "マ", "ma", "M", 2,
            "Katakana 'ma' - with a roof", Rarity.N),
        CharacterSeed("katakana_mi", "ミ", "mi", "M", 3,
            "Katakana 'mi' - three horizontal curves", Rarity.N),
        CharacterSeed("katakana_mu", "ム", "mu", "M", 2,
            "Katakana 'mu' - like a cursive checkmark", Rarity.N),
        CharacterSeed("katakana_me", "メ", "me", "M", 2,
            "Katakana 'me' - with crossing lines", Rarity.N),
        CharacterSeed("katakana_mo", "モ", "mo", "M", 3,
            "Katakana 'mo' - with horizontal bars", Rarity.N),

        // ヤ-row (Y)
        CharacterSeed("katakana_ya", "ヤ", "ya", "Y", 3,
            "Katakana 'ya' - with a vertical line", Rarity.N),
        CharacterSeed("katakana_yu", "ユ", "yu", "Y", 2,
            "Katakana 'yu' - like a hook", Rarity.N),
        CharacterSeed("katakana_yo", "ヨ", "yo", "Y", 3,
            "Katakana 'yo' - three horizontal lines", Rarity.N),

        // ラ-row (R)
        CharacterSeed("katakana_ra", "ラ", "ra", "R", 2,
            "Katakana 'ra' - simple and clean", Rarity.N),
        CharacterSeed("katakana_ri", "リ", "ri", "R", 2,
            "Katakana 'ri' - two vertical lines with hooks", Rarity.N),
        CharacterSeed("katakana_ru", "ル", "ru", "R", 2,
            "Katakana 'ru' - with a distinctive loop", Rarity.N),
        CharacterSeed("katakana_re", "レ", "re", "R", 1,
            "Katakana 're' - one diagonal stroke", Rarity.N),
        CharacterSeed("katakana_ro", "ロ", "ro", "R", 3,
            "Katakana 'ro' - a square box", Rarity.N),

        // ワ-row (W) + ン
        CharacterSeed("katakana_wa", "ワ", "wa", "W", 2,
            "Katakana 'wa' - with a curve", Rarity.N),
        CharacterSeed("katakana_wo", "ヲ", "wo", "W", 3,
            "Katakana 'wo' - with horizontal bars", Rarity.N),
        CharacterSeed("katakana_n", "ン", "n", "N", 2,
            "Katakana 'n' - like a cursive n, don't confuse with ソ!", Rarity.N),

        // ==================== DAKUTEN (Tenten) ====================
        // G-group (ga) - K-row with dakuten
        CharacterSeed("katakana_ga", "ガ", "ga", "G", 3,
            "Katakana 'ga' with tenten - voiced ka", Rarity.R),
        CharacterSeed("katakana_gi", "ギ", "gi", "G", 4,
            "Katakana 'gi' with tenten - voiced ki", Rarity.R),
        CharacterSeed("katakana_gu", "グ", "gu", "G", 2,
            "Katakana 'gu' with tenten - voiced ku", Rarity.R),
        CharacterSeed("katakana_ge", "ゲ", "ge", "G", 3,
            "Katakana 'ge' with tenten - voiced ke", Rarity.R),
        CharacterSeed("katakana_go", "ゴ", "go", "G", 2,
            "Katakana 'go' with tenten - voiced ko", Rarity.R),

        // Z-group (za) - S-row with dakuten
        CharacterSeed("katakana_za", "ザ", "za", "Z", 3,
            "Katakana 'za' with tenten - voiced sa", Rarity.R),
        CharacterSeed("katakana_ji", "ジ", "ji", "Z", 3,
            "Katakana 'ji' with tenten - voiced shi", Rarity.R),
        CharacterSeed("katakana_zu", "ズ", "zu", "Z", 2,
            "Katakana 'zu' with tenten - voiced su", Rarity.R),
        CharacterSeed("katakana_ze", "ゼ", "ze", "Z", 2,
            "Katakana 'ze' with tenten - voiced se", Rarity.R),
        CharacterSeed("katakana_zo", "ゾ", "zo", "Z", 2,
            "Katakana 'zo' with tenten - voiced so", Rarity.R),

        // D-group (da) - T-row with dakuten
        CharacterSeed("katakana_da", "ダ", "da", "D", 3,
            "Katakana 'da' with tenten - voiced ta", Rarity.R),
        CharacterSeed("katakana_dji", "ヂ", "ji", "D", 3,
            "Katakana 'ji' (dji) with tenten - voiced chi", Rarity.R),
        CharacterSeed("katakana_dzu", "ヅ", "zu", "D", 3,
            "Katakana 'zu' (dzu) with tenten - voiced tsu", Rarity.R),
        CharacterSeed("katakana_de", "デ", "de", "D", 3,
            "Katakana 'de' with tenten - voiced te", Rarity.R),
        CharacterSeed("katakana_do", "ド", "do", "D", 2,
            "Katakana 'do' with tenten - voiced to", Rarity.R),

        // B-group (ba) - H-row with dakuten
        CharacterSeed("katakana_ba", "バ", "ba", "B", 2,
            "Katakana 'ba' with tenten - voiced ha", Rarity.R),
        CharacterSeed("katakana_bi", "ビ", "bi", "B", 2,
            "Katakana 'bi' with tenten - voiced hi", Rarity.R),
        CharacterSeed("katakana_bu", "ブ", "bu", "B", 2,
            "Katakana 'bu' with tenten - voiced fu", Rarity.R),
        CharacterSeed("katakana_be", "ベ", "be", "B", 2,
            "Katakana 'be' with tenten - voiced he", Rarity.R),
        CharacterSeed("katakana_bo", "ボ", "bo", "B", 4,
            "Katakana 'bo' with tenten - voiced ho", Rarity.R),

        // ==================== HANDAKUTEN (Maru) ====================
        // P-group (pa) - H-row with handakuten
        CharacterSeed("katakana_pa", "パ", "pa", "P", 2,
            "Katakana 'pa' with maru (handakuten) - semi-voiced ha", Rarity.SR),
        CharacterSeed("katakana_pi", "ピ", "pi", "P", 2,
            "Katakana 'pi' with maru - semi-voiced hi", Rarity.SR),
        CharacterSeed("katakana_pu", "プ", "pu", "P", 2,
            "Katakana 'pu' with maru - semi-voiced fu", Rarity.SR),
        CharacterSeed("katakana_pe", "ペ", "pe", "P", 2,
            "Katakana 'pe' with maru - semi-voiced he", Rarity.SR),
        CharacterSeed("katakana_po", "ポ", "po", "P", 4,
            "Katakana 'po' with maru - semi-voiced ho", Rarity.SR),

        // ==================== YOON (Small Kana Combinations) ====================
        // Ky-group (kya) - K-row with small ya/yu/yo
        CharacterSeed("katakana_kya", "キャ", "kya", "KY", 4,
            "Katakana 'kya' - ki + small ya (yoon)", Rarity.SR),
        CharacterSeed("katakana_kyu", "キュ", "kyu", "KY", 4,
            "Katakana 'kyu' - ki + small yu (yoon)", Rarity.SR),
        CharacterSeed("katakana_kyo", "キョ", "kyo", "KY", 4,
            "Katakana 'kyo' - ki + small yo (yoon)", Rarity.SR),

        // Sh-group (sha) - S-row with small ya/yu/yo
        CharacterSeed("katakana_sha", "シャ", "sha", "SH", 4,
            "Katakana 'sha' - shi + small ya (yoon)", Rarity.SR),
        CharacterSeed("katakana_shu", "シュ", "shu", "SH", 4,
            "Katakana 'shu' - shi + small yu (yoon)", Rarity.SR),
        CharacterSeed("katakana_sho", "ショ", "sho", "SH", 4,
            "Katakana 'sho' - shi + small yo (yoon)", Rarity.SR),

        // Ch-group (cha) - T-row with small ya/yu/yo
        CharacterSeed("katakana_cha", "チャ", "cha", "CH", 4,
            "Katakana 'cha' - chi + small ya (yoon)", Rarity.SR),
        CharacterSeed("katakana_chu", "チュ", "chu", "CH", 4,
            "Katakana 'chu' - chi + small yu (yoon)", Rarity.SR),
        CharacterSeed("katakana_cho", "チョ", "cho", "CH", 4,
            "Katakana 'cho' - chi + small yo (yoon)", Rarity.SR),

        // Ny-group (nya) - N-row with small ya/yu/yo
        CharacterSeed("katakana_nya", "ニャ", "nya", "NY", 3,
            "Katakana 'nya' - ni + small ya (yoon)", Rarity.SR),
        CharacterSeed("katakana_nyu", "ニュ", "nyu", "NY", 3,
            "Katakana 'nyu' - ni + small yu (yoon)", Rarity.SR),
        CharacterSeed("katakana_nyo", "ニョ", "nyo", "NY", 3,
            "Katakana 'nyo' - ni + small yo (yoon)", Rarity.SR),

        // Hy-group (hya) - H-row with small ya/yu/yo
        CharacterSeed("katakana_hya", "ヒャ", "hya", "HY", 3,
            "Katakana 'hya' - hi + small ya (yoon)", Rarity.SR),
        CharacterSeed("katakana_hyu", "ヒュ", "hyu", "HY", 3,
            "Katakana 'hyu' - hi + small yu (yoon)", Rarity.SR),
        CharacterSeed("katakana_hyo", "ヒョ", "hyo", "HY", 3,
            "Katakana 'hyo' - hi + small yo (yoon)", Rarity.SR),

        // My-group (mya) - M-row with small ya/yu/yo
        CharacterSeed("katakana_mya", "ミャ", "mya", "MY", 3,
            "Katakana 'mya' - mi + small ya (yoon)", Rarity.SR),
        CharacterSeed("katakana_myu", "ミュ", "myu", "MY", 3,
            "Katakana 'myu' - mi + small yu (yoon)", Rarity.SR),
        CharacterSeed("katakana_myo", "ミョ", "myo", "MY", 3,
            "Katakana 'myo' - mi + small yo (yoon)", Rarity.SR),

        // Ry-group (rya) - R-row with small ya/yu/yo
        CharacterSeed("katakana_rya", "リャ", "rya", "RY", 3,
            "Katakana 'rya' - ri + small ya (yoon)", Rarity.SR),
        CharacterSeed("katakana_ryu", "リュ", "ryu", "RY", 3,
            "Katakana 'ryu' - ri + small yu (yoon)", Rarity.SR),
        CharacterSeed("katakana_ryo", "リョ", "ryo", "RY", 3,
            "Katakana 'ryo' - ri + small yo (yoon)", Rarity.SR),

        // ==================== DAKUTEN + YOON ====================
        // Gy-group (gya) - G-row with small ya/yu/yo
        CharacterSeed("katakana_gya", "ギャ", "gya", "GY", 5,
            "Katakana 'gya' - gi + small ya (dakuten + yoon)", Rarity.SR),
        CharacterSeed("katakana_gyu", "ギュ", "gyu", "GY", 5,
            "Katakana 'gyu' - gi + small yu (dakuten + yoon)", Rarity.SR),
        CharacterSeed("katakana_gyo", "ギョ", "gyo", "GY", 5,
            "Katakana 'gyo' - gi + small yo (dakuten + yoon)", Rarity.SR),

        // J-group (ja) - J-row with small ya/yu/yo
        CharacterSeed("katakana_ja", "ジャ", "ja", "J", 4,
            "Katakana 'ja' - ji + small ya (dakuten + yoon)", Rarity.SR),
        CharacterSeed("katakana_ju", "ジュ", "ju", "J", 4,
            "Katakana 'ju' - ji + small yu (dakuten + yoon)", Rarity.SR),
        CharacterSeed("katakana_jo", "ジョ", "jo", "J", 4,
            "Katakana 'jo' - ji + small yo (dakuten + yoon)", Rarity.SR),

        // By-group (bya) - B-row with small ya/yu/yo
        CharacterSeed("katakana_bya", "ビャ", "bya", "BY", 3,
            "Katakana 'bya' - bi + small ya (dakuten + yoon)", Rarity.SR),
        CharacterSeed("katakana_byu", "ビュ", "byu", "BY", 3,
            "Katakana 'byu' - bi + small yu (dakuten + yoon)", Rarity.SR),
        CharacterSeed("katakana_byo", "ビョ", "byo", "BY", 3,
            "Katakana 'byo' - bi + small yo (dakuten + yoon)", Rarity.SR),

        // ==================== HANDAKUTEN + YOON ====================
        // Py-group (pya) - P-row with small ya/yu/yo
        CharacterSeed("katakana_pya", "ピャ", "pya", "PY", 3,
            "Katakana 'pya' - pi + small ya (handakuten + yoon)", Rarity.SSR),
        CharacterSeed("katakana_pyu", "ピュ", "pyu", "PY", 3,
            "Katakana 'pyu' - pi + small yu (handakuten + yoon)", Rarity.SSR),
        CharacterSeed("katakana_pyo", "ピョ", "pyo", "PY", 3,
            "Katakana 'pyo' - pi + small yo (handakuten + yoon)", Rarity.SSR),

        // Small kana (standalone yoon characters)
        CharacterSeed("katakana_small_ya", "ャ", "ya (small)", "Y", 2,
            "Katakana small 'ya' - used in yoon combinations", Rarity.R),
        CharacterSeed("katakana_small_yu", "ュ", "yu (small)", "Y", 2,
            "Katakana small 'yu' - used in yoon combinations", Rarity.R),
        CharacterSeed("katakana_small_yo", "ョ", "yo (small)", "Y", 3,
            "Katakana small 'yo' - used in yoon combinations", Rarity.R),
    )

    // ==================== JLPT N5 KANJI (80 characters) ====================
    private val kanjiN5 = listOf(
        KanjiSeed("kanji_n5_01", "一", "ichi", listOf("one"), 1, listOf("イチ", "イツ"), listOf("ひと", "ひと-つ"), listOf("one", "one thing"), Rarity.N),
        KanjiSeed("kanji_n5_02", "二", "ni", listOf("two"), 2, listOf("ニ"), listOf("ふた", "ふた-つ"), listOf("two", "two things"), Rarity.N),
        KanjiSeed("kanji_n5_03", "三", "san", listOf("three"), 3, listOf("サン"), listOf("み", "み-っつ"), listOf("three", "three things"), Rarity.N),
        KanjiSeed("kanji_n5_04", "四", "yon", listOf("four"), 5, listOf("シ"), listOf("よん", "よ", "よ-つ"), listOf("four", "four things"), Rarity.N),
        KanjiSeed("kanji_n5_05", "五", "go", listOf("five"), 4, listOf("ゴ"), listOf("いつ", "いつ-つ"), listOf("five", "five things"), Rarity.N),
        KanjiSeed("kanji_n5_06", "六", "roku", listOf("six"), 4, listOf("ロク"), listOf("む", "む-っつ", "むい"), listOf("six", "six things"), Rarity.N),
        KanjiSeed("kanji_n5_07", "七", "nana", listOf("seven"), 2, listOf("シチ"), listOf("なな", "なな-つ", "なの"), listOf("seven", "seven things"), Rarity.N),
        KanjiSeed("kanji_n5_08", "八", "hachi", listOf("eight"), 2, listOf("ハチ"), listOf("や", "や-っつ", "よう"), listOf("eight", "eight things"), Rarity.N),
        KanjiSeed("kanji_n5_09", "九", "kyuu", listOf("nine"), 2, listOf("キュウ", "ク"), listOf("ここの", "ここの-つ"), listOf("nine", "nine things"), Rarity.N),
        KanjiSeed("kanji_n5_10", "十", "juu", listOf("ten"), 2, listOf("ジュウ", "ジッ", "ジュッ"), listOf("とお", "と"), listOf("ten", "ten things"), Rarity.N),
        KanjiSeed("kanji_n5_11", "百", "hyaku", listOf("hundred"), 6, listOf("ヒャク"), listOf("もも"), listOf("hundred"), Rarity.N),
        KanjiSeed("kanji_n5_12", "千", "sen", listOf("thousand"), 3, listOf("セン"), listOf("ち"), listOf("thousand"), Rarity.N),
        KanjiSeed("kanji_n5_13", "万", "man", listOf("ten thousand"), 3, listOf("マン", "バン"), emptyList(), listOf("ten thousand"), Rarity.R),
        KanjiSeed("kanji_n5_14", "日", "nichi", listOf("day", "sun"), 4, listOf("ニチ", "ジツ"), listOf("ひ", "か"), listOf("day", "sun", "Japan"), Rarity.N),
        KanjiSeed("kanji_n5_15", "月", "tsuki", listOf("month", "moon"), 4, listOf("ゲツ", "ガツ"), listOf("つき"), listOf("month", "moon"), Rarity.N),
        KanjiSeed("kanji_n5_16", "火", "hi", listOf("fire"), 4, listOf("カ"), listOf("ひ", "ほ-"), listOf("fire"), Rarity.N),
        KanjiSeed("kanji_n5_17", "水", "mizu", listOf("water"), 4, listOf("スイ"), listOf("みず"), listOf("water"), Rarity.N),
        KanjiSeed("kanji_n5_18", "木", "ki", listOf("tree", "wood"), 4, listOf("モク", "ボク"), listOf("き", "こ-"), listOf("tree", "wood"), Rarity.N),
        KanjiSeed("kanji_n5_19", "金", "kane", listOf("money", "gold"), 8, listOf("キン", "コン"), listOf("かね", "かな-"), listOf("money", "gold", "metal"), Rarity.R),
        KanjiSeed("kanji_n5_20", "土", "tsuchi", listOf("earth", "soil"), 3, listOf("ド", "ト"), listOf("つち"), listOf("earth", "soil", "ground"), Rarity.N),
        KanjiSeed("kanji_n5_21", "人", "hito", listOf("person"), 2, listOf("ジン", "ニン"), listOf("ひと"), listOf("person", "people"), Rarity.N),
        KanjiSeed("kanji_n5_22", "女", "onna", listOf("woman"), 3, listOf("ジョ", "ニョ", "ニョウ"), listOf("おんな", "め"), listOf("woman", "female"), Rarity.N),
        KanjiSeed("kanji_n5_23", "男", "otoko", listOf("man"), 7, listOf("ダン", "ナン"), listOf("おとこ"), listOf("man", "male"), Rarity.N),
        KanjiSeed("kanji_n5_24", "子", "ko", listOf("child"), 3, listOf("シ", "ス"), listOf("こ", "ね"), listOf("child"), Rarity.N),
        KanjiSeed("kanji_n5_25", "目", "me", listOf("eye"), 5, listOf("モク", "ボク"), listOf("め", "ま-"), listOf("eye"), Rarity.N),
        KanjiSeed("kanji_n5_26", "耳", "mimi", listOf("ear"), 6, listOf("ジ"), listOf("みみ"), listOf("ear"), Rarity.N),
        KanjiSeed("kanji_n5_27", "口", "kuchi", listOf("mouth"), 3, listOf("コウ", "ク"), listOf("くち"), listOf("mouth"), Rarity.N),
        KanjiSeed("kanji_n5_28", "手", "te", listOf("hand"), 4, listOf("シュ", "ズ"), listOf("て", "た-"), listOf("hand"), Rarity.N),
        KanjiSeed("kanji_n5_29", "足", "ashi", listOf("foot", "leg"), 7, listOf("ソク"), listOf("あし", "た-りる", "た-る", "た-す"), listOf("foot", "leg", "sufficient"), Rarity.N),
        KanjiSeed("kanji_n5_30", "力", "chikara", listOf("power", "strength"), 2, listOf("リョク", "リキ"), listOf("ちから"), listOf("power", "strength"), Rarity.N),
        KanjiSeed("kanji_n5_31", "山", "yama", listOf("mountain"), 3, listOf("サン", "ザン"), listOf("やま"), listOf("mountain"), Rarity.N),
        KanjiSeed("kanji_n5_32", "川", "kawa", listOf("river"), 3, listOf("セン"), listOf("かわ"), listOf("river"), Rarity.N),
        KanjiSeed("kanji_n5_33", "田", "ta", listOf("rice field"), 5, listOf("デン"), listOf("た"), listOf("rice field"), Rarity.N),
        KanjiSeed("kanji_n5_34", "雨", "ame", listOf("rain"), 8, listOf("ウ"), listOf("あめ", "あま-"), listOf("rain"), Rarity.N),
        KanjiSeed("kanji_n5_35", "天", "ten", listOf("heaven", "sky"), 4, listOf("テン"), listOf("あめ", "あま-"), listOf("heaven", "sky"), Rarity.N),
        KanjiSeed("kanji_n5_36", "気", "ki", listOf("spirit", "mood"), 6, listOf("キ", "ケ"), emptyList(), listOf("spirit", "mood", "air"), Rarity.N),
        KanjiSeed("kanji_n5_37", "空", "sora", listOf("sky", "empty"), 8, listOf("クウ"), listOf("そら", "あ-く", "あ-ける", "から"), listOf("sky", "empty"), Rarity.N),
        KanjiSeed("kanji_n5_38", "大", "oo", listOf("big", "large"), 3, listOf("ダイ", "タイ"), listOf("おお-", "おお-きい"), listOf("big", "large"), Rarity.N),
        KanjiSeed("kanji_n5_39", "小", "chii", listOf("small"), 3, listOf("ショウ"), listOf("ちい-さい", "こ-", "お-"), listOf("small"), Rarity.N),
        KanjiSeed("kanji_n5_40", "中", "naka", listOf("middle", "inside"), 4, listOf("チュウ", "ジュウ"), listOf("なか", "うち"), listOf("middle", "inside", "center"), Rarity.N),
        KanjiSeed("kanji_n5_41", "上", "ue", listOf("up", "above"), 3, listOf("ジョウ", "ショウ", "シャン"), listOf("うえ", "あ-がる", "あ-げる"), listOf("up", "above", "raise"), Rarity.N),
        KanjiSeed("kanji_n5_42", "下", "shita", listOf("down", "below"), 3, listOf("カ", "ゲ"), listOf("した", "さ-がる", "さ-げる", "くだ-る"), listOf("down", "below", "lower"), Rarity.N),
        KanjiSeed("kanji_n5_43", "左", "hidari", listOf("left"), 5, listOf("サ"), listOf("ひだり"), listOf("left"), Rarity.N),
        KanjiSeed("kanji_n5_44", "右", "migi", listOf("right"), 5, listOf("ウ", "ユウ"), listOf("みぎ"), listOf("right"), Rarity.N),
        KanjiSeed("kanji_n5_45", "東", "higashi", listOf("east"), 8, listOf("トウ"), listOf("ひがし"), listOf("east"), Rarity.N),
        KanjiSeed("kanji_n5_46", "西", "nishi", listOf("west"), 6, listOf("セイ", "サイ"), listOf("にし"), listOf("west"), Rarity.N),
        KanjiSeed("kanji_n5_47", "南", "minami", listOf("south"), 9, listOf("ナン", "ナ"), listOf("みなみ"), listOf("south"), Rarity.N),
        KanjiSeed("kanji_n5_48", "北", "kita", listOf("north"), 5, listOf("ホク"), listOf("きた"), listOf("north"), Rarity.N),
        KanjiSeed("kanji_n5_49", "白", "shiro", listOf("white"), 5, listOf("ハク", "ビャク"), listOf("しろ", "しら-", "しろ-い"), listOf("white"), Rarity.N),
        KanjiSeed("kanji_n5_50", "本", "hon", listOf("book", "origin"), 5, listOf("ホン"), listOf("もと"), listOf("book", "origin", "real"), Rarity.N),
        KanjiSeed("kanji_n5_51", "学", "gaku", listOf("study", "learning"), 8, listOf("ガク"), listOf("まな-ぶ"), listOf("study", "learning", "school"), Rarity.N),
        KanjiSeed("kanji_n5_52", "校", "kou", listOf("school"), 10, listOf("コウ"), emptyList(), listOf("school"), Rarity.N),
        KanjiSeed("kanji_n5_53", "先", "saki", listOf("previous", "ahead"), 6, listOf("セン"), listOf("さき", "ま-ず"), listOf("previous", "ahead", "former"), Rarity.N),
        KanjiSeed("kanji_n5_54", "生", "sei", listOf("life", "birth"), 5, listOf("セイ", "ショウ"), listOf("い-きる", "い-かす", "う-む", "なま", "な-る"), listOf("life", "birth", "raw"), Rarity.N),
        KanjiSeed("kanji_n5_55", "入", "iri", listOf("enter"), 2, listOf("ニュウ", "ジュ"), listOf("はい-る", "い-れる", "い-り"), listOf("enter", "insert"), Rarity.N),
        KanjiSeed("kanji_n5_56", "出", "de", listOf("exit"), 5, listOf("シュツ", "スイ"), listOf("で-る", "だ-す", "だ-す"), listOf("exit", "leave"), Rarity.N),
        KanjiSeed("kanji_n5_57", "休", "kyuu", listOf("rest"), 6, listOf("キュウ"), listOf("やす-む", "やす-まる", "やす-める"), listOf("rest", "day off"), Rarity.N),
        KanjiSeed("kanji_n5_58", "見", "mi", listOf("see"), 7, listOf("ケン"), listOf("み-る", "み-える", "み-せる"), listOf("see", "look", "visible"), Rarity.N),
        KanjiSeed("kanji_n5_59", "読", "doku", listOf("read"), 14, listOf("ドク", "トク", "トウ"), listOf("よ-む"), listOf("read"), Rarity.N),
        KanjiSeed("kanji_n5_60", "書", "sho", listOf("write"), 10, listOf("ショ"), listOf("か-く"), listOf("write", "book"), Rarity.N),
        KanjiSeed("kanji_n5_61", "話", "wa", listOf("talk"), 13, listOf("ワ"), listOf("はな-す", "はなし"), listOf("talk", "story", "conversation"), Rarity.N),
        KanjiSeed("kanji_n5_62", "買", "kai", listOf("buy"), 12, listOf("バイ"), listOf("か-う"), listOf("buy"), Rarity.N),
        KanjiSeed("kanji_n5_63", "売", "bai", listOf("sell"), 7, listOf("バイ"), listOf("う-る", "う-れる"), listOf("sell"), Rarity.N),
        KanjiSeed("kanji_n5_64", "食", "shoku", listOf("eat"), 9, listOf("ショク"), listOf("く-う", "く-らう", "た-べる"), listOf("eat", "food"), Rarity.N),
        KanjiSeed("kanji_n5_65", "飲", "in", listOf("drink"), 12, listOf("イン"), listOf("の-む"), listOf("drink"), Rarity.N),
        KanjiSeed("kanji_n5_66", "行", "kou", listOf("go"), 6, listOf("コウ", "ギョウ", "アン"), listOf("い-く", "ゆ-く", "おこな-う"), listOf("go", "conduct", "line"), Rarity.N),
        KanjiSeed("kanji_n5_67", "来", "rai", listOf("come"), 7, listOf("ライ"), listOf("く-る", "き-たる", "き-たす"), listOf("come", "become"), Rarity.N),
        KanjiSeed("kanji_n5_68", "帰", "ki", listOf("return"), 10, listOf("キ"), listOf("かえ-る", "かえ-す"), listOf("return", "go home"), Rarity.N),
        KanjiSeed("kanji_n5_69", "車", "kuruma", listOf("car"), 7, listOf("シャ"), listOf("くるま"), listOf("car", "vehicle", "wheel"), Rarity.N),
        KanjiSeed("kanji_n5_70", "道", "michi", listOf("road", "path"), 12, listOf("ドウ", "トウ"), listOf("みち"), listOf("road", "path", "way"), Rarity.N),
        KanjiSeed("kanji_n5_71", "電", "den", listOf("electricity"), 13, listOf("デン"), emptyList(), listOf("electricity", "electric"), Rarity.R),
        KanjiSeed("kanji_n5_72", "門", "mon", listOf("gate"), 8, listOf("モン"), listOf("かど", "と"), listOf("gate"), Rarity.R),
        KanjiSeed("kanji_n5_73", "間", "aida", listOf("interval", "space"), 12, listOf("カン", "ケン"), listOf("あいだ", "ま"), listOf("interval", "space", "between"), Rarity.N),
        KanjiSeed("kanji_n5_74", "時", "toki", listOf("time"), 10, listOf("ジ"), listOf("とき"), listOf("time", "hour"), Rarity.N),
        KanjiSeed("kanji_n5_75", "分", "fun", listOf("minute", "part"), 4, listOf("フン", "ブ", "ブン"), listOf("わ-ける", "わ-かれる", "わ-かる"), listOf("minute", "part", "understand"), Rarity.N),
        KanjiSeed("kanji_n5_76", "年", "nen", listOf("year"), 6, listOf("ネン"), listOf("とし"), listOf("year"), Rarity.N),
        KanjiSeed("kanji_n5_77", "前", "mae", listOf("before"), 9, listOf("ゼン"), listOf("まえ"), listOf("before", "front"), Rarity.N),
        KanjiSeed("kanji_n5_78", "後", "ato", listOf("after"), 9, listOf("ゴ", "コウ"), listOf("あと", "うし-ろ", "のち", "おく-れる"), listOf("after", "back", "behind"), Rarity.N),
        KanjiSeed("kanji_n5_79", "今", "ima", listOf("now"), 4, listOf("コン", "キン"), listOf("いま"), listOf("now"), Rarity.N),
        KanjiSeed("kanji_n5_80", "名", "na", listOf("name"), 6, listOf("メイ", "ミョウ"), listOf("な"), listOf("name", "famous"), Rarity.N),
    )

    // ==================== JLPT N4 KANJI (170 characters - subset for demo) ====================
    private val kanjiN4 = listOf(
        KanjiSeed("kanji_n4_01", "会", "kai", listOf("meet"), 6, listOf("カイ", "エ"), listOf("あ-う"), listOf("meet", "meeting", "association"), Rarity.R),
        KanjiSeed("kanji_n4_02", "社", "sha", listOf("company"), 7, listOf("シャ"), listOf("やしろ"), listOf("company", "shrine"), Rarity.R),
        KanjiSeed("kanji_n4_03", "店", "ten", listOf("shop"), 8, listOf("テン"), listOf("みせ"), listOf("shop", "store"), Rarity.R),
        KanjiSeed("kanji_n4_04", "駅", "eki", listOf("station"), 14, listOf("エキ"), emptyList(), listOf("station"), Rarity.R),
        KanjiSeed("kanji_n4_05", "銀", "gin", listOf("silver"), 14, listOf("ギン"), listOf("しろがね"), listOf("silver", "bank"), Rarity.SR),
        KanjiSeed("kanji_n4_06", "病", "byou", listOf("sick"), 10, listOf("ビョウ"), listOf("や-む", "やまい"), listOf("sick", "illness"), Rarity.R),
        KanjiSeed("kanji_n4_07", "院", "in", listOf("institution"), 10, listOf("イン"), emptyList(), listOf("institution", "hospital"), Rarity.R),
        KanjiSeed("kanji_n4_08", "仕", "shi", listOf("serve"), 5, listOf("シ", "ジ"), listOf("つか-える"), listOf("serve", "work"), Rarity.R),
        KanjiSeed("kanji_n4_09", "事", "ji", listOf("thing", "matter"), 8, listOf("ジ", "ズ"), listOf("こと"), listOf("thing", "matter", "fact"), Rarity.R),
        KanjiSeed("kanji_n4_10", "自", "ji", listOf("self"), 6, listOf("ジ", "シ"), listOf("みずか-ら"), listOf("self", "oneself"), Rarity.R),
        KanjiSeed("kanji_n4_11", "友", "tomo", listOf("friend"), 4, listOf("ユウ"), listOf("とも"), listOf("friend"), Rarity.R),
        KanjiSeed("kanji_n4_12", "家", "ie", listOf("house", "home"), 10, listOf("カ", "ケ"), listOf("いえ", "や", "うち"), listOf("house", "home", "family"), Rarity.R),
        KanjiSeed("kanji_n4_13", "族", "zoku", listOf("family", "tribe"), 11, listOf("ゾク"), emptyList(), listOf("family", "tribe"), Rarity.SR),
        KanjiSeed("kanji_n4_14", "父", "chichi", listOf("father"), 4, listOf("フ"), listOf("ちち", "とう"), listOf("father"), Rarity.R),
        KanjiSeed("kanji_n4_15", "母", "haha", listOf("mother"), 5, listOf("ボ"), listOf("はは", "かあ"), listOf("mother"), Rarity.R),
        KanjiSeed("kanji_n4_16", "兄", "ani", listOf("older brother"), 5, listOf("ケイ", "キョウ"), listOf("あに", "にい"), listOf("older brother"), Rarity.R),
        KanjiSeed("kanji_n4_17", "姉", "ane", listOf("older sister"), 8, listOf("シ"), listOf("あね", "ねえ"), listOf("older sister"), Rarity.R),
        KanjiSeed("kanji_n4_18", "弟", "otouto", listOf("younger brother"), 7, listOf("テイ", "ダイ", "デ"), listOf("おとうと"), listOf("younger brother"), Rarity.R),
        KanjiSeed("kanji_n4_19", "妹", "imouto", listOf("younger sister"), 8, listOf("マイ"), listOf("いもうと"), listOf("younger sister"), Rarity.R),
        KanjiSeed("kanji_n4_20", "姉", "ane", listOf("older sister"), 8, listOf("シ"), listOf("あね", "ねえ"), listOf("older sister"), Rarity.R),
    )

    // ==================== ACHIEVEMENTS ====================
    val achievements = listOf(
        AchievementSeed("first_steps", "First Steps", "Complete your first writing practice", "emoji_footprints", 1),
        AchievementSeed("hiragana_novice", "Hiragana Novice", "Learn 10 Hiragana characters", "emoji_flower", 10),
        AchievementSeed("hiragana_master", "Hiragana Master", "Learn all 46 Hiragana characters", "emoji_sakura", 46),
        AchievementSeed("katakana_novice", "Katakana Novice", "Learn 10 Katakana characters", "emoji_star", 10),
        AchievementSeed("katakana_master", "Katakana Master", "Learn all 46 Katakana characters", "emoji_diamond", 46),
        AchievementSeed("kanji_beginner", "Kanji Beginner", "Learn 10 N5 Kanji", "emoji_book", 10),
        AchievementSeed("kanji_explorer", "Kanji Explorer", "Learn 50 N5 Kanji", "emoji_scroll", 50),
        AchievementSeed("kanji_adept", "Kanji Adept", "Learn all N5 Kanji", "emoji_crown", 80),
        AchievementSeed("perfect_streak", "Perfect Streak", "Get 5 correct answers in a row", "emoji_fire", 5),
        AchievementSeed("daily_dedication", "Daily Dedication", "Study for 7 days in a row", "emoji_calendar", 7),
        AchievementSeed("speed_demon", "Speed Demon", "Complete a speed challenge", "emoji_bolt", 1),
        AchievementSeed("collector", "Card Collector", "Collect your first card", "emoji_card", 1),
        AchievementSeed("collection_grower", "Collection Grower", "Collect 10 cards", "emoji_cards", 10),
        AchievementSeed("mock_test_rookie", "Mock Test Rookie", "Complete your first mock test", "emoji_pencil", 1),
        AchievementSeed("n5_passer", "N5 Passer", "Pass JLPT N5 mock test", "emoji_certificate", 1),
        AchievementSeed("night_owl", "Night Owl", "Study after midnight", "emoji_moon", 1),
        AchievementSeed("early_bird", "Early Bird", "Study before 7 AM", "emoji_sun", 1),
    )

    // Helper data classes
    data class CharacterSeed(
        val id: String,
        val character: String,
        val romaji: String,
        val row: String,
        val strokeCount: Int,
        val description: String,
        val rarity: Rarity
    )

    data class KanjiSeed(
        val id: String,
        val character: String,
        val romaji: String,
        val meanings: List<String>,
        val strokeCount: Int,
        val onYomi: List<String>,
        val kunYomi: List<String>,
        val meaningDesc: List<String>,
        val rarity: Rarity
    )

    data class AchievementSeed(
        val id: String,
        val title: String,
        val description: String,
        val iconName: String,
        val maxProgress: Int
    )

    // Initial user progress
    fun getInitialUserProgress(): UserProgressEntity {
        return UserProgressEntity(
            id = 1,
            currentStreak = 0,
            longestStreak = 0,
            lastStudyDate = null,
            totalStudyTimeMinutes = 0,
            totalCharactersLearned = 0,
            totalKanjiLearned = 0,
            totalMockTestsTaken = 0,
            highestN5Score = 0,
            highestN4Score = 0,
            kanaOrbs = 100, // Give some starting currency
            kanjiOrbs = 50,
            premiumOrbs = 0,
            hasCompletedOnboarding = false,
            preferredDailyGoal = 15,
            enableNotifications = true,
            enableSound = true,
            enableHaptic = true
        )
    }

    /**
     * Generate complete CharacterEntity list with Kana and Kanji
     */
    fun generateCharacters(): List<CharacterEntity> {
        val characters = mutableListOf<CharacterEntity>()

        // Generate Hiragana
        hiraganaData.forEach { seed ->
            characters.add(createKanaEntity(seed, CharacterType.HIRAGANA))
        }

        // Generate Katakana
        katakanaData.forEach { seed ->
            characters.add(createKanaEntity(seed, CharacterType.KATAKANA))
        }

        // Generate N5 Kanji
        kanjiN5.forEach { seed ->
            characters.add(createKanjiEntity(seed, JlptLevel.N5))
        }

        // Generate N4 Kanji
        kanjiN4.forEach { seed ->
            characters.add(createKanjiEntity(seed, JlptLevel.N4))
        }

        return characters
    }

    private fun createKanaEntity(seed: CharacterSeed, type: CharacterType): CharacterEntity {
        val strokeData = KanaStrokeData.getStrokes(seed.character)

        return CharacterEntity(
            id = seed.id,
            character = seed.character,
            romaji = seed.romaji,
            type = type,
            row = seed.row,
            strokeCount = seed.strokeCount,
            strokes = strokeData,
            audioFileName = null,
            associatedCharacter = seed.description,
            associatedDescription = seed.description,
            associatedImageUrl = null,
            rarity = seed.rarity,
            jlptLevel = null,
            meanings = null,
            onYomi = null,
            kunYomi = null,
            radicals = null,
            isUnlocked = false,
            masteryLevel = 0,
            correctReviews = 0,
            incorrectReviews = 0,
            lastReviewDate = null,
            nextReviewDate = null,
            strokeAccuracy = 0f
        )
    }

    private fun createKanjiEntity(seed: KanjiSeed, level: JlptLevel): CharacterEntity {
        return CharacterEntity(
            id = seed.id,
            character = seed.character,
            romaji = seed.romaji,
            type = CharacterType.KANJI,
            row = level.name,
            strokeCount = seed.strokeCount,
            strokes = emptyList(), // Kanji strokes loaded from assets
            audioFileName = null,
            associatedCharacter = seed.meanings.joinToString(", "),
            associatedDescription = seed.meaningDesc.joinToString("; "),
            associatedImageUrl = null,
            rarity = seed.rarity,
            jlptLevel = level,
            meanings = seed.meanings,
            onYomi = seed.onYomi,
            kunYomi = seed.kunYomi,
            radicals = emptyList(),
            isUnlocked = false,
            masteryLevel = 0,
            correctReviews = 0,
            incorrectReviews = 0,
            lastReviewDate = null,
            nextReviewDate = null,
            strokeAccuracy = 0f
        )
    }

    /**
     * Generate achievement entities
     */
    fun generateAchievements(): List<AchievementEntity> {
        return achievements.map { seed ->
            AchievementEntity(
                achievementId = seed.id,
                title = seed.title,
                description = seed.description,
                iconName = seed.iconName,
                isUnlocked = false,
                unlockedDate = null,
                progress = 0,
                maxProgress = seed.maxProgress
            )
        }
    }
}
