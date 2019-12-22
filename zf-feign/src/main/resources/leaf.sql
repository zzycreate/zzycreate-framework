DROP TABLE IF EXISTS `leaf_alloc`;
CREATE TABLE `leaf_alloc` (
  `biz_tag` varchar(128)  NOT NULL DEFAULT '',
  `max_id` bigint(20) NOT NULL DEFAULT '1',
  `step` int(11) NOT NULL,
  `description` varchar(256)  DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`biz_tag`)
) ENGINE=InnoDB;

insert into leaf_alloc(biz_tag, max_id, step, description)
values ('zzz-user_test-id', 1, 50, 'zzz-user的test_id');

-- rbac
insert into `leaf`.`leaf_alloc`(biz_tag, max_id, step, description) values ('zzz_user-user_id', 1, 50, 'zzz-user的user_id');
insert into `leaf`.`leaf_alloc`(biz_tag, max_id, step, description) values ('zzz_user-user_role_id', 1, 50, 'zzz-user的user_role_id');
insert into `leaf`.`leaf_alloc`(biz_tag, max_id, step, description) values ('zzz_user-role_id', 1, 50, 'zzz-user的role_id');
insert into `leaf`.`leaf_alloc`(biz_tag, max_id, step, description) values ('zzz_user-role_perm_id', 1, 50, 'zzz-user的role_perm_id');
insert into `leaf`.`leaf_alloc`(biz_tag, max_id, step, description) values ('zzz_user-perm_id', 1, 50, 'zzz-user的perm_id');
