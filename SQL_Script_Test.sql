/*Name: Siam Al-mer Chowdhury, Steve Tran*/

Drop table Actor cascade constraints;
Drop table Genre cascade constraints;
Drop table Likes cascade constraints;
Drop table Member cascade constraints;
Drop table Movie cascade constraints;
Drop table Movie_Genre cascade constraints;
Drop table Profile cascade constraints;
Drop table Starred_by cascade constraints;
Drop table Watch cascade constraints;



Create table Member (
	member_id  varchar2(10),
	first_name varchar2(25),
	last_name varchar2(25),
	card_number INTEGER,
	exp_date varchar2(10),
	Primary key (member_id)
);



Create table Profile(
	member_id varchar2(10),
	profile_name varchar2(20),
	Primary key(member_id,profile_name),
	Foreign Key (member_id) References Member (member_id)
);


Create table Movie(
movie_id varchar(10),
title varchar2(50),
movie_year varchar2(12),
producer varchar(25),
avg_rating varchar2(10),
Primary Key(movie_id)
);


Create table Watch(
	member_id  varchar2(10),
	profile_name varchar2(20),
	movie_id varchar2(10),
	rating varchar2(5),
	Primary key (member_id,profile_name,movie_id),
	Foreign Key (movie_id) References Movie (movie_id),
    	Foreign Key (member_id,profile_name) References Profile (member_id,profile_name)
);




Create table Actor(
	actor_id varchar2(10),
	first_name varchar2(25),
	last_name varchar2(25),
	Primary key(actor_id)
);



Create Table Genre(
    m_genre varchar2(10),
    Primary key(m_genre)
);


Create table Movie_Genre(
	movie_id varchar2(10),
	m_genre varchar2(10),
	Primary key(movie_id,m_genre),
	Foreign Key (movie_id) References Movie (movie_id),
	Foreign Key (m_genre) References Genre (m_genre)
);


Create table Likes(
    member_id  varchar2(10),
    profile_name varchar2(20),
    m_genre varchar2(10),
    Primary key(member_id, profile_name, m_genre),
    Foreign Key (member_id,profile_name) References Profile (member_id,profile_name),
    Foreign Key (m_genre) References Genre (m_genre)
);




Create table Starred_By(
	actor_id varchar2(10),
	movie_id varchar2(10),
	Primary key(actor_id, movie_id),
	Foreign Key (actor_id) References Actor (actor_id),
   	Foreign Key (movie_id) References Movie (movie_id)
);




/*----------------------------------------------------------------------------------------------*/


/*Movies*/

Insert into Movie values('1', 'Avengers', '2020', 'Kevin Feige','1');
Insert into Movie values('2', 'Black', '2019', 'Sara','2');
Insert into Movie values('3', 'White', '2000', 'Kevin Feige','3');
Insert into Movie values('4', 'Spy Kids 1', '2002', 'Dacosta','4');
Insert into Movie values('5', 'Spy kids 2', '2004', 'Dacosta','5');
Insert into Movie values('6','Toy Story','1995','John Lasseter','6');
Insert into Movie values('7','Minions','2015','Pierre Coffin','7');
Insert into Movie values('8','Frozen','2013','Jennifer Lee','8');
Insert into Movie values('9','Mulan','2020','Niki Caro','9');
Insert into Movie values('10','Avatar','2009','James Cameron','10');



/*Accounts*/

Insert into Member values('M1', 'Steve', 'Smith', '23121', '01/01/2030');
Insert into Member values('M2', 'Steve', 'Jobs', '23122', '01/01/2029');
Insert into Member values('M3','Bob','Ross','11111', '05/01/2021');
Insert into Member values('M4', 'James', 'Charles', '22222', '10/10/2025');
Insert into Member values('M5', 'Jane', 'Rose', '33333', '12/05/2028');


---Profile
Insert into Profile values('M1', 'stevesmith');
Insert into Profile values('M1', 'stevesmith2');
Insert into Profile values('M2', 'stevejobs');
Insert into Profile values('M2', 'stevejobs2');
Insert into Profile values('M3', 'bobross');
Insert into Profile values('M3', 'bobjunior');
Insert into Profile values('M4','jamesch');
Insert into Profile values('M4','jamesch1');
Insert into Profile values('M4','jamesjr');
Insert into Profile values('M5','janerose');
Insert into Profile values('M5','janerose2');
Insert into Profile values('M5','janerose3');




/*Actor*/
Insert into Actor values('A1','Tom', 'Cruise');
Insert into Actor values('A2','Tom', 'Hanks2');
Insert into Actor values('A3','Donald', 'Trump');
Insert into Actor values('A4', 'Adam', 'Second');
Insert into Actor values('A5', 'Johny', 'Dep');
Insert into Actor values('A6', 'Bell', 'Phine');
Insert into Actor values('A7', 'Di', 'Caprio');
Insert into Actor values('A8', 'Bradd', 'Pitt');
Insert into Actor values('A9', 'Will', 'Smith');
Insert into Actor values('A10', 'Jim', 'Carry');
Insert into Actor values('A11','Wallace','Shawn');
Insert into Actor values('A12','Tom','Hanks');
Insert into Actor values('A13','John','Hamm');
Insert into Actor values('A14','Steve','Coogan');
Insert into Actor values('A15','Josh','Gad');
Insert into Actor values('A16','Kristen','Bell');
Insert into Actor values('A17','Jet','Li');
Insert into Actor values('A18','Gong','Li');
Insert into Actor values('A19','Stephan','Lang');
Insert into Actor values('A20','Laz','Alonso');




/*Genre*/
Insert into Genre values('Family');
Insert into Genre values('Musical');
Insert into Genre values('Scifi');
Insert into Genre values('Action');

/*Movie Genre*/
Insert into Movie_Genre values('10','Scifi');
Insert into Movie_Genre values('9','Musical');
Insert into Movie_Genre values('8','Musical');
Insert into Movie_Genre values('7','Family');
Insert into Movie_Genre values('6','Family');
Insert into Movie_Genre values('5','Family');
Insert into Movie_Genre values('4','Family');
Insert into Movie_Genre values('3','Scifi');
Insert into Movie_Genre values('2','Action');
Insert into Movie_Genre values('1','Action');




/*Watch*/
Insert into  Watch values ('M1','stevesmith','1','1');
Insert into  Watch values ('M1','stevesmith2','2','2');
Insert into  Watch values ('M2','stevejobs','3','3');
Insert into  Watch values ('M2','stevejobs2','4','4');
Insert into  Watch values ('M3','bobross','5','5');
Insert into  Watch values ('M3','bobjunior','6','6');
Insert into  Watch values ('M4','jamesch','7','7');
Insert into  Watch values ('M4','jamesch1','8','8');
Insert into  Watch values ('M4','jamesjr','9','9');
Insert into  Watch values ('M5','janerose','10','10');
Insert into  Watch values ('M5','janerose2','1','1');
Insert into  Watch values ('M5','janerose3','2','2');


/*Likes*/
 Insert into Likes values('M1', 'stevesmith', 'Family');
 Insert into Likes values('M1', 'stevesmith2', 'Musical');
 Insert into Likes values('M2', 'stevejobs', 'Musical');
 Insert into Likes values('M2', 'stevejobs2', 'Action');
 Insert into Likes values('M3', 'bobross', 'Scifi');
 Insert into Likes values('M3', 'bobjunior', 'Family');
 Insert into Likes values('M4', 'jamesch', 'Action');
 Insert into Likes values('M4', 'jamesch1', 'Family');
 Insert into Likes values('M4', 'jamesjr', 'Musical');
 Insert into Likes values('M5', 'janerose', 'Scifi');
 Insert into Likes values('M5', 'janerose2', 'Family');
 Insert into Likes values('M5', 'janerose3', 'Scifi');
 
 
 /*Starred_By*/
Insert into Starred_By values('A1', '1');
Insert into Starred_By values('A2', '2');
Insert into Starred_By values('A3', '3');
Insert into Starred_By values('A4', '4');
Insert into Starred_By values('A4', '5');
Insert into Starred_By values('A5', '6');
Insert into Starred_By values('A6', '7');
Insert into Starred_By values('A7', '8');
Insert into Starred_By values('A8', '9');
Insert into Starred_By values('A9', '10');
Insert into Starred_By values('A10', '5');
Insert into Starred_By values('A11', '4');
Insert into Starred_By values('A12', '8');
Insert into Starred_By values('A13', '4');
Insert into Starred_By values('A14', '7');
Insert into Starred_By values('A15', '9');
Insert into Starred_By values('A16', '1');
Insert into Starred_By values('A17', '3');
Insert into Starred_By values('A18', '5');
Insert into Starred_By values('A19', '7');
Insert into Starred_By values('A20', '2');

select * from movie;
select * from member;
select * from profile;
select * from actor;
select * from genre;
select * from watch;
select * from likes;
select * from movie_genre;
select * from starred_by;



