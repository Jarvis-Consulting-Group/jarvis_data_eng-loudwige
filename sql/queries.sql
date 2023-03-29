--Insert Data into the facilities table, facid: 9, Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.
INSERT INTO cd.facilities (
  facid, name, membercost, guestcost, 
  initialoutlay, monthlymaintenance
) 
VALUES 
  (9, 'Spa', 20, 30, 100000, 800);
  
--Insert calculated data into facilities table, Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.
INSERT INTO cd.facilities (
  facid, name, membercost, guestcost, 
  initialoutlay, monthlymaintenance
) 
SELECT 
  (
    SELECT 
      MAX(facid) 
    FROM 
      cd.facilities + 1
  ), 
  'Spa', 
  20, 
  30, 
  100000, 
  800;
  
--The initial outlay was 10000 rather than 8000: you need to alter the data to fix the error.
UPDATE 
  cd.facilities 
SET 
  initialoutlay = 10000 
WHERE 
  facid = 1;

--alter the price of the second tennis court so that it costs 10% more than the first one
UPDATE 
  cd.facilities AS facs 
SET 
  membercost = facs2.membercost * 1.1, 
  guestcost = facs2.memmbercost * 1.1 
FROM 
  (
    SELECT 
      * 
    FROM 
      cd.facilities 
    WHERE 
      facid = 0
  ) AS facs2 
WHERE 
  facs.facid = 1;

--delete all bookings from the cd.bookings table.
DELETE FROM 
  cd.bookings;

--remove member 37, who has never made a booking, from our database.
DELETE FROM 
  cd.members 
WHERE 
  memid = 37;

-- produce a list of facilities that charge a fee to members, and that fee is less than 1/50th of the monthly maintenance cost
SELECT 
  facid, 
  name, 
  membercost, 
  monthlymaintenance 
from 
  cd.facilities 
WHERE 
  membercost > 0 
  AND (
    membercost < monthlymaintenance / 50.0
  );

-- produce a list of all facilities with the word 'Tennis' in their name
SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  name LIKE '%Tennis';

--retrieve the details of facilities with ID 1 and 5
SELECT 
  facid, 
  name, 
  membercost, 
  guestcost, 
  initialoutlay, 
  monthlymaintenance 
FROM 
  cd.facilities 
WHERE 
  facid IN (1, 5);
  
-- Produce a list of members who joined after the start of September 2012
SELECT 
  memid, 
  surname, 
  firstname, 
  joindate 
FROM 
  cd.members 
WHERE 
  joindate >= '2012-09-01';
  
-- Provide a combined list of all surnames and all facility names. 
SELECT 
  surname 
FROM 
  cd.members 
UNION 
SELECT 
  name 
FROM 
  cd.facilities;
  
--Produce a list of the start times for bookings by members named 'David Farrell'
SELECT 
  starttime 
FROM 
  cd.bookings bk 
  INNER JOIN cd.members mbs ON mbs.memid = bk.memid 
WHERE 
  mbs.firstname = 'David' 
  AND mbs.surname = 'Farrell';
  
-- Return a list of start time and facility name pairings, ordered by the time.
SELECT 
  bk.starttime AS start, 
  fac.name AS name 
FROM 
  cd.facilities as fac 
  INNER JOIN cd.bookings AS bk ON fac.facid = bk.facid 
WHERE 
  bk.starttime >= '2012-09-21' 
  AND bk.starttime < '2012-09-22' 
  AND fac.name in (
    'Tennis Court 2', 'Tennis Court 1'
  ) 
ORDER BY 
  bk.starttime;
  
-- output a list of all members, including the individual who recommended them (if any)? Ensure that results are ordered by (surname, firstname).
SELECT 
  mbs.firstname AS memfname, 
  mbs.surname AS memsname, 
  rec.firstname AS recfname, 
  rec.surname AS recsname 
FROM 
  cd.members AS mbs 
  LEFT JOIN cd.members AS rec ON rec.memid = mbs.recommendedby 
ORDER BY 
  memsname, 
  memfname;
  
-- Output a list of all members who have recommended another member
SELECT 
  DISTINCT rec.firstname AS firstname, 
  rec.surname AS surname 
FROM 
  cd.members AS mbs 
  INNER JOIN cd.members AS rec ON rec.memid = mbs.recommendedby 
ORDER BY 
  surname, 
  firstname;
  
-- output a list of all members, including the individual who recommended them (if any), 
-- without using any joins? Ensure that there are no duplicates in the list, and that each firstname + surname pairing is formatted as a column and ordered.
SELECT 
  DISTINCT mems.firstname || ' ' || mems.surname AS member, 
  (
    SELECT 
      recs.firstname || ' ' || recs.surname AS recommender 
    FROM 
      cd.members AS recs 
    WHERE 
      recs.memid = mems.recommendedby
  ) 
FROM 
  cd.members AS mems 
ORDER BY 
  member;
  
 -- Produce a count of the recommendations each member has made. Order by member id
 select 
  recommendedby, 
  count(*) 
from 
  cd.members 
where 
  recommendedby is not null 
group by 
  recommendedby 
order by 
  recommendedby;
  
-- Produce a list of the total number of slots booked per facility. For now, just produce an output table consisting of facility id and slots, sorted by facility id.
SELECT 
  facid, 
  SUM(slots) AS slots 
FROM 
  cd.bookings 
GROUP BY 
  facid 
ORDER BY 
  facid;

-- Produce a list of the total number of slots booked per facility in the month of September 2012. 
-- Produce an output table consisting of facility id and slots, sorted by the number of slots.
SELECT 
  bk.facid, 
  SUM(bk.slots) AS "Total Slots" 
FROM 
  cd.bookings AS bk 
WHERE 
  bk.starttime >= '2012-09-01' 
  AND bk.starttime < '2012-10-01' 
GROUP BY 
  bk.facid 
ORDER BY 
  SUM(bk.slots);
  
-- Produce a list of the total number of slots booked per facility per month in the year of 2012. 
-- Produce an output table consisting of facility id and slots, sorted by the id and month.
 SELECT 
  facid, 
  extract(
    month 
    FROM 
      starttime
  ) AS month, 
  SUM(slots) AS "Total Slots" 
FROM 
  cd.bookings 
WHERE 
  extract(
    year 
    FROM 
      starttime
  ) = 2012 
GROUP BY 
  facid, 
  month 
ORDER BY 
  facid, 
  month;
  
-- Find the total number of members (including guests) who have made at least one booking.
SELECT 
  COUNT(DISTINCT memid) 
FROM 
  cd.bookings;
  
-- Retrieves a list of members who have made bookings on or after September 1, 2012, along with the earliest start time for each member's bookings. 
-- Group the result by memberid and order by memberid.
SELECT 
  mems.surname, 
  mems.firstname, 
  mems.memid, 
  MIN(bks.starttime) AS starttime 
FROM 
  cd.bookings bks 
  INNER JOIN cd.members mems on mems.memid = bks.memid 
WHERE 
  starttime >= '2012-09-01' 
GROUP BY 
  mems.surname, 
  mems.firstname, 
  mems.memid 
ORDER BY 
  mems.memid;
  
-- Produce a list of member names, with each row containing the total member count
SELECT 
  COUNT(*) over(), 
  firstname, 
  surname 
FROM 
  cd.members 
ORDER BY 
  joindate;

-- Produce a monotonically increasing numbered list of members (including guests), ordered by their date of joining.
SELECT 
  row_number() over(
    ORDER BY 
      joindate
  ), 
  firstname, 
  surname 
FROM 
  cd.members 
ORDER BY 
  joindate;
  
-- Output the facility id that has the highest number of slots booked, again
  SELECT 
  facid, 
  total 
FROM 
  (
    SELECT 
      facid, 
      sum(slots) total, 
      rank() over (
        ORDER BY 
          sum(slots) DESC
      ) rank 
    FROM 
      cd.bookings 
    GROUP BY 
      facid
  ) AS ranked 
WHERE 
  rank = 1;

-- Output the names of all members, formatted as 'Surname, Firstname'
SELECT 
  surname || ', ' || firstname AS name 
FROM 
  cd.members;
  
-- Find telephone numbers with parentheses
SELECT 
  memid, 
  telephone 
FROM 
  cd.members 
WHERE 
  telephone ~ '[()]';
  
-- Count the number of members whose surname starts with each letter of the alphabet
select 
  substr (mems.surname, 1, 1) as letter, 
  count(*) as count 
from 
  cd.members mems 
group by 
  letter 
order by 
  letter;
