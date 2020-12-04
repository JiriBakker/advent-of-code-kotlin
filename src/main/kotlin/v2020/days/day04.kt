package v2020.days.day04

private fun parsePassports(input: List<String>): List<Map<String, String>> {
    val passports = mutableListOf<Map<String, String>>()
    var passport = mutableMapOf<String, String>()

    input.forEach { line ->
        if (line.isEmpty()) {
            passports.add(passport)
            passport = mutableMapOf()
        } else {
            line.split(" ").forEach { field ->
                val (key, value) = field.split(":")
                passport[key] = value
            }
        }
    }

    if (passport.isNotEmpty()) {
        passports.add(passport)
    }

    return passports
}

// byr (Birth Year)
// iyr (Issue Year)
// eyr (Expiration Year)
// hgt (Height)
// hcl (Hair Color)
// ecl (Eye Color)
// pid (Passport ID)
// cid (Country ID)

private fun Map<String, String>.hasRequiredFields(): Boolean {
    return this.containsKey("byr") &&
        this.containsKey("iyr") &&
        this.containsKey("eyr") &&
        this.containsKey("hgt") &&
        this.containsKey("hcl") &&
        this.containsKey("ecl") &&
        this.containsKey("pid")
}

private fun Map<String, String>.isValidPassport(): Boolean {
    if (!this.hasRequiredFields()) {
        return false
    }
    // byr (Birth Year) - four digits; at least 1920 and at most 2002.
    val birthYear = this["byr"]?.toIntOrNull()
    if (birthYear !in 1920 .. 2002) {
        return false
    }

    // iyr (Issue Year) - four digits; at least 2010 and at most 2020.
    val issueYear = this["iyr"]?.toIntOrNull()
    if (issueYear !in 2010 .. 2020) {
        return false
    }

    // eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
    val expirationYear = this["eyr"]?.toIntOrNull()
    if (expirationYear !in 2020 .. 2030) {
        return false
    }

    // hgt (Height) - a number followed by either cm or in:
    val height = this["hgt"]
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
    val hairColor = this["hcl"]
    val hairColorHex = hairColor?.substring(1)
    if (hairColor?.first() != '#' || hairColorHex?.length != 6 || hairColorHex.any { !it.isDigit() && it !in 'a' .. 'f' }) {
        return false
    }

    // ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
    val eyeColor = this["ecl"]
    if (eyeColor !in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")) {
        return false
    }

    // pid (Passport ID) - a nine-digit number, including leading zeroes.
    val passportId = this["pid"]
    if (passportId?.length != 9 || passportId.any { !it.isDigit() }) {
        return false
    }

    // cid (Country ID) - ignored, missing or not.
    return true
}

fun day04a(input: List<String>): Int {
    val passports = parsePassports(input)
    return passports.count { it.hasRequiredFields() }
}

fun day04b(input: List<String>): Int {
    val passports = parsePassports(input)
    return passports.count { it.isValidPassport() }
}

