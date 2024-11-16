ALTER TABLE user_devices
    ADD consumption DOUBLE PRECISION;

ALTER TABLE user_devices
    ADD created_at TIMESTAMP;

ALTER TABLE user_devices
    ADD estimated_usage_hours DOUBLE PRECISION;

ALTER TABLE user_devices
    ADD id RAW(16);

ALTER TABLE user_devices
    ADD updated_at TIMESTAMP;

ALTER TABLE user_devices
    MODIFY consumption NOT NULL;

ALTER TABLE user_devices
    MODIFY created_at NOT NULL;

ALTER TABLE user_devices
    MODIFY estimated_usage_hours NOT NULL;

ALTER TABLE user_devices
    ADD CONSTRAINT pk_user_devices PRIMARY KEY (id);

ALTER TABLE TB_DEVICES
DROP
COLUMN CONSUMPTION;

ALTER TABLE TB_DEVICES
DROP
COLUMN ESTIMATED_USAGE_HOURS;