DELETE
FROM rent
WHERE id >= 28602604;

DELETE
FROM real_estate
WHERE id >= 28602604;

DELETE
FROM BATCH_STEP_EXECUTION_CONTEXT
WHERE STEP_EXECUTION_ID >= 513631;

DELETE
FROM BATCH_STEP_EXECUTION
WHERE STEP_EXECUTION_ID >= 513631;

UPDATE BATCH_STEP_EXECUTION_SEQ
SET ID = 513630;

DELETE
FROM BATCH_JOB_EXECUTION_CONTEXT
WHERE JOB_EXECUTION_ID >= 2196;

DELETE
FROM BATCH_JOB_EXECUTION_PARAMS
WHERE JOB_EXECUTION_ID >= 2196;

DELETE
FROM BATCH_JOB_EXECUTION
WHERE JOB_EXECUTION_ID >= 2196;

UPDATE BATCH_JOB_EXECUTION_SEQ
SET ID = 2195;

DELETE
FROM BATCH_JOB_INSTANCE
WHERE JOB_INSTANCE_ID >= 2196;

UPDATE BATCH_JOB_SEQ
SET ID = 2195;