CREATE TABLE super_heroes (
    id					int not null AUTO_INCREMENT,
    name          		varchar(80) not null,
    height              smallint not null,
    mass	 			smallint not null,
    gender				varchar(50)	not null,
    specie				varchar(50)	not null,
    primary key (id)
);