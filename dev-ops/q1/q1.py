import gzip
import re
from datetime import datetime
from collections import Counter
import requests


zip_file = './DevOps_interview_data_set.gz'


def main():
    raw_data=generate_raw_data()
    formatted_data=format_raw_data(raw_data)
    session = requests.Session()
    #can't verfiy the certificate in my enviroment,so diabled the ssl verify
    session.verify = False
    session.post('https://foo.com/bar', json=formatted_data)


def generate_raw_data():
    raw_list=[]
    with gzip.open(zip_file,'rt') as file:
      for line in file:
        pattern1 = "(?P<month>\w+) (?P<day>\d{2}) (?P<hour>\d{2}):(?P<minute>\d{2}):(?P<second>\d{2}) (?P<deviceName>\w+) (?P<processName>.*?)\[(?P<processId>\d+)\]"
        pattern2="\w+ \d+ \d+:\d+:\d+ \w+ .*?:\s(?P<description>.*)"
        matched=re.match(pattern1,line)
        matched2=re.match(pattern2,line)
        if matched and matched2:
          hour=matched.group('hour')
          timewindow=f'{int(hour):02d}00-{(int(hour) + 1)%24:02d}00'
          deviceName=matched.group('deviceName')
          processName=matched.group('processName')
          processID=matched.group('processId')
          description=matched2.group('description')
          processrow = '{}#{}#{}#{}#{}'.format(deviceName, processID, processName, timewindow, description)
          raw_list.append(processrow)
    return raw_list

def format_raw_data(list):
    formatted_list = []
    log_collections = Counter(list)
    for key,value in log_collections.items():
        formatted_raw_data = {}
        items = key.split('#')
        formatted_raw_data['deviceName'] = items[0]
        formatted_raw_data['processID'] = items[1]
        formatted_raw_data['processName'] = items[2]
        formatted_raw_data['timeWindow'] = items[3]
        formatted_raw_data['description'] = items[4]
        formatted_raw_data['numberOfOccurrence'] = value
        formatted_list.append(formatted_raw_data)
    return formatted_list


main()

