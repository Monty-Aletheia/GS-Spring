ALTER TABLE tb_users
    ADD firebase_id VARCHAR2(255);

ALTER TABLE tb_users
    ADD CONSTRAINT uc_tb_users_firebaseid UNIQUE (firebase_id);