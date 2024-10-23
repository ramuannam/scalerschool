//So this table I'm going to use to save my contact details that my end user is submitting through my contact page inside my web application into this database table.
CREATE TABLE IF NOT EXISTS contact_msg (
                                           contact_id INT AUTO_INCREMENT PRIMARY KEY,
                                           name VARCHAR(100) NOT NULL,
                                           mobile_num VARCHAR(10) NOT NULL,
                                           email VARCHAR(100) NOT NULL,
                                           subject VARCHAR(100) NOT NULL,
                                           message VARCHAR(500) NOT NULL,
                                           status VARCHAR(10) NOT NULL,
                                           created_at TIMESTAMP NOT NULL,
                                           created_by VARCHAR(50) NOT NULL,
                                           updated_at TIMESTAMP DEFAULT NULL,
                                           updated_by VARCHAR(50) DEFAULT NULL
);
