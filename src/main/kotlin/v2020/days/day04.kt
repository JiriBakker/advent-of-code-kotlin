package v2020.days.day04

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

fun day04a(input: List<String>): Int {
    val passports = parsePassports(input)
    return passports.count { it.hasRequiredFields() }
}

fun day04b(input: List<String>): Int {
    val passports = parsePassports(input)
    return passports.count { it.isValid() }
}

