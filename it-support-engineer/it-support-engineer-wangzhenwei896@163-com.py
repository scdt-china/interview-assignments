#!/usr/bin/python3.6
import json 
import requests
import re

with open('/var/root/interview_data_set/interview_data_set.bak', 'r') as f:
    lines = f.readlines()

    if not lines:
        print("File is Null")
    cList = []

    for line in lines:
        try:
            bItem = {}
            a = [i for i in line.split(' ', 6)]
            bItem["deviceName"], process, bItem["description"], bItem["timeWindow"] = a[3], a[4], a[6], a[2].split(':')[0]
            bItem["timeWindow"] = bItem["timeWindow"] + "00" + "-" + str("{:0>2d}".format(int(bItem["timeWindow"]) + 1)) + "00"
            bItem["processName"], bItem["processId"] = re.split('\[|\]|:', process)[0], re.split('\[|\]|:', process)[1]
            cList.append(bItem)
        except Exception as e:
            pass

    dList = []
    for it in cList:
        if it not in dList:
            dList.append(it)

    for it in dList:
        it["numberOfOccurrence"] = cList.count(it)

    json_str = json.dumps(dList, ensure_ascii=False)
    #print(json_str)

    if json_str:
        url = "https://foo.com/bar"
        headers = {'Content-Type': 'application/json; charset=UTF-8'}
        res = requests.request("post", url, json = json_str, headers = headers)
        print(res.status_code)
        print(res.text)
