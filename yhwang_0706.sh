#!/bin/bash
url='https://foo.com/bar'
n=0
time_format=':00:00'
while [ $n -lt 24 ]
do
start_hour=`printf "%02d\n" $n`
end_hour=`expr $start_hour + 1`
end_hour=`printf "%02d\n" $end_hour`
start_time=$start_hour$time_format
end_time=$end_hour$time_format

deviceName_format=`cat interview_data_set | grep -i error | awk '$3 >= "'$start_time'" && $3 < "'$end_time'" {print $0}' | awk '{print $4}' | tr "\n" ","`
deviceName=`echo ${deviceName_format%?}`
processId_format=`cat interview_data_set | grep -i error | awk '$3 >= "'$start_time'" && $3 < "'$end_time'" {print $0}'  | awk '{print $6}' |tr -d "a-zA-Z.()[]:"| tr "\n" ","`
processId=`echo ${processId_format%?}`
processName_format=`cat interview_data_set | grep -i error | awk '$3 >= "'$start_time'" && $3 < "'$end_time'" {print $0}' | awk '{print$5}'| tr "\n" ","`
processName=`echo ${processName_format%?}`
description_format=`cat interview_data_set | grep -i error | awk '$3 >= "'$start_time'" && $3 < "'$end_time'" {print $0}' | awk -F ':' '{print $4}'| tr "\n" ","`
description=`echo ${description_format%?}`
numberOfOccurrence=`cat interview_data_set | grep -i error | awk '$3 >= "'$start_time'" && $3 < "'$end_time'" {print $0}' | wc -l`
timeWindow=$start_hour'00''-'$end_hour'00'

echo 'deviceName:'$deviceName
echo 'processId:'$processId
echo 'processName:'$processName
echo 'description:'$description
echo 'timeWindow:'$timeWindow
echo 'numberOfOccurrence:'$numberOfOccurrence

((n++))

curl -i -X POST -H "'Content-type':'application/json'" -d '{"deviceName":"'$deviceName'","processId":"'$processId’”,"processName":"'$processName’”,"description":"'$description’”,"timeWindow":"'$timeWindow’”,"numberOfOccurrence":"'$numberOfOccurrence'"}’ $url -k
done


