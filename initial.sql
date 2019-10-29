USE ocere;

INSERT INTO auth_user (auth_user_id, first_name, last_name, email, password, mailpassword, status, client)
VALUES (1, 'Ankit', 'Wasankar', 'admin@gmail.com', '$2a$10$DD/FQ0hTIprg3fGarZl1reK1f7tzgM4RuFKjAKyun0Si60w6g3v5i',
        '$2a$10$DD/FQ0hTIprg3fGarZl1reK1f7tzgM4RuFKjAKyun0Si60w6g3v5i', 'VERIFIED', false);

INSERT INTO auth_user_role (auth_user_id, auth_role_id)
VALUES ('1', '1'),
       ('1', '2'),
       ('1', '3');

INSERT INTO auth_role (auth_role_id, role_name, role_desc)
VALUES (1, 'DEV_USER', 'DEV_USER'),
       (2, 'ADMIN_USER', 'ADMIN_USER'),
       (3, 'STAFF_USER', 'STAFF_USER'),
       (4, 'CLIENT_USER', 'CLIENT_USER');

INSERT INTO turnaround (id, description, hours)
VALUES (1, '2 Hours', 2),
       (2, '6 Hours', 6),
       (3, '12 Hours', 12),
       (4, '1 Day', 24),
       (5, '2 Days', 48),
       (6, '3 Days', 72),
       (7, '5 Days', 120),
       (8, '7 Days', 168);
INSERT INTO usergroup (group_id, is_active, created_at, is_empty, name, updated_at)
VALUES (1, true, NOW(), false, 'GroupTest', NOW());

INSERT INTO client (id, status, author_id, city, company_name, contact_id, contact_us_page,
                    address_line1, address_line2, job_title, country, county, tier, website)
VALUES (1, 'ACTIVE', 1, 'Zurich', 'Cheltenham-Ham', 1, 'https://www.ocere.com/', 'addressline1', 'addresline2', 'Job title', 'Switzerland', 'Zurich', 'A',
        'https://www.ocere.com/');
INSERT INTO client (id, status, author_id, city, company_name, contact_id, contact_us_page,
                    address_line1, address_line2, job_title, country, county, tier, website)
VALUES (2, 'ACTIVE', 1, 'Cheltenham', 'Cheltenham-bacon', 2, 'https://www.ocere.com/',
        'addressline1', 'addresline2', 'Job title', 'UK', 'Gloucstershire', 'B',
        'https://www.ocere.com/');
INSERT INTO client (id, status, author_id, city, company_name, contact_id, contact_us_page,
                   address_line1, address_line2, job_title, country, county, tier, website)
VALUES (3, 'ACTIVE', 1, 'London', 'Cheltenham-steak', 4, 'https://www.ocere.com/',
        'addressline1', 'addresline2', 'Job title', 'UK', 'London', 'C',
        'https://www.ocere.com/');

INSERT INTO `job` (`id`, `adwords_main_goal`, `asap`, `campaign_launch_date`, `company`, `content_keywords`,
                   `content_length`, `content_number_of_pieces`, `content_titles`, `content_type`, `content_value`,
                   `content_writing_style`, `created_at`, `currency`, `description`, `end_date`, `facebook_id`,
                   `facebook_main_goal`, `facebook_url`, `google_ads_id`, `google_doc_link`, `health_check`,
                   `landing_page_urls`, `link_value`, `monthly_click_spend`, `on_page_review`, `ppc_value`,
                   `reporting_email`, `reporting_keywords`, `seo_value`, `set_up_required`, `sign_off_required`,
                   `start_date`, `status`, `target_areas`, `target_keywords`, `thank_you_page_url`, `website`,
                   `white_label`, `author_id`, `client_id`, `owner_id`)
VALUES (1, 'That\s my main goal', b'0', '2019-10-15', 'Celtenham Ham',
        'asdf, asdf, asdf, asdf, asdf, asdf, asdf, asdf, asdf, asdf, asdf, asdf, asdf, asdf, asdf, asdf, asdf, asdf, asdf',
        3000, 15,
        'Title, Title, Title, Title, Title, Title, Title, Title, Title, Title, Title, Title, Title, Title, Title, Title, Title',
        0, 1234, 1, '2019-10-25 14:38:38', 0,
        '<p style=\'color: rgb(0, 0, 0); font-family: -apple-system, system-ui, \"Segoe UI\", Roboto, Oxygen, Ubuntu, Cantarell, \"Open Sans\", \"Helvetica Neue\", sans-serif; font-size: medium; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-style: initial; text-decoration-color: initial;\'><strong>Client URL:</strong></p><p style=\'color: rgb(0, 0, 0); font-family: -apple-system, system-ui, \"Segoe UI\", Roboto, Oxygen, Ubuntu, Cantarell, \"Open Sans\", \"Helvetica Neue\", sans-serif; font-size: medium; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-style: initial; text-decoration-color: initial;\'><strong>Google Ads ID (xxx-xxx-xxxx):</strong></p><p style=\'color: rgb(0, 0, 0); font-family: -apple-system, system-ui, \"Segoe UI\", Roboto, Oxygen, Ubuntu, Cantarell, \"Open Sans\", \"Helvetica Neue\", sans-serif; font-size: medium; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-style: initial; text-decoration-color: initial;\'><strong>Projection Brackets:</strong></p><p style=\'color: rgb(0, 0, 0); font-family: -apple-system, system-ui, \"Segoe UI\", Roboto, Oxygen, Ubuntu, Cantarell, \"Open Sans\", \"Helvetica Neue\", sans-serif; font-size: medium; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-style: initial; text-decoration-color: initial;\'><strong>Sample Keywords (Min. of 3):&nbsp;</strong></p><p style=\'color: rgb(0, 0, 0); font-family: -apple-system, system-ui, \"Segoe UI\", Roboto, Oxygen, Ubuntu, Cantarell, \"Open Sans\", \"Helvetica Neue\", sans-serif; font-size: medium; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-style: initial; text-decoration-color: initial;\'><br></p><p style=\'color: rgb(0, 0, 0); font-family: -apple-system, system-ui, \"Segoe UI\", Roboto, Oxygen, Ubuntu, Cantarell, \"Open Sans\", \"Helvetica Neue\", sans-serif; font-size: medium; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-style: initial; text-decoration-color: initial;\'><strong>Important: Please remember to setup a separate ticket to Request Access to MCC.</strong></p>',
        '2019-10-31', '1234', 'That\'s my main goal', 'facebook.url', '1234', 'link.stink', b'0', 'lading.url', 1234,
        '12', b'1', 1234, 'admin@gmail.com', '<p>asdf, asdf, asdf, asdf, asdf, asdf, asdf, asdf</p>', 1234, b'1', b'0',
        '2019-10-01', 0, 'sfdafdasgsdfg, sfdgfds,g ds,fg dsf,gsd g',
        '<p>asdf, asdf, asdf, asdf, asdf, asdf, asdf, asdf</p>', 'thankyou.url', 'cheltenhamham.uk', b'0', 1, 1, 1);
INSERT INTO `job_producttype` (`job_id`, `producttype_id`) VALUES
(1, 0),
(1, 1),
(1, 2),
(1, 3);
INSERT INTO `job_campaigntype` (`job_id`, `campaigntype_id`) VALUES
(1, 1),
(1, 2),
(1, 4),
(1, 5);
INSERT INTO `job_searchengine` (`job_id`, `seosearchengine_id`, `linksearchengine_id`) VALUES
(1, 1, NULL),
(1, 2, NULL),
(1, 3, NULL),
(2, 1, NULL),
(2, 3, NULL);


INSERT INTO ticket (ticket_id, created_at, description, priority, status, subject, template, group_id, assigned_user_id,
                    author_id, job_id, turnaround_id)
VALUES (1, '2019-10-18 16:20:02', '', 1, 0, 'PPC Review + Proposal', b'1', NULL, NULL, 1, NULL, 4),
       (2, '2019-10-18 16:31:08', '', 1, 0, 'Digital Review + Proposal', b'1', NULL, NULL, 1, NULL, 4),
       (3, '2019-10-18 16:31:29', '', 1, 0, 'Source sites for Outreach (new client)', b'1', NULL, NULL, 1, NULL, 4),
       (4, '2019-10-18 16:31:53', '', 1, 0, 'GA Analysis for Potential Client (need guidelines)', b'1', NULL, NULL, 1,
        NULL, 4),
       (5, '2019-10-18 16:32:21', '', 1, 0, 'PCC Update / Report', b'1', NULL, NULL, 1, NULL, 4);

    INSERT INTO contact (id, first_name, last_name, phone, email) VALUES
    (1, 'John', 'Jones', '012345689', 'john@jones.com'),
    (2, 'James', 'Jefferson', '012345689', 'james@jefferson.com'),
    (3, 'Jeremy', 'Jenkins', '012345689', 'jeremy@jenkins.com'),
    (4, 'Jane', 'Jarvis', '012345689', 'jane@jarvis.com'),
    (5, 'Jack', 'Johnson', '012345689', 'jack@johnson.com');

INSERT INTO predefined_ticket_collection (id, product_type)
VALUES (1, 0),
       (2, 1),
       (3, 2),
       (4, 3);
