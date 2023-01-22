# java-web

Link deploy: https://java-web-production.up.railway.app/
but in deploy, if you add the form for the first time, there are still errors, but only for the first time and if you use mobile, the error is in the "kode karyawan"

# Java Web menggunakan framework Springboot (jdbcTemplate)

# Database postgress use "SUPABASE"
"Supabase is an open source Firebase alternative. Start your project with a Postgres database, Authentication, instant APIs, Edge Functions, Realtime subscriptions, and Storage"

Create Table Manual: 
```
CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TABLE karyawan
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    kode_karyawan VARCHAR(255),
    name VARCHAR(255) unique NOT NULL,
    entry_date DATE NOT NULL,
    no_hp varchar(16),
    limit_reimbursement INT,
    created_at timestamp(3) NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    updated_at timestamp(3) NOT NULL DEFAULT (CURRENT_TIMESTAMP)
);
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON karyawan
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();
```

