SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE `Account` (
`id` int(11) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `date_created` datetime DEFAULT NULL,
  `date_last_modified` datetime DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

CREATE TABLE `ToDoItem` (
`id` int(11) NOT NULL,
  `title` varchar(500) NOT NULL,
  `description` varchar(10000) DEFAULT NULL,
  `notes` varchar(10000) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `date_due` datetime DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `date_last_modified` datetime DEFAULT NULL,
  `account_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

CREATE TABLE `Token` (
`id` int(11) NOT NULL,
  `token` varchar(200) NOT NULL,
  `date_will_expire` datetime NOT NULL,
  `date_created` datetime DEFAULT NULL,
  `date_last_modified` datetime DEFAULT NULL,
  `account_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;


ALTER TABLE `Account`
 ADD PRIMARY KEY (`id`);

ALTER TABLE `ToDoItem`
 ADD PRIMARY KEY (`id`), ADD KEY `fk_AccountTodoitems` (`account_id`);

ALTER TABLE `Token`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `unique_index_account_id` (`account_id`);


ALTER TABLE `Account`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
ALTER TABLE `ToDoItem`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
ALTER TABLE `Token`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;

ALTER TABLE `ToDoItem`
ADD CONSTRAINT `fk_AccountTodoitems` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`);

ALTER TABLE `Token`
ADD CONSTRAINT `fk_AccountToken` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`);
