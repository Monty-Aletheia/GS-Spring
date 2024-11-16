CREATE TABLE tb_devices
(
    id                    RAW(16)       NOT NULL,
    name                  VARCHAR2(255) NOT NULL,
    category              VARCHAR2(255),
    model                 VARCHAR2(255),
    power_rating          DOUBLE PRECISION,
    estimated_usage_hours DOUBLE PRECISION,
    consumption           DOUBLE PRECISION,
    created_at            TIMESTAMP NOT NULL,
    updated_at            TIMESTAMP,
    CONSTRAINT pk_tb_devices PRIMARY KEY (id)
);

CREATE TABLE tb_users
(
    id         RAW(16)       NOT NULL,
    name       VARCHAR2(255) NOT NULL,
    password VARCHAR2(255) NOT NULL,
    email      VARCHAR2(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT pk_tb_users PRIMARY KEY (id)
);

CREATE TABLE user_devices
(
    device_id RAW(16) NOT NULL,
    user_id   RAW(16) NOT NULL
);

ALTER TABLE tb_users
    ADD CONSTRAINT uc_tb_users_email UNIQUE (email);

ALTER TABLE user_devices
    ADD CONSTRAINT fk_usedev_on_device FOREIGN KEY (device_id) REFERENCES tb_devices (id);

ALTER TABLE user_devices
    ADD CONSTRAINT fk_usedev_on_user FOREIGN KEY (user_id) REFERENCES tb_users (id);
