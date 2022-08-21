EXTRN M: byte
PUBLIC print

SC2 SEGMENT para public 'CODE'
	assume CS:SC2
print:
    MOV AH, 02h ; печать буквы
    MOV DL, 32
    INT 21h
    
    ADD M, 30h
    MOV DL, M
    INT 21h

	ret
SC2 ENDS
END