# AdsManager
Default charset : UTF-8

Start instructions:
1)Clone repo on your computer.
[Configuration]
2)Open application.properties in resources/ folder.
3)Configure database information. You don't have to create all tables in database, just need to create a db with specified name.
4)Configure your admin and password account data. It will be created on startup of application.
5)Change server port if needed.
6)Open config.json in frontend/config/ folder and copy admin account data.
7)Specify your local computer address url.
[Application start config]
8)You need to create a run configuration to start an app. Add Spring Boot template configuration and specify main class. In this case it is AdManagerApplication. Set module classpath and check JDK version. Then move to bottom section called 'Before launch' and add 2 tasks before Build task. First one should be Maven Goal 'admanager: clean', then Maven Goal 'admanager: generate-resources'. And finally Build.
9)Launch an application. Frontend-maven-plugin is installed so you don't have to manually type anything in console. First run takes significant amount of time to download all fron-end and back-end libraries. At least on my PC :D 


When everything builds up you can open your browser and open /admin webpage to edit ads data. Or open /bid page to get some banner text from specified categories. 

Enjoy :D
