CREATE TABLE expenses
(
    expense_id      BIGSERIAL PRIMARY KEY,
    protocol_number VARCHAR(255)   NOT NULL UNIQUE,
    expense_type    VARCHAR(255)   NOT NULL,
    protocol_date   TIMESTAMP,
    expire_date     DATE,
    creditor        VARCHAR(255)   NOT NULL,
    description     VARCHAR(255),
    value           NUMERIC(19, 2) NOT NULL
);

CREATE TABLE commitments
(
    commitment_id     BIGSERIAL PRIMARY KEY,
    commitment_number VARCHAR(255)   NOT NULL UNIQUE,
    commitment_date   DATE           NOT NULL,
    value             NUMERIC(19, 2) NOT NULL,
    note              TEXT,
    expense_id        BIGINT,
    CONSTRAINT fk_commitment_expense FOREIGN KEY (expense_id) REFERENCES expenses (expense_id)
);

CREATE TABLE payments
(
    payment_id     BIGSERIAL PRIMARY KEY,
    payment_number VARCHAR(255) NOT NULL UNIQUE,
    payment_date   DATE         NOT NULL,
    value          NUMERIC(19, 2),
    note           TEXT,
    commitment_id  BIGINT,
    CONSTRAINT fk_payment_commitment FOREIGN KEY (commitment_id) REFERENCES commitments (commitment_id)
);

CREATE INDEX idx_commitment_expense ON commitments (expense_id);
CREATE INDEX idx_payment_commitment ON payments (commitment_id);