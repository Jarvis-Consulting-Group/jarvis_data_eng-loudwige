--Insert Data into the facilities table, facid: 9, Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.
INSERT INTO cd.facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
VALUES (9, 'Spa', 20, 30, 100000, 800);

--Insert calculated data into facilities table, Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.
INSERT INTO cd.facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
SELECT(SELECT MAX(facid) FROM cd.facilities + 1), 'Spa', 20, 30, 100000, 800;

--The initial outlay was 10000 rather than 8000: you need to alter the data to fix the error.
UPDATE cd.facilities
    SET initialoutlay = 10000
    WHERE facid = 1;

--alter the price of the second tennis court so that it costs 10% more than the first one
UPDATE cd.facilities AS facs SET
                               membercost = facs2.membercost * 1.1,
                               guestcost =  facs2.memmbercost * 1.1
                             FROM (SELECT * FROM cd.facilities WHERE facid = 0) AS facs2
                             WHERE facs.facid = 1;

--delete all bookings from the cd.bookings table.
DELETE FROM cd.bookings;

--remove member 37, who has never made a booking, from our database.
DELETE FROM cd.members WHERE memid = 37;

-- produce a list of facilities that charge a fee to members, and that fee is less than 1/50th of the monthly maintenance cost
SELECT facid, name, membercost, monthlymaintenance from cd.facilities
                                                   WHERE
                                                       membercost > 0 AND
                                                       (membercost < monthlymaintenance / 50.0);
-- produce a list of all facilities with the word 'Tennis' in their name
SELECT * FROM cd.facilities WHERE name LIKE '%Tennis';

--retrieve the details of facilities with ID 1 and 5