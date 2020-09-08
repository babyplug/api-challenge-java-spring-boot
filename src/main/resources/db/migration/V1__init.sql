CREATE TABLE user  (
  id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  display_name VARCHAR(255),
  age int(3) NULL,
  username VARCHAR(50),
  password VARCHAR(100),
  deleted TINYINT(1) NOT NULL DEFAULT 0,
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  created_by INT NULL,
  updated_by INT NULL
);

CREATE TABLE author(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NULL,
  deleted TINYINT(1) NOT NULL DEFAULT 0,
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  created_by INT NULL,
  updated_by INT NULL
);

CREATE TABLE photo (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NULL,
  description	TEXT NULL,
  filename VARCHAR(255) NULL,
  views	DOUBLE NULL,
  is_published TINYINT(1) NULL DEFAULT 0,
  author_id INT NULL,
  deleted TINYINT(1) NOT NULL DEFAULT 0,
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  created_by INT NULL,
  updated_by INT NULL
);

CREATE TABLE photo_metadata (
  id INT PRIMARY KEY AUTO_INCREMENT,
  height INT NOT NULL,
  width	INT NOT NULL,
  orientation VARCHAR(255) NULL,
  compressed VARCHAR(255) NULL,
  comment VARCHAR(255) NULL,
  photo_id INT NOT NULL,
  deleted TINYINT(1) NOT NULL DEFAULT 0,
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  created_by INT NULL,
  updated_by INT NULL,
  FOREIGN KEY (photo_id) REFERENCES photo(id)
);

CREATE TABLE album(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NULL,
  deleted TINYINT(1) NOT NULL DEFAULT 0,
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  created_by INT NULL,
  updated_by INT NULL
);

CREATE TABLE album_photos_photo (
  album_id INT,
  photo_id INT,
  PRIMARY KEY (album_id, photo_id),
  FOREIGN KEY (album_id) REFERENCES album(id),
  FOREIGN KEY (photo_id) REFERENCES photo(id)
);