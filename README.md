# MOVIE SEARCH APP

Movie Search Application
Welcome to the Movie Search Application! This Java project is part of the Phase 1 Integrative Project for the Java Developer Bootcamp.

Table of Contents.        
Overview.        
Technologies Used.        
Database Schema.        
Java Version.        
Author.        
Contact.        

Overview.        
The Movie Search Application is a Java-based program that interacts with a MySQL database to perform various operations related to movie management. It provides a user-friendly interface through the command line, allowing users to easily search for movies by title or genre, add new movies, update existing movie information, and delete movies as needed.        

Technologies Used.        
Java: Core programming language used for application logic.        
MySQL: Database management system used to store movie data.        
JDBC (Java Database Connectivity): Java API for connecting and executing SQL queries on the MySQL database.        
Git: Version control system for tracking changes and collaboration.        

DataBase Schema	
	--- Database Creation ---	
        Create database if not exists Films;	
        use Films;	

--- Table movies ---		

Create table movies(	        
	id VARCHAR(50) PRIMARY KEY,	
 	title VARCHAR(255) NOT NULL,
	siteUrl VARCHAR(255),	
	imagePath VARCHAR(255),	
    	image LONGBLOB	
);	


--- Table genres ---	
Create table genres(	
	id INT AUTO_INCREMENT PRIMARY KEY,	
    	movie_id VARCHAR(50),	
    	genre VARCHAR(50),	
    	FOREIGN KEY (movie_id) REFERENCES movies(id)	
);	
	
--- Example Movies ---	
INSERT INTO movies(id,title,siteUrl,imagePath,image) VALUES	
('TDKR','Batman: The Dark Knight Rises','https://m.imdb.com/title/tt1345836/?language=en',NULL, NULL),	
('TM','The Mummy','https://m.imdb.com/title/tt0120616/?ref_=vp_close',NULL, NULL),	
('SWVTESB','Star Wars: Episode V - The Empire Strikes Back','https://m.imdb.com/title/tt0080684/?ref_=nv_sr_srsg_0_tt_8_nm_0_q_Star%2520Wars%2520em',NULL, NULL),	
('IJROTLA','Raiders of the Lost Ark','https://m.imdb.com/title/tt0082971/?ref_=nv_sr_srsg_11_tt_8_nm_0_q_Indiana%2520Jones',NULL, NULL);	
	
--- Example Genres ---	
INSERT INTO genres(movie_id, genre) VALUES	
('SWVTESB','Action'),	
('SWVTESB','Adventure'),	
('SWVTESB', 'Fantasy'),	
('TM','Action'),	
('TM','Adventure'),	
('TM', 'Fantasy'),	
('TDKR','Action'),	
('TDKR','Drama'),	
('TDKR', 'Thriller'),	
('IJROTLA','Action'),	
('IJROTLA','Adventure');	


=======
Note: Add the movie from the console, BACK TO THE FUTURE with the following data:       
ID: BTFF
Title: Back To The Future
URLSite: www.bttf.com
ImagePath: src\main\resources\Images\Back_To_The_Future.jpg

-> For the other movies, edit/modify with the image paths so that the images are loaded into the database, because I did not know how to load them with the schema.

        
Java Version        
The project is developed using Java 17.        

Author        
This project is part of the Java Developer Bootcamp Phase 1 Integrative Project and develped by Gonzalo Cardin.        
        
Contact Information        
For inquiries or suggestions, please contact Gonzalo Cardin via e-mail at gonzalocardin@gmail.com.         
>>>>>>> 1a9455cabd585580579bc54716d795025a74a48a

Thank you for using the Movie Search Application! We hope you find it useful and enjoyable.        
Note: This project is actively being developed to add new features and enhancements. Stay tuned for updates!        
