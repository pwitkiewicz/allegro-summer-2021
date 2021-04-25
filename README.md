# ✨allegro-summer-2021 ✨
My allegro summer e-xperience submission, task no 3!\
It's a HTTP server which you can use to retrive list of repos and sum of stars of any github user.

## Requirements:
- Installed Java 11

## How to install:
### Easy way:
1. Go to [v1.0 release](https://github.com/pwitkiewicz/allegro-summer-2021/releases/tag/v1.0)
2. Download jar package
3. Run by executing `java -jar allegro-summer-2021-1.0.jar` in directory where you had downloaded the package

### If you would like to compile the app yourself:
1. Go to an directory of your choice and execute commands:
2. `git clone https://github.com/pwitkiewicz/allegro-summer-2021.git`
3. `cd allegro-summer-2021`
4. `gradlew run` on windows or `./gradlew run` on unix systems

On unix systems you might need to type `chmod 755 gradlew` before 3rd step.

IMPORTANT❗: Instead of steps 1 and 2 if you don't have git installed you can download zip with source code of the v1.0 release, unzip it, navigate to directory with unzipped source files and through terminal or cmd type command from third step.

## How to use:
After launching app (either by executing jar or `gradlew run`) server will start and be available at your browser, simply type `localhost:8080` at your address bar to access it.\
Type `localhost:8080/<username>` to look for user's repos and stars.\
Type `localhost:8080/stars/<username>` to get sum of stars for given user.\
Type `localhost:8080/shutdown` to turn off the server.

## Things to add/improve in future:
- Cuter HTML interface
- Implement handling any requests that can be processed by Github API.
