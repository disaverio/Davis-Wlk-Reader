Application to translate binary `wlk` files from Davis Weather Instruments, to human-readable `csv` files

```
Usage: WlkConverter options_list
Options:
  --inputFilePathList, -i -> Input file path (always required) { String }
  --outputFilePath, -o -> Output file path { String }
  --outputFormat, -f [CSV] -> Format for output file { Value should be one of [csv] }
  --outputFieldsListFilePath, -p -> File containing list of fields printed in output { String }
  --help, -h -> Usage info
```

1. `wlk` file names **must be** in the format `YYYY-MM.wlk`
2. `-i` is mandatory. As `-i` option you can provide a single file, or a folder. Multiple input are accepted, also mixed. E.g. of allowed input:
    - `-i /path/to/my/2021-01.wlk`
    - `-i /path/to/my/2021-01.wlk -i /path/to/my/2021-02.wlk -i /path/to/my/2021-03.wlk -i /path/to/my/2021-04.wlk`
    - `-i /path/to/my/wlk/folder`
    - `-i /path/to/my/wlk/folder -i /path/to/another/wlk/folder`
    - `-i /path/to/my/wlk/folder -i /path/to/my/2021-01.wlk -i /path/to/my/2021-02.wlk -i /path/to/another/wlk/folder`
3. `-o` is optional: if no path is provided the output will be printed in two files named `DailySummary_${yyyy}-${mm}.${ext}` and `DailyData_${yyyy}-${mm}.${ext}`. Note: prefixes `DailySummary_${yyyy}-${mm}` and `DailyData_${yyyy}-${mm}` are always prepended to provided filenames.
4. `-f`: at the moment is **not used**, since only `csv` is supported as output format
5. `-p` is optional: path to text files with two lines containing comma separated fields to be printed in output. **First line** contains fields for Daily Summary output, **second line** contains fields for Daily Data output. **If no path is provided** all the fields will be printed.

   Available fields (here grouped by affinity for reading convenience):
   - **Daily Summary** fields:
      - Inside
        - Temperature: `hiInTemp`, `hiInTempTime`, `avgInTemp`, `lowInTemp`, `lowInTempTime`
        - Humidity: `hiInHum`, `hiInHumTime`, `lowInHum`, `lowInHumTime`
      - Outside
         - Temperature: `hiOutTemp`, `hiOutTempTime`, `avgOutTemp`, `lowOutTemp`, `lowOutTempTime`, `hiChill`, `hiChillTime`, `avgChill`, `lowChill`, `lowChillTime`, `hiDew`, `hiDewTime`, `avgDew`, `lowDew`, `lowDewTime`, `hiWetBulb`, `avgWetBulb`, `lowWetBulb`
         - Humidity: `hiOutHum`, `hiOutHumTime`, `avgOutHum`, `lowOutHum`, `lowOutHumTime`
         - Pressure: `hiBar`, `hiBarTime`, `avgBar`, `lowBar`, `lowBarTime`
         - Wind: `hiSpeed`, `dirHiSpeed`, `hiSpeedTime`, `avgSpeed`, `dailyWindRunTotal`, `hi10MinSpeed`, `hi10MinSpeedTime`, `hi10MinDir`, `minutesAsDominantDirection`
         - Rain: `dailyRainTotal`, `hiRainRate`, `hiRainRateTime`
         - Other: `dailyUVDose`, `hiUV`, `hiUVTime`, `hiHeat`, `hiHeatTime`, `avgHeat`, `lowHeat`, `lowHeatTime`, `hiTHSW`, `hiTHSWTime`, `lowTHSW`, `lowTHSWTime`, `hiTHW`, `hiTHWTime`, `lowTHW`, `lowTHWTime`
      - Other: `date`, `integratedHeatDD65`, `integratedCoolDD65`, `dataSpan`, `numWindPackets`, `hiSolar`, `dailySolarEnergy`, `minSunlight`, `dailyETTotal`
   - **Daily Data** fields:
      - Inside: `insideTemp`, `insideHum`
      - Outside
         - Temperature: `outsideTemp`, `hiOutsideTemp`, `lowOutsideTemp`,
         - Humidity: `outsideHum`
         - Pressure: `barometer`
         - Wind: `windSpeed`, `hiWindSpeed`, `windDirection`, `hiWindDirection`
         - Rain: `rain`, `hiRainRate`
         - Other: `solarRad`, `hisolarRad`, `UV`, `hiUV`, `extraRad`, `ET`
      - Other: `date`, `time`, `archiveInterval`, `packedTime`, `numWindSamples`