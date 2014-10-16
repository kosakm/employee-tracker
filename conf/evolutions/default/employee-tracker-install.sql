# Create tables
# --- !Ups

CREATE TABLE location_event (
associate_id integer,
department_id integer,
site_id integer,
event_time timestamp,
event_type integer
);

CREATE TABLE department (
department_id integer PRIMARY KEY,
department_name varchar
);

CREATE TABLE associate (
associate_id integer PRIMARY KEY,
first_name varchar,
last_name varchar
);

# --- !Downs

DROP TABLE location_event;

DROP TABLE associate;

DROP TABLE department;

# Add foreign keys
# --- !Ups

ALTER TABLE location_event ADD CONSTRAINT loc_assoc_fk FOREIGN KEY (associate_id) REFERENCES associate(associate_id) MATCH FULL;

ALTER TABLE location_event ADD CONSTRAINT loc_dep_fk FOREIGN KEY (department_id) REFERENCES department(department_id) MATCH FULL;

# --- !Downs

ALTER TABLE location_event DROP CONSTRAINT loc_assoc_fk;

ALTER TABLE location_event DROP CONSTRAINT loc_dep_fk;

# Insert employee data
# --- !Ups

INSERT INTO associate
	VALUES (12, 'Eva', 'Asplund');
	
INSERT INTO associate
	VALUES (13, 'Austin', 'Luck');
	
INSERT INTO associate
	VALUES (14, 'Matt', 'Kosak');
	
INSERT INTO associate
	VALUES (15, 'Peyton', 'Wilkerson');
	
INSERT INTO associate
	VALUES (16, 'Josh', 'Intern');
	
INSERT INTO associate
	VALUES (17, 'Adam', 'Fisch');
	
INSERT INTO associate
	VALUES (18, 'Jayson', 'Lewis');
	
# --- !Downs

DELETE FROM associate
	WHERE associate_id = 12;
	
DELETE FROM associate
	WHERE associate_id = 13;
	
DELETE FROM associate
	WHERE associate_id = 14;
	
DELETE FROM associate
	WHERE associate_id = 15;
	
DELETE FROM associate
	WHERE associate_id = 16;
	
DELETE FROM associate
	WHERE associate_id = 17;
	
DELETE FROM associate
	WHERE associate_id = 18;
	
# Add departments
# --- !Ups

INSERT INTO department
	VALUES (24, 'Lingerie');
	
INSERT INTO department
	VALUES (25, 'Mens Lingerie');
	
INSERT INTO department
	VALUES (26, 'Dairy');
	
INSERT INTO department
	VALUES (27, 'Electronics');
	
# --- !Downs

DELETE FROM department
	WHERE department_id = 24;
	
DELETE FROM department
	WHERE department_id = 25;
	
DELETE FROM department
	WHERE department_id = 26;
	
DELETE FROM department
	WHERE department_id = 27;