CREATE TABLE USER_INFO
(
   ID     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
   FULL_NAME  VARCHAR(256) NOT NULL,
   EMAIL    VARCHAR(256) NOT NULL,
   PASSWORD VARCHAR(256) NOT NULL
);