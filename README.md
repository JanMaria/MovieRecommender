This is my first serious summary project written in Java. It is meant to be a beginning of my portfolio. I have written this code as a final assignment in Duke’s Java Programming Specialization on Coursera.org. Since then I made some important changes in it so as to adapt it to meet my goal, which is to show my current level of Java skills. 
If you ask me I would highlight the following:
-	reading data from an URL
-	efficiency solutions 
-	parsing database
-	some basic understanding of streams, functional interfaces and lambda expressions
-	interfaces (although an interface contained in the program is utterly redundant)
-	exceptions handling
-	data sorting and filtering methods

The program is a movie recommender. It makes use of a frequently updated online database which consist of basic information about movies(title, year, genres) and twitter movie ratings. The database can be found under this link: 
https://github.com/sidooms/MovieTweetings
In its basic form the recommender provides movie recommendations for a particular user in the database. These recommendations are based on a similarity measures between the user in question and other users. There are some filters provided which allows to specify characteristics of movies that are to be taken into consideration. The recommender has an option to print the results in terminal or to print them as an html table. 
