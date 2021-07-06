#! /usr/bin/awk -f

BEGIN {
    pre_logline = ""
}

{
    processId = 0
    deviceName = ""
    processName = ""
    description = ""
    timeWindow = ""
}

/--- last message peated 1 time ---/ {
    sub(/--- last message peated 1 time ---/, pre_logline)
}

{
    delete a
    split($3, a, ":")
    if (a[1] == "00")
        timeWindow = "0000-0100"
    else if (a[1] == "01")
        timeWindow = "0100-0200"
    else if (a[1] == "02")
        timeWindow = "0220-0300"
    else if (a[1] == "03")
        timeWindow = "0300-0400"
    else if (a[1] == "04")
        timeWindow = "0400-0500"
    else if (a[1] == "05")
        timeWindow = "0500-0600"
    else if (a[1] == "06")
        timeWindow = "0600-0700"
    else if (a[1] == "07")
        timeWindow = "0700-0800"
    else if (a[1] == "08")
        timeWindow = "0800-0900"
    else if (a[1] == "09")
        timeWindow = "0900-1000"
    else if (a[1] == "10")
        timeWindow = "1000-1100"
    else if (a[1] == "11")
        timeWindow = "1100-1200"
    else if (a[1] == "12")
        timeWindow = "1200-1300"
    else if (a[1] == "13")
        timeWindow = "1300-1400"
    else if (a[1] == "14")
        timeWindow = "1400-1500"
    else if (a[1] == "15")
        timeWindow = "1500-1600"
    else if (a[1] == "16")
        timeWindow = "1600-1700"
    else if (a[1] == "17")
        timeWindow = "1700-1800"
    else if (a[1] == "18")
        timeWindow = "1800-1900"
    else if (a[1] == "19")
        timeWindow = "1900-2000"
    else if (a[1] == "20")
        timeWindow = "2000-2100"
    else if (a[1] == "21")
        timeWindow = "2100-2200"
    else if (a[1] == "22")
        timeWindow = "2200-2300"
    else if (a[1] == "23")
        timeWindow = "2300-2400"
    else {
    }
    for (i = 4; i<=NF; i++) {
        if (i == 4) {
            pre_logline = $i
        } else {
            pre_logline = pre_logline" "$i
        }
    }
    if ((pre_logline, timeWindow) in countA) {
        countA[pre_logline, timeWindow] = countA[pre_logline, timeWindow] + 1
    } else {
        countA[pre_logline, timeWindow] = 1
    }
}

END {
    print "["
    n = 1
    sum = 0
    for (k in countA) {
        processId = 0
        deviceName = ""
        processName = ""
        description = ""
        timeWindow = ""
        c = length(countA)
        delete a
        split(k, a, SUBSEP)
 numberOfOccurrence = countA[a[1], a[2]]
        timeWindow = a[2]
        delete aa
        split(a[1], aa, " ")
        deviceName = aa[1]
        delete aaa
        split(aa[2], aaa, "]")
        delete aaaa
        split(aaa[1], aaaa, "[")
        processName = aaaa[1]
        processId = aaaa[2]
        for (i = 3; i<=length(aa); i++) {
            gsub(/"/, "\\\"", aa[i])
            if (i == 3) {
                description = aa[i]
            } else {
                description = description" "aa[i]
            }
        }
     printf "  {\n    \"deviceName\":\"%s\", \n    \"processId\":%d, \n    \"processName\":\"%s\", \n    \"description\":\"%s\", \n    \"timeWindow\":\"%s\", \n    \"numberOfOccurrence\":%d \n  }", deviceName, processId, processName, description, timeWindow, numberOfOccurrence
        sum = sum + numberOfOccurrence
        if (n < c)
            print ", "
        else
            print ""
        n++
    }
    print "]"
}