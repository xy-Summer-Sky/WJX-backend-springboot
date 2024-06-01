-- Create `users` table
CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
                                     age SMALLINT,
                                     gender SMALLINT,
                                     phone VARCHAR(20),
                                     password VARCHAR(255) NOT NULL,
                                     email VARCHAR(255) UNIQUE NOT NULL
);

-- Create `surveys` table
CREATE TABLE IF NOT EXISTS surveys (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       title VARCHAR(255) NOT NULL,
                                       description TEXT,
                                       created_by INT,
                                       FOREIGN KEY (created_by) REFERENCES users(id),
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create `questions` table
CREATE TABLE IF NOT EXISTS questions (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         text VARCHAR(255) NOT NULL,
                                         type VARCHAR(255) NOT NULL,
                                         survey_id BIGINT,
                                         FOREIGN KEY (survey_id) REFERENCES surveys(id) ON DELETE CASCADE
);

-- Create `options` table
CREATE TABLE IF NOT EXISTS options (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       option_text VARCHAR(255) NOT NULL,
                                       question_id BIGINT,
                                       FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

-- Create `responses` table
CREATE TABLE IF NOT EXISTS responses (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         answer_text TEXT NOT NULL ,
                                         question_id BIGINT,
                                         FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);
create table if not exists surveys_state
(
    survey_id     bigint                                    not null,
    id            int auto_increment
        primary key,
    receiveNumber int                                       not null,
    state         enum ('已发布', '未发布', '结束', '暂停') not null,
    constraint survey_id
        foreign key (survey_id) references surveys (id)
);

