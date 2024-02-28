create sequence game_seq start with 1 increment by 50;
create sequence guess_seq start with 1 increment by 50;
create sequence user_profile_seq start with 1 increment by 50;
create table game
(
    length       integer                     not null,
    created      timestamp(6) with time zone not null,
    game_id      bigint                      not null,
    user_id      bigint                      not null,
    secret_code  varchar(12)                 not null,
    external_key UUID                        not null unique,
    pool         varchar(20)                 not null,
    primary key (game_id)
);
create table guess
(
    close        integer                     not null,
    correct      integer                     not null,
    created      timestamp(6) with time zone not null,
    game_id      bigint                      not null,
    guess_id     bigint                      not null,
    guess_text   varchar(12)                 not null,
    external_key UUID                        not null unique,
    primary key (guess_id)
);
create table user_profile
(
    created         timestamp(6) with time zone not null,
    modified        timestamp(6) with time zone not null,
    user_profile_id bigint                      not null,
    external_key    UUID                        not null unique,
    oauth_key       varchar(30)                 not null unique,
    display_name    varchar(50)                 not null unique,
    primary key (user_profile_id)
);
create index IDXrfvkieodby6r3f6b3fm1i0pmk on game (user_id, created);
create index IDXfowmds51e7gqmjoej486tbj3w on guess (game_id, created);
alter table if exists game
    add constraint FKr01mjyhctq2qcstrix8juol8o foreign key (user_id) references user_profile;
alter table if exists guess
    add constraint FK17wrv62yn4umhcoh8y608l16d foreign key (game_id) references game;
