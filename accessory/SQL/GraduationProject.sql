CREATE TABLE `user` (
`id` int NOT NULL,
`name` varchar NOT NULL DEFAULT "",
`pass_word` varchar NOT NULL DEFAULT ""
);

CREATE TABLE `group` (
`id` int NOT NULL,
`project_name` varchar NOT NULL DEFAULT "",
`start_time` datetime NULL,
`description` varchar NOT NULL DEFAULT ""
);

CREATE TABLE `uesr_in_group` (
`user_id` int NOT NULL,
`group_id` int NOT NULL
);

