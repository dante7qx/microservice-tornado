-- 用户数据
insert into t_user(id, account,name,password,last_pwd_update_date,status,email) values
(1, 'superadmin','超级管理员','$2a$10$tjOiDRuFyuyLCszfrFIlfeu.ODX29jaem3wKJ0.Gq0.fY0p3QvfF.',now(),'NORMAL','admin@admin.com');

alter table t_user add constraint pk_t_user primary key (id) ;
alter table t_user modify column id bigint(20) not null auto_increment;

-- 权限数据
insert into t_authority(id, code, name, authority_desc, pid, show_order, update_user, update_date) values
(1, 'public', '公共权限', '公共权限', null, 1, 1, now()),
(2, 'public', '系统管理', '系统管理', null, 2, 1, now()),

(3, 'public', '用户管理', '用户管理', 2, 1, 1, now()),
(4, 'sysmgr.user.query', '查询用户', '查询用户', 3, 1, 1, now()),
(5, 'sysmgr.user.update', '更新用户', '更新用户', 3, 2, 1, now()),
(6, 'sysmgr.user.delete', '删除用户', '删除用户', 3, 3, 1, now()),

(7, 'public', '角色管理', '角色管理', 2, 2, 1, now()),
(8, 'sysmgr.role.query', '查询角色', '查询角色', 7, 1, 1, now()),
(9, 'sysmgr.role.update', '更新角色', '更新角色', 7, 2, 1, now()),
(10, 'sysmgr.role.delete', '删除角色', '删除角色', 7, 3, 1, now()),

(11, 'public', '权限管理', '权限管理', 2, 3, 1, now()),
(12, 'sysmgr.authority.query', '查询权限', '查询权限', 11, 1, 1, now()),
(13, 'sysmgr.authority.update', '更新权限', '更新权限', 11, 2, 1, now()),
(14, 'sysmgr.authority.delete', '删除权限', '删除权限', 11, 3, 1, now()),

(15, 'public', '菜单管理', '菜单管理', 2, 4, 1, now()),
(16, 'sysmgr.resource.query', '查询菜单', '查询菜单', 15, 1, 1, now()),
(17, 'sysmgr.resource.update', '更新菜单', '更新菜单', 15, 2, 1, now()),
(18, 'sysmgr.resource.delete', '删除菜单', '删除菜单', 15, 3, 1, now()),

(19, 'public', '服务模块管理', '服务模块管理', 2, 5, 1, now()),
(20, 'sysmgr.servicemodule.query', '查询服务模块', '查询服务模块', 19, 1, 1, now()),
(21, 'sysmgr.servicemodule.update', '更新服务模块', '更新服务模块', 19, 2, 1, now()),
(22, 'sysmgr.servicemodule.delete', '删除服务模块', '删除服务模块', 19, 3, 1, now()),

(23, 'public', 'IP规则管理', 'IP规则管理', 2, 6, 1, now()),
(24, 'sysmgr.iprule.query', '查询服务模块', '查询服务模块', 23, 1, 1, now()),
(25, 'sysmgr.iprule.update', '更新服务模块', '更新服务模块', 23, 2, 1, now()),
(26, 'sysmgr.iprule.delete', '删除服务模块', '删除服务模块', 23, 3, 1, now())
;

alter table t_authority add constraint pk_t_authority primary key (id) ;
alter table t_authority modify column id bigint(20) not null auto_increment;

-- 微服务模块数据
insert into t_service_module (id, name, url, update_user, update_date) values
(1, '系统管理服务模块', 'sysmgr', 1, now());

alter table t_service_module add constraint pk_t_service_module primary key (id) ;
alter table t_service_module modify column id bigint(20) not null auto_increment;

-- 资源数据
insert into t_resource(id, name, url, service_module_id, pid, authority_id, full_id, show_order, resource_desc,update_user,update_date) values 
(1, '系统管理', '-', 1, null, 1, '1', 1, '系统管理', 1, now()),
(2, '用户管理', 'user', 1, 1, 4, '2-1', 1, '用户管理', 1, now()),
(3, '角色管理', 'role', 1, 1, 8, '3-1', 2, '角色管理', 1, now()),
(4, '权限管理', 'authority', 1, 1, 12, '4-1', 3, '权限管理', 1, now()),
(5, '菜单管理', 'menu', 1, 1, 16, '5-1', 4, '菜单管理', 1, now()),
(6, '服务模块管理', 'servicemodule', 1, 1, 16, '6-1', 5, '服务模块管理', 1, now());

alter table t_resource add constraint pk_t_resource primary key (id) ;
alter table t_resource add constraint fk_t_resource_authority_id foreign key (authority_id) references t_authority (id);
alter table t_resource add constraint fk_t_resource_service_module_id foreign key (service_module_id) references t_service_module (id);
alter table t_resource modify column id bigint(20) not null auto_increment;

-- 角色数据
insert into t_role(id, name, role_desc, update_user, update_date) values
(1, '系统管理员', '管理系统管理服务模块中的各个功能', 1, now());

alter table t_role add constraint pk_t_role primary key (id) ;
alter table t_role modify column id bigint(20) not null auto_increment;


-- 角色权限数据
insert into t_role_authority(id, role_id, authority_id) values
(1, 1, 1),
(2, 1, 4),
(3, 1, 5),
(4, 1, 6),
(5, 1, 8),
(6, 1, 9),
(7, 1, 10),
(8, 1, 12),
(9, 1, 13),
(10, 1, 14),
(11, 1, 16),
(12, 1, 17),
(13, 1, 18),
(14, 1, 20),
(15, 1, 21),
(16, 1, 22),
(17, 1, 24),
(18, 1, 25),
(19, 1, 26);

alter table t_role_authority add constraint t_role_authority_pk primary key (id);
alter table t_role_authority add constraint t_role_authority_authority_id_fk foreign key (authority_id) references t_authority (id);
alter table t_role_authority add constraint t_role_authority_role_id_fk foreign key (role_id) references t_role (id);
alter table t_role_authority modify column id bigint(20) not null auto_increment;

-- 用户角色数据
insert into t_user_role(id, user_id, role_id) values 
(1, 1, 1);

alter table t_user_role add constraint t_user_role_pk primary key (id);
alter table t_user_role add constraint t_user_role_user_id_fk foreign key (user_id) references t_user (id);
alter table t_user_role add constraint t_user_role_role_id_fk foreign key (role_id) references t_role (id);
alter table t_user_role modify column id bigint(20) not null auto_increment;

-- IP 规则数据
insert into t_ip_rule(ip, update_user, remark, active) values ('127.0.0.1', 1, '本地测试', 1);