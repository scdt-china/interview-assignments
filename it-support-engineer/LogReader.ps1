#作者：Ted Yue （43757930@qq.com）

$logpath = '.\interview_data_set'
$log = Get-Content $logpath
$outputs = New-Object -TypeName System.Collections.ArrayList     #最终的结果数组，用来储存从日志中抓取到的关键信息

Function TimeToWindow ($time)       #此函数用来将具体时间（例如01:18:59）转变为时间窗口（0100-0200）
{
	$time_parts = $time.split(':')
	$hour = [int]$time_parts[0]
	$nexthour = $hour+1
	if($hour -lt 10){
	$hour_string = "0"+ $hour.ToString()
	}
	else{
	$hour_string = $hour.ToString()
	}
	if($nexthour -lt 10){
	$nexthour_string = "0"+ $nexthour.ToString()
	}
	else{
	$nexthour_string = $nexthour.ToString()
	}
	return $hour_string + "00-" + $nexthour_string + "00"
}

Function AddErrorToOutput([string]$deviceName,[string]$processId,[string]$processName,[string]$description,[string]$timeWindow)   #此函数用来将一行处理好的错误日志加入结果数组
{
	foreach($output in $outputs)
	{
		if (($output.description -eq $description)-and ($output.timeWindow -eq $timeWindow))      #如果错误描述和时间窗口和某行已有的错误相同，则只需要将发生次数+1
		{
			$output.processId += $processId      #将发生相同错误的进程号码加入原错误的统计中
			$output.numberOfOccurrence += 1
			return
		}
	}
	$error = New-Object -TypeName PSObject -Property $properties @{     #全新的错误则新建一个对象并且加入结果数组
	   'deviceName' = $deviceName
	   'processId' = ,$processId                                 #process ID使用数组，因为存在多个ID出现相同的错误
	   'processName' = $processName
	   'description' = $description
	   'timeWindow' = $timeWindow
	   'numberOfOccurrence' = 1
   }
	$outputs.Add($error)
}

Function CollectError ($line)    #核心函数，用来处理错误日志中的每一行
{
	$mapping = $line -match '\((.*)\[(\d.*)\]\)\: (.*)'             #查找是否有格式类似(XXX[XXXX]): XXX的错误显示
	if ($matches)
	{
		$part = $line.split(' ')                                    #按照空格分隔开，以便找到各项信息
		$deviceName = $part[3]
		$time = $part[2]
		$timeWindow = TimeToWindow $time                            #把时间转变为窗口
		$processId = $matches[2]
		$processname = $matches[1]
		$description = $matches[3]
		$adding = AddErrorToOutput $deviceName $processId $processName $description $timeWindow    #加入结果数组
	}
}


ForEach($log_line in $log)     #逐行用核心函数处理
{
	$collecting = CollectError $log_line
}
	
$output_body = $outputs | Convertto-Json | Out-File .\json_output.txt    #把结果数组转变为JSON格式，同时备份到文件

Invoke-WebRequest http://coolaf.com -Method POST -ContentType "application/json" -Body $output_body
