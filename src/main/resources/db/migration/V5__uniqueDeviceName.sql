ALTER TABLE tb_devices
    ADD CONSTRAINT uc_tb_devices_name UNIQUE (name);