use c9YdVqdx9s;

drop table task;
drop table project;
create table project
(
  id                        INT(5)                                NOT NULL AUTO_INCREMENT primary key,
  name                      VARCHAR(255),
  description               VARCHAR(255),
  date_of_creation          TIMESTAMP                             NOT NULL DEFAULT CURRENT_TIMESTAMP,
  date_of_last_modification TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

insert into project (name, description)
VALUES ("Project0", "Project0"),
       ("Project1", "Project1"),
       ("Project2", "Project2"),
       ("Project3", "Project3"),
       ("Project4", "Project4");

create table task
(
  id                        INT(5)                                NOT NULL AUTO_INCREMENT primary key,
  project_id                 INT REFERENCES `project` (`id`),
  name                      VARCHAR(255),
  description               VARCHAR(255),
  priority                  INT(1),
  date_of_creation          TIMESTAMP                             NOT NULL DEFAULT CURRENT_TIMESTAMP,
  date_of_last_modification TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status                    ENUM ('NEW','IN_PROGRESS','CLOSE')    NOT NULL
);

INSERT INTO task(project_id, name, description, priority, status)
VALUES (1, "Task1", "Task", 1, 'NEW'),
       (2, "Task3", "Task", 2, 'IN_PROGRESS'),
       (1, "Task2", "Task", 1, 'CLOSE');