create table if not exists categorias(

    id bigint not null,
    nombre varchar(100) not null,

    primary key(id)

);
insert into categorias
    (id,nombre)
values
    (1,"PROGRAMACION"),
    (2,"FRONT END"),
    (3,"DATA SCIENCE"),
    (4,"INNOVATION"),
    (5,"DEV OPS"),
    (6,"OFF TOPIC");