package v2020.days.day05

private class Passport(fields: Map<String, String>) {

    // byr (Birth Year)
    val birthYear: Int?
    // iyr (Issue Year)
    val issueYear: Int?
    // eyr (Expiration Year)
    val expirationYear: Int?
    // hgt (Height)
    val height: String?
    // hcl (Hair Color)
    val hairColor: String?
    // ecl (Eye Color)
    val eyeColor: String?
    // pid (Passport ID)
    val passportId: String?
    // cid (Country ID)
    val countryId: Int?

    init {
        birthYear = fields["byr"]?.toIntOrNull()
        issueYear = fields["iyr"]?.toIntOrNull()
        expirationYear = fields["eyr"]?.toIntOrNull()
        height = fields["hgt"]
        hairColor = fields["hcl"]
        eyeColor = fields["ecl"]
        passportId = fields["pid"]
        countryId = fields["cid"]?.toIntOrNull()
    }

    fun hasRequiredFields(): Boolean {
        return birthYear != null &&
            issueYear != null &&
            expirationYear != null &&
            height != null &&
            hairColor != null &&
            eyeColor != null &&
            passportId != null
    }

    fun isValid(): Boolean {
        if (!this.hasRequiredFields()) {
            return false
        }
        // byr (Birth Year) - four digits; at least 1920 and at most 2002.
        if (birthYear !in 1920 .. 2002) {
            return false
        }

        // iyr (Issue Year) - four digits; at least 2010 and at most 2020.
        if (issueYear !in 2010 .. 2020) {
            return false
        }

        // eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
        if (expirationYear !in 2020 .. 2030) {
            return false
        }

        // hgt (Height) - a number followed by either cm or in:
        val heightAmount = height?.substring(0, height.length - 2)?.toIntOrNull()
        val heightUnit = height?.substring(height.length - 2)

        if (heightUnit == "cm") {
            // If cm, the number must be at least 150 and at most 193.
            if (heightAmount !in 150 .. 193) {
                return false
            }
        } else if (heightUnit == "in") {
            // If in, the number must be at least 59 and at most 76.
            if (heightAmount !in 59 .. 76) {
                return false
            }
        } else {
            return false
        }

        // hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
        val hairColorHex = hairColor?.substring(1)
        if (hairColor?.first() != '#' || hairColorHex?.length != 6 || hairColorHex.any { !it.isDigit() && it !in 'a' .. 'f' }) {
            return false
        }

        // ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
        if (eyeColor !in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")) {
            return false
        }

        // pid (Passport ID) - a nine-digit number, including leading zeroes.
        if (passportId?.length != 9 || passportId.any { !it.isDigit() }) {
            return false
        }

        // cid (Country ID) - ignored, missing or not.
        return true
    }

}

private fun parsePassports(input: List<String>): List<Passport> {
    val passports = mutableListOf<Passport>()
    var passportFields = mutableMapOf<String, String>()

    input.forEach { line ->
        if (line.isEmpty()) {
            passports.add(Passport(passportFields))
            passportFields = mutableMapOf()
        } else {
            line.split(" ").forEach { field ->
                val (key, value) = field.split(":")
                passportFields[key] = value
            }
        }
    }

    if (passportFields.isNotEmpty()) {
        passports.add(Passport(passportFields))
    }

    return passports
}

private fun getBoardingPasses(input: List<String>): List<Pair<Int, Int>> {
    return input.map { line ->
        var rowRange = 0 .. 127
        line.take(7).forEach { char ->
            val mid = rowRange.first + (rowRange.count() / 2)
            if (char == 'B') {
                rowRange = mid .. rowRange.last
            } else {
                rowRange = rowRange.first until mid
            }
        }

        var colRange = 0 .. 7
        line.takeLast(3).forEach { char ->
            val mid = colRange.first + (colRange.count() / 2)
            if (char == 'R') {
                colRange = mid .. colRange.last
            } else {
                colRange = colRange.first until mid
            }
        }

        rowRange.first() to colRange.first()
    }
}

fun day05a(input: List<String>): Int {
    return getBoardingPasses(input).map { it.first * 8 + it.second }.max()!!
}

fun day05b(input: List<String>): Int {
    val boardingPasses = getBoardingPasses(input)

    val expectedCols = 0 .. 7

    val actualRow =
        boardingPasses
            .groupBy({ it.first }) { it.second }
            .entries

            // Ignore full rows
            .filter { it.value.size < 8 }

            // Ignore front row
            .sortedBy { it.key }.drop(1)

            .first()

    val missingCol = expectedCols.minus(actualRow.value).first()

    return actualRow.key * 8 + missingCol
}

