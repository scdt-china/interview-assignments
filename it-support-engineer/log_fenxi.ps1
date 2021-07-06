$server_url = "http://foo.com/bar"
$error_report = @{}

foreach($single_line in Get-Content "interview_data_set") {
    if ($single_line.contains("error")) {
         $msg = $single_line.split(" ", 5)
         $time = $msg[0] + " " + $msg[1] + " " + $msg[2]
         $timestamp = $msg[2].split(":", 3)
         $deviceName = $msg[3]
         $processMsg = $msg[4].split(":", 2)[0]
         $description = $msg[4].split(":", 2)[1]
         $processName = $processMsg.split("[")[0]
         $processId = $processMsg.split("[")[1].split("]")[0]

         if (!($error_report.ContainsKey($processName + $description))){
             $new_error = @{}
             $new_error["deviceName"] = $deviceName
             $new_error["processId"] = $processId
             $new_error["processName"] = $processName
             $new_error["description"] = $description
             $new_error["timeWindow"] = $timestamp[0] + $timestamp[1], $timestamp[0] + $timestamp[1]
             $new_error["numberOfOccurrence"] = 1
             $error_report[$processName + $description] = $new_error
         }
         else {
             $start_time = $error_report[$processName + $description]["timeWindow"].split(" ", 2)[0]
             $new_end = $timestamp[0] + $timestamp[1]
             $error_report[$processName + $description]["timeWindow"] = $start_time, $new_end
             $error_report[$processName + $description]["numberOfOccurrence"] = $error_report[$processName + $description]["numberOfOccurrence"] + 1
         }
    }
}

foreach($key in $error_report.Keys){
    Write-Host "======================="
    Write-Host "deviceName:" $($error_report[$key])["deviceName"]
    Write-Host "processName:" $($error_report[$key])["processName"]
    Write-Host "processId:" $($error_report[$key])["processId"]
    Write-Host "description:" $($error_report[$key])["description"]
    Write-Host "timeWindow:" $($error_report[$key])["timeWindow"]
    Write-Host "numberOfOccurrence:" $($error_report[$key])["numberOfOccurrence"]
}

## Invoke-WebRequest $server_url -Method POST -Body ($errorRecord | ConvertTo-Json -Depth 4) -ContentType "application/json"
