use c9YdVqdx9s;
create table project
(
  id             int primary key,
  name           varchar(255),
  description    varchar(255),
  dateOfCreation timestamp not null default now()
  #   dateOfLastModification timestamp not null default now()
);

ALTER TABLE project
  CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT;

create table task
(
  id             int primary key,
  # TODO: связать с таблицей проектов
  projectID      int,
  name           varchar(255),
  description    varchar(255),
  priority       int(1),
  dateOfCreation timestamp not null default now(),
  #   dateOfLastModification timestamp not null default now(),
  status         ENUM ('NEW','IN_PROGRESS','CLOSE')
);

ALTER TABLE `task`
  ADD FOREIGN KEY (`projectID`)
    REFERENCES `project`(`id`);