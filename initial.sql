    USE ocere;

    INSERT INTO auth_user (auth_user_id,first_name,last_name,email,password,mailpassword,status, client)
    VALUES  (1,'Ankit','Wasankar','admin@gmail.com','$2a$10$DD/FQ0hTIprg3fGarZl1reK1f7tzgM4RuFKjAKyun0Si60w6g3v5i','$2a$10$DD/FQ0hTIprg3fGarZl1reK1f7tzgM4RuFKjAKyun0Si60w6g3v5i','VERIFIED', false );

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

    INSERT INTO client (id, status, author_id, city, company_name, contact_us_page, address_line1, address_line2, job_title, country, county, tier, website)
    VALUES (1, 'ACTIVE', 1, 'Zurich', 'Cheltenham-Ham', 'https://www.ocere.com/', 'addressline1', 'addressline2', 'Job title1', 'Switzerland', 'Zurich', 'A', 'https://www.ocere.com/');
    INSERT INTO client (id, status, author_id, city, company_name, contact_us_page, address_line1, address_line2, job_title, country, county, tier, website)
    VALUES (2, 'ACTIVE', 1, 'Cheltenham', 'Cheltenham-bacon', 'https://www.ocere.com/', 'addressline1', 'addressline2', 'Job title2', 'UK', 'Gloucestershire', 'B', 'https://www.ocere.com/');
    INSERT INTO client (id, status, author_id, city, company_name, contact_us_page, address_line1, address_line2, job_title, country, county, tier, website)
    VALUES (3, 'ACTIVE', 1, 'London', 'Cheltenham-steak', 'https://www.ocere.com/', 'addressline1', 'addressline2', 'Job title3', 'UK', 'London', 'C', 'https://www.ocere.com/');

    INSERT INTO `ticket` (`ticket_id`, `created_at`, `description`, `priority`, `status`, `subject`, `template`, `group_id`, `assigned_user_id`, `author_id`, `job_id`, `turnaround_id`) VALUES
    (1, '2019-10-18 16:20:02', '', 1, 0, 'PPC Review + Proposal', b'1', NULL, NULL, 1, NULL, 4),
    (2, '2019-10-18 16:31:08', '', 1, 0, 'Digital Review + Proposal', b'1', NULL, NULL, 1, NULL, 4),
    (3, '2019-10-18 16:31:29', '', 1, 0, 'Source sites for Outreach (new client)', b'1', NULL, NULL, 1, NULL, 4),
    (4, '2019-10-18 16:31:53', '', 1, 0, 'GA Analysis for Potential Client (need guidelines)', b'1', NULL, NULL, 1, NULL, 4),
    (5, '2019-10-18 16:32:21', '', 1, 0, 'PCC Update / Report', b'1', NULL, NULL, 1, NULL, 4);

    INSERT INTO contact (id, first_name, last_name, phone, email) VALUES
    (1, 'John', 'Jones', '012345689', 'john@jones.com'),
    (2, 'James', 'Jefferson', '012345689', 'james@jefferson.com'),
    (3, 'Jeremy', 'Jenkins', '012345689', 'jeremy@jenkins.com'),
    (4, 'Jane', 'Jarvis', '012345689', 'jane@jarvis.com'),
    (5, 'Jack', 'Johnson', '012345689', 'jack@johnson.com');
