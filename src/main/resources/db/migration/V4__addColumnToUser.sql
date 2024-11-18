ALTER TABLE tb_users
    ADD firebase_id RAW(16);

ALTER TABLE tb_users
    ADD CONSTRAINT uc_tb_users_firebaseid UNIQUE (firebase_id);