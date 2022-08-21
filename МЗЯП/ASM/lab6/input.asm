EXTRN NEWSTR: NEAR	

PUBLIC INPUTCOM
PUBLIC INPUT

PUBLIC USER_NUMBER

; Сегмент данных
DATAS SEGMENT PARA PUBLIC 'DATA'
	PATTERN DB '_________________$'
	INPUT_MSG DB 'Input signed binary number: $'
	USER_NUMBER DB 16 DUP('0'), '$'
DATAS ENDS	

; Сегмент кода
CODES SEGMENT PARA PUBLIC 'CODE'	
    ASSUME CS:CODES, DS:DATAS

;______________
PRINT_PATTERN PROC NEAR
	MOV AH, 9
	MOV DX, OFFSET PATTERN
	INT 21h
	RET
PRINT_PATTERN ENDP

; ввод номера команды	
INPUTCOM PROC NEAR
	MOV AH, 1
	INT 21h	
	SUB AL, '0'
	MOV CL, 2
	MUL CL
	MOV SI, AX
	RET
INPUTCOM ENDP

; ввод двоичного знаково числа
INPUT PROC NEAR
	MOV AH, 9
	MOV DX, OFFSET INPUT_MSG
	INT 21h
	CALL NEWSTR
	CALL PRINT_PATTERN
	CALL NEWSTR
	MOV BX, 0

	MOV AH, 1	; для ввода числа по цифрам
	INT 21h
	CMP AL, '-'
	JE NEG_NUMBER
	JNE POS_NUMBER

	POS_NUMBER:
		JMP INPUT_POS_DIGIT

	NEG_NUMBER:
		JMP INPUT_NEG_DIGIT

	INPUT_NEG_DIGIT:
		MOV CX, 16
		INPUT_NEG_DIGIT_LOOP:
			MOV AH, 1	; для ввода числа по цифрам
			INT 21h
			SUB AL, '0'
			CMP AL, 0
			JE ADD_ONE
			JNE ADD_ZERO

			ADD_ONE:
				MOV USER_NUMBER[BX], 1
				JMP INPUT_NEG_DIGIT_NEXT_ITER
			ADD_ZERO:
				MOV USER_NUMBER[BX], 0

			INPUT_NEG_DIGIT_NEXT_ITER:
				INC BX
			loop INPUT_NEG_DIGIT_LOOP
		JMP PLUS_ONE

	INPUT_POS_DIGIT:
		MOV CX, 16
		INPUT_POS_DIGIT_LOOP:
			MOV AH, 1	; для ввода числа по цифрам
			INT 21h
			SUB AL, '0'
			MOV USER_NUMBER[BX], AL
			INC BX
			loop INPUT_POS_DIGIT_LOOP
		JMP INPUT_EXIT

	PLUS_ONE:
		MOV CX, 16
		MOV BX, 15
		PLUS_ONE_LOOP:
			CMP USER_NUMBER[BX], 0
			JE CHANGE_TO_ONE_AND_EXIT

			MOV USER_NUMBER[BX], 0
			JMP PLUS_ONE_LOOP_NEXT_ITER

			CHANGE_TO_ONE_AND_EXIT:
				MOV USER_NUMBER[BX], 1
				JMP INPUT_EXIT

			PLUS_ONE_LOOP_NEXT_ITER:
				DEC BX
		loop PLUS_ONE_LOOP

	INPUT_EXIT:
		RET
INPUT ENDP

CODES ENDS
END