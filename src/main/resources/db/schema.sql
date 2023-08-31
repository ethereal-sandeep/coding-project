-- Create user table
CREATE TABLE "user" (
                        "id" SERIAL PRIMARY KEY,
                        "user_id" VARCHAR(255) NOT NULL UNIQUE,
                        "name" VARCHAR(255) NOT NULL,
                        "email" VARCHAR(255) NOT NULL,
                        "time_zone" VARCHAR(255) NOT NULL,
                        "created_at" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                        "updated_at" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                        "source" VARCHAR(255)
);

-- Create user_availability table
CREATE TABLE "user_availability" (
                                     "id" SERIAL PRIMARY KEY,
                                     "user_id" VARCHAR(255) NOT NULL,
                                     "date" BIGINT NOT NULL,
                                     "start_time" BIGINT,
                                     "end_time" BIGINT,
                                     "status" VARCHAR(50) NOT NULL,
                                     "created_at" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                                     "updated_at" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                                     "source" VARCHAR(255)
);

-- Add indexes
CREATE INDEX "idx_user_userid" ON "user"("user_id");
CREATE INDEX "idx_user_availability_userid" ON "user_availability"("user_id");

-- ALTER TABLE "user_availability"
-- ADD CONSTRAINT "fk_user_availability_user"
-- FOREIGN KEY ("user_id") REFERENCES "user"("user_id");

truncate table "user";