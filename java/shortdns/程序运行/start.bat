﻿@echo off
@echo starting dnsDemo.......
java  -Xms512m -Xmx512m  -Xmn320m -XX:SurvivorRatio=6 -XX:+UseConcMarkSweepGC  -jar dnsDemo.jar
