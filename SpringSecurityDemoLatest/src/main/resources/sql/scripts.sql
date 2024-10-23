create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

--  admin : 6789
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES
                ('admin', '{bcrypt}$2a$12$tusR2cYXOyiRGyH1ZZRBgOMCLwCdbgJvkf2bogXYbesOccdTmILVy', 1),
                 ('user', '{noop}demo.techgatha@12345', 1);

INSERT INTO `authorities` (`username`, `authority`) VALUES
  ('admin', 'admin'),
('user', 'read');

CREATE TABLE `customer` (
`id` int NOT NULL AUTO_INCREMENT,
`email` varchar(45) NOT NULL,
`pwd` varchar(200) NOT NULL,
`role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `customer`( `email`, `pwd`, `role`) VALUES
            ('shalini@example.com','{noop}shalini.techgatha@12345','read'),
            ('admin@example.com','{bcrypt}$2a$12$tusR2cYXOyiRGyH1ZZRBgOMCLwCdbgJvkf2bogXYbesOccdTmILVy','admin');