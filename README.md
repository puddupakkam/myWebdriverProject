# myWebdriverProject

Copyright (c) [2014] - [Pavandeep Puddupakkam] and the [SeleniumWiki] contributors.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

I will update the read me file soon. The file will have information of the changes that you need to do get you started with webdriver.
Install TestNG on Eclipse to run the scripts:
http://testng.org/doc/download.html

To start the selenium remote server
c:
java -jar C:\Softwares\selenium-server-standalone-2.48.2.jar -role node -hub http://192.168.0.140:4444/grid/register -port 7010 -browser "maxInstances=10, platform=WINDOWS"  -Dwebdriver.chrome.driver="C:\Softwares\chromedriver.exe" -Dwebdriver.ie.driver="C:\Softwares\IEDriverServer_x64_2.48.0\IEDriverServer.exe"
