CREATE  OR REPLACE TYPE VARCHARARRAY  IS TABLE OF VARCHAR(100);
CREATE  OR REPLACE TYPE INTEGERARRAY  IS TABLE OF INTEGER;
CREATE  OR REPLACE TYPE DOUBLEARRAY  IS TABLE OF DOUBLE PRECISION;
CREATE  OR REPLACE TYPE DATEARRAY  IS TABLE OF DATE;
CREATE  OR REPLACE TYPE DATETIMEARRAY  IS TABLE OF TIMESTAMP;

CREATE OR REPLACE PROCEDURE JBSPROCVOID
IS
BEGIN
       DBMS_OUTPUT.put_line('Procedure vacio');
END;

CREATE OR REPLACE PROCEDURE JBSPROCVARCHARIN(param1 IN VARCHAR2)
IS
BEGIN
       DBMS_OUTPUT.put_line('Procedure Input [' || param1 || ']');
END;

CREATE OR REPLACE PROCEDURE JBSPROCVARCHAROUT(param1 OUT VARCHAR2)
IS
BEGIN
      param1:='Procedure Varchar Input';
END;

CREATE OR REPLACE PROCEDURE JBSPROCVARCHARINOUT(param1 IN VARCHAR2, param2 OUT VARCHAR2)
IS
BEGIN
      param2:=param1;
END;

CREATE OR REPLACE PROCEDURE JBSPROCINTEGERIN(param1 IN NUMBER)
IS
BEGIN
       DBMS_OUTPUT.put_line('Procedure Input [' || param1 || ']');
END;

CREATE OR REPLACE PROCEDURE JBSPROCINTEGEROUT(param1 OUT NUMBER)
IS
BEGIN
      param1:=212313;
END;

CREATE OR REPLACE PROCEDURE JBSPROCINTEGERINOUT(param1 IN NUMBER, param2 OUT NUMBER)
IS
BEGIN
      param2:=param1;
END;

CREATE OR REPLACE PROCEDURE JBSPROCDOUBLEIN(param1 IN NUMBER)
IS
BEGIN
       DBMS_OUTPUT.put_line('Procedure Input [' || param1 || ']');
END;

CREATE OR REPLACE PROCEDURE JBSPROCDOUBLEOUT(param1 OUT NUMBER)
IS
BEGIN
      param1:=212313.33;
END;

CREATE OR REPLACE PROCEDURE JBSPROCDOUBLEINOUT(param1 IN NUMBER, param2 OUT NUMBER)
IS
BEGIN
      param2:=param1;
END;

CREATE OR REPLACE PROCEDURE JBSPROCDATEIN(param1 IN DATE)
IS
BEGIN
       DBMS_OUTPUT.put_line('Procedure Input [' || param1 || ']');
END;

CREATE OR REPLACE PROCEDURE JBSPROCDATEOUT(param1 OUT DATE)
IS
BEGIN
	SELECT TO_DATE('14/12/2010 12:25','dd/mm/yyyy HH:MI') INTO param1 FROM DUAL;
END;

CREATE OR REPLACE PROCEDURE JBSPROCDATEINOUT(param1 IN DATE, param2 OUT DATE)
IS
BEGIN
      SELECT TO_DATE('14/12/2010 12:25','dd/mm/yyyy HH:MI') INTO param2 FROM DUAL;
END;

CREATE OR REPLACE PROCEDURE JBSPROCVARCHARARRAYIN(param1 IN VARCHARARRAY)
IS
BEGIN
       DBMS_OUTPUT.put_line('Procedure Input');
END;

CREATE OR REPLACE PROCEDURE JBSPROCVARCHARARRAYOUT(param1 OUT VARCHARARRAY)
IS
BEGIN
      param1:=VARCHARARRAY('valor1','valor2');
END;

CREATE OR REPLACE PROCEDURE JBSPROCVARCHARARRAYINOUT(param1 IN VARCHARARRAY, param2 OUT VARCHARARRAY)
IS
BEGIN
      param2:=VARCHARARRAY('valor1','valor2');
END;

CREATE OR REPLACE PROCEDURE JBSPROCINTEGERARRAYIN(param1 IN INTEGERARRAY)
IS
BEGIN
       DBMS_OUTPUT.put_line('Procedure Input ]');
END;

CREATE OR REPLACE PROCEDURE JBSPROCINTEGERARRAYINOUT(param1 IN INTEGERARRAY, param2 OUT INTEGERARRAY)
IS
BEGIN
       param2:= INTEGERARRAY(111,222,333);
END;

CREATE OR REPLACE PROCEDURE JBSPROCINTEGERARRAYOUT(param1 OUT INTEGERARRAY)
IS
BEGIN
       param1:= INTEGERARRAY(111,222,333);
END;

CREATE OR REPLACE PROCEDURE JBSPROCDOUBLEARRAYIN(param1 IN DOUBLEARRAY)
IS
BEGIN
       DBMS_OUTPUT.put_line('Procedure Input ]');
END;

CREATE OR REPLACE PROCEDURE JBSPROCDOUBLEARRAYINOUT(param1 IN DOUBLEARRAY, param2 OUT DOUBLEARRAY)
IS
BEGIN
       param2:= DOUBLEARRAY(111.11,222.22,333.33);
END;

CREATE OR REPLACE PROCEDURE JBSPROCDOUBLEARRAYOUT(param1 OUT DOUBLEARRAY)
IS
BEGIN
       param1:= DOUBLEARRAY(111.11,222.22,333.33);
END;

CREATE OR REPLACE PROCEDURE JBSPROCDATEARRAYIN(param1 IN DATEARRAY)
IS
BEGIN
       DBMS_OUTPUT.put_line('Procedure Input');
END;

CREATE OR REPLACE PROCEDURE JBSPROCDATEARRAYOUT(param1 OUT DATEARRAY)
IS
BEGIN
		param1 :=  DATEARRAY('14/12/2010','15/12/2010','16/12/2010');
END;

CREATE OR REPLACE PROCEDURE JBSPROCDATEARRAYINOUT(param1 IN DATEARRAY, param2 OUT DATEARRAY)
IS
BEGIN
       param2 :=  DATEARRAY('14/12/2010','15/12/2010','16/12/2010');
END;


CREATE OR REPLACE PROCEDURE JBSPROCDATETIMEARRAYIN(param1 IN DATETIMEARRAY)
IS
BEGIN
        DBMS_OUTPUT.put_line('Procedure Input ]');
END;

CREATE OR REPLACE PROCEDURE JBSPROCDATETIMEARRAYOUT(param1 OUT DATETIMEARRAY)
IS
BEGIN
       param1 :=  DATETIMEARRAY('14/12/2010 12:25','15/12/2010 13:25','16/12/2010 14:25');
END;

CREATE OR REPLACE PROCEDURE JBSPROCDATETIMEARRAYINOUT(param1 IN DATETIMEARRAY, param2 OUT DATETIMEARRAY)
IS
BEGIN
       param2 :=  DATETIMEARRAY('14/12/2010 12:25','15/12/2010 13:25','16/12/2010 14:25');
END;
