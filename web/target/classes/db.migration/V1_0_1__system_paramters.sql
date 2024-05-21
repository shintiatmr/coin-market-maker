create table system_parameters
(
    id             binary(16)   not null primary key,
    updated_at     datetime(6)  not null,
    created_at     datetime(6)  not null,
    deleted_at     datetime(6)  null,
    variable       varchar(255) null,
    value          varchar(255) null,
    description    varchar(255) null
)
    charset = utf8mb4;
create unique index system_parameters_variable_unique_idx
    on system_parameters (variable);