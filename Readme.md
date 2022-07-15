# Wlk Converter

## Quick Start
Tool to convert binary `wlk` files from Davis Weather Instruments, to `csv` file.
This is a needed step to, for example, import your WeatherLink `wlk` files to a different data collecting software like [Weewx](https://github.com/weewx/).  

Basic usage: download the latest release, place it in a folder containing one or more `wlk` files, and launch it with no options:
```shell
java -jar wlk-converter.jar
```
for each `wlk` file, two csv files will be created: one with all the daily records, and one with the daily summaries.

To avoid printing the whole list of fields available in the `wlk` archive, you can specify a text file with the list of desired fields.
A template-like text file with the full list of fields is here available in the `assets` folder with the name `fullListOfFields.txt`: simply edit it, and then pass it to the command:
```shell
java -jar wlk-converter.jar -p desiredFields.txt
```

It is possible to specify an input folder different from the current one, or directly a single `wlk` file.
Also a different output folder could be defined:
```shell
java -jar wlk-converter.jar \
    -i /path/to/folder/with/wlk-files \
    -o /path/to/output-folder \
    -p desiredFields.txt
```

To know more about options to control the unit system and the output files content like header, or fields sorting, see the [Details](#Details) section

## Details
```shell
Options:
    --version, -v -> Print version 
    --input, -i -> Input file path { String }
    --output, -o -> Output folder path { String }
    --unit, -u -> Unit system used for values in the printed output { Value should be one of [si, imperial] }
    --skip-header, -s [false] -> Skip header printing 
    --output-format, -f [CSV] -> Format for output file { Value should be one of [csv] }
    --fields-file, -p -> Path to file containing list of fields printed in output { String }
    --help, -h -> Usage info  
```

1. `wlk` file names **must be** in the format `YYYY-MM.wlk`
2. `-v` to print version.
3. `-i` is optional: if no path is provided the output will be saved in the current folder.
   As `-i` option you can provide a single file, or a folder. Multiple input are accepted, also mixed. E.g. of allowed input:
   - `-i /path/to/my/2021-01.wlk`
   - `-i /path/to/my/2021-01.wlk -i /path/to/my/2021-02.wlk -i /path/to/my/2021-03.wlk -i /path/to/my/2021-04.wlk`
   - `-i /path/to/my/wlk/folder`
   - `-i /path/to/my/wlk/folder -i /path/to/another/wlk/folder`
   - `-i /path/to/my/wlk/folder -i /path/to/my/2021-01.wlk -i /path/to/my/2021-02.wlk -i /path/to/another/wlk/folder`
4. `-o` is optional: if no path is provided the output will be saved in the current folder. Output files are always named as `DailySummary_${yyyy}-${mm}.${ext}` and `DailyData_${yyyy}-${mm}.${ext}`.
5. `-u` is optional. Used to choose between International Unit System, and Imperial Unit System in the printed output. If it is not provided default values.
   
   **Default values**: `Length:kilometre`, `Precipitation:millimetre`, `Pressure:hectopascal`, `RainRate:millimetreperhour`, `Speed:kilometreperhour`, `Temperature:celsius`
   
   **SI values**: `Length:metre`, `Precipitation:millimetre`, `Pressure:pascal`, `RainRate:millimetreperhour`, `Speed:metrepersecond`, `Temperature:celsius`
   
   **Imperial values**: `Length:mile`, `Precipitation:inch`, `Pressure:inchesofmercury`, `RainRate:inchperhour`, `Speed:mileperhour`, `Temperature:fahrenheit`
6. `-s`: is optional. Used to don't print the header in the generated csv files. Default: false.
7. `-f`: at the moment is **not used**, since only `csv` is supported as output format
8. `-p` is optional: path to text files with list of fields to be printed in output.

   To define daily data fields to be printed, begin the line with `DAILY_DATA_FIELDS:` header, and then specify a comma separated values list of fields.
   
   To define daily summary fields to be printed, begin the line with `DAILY_SUMMARY_FIELDS:` header, and then specify a comma separated values list of fields.
   
   Example:
    ```
    DAILY_DATA_FIELDS:date,time,outsideTemp,outsideHum,windSpeed,windDirection
    DAILY_SUMMARY_FIELDS:hiOutTemp,hiOutTempTime,hiBar,hiBarTime
    ```
   Fields will be printed in the same order as defined in the file.
   **If no path is provided** or **if the corresponding line is missing** all the fields will be printed. **If no fields follow the line header**, then the related output file will not be printed.

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
