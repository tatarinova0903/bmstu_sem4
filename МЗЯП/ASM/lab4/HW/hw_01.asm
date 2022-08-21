PUBLIC M
EXTRN print: far

SSTK SEGMENT para STACK 'STACK'
	db 100 dup(0)
SSTK ENDS

SD1 SEGMENT para public 'DATA'
	M db(0)
SD1 ENDS

SC1 SEGMENT para public 'CODE'
	assume CS:SC1, DS:SD1
main:	
        MOV AH, 01h ; ввод
        INT 21h
        MOV M, AL
        
        call print

        MOV AX, 4C00h
        INT 21h
SC1 ENDS
END main
