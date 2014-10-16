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