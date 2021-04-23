# ✨allegro-summer-2021 ✨
My allegro summer e-xperience submission, task no 3!\
It's a HTTP server which you can use to retrive list of repos and sum of stars of any github user.

## How to install:
To install and run the server go to an empty directory of your choice and execute commands:
1. `git clone https://github.com/pwitkiewicz/allegro-summer-2021.git`
2. `cd allegro-summer-2021`
3. `gradlew run`

On unix systems you might need to type `chmod 755 gradlew` before 3rd command.

## How to use:
After this two commands server will start and be available at your browser, simply type `localhost:8080` at your address bar to access it.\
Type `localhost:8080/<username>` to look for user's repos and stars.\
Type `localhost:8080/shutdown` to turn off the server.

## Things to add/improve in future:
- Cuter HTML interface
- Implement handling any requests that can be processed by Github API.