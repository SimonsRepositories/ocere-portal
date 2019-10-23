    USE ocere;

    INSERT INTO auth_user (auth_user_id,first_name,last_name,email,password,mailpassword,status)
    VALUES  (1,'Ankit','Wasankar','admin@gmail.com','$2a$10$DD/FQ0hTIprg3fGarZl1reK1f7tzgM4RuFKjAKyun0Si60w6g3v5i','$2a$10$DD/FQ0hTIprg3fGarZl1reK1f7tzgM4RuFKjAKyun0Si60w6g3v5i','VERIFIED');

    INSERT INTO auth_user_role (auth_user_id, auth_role_id)
    VALUES  ('1','1'),
            ('1','2'),
            ('1','3');

    INSERT INTO `auth_role` (`auth_role_id`, `role_name`, `role_desc`) VALUES
    (1, 'DEV_USER', 'DEV_USER'),
    (2, 'ADMIN_USER', 'ADMIN_USER'),
    (3, 'STAFF_USER', 'STAFF_USER'),
    (4, 'CLIENT_USER', 'CLIENT_USER');

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

    INSERT INTO client (id, status, assigned_user_id, author_id, city, company_name, contact_first_name, contact_last_name, contact_us_page, email, phone, postcode, satisfaction, street, tier, website)
    VALUES (1, 'ACTIVE', 1, 1, 'Cheltenham', 'Cheltenham-Ham', 'Ham' , 'Hamster', 'https://www.ocere.com/', 'cheltenham@ham.uk', '123456789012', 'GLP-50', 'HAPPY', 'Road 1', 'A', 'https://www.ocere.com/');

    INSERT INTO `ticket` (`ticket_id`, `created_at`, `description`, `priority`, `status`, `subject`, `template`, `group_id`, `assigned_user_id`, `author_id`, `job_id`, `turnaround_id`) VALUES
    (1, '2019-10-18 16:20:02', '', 1, 0, 'PPC Review + Proposal', b'1', NULL, NULL, 1, NULL, 4),
    (2, '2019-10-18 16:31:08', '', 1, 0, 'Digital Review + Proposal', b'1', NULL, NULL, 1, NULL, 4),
    (3, '2019-10-18 16:31:29', '', 1, 0, 'Source sites for Outreach (new client)', b'1', NULL, NULL, 1, NULL, 4),
    (4, '2019-10-18 16:31:53', '', 1, 0, 'GA Analysis for Potential Client (need guidelines)', b'1', NULL, NULL, 1, NULL, 4),
    (5, '2019-10-18 16:32:21', '', 1, 0, 'PCC Update / Report', b'1', NULL, NULL, 1, NULL, 4);
