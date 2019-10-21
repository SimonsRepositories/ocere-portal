USE ocere;

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

INSERT INTO client (id, status, assigned_user_id, author_id, city, company_name, contact_name, contact_us_page, email, phone, postcode, satisfaction, street, tier, website)
VALUES (1, 'ACTIVE', 1, 1, 'Cheltenham', 'Cheltenham-Ham', 'Ham Salesman #87', 'https://www.ocere.com/', 'cheltenham@ham.uk', '123456789012', 'GLP-50', 'Happy', 'Road 1', 'A', 'https://www.ocere.com/')
