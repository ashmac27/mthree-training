INSERT INTO users (FirstName, LastName, `Role`) 
VALUES
("Admin_f1", "Admin_l1", "admin"),
("Member_f1", "Member_l1", "member"),
("Member_f2", "Member_l2", "member"),
("Disabled_f1", "Disabled_l1", "disabled");

INSERT INTO posts (Title, Content, DateAdded, Approved, PublishDate, ExpireDate, UserId)
VALUES
("Title_1", "Content_1", "2021-01-30 07:27:39", true, null, null, 1),
("Title_2", "Content_2", "2021-03-30 07:27:39", true, null, "2021-08-30 07:27:39", 1),
("Title_3", "Content_3", "2021-05-30 07:27:39", true, "2021-09-30 07:27:39", null, 1),
("Title_4", "Content_4", "2021-04-30 07:27:39", true, "2021-08-30 07:27:39", "2021-12-30 07:27:39", 1),
("Title_5", "Content_5", "2021-06-30 07:27:39", false, null, null, 2),
("Title_6", "Content_6", "2021-06-30 07:27:39", false, "2021-12-30 07:27:39", "2022-12-30 07:27:39", 2),
("Title_7", "Content_7", "2021-07-30 07:27:39", false, "2021-12-30 07:27:39", null, 3),
("Title_8", "Content_8", "2021-08-30 07:27:39", false, null, "2021-12-30 07:27:39", 3);

INSERT INTO hashtags (PostId, Tag)
VALUES
(1 , "Hamburger"),
(1 , "GoldFlakes"),
(1 , "SuperFancy"),
(1 , "IAmRich"),
(4 , "SaveTheBees"),
(6 , "SchoolIsGreat"),
(7 , "JavaIsTheBest"),
(7 , "PythonIsTheBest"),
(8 , "ILuvLinux"),
(8 , "IUseArchBtw"),
(8 , "InstallGentoo");
