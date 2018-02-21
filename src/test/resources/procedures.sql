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
	SELECT SYSDATE INTO param1 FROM DUAL;
END;

CREATE OR REPLACE PROCEDURE JBSPROCDATEINOUT(param1 IN DATE, param2 OUT DATE)
IS
BEGIN
      param2:=param1;
END;

