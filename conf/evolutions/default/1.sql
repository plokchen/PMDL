# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table task (
  id                            bigint auto_increment not null,
  title_id                      bigint not null,
  description                   varchar(255),
  constraint pk_task primary key (id)
);

create table task_user (
  task_id                       bigint not null,
  user_id                       bigint not null,
  done_at                       TIMESTAMP DEFAULT NOW(),
  constraint pk_task_user primary key (task_id,user_id)
);

create table task_parents (
  task_id                       bigint not null,
  parent_id                     bigint not null,
  constraint pk_task_parents primary key (task_id,parent_id)
);

create table title (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  constraint pk_title primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  password                      varchar(255),
  constraint pk_user primary key (id)
);

create table user_title (
  user_id                       bigint not null,
  title_id                      bigint not null,
  constraint pk_user_title primary key (user_id,title_id)
);

alter table task add constraint fk_task_title_id foreign key (title_id) references title (id) on delete restrict on update restrict;
create index ix_task_title_id on task (title_id);

alter table task_user add constraint fk_task_user_task foreign key (task_id) references task (id) on delete restrict on update restrict;
create index ix_task_user_task on task_user (task_id);

alter table task_user add constraint fk_task_user_user foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_task_user_user on task_user (user_id);

alter table task_parents add constraint fk_task_parents_task_1 foreign key (task_id) references task (id) on delete restrict on update restrict;
create index ix_task_parents_task_1 on task_parents (task_id);

alter table task_parents add constraint fk_task_parents_task_2 foreign key (parent_id) references task (id) on delete restrict on update restrict;
create index ix_task_parents_task_2 on task_parents (parent_id);

alter table user_title add constraint fk_user_title_user foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_user_title_user on user_title (user_id);

alter table user_title add constraint fk_user_title_title foreign key (title_id) references title (id) on delete restrict on update restrict;
create index ix_user_title_title on user_title (title_id);


# --- !Downs

alter table task drop foreign key fk_task_title_id;
drop index ix_task_title_id on task;

alter table task_user drop foreign key fk_task_user_task;
drop index ix_task_user_task on task_user;

alter table task_user drop foreign key fk_task_user_user;
drop index ix_task_user_user on task_user;

alter table task_parents drop foreign key fk_task_parents_task_1;
drop index ix_task_parents_task_1 on task_parents;

alter table task_parents drop foreign key fk_task_parents_task_2;
drop index ix_task_parents_task_2 on task_parents;

alter table user_title drop foreign key fk_user_title_user;
drop index ix_user_title_user on user_title;

alter table user_title drop foreign key fk_user_title_title;
drop index ix_user_title_title on user_title;

drop table if exists task;

drop table if exists task_user;

drop table if exists task_parents;

drop table if exists title;

drop table if exists user;

drop table if exists user_title;

