insert into author values(0, 'George Raymond Richard Martin');
insert into book values(0, 'A Game of Thrones', 1996, (select id from author where id = 0));
insert into book values(1, 'A Clash of Kings', 1998, (select id from author where id = 0));
insert into book values(2, 'A Storm of Swords', 2000, (select id from author where id = 0));
insert into book values(3, 'A Feast for Crows', 2005, (select id from author where id = 0));
insert into book values(4, 'A Dance with Dragons', 2011, (select id from author where id = 0));

insert into author values(1, 'Joanne Rowling');
insert into book values(5, 'Harry Potter and the Philosopher''s Stone', 1997, (select id from author where id = 1));
insert into book values(6, 'Harry Potter and the Chamber of Secrets', 1998, (select id from author where id = 1));
insert into book values(7, 'Harry Potter and the Prisoner of Azkaban', 1999, (select id from author where id = 1));
insert into book values(8, 'Harry Potter and the Goblet of Fire', 2000, (select id from author where id = 1));
insert into book values(9, 'Harry Potter and the Order of the Phoenix', 2003, (select id from author where id = 1));
insert into book values(10, 'Harry Potter and the Half-Blood Prince', 2005, (select id from author where id = 1));
insert into book values(11, 'Harry Potter and the Deathly Hallows', 2007, (select id from author where id = 1));