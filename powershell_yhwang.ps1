$n=0
$time_format=':00:00'
$url='https://foo.com/bar'

while($n -lt 24)
    {
	$m=$n+1
	$start_hour='{0:d2}' -f $n
	$end_hour='{0:d2}' -f $m
    $start_time=$start_hour+$time_format
	$end_time=$end_hour+$time_format
	$n++
    $error= @{}
    $numberOfOccurrence=0
    foreach($line in Get-Content .\interview_data_set | findstr 'error') 
        {
        $time = $line.split()[2]
            if ($time -ge $start_time -and $time -le $end_time)
            {
        	$numberOfOccurrence++
			$timeWindow=$start_hour+'00'+'-'+$end_hour+'00'
            $device = $line.split()[3]
        	$processId_word = $line.split()[5]
            $regex= "\d+"
            $processId=[regex]::matches($processId_word,$regex).value 
        	$processName = $line.split()[4]
        	$description = $line.split(':')[3]
        
        	$error['processName']+=$processName+','
        	$error['description']+=$description+','
            $error['deviceName']+=$device+','
        	$error['processId']+=$processId+','
        }
        }
	$error['numberOfOccurrence']=$numberOfOccurrence
	$timeWindow=$start_hour+'00'+'-'+$end_hour+'00'
	$error['timeWindow']=$timeWindow
	echo $numberOfOccurrence
	if ($numberOfOccurrence -gt 0)
	    {
        $error['processId']=$error['processId'].TrimEnd(',')
	    $error['deviceName']=$error['deviceName'].TrimEnd(',')
	    $error['description']=$error['description'].TrimEnd(',')
	    }
	echo $error 
#    Invoke-WebRequest $url -Method POST -Body ($error | ConvertTo-Json) -ContentType "application/json"
    }

