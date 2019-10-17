USE ocere;

INSERT INTO auth_role (auth_role_id, role_name, role_desc)
VALUES  (1,'SUPER_USER','This user has ultimate rights for everything'),
        (2,'ADMIN_USER','This user has admin rights for administrative work'),
        (3,'SITE_USER','This user has access to site, after login - normal user');

INSERT INTO auth_user (auth_user_id,first_name,last_name,email,password,status)
VALUES  (1,'Ankit','Wasankar','admin@gmail.com','$2a$10$DD/FQ0hTIprg3fGarZl1reK1f7tzgM4RuFKjAKyun0Si60w6g3v5i','VERIFIED');

INSERT INTO auth_user_role (auth_user_id, auth_role_id)
VALUES  ('1','1'),
        ('1','2'),
        ('1','3');

INSERT INTO turnaround (id, description, hours)
VALUES  (1, '2 Hours', 2),
        (2, '6 Hours', 6),
        (3, '12 Hours', 12),
        (4, '1 Day', 24),
        (5, '2 Days', 48),
        (6, '3 Days', 72),
        (7, '5 Days', 120),
        (8, '7 Days', 168);

INSERT INTO usergroup (group_id, is_active, created_at, is_empty, `name`, updated_at)
VALUES  (1, true, NOW(), false, 'GroupTest', NOW());
