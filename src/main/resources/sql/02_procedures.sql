CREATE OR REPLACE FUNCTION count_enrollments(student_id INT)
RETURNS INT
LANGUAGE plpgsql
AS $func$
DECLARE
    total INT;
BEGIN
    SELECT COUNT(*)
    INTO total
    FROM matricula
    WHERE id_alumno = student_id;

    RETURN total;
END;
$func$;
