-- H2 specific code
-- Indexes (performances)
CREATE INDEX LABELS_CODE_IDX               ON LABELS (CODE);
CREATE INDEX LABELS_ACTIVE_CODE_IDX        ON LABELS (CODE, IS_ACTIVE);